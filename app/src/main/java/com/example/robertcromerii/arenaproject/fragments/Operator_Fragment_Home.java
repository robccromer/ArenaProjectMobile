package com.example.robertcromerii.arenaproject.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.robertcromerii.arenaproject.R;

/**
 * Created by Robert Cromer II on 2/28/2018.
 */

public class Operator_Fragment_Home extends Fragment
{
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.content_operator, null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
        
       /* view.findViewById(R.id.operator_manageUsersButton).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) 
            {
                Toast.makeText(getActivity(), "You Are inside Manage Users Operator Fragement", Toast.LENGTH_SHORT).show();
            }
        });*/
    }


}
