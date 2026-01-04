/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author HUY
 */
import java.sql.*;
import java.util.*;

public class model_ThongKe {

    public static class ThongKeMon {
        public final String tenMon;
        public final int soSv;

        public ThongKeMon(String tenMon, int soSv) {
            this.tenMon = tenMon;
            this.soSv = soSv;
        }
    }

    public static class HocKyItem {
        public final String maHocKy;
        public final String namHoc;

        public HocKyItem(String maHocKy, String namHoc) {
            this.maHocKy = maHocKy;
            this.namHoc = namHoc;
        }
    }

    // load combobox học kỳ (ma_hoc_ky)
    public List<String> getDsMaHocKy() throws SQLException {
        List<String> list = new ArrayList<>();
        String sql = "SELECT ma_hoc_ky FROM HocKy ORDER BY nam_hoc, ma_hoc_ky";
        try (Connection c = ConnectDB.getConnection();
             Statement st = c.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) list.add(rs.getString("ma_hoc_ky"));
        }
        return list;
    }

    // lấy năm học theo học kỳ
    public String getNamHocByHocKy(String maHocKy) throws SQLException {
        String sql = "SELECT nam_hoc FROM HocKy WHERE ma_hoc_ky = ?";
        try (Connection c = ConnectDB.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, maHocKy);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? rs.getString("nam_hoc") : "";
            }
        }
    }

    // tổng lượt đăng ký trong học kỳ (lọc "Đã đăng ký")
    public int getTongLuotDangKy(String maHocKy) throws SQLException {
        String sql = """
            SELECT COUNT(*) AS tong
            FROM DangKyTinChi dk
            JOIN LopHocPhan lhp ON dk.ma_lhp = lhp.ma_lhp
            WHERE lhp.ma_hoc_ky = ?
              AND dk.trang_thai = N'Đã đăng ký'
        """;
        try (Connection c = ConnectDB.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, maHocKy);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? rs.getInt("tong") : 0;
            }
        }
    }

    // thống kê số SV theo môn trong học kỳ (đếm DISTINCT SV)
    public List<ThongKeMon> getThongKeTheoMon(String maHocKy) throws SQLException {
        List<ThongKeMon> list = new ArrayList<>();

        String sql = """
            SELECT mh.ten_mon, COUNT(DISTINCT dk.ma_sv) AS so_sv
            FROM DangKyTinChi dk
            JOIN LopHocPhan lhp ON dk.ma_lhp = lhp.ma_lhp
            JOIN MonHoc mh ON lhp.ma_mon = mh.ma_mon
            WHERE lhp.ma_hoc_ky = ?
              AND dk.trang_thai = N'Đã đăng ký'
            GROUP BY mh.ten_mon
            ORDER BY so_sv DESC
        """;

        try (Connection c = ConnectDB.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, maHocKy);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(new ThongKeMon(
                            rs.getString("ten_mon"),
                            rs.getInt("so_sv")
                    ));
                }
            }
        }
        return list;
    }
}