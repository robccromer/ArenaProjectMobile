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
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.robertcromerii.arenaproject.DBHandler;
import com.example.robertcromerii.arenaproject.R;
import com.example.robertcromerii.arenaproject.adapters.ManageArenasListAdapter;
import com.example.robertcromerii.arenaproject.models.ArenaListData;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static android.content.Context.INPUT_METHOD_SERVICE;

/**
 * Created by Robert Cromer II on 2/28/2018.
 */

public class Operator_Fragment_manageArenas extends Fragment
{
    private String selectedArenaID = null;
    private String previousSelectedArenaID = null;
    private Button BTN_manageArenaSubmit, BTN_manageArenaRemove;
    private TextView ET_createArena;
    private ListView LV_manageArenasList;
    private List<ArenaListData> arenaListDataList;
    private ManageArenasListAdapter manageArenasListAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_operator_manage_arenas, null);
        BTN_manageArenaSubmit = (Button) view.findViewById(R.id.BTN_manageArenaSubmit);
        BTN_manageArenaRemove = (Button) view.findViewById(R.id.BTN_manageArenaRemove);
        LV_manageArenasList = (ListView) view.findViewById(R.id.LV_manageArenasList);
        ET_createArena = (TextView) view.findViewById(R.id.ET_createArena);
        try {
            BTN_manageArenaSubmit.setOnClickListener(new View.OnClickListener() {
                String ET_gameNameText = null;
                @Override
                public void onClick(View v)
                {
                    ET_gameNameText = ET_createArena.getText().toString().trim();
                    if(TextUtils.isEmpty(ET_gameNameText))
                    {
                        ET_createArena.setError("Field Cannot Be Empty");
                    }
                    else
                    {
                        InsertArenaCall();
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
            BTN_manageArenaRemove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    DeleteDesiredGameCall(selectedArenaID);
                }
            });
            LV_manageArenasList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    TextView TV_ArenaName = (TextView) view.findViewById(R.id.TV_ArenaName);
                    TextView TV_ArenaID = (TextView) view.findViewById(R.id.TV_ArenaID);
                    previousSelectedArenaID = selectedArenaID;
                    //String arenaName = TV_ArenaName.getText().toString();
                    selectedArenaID = TV_ArenaID.getText().toString();

                    Toast.makeText(getContext(), "Selected ArenaID -" + selectedArenaID + "|| Previous Arena ID" + previousSelectedArenaID, Toast.LENGTH_SHORT).show();
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
        GetArenaListCall();
    }

    private void GetArenaListCall() {
        GetArenaListBackground getArenaListBackground = new GetArenaListBackground(getContext());
        getArenaListBackground.execute();
    }
    private void InsertArenaCall() {
        InsertArenaBackground insertArenaBackground = new InsertArenaBackground();
        insertArenaBackground.execute();
    }
    public void DeleteDesiredGameCall(String arenaID) {
        DeleteDesiredArenaBackground deleteDesiredArenaBackground = new DeleteDesiredArenaBackground(arenaID);
        deleteDesiredArenaBackground.execute();
    }

    private class InsertArenaBackground extends AsyncTask<Void,Void,Void> {
        private String ET_createArenaValue = ET_createArena.getText().toString().trim();
        private PreparedStatement preparedStatement = null;
        private Connection connection = null;
        @Override
        protected Void doInBackground(Void... params)
        {
            Log.d("", "Game Name Entered: " + ET_createArenaValue);
            try
            {
                connection = DBHandler.getConnection();
                String insertNewArena = "INSERT INTO arena (ArenaName) VALUES(?)";

                preparedStatement = connection.prepareStatement(insertNewArena);
                preparedStatement.setString(1, ET_createArenaValue);
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
            ET_createArena.setText(null);
            ET_createArenaValue = null;
            GetArenaListCall();
        }
    }
    private class DeleteDesiredArenaBackground extends AsyncTask<Void,Void,Void> {
        private String arenaIDValue;
        private PreparedStatement preparedStatement = null;
        private Connection connection = null;
        public DeleteDesiredArenaBackground(String arenaIDValue)
        {
            this.arenaIDValue = arenaIDValue;
        }
        @Override
        protected Void doInBackground(Void... voids)
        {
            try
            {
                connection = DBHandler.getConnection();
                String deleteGameQuery = "DELETE FROM arenadatabase.arena WHERE ArenaID=" + arenaIDValue;

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
            GetArenaListCall();
            selectedArenaID = null;
            previousSelectedArenaID=null;
        }
    }
    private class GetArenaListBackground extends AsyncTask<Void,Void,Void> {
        private Context context;
        private PreparedStatement preparedStatement = null;
        private Connection connection = null;

        public GetArenaListBackground(Context context)
        {
            this.context = context;
        }
        @Override
        protected Void doInBackground(Void... voids)
        {
            int i = 0;
            arenaListDataList = new ArrayList<>();
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
                    arenaListDataList.add(new ArenaListData(arenaID, arenaName));
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
                manageArenasListAdapter = new ManageArenasListAdapter(getActivity(), arenaListDataList);
                LV_manageArenasList.setAdapter(manageArenasListAdapter);
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
        }
    }
}
