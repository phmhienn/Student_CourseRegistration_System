/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

/**
 *
 * @author Dvtt
 */
import model.ConnectDB;
import view.view_HocPhi;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.*;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.File;
import java.io.FileOutputStream;


public class controller_HocPhi {

    private final view_HocPhi v;
    private static final int DON_GIA_TIN_CHI = 520000;

    public controller_HocPhi(view_HocPhi v) {
        this.v = v;
        loadComboSinhVien();
        loadComboHocKy();
        loadTableHocPhi();
        addEvents();
    }

    private void loadComboSinhVien() {
        try (Connection c = ConnectDB.getConnection();
             Statement st = c.createStatement();
             ResultSet rs = st.executeQuery("SELECT ma_sv FROM SinhVien")) {

            v.cbSinhVien.removeAllItems();
            while (rs.next()) {
                v.cbSinhVien.addItem(rs.getString("ma_sv"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadComboHocKy() {
        try (Connection c = ConnectDB.getConnection();
             Statement st = c.createStatement();
             ResultSet rs = st.executeQuery("SELECT ma_hoc_ky FROM HocKy")) {

            v.cbHocKy.removeAllItems();
            while (rs.next()) {
                v.cbHocKy.addItem(rs.getString("ma_hoc_ky"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void tinhHocPhi() {
    String maSV = (String) v.cbSinhVien.getSelectedItem();
    String maHK = (String) v.cbHocKy.getSelectedItem();

    if (maSV == null || maHK == null) {
        JOptionPane.showMessageDialog(v, "Chọn sinh viên và học kỳ");
        return;
    }

    String sql = """
        SELECT ISNULL(SUM(mh.so_tin_chi), 0) AS tong_tc
        FROM DangKyTinChi dk
        JOIN LopHocPhan lhp ON dk.ma_lhp = lhp.ma_lhp
        JOIN MonHoc mh ON lhp.ma_mon = mh.ma_mon
        WHERE dk.ma_sv = ?
          AND lhp.ma_hoc_ky = ?
    """;

    try (Connection c = ConnectDB.getConnection();
         PreparedStatement ps = c.prepareStatement(sql)) {

        ps.setString(1, maSV);
        ps.setString(2, maHK);

        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            int tongTC = rs.getInt("tong_tc");
            long tongTien = tongTC * 520000L;

            v.txtTongTinChi.setText(String.valueOf(tongTC));
            v.txtTongTien.setText(String.valueOf(tongTien));
            v.txtTrangThai.setText("Chưa đóng");

            System.out.println("=== TINH HOC PHI OK ===");
            System.out.println("SV = " + maSV);
            System.out.println("HK = " + maHK);
            System.out.println("Tin chi = " + tongTC);
            System.out.println("Tien = " + tongTien);
        }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void luuHocPhi() {
        String maSV = (String) v.cbSinhVien.getSelectedItem();
        String maHK = (String) v.cbHocKy.getSelectedItem();

        if (maSV == null || maHK == null) return;

        int tongTC = Integer.parseInt(v.txtTongTinChi.getText());
        long tongTien = Long.parseLong(v.txtTongTien.getText());
        String trangThai = v.txtTrangThai.getText();

        String checkSql = "SELECT COUNT(*) FROM HocPhi WHERE ma_sv=? AND ma_hoc_ky=?";
        String insertSql = """
            INSERT INTO HocPhi(ma_sv, ma_hoc_ky, tong_tin_chi, tong_tien, trang_thai)
            VALUES (?,?,?,?,?)
        """;
        String updateSql = """
            UPDATE HocPhi
            SET tong_tin_chi=?, tong_tien=?, trang_thai=?
            WHERE ma_sv=? AND ma_hoc_ky=?
        """;

        try (Connection c = ConnectDB.getConnection()) {

            PreparedStatement check = c.prepareStatement(checkSql);
            check.setString(1, maSV);
            check.setString(2, maHK);

            ResultSet rs = check.executeQuery();
            rs.next();
            boolean exists = rs.getInt(1) > 0;

            PreparedStatement ps;
            if (!exists) {
                ps = c.prepareStatement(insertSql);
                ps.setString(1, maSV);
                ps.setString(2, maHK);
                ps.setInt(3, tongTC);
                ps.setLong(4, tongTien);
                ps.setString(5, trangThai);
            } else {
                ps = c.prepareStatement(updateSql);
                ps.setInt(1, tongTC);
                ps.setLong(2, tongTien);
                ps.setString(3, trangThai);
                ps.setString(4, maSV);
                ps.setString(5, maHK);
            }

            ps.executeUpdate();
            loadTableHocPhi();
            JOptionPane.showMessageDialog(v, "Lưu học phí thành công");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void danhDauDaDong() {
        int row = v.tblHocPhi.getSelectedRow();
        if (row < 0) return;

        String maSV = v.tblHocPhi.getValueAt(row, 0).toString();
        String maHK = v.tblHocPhi.getValueAt(row, 1).toString();

        try (Connection c = ConnectDB.getConnection();
             PreparedStatement ps = c.prepareStatement(
                     "UPDATE HocPhi SET trang_thai=N'Đã đóng' WHERE ma_sv=? AND ma_hoc_ky=?"
             )) {

            ps.setString(1, maSV);
            ps.setString(2, maHK);
            ps.executeUpdate();
            loadTableHocPhi();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadTableHocPhi() {
        DefaultTableModel model = (DefaultTableModel) v.tblHocPhi.getModel();
        model.setRowCount(0);

        try (Connection c = ConnectDB.getConnection();
             Statement st = c.createStatement();
             ResultSet rs = st.executeQuery("SELECT * FROM HocPhi")) {

            while (rs.next()) {
model.addRow(new Object[]{
                        rs.getString("ma_sv"),
                        rs.getString("ma_hoc_ky"),
                        rs.getInt("tong_tin_chi"),
                        rs.getLong("tong_tien"),
                        rs.getString("trang_thai")
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private void xuatExcel() {
    DefaultTableModel model = (DefaultTableModel) v.tblHocPhi.getModel();

    if (model.getRowCount() == 0) {
        JOptionPane.showMessageDialog(v, "Không có dữ liệu để xuất");
        return;
    }

    JFileChooser chooser = new JFileChooser();
    chooser.setDialogTitle("Lưu file Excel");

    if (chooser.showSaveDialog(v) != JFileChooser.APPROVE_OPTION) return;

    File file = chooser.getSelectedFile();
    if (!file.getName().endsWith(".xlsx")) {
        file = new File(file.getAbsolutePath() + ".xlsx");
    }

    try (Workbook wb = new XSSFWorkbook()) {
        Sheet sheet = wb.createSheet("HocPhi");

        // Header
        Row header = sheet.createRow(0);
        for (int i = 0; i < model.getColumnCount(); i++) {
            header.createCell(i).setCellValue(model.getColumnName(i));
        }

        // Data
        for (int i = 0; i < model.getRowCount(); i++) {
            Row row = sheet.createRow(i + 1);
            for (int j = 0; j < model.getColumnCount(); j++) {
                row.createCell(j).setCellValue(
                        model.getValueAt(i, j).toString()
                );
            }
        }

        for (int i = 0; i < model.getColumnCount(); i++) {
            sheet.autoSizeColumn(i);
        }

        FileOutputStream out = new FileOutputStream(file);
        wb.write(out);
        out.close();

        JOptionPane.showMessageDialog(v, "Xuất Excel thành công");

        } catch (Exception e) {
            JOptionPane.showMessageDialog(v, "Lỗi xuất Excel");
            e.printStackTrace();
        }
    }

    private void addEvents() {
        v.btnQuayLai.addActionListener(e -> v.dispose());
        v.btnTinhHocPhi.addActionListener(e -> tinhHocPhi());
        v.btnLuu.addActionListener(e -> luuHocPhi());
        v.btnDaDong.addActionListener(e -> danhDauDaDong());
        v.btnXuatExcel.addActionListener(e -> xuatExcel());

        v.tblHocPhi.getSelectionModel().addListSelectionListener(e -> {
            int r = v.tblHocPhi.getSelectedRow();
            if (r >= 0) {
                v.cbSinhVien.setSelectedItem(v.tblHocPhi.getValueAt(r, 0));
                v.cbHocKy.setSelectedItem(v.tblHocPhi.getValueAt(r, 1));
                v.txtTongTinChi.setText(v.tblHocPhi.getValueAt(r, 2).toString());
                v.txtTongTien.setText(v.tblHocPhi.getValueAt(r, 3).toString());
                v.txtTrangThai.setText(v.tblHocPhi.getValueAt(r, 4).toString());
            }
        });
    }
}
