/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

/**
 *
 * @author HUY
 */
import Model.SinhVienInfo;
import dao.DBconn;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
public class SinhVienDAO {
    public SinhVienInfo findInfoByMaSv(String maSv) {
        String sql = """
            SELECT 
              sv.ma_sv, sv.ho_ten, sv.ngay_sinh, sv.gioi_tinh,
              sv.email, sv.so_dien_thoai, sv.dia_chi, sv.khoa_hoc, sv.trang_thai,
              c.ma_ctdt, c.ten_ctdt, c.bac_dao_tao,
              k.ma_khoa, k.ten_khoa
            FROM SinhVien sv
            LEFT JOIN ChuongTrinhDaoTao c ON sv.ma_ctdt = c.ma_ctdt
            LEFT JOIN Khoa k ON c.ma_khoa = k.ma_khoa
            WHERE sv.ma_sv = ?
        """;

        try (Connection con = DBconn.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, maSv);
            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) return null;

                SinhVienInfo x = new SinhVienInfo();
                x.setMaSv(rs.getString("ma_sv"));
                x.setHoTen(rs.getString("ho_ten"));
                x.setNgaySinh(rs.getDate("ngay_sinh"));
                x.setGioiTinh(rs.getString("gioi_tinh"));

                x.setEmail(rs.getString("email"));
                x.setSoDienThoai(rs.getString("so_dien_thoai"));
                x.setDiaChi(rs.getString("dia_chi"));

                Integer khoaHoc = (Integer) rs.getObject("khoa_hoc");
                x.setKhoaHoc(khoaHoc);
                x.setTrangThai(rs.getString("trang_thai"));

                x.setMaCtdt(rs.getString("ma_ctdt"));
                x.setTenCtdt(rs.getString("ten_ctdt"));
                x.setBacDaoTao(rs.getString("bac_dao_tao"));

                x.setMaKhoa(rs.getString("ma_khoa"));
                x.setTenKhoa(rs.getString("ten_khoa"));
                return x;
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public boolean updateContact(String maSv, String email, String sdt, String diaChi) {
        String sql = "UPDATE SinhVien SET email=?, so_dien_thoai=?, dia_chi=? WHERE ma_sv=?";
        try (Connection con = DBconn.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, email);
            ps.setString(2, sdt);
            ps.setString(3, diaChi);
            ps.setString(4, maSv);
            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
