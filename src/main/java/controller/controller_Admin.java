/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import model.ConnectDB;
import model.Session;
import model.model_Admin;
import model.model_Taikhoan;
import model.model_Vaitro;
import view.view_Admin;
import view.view_BaoCaoDangKy;
import view.view_Dangnhap;
import view.view_QLLopHocPhan;
import view.view_QLSinhVien;
import view.view_Quanlymonhoc;

/**
 *
 * @author Dvtt
 */
public class controller_Admin {
    private final view_Admin v;

    public controller_Admin(view_Admin v) {
        this.v = v;
        
        v.btnQLLopHocPhan.addActionListener(e -> moQLLopHocPhan());
        v.btnQuanLyMonHoc.addActionListener(e -> moQuanlymonhoc());
        v.btnDangXuat.addActionListener(e -> dangXuat());
        
    }

    private void moQuanlymonhoc() {
        view_Quanlymonhoc fm = new view_Quanlymonhoc();
        new controller_Quanlymonhoc(fm);
        fm.setVisible(true);
    }
    
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
        v.dispose();
        view_Dangnhap man = new view_Dangnhap();
        new controller_Dangnhap(man);
        man.setVisible(true);
    }
}
