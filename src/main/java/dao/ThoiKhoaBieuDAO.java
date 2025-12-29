/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

/**
 *
 * @author HUY
 */
import Model.ThoiKhoaBieuSV;
import dao.DBconn;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
public class ThoiKhoaBieuDAO {
    public List<ThoiKhoaBieuSV> getBySinhVienHocKy(String maSv, String maHocKy) {
        String sql = """
            SELECT 
              mh.ten_mon,
              lhp.ma_lhp,
              tkb.thu,
              tkb.tiet_bat_dau,
              tkb.so_tiet,
              tkb.phong_hoc
            FROM DangKyHocPhan dk
            JOIN LopHocPhan lhp ON dk.ma_lhp = lhp.ma_lhp
            JOIN MonHoc mh ON lhp.ma_mon = mh.ma_mon
            JOIN ThoiKhoaBieu tkb ON lhp.ma_lhp = tkb.ma_lhp
            JOIN HocKy hk ON lhp.ma_hoc_ky = hk.ma_hoc_ky
            WHERE dk.ma_sv = ?
              AND dk.trang_thai = 'đăng ký'
              AND hk.ma_hoc_ky = ?
            ORDER BY tkb.thu, tkb.tiet_bat_dau
        """;

        List<ThoiKhoaBieuSV> list = new ArrayList<>();

        try (Connection con = DBconn.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, maSv);
            ps.setString(2, maHocKy);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    ThoiKhoaBieuSV t = new ThoiKhoaBieuSV();
                    t.setTenMon(rs.getString("ten_mon"));
                    t.setMaLhp(rs.getString("ma_lhp"));
                    t.setThu(rs.getInt("thu"));
                    t.setTietBatDau(rs.getInt("tiet_bat_dau"));
                    t.setSoTiet(rs.getInt("so_tiet"));
                    t.setPhongHoc(rs.getString("phong_hoc"));
                    list.add(t);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return list;
    }
}
