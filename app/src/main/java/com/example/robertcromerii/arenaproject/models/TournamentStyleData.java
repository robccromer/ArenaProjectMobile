package com.example.robertcromerii.arenaproject.models;

/**
 * Created by Robert Cromer II on 3/27/2018.
 */

public class TournamentStyleData
{
    private String styleName;
    private String styleID;

    public TournamentStyleData(String styleID, String styleName) {
        this.styleName = styleName;
        this.styleID = styleID;
    }
    public String getStyleName() {
        return styleName;
    }

    public void setStyleName(String styleName) {
        this.styleName = styleName;
    }

    public String getStyleID() {
        return styleID;
    }

    public void setStyleID(String styleID) {
        this.styleID = styleID;
    }

    @Override
    public String toString() {
        return styleName;
    }

    public String idToString()
    {
        return styleID;
    }

}
