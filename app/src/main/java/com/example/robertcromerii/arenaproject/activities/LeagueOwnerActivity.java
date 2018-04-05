package com.example.robertcromerii.arenaproject.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.example.robertcromerii.arenaproject.R;
import com.example.robertcromerii.arenaproject.fragments.LeagueOwner_Fragment_Home;
import com.example.robertcromerii.arenaproject.fragments.LeagueOwner_Fragment_ManageLeagues;
import com.example.robertcromerii.arenaproject.fragments.LeagueOwner_Fragment_ManageMatches;
import com.example.robertcromerii.arenaproject.fragments.LeagueOwner_Fragment_ManageTournaments;

public class LeagueOwnerActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_league_owner);
        Toolbar toolbar = (Toolbar) findViewById(R.id.leagueOwner_toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.leagueOwner_drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.leagueOwner_nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.leagueOwner_drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_league_owner_actions, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.leageOwner_action_settings) {
            Intent logoutIntent = new Intent(LeagueOwnerActivity.this, LoginActivity.class);
            LeagueOwnerActivity.this.startActivity(logoutIntent);
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        Fragment LeagueOwnerNavigationDrawFramgement = null;
        int id = item.getItemId();

        if(id == R.id.leagueOwner_nav_Home)
        {
            LeagueOwnerNavigationDrawFramgement = new LeagueOwner_Fragment_Home();
        }
        else if (id == R.id.leagueOwner_nav_ManageLeagues)
        {
            LeagueOwnerNavigationDrawFramgement = new LeagueOwner_Fragment_ManageLeagues();
        }
        else if (id == R.id.leagueOwner_nav_ManageTournament)
        {
            LeagueOwnerNavigationDrawFramgement = new LeagueOwner_Fragment_ManageTournaments();
        }
        else if (id == R.id.leagueOwner_nav_ManageMatches)
        {
            LeagueOwnerNavigationDrawFramgement = new LeagueOwner_Fragment_ManageMatches();
        }
        if(LeagueOwnerNavigationDrawFramgement !=null)
        {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.leagueOwnerMainArea, LeagueOwnerNavigationDrawFramgement);
            fragmentTransaction.commit();
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.leagueOwner_drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
