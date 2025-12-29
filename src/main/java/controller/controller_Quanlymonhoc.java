
package controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import model.ConnectDB;
import model.model_Khoa;
import view.view_Quanlymonhoc;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
/**
 *
 * @author Dvtt
 */
public class controller_Quanlymonhoc {
    private final view_Quanlymonhoc v;

    public controller_Quanlymonhoc(view_Quanlymonhoc v) {
        this.v = v;

        taiKhoa();
        taiDanhSachMonHoc(null);

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
        v.btnQuayLai.addActionListener(e -> v.dispose());

        v.btnTim.addActionListener(e -> tim());
        v.btnXuatExcel.addActionListener(e -> xuatExcel());
        v.btnNhapExcel.addActionListener(e -> nhapExcel());
    }

    private void taiKhoa() {
        v.cboKhoa.removeAllItems();
        String sql = "SELECT ma_khoa, ten_khoa FROM Khoa ORDER BY ten_khoa";

        try (Connection c = ConnectDB.getConnection();
             Statement st = c.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                v.cboKhoa.addItem(new model_Khoa(rs.getString("ma_khoa"), rs.getString("ten_khoa")));
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(v, "Lỗi tải khoa: " + ex.getMessage());
        }
    }

    private void taiDanhSachMonHoc(String dieuKienWhere) {
        v.dtm.setRowCount(0);

        String sql = "SELECT ma_mon, ten_mon, so_tin_chi, ma_khoa FROM MonHoc";
        if (dieuKienWhere != null && !dieuKienWhere.trim().isEmpty()) sql += " WHERE " + dieuKienWhere;
        sql += " ORDER BY ma_mon";

        try (Connection c = ConnectDB.getConnection();
             Statement st = c.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                v.dtm.addRow(new Object[]{
                        rs.getString("ma_mon"),
                        rs.getString("ten_mon"),
                        rs.getInt("so_tin_chi"),
                        rs.getString("ma_khoa")
                });
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(v, "Lỗi tải môn học: " + ex.getMessage());
        }
    }

    private void doRowClick() {
        int row = v.tbl.getSelectedRow();
        if (row < 0) return;

        v.txtMaMon.setText(v.dtm.getValueAt(row, 0).toString());
        v.txtTenMon.setText(v.dtm.getValueAt(row, 1).toString());
        v.txtTinChi.setText(v.dtm.getValueAt(row, 2).toString());

        String maKhoa = v.dtm.getValueAt(row, 3).toString();
        // set combo theo ma khoa
        for (int i = 0; i < v.cboKhoa.getItemCount(); i++) {
            model_Khoa k = v.cboKhoa.getItemAt(i);
            if (k.getMaKhoa().equalsIgnoreCase(maKhoa)) {
                v.cboKhoa.setSelectedIndex(i);
                break;
            }
        }
    }

    private void them() {
        String ma = v.txtMaMon.getText().trim();
        String ten = v.txtTenMon.getText().trim();
        String tcStr = v.txtTinChi.getText().trim();
        model_Khoa khoa = (model_Khoa) v.cboKhoa.getSelectedItem();

        if (ma.isEmpty() || ten.isEmpty() || tcStr.isEmpty() || khoa == null) {
            JOptionPane.showMessageDialog(v, "Nhập đủ thông tin môn học!");
            return;
        }

        int tc;
        try { tc = Integer.parseInt(tcStr); }
        catch (Exception ex) { JOptionPane.showMessageDialog(v, "Tín chỉ phải là số!"); return; }

        String sql = "INSERT INTO MonHoc(ma_mon, ten_mon, so_tin_chi, ma_khoa) VALUES(" +
                "'" + ma + "', N'" + ten + "', " + tc + ", '" + khoa.getMaKhoa() + "')";

        try (Connection c = ConnectDB.getConnection();
             Statement st = c.createStatement()) {

            st.executeUpdate(sql);
            taiDanhSachMonHoc(null);
            JOptionPane.showMessageDialog(v, "Thêm thành công!");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(v, "Lỗi thêm: " + ex.getMessage());
        }
    }

    private void sua() {
        String ma = v.txtMaMon.getText().trim();
        if (ma.isEmpty()) { JOptionPane.showMessageDialog(v, "Chọn môn cần sửa!"); return; }

        String ten = v.txtTenMon.getText().trim();
        String tcStr = v.txtTinChi.getText().trim();
        model_Khoa khoa = (model_Khoa) v.cboKhoa.getSelectedItem();

        int tc;
        try { tc = Integer.parseInt(tcStr); }
        catch (Exception ex) { JOptionPane.showMessageDialog(v, "Tín chỉ phải là số!"); return; }

        String sql = "UPDATE MonHoc SET " +
                "ten_mon=N'" + ten + "', " +
                "so_tin_chi=" + tc + ", " +
                "ma_khoa='" + (khoa == null ? "" : khoa.getMaKhoa()) + "'" +
                " WHERE ma_mon='" + ma + "'";

        try (Connection c = ConnectDB.getConnection();
             Statement st = c.createStatement()) {

            st.executeUpdate(sql);
            taiDanhSachMonHoc(null);
            JOptionPane.showMessageDialog(v, "Sửa thành công!");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(v, "Lỗi sửa: " + ex.getMessage());
        }
    }

    private void xoa() {
        String ma = v.txtMaMon.getText().trim();
        if (ma.isEmpty()) { JOptionPane.showMessageDialog(v, "Chọn môn cần xoá!"); return; }

        int opt = JOptionPane.showConfirmDialog(v, "Xoá môn " + ma + " ?", "Xác nhận", JOptionPane.YES_NO_OPTION);
        if (opt != JOptionPane.YES_OPTION) return;

        String sql = "DELETE FROM MonHoc WHERE ma_mon='" + ma + "'";

        try (Connection c = ConnectDB.getConnection();
             Statement st = c.createStatement()) {

            st.executeUpdate(sql);
            taiDanhSachMonHoc(null);
            lamMoi();
            JOptionPane.showMessageDialog(v, "Xoá thành công!");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(v, "Lỗi xoá: " + ex.getMessage());
        }
    }

    private void lamMoi() {
        v.txtMaMon.setText("");
        v.txtTenMon.setText("");
        v.txtTinChi.setText("");
        v.txtTim.setText("");
        v.tbl.clearSelection();
    }

    // ✅ Tim theo tu khoa + theo khoa
    private void tim() {
        String key = v.txtTim.getText().trim();
        model_Khoa khoa = (model_Khoa) v.cboKhoa.getSelectedItem();

        // điều kiện: tìm tên/mã + lọc khoa
        String where = "1=1";
        if (!key.isEmpty()) {
            where += " AND (ma_mon LIKE '%" + key + "%' OR ten_mon LIKE N'%" + key + "%')";
        }
        if (khoa != null) {
            where += " AND ma_khoa='" + khoa.getMaKhoa() + "'";
        }

        taiDanhSachMonHoc(where);
    }

    // ✅ Export Excel
    private void xuatExcel() {
    if (v.dtm.getRowCount() == 0) {
        JOptionPane.showMessageDialog(v, "Không có dữ liệu đề xuất!");
        return;
    }

    JFileChooser fc = new JFileChooser();
    fc.setDialogTitle("Chọnn nơi lưu file Excel");
    if (fc.showSaveDialog(v) != JFileChooser.APPROVE_OPTION) return;

    File f = fc.getSelectedFile();
    String path = f.getAbsolutePath();
    if (!path.toLowerCase().endsWith(".xlsx")) path += ".xlsx";

    try (Workbook wb = new XSSFWorkbook()) {
        Sheet sheet = wb.createSheet("MonHoc");

        // Header
        Row header = sheet.createRow(0);
        header.createCell(0).setCellValue("Mã môn");
        header.createCell(1).setCellValue("Tên môn");
        header.createCell(2).setCellValue("Tín chỉ");
        header.createCell(3).setCellValue("Mã khoa");

        int r = 1;

        for (int i = 0; i < v.dtm.getRowCount(); i++) {

            // Lay gia tri an toan (null -> "")
            String maMon  = layChuoiAnToan(v.dtm.getValueAt(i, 0));
            String tenMon = layChuoiAnToan(v.dtm.getValueAt(i, 1));
            String tinChiStr = layChuoiAnToan(v.dtm.getValueAt(i, 2));
            String maKhoa = layChuoiAnToan(v.dtm.getValueAt(i, 3));

            // Neu dong trong (VD maMon rong) thi bo qua
            if (maMon.trim().isEmpty() && tenMon.trim().isEmpty() && tinChiStr.trim().isEmpty() && maKhoa.trim().isEmpty()) {
                continue;
            }

            Row row = sheet.createRow(r++);
            row.createCell(0).setCellValue(maMon);
            row.createCell(1).setCellValue(tenMon);

            // Tin chi: neu khong parse duoc thi ghi 0 (hoac ghi dang text tuy ban)
            int tinChi = 0;
            try {
                if (!tinChiStr.trim().isEmpty()) tinChi = Integer.parseInt(tinChiStr.trim());
            } catch (Exception ignore) { tinChi = 0; }

            row.createCell(2).setCellValue(tinChi);
            row.createCell(3).setCellValue(maKhoa);
        }

        // Auto size cot cho dep
        for (int c = 0; c < 4; c++) sheet.autoSizeColumn(c);

        try (FileOutputStream fos = new FileOutputStream(path)) {
            wb.write(fos);
        }

        JOptionPane.showMessageDialog(v, "Xuất thành công: " + path);
    } catch (Exception ex) {
        JOptionPane.showMessageDialog(v, "Lỗi xuất Excel: " + ex.getMessage());
        }
    }

    private String layChuoiAnToan(Object obj) {
        return (obj == null) ? "" : String.valueOf(obj);
    }

    // ✅ Import Excel (upsert: có thì update, chưa có thì insert)
    private void nhapExcel() {
        JFileChooser fc = new JFileChooser();
        fc.setDialogTitle("Chon file Excel (.xlsx)");
        if (fc.showOpenDialog(v) != JFileChooser.APPROVE_OPTION) return;

        File f = fc.getSelectedFile();

        try (FileInputStream fis = new FileInputStream(f);
             Workbook wb = new XSSFWorkbook(fis);
             Connection c = ConnectDB.getConnection();
             Statement st = c.createStatement()) {

            Sheet sheet = wb.getSheetAt(0);
            int count = 0;

            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);
                if (row == null) continue;

                String ma = row.getCell(0).getStringCellValue().trim();
                String ten = row.getCell(1).getStringCellValue().trim();
                int tc = (int) row.getCell(2).getNumericCellValue();
                String mk = row.getCell(3).getStringCellValue().trim();

                if (ma.isEmpty()) continue;

                // check exists
                String check = "SELECT COUNT(*) AS c FROM MonHoc WHERE ma_mon='" + ma + "'";
                try (ResultSet rs = st.executeQuery(check)) {
                    rs.next();
                    int exists = rs.getInt("c");

                    String sql;
                    if (exists > 0) {
                        sql = "UPDATE MonHoc SET ten_mon=N'" + ten + "', so_tin_chi=" + tc + ", ma_khoa='" + mk + "'" +
                              " WHERE ma_mon='" + ma + "'";
                    } else {
                        sql = "INSERT INTO MonHoc(ma_mon, ten_mon, so_tin_chi, ma_khoa) VALUES(" +
                              "'" + ma + "', N'" + ten + "', " + tc + ", '" + mk + "')";
                    }
                    st.executeUpdate(sql);
                    count++;
                }
            }

            taiDanhSachMonHoc(null);
            JOptionPane.showMessageDialog(v, "Nhập Excel thành công");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(v, "Lỗi nhập Excel: " + ex.getMessage());
        }
    }
}
