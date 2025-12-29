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

        view.lblInfo.setText("Tai khoan: " + session.getTenDangNhap()
            + " | Vai tro: " + session.getVaiTro()
            + " | Ma lien ket: " + session.getMaLienKet());
        
        String maSV = session.getMaLienKet();
        String tenSV = getTenSinhVien(maSV);

        view.lblInfo.setText(tenSV);

        view.btnThongTin.addActionListener(e -> moThongTinSinhVien());
        view.btnDangKy.addActionListener(e -> moDangKyMonHoc());
        view.btnDaDangKy.addActionListener(e -> moMonHocDaDangKy());
        view.btnDsMonHoc.addActionListener(e -> moDanhSachMonHoc());
        view.btnDangXuat.addActionListener(e -> dangXuat());
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
        loadView(new view_dangky());
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
        loadView(new view_dsmonhocdadangky());
    }
    
    private void moDanhSachMonHoc() {
    loadView(new view_DSmonhoc());
}
    
    

    private void dangXuat() {
        view.dispose();
        view_Dangnhap man = new view_Dangnhap();
        new controller_Dangnhap(man);   
        man.setVisible(true);
    }  
}
