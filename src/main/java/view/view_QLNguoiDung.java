/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package view;

/**
 *
<<<<<<< HEAD
<<<<<<< HEAD
 * @author thedu
=======
 * @author HUY
>>>>>>> origin/nhuy
=======
 * @author HUY
>>>>>>> origin/nhuy
 */
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

public class view_QLNguoiDung extends JFrame {

    // ===== INPUT =====
    public JTextField txtUsername = new JTextField();
    public JTextField txtPassword = new JTextField();
    public JTextField txtHoTen = new JTextField();
    public JComboBox<String> cbTrangThai = new JComboBox<>(new String[]{"Hoạt động", "Khoá"});
    public JTextField txtTimKiem = new JTextField();
    // ===== BUTTONS =====
    public JButton btnThem = new JButton("Thêm");
    public JButton btnSua = new JButton("Sửa");
    public JButton btnXoa = new JButton("Xoá");
    public JButton btnLamMoi = new JButton("Làm mới");

    public JButton btnTimKiem = new JButton("Tìm kiếm");

    // ✅ THÊM 2 NÚT EXCEL
    public JButton btnXuatExcel = new JButton("Xuất Excel");
    public JButton btnNhapExcel = new JButton("Nhập Excel");

    public JButton btnQuayLai = new JButton("Quay lại");

    // ===== TABLE =====
    public DefaultTableModel model = new DefaultTableModel(
        new Object[]{"ID", "Username", "Password", "Họ tên", "Trạng thái"}, 0
    ) {
        @Override public boolean isCellEditable(int row, int column) { return false; }
    };
    public JTable tbl = new JTable(model);


    public view_QLNguoiDung() {
        setTitle("Quản lý người dùng");
        setSize(1180, 650);
        setMinimumSize(new Dimension(1050, 600));
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        try { UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); }
        catch (Exception ignored) {}

        Font fTitle = new Font("Segoe UI", Font.BOLD, 18);
        Font fLabel = new Font("Segoe UI", Font.PLAIN, 13);
        Font fInput = new Font("Segoe UI", Font.PLAIN, 13);

        JPanel root = new JPanel(new BorderLayout(12, 12));
        root.setBorder(new EmptyBorder(12, 12, 12, 12));
        root.setBackground(new Color(245, 246, 248));
        setContentPane(root);

        // ===== TOP BAR =====
        JPanel topBar = new JPanel(new BorderLayout());
        topBar.setBackground(Color.WHITE);
        topBar.setBorder(new EmptyBorder(10, 12, 10, 12));

        JLabel lblTitle = new JLabel("Quản lý người dùng", SwingConstants.CENTER);
        lblTitle.setFont(fTitle);

        JPanel topRight = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 0));
        topRight.setOpaque(false);
        styleNeutral(btnQuayLai);
        topRight.add(btnQuayLai);

        topBar.add(lblTitle, BorderLayout.CENTER);
        topBar.add(topRight, BorderLayout.EAST);

        // ===== INPUT WRAP =====
        JPanel inputWrap = new JPanel(new BorderLayout(12, 12));
        inputWrap.setBackground(Color.WHITE);
        inputWrap.setBorder(BorderFactory.createTitledBorder("Thông tin tài khoản"));

        JPanel form = new JPanel(new GridLayout(1, 4, 12, 10));
        form.setOpaque(false);
        form.setBorder(new EmptyBorder(12, 14, 12, 14));

        form.add(fieldBox("Username", txtUsername, fLabel, fInput));
        form.add(fieldBox("Password", txtPassword, fLabel, fInput));
        form.add(fieldBox("Họ tên", txtHoTen, fLabel, fInput));
        form.add(comboBox("Trạng thái", cbTrangThai, fLabel, fInput));

        // ===== ACTION BAR =====
        JPanel actionBar = new JPanel(new BorderLayout());
        actionBar.setOpaque(false);
        actionBar.setBorder(new EmptyBorder(0, 14, 12, 14));

        JPanel left = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        left.setOpaque(false);

        stylePrimary(btnThem);
        stylePrimary(btnSua);
        styleDanger(btnXoa);
        styleNeutral(btnLamMoi);
        stylePrimary(btnTimKiem);

        // Excel style
        stylePrimary(btnXuatExcel);
        styleNeutral(btnNhapExcel);

        JLabel lblKey = new JLabel("Từ khoá");
        lblKey.setFont(fLabel);

        txtTimKiem.setFont(fInput);
        txtTimKiem.setPreferredSize(new Dimension(260, 36));
        txtTimKiem.setBorder(new CompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200)),
                new EmptyBorder(6, 8, 6, 8)
        ));

        left.add(btnThem);
        left.add(btnSua);
        left.add(btnXoa);
        left.add(btnLamMoi);
        left.add(Box.createHorizontalStrut(16));
        left.add(lblKey);
        left.add(txtTimKiem);
        left.add(btnTimKiem);

        JPanel right = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        right.setOpaque(false);
        right.add(btnXuatExcel);
        right.add(btnNhapExcel);

        actionBar.add(left, BorderLayout.WEST);
        actionBar.add(right, BorderLayout.EAST);

        inputWrap.add(form, BorderLayout.CENTER);
        inputWrap.add(actionBar, BorderLayout.SOUTH);

        // ===== TABLE WRAP =====
        JPanel tableWrap = new JPanel(new BorderLayout());
        tableWrap.setBackground(Color.WHITE);
        tableWrap.setBorder(BorderFactory.createTitledBorder("Danh sách người dùng"));

        tbl.setRowHeight(30);
        tbl.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        tbl.setGridColor(new Color(230, 230, 230));
        tbl.setShowGrid(true);
        tbl.setSelectionBackground(new Color(210, 225, 245));
        tbl.setSelectionForeground(Color.BLACK);

        JTableHeader header = tbl.getTableHeader();
        header.setFont(new Font("Segoe UI", Font.BOLD, 13));
        header.setPreferredSize(new Dimension(header.getPreferredSize().width, 34));

        DefaultTableCellRenderer center = new DefaultTableCellRenderer();
        center.setHorizontalAlignment(SwingConstants.CENTER);
        tbl.getColumnModel().getColumn(0).setCellRenderer(center);
        tbl.getColumnModel().getColumn(3).setCellRenderer(center);

        tbl.getColumnModel().getColumn(0).setPreferredWidth(80);
        tbl.getColumnModel().getColumn(1).setPreferredWidth(220);
        tbl.getColumnModel().getColumn(2).setPreferredWidth(320);
        tbl.getColumnModel().getColumn(3).setPreferredWidth(120);

        JScrollPane sp = new JScrollPane(tbl);
        sp.setBorder(new EmptyBorder(10, 10, 10, 10));
        sp.getViewport().setBackground(Color.WHITE);
        tableWrap.add(sp, BorderLayout.CENTER);

        JPanel centerWrap = new JPanel();
        centerWrap.setLayout(new BoxLayout(centerWrap, BoxLayout.Y_AXIS));
        centerWrap.setOpaque(false);

        centerWrap.add(inputWrap);
        centerWrap.add(Box.createVerticalStrut(12));
        centerWrap.add(tableWrap);

        root.add(topBar, BorderLayout.NORTH);
        root.add(centerWrap, BorderLayout.CENTER);
    }

    public void clearForm() {
        txtUsername.setText("");
        txtPassword.setText("");
        txtHoTen.setText("");
        cbTrangThai.setSelectedIndex(0);
        txtTimKiem.setText("");
        tbl.clearSelection();
        txtUsername.setEnabled(true);
    }

    // ===== helper UI blocks =====
    private JPanel fieldBox(String label, JTextField field, Font fLabel, Font fInput) {
        JPanel p = new JPanel();
        p.setOpaque(false);
        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));

        JLabel l = new JLabel(label, SwingConstants.CENTER);
        l.setFont(fLabel);
        l.setAlignmentX(Component.CENTER_ALIGNMENT);

        field.setFont(fInput);
        field.setPreferredSize(new Dimension(10, 36));
        field.setMaximumSize(new Dimension(Integer.MAX_VALUE, 36));
        field.setBorder(new CompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200)),
                new EmptyBorder(6, 8, 6, 8)
        ));

        p.add(l);
        p.add(Box.createVerticalStrut(6));
        p.add(field);
        return p;
    }

    private JPanel comboBox(String label, JComboBox<?> cb, Font fLabel, Font fInput) {
        JPanel p = new JPanel();
        p.setOpaque(false);
        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));

        JLabel l = new JLabel(label, SwingConstants.CENTER);
        l.setFont(fLabel);
        l.setAlignmentX(Component.CENTER_ALIGNMENT);

        cb.setFont(fInput);
        cb.setPreferredSize(new Dimension(10, 36));
        cb.setMaximumSize(new Dimension(Integer.MAX_VALUE, 36));

        p.add(l);
        p.add(Box.createVerticalStrut(6));
        p.add(cb);
        return p;
    }

    private void stylePrimary(JButton b) {
        styleButton(b, new Color(45, 108, 223), Color.WHITE, new Color(30, 90, 200));
    }
    private void styleDanger(JButton b) {
        styleButton(b, new Color(210, 55, 75), Color.WHITE, new Color(185, 40, 60));
    }
    private void styleNeutral(JButton b) {
        styleButton(b, new Color(235, 235, 235), new Color(40, 40, 40), new Color(220, 220, 220));
    }

    private void styleButton(JButton b, Color bg, Color fg, Color hover) {
        b.setFocusPainted(false);
        b.setBorderPainted(false);
        b.setOpaque(true);
        b.setBackground(bg);
        b.setForeground(fg);
        b.setFont(new Font("Segoe UI", Font.BOLD, 13));
        b.setMargin(new Insets(10, 18, 10, 18));
        b.setCursor(new Cursor(Cursor.HAND_CURSOR));

        Color normal = bg;
        b.addMouseListener(new MouseAdapter() {
            @Override public void mouseEntered(MouseEvent e) { b.setBackground(hover); }
            @Override public void mouseExited(MouseEvent e) { b.setBackground(normal); }
        });
    }
}
