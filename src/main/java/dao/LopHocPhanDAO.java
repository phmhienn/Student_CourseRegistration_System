/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

/**
 *
 * @author HUY
 */
import Model.LopHocPhan;
import dao.DBconn;

import java.sql.*;
import java.util.*;
public class LopHocPhanDAO {
    public List<Map<String, Object>> findAllJoin() {
        String sql = """
            SELECT l.ma_lhp, l.ma_mon, m.ten_mon,
                   l.ma_gv, g.ho_ten AS ten_gv,
                   l.ma_hoc_ky, h.ten_hoc_ky,
                   l.so_luong_toi_da, l.so_luong_da_dang_ky, l.trang_thai
            FROM LopHocPhan l
            JOIN MonHoc m ON l.ma_mon = m.ma_mon
            JOIN GiangVien g ON l.ma_gv = g.ma_gv
            JOIN HocKy h ON l.ma_hoc_ky = h.ma_hoc_ky
            ORDER BY l.ma_lhp
        """;

        List<Map<String, Object>> list = new ArrayList<>();
        try (Connection c = DBconn.getConnection();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Map<String, Object> row = new HashMap<>();
                row.put("ma_lhp", rs.getString("ma_lhp"));
                row.put("ten_mon", rs.getString("ten_mon"));
                row.put("ten_gv", rs.getString("ten_gv"));
                row.put("ten_hoc_ky", rs.getString("ten_hoc_ky"));
                row.put("so_toi_da", rs.getInt("so_luong_toi_da"));
                row.put("so_da_dk", rs.getInt("so_luong_da_dang_ky"));
                row.put("trang_thai", rs.getString("trang_thai"));
                // lưu thêm mã để set combobox khi click table
                row.put("ma_mon", rs.getString("ma_mon"));
                row.put("ma_gv", rs.getString("ma_gv"));
                row.put("ma_hoc_ky", rs.getString("ma_hoc_ky"));
                list.add(row);
            }
        } catch (Exception e) { throw new RuntimeException(e); }
        return list;
    }

    public boolean insert(LopHocPhan lhp) {
        String sql = """
            INSERT INTO LopHocPhan(ma_lhp, ma_mon, ma_gv, ma_hoc_ky, so_luong_toi_da, trang_thai)
            VALUES(?,?,?,?,?,?)
        """;
        try (Connection c = DBconn.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, lhp.getMaLhp());
            ps.setString(2, lhp.getMaMon());
            ps.setString(3, lhp.getMaGv());
            ps.setString(4, lhp.getMaHocKy());
            ps.setInt(5, lhp.getSoLuongToiDa());
            ps.setString(6, lhp.getTrangThai());
            return ps.executeUpdate() > 0;
        } catch (Exception e) { throw new RuntimeException(e); }
    }

    public boolean update(LopHocPhan lhp) {
        String sql = """
            UPDATE LopHocPhan
            SET ma_mon=?, ma_gv=?, ma_hoc_ky=?, so_luong_toi_da=?, trang_thai=?
            WHERE ma_lhp=?
        """;
        try (Connection c = DBconn.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, lhp.getMaMon());
            ps.setString(2, lhp.getMaGv());
            ps.setString(3, lhp.getMaHocKy());
            ps.setInt(4, lhp.getSoLuongToiDa());
            ps.setString(5, lhp.getTrangThai());
            ps.setString(6, lhp.getMaLhp());
            return ps.executeUpdate() > 0;
        } catch (Exception e) { throw new RuntimeException(e); }
    }

    public boolean delete(String maLhp) {
        // lưu ý: nếu đã có DangKyHocPhan trỏ vào -> sẽ lỗi FK. Bạn cần hủy đăng ký trước hoặc ON DELETE.
        String sql = "DELETE FROM LopHocPhan WHERE ma_lhp=?";
        try (Connection c = DBconn.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, maLhp);
            return ps.executeUpdate() > 0;
        } catch (Exception e) { throw new RuntimeException(e); }
    }
}
