package com.example.robertcromerii.arenaproject;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

public class operatorActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_operator);

        final TextView tvWelcomeUserText = (TextView)findViewById(R.id.tvWelcomeUserText);
    }
}
