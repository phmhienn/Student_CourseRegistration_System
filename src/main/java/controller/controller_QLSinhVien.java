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
import view.view_QLSinhVien;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.io.*;
import java.sql.*;

public class controller_QLSinhVien {

    private view_QLSinhVien v;

    public controller_QLSinhVien(view_QLSinhVien v) {
        this.v = v;

        loadTable("");
        addEvents();
    }

    private void loadTable(String key) {
        DefaultTableModel model = (DefaultTableModel) v.tblSinhVien.getModel();
        model.setRowCount(0);

        String sql = """
            SELECT * FROM SinhVien
            WHERE ma_sv LIKE ? OR ho_ten LIKE ?
        """;

        try (Connection c = ConnectDB.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setString(1, "%" + key + "%");
            ps.setString(2, "%" + key + "%");

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                model.addRow(new Object[]{
                        rs.getString("ma_sv"),
                        rs.getString("ho_ten"),
                        rs.getDate("ngay_sinh"),
                        rs.getString("gioi_tinh"),
                        rs.getString("lop"),
                        rs.getString("khoa")
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void addEvents() {

        v.btnQuayLai.addActionListener(e -> v.dispose());

        v.btnThem.addActionListener(e -> {
            String maSV  = v.txtMaSV.getText().trim();
            String hoTen = v.txtHoTen.getText().trim();

            if (maSV.isEmpty() || hoTen.isEmpty()) {
                return;
            }

            try (Connection c = ConnectDB.getConnection()) {
                PreparedStatement ps = c.prepareStatement(
                    "INSERT INTO SinhVien VALUES (?,?,?,?,?,?)"
                );
                ps.setString(1, maSV);
                ps.setString(2, hoTen);
                ps.setDate(3, new java.sql.Date(
                    ((java.util.Date) v.spNgaySinh.getValue()).getTime()
                ));
                ps.setString(4, v.cbGioiTinh.getSelectedItem().toString());
                ps.setString(5, v.txtLop.getText());
                ps.setString(6, v.txtKhoa.getText());

                ps.executeUpdate();
                loadTable("");
                v.btnLamMoi.doClick();

            } catch (SQLIntegrityConstraintViolationException ex) {
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        v.btnSua.addActionListener(e -> {
            try (Connection c = ConnectDB.getConnection()) {
                PreparedStatement ps = c.prepareStatement(
                        "UPDATE SinhVien SET ho_ten=?, ngay_sinh=?, gioi_tinh=?, lop=?, khoa=? WHERE ma_sv=?"
                );
                ps.setString(1, v.txtHoTen.getText());
                ps.setDate(2, new Date(((java.util.Date) v.spNgaySinh.getValue()).getTime()));
                ps.setString(3, v.cbGioiTinh.getSelectedItem().toString());
                ps.setString(4, v.txtLop.getText());
                ps.setString(5, v.txtKhoa.getText());
                ps.setString(6, v.txtMaSV.getText());
                ps.executeUpdate();
                loadTable("");
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        v.btnXoa.addActionListener(e -> {
            try (Connection c = ConnectDB.getConnection()) {
                PreparedStatement ps =
                        c.prepareStatement("DELETE FROM SinhVien WHERE ma_sv=?");
                ps.setString(1, v.txtMaSV.getText());
                ps.executeUpdate();
                loadTable("");
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        v.btnLamMoi.addActionListener(e -> {
            v.txtMaSV.setText("");
            v.txtHoTen.setText("");
            v.txtLop.setText("");
            v.txtKhoa.setText("");
            v.txtMaSV.setEditable(true);  
            v.tblSinhVien.clearSelection(); 

            loadTable("");
        });


        v.btnTimKiem.addActionListener(e ->
                loadTable(v.txtTimKiem.getText())
        );

        // Excel
        v.btnXuatExcel.addActionListener(e -> xuatExcel());
        v.btnNhapExcel.addActionListener(e -> nhapExcel());

        v.tblSinhVien.getSelectionModel().addListSelectionListener(e -> {
            int r = v.tblSinhVien.getSelectedRow();
            if (r >= 0) {
                v.txtMaSV.setText(v.tblSinhVien.getValueAt(r, 0).toString());
                v.txtHoTen.setText(v.tblSinhVien.getValueAt(r, 1).toString());
                v.spNgaySinh.setValue(v.tblSinhVien.getValueAt(r, 2));
                v.cbGioiTinh.setSelectedItem(v.tblSinhVien.getValueAt(r, 3));
                v.txtLop.setText(v.tblSinhVien.getValueAt(r, 4).toString());
                v.txtKhoa.setText(v.tblSinhVien.getValueAt(r, 5).toString());
                v.txtMaSV.setEditable(false);
            }
        });
    }

    private void xuatExcel() {
        try (Workbook wb = new XSSFWorkbook()) {
            Sheet sheet = wb.createSheet("SinhVien");
            DefaultTableModel m = (DefaultTableModel) v.tblSinhVien.getModel();

            for (int i = 0; i < m.getRowCount(); i++) {
                Row r = sheet.createRow(i);
                for (int j = 0; j < m.getColumnCount(); j++) {
                    r.createCell(j).setCellValue(m.getValueAt(i, j).toString());
                }
            }

            JFileChooser fc = new JFileChooser();
            if (fc.showSaveDialog(v) == JFileChooser.APPROVE_OPTION) {
                FileOutputStream out = new FileOutputStream(fc.getSelectedFile() + ".xlsx");
                wb.write(out);
                out.close();
                JOptionPane.showMessageDialog(v, "Xuất Excel thành công");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void nhapExcel() {
    JFileChooser fc = new JFileChooser();
    if (fc.showOpenDialog(v) != JFileChooser.APPROVE_OPTION) return;

    int success = 0;
    int skipped = 0;

    try (Workbook wb = new XSSFWorkbook(new FileInputStream(fc.getSelectedFile()));
         Connection c = ConnectDB.getConnection()) {

        Sheet sheet = wb.getSheetAt(0);

        for (Row r : sheet) {

            if (r.getRowNum() == 0) continue;

            try {
                String maSV = r.getCell(0).getStringCellValue().trim();
                String hoTen = r.getCell(1).getStringCellValue().trim();

                // ===== XỬ LÝ NGÀY SINH (DATE HOẶC STRING) =====
                Cell cellNgaySinh = r.getCell(2);
                java.sql.Date ngaySinh;

                if (cellNgaySinh.getCellType() == CellType.NUMERIC) {
                    java.util.Date d = cellNgaySinh.getDateCellValue();
                    ngaySinh = new java.sql.Date(d.getTime());
                } else {
                    ngaySinh = java.sql.Date.valueOf(cellNgaySinh.getStringCellValue().trim());
                }

                String gioiTinh = r.getCell(3).getStringCellValue().trim();
                String lop = r.getCell(4).getStringCellValue().trim();
                String khoa = r.getCell(5).getStringCellValue().trim();

                PreparedStatement check = c.prepareStatement(
                        "SELECT COUNT(*) FROM SinhVien WHERE ma_sv=?"
                );
                check.setString(1, maSV);
                ResultSet rs = check.executeQuery();
                rs.next();
                if (rs.getInt(1) > 0) {
                    skipped++;
                    continue;
                }

                PreparedStatement ps = c.prepareStatement(
                        "INSERT INTO SinhVien VALUES (?,?,?,?,?,?)"
                );
                ps.setString(1, maSV);
                ps.setString(2, hoTen);
                ps.setDate(3, ngaySinh);
                ps.setString(4, gioiTinh);
                ps.setString(5, lop);
                ps.setString(6, khoa);
                ps.executeUpdate();

                success++;

            } catch (Exception rowEx) {
                skipped++;
            }
        }

        loadTable("");
        JOptionPane.showMessageDialog(
                v,
                "Nhập Excel xong!\nThành công: " + success + "\nBỏ qua: " + skipped
        );

      } catch (Exception e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(v, "Lỗi khi nhập Excel");
        }
    }
}