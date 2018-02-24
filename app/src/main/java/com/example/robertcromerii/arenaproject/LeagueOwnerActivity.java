package com.example.robertcromerii.arenaproject;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class LeagueOwnerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_operator);

        final TextView tvWelcomeUserText = (TextView)findViewById(R.id.tvWelcomeUserText);
    }
}
