/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author Dvtt
 */
public class ConnectDB {
    private static String url =
            "jdbc:sqlserver://PHAMHIEN;" +
                    "databaseName=QLTC;" +
                    "encrypt=false;" +
                    "trustServerCertificate=true;";
    private static String user = "sa";
    private static String pass = "123456";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, user, pass);
    }
}