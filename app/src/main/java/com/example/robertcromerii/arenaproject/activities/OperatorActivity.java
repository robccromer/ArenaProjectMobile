package com.example.robertcromerii.arenaproject.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.example.robertcromerii.arenaproject.R;
import com.example.robertcromerii.arenaproject.fragments.Operator_Fragment_Home;
import com.example.robertcromerii.arenaproject.fragments.Operator_Fragment_manageArenas;
import com.example.robertcromerii.arenaproject.fragments.Operator_Fragment_manageGames;
import com.example.robertcromerii.arenaproject.fragments.Operator_Fragment_manageUsers;

public class OperatorActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_operator);
        Toolbar operatorToolbar = (Toolbar) findViewById(R.id.operator_toolbar);
        setSupportActionBar(operatorToolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.operator_drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, operatorToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.operator_nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.operator_drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START))
        {
            drawer.closeDrawer(GravityCompat.START);
        }
        else
        {
            super.onBackPressed();
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_operator_actions, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.operator_action_settings) {
            Intent logoutIntent = new Intent(OperatorActivity.this, LoginActivity.class);
            OperatorActivity.this.startActivity(logoutIntent);
        }

        return super.onOptionsItemSelected(item);
    }
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        Fragment operatorNavigationDrawFramgement = null;
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if (id == R.id.nav_operator_Home)
        {
            operatorNavigationDrawFramgement = new Operator_Fragment_Home();
        }
        else if (id == R.id.nav_operator_ManageUsers)
        {
            operatorNavigationDrawFramgement = new Operator_Fragment_manageUsers();
        }
        else if (id == R.id.nav_operator_manageArenas)
        {
            operatorNavigationDrawFramgement = new Operator_Fragment_manageArenas();
        }
        else if (id == R.id.nav_operator_manageGames)
        {
            operatorNavigationDrawFramgement = new Operator_Fragment_manageGames();
        }
        if(operatorNavigationDrawFramgement !=null)
        {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.operatorMainArea, operatorNavigationDrawFramgement);
            fragmentTransaction.commit();
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.operator_drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
