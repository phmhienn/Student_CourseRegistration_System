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
   public static Connection getConnection() throws Exception {
        String url = "jdbc:mysql://localhost:3306/QLTC?useUnicode=true&characterEncoding=UTF-8";
        String user = "root";
        String pass = "123456";
        Class.forName("com.mysql.cj.jdbc.Driver");
        return DriverManager.getConnection(url, user, pass);
    }
}
