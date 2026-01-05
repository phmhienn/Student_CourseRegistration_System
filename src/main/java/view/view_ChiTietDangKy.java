package view;

import java.awt.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class view_ChiTietDangKy extends JFrame {

   
    public JComboBox<String> cbMaLHP = new JComboBox<>();
    public JTextField txtMaMon = new JTextField();
    public JTextField txtTenMon = new JTextField();
    public JTextField txtHocKy = new JTextField();
    public JTextField txtLop = new JTextField();
    public JTextField txtPhong = new JTextField();
    public JTextField txtLichhoc = new JTextField();
    public JTextField txtTimKiem = new JTextField();
    
    public JButton btnSua = new JButton("Sửa");
    public JButton btnHuy = new JButton("Hủy");
    public JButton btnLamMoi = new JButton("Làm mới");
    public JButton btnTimKiem = new JButton("Tìm kiếm");
    public JButton btnDong = new JButton("Quay lại");

    public JTable tblChiTietDangKy = new JTable();

    public view_ChiTietDangKy() {
        setTitle("Chi tiết đăng ký lớp học phần");
        setSize(1100, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        
        JPanel pnlHeader = new JPanel(new BorderLayout());
        pnlHeader.setPreferredSize(new Dimension(1100, 55));
        pnlHeader.setBackground(Color.WHITE);

        JLabel lblTitle = new JLabel("Chi tiết đăng ký lớp học phần", SwingConstants.CENTER);
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 22));

        JPanel pnlRight = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 12));
        pnlRight.setOpaque(false);
        btnDong.setBackground(new Color(230, 230, 230));
        pnlRight.add(btnDong);

        pnlHeader.add(lblTitle, BorderLayout.CENTER);
        pnlHeader.add(pnlRight, BorderLayout.EAST);

        add(pnlHeader, BorderLayout.NORTH);

        
        JPanel pnlForm = new JPanel(new BorderLayout());
        pnlForm.setBorder(BorderFactory.createTitledBorder("Thông tin lớp học phần"));
        pnlForm.setBackground(new Color(245, 245, 245));

        JPanel pnlInput = new JPanel(new GridLayout(3, 4, 20, 12));
        pnlInput.setBackground(new Color(245, 245, 245));

        pnlInput.add(fieldCombo("Mã LHP", cbMaLHP));
        pnlInput.add(field("Mã môn", txtMaMon));
        pnlInput.add(field("Tên môn", txtTenMon));
        pnlInput.add(field("Lớp", txtLop));
        
        pnlInput.add(field("Học kỳ", txtHocKy));
        pnlInput.add(field("Phòng", txtPhong));
        pnlInput.add(field("Lịch học", txtLichhoc));
        pnlInput.add(new JLabel());

        
        setReadonly(txtMaMon);
        setReadonly(txtTenMon);
        setReadonly(txtHocKy);
        setReadonly(txtLop);
        setReadonly(txtPhong);
        setReadonly(txtLichhoc);

        pnlForm.add(pnlInput, BorderLayout.CENTER);

       
        JPanel pnlButton = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 10));
        pnlButton.setBackground(new Color(245, 245, 245));
        txtTimKiem.setPreferredSize(new Dimension(220, 32));
        txtTimKiem.setBorder(BorderFactory.createLineBorder(Color.GRAY));

        styleBlue(btnSua);
        styleRed(btnHuy);
        styleGray(btnLamMoi);
        styleBlue(btnTimKiem);

        pnlButton.add(btnSua);
        pnlButton.add(btnHuy);
        pnlButton.add(btnLamMoi);
        pnlButton.add(new JLabel("Từ khóa"));
        pnlButton.add(txtTimKiem);
        pnlButton.add(btnTimKiem);

        pnlForm.add(pnlButton, BorderLayout.SOUTH);

     
        tblChiTietDangKy.setRowHeight(28);
        tblChiTietDangKy.setModel(new DefaultTableModel(
            new Object[]{
                "Mã LHP", "Mã môn", "Tên môn", "Số tín chỉ",
                "Lớp", "Thứ", "Ca", "Phòng", "Học kỳ"
            }, 0
        ));

        JScrollPane scroll = new JScrollPane(tblChiTietDangKy);

        JPanel pnlMain = new JPanel(new BorderLayout());
        pnlMain.add(pnlForm, BorderLayout.NORTH);
        pnlMain.add(scroll, BorderLayout.CENTER);

        add(pnlMain, BorderLayout.CENTER);
    }
    
    private JPanel fieldCombo(String label, JComboBox<?> cb) {
        JLabel lb = new JLabel(label);
        lb.setPreferredSize(new Dimension(70, 25));

        cb.setPreferredSize(new Dimension(200, 28));

        JPanel p = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 5));
        p.setBackground(new Color(245, 245, 245));
        p.add(lb);
        p.add(cb);
        return p;
    }
   
    private JPanel field(String label, JTextField txt) {
        JLabel lb = new JLabel(label);
        lb.setPreferredSize(new Dimension(70, 25));

        txt.setPreferredSize(new Dimension(200, 28));

        JPanel p = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 5));
        p.setBackground(new Color(245, 245, 245));
        p.add(lb);
        p.add(txt);
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
        b.setPreferredSize(new Dimension(100, 32));
    }

    private void styleRed(JButton b) {
        b.setBackground(new Color(217, 48, 48));
        b.setForeground(Color.WHITE);
        b.setOpaque(true);
        b.setBorderPainted(false);
        b.setFocusPainted(false);
        b.setPreferredSize(new Dimension(100, 32));
    }
    

    private void styleGray(JButton b) {
        b.setBackground(new Color(220, 220, 220));
        b.setForeground(Color.BLACK);
        b.setOpaque(true);
        b.setBorderPainted(false);
        b.setFocusPainted(false);
        b.setPreferredSize(new Dimension(100, 32));
    }
}
