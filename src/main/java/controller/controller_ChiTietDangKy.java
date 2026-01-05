package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import model.ConnectDB;
import view.view_ChiTietDangKy;

public class controller_ChiTietDangKy {

    private final view_ChiTietDangKy view;

    public controller_ChiTietDangKy(view_ChiTietDangKy v) {
        this.view = v;

        loadBang();
        loadComboMaLHP();

        view.cbMaLHP.addActionListener(e -> loadTheoMaLHP());
        view.tblChiTietDangKy.getSelectionModel()
                .addListSelectionListener(e -> dayLenForm());


        view.btnTimKiem.addActionListener(e -> timKiem());
        view.btnLamMoi.addActionListener(e -> lamMoi());
        view.btnSua.addActionListener(e -> suaDangKy());
        view.btnHuy.addActionListener(e -> huyDangKy());
        view.btnDong.addActionListener(e -> view.dispose());
    }


    private void loadBang() {
        DefaultTableModel model =
                (DefaultTableModel) view.tblChiTietDangKy.getModel();
        model.setRowCount(0);

        String sql = """
            SELECT 
                lhp.ma_lhp,
                mh.ma_mon,
                mh.ten_mon,
                mh.so_tin_chi,
                sv.lop,
                lhp.thu,
                lhp.ca_hoc,
                lhp.phong_hoc,
                lhp.ma_hoc_ky
            FROM DangKyTinChi dk
            JOIN SinhVien sv ON dk.ma_sv = sv.ma_sv
            JOIN LopHocPhan lhp ON dk.ma_lhp = lhp.ma_lhp
            JOIN MonHoc mh ON lhp.ma_mon = mh.ma_mon
            GROUP BY
                lhp.ma_lhp, mh.ma_mon, mh.ten_mon, mh.so_tin_chi,
                sv.lop, lhp.thu, lhp.ca_hoc, lhp.phong_hoc, lhp.ma_hoc_ky
        """;

        try (Connection con = ConnectDB.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                model.addRow(new Object[]{
                        rs.getString("ma_lhp"),
                        rs.getString("ma_mon"),
                        rs.getString("ten_mon"),
                        rs.getInt("so_tin_chi"),
                        rs.getString("lop"),
                        rs.getString("thu"),
                        rs.getString("ca_hoc"),
                        rs.getString("phong_hoc"),
                        rs.getString("ma_hoc_ky")
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadComboMaLHP() {
        view.cbMaLHP.removeAllItems();

        String sql = "SELECT ma_lhp FROM LopHocPhan";

        try (Connection con = ConnectDB.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                view.cbMaLHP.addItem(rs.getString("ma_lhp"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadTheoMaLHP() {
        if (view.cbMaLHP.getSelectedItem() == null) return;

        String maLHP = view.cbMaLHP.getSelectedItem().toString();

        String sql = """
            SELECT 
                mh.ma_mon,
                mh.ten_mon,
                lhp.thu,
                lhp.ca_hoc,
                lhp.phong_hoc,
                lhp.ma_hoc_ky
            FROM LopHocPhan lhp
            JOIN MonHoc mh ON lhp.ma_mon = mh.ma_mon
            WHERE lhp.ma_lhp = ?
        """;

        try (Connection con = ConnectDB.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, maLHP);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                view.txtMaMon.setText(rs.getString("ma_mon"));
                view.txtTenMon.setText(rs.getString("ten_mon"));
                view.txtPhong.setText(rs.getString("phong_hoc"));
                view.txtHocKy.setText(rs.getString("ma_hoc_ky"));

                view.txtLichhoc.setText(
                        rs.getString("thu") + " - " + rs.getString("ca_hoc")
                );
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void dayLenForm() {
        int row = view.tblChiTietDangKy.getSelectedRow();
        if (row == -1) return;

        String maMon = view.tblChiTietDangKy.getValueAt(row, 1).toString();
        String maLHP = view.tblChiTietDangKy.getValueAt(row, 0).toString();

        // ĐẨY THÔNG TIN LÊN FORM
        view.txtMaMon.setText(maMon);
        view.txtTenMon.setText(view.tblChiTietDangKy.getValueAt(row, 2).toString());
        view.txtLop.setText(view.tblChiTietDangKy.getValueAt(row, 4).toString());
        view.txtPhong.setText(view.tblChiTietDangKy.getValueAt(row, 7).toString());
        view.txtHocKy.setText(view.tblChiTietDangKy.getValueAt(row, 8).toString());

        String thu = view.tblChiTietDangKy.getValueAt(row, 5).toString();
        String ca  = view.tblChiTietDangKy.getValueAt(row, 6).toString();
        view.txtLichhoc.setText(thu + " - " + ca);

        loadComboMaLHPTheoMaMon(maMon);

        view.cbMaLHP.setSelectedItem(maLHP);
    }

    private void loadComboMaLHPTheoMaMon(String maMon) {
        view.cbMaLHP.removeAllItems();

        String sql = """
            SELECT ma_lhp
            FROM LopHocPhan
            WHERE ma_mon = ?
        """;

        try (Connection con = ConnectDB.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, maMon);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                view.cbMaLHP.addItem(rs.getString("ma_lhp"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void timKiem() {
        String key = view.txtTimKiem.getText().trim();
        DefaultTableModel model =
                (DefaultTableModel) view.tblChiTietDangKy.getModel();
        model.setRowCount(0);

        String sql = """
            SELECT 
                lhp.ma_lhp,
                mh.ma_mon,
                mh.ten_mon,
                mh.so_tin_chi,
                sv.lop,
                lhp.thu,
                lhp.ca_hoc,
                lhp.phong_hoc,
                lhp.ma_hoc_ky
            FROM DangKyTinChi dk
            JOIN SinhVien sv ON dk.ma_sv = sv.ma_sv
            JOIN LopHocPhan lhp ON dk.ma_lhp = lhp.ma_lhp
            JOIN MonHoc mh ON lhp.ma_mon = mh.ma_mon
            WHERE lhp.ma_lhp LIKE ?
               OR mh.ma_mon LIKE ?
               OR mh.ten_mon LIKE ?
               OR sv.lop LIKE ?
            GROUP BY
                lhp.ma_lhp, mh.ma_mon, mh.ten_mon, mh.so_tin_chi,
                sv.lop, lhp.thu, lhp.ca_hoc, lhp.phong_hoc, lhp.ma_hoc_ky
        """;

        try (Connection con = ConnectDB.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            for (int i = 1; i <= 4; i++) {
                ps.setString(i, "%" + key + "%");
            }

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                model.addRow(new Object[]{
                        rs.getString("ma_lhp"),
                        rs.getString("ma_mon"),
                        rs.getString("ten_mon"),
                        rs.getInt("so_tin_chi"),
                        rs.getString("lop"),
                        rs.getString("thu"),
                        rs.getString("ca_hoc"),
                        rs.getString("phong_hoc"),
                        rs.getString("ma_hoc_ky")
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void lamMoi() {
        view.cbMaLHP.setSelectedIndex(-1);
        view.txtMaMon.setText("");
        view.txtTenMon.setText("");
        view.txtLop.setText("");
        view.txtPhong.setText("");
        view.txtHocKy.setText("");
        view.txtLichhoc.setText("");
        view.txtTimKiem.setText("");

        loadBang();
    }


    private void suaDangKy() {
        int row = view.tblChiTietDangKy.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(view, "Chọn dòng cần sửa");
            return;
        }

        String maLHP_Moi = view.cbMaLHP.getSelectedItem().toString();
        String lop = view.txtLop.getText();
        String phong = view.txtPhong.getText();
        String maHK = view.txtHocKy.getText();

        String lich = view.txtLichhoc.getText();
        String thu = lich.split(" - ")[0];
        String ca  = lich.split(" - ")[1];

        if (daCoLopDangKy(maLHP_Moi, lop)) {
            JOptionPane.showMessageDialog(
                    view,
                    "❌ Lớp học phần này đã được đăng ký cho lớp khác",
                    "Lỗi nghiệp vụ",
                    JOptionPane.ERROR_MESSAGE
            );
            return;
        }

        if (biTrungPhongCa(phong, thu, ca, maHK, maLHP_Moi)) {
            JOptionPane.showMessageDialog(
                    view,
                    "❌ Trùng phòng – trùng ca học",
                    "Lỗi",
                    JOptionPane.ERROR_MESSAGE
            );
            return;
        }

        String sql = """
            UPDATE DangKyTinChi dk
            JOIN SinhVien sv ON dk.ma_sv = sv.ma_sv
            SET dk.ma_lhp = ?
            WHERE sv.lop = ?
        """;

        try (Connection con = ConnectDB.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, maLHP_Moi);
            ps.setString(2, lop);

            ps.executeUpdate();

            JOptionPane.showMessageDialog(view, "✅ Đã đổi lớp học phần cho cả lớp");

            loadBang();
            if (view.tblChiTietDangKy.getRowCount() > 0) {
                view.tblChiTietDangKy.setRowSelectionInterval(0, 0);
            }

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(view, "❌ Lỗi khi sửa đăng ký");
        }
    }


    private void huyDangKy() {
        int row = view.tblChiTietDangKy.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(view, "Chọn dòng cần hủy");
            return;
        }

        String maLHP = view.tblChiTietDangKy.getValueAt(row, 0).toString();

        int confirm = JOptionPane.showConfirmDialog(
                view,
                "Bạn có chắc muốn hủy đăng ký LHP " + maLHP + " ?",
                "Xác nhận",
                JOptionPane.YES_NO_OPTION
        );

        if (confirm != JOptionPane.YES_OPTION) return;

        String sql = "DELETE FROM DangKyTinChi WHERE ma_lhp = ?";

        try (Connection con = ConnectDB.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, maLHP);
            ps.executeUpdate();
            JOptionPane.showMessageDialog(view, "Đã hủy đăng ký");
            loadBang();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean daCoLopDangKy(String maLHP, String lopHienTai) {
        String sql = """
            SELECT COUNT(*)
            FROM DangKyTinChi dk
            JOIN SinhVien sv ON dk.ma_sv = sv.ma_sv
            WHERE dk.ma_lhp = ?
              AND sv.lop <> ?
        """;

        try (Connection con = ConnectDB.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, maLHP);
            ps.setString(2, lopHienTai);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    private boolean biTrungPhongCa(
            String phong, String thu, String ca, String maHK, String maLHP) {

        String sql = """
            SELECT COUNT(*)
            FROM LopHocPhan
            WHERE phong_hoc = ?
              AND thu = ?
              AND ca_hoc = ?
              AND ma_hoc_ky = ?
              AND ma_lhp <> ?
        """;

        try (Connection con = ConnectDB.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, phong);
            ps.setString(2, thu);
            ps.setString(3, ca);
            ps.setString(4, maHK);
            ps.setString(5, maLHP);

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