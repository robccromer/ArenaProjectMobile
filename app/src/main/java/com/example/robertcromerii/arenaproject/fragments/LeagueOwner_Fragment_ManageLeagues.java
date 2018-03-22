package com.example.robertcromerii.arenaproject.fragments;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.robertcromerii.arenaproject.DBHandler;
import com.example.robertcromerii.arenaproject.R;
import com.example.robertcromerii.arenaproject.activities.LoginActivity;
import com.example.robertcromerii.arenaproject.adapters.ManageLeaguesListAdapter;
import com.example.robertcromerii.arenaproject.models.LeagueListData;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Robert Cromer II on 3/19/2018.
 */

public class LeagueOwner_Fragment_ManageLeagues extends Fragment
{
    private ListView LV_manageLeagues;
    private List<LeagueListData> leaguesListDataList;
    private ManageLeaguesListAdapter manageLeaguesListAdapter;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View view =  inflater.inflate(R.layout.fragment_leagueowner_manageleagues, null);


        LV_manageLeagues = (ListView) view.findViewById(R.id.LV_manageLeagues);

        return view;
    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        GetLeagueListCall();
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
}
