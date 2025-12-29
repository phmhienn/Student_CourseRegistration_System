package view;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;

public class view_Sinhvien extends JFrame {

    public JLabel lblInfo = new JLabel("", SwingConstants.RIGHT);

    public JButton btnThongTin = new JButton("Thông tin sinh viên");
    public JButton btnDangKy = new JButton("Đăng ký môn học");
    public JButton btnDaDangKy = new JButton("Môn học đã đăng ký");
    public JButton btnDsMonHoc = new JButton("Danh sách môn học");
    public JButton btnDangXuat = new JButton("Đăng xuất");

    public JPanel panelContent = new JPanel(new BorderLayout());

    public view_Sinhvien() {
        setTitle("Sinh viên");
        setSize(1200, 650);
        setMinimumSize(new Dimension(1050, 600));
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        try { UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); }
        catch (Exception ignored) {}

        Font fTitle = new Font("Segoe UI", Font.BOLD, 18);
        Font fLabel = new Font("Segoe UI", Font.BOLD, 13);
        Font fBtn = new Font("Segoe UI", Font.BOLD, 13);

        JPanel root = new JPanel(new BorderLayout(12, 12));
        root.setBorder(new EmptyBorder(12, 12, 12, 12));
        root.setBackground(new Color(245, 246, 248));
        setContentPane(root);

        JPanel topBar = new JPanel(new BorderLayout());
        topBar.setBackground(Color.WHITE);
        topBar.setBorder(new EmptyBorder(12, 14, 12, 14));

        JLabel lblTitle = new JLabel("Trang sinh viên");
        lblTitle.setFont(fTitle);

        lblInfo.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        lblInfo.setForeground(new Color(60, 60, 60));

        topBar.add(lblTitle, BorderLayout.WEST);
        topBar.add(lblInfo, BorderLayout.EAST);

        JPanel menuWrap = new JPanel();
        menuWrap.setBackground(Color.WHITE);
        menuWrap.setBorder(new EmptyBorder(12, 12, 12, 12));
        menuWrap.setPreferredSize(new Dimension(280, 0));
        menuWrap.setLayout(new BorderLayout(0, 10));

        JLabel lblMenu = new JLabel("Chức năng");
        lblMenu.setFont(fLabel);
        lblMenu.setHorizontalAlignment(SwingConstants.LEFT);

        JPanel menuBtns = new JPanel();
        menuBtns.setOpaque(false);
        menuBtns.setLayout(new BoxLayout(menuBtns, BoxLayout.Y_AXIS));

        Dimension btnSize = new Dimension(240, 42);

        styleMenuButton(btnThongTin, fBtn, btnSize);
        styleMenuButton(btnDangKy, fBtn, btnSize);
        styleMenuButton(btnDaDangKy, fBtn, btnSize);
        styleMenuButton(btnDsMonHoc, fBtn, btnSize);

        menuBtns.add(btnThongTin);
        menuBtns.add(Box.createVerticalStrut(10));
        menuBtns.add(btnDangKy);
        menuBtns.add(Box.createVerticalStrut(10));
        menuBtns.add(btnDaDangKy);
        menuBtns.add(Box.createVerticalStrut(10));
        menuBtns.add(btnDsMonHoc);
        menuBtns.add(Box.createVerticalGlue());

        styleDanger(btnDangXuat);
        btnDangXuat.setPreferredSize(new Dimension(240, 44));
        btnDangXuat.setMaximumSize(new Dimension(240, 44));

        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
        bottom.setOpaque(false);
        bottom.add(btnDangXuat);

        JPanel menuTop = new JPanel(new BorderLayout());
        menuTop.setOpaque(false);
        menuTop.add(lblMenu, BorderLayout.NORTH);
        menuTop.add(Box.createVerticalStrut(10), BorderLayout.CENTER);

        menuWrap.add(lblMenu, BorderLayout.NORTH);
        menuWrap.add(menuBtns, BorderLayout.CENTER);
        menuWrap.add(bottom, BorderLayout.SOUTH);

        panelContent.setBackground(Color.WHITE);
        panelContent.setBorder(new CompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 220, 220)),
                new EmptyBorder(10, 10, 10, 10)
        ));

        root.add(topBar, BorderLayout.NORTH);
        root.add(menuWrap, BorderLayout.WEST);
        root.add(panelContent, BorderLayout.CENTER);
    }

    private void styleMenuButton(JButton b, Font fBtn, Dimension size) {
        b.setFont(fBtn);
        b.setPreferredSize(size);
        b.setMaximumSize(size);
        b.setFocusPainted(false);
        b.setBorderPainted(false);
        b.setOpaque(true);
        b.setBackground(new Color(235, 235, 235));
        b.setForeground(new Color(40, 40, 40));
        b.setCursor(new Cursor(Cursor.HAND_CURSOR));
        b.setAlignmentX(Component.CENTER_ALIGNMENT);

        Color normal = b.getBackground();
        Color hover = new Color(220, 230, 245);

        b.addMouseListener(new MouseAdapter() {
            @Override public void mouseEntered(MouseEvent e) { b.setBackground(hover); }
            @Override public void mouseExited(MouseEvent e) { b.setBackground(normal); }
        });
    }

    private void styleDanger(JButton b) {
        b.setFocusPainted(false);
        b.setBorderPainted(false);
        b.setOpaque(true);
        b.setBackground(new Color(210, 55, 75));
        b.setForeground(Color.WHITE);
        b.setFont(new Font("Segoe UI", Font.BOLD, 13));
        b.setCursor(new Cursor(Cursor.HAND_CURSOR));

        Color normal = b.getBackground();
        Color hover = new Color(185, 40, 60);

        b.addMouseListener(new MouseAdapter() {
            @Override public void mouseEntered(MouseEvent e) { b.setBackground(hover); }
            @Override public void mouseExited(MouseEvent e) { b.setBackground(normal); }
        });
    }
}
