/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import model.Session;
import view.view_Dangnhap;
import view.view_Sinhvien;
import model.ConnectDB;
import java.awt.*;
import javax.swing.*;
import view.view_DSmonhoc;
import view.view_ThoiKhoaBieu;
import view.view_dangky;
import view.view_dsmonhocdadangky;

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

        String maSV = session.getMaLienKet();
        String tenSV = getTenSinhVien(maSV);

        view.btnThongTin.addActionListener(e -> moThongTinSinhVien());
        view.btnDangKy.addActionListener(e -> moDangKyMonHoc());
        view.btnDaDangKy.addActionListener(e -> moMonHocDaDangKy());
        view.btnDsMonHoc.addActionListener(e -> moDanhSachMonHoc());
        view.btnDangXuat.addActionListener(e -> dangXuat());
        view.btnThoiKhoaBieu.addActionListener(e -> moThoiKhoaBieu());

    }
    private String getTenSinhVien(String maSV) {
    try (var c = ConnectDB.getConnection()) {
        String sql = "SELECT ho_ten FROM SinhVien WHERE ma_sv = ?";
        var ps = c.prepareStatement(sql);
        ps.setString(1, maSV);
        var rs = ps.executeQuery();
        if (rs.next()) {
            return rs.getString("ho_ten");
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
    return "";
}
    
    private void moDangKyMonHoc() {
        view_dangky pnl = new view_dangky();
        new controller_dangky(pnl, session); 
        loadView(pnl);
    }
    private void loadView(JPanel panel) {
        view.panelContent.removeAll();
        view.panelContent.add(panel, BorderLayout.CENTER);
        view.panelContent.revalidate();
        view.panelContent.repaint();
    }
    
    private void moThongTinSinhVien() {
        
    }
    
    private void moMonHocDaDangKy() {
        view_dsmonhocdadangky pnl = new view_dsmonhocdadangky();
        new controller_dsmonhocdadangky(pnl, session); 
        loadView(pnl);
    }
    
    private void moDanhSachMonHoc() {
    view_DSmonhoc pnl = new view_DSmonhoc();
        new controller_DSmonhoc(pnl,session); 
        loadView(pnl);
    }
    
    private void moThoiKhoaBieu() {
    view_ThoiKhoaBieu pnl = new view_ThoiKhoaBieu();
        new controller_ThoiKhoaBieu(pnl, session);
        loadView(pnl);
    }


    

    private void dangXuat() {
        view.dispose();
        view_Dangnhap man = new view_Dangnhap();
        new controller_Dangnhap(man);   
        man.setVisible(true);
    }  
}