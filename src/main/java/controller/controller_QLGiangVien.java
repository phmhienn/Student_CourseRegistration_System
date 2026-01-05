/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

/**
 *
 * @author HUY
 */
import model.ConnectDB;
import view.view_QLGiangVien;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.io.*;
import java.sql.*;

public class controller_QLGiangVien {

    private view_QLGiangVien v;

    public controller_QLGiangVien(view_QLGiangVien v) {
        this.v = v;

        loadTable("");
        addEvents();
    }

    private void loadTable(String key) {
        DefaultTableModel model = (DefaultTableModel) v.tblGiangVien.getModel();
        model.setRowCount(0);

        String sql = """
            SELECT ma_gv, ten_gv, hoc_vi, hoc_ham, email, dien_thoai, ma_khoa, trang_thai
            FROM GiangVien
            WHERE ma_gv LIKE ? OR ten_gv LIKE ? OR email LIKE ? OR dien_thoai LIKE ? OR ma_khoa LIKE ?
            ORDER BY ma_gv
        """;

        try (Connection c = ConnectDB.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            String kw = "%" + key + "%";
            ps.setString(1, kw);
            ps.setString(2, kw);
            ps.setString(3, kw);
            ps.setString(4, kw);
            ps.setString(5, kw);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                boolean tt = rs.getBoolean("trang_thai");
                model.addRow(new Object[]{
                        rs.getString("ma_gv"),
                        rs.getString("ten_gv"),
                        rs.getString("hoc_vi"),
                        rs.getString("hoc_ham"),
                        rs.getString("email"),
                        rs.getString("dien_thoai"),
                        rs.getString("ma_khoa"),
                        tt ? "Hoạt động" : "Khoá"
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(v, "Lỗi load dữ liệu: " + e.getMessage());
        }
    }

    private void addEvents() {

        // nếu view có nút quay lại
        if (v.btnQuayLai != null) {
            v.btnQuayLai.addActionListener(e -> v.dispose());
        }

        v.btnThem.addActionListener(e -> {
            try (Connection c = ConnectDB.getConnection()) {
                PreparedStatement ps = c.prepareStatement(
                        "INSERT INTO GiangVien(ma_gv, ten_gv, hoc_vi, hoc_ham, email, dien_thoai, ma_khoa, trang_thai) VALUES (?,?,?,?,?,?,?,?)"
                );
                ps.setString(1, v.txtMaGV.getText().trim());
                ps.setString(2, v.txtTenGV.getText().trim());
                ps.setString(3, emptyToNull(v.txtHocVi.getText()));
                ps.setString(4, emptyToNull(v.txtHocHam.getText()));
                ps.setString(5, emptyToNull(v.txtEmail.getText()));
                ps.setString(6, emptyToNull(v.txtDienThoai.getText()));
                ps.setString(7, emptyToNull(v.txtMaKhoa.getText()));
                ps.setBoolean(8, v.cbTrangThai.getSelectedItem().toString().equalsIgnoreCase("Hoạt động"));

                ps.executeUpdate();
                loadTable("");
                JOptionPane.showMessageDialog(v, "Thêm giảng viên thành công");
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(v, "Thêm lỗi: " + ex.getMessage());
            }
        });

        v.btnSua.addActionListener(e -> {
            try (Connection c = ConnectDB.getConnection()) {
                PreparedStatement ps = c.prepareStatement(
                        "UPDATE GiangVien SET ten_gv=?, hoc_vi=?, hoc_ham=?, email=?, dien_thoai=?, ma_khoa=?, trang_thai=? WHERE ma_gv=?"
                );
                ps.setString(1, v.txtTenGV.getText().trim());
                ps.setString(2, emptyToNull(v.txtHocVi.getText()));
                ps.setString(3, emptyToNull(v.txtHocHam.getText()));
                ps.setString(4, emptyToNull(v.txtEmail.getText()));
                ps.setString(5, emptyToNull(v.txtDienThoai.getText()));
                ps.setString(6, emptyToNull(v.txtMaKhoa.getText()));
                ps.setBoolean(7, v.cbTrangThai.getSelectedItem().toString().equalsIgnoreCase("Hoạt động"));
                ps.setString(8, v.txtMaGV.getText().trim());

                ps.executeUpdate();
                loadTable("");
                JOptionPane.showMessageDialog(v, "Sửa giảng viên thành công");
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(v, "Sửa lỗi: " + ex.getMessage());
            }
        });

        v.btnXoa.addActionListener(e -> {
            try (Connection c = ConnectDB.getConnection()) {
                String ma = v.txtMaGV.getText().trim();
                if (ma.isEmpty()) {
                    JOptionPane.showMessageDialog(v, "Chọn giảng viên cần xoá");
                    return;
                }

                int ok = JOptionPane.showConfirmDialog(v,
                        "Bạn có chắc muốn xoá GV: " + ma + " ?",
                        "Xác nhận",
                        JOptionPane.YES_NO_OPTION);
                if (ok != JOptionPane.YES_OPTION) return;

                PreparedStatement ps = c.prepareStatement("DELETE FROM GiangVien WHERE ma_gv=?");
                ps.setString(1, ma);
                ps.executeUpdate();
                loadTable("");
                JOptionPane.showMessageDialog(v, "Xoá giảng viên thành công");
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(v, "Xoá lỗi: " + ex.getMessage());
            }
        });

        v.btnLamMoi.addActionListener(e -> {
            clearForm();
            loadTable("");
        });

        v.btnTimKiem.addActionListener(e -> loadTable(v.txtTimKiem.getText().trim()));

        // Excel
        v.btnXuatExcel.addActionListener(e -> xuatExcel());
        v.btnNhapExcel.addActionListener(e -> nhapExcel());

        // click table -> fill form
        v.tblGiangVien.getSelectionModel().addListSelectionListener(e -> {
            int r = v.tblGiangVien.getSelectedRow();
            if (r >= 0) {
                v.txtMaGV.setText(v.tblGiangVien.getValueAt(r, 0).toString());
                v.txtTenGV.setText(nvl(v.tblGiangVien.getValueAt(r, 1)));
                v.txtHocVi.setText(nvl(v.tblGiangVien.getValueAt(r, 2)));
                v.txtHocHam.setText(nvl(v.tblGiangVien.getValueAt(r, 3)));
                v.txtEmail.setText(nvl(v.tblGiangVien.getValueAt(r, 4)));
                v.txtDienThoai.setText(nvl(v.tblGiangVien.getValueAt(r, 5)));
                v.txtMaKhoa.setText(nvl(v.tblGiangVien.getValueAt(r, 6)));

                String tt = v.tblGiangVien.getValueAt(r, 7).toString();
                v.cbTrangThai.setSelectedItem(tt);

                v.txtMaGV.setEditable(false);
            }
        });
    }

    private void clearForm() {
        v.txtMaGV.setText("");
        v.txtTenGV.setText("");
        v.txtHocVi.setText("");
        v.txtHocHam.setText("");
        v.txtEmail.setText("");
        v.txtDienThoai.setText("");
        v.txtMaKhoa.setText("");
        v.cbTrangThai.setSelectedIndex(0);
        v.txtTimKiem.setText("");
        v.txtMaGV.setEditable(true);
    }

    private void xuatExcel() {
        try (Workbook wb = new XSSFWorkbook()) {
            Sheet sheet = wb.createSheet("GiangVien");
            DefaultTableModel m = (DefaultTableModel) v.tblGiangVien.getModel();

            // header
            Row header = sheet.createRow(0);
            for (int j = 0; j < m.getColumnCount(); j++) {
                header.createCell(j).setCellValue(m.getColumnName(j));
            }

            // data
            for (int i = 0; i < m.getRowCount(); i++) {
                Row r = sheet.createRow(i + 1);
                for (int j = 0; j < m.getColumnCount(); j++) {
                    Object val = m.getValueAt(i, j);
                    r.createCell(j).setCellValue(val == null ? "" : val.toString());
                }
            }

            JFileChooser fc = new JFileChooser();
            fc.setDialogTitle("Chọn nơi lưu file Excel");
            if (fc.showSaveDialog(v) == JFileChooser.APPROVE_OPTION) {
                File f = fc.getSelectedFile();
                String path = f.getAbsolutePath();
                if (!path.toLowerCase().endsWith(".xlsx")) path += ".xlsx";

                try (FileOutputStream out = new FileOutputStream(path)) {
                    wb.write(out);
                }
                JOptionPane.showMessageDialog(v, "Xuất Excel thành công:\n" + path);
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(v, "Xuất Excel lỗi: " + e.getMessage());
        }
    }

    private void nhapExcel() {
        JFileChooser fc = new JFileChooser();
        fc.setDialogTitle("Chọn file Excel");
        if (fc.showOpenDialog(v) == JFileChooser.APPROVE_OPTION) {
            File file = fc.getSelectedFile();

            try (Workbook wb = new XSSFWorkbook(new FileInputStream(file));
                 Connection c = ConnectDB.getConnection()) {

                Sheet sheet = wb.getSheetAt(0);

                String sql = "INSERT INTO GiangVien(ma_gv, ten_gv, hoc_vi, hoc_ham, email, dien_thoai, ma_khoa, trang_thai) VALUES (?,?,?,?,?,?,?,?)";
                PreparedStatement ps = c.prepareStatement(sql);

                boolean firstRowIsHeader = true;

                for (Row r : sheet) {
                    if (firstRowIsHeader) {
                        firstRowIsHeader = false;
                        continue;
                    }
                    if (r == null) continue;

                    String ma = getCellString(r.getCell(0));
                    if (ma.isEmpty()) continue; // bỏ dòng trống

                    ps.setString(1, ma);
                    ps.setString(2, getCellString(r.getCell(1)));
                    ps.setString(3, nullIfEmpty(getCellString(r.getCell(2))));
                    ps.setString(4, nullIfEmpty(getCellString(r.getCell(3))));
                    ps.setString(5, nullIfEmpty(getCellString(r.getCell(4))));
                    ps.setString(6, nullIfEmpty(getCellString(r.getCell(5))));
                    ps.setString(7, nullIfEmpty(getCellString(r.getCell(6))));

                    String tt = getCellString(r.getCell(7));
                    boolean trangThai = tt.equalsIgnoreCase("Hoạt động") || tt.equals("1") || tt.equalsIgnoreCase("true");
                    ps.setBoolean(8, trangThai);

                    ps.addBatch();
                }

                ps.executeBatch();
                loadTable("");
                JOptionPane.showMessageDialog(v, "Nhập Excel thành công");
            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(v, "Nhập Excel lỗi: " + e.getMessage());
            }
        }
    }

    // ===== Helpers =====
    private static String nvl(Object o) {
        return o == null ? "" : o.toString();
    }

    private static String emptyToNull(String s) {
        if (s == null) return null;
        s = s.trim();
        return s.isEmpty() ? null : s;
    }

    private static String nullIfEmpty(String s) {
        s = (s == null) ? "" : s.trim();
        return s.isEmpty() ? null : s;
    }

    private static String getCellString(Cell cell) {
        if (cell == null) return "";
        cell.setCellType(CellType.STRING);
        return cell.getStringCellValue().trim();
    }
}
