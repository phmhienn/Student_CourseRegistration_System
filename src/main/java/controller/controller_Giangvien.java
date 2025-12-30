/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import model.Session;
import view.view_Dangnhap;
import view.view_Giangvien;
// import các view con (sẽ tạo sau)
import view.view_QLSinhVien;
import view.view_QLLopHocPhan;
import view.view_BaoCaoDangKy;
/**
 *
 * @author Dvtt
 */
public class controller_Giangvien {
    private final view_Giangvien view;
    private final Session session;

    public controller_Giangvien(view_Giangvien view, Session session) {
        this.view = view;
        this.session = session;

        // ====== Hiển thị thông tin đăng nhập ======
        view.lblInfo.setText(
                "Tài khoản: " + session.getTenDangNhap()
                        + " | Vai trò: GIẢNG VIÊN"
                        + " | Mã liên kết: " + session.getMaLienKet()
        );

        // ====== Gán sự kiện ======
        view.btnQLSinhVien.addActionListener(e -> moQLSinhVien());
        view.btnQLLopHocPhan.addActionListener(e -> moQLLopHocPhan());
        view.btnXemBaoCao.addActionListener(e -> moBaoCaoDangKy());
        view.btnDangXuat.addActionListener(e -> dangXuat());
    }

    // ================== CÁC CHỨC NĂNG ==================

    private void moQLSinhVien() {
        view_QLSinhVien man = new view_QLSinhVien();
        man.setVisible(true);
    }

    private void moQLLopHocPhan() {
        view_QLLopHocPhan man = new view_QLLopHocPhan();
        man.setVisible(true);
    }

    private void moBaoCaoDangKy() {
        view_BaoCaoDangKy man = new view_BaoCaoDangKy();
        man.setVisible(true);
    }

    private void dangXuat() {
        view.dispose();
        view_Dangnhap man = new view_Dangnhap();
        new controller_Dangnhap(man);
        man.setVisible(true);
    }
}