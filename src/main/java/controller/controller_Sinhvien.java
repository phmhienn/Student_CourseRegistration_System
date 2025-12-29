/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import model.Session;
import view.view_Dangnhap;
import view.view_Sinhvien;

/**
 *
 * @author Dvtt
 */
public class controller_Sinhvien {
    private final view_Sinhvien view;
    private final Session session;

    public controller_Sinhvien(view_Sinhvien view, Session session) {
        this.view = view;
        this.session = session;

        view.lblInfo.setText("Tai khoan: " + session.getTenDangNhap()
                + " | Vai tro: SV | Ma lien ket: " + session.getMaLienKet());

        view.btnDangXuat.addActionListener(e -> dangXuat());
    }

    private void dangXuat() {
        view.dispose();
        view_Dangnhap man = new view_Dangnhap();
        new controller_Dangnhap(man);   // DONG DONG: constructor public + package dung
        man.setVisible(true);
    }
}
