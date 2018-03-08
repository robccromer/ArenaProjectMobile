package com.example.robertcromerii.arenaproject.adapters.controllers;

/**
 * Created by Robert Cromer II on 3/6/2018.
 */

public class ArenaListData
{
    private String arenaName;
    private int arenaID;
    public ArenaListData(int arenaID, String arenaName) {
        this.arenaName = arenaName;
        this.arenaID = arenaID;
    }
    public String getArenaName() {
        return arenaName;
    }
    public void setArenaName(String arenaName) {
        this.arenaName = arenaName;
    }
    public int getArenaID() {
        return arenaID;
    }
    public void setArenaID(int arenaID) {
        this.arenaID = arenaID;
    }
}
