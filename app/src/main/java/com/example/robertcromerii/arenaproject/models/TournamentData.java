package com.example.robertcromerii.arenaproject.models;

/**
 * Created by Robert Cromer II on 3/29/2018.
 */

public class TournamentData
{

    private String tournamentName;
    private String tournamentID;
    private String tournamentDescription;
    private String tournamentStyle;

    public TournamentData(String tournamentName, String tournamentID, String tournamentDescription, String tournamentStyle) {
        this.tournamentName = tournamentName;
        this.tournamentID = tournamentID;
        this.tournamentDescription = tournamentDescription;
        this.tournamentStyle = tournamentStyle;
    }

    public String getTournamentName() {
        return tournamentName;
    }

    public void setTournamentName(String tournamentName) {
        this.tournamentName = tournamentName;
    }

    public String getTournamentID() {
        return tournamentID;
    }

    public void setTournamentID(String tournamentID) {
        this.tournamentID = tournamentID;
    }

    public String getTournamentDescription() {
        return tournamentDescription;
    }

    public void setTournamentDescription(String tournamentDescription) {
        this.tournamentDescription = tournamentDescription;
    }

    public String getTournamentStyle() {
        return tournamentStyle;
    }

    public void setTournamentStyle(String tournamentStyle) {
        this.tournamentStyle = tournamentStyle;
    }

}
