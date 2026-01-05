package view;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class view_Admin extends JFrame {

    public JButton btnQuanLyMonHoc        = new JButton("Quản lý môn học");
    public JButton btnQLSinhVien          = new JButton("Quản lý sinh viên");
    public JButton btnQLLopHocPhan        = new JButton("Quản lý lớp học phần");
    public JButton btnQLGiangVien           = new JButton("Quản lý giảng viên");

    public JButton btnQuanLyNguoiDung     = new JButton("Quản lý người dùng");
    public JButton btnQuanLyDangKyTinChi  = new JButton("Quản lý đăng ký tín chỉ");
    public JButton btnThongKe             = new JButton("Thống kê");
    public JButton btnHocPhi              = new JButton("Học phí");

    public JButton btnDangXuat            = new JButton("Đăng xuất");

    public view_Admin() {
        setTitle("Trang Admin");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(1200, 650);
        setMinimumSize(new Dimension(1000, 600));
        setLocationRelativeTo(null);

        try { UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); }
        catch (Exception ignored) {}

        JPanel root = new JPanel(new BorderLayout(12, 12));
        root.setBorder(new EmptyBorder(15, 15, 15, 15));
        root.setBackground(new Color(245, 246, 248));
        setContentPane(root);

        JPanel topBar = new JPanel(new BorderLayout());
        topBar.setBackground(Color.WHITE);
        topBar.setBorder(new EmptyBorder(16, 18, 16, 18));

        JLabel lblTitle = new JLabel("Trang Admin");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 28));

        JPanel topRight = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 0));
        topRight.setOpaque(false);
        styleDanger(btnDangXuat);
        btnDangXuat.setPreferredSize(new Dimension(120, 40));
        topRight.add(btnDangXuat);

        topBar.add(lblTitle, BorderLayout.WEST);
        topBar.add(topRight, BorderLayout.EAST);

        JPanel box = new JPanel(new BorderLayout(0, 12));
        box.setBackground(Color.WHITE);
        box.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 220, 220)),
                new EmptyBorder(25, 25, 25, 25)
        ));

        JLabel lblChucNang = new JLabel("Chức năng");
        lblChucNang.setFont(new Font("Segoe UI", Font.BOLD, 16));

        JPanel grid = new JPanel(new GridLayout(2, 4, 18, 18));
        grid.setOpaque(false);

        Font fBtn = new Font("Segoe UI", Font.BOLD, 15);
        Dimension size = new Dimension(250, 100);

        styleCardButton(btnQuanLyMonHoc, fBtn, size);
        styleCardButton(btnQLLopHocPhan, fBtn, size);
        styleCardButton(btnQLGiangVien, fBtn, size);
        styleCardButton(btnQLSinhVien, fBtn, size);

        styleCardButton(btnQuanLyNguoiDung, fBtn, size);
        styleCardButton(btnQuanLyDangKyTinChi, fBtn, size);
        styleCardButton(btnThongKe, fBtn, size);
        styleCardButton(btnHocPhi, fBtn, size);

        grid.add(btnQuanLyMonHoc);
        grid.add(btnQLLopHocPhan);
        grid.add(btnQLGiangVien);
        grid.add(btnQLSinhVien);

        grid.add(btnQuanLyNguoiDung);
        grid.add(btnQuanLyDangKyTinChi);
        grid.add(btnThongKe);
        grid.add(btnHocPhi);

        box.add(lblChucNang, BorderLayout.NORTH);
        box.add(grid, BorderLayout.CENTER);

        root.add(topBar, BorderLayout.NORTH);
        root.add(box, BorderLayout.CENTER);
    }

    private void styleCardButton(JButton b, Font f, Dimension size) {
        b.setFont(f);
        b.setPreferredSize(size);
        b.setFocusPainted(false);
        b.setBorderPainted(false);
        b.setOpaque(true);
        b.setBackground(new Color(235, 235, 235));
        b.setForeground(new Color(30, 30, 30));
        b.setCursor(new Cursor(Cursor.HAND_CURSOR));

        Color normal = b.getBackground();
        Color hover  = new Color(210, 225, 245);

        b.addMouseListener(new MouseAdapter() {
            @Override public void mouseEntered(MouseEvent e) { b.setBackground(hover); }
            @Override public void mouseExited(MouseEvent e)  { b.setBackground(normal); }
        });
    }

    private void styleDanger(JButton b) {
        b.setFocusPainted(false);
        b.setBorderPainted(false);
        b.setOpaque(true);
        b.setBackground(new Color(210, 55, 75));
        b.setForeground(Color.WHITE);
        b.setFont(new Font("Segoe UI", Font.BOLD, 14));
        b.setCursor(new Cursor(Cursor.HAND_CURSOR));

        Color normal = b.getBackground();
        Color hover  = new Color(185, 40, 60);

        b.addMouseListener(new MouseAdapter() {
            @Override public void mouseEntered(MouseEvent e) { b.setBackground(hover); }
            @Override public void mouseExited(MouseEvent e)  { b.setBackground(normal); }
        });
    }
}