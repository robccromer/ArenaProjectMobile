package com.example.robertcromerii.arenaproject.models;

/**
 * Created by Robert Cromer II on 3/6/2018.
 */

public class LeagueListData
{
    private String leagueName;
    private String leagueID;
    public LeagueListData(String leagueID, String leagueName) {
        this.leagueName = leagueName;
        this.leagueID = leagueID;
    }
    public String getLeagueName() {
        return leagueName;
    }
    public void setLeagueName(String leagueName) {
        this.leagueName = leagueName;
    }
    public String getLeagueID() {
        return leagueID;
    }
    public void setLeagueID(String leagueID) {
        this.leagueID = leagueID;
    }


    @Override
    public String toString() {
        return leagueName;
    }
}
