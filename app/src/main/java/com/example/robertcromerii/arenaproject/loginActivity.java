package com.example.robertcromerii.arenaproject;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.app.LoaderManager.LoaderCallbacks;

import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;

import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.transform.Result;

import static android.Manifest.permission.READ_CONTACTS;

/**
 * A login screen that offers login via email/password.
 */
public class loginActivity extends AppCompatActivity
{
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
                Intent registerIntent = new Intent(loginActivity.this, registerActivity.class);
                loginActivity.this.startActivity(registerIntent);
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

    private class Background extends AsyncTask<Void,Void,Void>
    {
        private String username = login_Username.getText().toString();
        private String userPassword = login_UserPassword.getText().toString();
        @Override
        protected Void doInBackground(Void... params)
        {
            try
            {
                Connection connection = DBHandler.getConnection();
                String loginUserRequest = "SELECT * FROM users WHERE userName = "+username+" and userPassword =" + userPassword;

                PreparedStatement preparedStatement = connection.prepareStatement(loginUserRequest);
                preparedStatement.setString(1, username);
                preparedStatement.setString(2, userPassword);
                ResultSet resultSet = connection.createStatement().executeQuery(loginUserRequest);

                while (resultSet.next())
                {
                    String userID = resultSet.getString("userID");
                    String userRoleID = resultSet.getString("userRoleID");

                    Log.d("User Info:", "Username -" + username + " User ID - "+ userID + " User Role ID " + userRoleID);
                }
            }
            catch (SQLException e)
            {
                e.printStackTrace();
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

