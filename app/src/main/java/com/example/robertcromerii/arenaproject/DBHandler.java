package com.example.robertcromerii.arenaproject;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by Robert Cromer II on 2/19/2018.
 */

public class DBHandler
{
    private static final String CONNECTION_STRING = "jdbc:mysql://10.0.2.2:3306/arenadatabase";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "#altf4ctrlf6f12!";

    // connection  method that connects us to the MySQL database
    public static Connection getConnection() throws SQLException
    {
        return DriverManager.getConnection(CONNECTION_STRING, USERNAME, PASSWORD);
    }
    // method that displays our errors in more detail if connection fails
    public static void displayException(SQLException ex)
    {
        System.err.println("Error Message: " + ex.getMessage());
        System.err.println("Error Code: " + ex.getErrorCode());
        System.err.println("SQL Status: " + ex.getSQLState());
    }
}
