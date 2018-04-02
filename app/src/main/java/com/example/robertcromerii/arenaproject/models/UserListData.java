package com.example.robertcromerii.arenaproject.models;

/**
 * Created by Robert Cromer II on 3/6/2018.
 */

public class UserListData
{
    private String pendingUserName;
    private String pendingUserID;

    public UserListData(String pendingUserID, String pendingUserName) {
        this.pendingUserName = pendingUserName;
        this.pendingUserID = pendingUserID;
    }

    public String getPendingUserName() {
        return pendingUserName;
    }

    public void setPendingUserName(String pendingUserName) {
        this.pendingUserName = pendingUserName;
    }

    public String getPendingUserID() {
        return pendingUserID;
    }

    public void setPendingUserID(String pendingUserID) {
        this.pendingUserID = pendingUserID;
    }
}
