package view;

import java.awt.*;
import javax.swing.*;

public class view_Sinhvien extends JFrame {

   
    public JLabel lblInfo = new JLabel("Tên sinh viên");
    
    public JButton btnThongTin = new JButton("Thông tin sinh viên");
    public JButton btnDangKy = new JButton("Đăng ký môn học");
    public JButton btndadangky = new JButton("Môn học đã đăng ký");
    public JButton btndsmonhoc =new JButton("Danh sách môn học");
    public JButton btnDangXuat = new JButton("Đăng xuất");

    
    public JPanel panelContent = new JPanel(new BorderLayout());

    public view_Sinhvien() {

        setTitle("Sinh viên");
        setSize(1200, 650);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel top = new JPanel(new BorderLayout());
        top.setBackground(new Color(170, 195, 220));
        top.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));

        lblInfo.setFont(new Font("Arial", Font.BOLD, 16));
        top.add(lblInfo, BorderLayout.EAST);

        add(top, BorderLayout.NORTH);

        JPanel menu = new JPanel();
        menu.setPreferredSize(new Dimension(220, 0));
        menu.setLayout(new BoxLayout(menu, BoxLayout.Y_AXIS));
        menu.setBackground(new Color(200, 220, 235));
        menu.setBorder(BorderFactory.createEmptyBorder(15, 10, 15, 10));

        Dimension btnSize = new Dimension(200, 36);
        setupMenuButton(btnThongTin, btnSize);
        setupMenuButton(btnDangKy, btnSize);
        setupMenuButton(btndadangky, btnSize);
        setupMenuButton(btndsmonhoc,btnSize);
        setupMenuButton(btnDangXuat, btnSize);

        menu.add(btnThongTin);
        menu.add(Box.createVerticalStrut(10));
        menu.add(btnDangKy);
        menu.add(Box.createVerticalStrut(10));
        menu.add(btndadangky);
        menu.add(Box.createVerticalStrut(10));
        menu.add(btndsmonhoc);
        menu.add(Box.createVerticalGlue());
        menu.add(btnDangXuat);

        add(menu, BorderLayout.WEST);

        panelContent.setBackground(Color.WHITE);
        panelContent.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        add(panelContent, BorderLayout.CENTER);
    }

    private void setupMenuButton(JButton btn, Dimension size) {
        btn.setMaximumSize(size);
        btn.setAlignmentX(Component.CENTER_ALIGNMENT);
        btn.setFocusPainted(false);
        btn.setFont(new Font("Arial", Font.PLAIN, 14));
    }
}
