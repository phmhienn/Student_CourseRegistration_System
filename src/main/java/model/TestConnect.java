/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author HUY
 */
import java.sql.Connection;
import model.ConnectDB;
public class TestConnect {
    public static void main(String[] args) {
        try {
            Connection conn = ConnectDB.getConnection();
            if (conn != null) {
                System.out.println(" KẾT NỐI SQL SERVER THÀNH CÔNG!");
                conn.close();
            }
        } catch (Exception e) {
            System.out.println(" KẾT NỐI THẤT BẠI!");
            e.printStackTrace();
        }
    }
}
