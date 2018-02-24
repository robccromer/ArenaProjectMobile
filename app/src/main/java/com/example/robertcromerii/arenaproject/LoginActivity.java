package com.example.robertcromerii.arenaproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;

import android.os.AsyncTask;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity
{
    private static final String TAG = "LoginActivity";
    EditText login_UserPassword, login_Username;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        login_UserPassword = (EditText)findViewById(R.id.login_UserPassword);
        login_Username = (EditText)findViewById(R.id.login_Username);

        Button login_LoginButton = (Button)findViewById(R.id.login_loginButton);

        TextView login_registerHereLink = (TextView)findViewById(R.id.login_registerHereText);

        try
        {
            DBHandler.getConnection();
        } catch (SQLException e)
        {
            e.printStackTrace();
        }

        login_registerHereLink.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent registerIntent = new Intent(LoginActivity.this, RegisterActivity.class);
                LoginActivity.this.startActivity(registerIntent);
            }
        });

        login_LoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                new Background().execute();
            }
        });
    }
    protected void loginRoleActivty(String userRoleID)
    {
        switch(userRoleID)
        {
            case "0":
                Intent operatorIntent = new Intent(LoginActivity.this, OperatorActivity.class);
                LoginActivity.this.startActivity(operatorIntent);
                break;
            default:
                Intent loginIntent = new Intent(LoginActivity.this, LoginActivity.class);
                LoginActivity.this.startActivity(loginIntent);
        }
    }
    private class Background extends AsyncTask<Void,Void,Void>
    {
        private String username = login_Username.getText().toString();
        private String userPassword = login_UserPassword.getText().toString();
        private PreparedStatement preparedStatement = null;
        private Connection connection = null;
        @Override
        protected Void doInBackground(Void... params)
        {
            Log.d(TAG, "Username: " + username + "||" + "User Password:" + userPassword);
            try
            {
                connection = DBHandler.getConnection();
                String loginUserRequest = "SELECT userRoleID FROM arenadatabase.users WHERE userName = ? and userPassword = ?";

                preparedStatement = connection.prepareStatement(loginUserRequest);
                preparedStatement.setString(1, username);
                preparedStatement.setString(2, userPassword);
                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next())
                {
                    String userRoleID = resultSet.getString("userRoleID");
                    loginRoleActivty(userRoleID);
                }
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
            login_Username.setText("");
            login_UserPassword.setText("");
            super.onPostExecute(result);
        }
    }
}

