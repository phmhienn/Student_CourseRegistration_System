/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

/**
 *
 * @author HUY
 */
import Model.ComboItem;
import dao.DBconn;

import java.sql.*;
import java.util.*;
public class DanhMucDAO {
     public List<ComboItem> getMonHoc() {
        String sql = "SELECT ma_mon, ten_mon FROM MonHoc ORDER BY ten_mon";
        return queryCombo(sql, "ma_mon", "ten_mon");
    }

    public List<ComboItem> getGiangVien() {
        String sql = "SELECT ma_gv, ho_ten FROM GiangVien ORDER BY ho_ten";
        return queryCombo(sql, "ma_gv", "ho_ten");
    }

    public List<ComboItem> getHocKy() {
        String sql = "SELECT ma_hoc_ky, ten_hoc_ky FROM HocKy ORDER BY ngay_bat_dau DESC";
        return queryCombo(sql, "ma_hoc_ky", "ten_hoc_ky");
    }

    private List<ComboItem> queryCombo(String sql, String idCol, String nameCol) {
        List<ComboItem> list = new ArrayList<>();
        try (Connection c = DBconn.getConnection();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(new ComboItem(rs.getString(idCol), rs.getString(nameCol)));
            }
        } catch (Exception e) { throw new RuntimeException(e); }
        return list;
    }
}
