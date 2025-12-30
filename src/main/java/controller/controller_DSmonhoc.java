/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import model.ConnectDB;
import java.sql.*;
import model.Session;
import view.view_DSmonhoc;

/**
 *
 * @author thedu
 */
public class controller_DSmonhoc {
    view_DSmonhoc view;
    Session session ;
    public controller_DSmonhoc(view_DSmonhoc view,Session session) {
        this.view = view;
        this.session=session;

        loadAllMonHoc();

        view.btnTim.addActionListener(e -> timMonHoc());
    }

    
    private void loadAllMonHoc() {
        try (Connection c = ConnectDB.getConnection()) {

            String sql = """
                SELECT ma_mon, ten_mon, so_tin_chi,
                       so_tiet_ly_thuyet, so_tiet_thuc_hanh, ma_khoa
                FROM MonHoc
            """;

            ResultSet rs = c.createStatement().executeQuery(sql);
            view.model.setRowCount(0);

            while (rs.next()) {
                view.model.addRow(new Object[]{
                    rs.getString("ma_mon"),
                    rs.getString("ten_mon"),
                    rs.getInt("so_tin_chi"),
                    rs.getInt("so_tiet_ly_thuyet"),
                    rs.getInt("so_tiet_thuc_hanh"),
                    rs.getString("ma_khoa")
                });
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    
    private void timMonHoc() {
        String key = view.txtTim.getText().trim();

        if (key.isEmpty()) {
            loadAllMonHoc();
            return;
        }

        try (Connection c = ConnectDB.getConnection()) {

            String sql = """
                SELECT ma_mon, ten_mon, so_tin_chi,
                       so_tiet_ly_thuyet, so_tiet_thuc_hanh, ma_khoa
                FROM MonHoc
                WHERE ma_mon LIKE ?
                   OR ten_mon LIKE ?
                   OR ma_khoa LIKE ?
            """;

            PreparedStatement ps = c.prepareStatement(sql);
            ps.setString(1, "%" + key + "%");
            ps.setString(2, "%" + key + "%");
            ps.setString(3, "%" + key + "%");

            ResultSet rs = ps.executeQuery();
            view.model.setRowCount(0);

            while (rs.next()) {
                view.model.addRow(new Object[]{
                    rs.getString("ma_mon"),
                    rs.getString("ten_mon"),
                    rs.getInt("so_tin_chi"),
                    rs.getInt("so_tiet_ly_thuyet"),
                    rs.getInt("so_tiet_thuc_hanh"),
                    rs.getString("ma_khoa")
                });
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}