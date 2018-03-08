package com.example.robertcromerii.arenaproject.fragments;
        import android.content.Context;
        import android.os.AsyncTask;
        import android.os.Bundle;
        import android.support.annotation.Nullable;
        import android.support.v4.app.Fragment;
        import android.support.v4.app.FragmentTransaction;
        import android.text.TextUtils;
        import android.util.Log;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.Button;
        import android.widget.EditText;
        import android.widget.ExpandableListView;

        import com.example.robertcromerii.arenaproject.DBHandler;
        import com.example.robertcromerii.arenaproject.R;
        import com.example.robertcromerii.arenaproject.adapters.ManageGamesExpandableListAdapter;

        import java.sql.Connection;
        import java.sql.PreparedStatement;
        import java.sql.ResultSet;
        import java.sql.SQLException;
        import java.util.ArrayList;
        import java.util.HashMap;
        import java.util.List;

/**
 * Created by Robert Cromer II on 2/28/2018.
 */

public class Operator_Fragment_manageGames extends Fragment
{
    ExpandableListView manageGameExpandableListView = null;
    ManageGamesExpandableListAdapter manageGamesExpandableListAdapter;
    List<String> manageGameListHeader;
    HashMap<String,List<String>> manageGameListMap;
    EditText ET_gameName;
    Button BTN_addGame;
    Button BTN_operator_RefreshListView;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_operator_manage_games, null);

        manageGameExpandableListView = rootView.findViewById(R.id.ELV_manageGameList);

        return rootView;
    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ET_gameName = view.findViewById(R.id.ET_gameNameInput);
        BTN_addGame = view.findViewById(R.id.BTN_operator_addGame);
        BTN_operator_RefreshListView = view.findViewById(R.id.BTN_operator_RefreshListView);
        BTN_operator_RefreshListView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {ReloadFragement();
            }
        });
        BTN_addGame.setOnClickListener(new View.OnClickListener() {
            String ET_gameNameText = null;
            @Override
            public void onClick(View v)
            {
                ET_gameNameText = ET_gameName.getText().toString().trim();
                if(TextUtils.isEmpty(ET_gameNameText))
                {
                    ET_gameName.setError("Field Cannot Be Empty");
                }
                else
                {
                    new InsertGameBackground().execute();
                }
            }
        });
        GetGameListCall();
    }
    private void GetGameListCall() {
        GetGameListBackground getGameListBackground = new GetGameListBackground(getContext());
        getGameListBackground.execute();
    }
    public void DeleteDesiredGameCall(String childText, int groupID) {
        DeleteDesiredGameBackground deleteDesiredGame = new DeleteDesiredGameBackground(childText, groupID);
        deleteDesiredGame.execute();
    }
    private class DeleteDesiredGameBackground extends AsyncTask<Void,Void,Void> {
        private String gameIDValue;
        private int groupID;
        private PreparedStatement preparedStatement = null;
        private Connection connection = null;
        public DeleteDesiredGameBackground(String gameIDValue, int groupID)
        {
            this.gameIDValue = gameIDValue;
            this.groupID = groupID;
        }

        @Override
        protected Void doInBackground(Void... voids)
        {
            manageGamesExpandableListAdapter = null;
            try
            {
                connection = DBHandler.getConnection();
                String deleteGameQuery = "DELETE FROM arenadatabase.game WHERE GameID=" + gameIDValue;

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
            GetGameListCall();
        }
    }
    public void ReloadFragement()
    {
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.detach(this).attach(this).commit();
    }

    private class GetGameListBackground extends AsyncTask<Void,Void,Void> {
        private Context context;
        private PreparedStatement preparedStatement = null;
        private Connection connection = null;

        public GetGameListBackground(Context context)
        {
            this.context = context;
        }
        @Override
        protected Void doInBackground(Void... voids)
        {
            int i = 0;
            manageGameListHeader = new ArrayList<>();
            manageGameListMap = new HashMap<>();
            try
            {
                connection = DBHandler.getConnection();
                String getGameQuery = "SELECT * FROM arenadatabase.game;";

                preparedStatement = connection.prepareStatement(getGameQuery);
                preparedStatement.executeQuery();

                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next())
                {
                    List<String> manageGameListChild = new ArrayList<>();
                    String gameID = resultSet.getString("GameID");
                    String gameName = resultSet.getString("GameName");
                    manageGameListHeader.add(gameName);
                    manageGameListChild.add(gameID);
                    manageGameListMap.put(gameName, manageGameListChild);
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
                manageGamesExpandableListAdapter = new ManageGamesExpandableListAdapter(this.context, manageGameListHeader, manageGameListMap);
                manageGamesExpandableListAdapter.notifyDataSetChanged();
                manageGameExpandableListView.setAdapter(manageGamesExpandableListAdapter);
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
        }
    }
    private class InsertGameBackground extends AsyncTask<Void,Void,Void> {
        private String ET_gameNameText = ET_gameName.getText().toString().trim();
        private PreparedStatement preparedStatement = null;
        private Connection connection = null;
        @Override
        protected Void doInBackground(Void... params)
        {
            Log.d("", "Game Name Entered: " + ET_gameNameText);
            try
            {
                connection = DBHandler.getConnection();
                String insertNewGame = "INSERT INTO game (GameName) VALUES(?)";

                preparedStatement = connection.prepareStatement(insertNewGame);
                preparedStatement.setString(1, ET_gameNameText);
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
            ET_gameName.setText(null);
            ET_gameNameText = null;
            GetGameListCall();
        }
    }
}

