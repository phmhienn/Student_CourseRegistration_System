/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import model.Session;
import view.view_Dangnhap;
import view.view_Giangvien;

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

        view.lblInfo.setText("Tai khoan: " + session.getTenDangNhap()
                + " | Vai tro: GV | Ma lien ket: " + session.getMaLienKet());

        view.btnDangXuat.addActionListener(e -> dangXuat());
    }

    private void dangXuat() {
        view.dispose();
        view_Dangnhap man = new view_Dangnhap();
        new controller_Dangnhap(man);
        man.setVisible(true);
    }
}
