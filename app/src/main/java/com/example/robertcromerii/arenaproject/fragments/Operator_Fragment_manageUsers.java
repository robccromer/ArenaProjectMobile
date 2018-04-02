package com.example.robertcromerii.arenaproject.fragments;


import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.robertcromerii.arenaproject.DBHandler;
import com.example.robertcromerii.arenaproject.R;
import com.example.robertcromerii.arenaproject.adapters.ManageUsersListAdapter;
import com.example.robertcromerii.arenaproject.models.UserListData;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import static com.example.robertcromerii.arenaproject.Utilities.setListViewHeightBasedOnChildren;

/**
 * Created by Robert Cromer II on 2/28/2018.
 */

public class Operator_Fragment_manageUsers extends Fragment
{
    private Button BTN_manageUsersAccept, BTN_manageUsersDeny;

    private ListView LV_manageUsers;
    private List<UserListData> userListDataList;

    private ManageUsersListAdapter manageUsersListAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.fragment_operator_manage_users, null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
        try {
            userListDataList = new ArrayList<>();
            BTN_manageUsersAccept = (Button) view.findViewById(R.id.BTN_manageUsersDeny);
            BTN_manageUsersDeny = (Button) view.findViewById(R.id.BTN_manageUsersDeny);
            LV_manageUsers = (ListView) view.findViewById(R.id.LV_manageUsers);

            GetAllUsersCall();

            LV_manageUsers.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    Toast.makeText(getContext(), "Clicked User : " + view.getTag(), Toast.LENGTH_SHORT).show();
                }
            });
        }
        catch(Exception exception)
        {
            exception.printStackTrace();
        }
    }

    private class GetPendingUserListBackground extends AsyncTask<Void,Void,Void> {
        private Context context;
        private PreparedStatement preparedStatement = null;
        private Connection connection = null;

        public GetPendingUserListBackground(Context context)
        {
            this.context = context;
        }
        @Override
        protected Void doInBackground(Void... voids)
        {
            try
            {
                connection = DBHandler.getConnection();
                String getGameQuery = "SELECT * FROM arenadatabase.pending;";

                preparedStatement = connection.prepareStatement(getGameQuery);
                preparedStatement.executeQuery();

                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next())
                {

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

    private void GetAllUsersCall() {
        GetAllUsers getAllUsers = new GetAllUsers(getContext());
        getAllUsers.execute();
    }

    private class GetAllUsers extends AsyncTask<Void,Void,Void> {
        private Context context;
        private PreparedStatement preparedStatement = null;
        private Connection connection = null;

        public GetAllUsers(Context context)
        {
            this.context = context;
        }
        @Override
        protected Void doInBackground(Void... voids)
        {
            try
            {
                connection = DBHandler.getConnection();
                String getGameQuery = "SELECT * FROM users";

                preparedStatement = connection.prepareStatement(getGameQuery);
                preparedStatement.executeQuery();

                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next())
                {
                    String userID = resultSet.getString("userID");
                    String userName = resultSet.getString("userName");
                    userListDataList.add(new UserListData(userID, userName));
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
                manageUsersListAdapter = new ManageUsersListAdapter(getActivity(), userListDataList);
                LV_manageUsers.setAdapter(manageUsersListAdapter);
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
        }
    }

}
