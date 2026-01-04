package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JOptionPane;
import static model.ConnectDB.getConnection;
import model.model_Admin;
import view.view_Admin;
import view.view_Dangnhap;

public class controller_Dangnhap {

     private final view_Dangnhap view;

    public controller_Dangnhap(view_Dangnhap view) {
        this.view = view;
        view.btnLogin.addActionListener(e -> dangNhap());
    }
    
    private void dangNhap() {
        String user = view.txtUser.getText().trim();
        String pass = new String(view.txtPass.getPassword()).trim();

        if (user.isEmpty() || pass.isEmpty()) {
            view.lblMsg.setText("Nhập đủ thông tin!");
            return;
        }

        String sql = """
            SELECT admin_id, username, ho_ten
            FROM Admin
            WHERE username = ?
              AND password = ?
              AND trang_thai = 1
            """;

        try (
            Connection con = getConnection();
            PreparedStatement ps = con.prepareStatement(sql)
        ) {
            ps.setString(1, user);
            ps.setString(2, pass);

            ResultSet rs = ps.executeQuery();
            if (!rs.next()) {
                view.lblMsg.setText("Sai tài khoản hoặc mật khẩu!");
                return;
            }

            model_Admin admin = new model_Admin();
            admin.setAdminId(rs.getInt("admin_id"));
            admin.setUsername(rs.getString("username"));
            admin.setHoTen(rs.getString("ho_ten"));

            view_Admin f = new view_Admin();
            new controller_Admin(f, admin);
            f.setVisible(true);
            view.dispose();

        } catch (Exception ex) {
            ex.printStackTrace();
            view.lblMsg.setText("Lỗi hệ thống!");
        }
    }
}
