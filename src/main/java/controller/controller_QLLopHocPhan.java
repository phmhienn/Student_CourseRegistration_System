package controller;
import java.sql.*;
import model.ConnectDB;
import view.view_QLLopHocPhan;
public class controller_QLLopHocPhan {
    private final view_QLLopHocPhan view;

    public controller_QLLopHocPhan(view_QLLopHocPhan view) {
        this.view = view;

        loadHocKy();
        loadTrangThai();
        loadTable();
    }

    // ================== LOAD HỌC KỲ ==================
    private void loadHocKy() {
        try (Connection con = ConnectDB.getConnection()) {

            String sql = "SELECT ma_hoc_ky FROM HocKy";
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            view.cboHocKy.removeAllItems();
            while (rs.next()) {
                view.cboHocKy.addItem(rs.getString("ma_hoc_ky"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // ================== LOAD TRẠNG THÁI ==================
    private void loadTrangThai() {
        view.cboTrangThai.removeAllItems();
        view.cboTrangThai.addItem("Mở");
        view.cboTrangThai.addItem("Đóng");
    }
    private void loadTable() {
        try (Connection con = ConnectDB.getConnection()) {

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
            """;

            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            view.tableModel.setRowCount(0);

            while (rs.next()) {
                Object[] row = {
                        rs.getString("ma_lhp"),
                        rs.getString("ma_mon"),
                        rs.getString("ten_mon"),
                        rs.getInt("so_tin_chi"),
                        rs.getString("ma_gv"),
                        rs.getString("ma_hoc_ky"),
                        rs.getInt("so_luong_toi_da"),
                        rs.getInt("so_luong_da_dang_ky"),
                        rs.getString("trang_thai")
                };
                view.tableModel.addRow(row);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
