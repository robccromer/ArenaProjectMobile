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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.robertcromerii.arenaproject.DBHandler;
import com.example.robertcromerii.arenaproject.R;
import com.example.robertcromerii.arenaproject.activities.LoginActivity;
import com.example.robertcromerii.arenaproject.adapters.ManageArenasListAdapter;
import com.example.robertcromerii.arenaproject.adapters.ManageLeaguesListAdapter;
import com.example.robertcromerii.arenaproject.models.ArenaListData;
import com.example.robertcromerii.arenaproject.models.LeagueListData;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static android.content.Context.INPUT_METHOD_SERVICE;

/**
 * Created by Robert Cromer II on 3/19/2018.
 */

public class LeagueOwner_Fragment_ManageLeagues extends Fragment
{
    private String selectedLeagueID = null;
    private String previousSelectedLeagueID = null;


    private List<LeagueListData> leaguesListDataList;
    private ManageLeaguesListAdapter manageLeaguesListAdapter;
    private List<ArenaListData> arenaList_spinner;

    private ListView LV_manageLeagues;
    private EditText ET_manageLeague_LeagueName, ET_manageLeague_LeagueDesc;
    private Spinner Spinner_manageLeague_Arena;
    private Button BTN_addLeague, BTN_removeLeague;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_leagueowner_manageleagues, null);

        LV_manageLeagues = (ListView) view.findViewById(R.id.LV_manageLeagues);
        ET_manageLeague_LeagueName = (EditText) view.findViewById(R.id.ET_manageLeague_LeagueName);
        ET_manageLeague_LeagueDesc = (EditText) view.findViewById(R.id.ET_manageLeague_LeagueDesc);
        BTN_addLeague = (Button) view.findViewById(R.id.BTN_addLeague);
        BTN_removeLeague = (Button) view.findViewById(R.id.BTN_removeLeague);
        Spinner_manageLeague_Arena = (Spinner)view.findViewById(R.id.Spinner_manageLeague_Arena);

        try
        {
            BTN_addLeague.setOnClickListener(new View.OnClickListener() {
                String ET_createLeagueNameText, ET_createLeagueDescText = null;
                @Override
                public void onClick(View v)
                {
                    ET_createLeagueNameText = ET_manageLeague_LeagueName.getText().toString().trim();
                    ET_createLeagueDescText = ET_manageLeague_LeagueName.getText().toString().trim();
                    if(TextUtils.isEmpty(ET_createLeagueNameText))
                    {
                        ET_manageLeague_LeagueName.setError("Field Cannot Be Empty");
                    }
                    if(TextUtils.isEmpty(ET_createLeagueDescText))
                    {
                        ET_manageLeague_LeagueDesc.setError("Field Cannot Be Empty");
                    }
                    else
                    {
                        InsertLeagueCall();
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
            BTN_removeLeague.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    DeleteDesiredLeagueCall(selectedLeagueID);
                }
            });
            LV_manageLeagues.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    TextView TV_leagueName= (TextView)view.findViewById(R.id.TV_leagueName);
                    TextView TV_leagueID= (TextView)view.findViewById(R.id.TV_leagueID);

                    TV_leagueName.setText(leaguesListDataList.get(i).getLeagueName());
                    TV_leagueID.setText(leaguesListDataList.get(i).getLeagueID());

                    previousSelectedLeagueID = selectedLeagueID;
                    //String arenaName = TV_ArenaName.getText().toString();
                    selectedLeagueID = TV_leagueID.getText().toString();

                    Toast.makeText(getContext(), "Selected League ID -" + selectedLeagueID + "|| Previous League ID" + previousSelectedLeagueID, Toast.LENGTH_SHORT).show();
                    //logData(arenaName);
                }
            });

        }
        catch(Exception exception)
        {
            exception.printStackTrace();
        }
        return view;
    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        GetLeagueListCall();
        GetArenaListCall();
        try
        {

        }
        catch(Exception exception)
        {
            exception.printStackTrace();
        }
    }

    private void GetLeagueListCall() {
        GetLeaguesListBackground getLeaguesListBackground = new GetLeaguesListBackground(getContext());
        getLeaguesListBackground.execute();
    }
    private void InsertLeagueCall() {
        InsertLeagueBackground insertLeagueBackground = new InsertLeagueBackground();
        insertLeagueBackground.execute();
    }
    private void GetArenaListCall() {
        GetArenaListBackground getArenaListBackground = new GetArenaListBackground();
        getArenaListBackground.execute();
    }
    private class InsertLeagueBackground extends AsyncTask<Void,Void,Void> {
        private String ET_manageLeague_LeagueNameValue = ET_manageLeague_LeagueName.getText().toString().trim();
        private String ET_manageLeague_LeagueDescValue = ET_manageLeague_LeagueDesc.getText().toString().trim();
        //private String Spinner_manageLeague_ArenaValue = Spinner_manageLeague_Arena.getSelectedItem().toString();
        private PreparedStatement preparedStatement = null;
        private Connection connection = null;
        @Override
        protected Void doInBackground(Void... params)
        {
            Log.d("", "League Name Entered: " + ET_manageLeague_LeagueNameValue);
            try
            {
                connection = DBHandler.getConnection();
                String insertNewLeague = "INSERT INTO arenadatabase.league (LeagueName, leagueDesc, users_userID_LeagueOwner) VALUES (?,?,?)";
                preparedStatement = connection.prepareStatement(insertNewLeague);
                preparedStatement.setString(1, ET_manageLeague_LeagueNameValue);
                preparedStatement.setString(2, ET_manageLeague_LeagueDescValue);
                preparedStatement.setString(3, LoginActivity.currentUserID);
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
            ET_manageLeague_LeagueName.setText(null);
            ET_manageLeague_LeagueNameValue = null;
            ET_manageLeague_LeagueDesc.setText(null);
            ET_manageLeague_LeagueDescValue = null;

            GetLeagueListCall();
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
            leaguesListDataList = new ArrayList<>();
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
                    leaguesListDataList.add(new LeagueListData(leagueID,leagueName));
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
                manageLeaguesListAdapter = new ManageLeaguesListAdapter(getActivity(), leaguesListDataList);
                LV_manageLeagues.setAdapter(manageLeaguesListAdapter);
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
        }
    }
    private class GetArenaListBackground extends AsyncTask<Void,Void,Void> {
        private PreparedStatement preparedStatement = null;
        private Connection connection = null;

        @Override
        protected Void doInBackground(Void... voids)
        {
            int i = 0;
            arenaList_spinner = new ArrayList<>();
            try
            {
                connection = DBHandler.getConnection();
                String getGameQuery = "SELECT * FROM arenadatabase.arena;";

                preparedStatement = connection.prepareStatement(getGameQuery);
                preparedStatement.executeQuery();

                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next())
                {
                    int arenaID = resultSet.getInt("ArenaID");
                    String arenaName = resultSet.getString("ArenaName");
                    arenaList_spinner.add(new ArenaListData(arenaID, arenaName));
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
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
        }
    }

    public void DeleteDesiredLeagueCall(String leagueID) {
        DeleteDesiredLeagueBackground deleteDesiredLeagueBackground = new DeleteDesiredLeagueBackground(leagueID);
        deleteDesiredLeagueBackground.execute();
    }
    private class DeleteDesiredLeagueBackground extends AsyncTask<Void,Void,Void> {
        private String leagueIDValue;
        private PreparedStatement preparedStatement = null;
        private Connection connection = null;
        public DeleteDesiredLeagueBackground(String leagueIDValue)
        {
            this.leagueIDValue = leagueIDValue;
        }
        @Override
        protected Void doInBackground(Void... voids)
        {
            try
            {
                connection = DBHandler.getConnection();
                String deleteGameQuery = "DELETE FROM arenadatabase.league WHERE LeagueID=" + leagueIDValue;

                preparedStatement = connection.prepareStatement(deleteGameQuery);
                preparedStatement.execute();
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
            super.onPostExecute(result);
            GetLeagueListCall();
        }
    }

}
