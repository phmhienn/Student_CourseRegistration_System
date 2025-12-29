/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package view;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
/**
 *
 * @author Dvtt
 */
public class view_Giangvien extends JFrame{
    public JLabel lblInfo = new JLabel("Xin chào Giảng viên", SwingConstants.CENTER);

    // ====== Buttons ======
    public JButton btnQLSinhVien = new JButton("Quản lý sinh viên");
    public JButton btnQLLopHocPhan = new JButton("Quản lý lớp học phần");
    public JButton btnXemBaoCao = new JButton("Xem báo cáo đăng ký");
    public JButton btnThongTinCaNhan = new JButton("Thông tin cá nhân");
    public JButton btnDangXuat = new JButton("Đăng xuất");

    public view_Giangvien() {
        setTitle("Trang Giảng Viên");
        setSize(500, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        // ====== Panel tiêu đề ======
        JPanel pnTop = new JPanel(new BorderLayout());
        pnTop.add(lblInfo, BorderLayout.CENTER);

        // ====== Panel chức năng ======
        JPanel pnCenter = new JPanel(new GridLayout(2, 2, 10, 10));
        pnCenter.add(btnQLSinhVien);
        pnCenter.add(btnQLLopHocPhan);
        pnCenter.add(btnXemBaoCao);
        pnCenter.add(btnThongTinCaNhan);

        // ====== Panel đăng xuất ======
        JPanel pnBottom = new JPanel();
        pnBottom.add(btnDangXuat);

        // ====== Add vào Frame ======
        add(pnTop, BorderLayout.NORTH);
        add(pnCenter, BorderLayout.CENTER);
        add(pnBottom, BorderLayout.SOUTH);
    }
}
