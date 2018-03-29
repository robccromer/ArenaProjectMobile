package com.example.robertcromerii.arenaproject.fragments;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;

import com.example.robertcromerii.arenaproject.DBHandler;
import com.example.robertcromerii.arenaproject.R;
import com.example.robertcromerii.arenaproject.activities.LoginActivity;
import com.example.robertcromerii.arenaproject.models.LeagueListData;
import com.example.robertcromerii.arenaproject.models.TournamentStyleData;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import static android.content.Context.INPUT_METHOD_SERVICE;

/**
 * Created by Robert Cromer II on 3/19/2018.
 */

public class LeagueOwner_Fragment_ManageTournaments extends Fragment
{

    private String selectedTournamentStyleID;
    private String selectedLeagueID;

    EditText ET_tournamentName;
    EditText ET_tournamentDesc;
    EditText ET_tournamentDate;
    Button Button_addTournament;
    Spinner Spinner_tournamentStyle;
    Spinner Spinner_tournamentLeague;

    ArrayList<TournamentStyleData> tournamentStyleList;
    ArrayList<LeagueListData> tournamentLeagueList;

    ArrayAdapter<LeagueListData> LeagueListadapter;
    ArrayAdapter<TournamentStyleData> tournamentStyleAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_leagueowner_managetournaments, null);

        ET_tournamentName = (EditText) view.findViewById(R.id.ET_tournamentName);
        ET_tournamentDesc = (EditText) view.findViewById(R.id.ET_tournamentDesc);
        ET_tournamentDate = (EditText) view.findViewById(R.id.ET_tournamentDate);
        Button_addTournament = (Button) view.findViewById(R.id.Button_addTournament);
        Spinner_tournamentLeague = (Spinner) view.findViewById(R.id.Spinner_tournamentLeague);
        Spinner_tournamentStyle = (Spinner) view.findViewById(R.id.Spinner_tournamentStyle);

        try{
            GetLeagueListCall();
            GetTournamentStyleCall();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
        }

        return view;
    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Button_addTournament.setOnClickListener(new View.OnClickListener() {
            String ET_tournamentNameText, ET_tournamentDescText = null;
            @Override
            public void onClick(View v)
            {
                ET_tournamentNameText = ET_tournamentName.getText().toString().trim();
                ET_tournamentDescText = ET_tournamentDesc.getText().toString().trim();

                if(TextUtils.isEmpty(ET_tournamentNameText))
                {
                    ET_tournamentName.setError("Field Cannot Be Empty");
                }
                if(TextUtils.isEmpty(ET_tournamentDescText))
                {
                    ET_tournamentDesc.setError("Field Cannot Be Empty");
                }
                else
                {
                    InsertTournamentCall();
                }
                try {
                    InputMethodManager inputManager = (InputMethodManager)getActivity(). getSystemService(INPUT_METHOD_SERVICE);
                    inputManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                }
                catch (Exception exception)
                {
                    exception.printStackTrace();
                }
            }
        });
        Spinner_tournamentLeague.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Object object = adapterView.getItemAtPosition(i);
                LeagueListData leagueListData = (LeagueListData) object;
                selectedLeagueID = leagueListData.getLeagueID();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        Spinner_tournamentStyle.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Object object = adapterView.getItemAtPosition(i);
                TournamentStyleData tournamentStyleData = (TournamentStyleData) object;
                selectedTournamentStyleID = tournamentStyleData.getStyleID();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void GetLeagueListCall() {
        GetLeaguesListBackground getLeaguesListBackground = new GetLeaguesListBackground(getContext());
        getLeaguesListBackground.execute();
    }
    private void GetTournamentStyleCall() {
        GetTournamentStyleBackground getTournamentStyleBackground = new GetTournamentStyleBackground(getContext());
        getTournamentStyleBackground.execute();
    }

    private class GetTournamentStyleBackground extends AsyncTask<Void,Void,Void> {
        private Context context;
        private PreparedStatement preparedStatement = null;
        private Connection connection = null;

        public GetTournamentStyleBackground(Context context)
        {
            this.context = context;
        }
        @Override
        protected Void doInBackground(Void... voids)
        {
            int i = 0;
            tournamentStyleList = new ArrayList<>();
            tournamentStyleAdapter = new ArrayAdapter<>(getContext(), R.layout.simple_spinner_dropdown_item, tournamentStyleList);
            try
            {
                connection = DBHandler.getConnection();
                String getGameQuery = "SELECT * FROM tournamentstylecode";

                preparedStatement = connection.prepareStatement(getGameQuery);
                preparedStatement.executeQuery();

                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next())
                {
                    String styleID = resultSet.getString("TournamentStyleCodeID");
                    String styleName = resultSet.getString("TournamentStyleCodeName");
                    tournamentStyleList.add(new TournamentStyleData(styleID,styleName));
                }
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
            finally
            {
                try
                {
                    if (preparedStatement != null)
                    {
                        preparedStatement.close();
                    }
                    if (connection != null)
                    {
                        connection.close();
                    }
                }
                catch(Exception exception)
                {
                    exception.printStackTrace();
                }
            }
            return null;
        }
        protected void onPostExecute(Void result)
        {
            try
            {
                super.onPostExecute(result);
                tournamentStyleAdapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item);
                Spinner_tournamentStyle.setAdapter(tournamentStyleAdapter);
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
        }
    }
    private class GetLeaguesListBackground extends AsyncTask<Void,Void,Void> {
        private Context context;
        private PreparedStatement preparedStatement = null;
        private Connection connection = null;

        public GetLeaguesListBackground(Context context)
        {
            this.context = context;
        }
        @Override
        protected Void doInBackground(Void... voids)
        {
            int i = 0;
            tournamentLeagueList = new ArrayList<>();
            LeagueListadapter = new ArrayAdapter<>(getContext(), R.layout.simple_spinner_dropdown_item, tournamentLeagueList);

            try
            {
                connection = DBHandler.getConnection();
                String getGameQuery = "SELECT * FROM arenadatabase.league WHERE users_userID_LeagueOwner = " + LoginActivity.currentUserID;

                preparedStatement = connection.prepareStatement(getGameQuery);
                preparedStatement.executeQuery();

                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next())
                {
                    String leagueID = resultSet.getString("LeagueID");
                    String leagueName = resultSet.getString("LeagueName");
                    tournamentLeagueList.add (new LeagueListData(leagueID,leagueName));
                }
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
            finally
            {
                try
                {
                    if (preparedStatement != null)
                    {
                        preparedStatement.close();
                    }
                    if (connection != null)
                    {
                        connection.close();
                    }
                }
                catch(Exception exception)
                {
                    exception.printStackTrace();
                }
            }
            return null;
        }
        protected void onPostExecute(Void result)
        {
            try
            {
                super.onPostExecute(result);
                LeagueListadapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item);
                Spinner_tournamentLeague.setAdapter(LeagueListadapter);
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
        }
    }

    private void InsertTournamentCall() {
        InsertTournamentBackground insertTournamentBackground = new InsertTournamentBackground();
        insertTournamentBackground.execute();
    }
    private class InsertTournamentBackground extends AsyncTask<Void,Void,Void> {
        private String ET_tournamentNameValue = ET_tournamentName.getText().toString().trim();
        private String ET_tournamentDescValue = ET_tournamentDesc.getText().toString().trim();
        private String ET_tournamentDateValue = ET_tournamentDate.getText().toString().trim();

        private PreparedStatement preparedStatement = null;
        private Connection connection = null;
        @Override
        protected Void doInBackground(Void... params)
        {
            try
            {
                connection = DBHandler.getConnection();
                String insertNewLeague = "INSERT INTO tournament (TournamentName, TournamentDescription, League_LeagueID, TournamentStyleCode_TournamentStyleCodeID, tournamentDate) VALUES (?,?,?,?,?)";
                preparedStatement = connection.prepareStatement(insertNewLeague);
                preparedStatement.setString(1, ET_tournamentNameValue);
                preparedStatement.setString(2, ET_tournamentDescValue);
                preparedStatement.setString(3, selectedLeagueID);
                preparedStatement.setString(4, selectedTournamentStyleID);
                preparedStatement.setString(5, ET_tournamentDateValue);

                Log.d("Statement", preparedStatement.toString());
                preparedStatement.execute();
            }
            catch (SQLException e)
            {
                e.printStackTrace();
            }
            finally
            {
                try
                {
                    if (preparedStatement != null)
                    {
                        preparedStatement.close();
                    }
                    if (connection != null)
                    {
                        connection.close();
                    }
                }
                catch(Exception exception)
                {
                    exception.printStackTrace();
                }
            }
            return null;
        }
        protected void onPostExecute(Void result)
        {
            super.onPostExecute(result);
            GetLeagueListCall();
        }
    }


}