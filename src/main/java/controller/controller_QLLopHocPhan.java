package controller;
import java.sql.*;
import model.ConnectDB;
import view.view_QLLopHocPhan;

import javax.swing.*;

public class controller_QLLopHocPhan {
    private final view_QLLopHocPhan view;


    public controller_QLLopHocPhan(view_QLLopHocPhan view) {
        this.view = view;
        
        view.btnQuayLai.addActionListener(e -> view.dispose());
        view.btnThem.addActionListener(e -> themLopHocPhan());
        view.btnSua.addActionListener(e -> suaLopHocPhan());
        view.btnXoa.addActionListener(e -> xoaLopHocPhan());
        view.btnLamMoi.addActionListener(e -> resetForm());

        loadHocKy();
        loadTrangThai();
        loadTable();
    }

    // ================== LOAD HỌC KỲ ==================
    private void loadHocKy() {
        String sql = "SELECT ma_hoc_ky FROM HocKy ORDER BY ma_hoc_ky";

        try (Connection con = ConnectDB.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            view.cboHocKy.removeAllItems();
            view.cboHocKy.addItem(""); // item trống (chưa chọn)

            while (rs.next()) {
                view.cboHocKy.addItem(rs.getString("ma_hoc_ky"));
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(
                    view,
                    "Lỗi load học kỳ!",
                    "Lỗi",
                    JOptionPane.ERROR_MESSAGE
            );
            e.printStackTrace();
        }
    }


    // ================== LOAD TRẠNG THÁI ==================
    private void loadTrangThai() {
        view.cboTrangThai.removeAllItems();
        view.cboTrangThai.addItem("");     // chưa chọn
        view.cboTrangThai.addItem("Mở");
        view.cboTrangThai.addItem("Đóng");
        view.cboTrangThai.addItem("Huỷ");
    }

    private void loadTable() {
        String sql = """
        SELECT 
            lhp.ma_lhp,
            lhp.ma_mon,
            mh.ten_mon,
            mh.so_tin_chi,
            lhp.ma_gv,
            lhp.ma_hoc_ky,
            lhp.so_luong_toi_da,
            lhp.so_luong_da_dang_ky,
            lhp.trang_thai
        FROM LopHocPhan lhp
        JOIN MonHoc mh ON lhp.ma_mon = mh.ma_mon
        ORDER BY lhp.ma_hoc_ky, lhp.ma_lhp
    """;

        try (Connection con = ConnectDB.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            view.tableModel.setRowCount(0);

            while (rs.next()) {
                Object[] row = new Object[] {
                        rs.getString("ma_lhp"),               // Mã LHP
                        rs.getString("ma_mon"),               // Mã môn
                        rs.getString("ten_mon"),              // Tên môn
                        rs.getInt("so_tin_chi"),              // Số TC
                        rs.getString("ma_gv"),                // Mã GV
                        rs.getString("ma_hoc_ky"),            // Học kỳ
                        rs.getInt("so_luong_toi_da"),          // SL tối đa
                        rs.getInt("so_luong_da_dang_ky"),      // Đã ĐK
                        rs.getString("trang_thai")             // Trạng thái
                };
                view.tableModel.addRow(row);
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(
                    view,
                    "Lỗi load dữ liệu lớp học phần!",
                    "Lỗi",
                    JOptionPane.ERROR_MESSAGE
            );
            e.printStackTrace();
        }
    }
    private void themLopHocPhan() {

        // 1️⃣ LẤY DỮ LIỆU TỪ FORM
        String maLHP = view.txtMaLHP.getText().trim();
        String maMon = view.txtMaMon.getText().trim();
        String maGV  = view.txtMaGV.getText().trim();
        String hocKy = (String) view.cboHocKy.getSelectedItem();
        String trangThai = (String) view.cboTrangThai.getSelectedItem();

        // 2️⃣ VALIDATE RỖNG
        if (maLHP.isEmpty() || maMon.isEmpty() || maGV.isEmpty()
                || hocKy == null || hocKy.isEmpty()
                || trangThai == null || trangThai.isEmpty()
                || view.txtSoLuongToiDa.getText().trim().isEmpty()) {

            JOptionPane.showMessageDialog(
                    view,
                    "Vui lòng nhập đầy đủ thông tin!",
                    "Thiếu dữ liệu",
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        int soLuongToiDa;
        try {
            soLuongToiDa = Integer.parseInt(view.txtSoLuongToiDa.getText().trim());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(
                    view,
                    "Số lượng tối đa phải là số!",
                    "Lỗi",
                    JOptionPane.ERROR_MESSAGE
            );
            return;
        }

        try (Connection con = ConnectDB.getConnection()) {

            // 3️⃣ CHECK TRÙNG MÃ LỚP
            if (isExist(con, "SELECT 1 FROM LopHocPhan WHERE ma_lhp = ?", maLHP)) {
                JOptionPane.showMessageDialog(view, "Mã lớp học phần đã tồn tại!");
                return;
            }

            // 4️⃣ CHECK MÃ MÔN
            if (!isExist(con, "SELECT 1 FROM MonHoc WHERE ma_mon = ?", maMon)) {
                JOptionPane.showMessageDialog(view, "Mã môn học không tồn tại!");
                return;
            }

            // 5️⃣ CHECK MÃ GIẢNG VIÊN
            if (!isExist(con, "SELECT 1 FROM GiangVien WHERE ma_gv = ?", maGV)) {
                JOptionPane.showMessageDialog(view, "Mã giảng viên không tồn tại!");
                return;
            }

            // 6️⃣ INSERT LỚP HỌC PHẦN
            String sqlInsert = """
            INSERT INTO LopHocPhan
            (ma_lhp, ma_mon, ma_gv, ma_hoc_ky,
so_luong_toi_da, so_luong_da_dang_ky, trang_thai)
            VALUES (?, ?, ?, ?, ?, 0, ?)
        """;

            try (PreparedStatement ps = con.prepareStatement(sqlInsert)) {
                ps.setString(1, maLHP);
                ps.setString(2, maMon);
                ps.setString(3, maGV);
                ps.setString(4, hocKy);
                ps.setInt(5, soLuongToiDa);
                ps.setString(6, trangThai);
                ps.executeUpdate();
            }

            JOptionPane.showMessageDialog(
                    view,
                    "Thêm lớp học phần thành công!",
                    "Thành công",
                    JOptionPane.INFORMATION_MESSAGE
            );

            loadTable();


        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(
                    view,
                    "Lỗi khi thêm lớp học phần!",
                    "Lỗi",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }
    private void suaLopHocPhan() {

        // 0️⃣ PHẢI CHỌN DÒNG
        int row = view.tblLopHocPhan.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(
                    view,
                    "Vui lòng chọn lớp học phần cần sửa!",
                    "Chưa chọn",
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        // 1️⃣ LẤY DỮ LIỆU
        String maLHP = view.txtMaLHP.getText().trim();
        String maMon = view.txtMaMon.getText().trim();
        String maGV  = view.txtMaGV.getText().trim();
        String hocKy = (String) view.cboHocKy.getSelectedItem();
        String trangThai = (String) view.cboTrangThai.getSelectedItem();

        if (maLHP.isEmpty() || maMon.isEmpty() || maGV.isEmpty()
                || hocKy == null || hocKy.isEmpty()
                || trangThai == null || trangThai.isEmpty()
                || view.txtSoLuongToiDa.getText().trim().isEmpty()) {

            JOptionPane.showMessageDialog(view, "Vui lòng nhập đầy đủ thông tin!");
            return;
        }

        int soLuongToiDa;
        try {
            soLuongToiDa = Integer.parseInt(view.txtSoLuongToiDa.getText().trim());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(view, "Số lượng tối đa phải là số!");
            return;
        }

        try (Connection con = ConnectDB.getConnection()) {

            // 2️⃣ LẤY SỐ SV ĐÃ ĐĂNG KÝ
            int daDangKy = 0;
            String sqlSL = "SELECT so_luong_da_dang_ky FROM LopHocPhan WHERE ma_lhp = ?";
            try (PreparedStatement ps = con.prepareStatement(sqlSL)) {
                ps.setString(1, maLHP);
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    daDangKy = rs.getInt(1);
                }
}

            // 3️⃣ CHECK NGHIỆP VỤ
            if (soLuongToiDa < daDangKy) {
                JOptionPane.showMessageDialog(
                        view,
                        "SL tối đa không được nhỏ hơn số SV đã đăng ký (" + daDangKy + ")!"
                );
                return;
            }

            // 4️⃣ CHECK MÃ MÔN
            if (!isExist(con, "SELECT 1 FROM MonHoc WHERE ma_mon = ?", maMon)) {
                JOptionPane.showMessageDialog(view, "Mã môn không tồn tại!");
                return;
            }

            // 5️⃣ CHECK GIẢNG VIÊN
            if (!isExist(con, "SELECT 1 FROM GiangVien WHERE ma_gv = ?", maGV)) {
                JOptionPane.showMessageDialog(view, "Mã giảng viên không tồn tại!");
                return;
            }

            // 6️⃣ UPDATE
            String sqlUpdate = """
            UPDATE LopHocPhan
            SET ma_mon = ?,
                ma_gv = ?,
                ma_hoc_ky = ?,
                so_luong_toi_da = ?,
                trang_thai = ?
            WHERE ma_lhp = ?
        """;

            try (PreparedStatement ps = con.prepareStatement(sqlUpdate)) {
                ps.setString(1, maMon);
                ps.setString(2, maGV);
                ps.setString(3, hocKy);
                ps.setInt(4, soLuongToiDa);
                ps.setString(5, trangThai);
                ps.setString(6, maLHP);
                ps.executeUpdate();
            }

            JOptionPane.showMessageDialog(view, "Cập nhật lớp học phần thành công!");
            loadTable();
            view.tblLopHocPhan.clearSelection();
            view.txtMaLHP.setEditable(true);

        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(view, "Lỗi khi sửa lớp học phần!");
        }
    }
    private void xoaLopHocPhan() {

        // 0️⃣ PHẢI CHỌN DÒNG
        int row = view.tblLopHocPhan.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(
                    view,
                    "Vui lòng chọn lớp học phần cần xoá!",
                    "Chưa chọn",
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        String maLHP = view.txtMaLHP.getText().trim();
        if (maLHP.isEmpty()) {
            JOptionPane.showMessageDialog(view, "Không xác định được mã lớp học phần!");
            return;
        }

        // 1️⃣ XÁC NHẬN
        int confirm = JOptionPane.showConfirmDialog(
                view,
                "Bạn có chắc chắn muốn xoá lớp học phần [" + maLHP + "] ?",
                "Xác nhận xoá",
                JOptionPane.YES_NO_OPTION
        );
        if (confirm != JOptionPane.YES_OPTION) return;

        try (Connection con = ConnectDB.getConnection()) {

            // 2️⃣ CHECK SỐ SV ĐÃ ĐĂNG KÝ
            int daDangKy = 0;
String sqlCheckDK = "SELECT so_luong_da_dang_ky FROM LopHocPhan WHERE ma_lhp = ?";
            try (PreparedStatement ps = con.prepareStatement(sqlCheckDK)) {
                ps.setString(1, maLHP);
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    daDangKy = rs.getInt(1);
                }
            }

            if (daDangKy > 0) {
                JOptionPane.showMessageDialog(
                        view,
                        "Không thể xoá lớp học phần đã có sinh viên đăng ký (" + daDangKy + ")!",
                        "Bị chặn",
                        JOptionPane.ERROR_MESSAGE
                );
                return;
            }

            // 3️⃣ CHECK THỜI KHOÁ BIỂU (FIX LỖI FK)
            String sqlTKB = "SELECT 1 FROM ThoiKhoaBieu WHERE ma_lhp = ?";
            try (PreparedStatement ps = con.prepareStatement(sqlTKB)) {
                ps.setString(1, maLHP);
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    JOptionPane.showMessageDialog(
                            view,
                            "Không thể xoá lớp học phần đã có thời khoá biểu!",
                            "Bị ràng buộc dữ liệu",
                            JOptionPane.ERROR_MESSAGE
                    );
                    return;
                }
            }

            // 4️⃣ XOÁ
            String sqlDelete = "DELETE FROM LopHocPhan WHERE ma_lhp = ?";
            try (PreparedStatement ps = con.prepareStatement(sqlDelete)) {
                ps.setString(1, maLHP);
                ps.executeUpdate();
            }

            JOptionPane.showMessageDialog(
                    view,
                    "Xoá lớp học phần thành công!",
                    "Thành công",
                    JOptionPane.INFORMATION_MESSAGE
            );

            // 5️⃣ LOAD LẠI
            loadTable();
            view.btnLamMoi.doClick();

        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(
                    view,
                    "Lỗi khi xoá lớp học phần!",
                    "Lỗi",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    private boolean isExist(Connection con, String sql, String value) throws SQLException {
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, value);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        }
    }

    private void resetForm() {

    // 1️⃣ XÓA TEXT FIELD
    view.txtMaLHP.setText("");
    view.txtMaMon.setText("");
    view.txtTenMon.setText("");
    view.txtSoTinChi.setText("");
    view.txtMaGV.setText("");
    view.txtSoLuongToiDa.setText("");
    view.txtSoLuongDaDK.setText("");

    // 2️⃣ RESET COMBOBOX
    view.cboHocKy.setSelectedIndex(0);     // item trống
    view.cboTrangThai.setSelectedIndex(0);

    // 3️⃣ BỎ CHỌN TABLE
    view.tblLopHocPhan.clearSelection();

    // 4️⃣ MỞ LẠI MÃ LHP (CHUẨN NGHIỆP VỤ)
    view.txtMaLHP.setEditable(true);
}
    
}