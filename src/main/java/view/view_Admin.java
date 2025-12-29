package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumnModel;

public class view_Admin extends JFrame {

    public JTextField txtTenDangNhap = new JTextField();
    public JTextField txtMatKhau = new JTextField();
    public JComboBox<String> cboVaiTro = new JComboBox<>(new String[]{"SV", "GV", "AD"});
    public JTextField txtMaLienKet = new JTextField();

    public JButton btnThem = new JButton("Thêm");
    public JButton btnSua = new JButton("Sửa");
    public JButton btnXoa = new JButton("Xoá");
    public JButton btnLamMoi = new JButton("Làm mới");
    public JButton btnQuanLyMonHoc = new JButton("Quản lý môn học");
    public JButton btnDangXuat = new JButton("Đăng xuất");

    public DefaultTableModel dtm = new DefaultTableModel(
            new String[]{"Tên đăng nhập", "Mật khẩu", "Vai trò", "Mã liên kết"}, 0
    ) {
        @Override
        public boolean isCellEditable(int row, int column) { return false; }
    };

    public JTable tbl = new JTable(dtm);

    public view_Admin() {
        try { UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); }
        catch (Exception ignored) {}

        setTitle("Admin - Quản lý");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(1020, 600);
        setMinimumSize(new Dimension(980, 560));
        setLocationRelativeTo(null);

        Font fTitle = new Font("Segoe UI", Font.BOLD, 18);
        Font fLabel = new Font("Segoe UI", Font.PLAIN, 13);
        Font fInput = new Font("Segoe UI", Font.PLAIN, 13);
        Font fBtn   = new Font("Segoe UI", Font.BOLD, 13);

        JPanel root = new JPanel(new BorderLayout(10, 10));
        root.setBorder(new EmptyBorder(12, 12, 12, 12));
        root.setBackground(new Color(245, 246, 248));
        setContentPane(root);

        JPanel topBar = new JPanel(new BorderLayout());
        topBar.setBackground(Color.WHITE);
        topBar.setBorder(new EmptyBorder(10, 12, 10, 12));

        JLabel lblTitle = new JLabel("Quản lý (Admin)", SwingConstants.CENTER);
        lblTitle.setFont(fTitle);

        JPanel rightActions = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        rightActions.setOpaque(false);

        stylePrimary(btnQuanLyMonHoc, fBtn);
        styleDanger(btnDangXuat, fBtn);

        rightActions.add(btnQuanLyMonHoc);
        rightActions.add(btnDangXuat);

        topBar.add(lblTitle, BorderLayout.CENTER);
        topBar.add(rightActions, BorderLayout.EAST);

        JPanel formWrap = new JPanel();
        formWrap.setLayout(new BoxLayout(formWrap, BoxLayout.Y_AXIS));
        formWrap.setBackground(Color.WHITE);
        formWrap.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 220, 220)),
                new EmptyBorder(12, 12, 12, 12)
        ));

        JLabel lblForm = new JLabel("Thông tin tài khoản");
        lblForm.setFont(new Font("Segoe UI", Font.BOLD, 13));
        lblForm.setForeground(new Color(60, 60, 60));

        JPanel form = new JPanel(new GridLayout(2, 4, 12, 10));
        form.setOpaque(false);

        JLabel l1 = new JLabel("Tên đăng nhập"); l1.setFont(fLabel);
        JLabel l2 = new JLabel("Mật khẩu");      l2.setFont(fLabel);
        JLabel l3 = new JLabel("Vai trò");       l3.setFont(fLabel);
        JLabel l4 = new JLabel("Mã liên kết (SV/GV)"); l4.setFont(fLabel);

        styleInput(txtTenDangNhap, fInput);
        styleInput(txtMatKhau, fInput);
        styleInput(txtMaLienKet, fInput);
        cboVaiTro.setFont(fInput);
        cboVaiTro.setPreferredSize(new Dimension(140, 34));

        form.add(l1); form.add(l2); form.add(l3); form.add(l4);
        form.add(txtTenDangNhap);
        form.add(txtMatKhau);
        form.add(cboVaiTro);
        form.add(txtMaLienKet);

        JPanel actionBar = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 8));
        actionBar.setOpaque(false);

        stylePrimary(btnThem, fBtn);
        stylePrimary(btnSua, fBtn);
        styleDanger(btnXoa, fBtn);
        styleNeutral(btnLamMoi, fBtn);

        actionBar.add(btnThem);
        actionBar.add(btnSua);
        actionBar.add(btnXoa);
        actionBar.add(btnLamMoi);

        formWrap.add(lblForm);
        formWrap.add(Box.createVerticalStrut(10));
        formWrap.add(form);
        formWrap.add(Box.createVerticalStrut(8));
        formWrap.add(actionBar);

        JPanel tableWrap = new JPanel(new BorderLayout());
        tableWrap.setBackground(Color.WHITE);
        tableWrap.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 220, 220)),
                new EmptyBorder(12, 12, 12, 12)
        ));

        JLabel lblTable = new JLabel("Danh sách tài khoản");
        lblTable.setFont(new Font("Segoe UI", Font.BOLD, 13));
        lblTable.setForeground(new Color(60, 60, 60));

        styleTable();

        JScrollPane sp = new JScrollPane(tbl);
        sp.setBorder(new EmptyBorder(10, 0, 0, 0));
        sp.getViewport().setBackground(Color.WHITE);

        tableWrap.add(lblTable, BorderLayout.NORTH);
        tableWrap.add(sp, BorderLayout.CENTER);

        JPanel center = new JPanel();
        center.setOpaque(false);
        center.setLayout(new BoxLayout(center, BoxLayout.Y_AXIS));
        center.add(formWrap);
        center.add(Box.createVerticalStrut(12));
        center.add(tableWrap);

        root.add(topBar, BorderLayout.NORTH);
        root.add(center, BorderLayout.CENTER);
    }

    private void styleInput(JTextField t, Font f) {
        t.setFont(f);
        t.setPreferredSize(new Dimension(220, 34));
        t.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200)),
                new EmptyBorder(6, 8, 6, 8)
        ));
    }

    private void styleTable() {
        tbl.setRowHeight(30);
        tbl.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        tbl.setGridColor(new Color(230, 230, 230));
        tbl.setShowGrid(true);
        tbl.setSelectionBackground(new Color(210, 225, 245));
        tbl.setSelectionForeground(Color.BLACK);

        JTableHeader header = tbl.getTableHeader();
        header.setFont(new Font("Segoe UI", Font.BOLD, 13));
        header.setPreferredSize(new Dimension(header.getPreferredSize().width, 32));

        DefaultTableCellRenderer c = new DefaultTableCellRenderer();
        c.setHorizontalAlignment(SwingConstants.CENTER);
        tbl.getColumnModel().getColumn(2).setCellRenderer(c);
        tbl.getColumnModel().getColumn(3).setCellRenderer(c);

        tbl.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

        TableColumnModel cm = tbl.getColumnModel();
        cm.getColumn(0).setPreferredWidth(220);
        cm.getColumn(1).setPreferredWidth(260);
        cm.getColumn(2).setPreferredWidth(100);
        cm.getColumn(3).setPreferredWidth(180);
    }

    private void stylePrimary(JButton b, Font f) {
        styleButton(b, f, new Color(45, 108, 223), Color.WHITE, new Color(30, 90, 200));
    }

    private void styleDanger(JButton b, Font f) {
        styleButton(b, f, new Color(210, 55, 75), Color.WHITE, new Color(185, 40, 60));
    }

    private void styleNeutral(JButton b, Font f) {
        styleButton(b, f, new Color(235, 235, 235), new Color(40, 40, 40), new Color(220, 220, 220));
    }

    private void styleButton(JButton b, Font f, Color bg, Color fg, Color hover) {
        b.setFocusPainted(false);
        b.setBorderPainted(false);
        b.setOpaque(true);
        b.setBackground(bg);
        b.setForeground(fg);
        b.setFont(f);
        b.setMargin(new Insets(10, 16, 10, 16));
        b.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        Color normal = bg;
        b.addMouseListener(new MouseAdapter() {
            @Override public void mouseEntered(MouseEvent e) { b.setBackground(hover); }
            @Override public void mouseExited(MouseEvent e) { b.setBackground(normal); }
        });
    }
}
