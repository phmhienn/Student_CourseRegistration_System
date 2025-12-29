/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import model.ConnectDB;
import model.Session;
import model.model_Admin;
import model.model_Taikhoan;
import model.model_Vaitro;
import view.view_Admin;
import view.view_Dangnhap;
import view.view_Quanlymonhoc;

/**
 *
 * @author Dvtt
 */
public class controller_Admin {
    private final view_Admin v;

    public controller_Admin(view_Admin v) {
        this.v = v;

        taiDanhSachTaiKhoan();

        v.tbl.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) doRowClick();
            }
        });

        v.btnThem.addActionListener(e -> them());
        v.btnSua.addActionListener(e -> sua());
        v.btnXoa.addActionListener(e -> xoa());
        v.btnLamMoi.addActionListener(e -> lamMoi());

        v.btnQuanLyMonHoc.addActionListener(e -> moQuanlymonhoc());
        v.btnDangXuat.addActionListener(e -> dangXuat());
    }

    private void taiDanhSachTaiKhoan() {
        v.dtm.setRowCount(0);
        String sql = "SELECT ten_dang_nhap, mat_khau, ma_vai_tro, ma_lien_ket FROM TaiKhoan ORDER BY ten_dang_nhap";

        try (Connection c = ConnectDB.getConnection();
             Statement st = c.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                v.dtm.addRow(new Object[]{
                        rs.getString("ten_dang_nhap"),
                        rs.getString("mat_khau"),
                        rs.getString("ma_vai_tro"),
                        rs.getString("ma_lien_ket")
                });
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(v, "Lỗi tải danh sách: " + ex.getMessage());
        }
    }

    private void doRowClick() {
        int row = v.tbl.getSelectedRow();
        if (row < 0) return;

        v.txtTenDangNhap.setText(v.dtm.getValueAt(row, 0).toString());
        v.txtMatKhau.setText(v.dtm.getValueAt(row, 1).toString());
        v.cboVaiTro.setSelectedItem(v.dtm.getValueAt(row, 2).toString());

        Object mlk = v.dtm.getValueAt(row, 3);
        v.txtMaLienKet.setText(mlk == null ? "" : mlk.toString());
    }

    private void them() {
        String u = v.txtTenDangNhap.getText().trim();
        String p = v.txtMatKhau.getText().trim();
        String r = v.cboVaiTro.getSelectedItem().toString();
        String lk = v.txtMaLienKet.getText().trim();

        if (u.isEmpty() || p.isEmpty()) {
            JOptionPane.showMessageDialog(v, "Nhập tên đăng nhập và mật khẩu!");
            return;
        }

        if (!r.equals("AD") && lk.isEmpty()) {
            JOptionPane.showMessageDialog(v, "Vai trò SV/GV phải có mã liên kết!");
            return;
        }

        String sql = "INSERT INTO TaiKhoan(ten_dang_nhap, mat_khau, ma_vai_tro, ma_lien_ket) VALUES(" +
                "'" + u + "','" + p + "','" + r + "'," + (lk.isEmpty() ? "NULL" : "'" + lk + "'") + ")";

        try (Connection c = ConnectDB.getConnection();
             Statement st = c.createStatement()) {

            st.executeUpdate(sql);
            taiDanhSachTaiKhoan();
            JOptionPane.showMessageDialog(v, "Thêm thành công!");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(v, "Lỗi thêm: " + ex.getMessage());
        }
    }

    private void sua() {
        String u = v.txtTenDangNhap.getText().trim();
        String p = v.txtMatKhau.getText().trim();
        String r = v.cboVaiTro.getSelectedItem().toString();
        String lk = v.txtMaLienKet.getText().trim();

        if (u.isEmpty()) {
            JOptionPane.showMessageDialog(v, "Chọn tài khoản cần sửa!");
            return;
        }

        if (p.isEmpty()) {
            JOptionPane.showMessageDialog(v, "Mật khẩu không được rỗng!");
            return;
        }

        if (!r.equals("AD") && lk.isEmpty()) {
            JOptionPane.showMessageDialog(v, "Vai trò SV/GV phải có mã liên kết!");
            return;
        }

        String sql = "UPDATE TaiKhoan SET " +
                "mat_khau='" + p + "', " +
                "ma_vai_tro='" + r + "', " +
                "ma_lien_ket=" + (lk.isEmpty() ? "NULL" : "'" + lk + "'") +
                " WHERE ten_dang_nhap='" + u + "'";

        try (Connection c = ConnectDB.getConnection();
             Statement st = c.createStatement()) {

            st.executeUpdate(sql);
            taiDanhSachTaiKhoan();
            JOptionPane.showMessageDialog(v, "Sảa thành công!");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(v, "Lỗi sửa: " + ex.getMessage());
        }
    }

    private void xoa() {
        String u = v.txtTenDangNhap.getText().trim();
        if (u.isEmpty()) {
            JOptionPane.showMessageDialog(v, "Chọn tài khoản cần xoá!");
            return;
        }

        int opt = JOptionPane.showConfirmDialog(v, "Xoá tài khoản " + u + " ?", "Xác nhận", JOptionPane.YES_NO_OPTION);
        if (opt != JOptionPane.YES_OPTION) return;

        String sql = "DELETE FROM TaiKhoan WHERE ten_dang_nhap='" + u + "'";

        try (Connection c = ConnectDB.getConnection();
             Statement st = c.createStatement()) {

            st.executeUpdate(sql);
            taiDanhSachTaiKhoan();
            lamMoi();
            JOptionPane.showMessageDialog(v, "Xoá thành công!");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(v, "Lỗi xoá: " + ex.getMessage());
        }
    }

    private void lamMoi() {
        v.txtTenDangNhap.setText("");
        v.txtMatKhau.setText("");
        v.cboVaiTro.setSelectedIndex(0);
        v.txtMaLienKet.setText("");
        v.tbl.clearSelection();
    }

    private void moQuanlymonhoc() {
        view_Quanlymonhoc fm = new view_Quanlymonhoc();
        new controller_Quanlymonhoc(fm);
        fm.setVisible(true);
    }

    private void dangXuat() {
        v.dispose();
        view_Dangnhap man = new view_Dangnhap();
        new controller_Dangnhap(man);
        man.setVisible(true);
    }
}
