package com.example.robertcromerii.arenaproject.activities;

/**
 * Created by Robert Cromer II on 3/21/2018.
 */

public class UserContext
{
    private String userID;
    public UserContext(String userID) {
        this.userID = userID;
    }
    public String getUserID()
    {
        return userID;
    }
    public void setUserID(String userID)
    {
        this.userID = userID;
    }
}
