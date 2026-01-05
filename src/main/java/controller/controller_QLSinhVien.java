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
            try (Connection c = ConnectDB.getConnection()) {
                PreparedStatement ps = c.prepareStatement(
                        "INSERT INTO SinhVien VALUES (?,?,?,?,?,?)"
                );
                ps.setString(1, v.txtMaSV.getText());
                ps.setString(2, v.txtHoTen.getText());
                ps.setDate(3, new Date(((java.util.Date) v.spNgaySinh.getValue()).getTime()));
                ps.setString(4, v.cbGioiTinh.getSelectedItem().toString());
                ps.setString(5, v.txtLop.getText());
                ps.setString(6, v.txtKhoa.getText());
                ps.executeUpdate();
                loadTable("");
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
        if (fc.showOpenDialog(v) == JFileChooser.APPROVE_OPTION) {
            try (Workbook wb = new XSSFWorkbook(new FileInputStream(fc.getSelectedFile()))) {
                Sheet sheet = wb.getSheetAt(0);
                Connection c = ConnectDB.getConnection();

                for (Row r : sheet) {
                    if (r.getRowNum() == 0) continue;
                    PreparedStatement ps = c.prepareStatement(
                            "INSERT INTO SinhVien VALUES (?,?,?,?,?,?)"
                    );
                    ps.setString(1, r.getCell(0).getStringCellValue());
                    ps.setString(2, r.getCell(1).getStringCellValue());
                    ps.setDate(3, Date.valueOf(r.getCell(2).getStringCellValue()));
                    ps.setString(4, r.getCell(3).getStringCellValue());
                    ps.setString(5, r.getCell(4).getStringCellValue());
                    ps.setString(6, r.getCell(5).getStringCellValue());
                    ps.executeUpdate();
                }
                loadTable("");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}