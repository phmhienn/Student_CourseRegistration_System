/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package db;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 *
 * @author thedu
 */
public class DBConnection {
    public static Connection getConnection() throws Exception {
        String url = "jdbc:mysql://localhost:3306/qltc?useSSL=false&serverTimezone=UTC";
        String user = "root";      // đổi nếu cần
        String pass = "123456";          // đổi nếu cần
        return DriverManager.getConnection(url, user, pass);
    }
}
