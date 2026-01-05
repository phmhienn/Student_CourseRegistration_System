package view;

import java.awt.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class view_QLdangky extends JFrame {

   
    public JTextField txtMaLHP = new JTextField();
    public JTextField txtMaMon = new JTextField();
    public JTextField txtTenMon = new JTextField();
    public JTextField txtSoTinChi = new JTextField();

    public JComboBox<String> cbLop = new JComboBox<>();
    public JComboBox<String> cbPhongHoc = new JComboBox<>();
    public JComboBox<String> cbLichHoc = new JComboBox<>();
    public JComboBox<String> cbHocKy = new JComboBox<>();

    public JTextField txtTimKiem = new JTextField();

    
    public JButton btnDangKy = new JButton("Đăng ký");
    public JButton btnTimKiem = new JButton("Tìm kiếm");
    public JButton btnchitietdangky = new JButton("Chi tiết đăng ký");
    public JButton btnQuaylai = new JButton("Quay lại");

    public JTable tblDangKy = new JTable();

    public view_QLdangky() {
        setTitle("Quản lý đăng ký tín chỉ");
        setSize(1100, 620);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        
        JPanel pnlHeader = new JPanel(new BorderLayout());
        pnlHeader.setPreferredSize(new Dimension(1100, 55));
        pnlHeader.setBackground(Color.WHITE);

        JLabel lblTitle = new JLabel("Quản lý đăng ký tín chỉ", SwingConstants.CENTER);
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 22));

        JPanel pnlRight = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 12));
        pnlRight.setOpaque(false);
        styleGray(btnQuaylai);
        pnlRight.add(btnQuaylai);

        pnlHeader.add(lblTitle, BorderLayout.CENTER);
        pnlHeader.add(pnlRight, BorderLayout.EAST);
        add(pnlHeader, BorderLayout.NORTH);

       
        JPanel pnlForm = new JPanel(new BorderLayout());
        pnlForm.setBorder(BorderFactory.createTitledBorder("Thông tin đăng ký"));
        pnlForm.setBackground(new Color(245, 245, 245));

        JPanel pnlInput = new JPanel(new GridLayout(2, 4, 20, 12));
        pnlInput.setBackground(new Color(245, 245, 245));

        pnlInput.add(field("Mã LHP", txtMaLHP));
        pnlInput.add(field("Mã môn", txtMaMon));
        pnlInput.add(field("Tên môn", txtTenMon));
        pnlInput.add(field("Lớp", cbLop));

        pnlInput.add(field("Số tín chỉ", txtSoTinChi));
        pnlInput.add(field("Phòng", cbPhongHoc));
        pnlInput.add(field("Lịch học", cbLichHoc));
        pnlInput.add(field("Học kỳ", cbHocKy));

        setReadonly(txtMaLHP);
        setReadonly(txtTenMon);
        setReadonly(txtSoTinChi);

        pnlForm.add(pnlInput, BorderLayout.CENTER);

        
        JPanel pnlButton = new JPanel(new FlowLayout(FlowLayout.LEFT, 12, 10));
        pnlButton.setBackground(new Color(245, 245, 245));

        styleBlue(btnDangKy);
        styleGray(btnchitietdangky);
        styleRed(btnTimKiem);

        txtTimKiem.setPreferredSize(new Dimension(220, 32));
        txtTimKiem.setBorder(BorderFactory.createLineBorder(Color.GRAY));

        pnlButton.add(btnDangKy);
        pnlButton.add(btnchitietdangky);
        pnlButton.add(Box.createHorizontalStrut(30));
        pnlButton.add(new JLabel("Từ khóa"));
        pnlButton.add(txtTimKiem);
        pnlButton.add(btnTimKiem);

        pnlForm.add(pnlButton, BorderLayout.SOUTH);

        
        tblDangKy.setRowHeight(28);
        tblDangKy.setModel(new DefaultTableModel(
            new Object[]{
                "Mã LHP", "Mã môn", "Tên môn", "Số tín chỉ",
                "Thứ", "Ca", "Phòng", "Học kỳ"
            }, 0
        ));

        JScrollPane scroll = new JScrollPane(tblDangKy);

        JPanel pnlMain = new JPanel(new BorderLayout());
        pnlMain.add(pnlForm, BorderLayout.NORTH);
        pnlMain.add(scroll, BorderLayout.CENTER);

        add(pnlMain, BorderLayout.CENTER);
    }

    
    private JPanel field(String label, JComponent field) {
        JLabel lb = new JLabel(label);
        lb.setPreferredSize(new Dimension(80, 22));

        field.setPreferredSize(new Dimension(200, 32));

        JPanel p = new JPanel(new BorderLayout(5, 4));
        p.setBackground(new Color(245, 245, 245));

        p.add(lb, BorderLayout.NORTH);
        p.add(field, BorderLayout.CENTER);

        return p;
    }

    
    private void setReadonly(JTextField txt) {
        txt.setEditable(false);
        txt.setBackground(Color.WHITE);
        txt.setBorder(BorderFactory.createLineBorder(Color.GRAY));
    }

    private void styleBlue(JButton b) {
        b.setBackground(new Color(45, 107, 203));
        b.setForeground(Color.WHITE);
        b.setOpaque(true);
        b.setBorderPainted(false);
        b.setFocusPainted(false);
        b.setPreferredSize(new Dimension(110, 32));
    }

    private void styleRed(JButton b) {
        b.setBackground(new Color(217, 48, 48));
        b.setForeground(Color.WHITE);
        b.setOpaque(true);
        b.setBorderPainted(false);
        b.setFocusPainted(false);
        b.setPreferredSize(new Dimension(110, 32));
    }

    private void styleGray(JButton b) {
        b.setBackground(new Color(220, 220, 220));
        b.setForeground(Color.BLACK);
        b.setOpaque(true);
        b.setBorderPainted(false);
        b.setFocusPainted(false);
        b.setPreferredSize(new Dimension(140, 32));
    }
}