package controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import model.ConnectDB;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import view.view_Quanlymonhoc;

public class controller_Quanlymonhoc {

    private final view_Quanlymonhoc v;

    public controller_Quanlymonhoc(view_Quanlymonhoc v) {
        this.v = v;
        loadTable();

        v.tbl.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) fillForm();
        });
        
        v.btnTim.addActionListener(e -> tim());
        v.btnThem.addActionListener(e -> them());
        v.btnSua.addActionListener(e -> sua());
        v.btnXoa.addActionListener(e -> xoa());
        v.btnLamMoi.addActionListener(e -> lamMoi());
        v.btnXuatExcel.addActionListener(e -> xuatExcel());
        v.btnNhapExcel.addActionListener(e -> nhapExcel());
        v.btnQuayLai.addActionListener(e -> v.dispose());
    }

    private void loadTable() {
        v.dtm.setRowCount(0);

        String sql = "SELECT ma_mon, ten_mon, so_tin_chi FROM MonHoc";

        try (Connection con = ConnectDB.getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                v.dtm.addRow(new Object[]{
                    rs.getString(1),
                    rs.getString(2),
                    rs.getInt(3)
                });
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(v, e.getMessage());
        }
    }

    private void fillForm() {
        int r = v.tbl.getSelectedRow();
        if (r < 0) return;

        v.txtMaMon.setText(v.dtm.getValueAt(r, 0).toString());
        v.txtTenMon.setText(v.dtm.getValueAt(r, 1).toString());
        v.txtTinChi.setText(v.dtm.getValueAt(r, 2).toString());

        v.txtMaMon.setEnabled(false);
    }

    private void them() {
        String ma = v.txtMaMon.getText().trim();
        String ten = v.txtTenMon.getText().trim();
        int tc;
        try {
            tc = Integer.parseInt(v.txtTinChi.getText().trim());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(v, "Số tín chỉ phải là số!");
            return;
        }
        String sql =
            "INSERT INTO MonHoc(ma_mon,ten_mon,so_tin_chi) VALUES(" +
            "'" + ma + "',N'" + ten + "'," + tc + ")";

        try (Connection con = ConnectDB.getConnection();
             Statement st = con.createStatement()) {
            
            st.executeUpdate(sql);
            loadTable();
            lamMoi();
        } catch (Exception e) {
            if (e.getMessage().toLowerCase().contains("duplicate")
            || e.getMessage().toLowerCase().contains("primary key")
            || e.getMessage().toLowerCase().contains("unique")) {

            JOptionPane.showMessageDialog(v, "Mã môn đã tồn tại!");

            } else {
                JOptionPane.showMessageDialog(v, "Lỗi: " + e.getMessage());
            }
        }
    }

    private void sua() {
        String ma = v.txtMaMon.getText().trim();
        String ten = v.txtTenMon.getText().trim();
        int tc = Integer.parseInt(v.txtTinChi.getText().trim());

        String sql =
            "UPDATE MonHoc SET ten_mon=N'" + ten + "', so_tin_chi=" + tc +
            " WHERE ma_mon='" + ma + "'";

        try (Connection con = ConnectDB.getConnection();
             Statement st = con.createStatement()) {

            st.executeUpdate(sql);
            loadTable();
            lamMoi();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(v, e.getMessage());
        }
    }

    private void xoa() {
        String ma = v.txtMaMon.getText().trim();
        String sql = "DELETE FROM MonHoc WHERE ma_mon='" + ma + "'";

        try (Connection con = ConnectDB.getConnection();
             Statement st = con.createStatement()) {

            st.executeUpdate(sql);
            loadTable();
            lamMoi();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(v, e.getMessage());
        }
    }
    
    private void tim() {
        v.dtm.setRowCount(0);

        String key = v.txtTim.getText().trim();

        String sql =
            "SELECT ma_mon, ten_mon, so_tin_chi " +
            "FROM MonHoc " +
            "WHERE ma_mon LIKE '%" + key + "%' " +
            "OR ten_mon LIKE N'%" + key + "%'";

        try (Connection con = ConnectDB.getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                v.dtm.addRow(new Object[]{
                    rs.getString("ma_mon"),
                    rs.getString("ten_mon"),
                    rs.getInt("so_tin_chi")
                });
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(v, "Lỗi tìm kiếm: " + e.getMessage());
        }
    }


    
    private void xuatExcel() {
        JFileChooser fc = new JFileChooser();
        fc.setDialogTitle("Chọn nơi lưu Excel");
        if (fc.showSaveDialog(v) != JFileChooser.APPROVE_OPTION) return;

        File file = fc.getSelectedFile();
        if (!file.getName().toLowerCase().endsWith(".xlsx")) {
            file = new File(file.getAbsolutePath() + ".xlsx");
        }

        try (Workbook wb = new XSSFWorkbook()) {
            Sheet sheet = wb.createSheet("MonHoc");

            // header
            Row header = sheet.createRow(0);
            header.createCell(0).setCellValue("ma_mon");
            header.createCell(1).setCellValue("ten_mon");
            header.createCell(2).setCellValue("so_tin_chi");

            String sql = "SELECT ma_mon, ten_mon, so_tin_chi FROM MonHoc ORDER BY ma_mon";

            try (Connection con = ConnectDB.getConnection();
                 Statement st = con.createStatement();
                 ResultSet rs = st.executeQuery(sql)) {

                int r = 1;
                while (rs.next()) {
                    Row row = sheet.createRow(r++);
                    row.createCell(0).setCellValue(rs.getString("ma_mon"));
                    row.createCell(1).setCellValue(rs.getString("ten_mon"));
                    row.createCell(2).setCellValue(rs.getInt("so_tin_chi"));
                }
            }

            sheet.autoSizeColumn(0);
            sheet.autoSizeColumn(1);
            sheet.autoSizeColumn(2);

            try (FileOutputStream fos = new FileOutputStream(file)) {
                wb.write(fos);
            }

            JOptionPane.showMessageDialog(v, "Xuất Excel thành công!");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(v, "Lỗi xuất Excel: " + e.getMessage());
        }
    }

    // ================= NHẬP EXCEL =================
    private void nhapExcel() {
        JFileChooser fc = new JFileChooser();
        fc.setDialogTitle("Chọn file Excel để nhập");
        if (fc.showOpenDialog(v) != JFileChooser.APPROVE_OPTION) return;

        File file = fc.getSelectedFile();

        try (FileInputStream fis = new FileInputStream(file);
             Workbook wb = new XSSFWorkbook(fis);
             Connection con = ConnectDB.getConnection();
             Statement st = con.createStatement()) {

            Sheet sheet = wb.getSheetAt(0);

            // dữ liệu từ dòng 1 (dòng 0 là header)-------------
            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);
                if (row == null) continue;

                String ma  = row.getCell(0).toString().trim();
                String ten = row.getCell(1).toString().trim();

                // số tín chỉ có thể đọc ra kiểu 3.0 -> ép về int--------------
                int tc = (int) Double.parseDouble(row.getCell(2).toString().trim());

                if (ma.isEmpty() || ten.isEmpty()) continue;

                // nếu trùng mã => update, chưa có => insert
                String sql =
                    "IF EXISTS (SELECT 1 FROM MonHoc WHERE ma_mon='" + ma + "') " +
                    "UPDATE MonHoc SET ten_mon=N'" + ten + "', so_tin_chi=" + tc +
                    " WHERE ma_mon='" + ma + "' " +
                    "ELSE INSERT INTO MonHoc(ma_mon,ten_mon,so_tin_chi) VALUES(" +
                    "'" + ma + "',N'" + ten + "'," + tc + ")";

                st.executeUpdate(sql);
            }

            JOptionPane.showMessageDialog(v, "Nhập Excel thành công!");
            loadTable();
            lamMoi();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(v, "Lỗi nhập Excel: " + e.getMessage());
        }
    }
    
    private void lamMoi() {
        v.txtMaMon.setText("");
        v.txtTenMon.setText("");
        v.txtTinChi.setText("");
        v.txtMaMon.setEnabled(true);
        v.tbl.clearSelection();
    }
}
