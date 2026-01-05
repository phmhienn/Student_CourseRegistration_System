/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import java.sql.Connection;
import java.sql.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import view.view_QLdangky;
import model.ConnectDB;

/**
 *
 * @author thedu
 */
public class controller_QLdangky {

    view_QLdangky view;
    private boolean isAutoFill = false;

    public controller_QLdangky(view_QLdangky v) {
        this.view = v;

        loadLop();
        loadHocKy();
        loadBang();

        view.cbHocKy.addActionListener(e -> {
            loadPhongHoc();
            loadBang();
        });
        view.cbPhongHoc.addActionListener(e -> loadLichHocTheoPhong());

        view.btnDangKy.addActionListener(e -> dangKy());
        view.btnSua.addActionListener(e -> sua());
        view.btnHuy.addActionListener(e -> huyDangKy());
        view.btnTimKiem.addActionListener(e -> timKiem());
        view.btnQuaylai.addActionListener(e -> view.dispose());

        view.tblDangKy.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                dayDuLieuLenForm();
            }
        });

        view.txtMaMon.getDocument().addDocumentListener(
                new javax.swing.event.DocumentListener() {
                    public void insertUpdate(javax.swing.event.DocumentEvent e) { loadTheoMa(); }
                    public void removeUpdate(javax.swing.event.DocumentEvent e) { loadTheoMa(); }
                    public void changedUpdate(javax.swing.event.DocumentEvent e) { loadTheoMa(); }
                }
        );
    }

    private void loadTheoMa() {
        if (isAutoFill) return;

        String maMon = view.txtMaMon.getText().trim();
        if (maMon.isEmpty()) {
            isAutoFill = true;
            view.txtTenMon.setText("");
            view.txtSoTinChi.setText("");
            isAutoFill = false;
            return;
        }

        String sql = "SELECT ten_mon, so_tin_chi FROM MonHoc WHERE ma_mon = ?";

        try (Connection con = ConnectDB.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, maMon);
            ResultSet rs = ps.executeQuery();

            isAutoFill = true;
            if (rs.next()) {
                view.txtTenMon.setText(rs.getString("ten_mon"));
                view.txtSoTinChi.setText(String.valueOf(rs.getInt("so_tin_chi")));
            } else {
                view.txtTenMon.setText("");
                view.txtSoTinChi.setText("");
            }
            isAutoFill = false;

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadLop() {
        String sql = "SELECT DISTINCT lop FROM SinhVien";
        try (Connection con = ConnectDB.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            view.cbLop.removeAllItems();
            while (rs.next()) {
                view.cbLop.addItem(rs.getString("lop"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadHocKy() {
        String sql = "SELECT ma_hoc_ky FROM HocKy WHERE trang_thai = 1";
        try (Connection con = ConnectDB.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            view.cbHocKy.removeAllItems();
            while (rs.next()) {
                view.cbHocKy.addItem(rs.getString("ma_hoc_ky"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadLichHocTheoPhong() {
        view.cbLichHoc.removeAllItems();

        if (view.cbPhongHoc.getSelectedItem() == null ||
                view.cbHocKy.getSelectedItem() == null ||
                view.txtMaMon.getText().isEmpty()) return;

        String phong = view.cbPhongHoc.getSelectedItem().toString();
        String maHK = view.cbHocKy.getSelectedItem().toString();
        String maMon = view.txtMaMon.getText();

        String sql = """
            SELECT DISTINCT thu, ca_hoc
            FROM LopHocPhan
            WHERE ma_mon = ?
              AND ma_hoc_ky = ?
              AND phong_hoc = ?
              AND (thu, ca_hoc) NOT IN (
                  SELECT lhp.thu, lhp.ca_hoc
                  FROM DangKyTinChi dk
                  JOIN LopHocPhan lhp ON dk.ma_lhp = lhp.ma_lhp
                  WHERE lhp.phong_hoc = ?
                    AND lhp.ma_hoc_ky = ?
              )
        """;

        try (Connection con = ConnectDB.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, maMon);
            ps.setString(2, maHK);
            ps.setString(3, phong);
            ps.setString(4, phong);
            ps.setString(5, maHK);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                view.cbLichHoc.addItem(
                        rs.getString("thu") + " - " + rs.getString("ca_hoc")
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadPhongHoc() {
        view.cbPhongHoc.removeAllItems();

        if (view.txtMaMon.getText().isEmpty() ||
                view.cbHocKy.getSelectedItem() == null) return;

        String maMon = view.txtMaMon.getText().trim();
        String maHK = view.cbHocKy.getSelectedItem().toString();

        String sql = """
            SELECT DISTINCT phong_hoc
            FROM LopHocPhan
            WHERE ma_mon = ?
            AND ma_hoc_ky = ?
        """;

        try (Connection con = ConnectDB.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, maMon);
            ps.setString(2, maHK);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                view.cbPhongHoc.addItem(rs.getString("phong_hoc"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void dayDuLieuLenForm() {
        int row = view.tblDangKy.getSelectedRow();
        if (row == -1) return;

        String maMon = view.tblDangKy.getValueAt(row, 0).toString();
        String tenMon = view.tblDangKy.getValueAt(row, 1).toString();
        String soTC = view.tblDangKy.getValueAt(row, 2).toString();
        String lop = view.tblDangKy.getValueAt(row, 3).toString();
        String lich = view.tblDangKy.getValueAt(row, 4).toString();
        String hocKy = view.tblDangKy.getValueAt(row, 5).toString();

        view.txtMaMon.setText(maMon);
        view.txtTenMon.setText(tenMon);
        view.txtSoTinChi.setText(soTC);
        view.cbLop.setSelectedItem(lop);
        view.cbHocKy.setSelectedItem(hocKy);
    }

    private boolean daDangKyMon(String lop, String maMon, String maHK) {

        String sql = """
            SELECT COUNT(*)
            FROM DangKyTinChi dk
            JOIN SinhVien sv ON dk.ma_sv = sv.ma_sv
            JOIN LopHocPhan lhp ON dk.ma_lhp = lhp.ma_lhp
            WHERE sv.lop = ?
            AND lhp.ma_mon = ?
            AND lhp.ma_hoc_ky = ?
        """;

        try (Connection con = ConnectDB.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, lop);
            ps.setString(2, maMon);
            ps.setString(3, maHK);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    private String getMaLHP() throws Exception {
        String maMon = view.txtMaMon.getText();
        String maHK = view.cbHocKy.getSelectedItem().toString();
        String phong = view.cbPhongHoc.getSelectedItem().toString();

        String lich = view.cbLichHoc.getSelectedItem().toString();
        String thu = lich.split(" - ")[0];
        String ca = lich.split(" - ")[1];

        String sql = """
            SELECT ma_lhp
            FROM LopHocPhan
            WHERE ma_mon = ?
              AND ma_hoc_ky = ?
              AND thu = ?
              AND ca_hoc = ?
              AND phong_hoc = ?
        """;

        try (Connection con = ConnectDB.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, maMon);
            ps.setString(2, maHK);
            ps.setString(3, thu);
            ps.setString(4, ca);
            ps.setString(5, phong);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) return rs.getString("ma_lhp");
        }

        throw new Exception("Không tìm thấy lớp học phần phù hợp");
    }

    private void dangKy() {
        if (view.cbLop.getSelectedItem() == null ||
                view.cbLichHoc.getSelectedItem() == null ||
                view.cbPhongHoc.getSelectedItem() == null) {

            JOptionPane.showMessageDialog(view, "Chọn đủ thông tin");
            return;
        }

        String lop  = view.cbLop.getSelectedItem().toString();
        String maHK = view.cbHocKy.getSelectedItem().toString();
        String maMon = view.txtMaMon.getText().trim();

        if (daDangKyMon(lop, maMon, maHK)) {
            JOptionPane.showMessageDialog(
                    view,
                    "❌ Lớp " + lop + " đã đăng ký môn " + maMon + " trong học kỳ này",
                    "Trùng môn học",
                    JOptionPane.ERROR_MESSAGE
            );
            return;
        }

        try {
            String maLHP = getMaLHP();

            String sql = """
                INSERT INTO DangKyTinChi (ma_sv, ma_lhp, loai_dang_ky, trang_thai)
                SELECT sv.ma_sv, ?, 'Học mới', 'Đã đăng ký'
                FROM SinhVien sv
                WHERE sv.lop = ?
                AND sv.ma_sv NOT IN (
                    SELECT ma_sv FROM DangKyTinChi WHERE ma_lhp = ?
                )
            """;

            try (Connection con = ConnectDB.getConnection();
                 PreparedStatement ps = con.prepareStatement(sql)) {

                ps.setString(1, maLHP);
                ps.setString(2, lop);
                ps.setString(3, maLHP);

                ps.executeUpdate();
                JOptionPane.showMessageDialog(view, "Đăng ký thành công");
                loadBang();
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(view, e.getMessage());
        }
    }

    private void sua() {
        int row = view.tblDangKy.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(view, "Chọn môn cần sửa");
            return;
        }

        try {
            String maMon = view.tblDangKy.getValueAt(row, 0).toString();
            String lop   = view.cbLop.getSelectedItem().toString();
            String maHK  = view.cbHocKy.getSelectedItem().toString();
            String phong = view.cbPhongHoc.getSelectedItem().toString();

            String lich  = view.cbLichHoc.getSelectedItem().toString();
            String thu = lich.split(" - ")[0];
            String ca  = lich.split(" - ")[1];


            if (biTrungPhongCa(phong, thu, ca, maHK)) {
                JOptionPane.showMessageDialog(
                        view,
                        "❌ Phòng " + phong + " đã bị trùng ca " + thu + " - " + ca,
                        "Lỗi trùng lịch",
                        JOptionPane.ERROR_MESSAGE
                );
                return;
            }

            String maLHP = getMaLHP();

            String sql = """
                UPDATE DangKyTinChi dk
                JOIN SinhVien sv ON dk.ma_sv = sv.ma_sv
                JOIN LopHocPhan lhp ON dk.ma_lhp = lhp.ma_lhp
                SET dk.ma_lhp = ?
                WHERE sv.lop = ?
                AND lhp.ma_mon = ?
                AND lhp.ma_hoc_ky = ?
            """;

            try (Connection con = ConnectDB.getConnection();
                 PreparedStatement ps = con.prepareStatement(sql)) {

                ps.setString(1, maLHP);
                ps.setString(2, lop);
                ps.setString(3, maMon);
                ps.setString(4, maHK);

                ps.executeUpdate();
            }

            JOptionPane.showMessageDialog(view, "✅ Đã đổi lịch học cho toàn bộ lớp");
            loadBang();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(view, e.getMessage());
        }
    }

    private void huyDangKy() {
        int row = view.tblDangKy.getSelectedRow();
        if (row == -1) return;

        String maMon = view.tblDangKy.getValueAt(row, 0).toString();
        String lop = view.cbLop.getSelectedItem().toString();

        String sql = """
            DELETE dk
            FROM DangKyTinChi dk
            JOIN LopHocPhan lhp ON dk.ma_lhp = lhp.ma_lhp
            WHERE lhp.ma_mon = ?
              AND dk.ma_sv IN (
                  SELECT ma_sv FROM SinhVien WHERE lop = ?
              )
        """;

        try (Connection con = ConnectDB.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, maMon);
            ps.setString(2, lop);
            ps.executeUpdate();
            JOptionPane.showMessageDialog(view, "Đã hủy đăng ký");
            loadBang();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void timKiem() {
        DefaultTableModel model = (DefaultTableModel) view.tblDangKy.getModel();
        model.setRowCount(0);

        if (view.cbLop.getSelectedItem() == null ||
                view.cbHocKy.getSelectedItem() == null) {

            JOptionPane.showMessageDialog(view, "Chọn lớp và học kỳ");
            return;
        }

        String lop = view.cbLop.getSelectedItem().toString();
        String maHK = view.cbHocKy.getSelectedItem().toString();
        String maMon = view.txtMaMon.getText().trim();

        if (maMon.isEmpty()) {
            JOptionPane.showMessageDialog(view, "Nhập mã môn cần tìm");
            return;
        }

        String sql = """
            SELECT mh.ma_mon,mh.ten_mon,mh.so_tin_chi,sv.lop,CONCAT(lhp.thu,' ',lhp.ca_hoc) AS lich_hoc,lhp.phong_hoc,lhp.ma_hoc_ky
              FROM DangKyTinChi dk
              JOIN SinhVien sv ON dk.ma_sv = sv.ma_sv
              JOIN LopHocPhan lhp ON dk.ma_lhp = lhp.ma_lhp
              JOIN MonHoc mh ON lhp.ma_mon = mh.ma_mon
              WHERE mh.ma_mon = ?
                AND sv.lop = ?
                AND lhp.ma_hoc_ky = ?
              GROUP BY
                  mh.ma_mon,
                  mh.ten_mon,
                  mh.so_tin_chi,
                  sv.lop,
                  lhp.thu,
                  lhp.ca_hoc,
                  lhp.phong_hoc,
                  lhp.ma_hoc_ky;
        """;

        try (Connection con = ConnectDB.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, maMon);
            ps.setString(2, lop);
            ps.setString(3, maHK);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                model.addRow(new Object[]{
                        rs.getString("ma_mon"),
                        rs.getString("ten_mon"),
                        rs.getInt("so_tin_chi"),
                        rs.getString("lop"),
                        rs.getString("lich_hoc"),
                        rs.getString("phong_hoc"),
                        rs.getString("ma_hoc_ky")
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadBang() {
        DefaultTableModel model = (DefaultTableModel) view.tblDangKy.getModel();
        model.setRowCount(0);

        if (view.cbLop.getSelectedItem() == null ||
                view.cbHocKy.getSelectedItem() == null) return;

        String lop = view.cbLop.getSelectedItem().toString();
        String maHK = view.cbHocKy.getSelectedItem().toString();

        String sql = """
            SELECT mh.ma_mon,mh.ten_mon,mh.so_tin_chi,sv.lop , CONCAT(lhp.thu,' ',lhp.ca_hoc) AS lich_hoc, lhp.phong_hoc, lhp.ma_hoc_ky
              FROM DangKyTinChi dk
              JOIN SinhVien sv ON dk.ma_sv = sv.ma_sv
              JOIN LopHocPhan lhp ON dk.ma_lhp = lhp.ma_lhp
              JOIN MonHoc mh ON lhp.ma_mon = mh.ma_mon
              WHERE sv.lop = ? AND lhp.ma_hoc_ky = ?
              GROUP BY
                  mh.ma_mon,
                  mh.ten_mon,
                  mh.so_tin_chi,
                  sv.lop,
                  lhp.thu,
                  lhp.ca_hoc,
                  lhp.phong_hoc,
                  lhp.ma_hoc_ky
            
        """;

        try (Connection con = ConnectDB.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, lop);
            ps.setString(2, maHK);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                model.addRow(new Object[]{
                        rs.getString("ma_mon"),
                        rs.getString("ten_mon"),
                        rs.getInt("so_tin_chi"),
                        rs.getString("lop"),
                        rs.getString("lich_hoc"),
                        rs.getString("phong_hoc"),
                        rs.getString("ma_hoc_ky")
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean biTrungPhongCa(String phong, String thu, String ca, String maHK) {

        String sql = """
        SELECT COUNT(*)
        FROM DangKyTinChi dk
        JOIN LopHocPhan lhp ON dk.ma_lhp = lhp.ma_lhp
        WHERE lhp.phong_hoc = ?
          AND lhp.thu = ?
          AND lhp.ca_hoc = ?
          AND lhp.ma_hoc_ky = ?
    """;

        try (Connection con = ConnectDB.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, phong);
            ps.setString(2, thu);
            ps.setString(3, ca);
            ps.setString(4, maHK);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}