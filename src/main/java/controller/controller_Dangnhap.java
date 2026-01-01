package controller;

import javax.swing.JOptionPane;
import model.Session;
import model.model_Dangnhap;
import view.view_Admin;
import view.view_Dangnhap;

public class controller_Dangnhap {

    private final view_Dangnhap view;
    private final model_Dangnhap model = new model_Dangnhap();

    public controller_Dangnhap(view_Dangnhap view) {
        this.view = view;

        view.btnLogin.addActionListener(e -> xuLyDangNhap());
        view.btnExit.addActionListener(e -> System.exit(0));
    }

    private void xuLyDangNhap() {
        String u = view.txtUser.getText().trim();
        String p = new String(view.txtPass.getPassword()).trim();

        if (u.isEmpty() || p.isEmpty()) {
            view.lblMsg.setText("Nhập đủ thông tin!");
            return;
        }

        try {
            Session s = model.dangNhap(u, p);

            if (s == null) {
                view.lblMsg.setText("Sai tài khoản hoặc mật khẩu!");
                return;
            }

            view_Admin f = new view_Admin();
            new controller_Admin(f);

            f.setVisible(true);
            view.dispose();

        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(view, "Lỗi: " + ex.getMessage());
        }
    }
}
