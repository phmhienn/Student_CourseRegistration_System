package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;

public class view_Dangnhap extends JFrame {

    public JTextField txtUser = new JTextField();
    public JPasswordField txtPass = new JPasswordField();
    public JButton btnLogin = new JButton("Đăng nhập");
    public JButton btnExit = new JButton("Thoát");
    public JLabel lblMsg = new JLabel(" ");

    public view_Dangnhap() {
        setTitle("Đăng nhập");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(520, 340);
        setLocationRelativeTo(null);

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) {}

        Font fTitle = new Font("Segoe UI", Font.BOLD, 20);
        Font fLabel = new Font("Segoe UI", Font.PLAIN, 13);
        Font fInput = new Font("Segoe UI", Font.PLAIN, 13);

        JPanel root = new JPanel(new BorderLayout());
        root.setBackground(new Color(245, 246, 248));
        root.setBorder(new EmptyBorder(20, 20, 20, 20));
        setContentPane(root);

        JPanel center = new JPanel();
        center.setOpaque(false);
        center.setLayout(new BoxLayout(center, BoxLayout.Y_AXIS));

        JLabel lblTitle = new JLabel("ĐĂNG NHẬP");
        lblTitle.setFont(fTitle);
        lblTitle.setAlignmentX(CENTER_ALIGNMENT);

        lblMsg.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lblMsg.setForeground(new Color(210, 55, 75));
        lblMsg.setAlignmentX(CENTER_ALIGNMENT);

        JPanel userBox = fieldBox("Tên đăng nhập", txtUser, fLabel, fInput);
        userBox.setAlignmentX(CENTER_ALIGNMENT);

        JPanel passBox = passwordBox("Mật khẩu", txtPass, fLabel, fInput);
        passBox.setAlignmentX(CENTER_ALIGNMENT);

        JPanel actions = new JPanel(new FlowLayout(FlowLayout.CENTER, 14, 0));
        actions.setOpaque(false);

        stylePrimary(btnLogin);
        styleNeutral(btnExit);

        actions.add(btnExit);
        actions.add(btnLogin);

        center.add(Box.createVerticalGlue());
        center.add(lblTitle);
        center.add(Box.createVerticalStrut(12));
        center.add(lblMsg);
        center.add(Box.createVerticalStrut(12));
        center.add(userBox);
        center.add(Box.createVerticalStrut(10));
        center.add(passBox);
        center.add(Box.createVerticalStrut(14));
        center.add(actions);
        center.add(Box.createVerticalGlue());

        root.add(center, BorderLayout.CENTER);

        getRootPane().setDefaultButton(btnLogin);
        btnExit.addActionListener(e -> System.exit(0));
    }

    private JPanel fieldBox(String label, JTextField field, Font fLabel, Font fInput) {
        JPanel p = new JPanel();
        p.setOpaque(false);
        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));

        JLabel l = new JLabel(label);
        l.setFont(fLabel);
        l.setAlignmentX(CENTER_ALIGNMENT);

        Dimension d = new Dimension(320, 36);
        field.setFont(fInput);
        field.setPreferredSize(d);
        field.setMaximumSize(d);
        field.setMinimumSize(d);
        field.setBorder(new EmptyBorder(6, 8, 6, 8));

        p.add(l);
        p.add(Box.createVerticalStrut(6));
        p.add(field);
        return p;
    }

    private JPanel passwordBox(String label, JPasswordField field, Font fLabel, Font fInput) {
        JPanel p = new JPanel();
        p.setOpaque(false);
        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));

        JLabel l = new JLabel(label);
        l.setFont(fLabel);
        l.setAlignmentX(CENTER_ALIGNMENT);

        Dimension d = new Dimension(320, 36);
        field.setFont(fInput);
        field.setPreferredSize(d);
        field.setMaximumSize(d);
        field.setMinimumSize(d);
        field.setBorder(new EmptyBorder(6, 8, 6, 8));

        p.add(l);
        p.add(Box.createVerticalStrut(6));
        p.add(field);
        return p;
    }

    private void stylePrimary(JButton b) {
        styleButton(b, new Color(45, 108, 223), Color.WHITE, new Color(30, 90, 200));
    }

    private void styleNeutral(JButton b) {
        styleButton(b, new Color(230, 230, 230), Color.BLACK, new Color(210, 210, 210));
    }

    private void styleButton(JButton b, Color bg, Color fg, Color hover) {
        b.setFocusPainted(false);
        b.setBorderPainted(false);
        b.setOpaque(true);
        b.setBackground(bg);
        b.setForeground(fg);
        b.setFont(new Font("Segoe UI", Font.BOLD, 13));
        b.setMargin(new Insets(10, 20, 10, 20));
        b.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        Color normal = bg;
        b.addMouseListener(new MouseAdapter() {
            @Override public void mouseEntered(MouseEvent e) { b.setBackground(hover); }
            @Override public void mouseExited(MouseEvent e) { b.setBackground(normal); }
        });
    }
}
