/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import javax.swing.JOptionPane;
import model.Session;
import model.model_Dangnhap;
import view.view_Admin;
import view.view_Dangnhap;
import view.view_Giangvien;
import view.view_Sinhvien;

/**
 *
 * @author Dvtt
 */
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
            view.lblMsg.setText("Nhap du thong tin!");
            return;
        }

        try {
            Session s = model.dangNhap(u, p);
            if (s == null) {
                view.lblMsg.setText("Sai tai khoan hoac mat khau!");
                return;
            }

            // PHAN QUYEN
            if ("SV".equalsIgnoreCase(s.getVaiTro())) {
                view_Sinhvien f = new view_Sinhvien();
                new controller_Sinhvien(f, s);
                f.setVisible(true);
            } else if ("GV".equalsIgnoreCase(s.getVaiTro())) {
                view_Giangvien f = new view_Giangvien();
                new controller_Giangvien(f, s);
                f.setVisible(true);
            } else { // AD
                view_Admin f = new view_Admin();
                new controller_Admin(f);
                f.setVisible(true);
            }

            view.dispose();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(view, "Loi: " + ex.getMessage());
        }
    }
}
