/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 *
 * @author Dvtt
 */
public class model_Dangnhap {
     public Session dangNhap(String user, String pass) throws Exception {
        String sql = "SELECT ma_vai_tro, ma_lien_ket FROM TaiKhoan WHERE ten_dang_nhap=? AND mat_khau=?";

        try (Connection c = ConnectDB.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setString(1, user);
            ps.setString(2, pass);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    String vt = rs.getString("ma_vai_tro");
                    String mlk = rs.getString("ma_lien_ket"); // co the null
                    return new Session(user, vt, mlk);
                }
            }
        }
        return null;
    }
}
