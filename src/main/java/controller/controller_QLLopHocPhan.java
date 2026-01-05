/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

/**
 *
 * @author thedu
 */
import model.ConnectDB;
import view.view_Admin;
import view.view_QLLopHocPhan;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.*;
public class controller_QLLopHocPhan {
    private final view_QLLopHocPhan view;
    private final view_Admin adminView;
    private final DefaultTableModel model;

    public controller_QLLopHocPhan(view_QLLopHocPhan view, view_Admin adminView)  {
        this.view = view;
        this.adminView = adminView;
        this.model = (DefaultTableModel) view.tbl.getModel();
        loadComboGiangVien();
        loadComboMonHoc();
        loadComboHocKy();
        loadTable();
        addEvents();
    }

    

    private void loadComboGiangVien() {
        view.cboGiangVien.removeAllItems();
        view.cboGiangVien.addItem("--Chọn Giảng Viên--");
        String sql = """
        SELECT ma_gv, ten_gv
        FROM GiangVien
        WHERE trang_thai = 1
        """;

        try (Connection con = ConnectDB.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                String item = rs.getString("ma_gv")
                        + " - "
                        + rs.getString("ten_gv");
                view.cboGiangVien.addItem(item);
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(view, "Lỗi load giảng viên");
            e.printStackTrace();
        }
    }

    private void loadComboMonHoc() {
        view.cboMonHoc.removeAllItems();
        view.cboMonHoc.addItem("--Chọn Môn Học--");
        String sql = "SELECT ma_mon, ten_mon FROM MonHoc";

        try (Connection con = ConnectDB.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                view.cboMonHoc.addItem(
                        rs.getString("ma_mon") + " - " + rs.getString("ten_mon")
                );
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(view, "Lỗi load môn học");
        }
    }

    private void loadComboHocKy() {
        view.cboHocKy.removeAllItems();
        view.cboHocKy.addItem("--Chọn Học Kỳ--");
        String sql = """
        SELECT ma_hoc_ky, ten_hoc_ky, nam_hoc
        FROM HocKy
        WHERE trang_thai = 1
        """;

        try (Connection con = ConnectDB.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                String item =
                        rs.getString("ma_hoc_ky") + " - " +
                                rs.getString("ten_hoc_ky") + " (" +
                                rs.getString("nam_hoc") + ")";
                view.cboHocKy.addItem(item);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(view, "Lỗi load học kỳ");
            e.printStackTrace();
        }
    }



    private void loadTable()  {
        model.setRowCount(0);

        String sql = """
                SELECT lhp.ma_lhp, mh.ma_mon, mh.ten_mon, lhp.ma_hoc_ky,
                       lhp.giang_vien, lhp.so_luong_toi_da,
                       lhp.thu, lhp.ca_hoc, lhp.phong_hoc
                FROM LopHocPhan lhp
                JOIN MonHoc mh ON lhp.ma_mon = mh.ma_mon
                """;

        try (Connection con = ConnectDB.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                model.addRow(new Object[]{
                        rs.getString("ma_lhp"),
                        rs.getString("ma_mon"),
                        rs.getString("ten_mon"),
                        rs.getString("ma_hoc_ky"),
                        rs.getString("giang_vien"),
                        rs.getInt("so_luong_toi_da"),
                        rs.getString("thu"),
                        rs.getString("ca_hoc"),
                        rs.getString("phong_hoc")
                });
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(view, "Lỗi load lớp học phần");
        }
    }

    private void addEvents() {

        view.tbl.getSelectionModel().addListSelectionListener(e -> {
            int row = view.tbl.getSelectedRow();
            if (row >= 0) {
                view.txtMaLHP.setText(model.getValueAt(row, 0).toString());

                String maMon = model.getValueAt(row, 1).toString();
                String tenMon = model.getValueAt(row, 2).toString();
                view.cboMonHoc.setSelectedItem(maMon + " - " + tenMon);

                view.cboHocKy.setSelectedItem(model.getValueAt(row, 3).toString());
                view.cboGiangVien.setSelectedItem(model.getValueAt(row, 4).toString());
                view.txtSoLuong.setText(model.getValueAt(row, 5).toString());
                view.cboThu.setSelectedItem(model.getValueAt(row, 6).toString());
                view.cboCaHoc.setSelectedItem(model.getValueAt(row, 7).toString());
                view.txtPhong.setText(model.getValueAt(row, 8).toString());

                view.txtMaLHP.setEditable(false);
            }
        });

        view.btnThem.addActionListener(e -> them());
        view.btnSua.addActionListener(e -> sua());
        view.btnXoa.addActionListener(e -> xoa());
        view.btnLamMoi.addActionListener(e -> lamMoi());
        view.btnTim.addActionListener(e -> tim());

        view.btnQuayLai.addActionListener(e -> view.dispose());

        view.btnNhapExcel.addActionListener(e ->
                JOptionPane.showMessageDialog(view, "Chưa implement Import Excel"));

        view.btnXuatExcel.addActionListener(e ->
                JOptionPane.showMessageDialog(view, "Chưa implement Export Excel"));
    }


    private void them() {
        String sql = """
                INSERT INTO LopHocPhan
                (ma_lhp, ma_mon, ma_hoc_ky, giang_vien,
                 so_luong_toi_da, thu, ca_hoc, phong_hoc)
                VALUES (?,?,?,?,?,?,?,?)
                """;

        try (Connection con = ConnectDB.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, view.txtMaLHP.getText());
            ps.setString(2, getMaMon());
            ps.setString(3, getMaHocKy());
            ps.setString(4, getMaGiangVien());
            ps.setInt(5, Integer.parseInt(view.txtSoLuong.getText()));
            ps.setString(6, view.cboThu.getSelectedItem().toString());
            ps.setString(7, view.cboCaHoc.getSelectedItem().toString());
            ps.setString(8, view.txtPhong.getText());

            ps.executeUpdate();
            JOptionPane.showMessageDialog(view, "Thêm thành công");
            loadTable();
            lamMoi();

        } catch (Exception  e) {
            JOptionPane.showMessageDialog(view, "Trùng mã LHP hoặc dữ liệu sai");
        }
    }

    private void sua()  {
        String sql = """
                UPDATE LopHocPhan SET
                ma_mon=?, ma_hoc_ky=?, giang_vien=?, so_luong_toi_da=?,
                thu=?, ca_hoc=?, phong_hoc=?
                WHERE ma_lhp=?
                """;

        try (Connection con = ConnectDB.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, getMaMon());
            ps.setString(2, getMaHocKy());
            ps.setString(3, getMaGiangVien());
            ps.setInt(4, Integer.parseInt(view.txtSoLuong.getText()));
            ps.setString(5, view.cboThu.getSelectedItem().toString());
            ps.setString(6, view.cboCaHoc.getSelectedItem().toString());
            ps.setString(7, view.txtPhong.getText());
            ps.setString(8, view.txtMaLHP.getText());

            ps.executeUpdate();
            JOptionPane.showMessageDialog(view, "Cập nhật thành công");
            loadTable();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(view, "Lỗi cập nhật");
        }
    }

    private void xoa() {
        int c = JOptionPane.showConfirmDialog(
                view, "Xoá lớp học phần này?", "Xác nhận", JOptionPane.YES_NO_OPTION);

        if (c != JOptionPane.YES_OPTION) return;

        String sql = "DELETE FROM LopHocPhan WHERE ma_lhp=?";

        try (Connection con = ConnectDB.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, view.txtMaLHP.getText());
            ps.executeUpdate();
            JOptionPane.showMessageDialog(view, "Đã xoá");
            loadTable();
            lamMoi();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(view, "Không thể xoá (đã có đăng ký)");
        }
    }

    private void tim()  {
        model.setRowCount(0);
        String key = "%" + view.txtTim.getText() + "%";

        String sql = """
                SELECT lhp.ma_lhp, mh.ma_mon, mh.ten_mon, lhp.ma_hoc_ky,
                       lhp.giang_vien, lhp.so_luong_toi_da,
                       lhp.thu, lhp.ca_hoc, lhp.phong_hoc
                FROM LopHocPhan lhp
                JOIN MonHoc mh ON lhp.ma_mon = mh.ma_mon
                WHERE lhp.ma_lhp LIKE ? OR mh.ten_mon LIKE ? OR lhp.giang_vien LIKE ? 
                """;

        try (Connection con = ConnectDB.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, key);
            ps.setString(2, key);
            ps.setString(3, key);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                model.addRow(new Object[]{
                        rs.getString("ma_lhp"),
                        rs.getString("ma_mon"),
                        rs.getString("ten_mon"),
                        rs.getString("ma_hoc_ky"),
                        rs.getString("giang_vien"),
                        rs.getInt("so_luong_toi_da"),
                        rs.getString("thu"),
                        rs.getString("ca_hoc"),
                        rs.getString("phong_hoc")
                });
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(view, "Lỗi tìm kiếm");
        }
    }

    private String getMaMon() {
        String item = view.cboMonHoc.getSelectedItem().toString();
        return item.split(" - ")[0]; // lấy mã môn
    }
    private String getMaHocKy() {
        String item = view.cboHocKy.getSelectedItem().toString();
        return item.split(" - ")[0];
    }
    private String getMaGiangVien() {
        String item = view.cboGiangVien.getSelectedItem().toString();
        return item.split(" - ")[0];
    }


    private void lamMoi() {
        view.txtMaLHP.setText("");
        view.txtSoLuong.setText("");
        view.txtPhong.setText("");
        view.txtMaLHP.setEditable(true);
        view.tbl.clearSelection();
    }
}
