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

    
    private void dayLenForm() {
        int row = view.tblChiTietDangKy.getSelectedRow();
        if (row == -1) return;

        view.txtMaLHP.setText(view.tblChiTietDangKy.getValueAt(row, 0).toString());
        view.txtMaMon.setText(view.tblChiTietDangKy.getValueAt(row, 1).toString());
        view.txtTenMon.setText(view.tblChiTietDangKy.getValueAt(row, 2).toString());
        view.txtLop.setText(view.tblChiTietDangKy.getValueAt(row, 4).toString());
        view.txtPhong.setText(view.tblChiTietDangKy.getValueAt(row, 7).toString());
        view.txtHocKy.setText(view.tblChiTietDangKy.getValueAt(row, 8).toString());

        String thu = view.tblChiTietDangKy.getValueAt(row, 5).toString();
        String ca = view.tblChiTietDangKy.getValueAt(row, 6).toString();
        view.txtLichhoc.setText(thu + " - " + ca);
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
        view.txtMaLHP.setText("");
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

        String maLHP = view.tblChiTietDangKy.getValueAt(row, 0).toString();
        String phong = view.tblChiTietDangKy.getValueAt(row, 7).toString();
        String thu = view.tblChiTietDangKy.getValueAt(row, 5).toString();
        String ca = view.tblChiTietDangKy.getValueAt(row, 6).toString();
        String maHK = view.tblChiTietDangKy.getValueAt(row, 8).toString();

        if (biTrungPhongCa(phong, thu, ca, maHK, maLHP)) {
            JOptionPane.showMessageDialog(
                    view,
                    "❌ Trùng phòng – trùng ca học",
                    "Lỗi",
                    JOptionPane.ERROR_MESSAGE
            );
            return;
        }

        JOptionPane.showMessageDialog(
                view,
                "✅ Sửa thành công"
        );
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
