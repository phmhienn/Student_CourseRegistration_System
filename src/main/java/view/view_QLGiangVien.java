/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package view;

/**
 *
 * @author HUY
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

public class view_QLGiangVien extends JFrame {

    // ===== INPUT =====
    public JTextField txtMaGV = new JTextField();
    public JTextField txtTenGV = new JTextField();
    public JTextField txtHocVi = new JTextField();
    public JTextField txtHocHam = new JTextField();
    public JTextField txtEmail = new JTextField();
    public JTextField txtDienThoai = new JTextField();
    public JTextField txtMaKhoa = new JTextField();
    public JTextField txtTimKiem = new JTextField();

    public JComboBox<String> cbTrangThai = new JComboBox<>(new String[]{"Hoạt động", "Khoá"});

    // ===== BUTTONS =====
    public JButton btnThem = new JButton("Thêm");
    public JButton btnSua = new JButton("Sửa");
    public JButton btnXoa = new JButton("Xoá");
    public JButton btnLamMoi = new JButton("Làm mới");

    public JButton btnTimKiem = new JButton("Tìm kiếm");
    public JButton btnXuatExcel = new JButton("Xuất Excel");
    public JButton btnNhapExcel = new JButton("Nhập Excel");

    public JButton btnQuayLai = new JButton("Quay lại");

    // ===== TABLE =====
    public DefaultTableModel model = new DefaultTableModel(
            new Object[]{"Mã GV", "Tên GV", "Học vị", "Học hàm", "Email", "Điện thoại", "Mã khoa", "Trạng thái"}, 0
    ) {
        @Override public boolean isCellEditable(int row, int column) { return false; }
    };
    public JTable tblGiangVien = new JTable(model);

    public view_QLGiangVien() {
        setTitle("Quản lý giảng viên");
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

        JLabel lblTitle = new JLabel("Quản lý giảng viên", SwingConstants.CENTER);
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
        inputWrap.setBorder(BorderFactory.createTitledBorder("Thông tin giảng viên"));

        // 2 hàng x 6 cột giống hệt view SV
        JPanel form = new JPanel(new GridLayout(2, 6, 12, 10));
        form.setOpaque(false);
        form.setBorder(new EmptyBorder(12, 14, 12, 14));

        // Row 1: 6 ô
        form.add(fieldBox("Mã giảng viên", txtMaGV, fLabel, fInput));
        form.add(fieldBox("Tên giảng viên", txtTenGV, fLabel, fInput));
        form.add(fieldBox("Học vị", txtHocVi, fLabel, fInput));
        form.add(fieldBox("Học hàm", txtHocHam, fLabel, fInput));
        form.add(fieldBox("Email", txtEmail, fLabel, fInput));
        form.add(fieldBox("Điện thoại", txtDienThoai, fLabel, fInput));

        // Row 2: mình vẫn giữ 6 slot để layout giống
        form.add(fieldBox("Mã khoa", txtMaKhoa, fLabel, fInput));
        form.add(comboBox("Trạng thái", cbTrangThai, fLabel, fInput));
        form.add(new JPanel());
        form.add(new JPanel());
        form.add(new JPanel());
        form.add(new JPanel());

        for (Component c : form.getComponents()) {
            if (c instanceof JPanel) ((JPanel) c).setOpaque(false);
        }

        // ===== ACTION BAR (nút + tìm kiếm + excel) =====
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
        tableWrap.setBorder(BorderFactory.createTitledBorder("Danh sách giảng viên"));

        tblGiangVien.setRowHeight(30);
        tblGiangVien.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        tblGiangVien.setGridColor(new Color(230, 230, 230));
        tblGiangVien.setShowGrid(true);
        tblGiangVien.setSelectionBackground(new Color(210, 225, 245));
        tblGiangVien.setSelectionForeground(Color.BLACK);

        JTableHeader header = tblGiangVien.getTableHeader();
        header.setFont(new Font("Segoe UI", Font.BOLD, 13));
        header.setPreferredSize(new Dimension(header.getPreferredSize().width, 34));

        DefaultTableCellRenderer center = new DefaultTableCellRenderer();
        center.setHorizontalAlignment(SwingConstants.CENTER);

        // Center các cột hợp lý
        tblGiangVien.getColumnModel().getColumn(0).setCellRenderer(center); // Mã GV
        tblGiangVien.getColumnModel().getColumn(7).setCellRenderer(center); // Trạng thái

        // set width gần giống style SV
        tblGiangVien.getColumnModel().getColumn(0).setPreferredWidth(120);
        tblGiangVien.getColumnModel().getColumn(1).setPreferredWidth(220);
        tblGiangVien.getColumnModel().getColumn(2).setPreferredWidth(120);
        tblGiangVien.getColumnModel().getColumn(3).setPreferredWidth(120);
        tblGiangVien.getColumnModel().getColumn(4).setPreferredWidth(200);
        tblGiangVien.getColumnModel().getColumn(5).setPreferredWidth(140);
        tblGiangVien.getColumnModel().getColumn(6).setPreferredWidth(120);
        tblGiangVien.getColumnModel().getColumn(7).setPreferredWidth(120);

        JScrollPane sp = new JScrollPane(tblGiangVien);
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

    // ===== Helpers UI (y hệt view SV) =====
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
