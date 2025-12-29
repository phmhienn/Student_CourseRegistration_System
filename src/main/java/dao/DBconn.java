package dao;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
import java.sql.*;
/**
 *
 * @author HUY
 */
public class DBconn {
   
    private static final String url = "jdbc:mysql://localhost:3306/QLTC?useSSL=false&serverTimezone=UTC&characterEncoding=utf8";
    private static final String user = "root";
    private static final String pass = "root123";

    public static Connection getConnection() throws Exception {
        Class.forName("com.mysql.cj.jdbc.Driver");
        return DriverManager.getConnection(url, user, pass);
    }
}
