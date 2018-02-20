package com.example.robertcromerii.arenaproject;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class registerActivity extends AppCompatActivity
{
    EditText register_UserPassword, register_Username;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        register_UserPassword = (EditText)findViewById(R.id.register_UserPassword);
        register_Username = (EditText)findViewById(R.id.register_Username);

        Button register_registerButton = (Button)findViewById(R.id.register_registerButton);

        TextView register_LoginHereLink = (TextView)findViewById(R.id.register_LoginHereText);

        register_LoginHereLink.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent loginIntent = new Intent(registerActivity.this, loginActivity.class);
                registerActivity.this.startActivity(loginIntent);
            }
        });

        register_registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                new Background().execute();
            }
        });
    }
    private class Background extends AsyncTask<Void,Void,Void>
    {
        private String username = register_Username.getText().toString();
        private String userPassword = register_UserPassword.getText().toString();
        @Override
        protected Void doInBackground(Void... params)
        {
            try
            {
                Connection connection = DBHandler.getConnection();
                String registerUserRequest = "Insert INTO users (username, userPassword) VALUES (?,?)";

                PreparedStatement preparedStatement = connection.prepareStatement(registerUserRequest);
                preparedStatement.setString(1, username);
                preparedStatement.setString(2, userPassword);
                preparedStatement.execute();
            }
            catch (SQLException e)
            {
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(Void result)
        {
            register_Username.setText("");
            register_UserPassword.setText("");
            super.onPostExecute(result);
        }
    }

}
