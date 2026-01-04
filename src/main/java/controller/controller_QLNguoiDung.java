/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

/**
 *
 * @author thedu
 */
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import model.ConnectDB;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import view.view_QLNguoiDung;

public class controller_QLNguoiDung {

    private final view_QLNguoiDung v;

    public controller_QLNguoiDung(view_QLNguoiDung v) {
        this.v = v;

        loadTable("");

        v.tbl.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) fillFormFromTable();
        });

        v.btnThem.addActionListener(e -> them());
        v.btnSua.addActionListener(e -> sua());
        v.btnXoa.addActionListener(e -> xoa());
        v.btnLamMoi.addActionListener(e -> v.clearForm());
        v.btnTimKiem.addActionListener(e -> loadTable(v.txtTimKiem.getText().trim()));
        v.btnXuatExcel.addActionListener(e -> xuatExcel());
        v.btnNhapExcel.addActionListener(e -> nhapExcel());
        v.btnQuayLai.addActionListener(e -> v.dispose());
    }

    // ================= LOAD =================
    private void loadTable(String key) {
        v.model.setRowCount(0);

        String sql =
            "SELECT admin_id, username, password, ho_ten, trang_thai " +
            "FROM Admin " +
            "WHERE username LIKE '%" + key + "%' OR ho_ten LIKE N'%" + key + "%' " +
            "ORDER BY admin_id DESC";

        try (Connection con = ConnectDB.getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                v.model.addRow(new Object[]{
                    rs.getInt("admin_id"),
                    rs.getString("username"),
                    rs.getString("password"),
                    rs.getString("ho_ten"),
                    rs.getBoolean("trang_thai") ? "Hoạt động" : "Khoá"
                });
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(v, e.getMessage());
        }
    }

    private void fillFormFromTable() {
        int r = v.tbl.getSelectedRow();
        if (r < 0) return;

        v.txtUsername.setText(v.model.getValueAt(r, 1).toString());
        v.txtPassword.setText(v.model.getValueAt(r, 2).toString());
        v.txtHoTen.setText(v.model.getValueAt(r, 3).toString());
        v.cbTrangThai.setSelectedItem(v.model.getValueAt(r, 4).toString());

        v.txtUsername.setEnabled(false);
    }

    // ================= THÊM =================
    private void them() {
        String user = v.txtUsername.getText().trim();
        String pass = v.txtPassword.getText().trim();
        String hoten = v.txtHoTen.getText().trim();
        int tt = v.cbTrangThai.getSelectedIndex() == 0 ? 1 : 0;

        if (user.isEmpty() || pass.isEmpty()) {
            JOptionPane.showMessageDialog(v, "Thiếu username hoặc password");
            return;
        }

        String sql =
            "INSERT INTO Admin(username,password,ho_ten,trang_thai) VALUES (" +
            "'" + user + "','" + pass + "',N'" + hoten + "'," + tt + ")";

        try (Connection con = ConnectDB.getConnection();
             Statement st = con.createStatement()) {

            st.executeUpdate(sql);
            loadTable("");
            v.clearForm();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(v, e.getMessage());
        }
    }

    // ================= SỬA =================
    private void sua() {
        String user = v.txtUsername.getText().trim();
        String pass = v.txtPassword.getText().trim();
        String hoten = v.txtHoTen.getText().trim();
        int tt = v.cbTrangThai.getSelectedIndex() == 0 ? 1 : 0;

        if (user.isEmpty()) return;

        String sql =
            "UPDATE Admin SET password='" + pass + "', ho_ten=N'" + hoten +
            "', trang_thai=" + tt +
            " WHERE username='" + user + "'";

        try (Connection con = ConnectDB.getConnection();
             Statement st = con.createStatement()) {

            st.executeUpdate(sql);
            loadTable("");
            v.clearForm();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(v, e.getMessage());
        }
    }

    // ================= XOÁ =================
    private void xoa() {
        String user = v.txtUsername.getText().trim();
        if (user.isEmpty() || user.equalsIgnoreCase("admin")) return;

        String sql = "DELETE FROM Admin WHERE username='" + user + "'";

        try (Connection con = ConnectDB.getConnection();
             Statement st = con.createStatement()) {

            st.executeUpdate(sql);
            loadTable("");
            v.clearForm();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(v, e.getMessage());
        }
    }

    // ================= XUẤT EXCEL =================
    private void xuatExcel() {
        JFileChooser fc = new JFileChooser();
        if (fc.showSaveDialog(v) != JFileChooser.APPROVE_OPTION) return;

        File f = fc.getSelectedFile();
        if (!f.getName().endsWith(".xlsx")) {
            f = new File(f.getAbsolutePath() + ".xlsx");
        }

        try (Workbook wb = new XSSFWorkbook()) {
            Sheet s = wb.createSheet("Admin");
            Row h = s.createRow(0);
            h.createCell(0).setCellValue("ID");
            h.createCell(1).setCellValue("Username");
            h.createCell(2).setCellValue("Password");
            h.createCell(3).setCellValue("Họ tên");
            h.createCell(4).setCellValue("Trạng thái");

            String sql = "SELECT * FROM Admin";
            try (Connection con = ConnectDB.getConnection();
                 Statement st = con.createStatement();
                 ResultSet rs = st.executeQuery(sql)) {

                int r = 1;
                while (rs.next()) {
                    Row row = s.createRow(r++);
                    row.createCell(0).setCellValue(rs.getInt(1));
                    row.createCell(1).setCellValue(rs.getString(2));
                    row.createCell(2).setCellValue(rs.getString(3));
                    row.createCell(3).setCellValue(rs.getString(4));
                    row.createCell(4).setCellValue(rs.getBoolean(5) ? 1 : 0);
                }
            }
            wb.write(new FileOutputStream(f));
            JOptionPane.showMessageDialog(v, "Nhập Excel thành công!");
            loadTable("");
            v.clearForm();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(v, e.getMessage());
        }
    }

    // ================= NHẬP EXCEL =================
    private void nhapExcel() {
        JFileChooser fc = new JFileChooser();
        if (fc.showOpenDialog(v) != JFileChooser.APPROVE_OPTION) return;

        File f = fc.getSelectedFile();

        try (Workbook wb = new XSSFWorkbook(new FileInputStream(f));
             Connection con = ConnectDB.getConnection();
             Statement st = con.createStatement()) {

            Sheet s = wb.getSheetAt(0);
            for (int i = 1; i <= s.getLastRowNum(); i++) {
                Row r = s.getRow(i);
                if (r == null) continue;

                String u = r.getCell(1).getStringCellValue();
                String p = r.getCell(2).getStringCellValue();
                String h = r.getCell(3).getStringCellValue();
                int t = (int) r.getCell(4).getNumericCellValue();

                String sql =
                    "IF EXISTS (SELECT 1 FROM Admin WHERE username='" + u + "') " +
                    "UPDATE Admin SET password='" + p + "', ho_ten=N'" + h +
                    "', trang_thai=" + t +
                    " WHERE username='" + u + "' " +
                    "ELSE INSERT INTO Admin(username,password,ho_ten,trang_thai) " +
                    "VALUES('" + u + "','" + p + "',N'" + h + "'," + t + ")";

                st.executeUpdate(sql);
            }
            JOptionPane.showMessageDialog(v, "Nhập Excel thành công!");
            loadTable("");
            v.clearForm();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(v, e.getMessage());
        }
    }
}