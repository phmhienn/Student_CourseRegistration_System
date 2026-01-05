package controller;

import model.ConnectDB;
import view.view_Admin;
import view.view_QuanLyLopHocPhan;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.*;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

public class controller_QLLopHocPhan {

    private final view_QuanLyLopHocPhan view;
    private final view_Admin adminView;
    private final DefaultTableModel model;

    public controller_QLLopHocPhan(view_QuanLyLopHocPhan view, view_Admin adminView) {
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
        SELECT ma_gv
        FROM GiangVien
        WHERE trang_thai = 1
        """;

        try (Connection con = ConnectDB.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                view.cboGiangVien.addItem(rs.getString("ma_gv"));
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
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(view, "Lỗi load môn học");
        }
    }

    private void loadComboHocKy() {
        view.cboHocKy.removeAllItems();
        view.cboHocKy.addItem("--Chọn Học Kỳ--");

        String sql = """
        SELECT ma_hoc_ky
        FROM HocKy
        WHERE trang_thai = 1
        """;

        try (Connection con = ConnectDB.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                view.cboHocKy.addItem(rs.getString("ma_hoc_ky"));
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(view, "Lỗi load học kỳ");
            e.printStackTrace();
        }
    }

    private void loadTable() {
        model.setRowCount(0);

        String sql = """
                SELECT lhp.ma_lhp, mh.ma_mon, mh.ten_mon, lhp.ma_hoc_ky,
                       lhp.giang_vien, lhp.so_luong_toi_da,
                       lhp.thu, lhp.ca_hoc, lhp.phong_hoc, lhp.trang_thai
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
                        rs.getString("phong_hoc"),
                        rs.getString("trang_thai")
                });
            }

        } catch (SQLException e) {
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

                String maHocKy = model.getValueAt(row, 3).toString();
                view.cboHocKy.setSelectedItem(maHocKy);
                view.cboGiangVien.setSelectedItem(model.getValueAt(row, 4).toString());
                view.txtSoLuong.setText(model.getValueAt(row, 5).toString());
                view.cboThu.setSelectedItem(model.getValueAt(row, 6).toString());
                view.cboCaHoc.setSelectedItem(model.getValueAt(row, 7).toString());
                view.txtPhong.setText(model.getValueAt(row, 8).toString());
                view.cboTrangThai.setSelectedItem(model.getValueAt(row, 9).toString());
                view.txtMaLHP.setEditable(false);
            }
        });

        view.btnThem.addActionListener(e -> them());
        view.btnSua.addActionListener(e -> sua());
        view.btnXoa.addActionListener(e -> xoa());
        view.btnLamMoi.addActionListener(e -> lamMoi());
        view.btnTim.addActionListener(e -> tim());
        view.btnQuayLai.addActionListener(e -> view.dispose());
        view.btnNhapExcel.addActionListener(e ->nhapExcel());
        view.btnXuatExcel.addActionListener(e ->xuatExcel());
    }
    
    private void them() {
        if (!validateForm()) return;
        if (isTrungLichPhong(
                view.cboThu.getSelectedItem().toString(),
                view.cboCaHoc.getSelectedItem().toString(),
                view.txtPhong.getText().trim(),
                getMaHocKy(),
                ""
        )) {
            JOptionPane.showMessageDialog(view,
                    "Phòng học đã được sử dụng ở ca này!");
            return;
        }
        String sql = """
                INSERT INTO LopHocPhan
                (ma_lhp, ma_mon, ten_mon, ma_hoc_ky, giang_vien,
                 so_luong_toi_da, thu, ca_hoc, phong_hoc, trang_thai)
                VALUES (?,?,?,?,?,?,?,?,?,?)
                """;

        try (Connection con = ConnectDB.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, view.txtMaLHP.getText());
            ps.setString(2, getMaMon());
            ps.setString(3, view.cboMonHoc.getSelectedItem().toString());
            ps.setString(4, getMaHocKy());
            ps.setString(5, getMaGiangVien());
            ps.setInt(6, Integer.parseInt(view.txtSoLuong.getText()));
            ps.setString(7, view.cboThu.getSelectedItem().toString());
            ps.setString(8, view.cboCaHoc.getSelectedItem().toString());
            ps.setString(9, view.txtPhong.getText());
            ps.setString(10, view.cboTrangThai.getSelectedItem().toString());

            ps.executeUpdate();
            JOptionPane.showMessageDialog(view, "Thêm thành công");
            loadTable();
            lamMoi();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(view, "Trùng mã LHP hoặc dữ liệu sai");
        }
    }

    private void sua() {
        if (!validateForm()) return;
        if (isTrungLichPhong(
                view.cboThu.getSelectedItem().toString(),
                view.cboCaHoc.getSelectedItem().toString(),
                view.txtPhong.getText().trim(),
                getMaHocKy(),
                view.txtMaLHP.getText()
        )) {
            JOptionPane.showMessageDialog(view,
                    "Phòng học đã bị trùng lịch!");
            return;
        }
        String sql = """
                UPDATE LopHocPhan SET
                ma_mon=?, ma_hoc_ky=?, giang_vien=?, so_luong_toi_da=?,
                thu=?, ca_hoc=?, phong_hoc=?,trang_thai=?
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
            ps.setString(8, view.cboTrangThai.getSelectedItem().toString());
            ps.setString(9, view.txtMaLHP.getText());


            ps.executeUpdate();
            JOptionPane.showMessageDialog(view, "Cập nhật thành công");
            loadTable();

        } catch (SQLException e) {
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

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(view, "Không thể xoá (đã có đăng ký)");
        }
    }

    private void tim() {
        model.setRowCount(0);
        String key = "%" + view.txtTim.getText() + "%";

        String sql = """
                SELECT lhp.ma_lhp, mh.ma_mon, mh.ten_mon, lhp.ma_hoc_ky,
                       lhp.giang_vien, lhp.so_luong_toi_da,
                       lhp.thu, lhp.ca_hoc, lhp.phong_hoc, lhp.trang_thai
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
                        rs.getString("phong_hoc"),
                        rs.getString("trang_thai")
                });
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(view, "Lỗi tìm kiếm");
        }
    }

    private void nhapExcel() {

        JFileChooser fc = new JFileChooser();
        if (fc.showOpenDialog(view) != JFileChooser.APPROVE_OPTION) return;

        File file = fc.getSelectedFile();

        String sql = """
        INSERT INTO LopHocPhan
        (ma_lhp, ma_mon, ten_mon, ma_hoc_ky, giang_vien,
         so_luong_toi_da, thu, ca_hoc, phong_hoc, trang_thai)
        VALUES (?,?,?,?,?,?,?,?,?,?)
        """;

        try (FileInputStream fis = new FileInputStream(file);
             Workbook wb = new XSSFWorkbook(fis);
             Connection con = ConnectDB.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            Sheet sheet = wb.getSheetAt(0);

            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                Row r = sheet.getRow(i);
                if (r == null) continue;

                ps.setString(1, r.getCell(0).getStringCellValue().trim()); // ma_lhp
                ps.setString(2, r.getCell(1).getStringCellValue().trim()); // ma_mon
                ps.setString(3, r.getCell(2).getStringCellValue().trim()); // ten_mon
                ps.setString(4, r.getCell(3).getStringCellValue().trim()); // ma_hoc_ky
                ps.setString(5, r.getCell(4).getStringCellValue().trim()); // giang_vien
                ps.setInt(6, (int) r.getCell(5).getNumericCellValue());    // so_luong
                ps.setString(7, r.getCell(6).getStringCellValue().trim()); // thu
                ps.setString(8, r.getCell(7).getStringCellValue().trim()); // ca_hoc
                ps.setString(9, r.getCell(8).getStringCellValue().trim()); // phong
                ps.setString(10, r.getCell(9).getStringCellValue().trim()); // trang_thai

                ps.addBatch();
            }

            ps.executeBatch();
            JOptionPane.showMessageDialog(view, "Nhập Excel thành công");
            loadTable();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(view, "Lỗi nhập Excel: " + e.getMessage());
        }
    }
    private void xuatExcel() {

        if (model.getRowCount() == 0) {
            JOptionPane.showMessageDialog(view, "Không có dữ liệu để xuất");
            return;
        }

        JFileChooser fc = new JFileChooser();
        if (fc.showSaveDialog(view) != JFileChooser.APPROVE_OPTION) return;

        File file = fc.getSelectedFile();
        if (!file.getName().endsWith(".xlsx")) {
            file = new File(file.getAbsolutePath() + ".xlsx");
        }

        try (Workbook wb = new XSSFWorkbook();
             FileOutputStream fos = new FileOutputStream(file)) {

            Sheet sheet = wb.createSheet("LopHocPhan");

            String[] headers = {
                    "Mã LHP", "Mã môn", "Tên môn", "Mã học kỳ", "Giảng viên",
                    "Số lượng tối đa", "Thứ", "Ca học", "Phòng học", "Trạng thái"
            };

            Row headerRow = sheet.createRow(0);
            for (int i = 0; i < headers.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(headers[i]);
            }

            for (int i = 0; i < model.getRowCount(); i++) {
                Row row = sheet.createRow(i + 1);

                row.createCell(0).setCellValue(model.getValueAt(i, 0).toString()); // ma_lhp
                row.createCell(1).setCellValue(model.getValueAt(i, 1).toString()); // ma_mon
                row.createCell(2).setCellValue(model.getValueAt(i, 2).toString()); // ten_mon
                row.createCell(3).setCellValue(model.getValueAt(i, 3).toString()); // ma_hoc_ky
                row.createCell(4).setCellValue(model.getValueAt(i, 4).toString()); // giang_vien
                row.createCell(5).setCellValue(
                        Integer.parseInt(model.getValueAt(i, 5).toString())
                ); // so_luong
                row.createCell(6).setCellValue(model.getValueAt(i, 6).toString()); // thu
                row.createCell(7).setCellValue(model.getValueAt(i, 7).toString()); // ca
                row.createCell(8).setCellValue(model.getValueAt(i, 8).toString()); // phong
                row.createCell(9).setCellValue(model.getValueAt(i, 9).toString()); // trang_thai
            }


            for (int i = 0; i < headers.length; i++) {
                sheet.autoSizeColumn(i);
            }

            wb.write(fos);
            JOptionPane.showMessageDialog(view, "Xuất Excel thành công!");

        } catch (Exception e) {
            JOptionPane.showMessageDialog(view, "Lỗi xuất Excel: " + e.getMessage());
        }
    }

    private boolean isTrungLichPhong(String thu, String ca, String phong, String maHocKy, String maLHP) {

        String sql = """
        SELECT COUNT(*)
        FROM LopHocPhan
        WHERE thu = ?
          AND ca_hoc = ?
          AND phong_hoc = ?
          AND ma_hoc_ky = ?
          AND ma_lhp <> ?
        """;

        try (Connection con = ConnectDB.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, thu);
            ps.setString(2, ca);
            ps.setString(3, phong);
            ps.setString(4, maHocKy);
            ps.setString(5, maLHP);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }
    private boolean validateForm() {


        if (view.txtMaLHP.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(view, "Mã lớp học phần không được để trống");
            view.txtMaLHP.requestFocus();
            return false;
        }


        if (view.cboMonHoc.getSelectedIndex() == 0) {
            JOptionPane.showMessageDialog(view, "Vui lòng chọn môn học");
            view.cboMonHoc.requestFocus();
            return false;
        }


        if (view.cboHocKy.getSelectedIndex() == 0) {
            JOptionPane.showMessageDialog(view, "Vui lòng chọn học kỳ");
            view.cboHocKy.requestFocus();
            return false;
        }


        if (view.cboGiangVien.getSelectedIndex() == 0) {
            JOptionPane.showMessageDialog(view, "Vui lòng chọn giảng viên");
            view.cboGiangVien.requestFocus();
            return false;
        }


        String soLuongStr = view.txtSoLuong.getText().trim();
        if (soLuongStr.isEmpty()) {
            JOptionPane.showMessageDialog(view, "Số lượng không được để trống");
            view.txtSoLuong.requestFocus();
            return false;
        }

        int soLuong;
        try {
            soLuong = Integer.parseInt(soLuongStr);
            if (soLuong <= 0) {
                JOptionPane.showMessageDialog(view, "Số lượng phải lớn hơn 0");
                view.txtSoLuong.requestFocus();
                return false;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(view, "Số lượng phải là số nguyên");
            view.txtSoLuong.requestFocus();
            return false;
        }


        if (view.cboThu.getSelectedItem() == null) {
            JOptionPane.showMessageDialog(view, "Vui lòng chọn thứ học");
            return false;
        }


        if (view.cboCaHoc.getSelectedItem() == null) {
            JOptionPane.showMessageDialog(view, "Vui lòng chọn ca học");
            return false;
        }


        if (view.txtPhong.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(view, "Phòng học không được để trống");
            view.txtPhong.requestFocus();
            return false;
        }

        return true;
    }


    private String getMaMon() {
        String item = view.cboMonHoc.getSelectedItem().toString();
        return item.split(" - ")[0];
    }
    private String getMaHocKy() {
        return view.cboHocKy.getSelectedItem().toString();
    }
    private String getMaGiangVien() {
        return view.cboGiangVien.getSelectedItem().toString();
    }

    private void lamMoi() {
        view.txtMaLHP.setText("");
        view.txtSoLuong.setText("");
        view.txtPhong.setText("");
        view.cboMonHoc.setSelectedIndex(0);
        view.cboHocKy.setSelectedIndex(0);
        view.cboGiangVien.setSelectedIndex(0);
        view.cboThu.setSelectedIndex(0);
        view.cboCaHoc.setSelectedIndex(0);
        view.cboTrangThai.setSelectedIndex(0);
        view.txtMaLHP.setEditable(true);
        view.tbl.clearSelection();
    }
}