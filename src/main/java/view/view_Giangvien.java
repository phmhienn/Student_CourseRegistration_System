package view;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

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
        try { UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); }
        catch (Exception ignored) {}

        Font fTitle = new Font("Segoe UI", Font.BOLD, 18);
        Font fText  = new Font("Segoe UI", Font.PLAIN, 13);

        JPanel root = new JPanel(new BorderLayout(12, 12));
        root.setBorder(new EmptyBorder(12, 12, 12, 12));
        root.setBackground(new Color(245, 246, 248));
        setContentPane(root);

        JPanel topBar = new JPanel(new BorderLayout());
        topBar.setBackground(Color.WHITE);
        topBar.setBorder(new EmptyBorder(12, 14, 12, 14));

        JLabel lblTitle = new JLabel("Trang giảng viên");
        lblTitle.setFont(fTitle);

        lblInfo.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        lblInfo.setForeground(new Color(60, 60, 60));

        JPanel infoWrap = new JPanel();
        infoWrap.setOpaque(false);
        infoWrap.setLayout(new BoxLayout(infoWrap, BoxLayout.Y_AXIS));
        infoWrap.add(lblTitle);
        infoWrap.add(Box.createVerticalStrut(4));
        infoWrap.add(lblInfo);

        JPanel topRight = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 0));
        topRight.setOpaque(false);
        styleDanger(btnDangXuat);
        topRight.add(btnDangXuat);

        topBar.add(infoWrap, BorderLayout.WEST);
        topBar.add(topRight, BorderLayout.EAST);

        JPanel center = new JPanel();
        center.setOpaque(false);
        center.setLayout(new BoxLayout(center, BoxLayout.Y_AXIS));

        JPanel box = new JPanel(new BorderLayout());
        box.setBackground(Color.WHITE);
        box.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 220, 220)),
                new EmptyBorder(18, 18, 18, 18)
        ));

        JLabel lblChucNang = new JLabel("Chức năng");
        lblChucNang.setFont(new Font("Segoe UI", Font.BOLD, 14));

        JPanel grid = new JPanel(new GridLayout(2, 2, 14, 14));
        grid.setOpaque(false);

        stylePrimary(btnQLSinhVien);
        stylePrimary(btnQLLopHocPhan);
        stylePrimary(btnXemBaoCao);
        stylePrimary(btnThongTinCaNhan);

        btnQLSinhVien.setFont(fText);
        btnQLLopHocPhan.setFont(fText);
        btnXemBaoCao.setFont(fText);
        btnThongTinCaNhan.setFont(fText);

        grid.add(btnQLSinhVien);
        grid.add(btnQLLopHocPhan);
        grid.add(btnXemBaoCao);
        grid.add(btnThongTinCaNhan);

        box.add(lblChucNang, BorderLayout.NORTH);
        box.add(Box.createVerticalStrut(10), BorderLayout.CENTER);

        JPanel wrapGrid = new JPanel(new BorderLayout());
        wrapGrid.setOpaque(false);
        wrapGrid.add(grid, BorderLayout.CENTER);

        box.add(wrapGrid, BorderLayout.CENTER);

        center.add(box);

        root.add(topBar, BorderLayout.NORTH);
        root.add(center, BorderLayout.CENTER);
    }

    private void stylePrimary(JButton b) {
        styleButton(b, new Color(45, 108, 223), Color.WHITE, new Color(30, 90, 200));
    }

    private void styleDanger(JButton b) {
        styleButton(b, new Color(210, 55, 75), Color.WHITE, new Color(185, 40, 60));
    }

    private void styleButton(JButton b, Color bg, Color fg, Color hover) {
        b.setFocusPainted(false);
        b.setBorderPainted(false);
        b.setOpaque(true);
        b.setBackground(bg);
        b.setForeground(fg);
        b.setFont(new Font("Segoe UI", Font.BOLD, 13));
        b.setMargin(new Insets(12, 16, 12, 16));
        b.setCursor(new Cursor(Cursor.HAND_CURSOR));

        Color normal = bg;
        b.addMouseListener(new MouseAdapter() {
            @Override public void mouseEntered(MouseEvent e) { b.setBackground(hover); }
            @Override public void mouseExited(MouseEvent e) { b.setBackground(normal); }
        });
    }
}

