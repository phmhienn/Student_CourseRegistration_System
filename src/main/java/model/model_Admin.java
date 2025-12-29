/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.util.List;
import java.util.ArrayList;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 *
 * @author Dvtt
 */
public class model_Admin {
    public static class TaiKhoanRow {
        public String tenDangNhap;
        public String maVaiTro;
        public String maLienKet;

        public TaiKhoanRow(String tenDangNhap, String maVaiTro, String maLienKet) {
            this.tenDangNhap = tenDangNhap;
            this.maVaiTro = maVaiTro;
            this.maLienKet = maLienKet;
        }
    }
    
    public List<model_Vaitro> layDanhSachVaiTro() throws Exception {
        String sql = "SELECT ma_vai_tro, ten_vai_tro FROM VaiTro ORDER BY ma_vai_tro";
        List<model_Vaitro> list = new ArrayList<>();

        try (Connection c = ConnectDB.getConnection();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                list.add(new model_Vaitro(
                        rs.getString("ma_vai_tro"),
                        rs.getString("ten_vai_tro")
                ));
            }
        }
        return list;
    }

    // Lay danh sach tai khoan (hien bang luon)
    public List<model_Taikhoan> layDanhSachTaiKhoan() throws Exception {
        String sql = """
            SELECT tk.ten_dang_nhap, tk.ma_vai_tro, vt.ten_vai_tro, tk.ma_lien_ket
            FROM TaiKhoan tk
            LEFT JOIN VaiTro vt ON tk.ma_vai_tro = vt.ma_vai_tro
            ORDER BY tk.ten_dang_nhap
        """;

        List<model_Taikhoan> list = new ArrayList<>();

        try (Connection c = ConnectDB.getConnection();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                list.add(new model_Taikhoan(
                        rs.getString("ten_dang_nhap"),
                        rs.getString("ma_vai_tro"),
                        rs.getString("ten_vai_tro"),
                        rs.getString("ma_lien_ket") // co the null
                ));
            }
        }
        return list;
    }

    public boolean themTaiKhoan(String tenDangNhap, String matKhau, String maVaiTro, String maLienKet) throws Exception {
        String sql = "INSERT INTO TaiKhoan(ten_dang_nhap, mat_khau, ma_vai_tro, ma_lien_ket) VALUES(?,?,?,?)";

        try (Connection c = ConnectDB.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setString(1, tenDangNhap);
            ps.setString(2, matKhau);
            ps.setString(3, maVaiTro);

            if (maLienKet == null || maLienKet.trim().isEmpty()) ps.setNull(4, java.sql.Types.VARCHAR);
            else ps.setString(4, maLienKet.trim());

            return ps.executeUpdate() > 0;
        }
    }

    public boolean suaTaiKhoan(String tenDangNhap, String matKhau, String maVaiTro, String maLienKet) throws Exception {
        // Neu matKhau rong -> khong sua mat khau
        boolean suaMatKhau = (matKhau != null && !matKhau.trim().isEmpty());

        String sql;
        if (suaMatKhau) {
            sql = "UPDATE TaiKhoan SET mat_khau=?, ma_vai_tro=?, ma_lien_ket=? WHERE ten_dang_nhap=?";
        } else {
            sql = "UPDATE TaiKhoan SET ma_vai_tro=?, ma_lien_ket=? WHERE ten_dang_nhap=?";
        }

        try (Connection c = ConnectDB.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            int idx = 1;

            if (suaMatKhau) {
                ps.setString(idx++, matKhau.trim());
            }

            ps.setString(idx++, maVaiTro);

            if (maLienKet == null || maLienKet.trim().isEmpty()) ps.setNull(idx++, java.sql.Types.VARCHAR);
            else ps.setString(idx++, maLienKet.trim());

            ps.setString(idx, tenDangNhap);

            return ps.executeUpdate() > 0;
        }
    }

    public boolean xoaTaiKhoan(String tenDangNhap) throws Exception {
        String sql = "DELETE FROM TaiKhoan WHERE ten_dang_nhap=?";
        try (Connection c = ConnectDB.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, tenDangNhap);
            return ps.executeUpdate() > 0;
        }
    }
    
}
