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
import model.model_Admin;
import view.*;

/**
 *
 * @author Dvtt
 */
public class controller_Admin {
    private final view_Admin v;
    private final model_Admin admin;

    public controller_Admin(view_Admin v,model_Admin admin) {
        this.v = v;
        this.admin = admin;
       
        v.btnQLSinhVien.addActionListener(e -> moQLSinhVien());
        v.btnQuanLyMonHoc.addActionListener(e -> moQuanlymonhoc());
        v.btnDangXuat.addActionListener(e -> dangXuat());
        v.btnQuanLyNguoiDung.addActionListener(e-> moQLNguoiDung());
        v.btnQuanLyDangKyTinChi.addActionListener(e -> moQLdangky());
        v.btnQLLopHocPhan.addActionListener(e -> moQLLopHocPhan());
    }
    private void moQLLopHocPhan(){
        view_QuanLyLopHocPhan man = new view_QuanLyLopHocPhan();
        new controller_QLLopHocPhan(man, v);
        man.setVisible(true);
    }
    private void moQuanlymonhoc() {
        view_Quanlymonhoc fm = new view_Quanlymonhoc();
        new controller_Quanlymonhoc(fm);
        fm.setVisible(true);
    }
    
    private void moQLSinhVien() {
        view_QLSinhVien man = new view_QLSinhVien();
        new controller_QLSinhVien(man);
        man.setVisible(true);
    }
    
    private void moQLNguoiDung() {
        view_QLNguoiDung man = new view_QLNguoiDung();
        new controller_QLNguoiDung(man);
        man.setVisible(true);
    }
    
    private void moQLdangky(){
        view_QLdangky man =new view_QLdangky();
        new controller_QLdangky(man);
        man.setVisible(true);
    }

    private void dangXuat() {
        v.dispose();
        view_Dangnhap man = new view_Dangnhap();
        new controller_Dangnhap(man);
        man.setVisible(true);
    }
}
