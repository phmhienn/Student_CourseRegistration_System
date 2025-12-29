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
import view.view_dangky;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;
import model.Session;

public class controller_dangky {

    private final view_dangky view;
    private Session session;

    
    private final Map<String, Integer> mapLichHoc = new HashMap<>();

    public controller_dangky(view_dangky view) {
        this.view = view;
        this.session = session;
        
        view.txtTenMon.setEnabled(false);
        view.txtSoTinChi.setEnabled(false);

        loadHocKy();
        loadLopHocPhan();
        loadMonDaDangKy();

       
        view.cbLop.addActionListener(e -> loadLichHocTheoLop());

        
        bindAutoFillMonHocByMaMon();

        view.btnDangKy.addActionListener(e -> dangKy());
        view.btnHuy.addActionListener(e -> huyDangKy());
        view.btnTim.addActionListener(e -> timMon());
    }

    
    private void loadHocKy() {
        try (Connection c = ConnectDB.getConnection()) {
            ResultSet rs = c.createStatement()
                    .executeQuery("SELECT ma_hoc_ky FROM HocKy");

            view.cbHocKy.removeAllItems();
            while (rs.next()) {
                view.cbHocKy.addItem(rs.getString("ma_hoc_ky"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    
    private void loadLopHocPhan() {
        try (Connection c = ConnectDB.getConnection()) {
            ResultSet rs = c.createStatement()
                    .executeQuery("SELECT ma_lhp FROM LopHocPhan");

            view.cbLop.removeAllItems();
            while (rs.next()) {
                view.cbLop.addItem(rs.getString("ma_lhp"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    
    private void loadLichHocTheoLop() {
        if (view.cbLop.getSelectedItem() == null) return;

        String maLHP = view.cbLop.getSelectedItem().toString();

        view.cbLichHoc.removeAllItems();
        mapLichHoc.clear();

        try (Connection c = ConnectDB.getConnection()) {
            String sql = """
                SELECT ma_tkb,CONCAT('Thứ ', thu,' | Tiết ', tiet_bat_dau, '-', (tiet_bat_dau + so_tiet - 1),' | Phòng ', phong_hoc) AS lich_hoc
                FROM ThoiKhoaBieu
                WHERE ma_lhp = ?
                ORDER BY thu, tiet_bat_dau
                """;

            PreparedStatement ps = c.prepareStatement(sql);
            ps.setString(1, maLHP);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int maTKB = rs.getInt("ma_tkb");
                String hienThi = rs.getString("lich_hoc");
                mapLichHoc.put(hienThi, maTKB);
                view.cbLichHoc.addItem(hienThi);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private boolean isTrungMonHoc(String maMon, String maHocKy) {
    try (Connection c = ConnectDB.getConnection()) {

        String sql = """
            SELECT COUNT(*)
            FROM DangKyHocPhan dk
            JOIN LopHocPhan l ON dk.ma_lhp = l.ma_lhp
            WHERE dk.ma_sv = ?
              AND l.ma_mon = ?
              AND dk.ma_hoc_ky = ?
        """;

        PreparedStatement ps = c.prepareStatement(sql);
        ps.setString(1, session.getMaLienKet());
        ps.setString(2, maMon);
        ps.setString(3, maHocKy);

        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            return rs.getInt(1) > 0;
        }

    } catch (Exception e) {
        e.printStackTrace();
    }
    return false;
}

    
    private void bindAutoFillMonHocByMaMon() {
        view.txtMaMon.getDocument().addDocumentListener(new DocumentListener() {
            private void handle() {
                String maMon = view.txtMaMon.getText().trim();

                
                if (maMon.length() < 2) {
                    view.txtTenMon.setText("");
                    view.txtSoTinChi.setText("");
                    return;
                }

                autoFillMonHoc(maMon);
            }

            @Override public void insertUpdate(DocumentEvent e) { handle(); }
            @Override public void removeUpdate(DocumentEvent e) { handle(); }
            @Override public void changedUpdate(DocumentEvent e) { handle(); }
        });
    }

    private void autoFillMonHoc(String maMon) {
        try (Connection c = ConnectDB.getConnection()) {
            String sql = "SELECT ten_mon, so_tin_chi FROM MonHoc WHERE ma_mon = ?";
            PreparedStatement ps = c.prepareStatement(sql);
            ps.setString(1, maMon);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                view.txtTenMon.setText(rs.getString("ten_mon"));
                view.txtSoTinChi.setText(String.valueOf(rs.getInt("so_tin_chi")));
            } else {
                view.txtTenMon.setText("");
                view.txtSoTinChi.setText("");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    
    private void timMon() {
        try (Connection c = ConnectDB.getConnection()) {

            String sql = """
                SELECT m.ma_mon, m.ten_mon, m.so_tin_chi,l.ma_lhp,
                CONCAT('Thứ ', t.thu,' | Tiết ',t.tiet_bat_dau, '-', (t.tiet_bat_dau + t.so_tiet - 1)) AS lich_hoc,
                l.ma_hoc_ky
                FROM MonHoc m
                JOIN LopHocPhan l ON m.ma_mon = l.ma_mon
                JOIN ThoiKhoaBieu t ON l.ma_lhp = t.ma_lhp
                WHERE m.ma_mon = ?
            """;

            PreparedStatement ps = c.prepareStatement(sql);
            ps.setString(1, view.txtMaMon.getText().trim());

            ResultSet rs = ps.executeQuery();

            view.model.setRowCount(0);
            while (rs.next()) {
                view.model.addRow(new Object[]{
                    rs.getString("ma_mon"),
                    rs.getString("ten_mon"),
                    rs.getInt("so_tin_chi"),
                    rs.getString("ma_lhp"),
                    rs.getString("lich_hoc"),
                    rs.getString("ma_hoc_ky")
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    
    private void dangKy() {
        String maMon = view.txtMaMon.getText().trim();
        String maHocKy = view.cbHocKy.getSelectedItem().toString();

        if (isTrungMonHoc(maMon, maHocKy)) {
            JOptionPane.showMessageDialog(
                view,
                "Bạn đã đăng ký môn này trong học kỳ này rồi!",
                "Trùng môn học",
                JOptionPane.WARNING_MESSAGE
            );
            return;
        }
        if (view.cbLop.getSelectedItem() == null || view.cbHocKy.getSelectedItem() == null) {
            JOptionPane.showMessageDialog(view, "Vui lòng chọn Lớp học phần và Học kỳ!");
            return;
        }
        if (view.cbLichHoc.getSelectedItem() == null) {
            JOptionPane.showMessageDialog(view, "Vui lòng chọn Lịch học!");
            return;
        }

        String maLHP = view.cbLop.getSelectedItem().toString();
        String lichText = view.cbLichHoc.getSelectedItem().toString();

        Integer maTKB = mapLichHoc.get(lichText);
        if (maTKB == null) {
            JOptionPane.showMessageDialog(view, "Không lấy được mã TKB (ma_tkb).");
            return;
        }

        try (Connection c = ConnectDB.getConnection()) {

            String sql = """
                INSERT INTO DangKyHocPhan(ma_sv, ma_lhp, ma_hoc_ky, ma_tkb, ngay_dang_ky, trang_thai) VALUES (?, ?, ?, ?, CURDATE(), 'Đăng ký')""";

            PreparedStatement ps = c.prepareStatement(sql);
            ps.setString(1, session.getMaLienKet());
            ps.setString(2, maLHP);
            ps.setString(3, maHocKy);
            ps.setInt(4, maTKB);

            ps.executeUpdate();

            JOptionPane.showMessageDialog(view, "Đăng ký thành công!");
            loadMonDaDangKy();

        } catch (SQLIntegrityConstraintViolationException dup) {
            JOptionPane.showMessageDialog(view, "Bạn đã đăng ký lớp này rồi!");
        } catch (SQLException e) {
            // Nếu lỗi do bảng DangKyHocPhan không có ma_tkb -> thông báo rõ
            JOptionPane.showMessageDialog(view, "Lỗi SQL: " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(view, "Lỗi đăng ký!");
            e.printStackTrace();
        }
    }
    private void huyDangKy() {

    int row = view.table.getSelectedRow();
    if (row == -1) {
        JOptionPane.showMessageDialog(view, "Vui lòng chọn môn cần hủy!");
        return;
    }

    int confirm = JOptionPane.showConfirmDialog(
        view,
        "Bạn có chắc chắn muốn hủy đăng ký môn này?",
        "Xác nhận",
        JOptionPane.YES_NO_OPTION
    );

    if (confirm != JOptionPane.YES_OPTION) return;

    String maLHP = view.table.getValueAt(row, 3).toString();
    String maHocKy = view.table.getValueAt(row, 5).toString();

    try (Connection c = ConnectDB.getConnection()) {

        String sql = """
            DELETE FROM DangKyHocPhan
            WHERE ma_sv = ?
              AND ma_lhp = ?
              AND ma_hoc_ky = ?
        """;

        PreparedStatement ps = c.prepareStatement(sql);
        ps.setString(1, session.getMaLienKet());
        ps.setString(2, maLHP);
        ps.setString(3, maHocKy);

        ps.executeUpdate();

        JOptionPane.showMessageDialog(view, "Hủy đăng ký thành công!");

        
        loadMonDaDangKy();

    } catch (Exception e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(view, "Lỗi hủy đăng ký!");
    }
}

    
    private void clear() {
        view.txtMaMon.setText("");
        view.txtTenMon.setText("");
        view.txtSoTinChi.setText("");

        view.model.setRowCount(0);
    }
    private void loadMonDaDangKy() {
    try (Connection c = ConnectDB.getConnection()) {

        String sql = """
            SELECT m.ma_mon, m.ten_mon, m.so_tin_chi,l.ma_lhp,
            CONCAT('Thứ ', t.thu,' | Tiết ', t.tiet_bat_dau, '-', (t.tiet_bat_dau + t.so_tiet - 1),' | Phòng ', t.phong_hoc)
            AS lich_hoc,dk.ma_hoc_ky
            FROM DangKyHocPhan dk
            JOIN LopHocPhan l ON dk.ma_lhp = l.ma_lhp
            JOIN MonHoc m ON l.ma_mon = m.ma_mon
            JOIN ThoiKhoaBieu t ON dk.ma_tkb = t.ma_tkb
            WHERE dk.ma_sv = ?
            ORDER BY dk.ngay_dang_ky DESC
        """;

        PreparedStatement ps = c.prepareStatement(sql);
        ps.setString(1, session.getMaLienKet());

        ResultSet rs = ps.executeQuery();

        view.model.setRowCount(0); 

        while (rs.next()) {
            view.model.addRow(new Object[]{
                rs.getString("ma_mon"),
                rs.getString("ten_mon"),
                rs.getInt("so_tin_chi"),
                rs.getString("ma_lhp"),
                rs.getString("lich_hoc"),
                rs.getString("ma_hoc_ky")
            });
        }

    } catch (Exception e) {
        e.printStackTrace();
    }
}
  
}

