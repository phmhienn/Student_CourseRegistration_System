package view;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

public class view_QLLopHocPhan extends JFrame {

    public JTextField txtMaLHP = new JTextField();
    public JTextField txtMaMon = new JTextField();
    public JTextField txtTenMon = new JTextField();
    public JTextField txtSoTinChi = new JTextField();
    public JTextField txtMaGV = new JTextField();
    public JTextField txtSoLuongToiDa = new JTextField();
    public JTextField txtSoLuongDaDK = new JTextField();

    public JComboBox<String> cboHocKy = new JComboBox<>();
    public JComboBox<String> cboTrangThai = new JComboBox<>();

    public JButton btnThem = new JButton("Thêm lớp");
    public JButton btnSua = new JButton("Sửa lớp");
    public JButton btnXoa = new JButton("Xoá lớp");
    public JButton btnLamMoi = new JButton("Làm mới");
    public JButton btnQuayLai = new JButton("Quay lại");

    public JTable tblLopHocPhan;
    public DefaultTableModel tableModel;

    public view_QLLopHocPhan() {
        setTitle("Quản lý lớp học phần");
        setSize(1180, 650);
        setMinimumSize(new Dimension(1060, 600));
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

        JPanel topBar = new JPanel(new BorderLayout());
        topBar.setBackground(Color.WHITE);
        topBar.setBorder(new EmptyBorder(10, 12, 10, 12));

        JLabel lblTitle = new JLabel("Quản lý lớp học phần", SwingConstants.CENTER);
        lblTitle.setFont(fTitle);

        JPanel topRight = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 0));
        topRight.setOpaque(false);
        styleNeutral(btnQuayLai);
        topRight.add(btnQuayLai);

        topBar.add(lblTitle, BorderLayout.CENTER);
        topBar.add(topRight, BorderLayout.EAST);

        JPanel inputWrap = new JPanel(new BorderLayout(12, 12));
        inputWrap.setBackground(Color.WHITE);
        inputWrap.setBorder(BorderFactory.createTitledBorder("Thông tin lớp học phần"));

        JPanel form = new JPanel(new GridLayout(2, 4, 14, 10));
        form.setOpaque(false);
        form.setBorder(new EmptyBorder(12, 14, 12, 14));

        form.add(fieldBox("Mã LHP", txtMaLHP, fLabel, fInput));
        form.add(fieldBox("Mã môn", txtMaMon, fLabel, fInput));
        form.add(fieldBox("Tên môn", txtTenMon, fLabel, fInput));
        form.add(fieldBox("Số tín chỉ", txtSoTinChi, fLabel, fInput));

        form.add(fieldBox("Mã giảng viên", txtMaGV, fLabel, fInput));
        form.add(comboBox("Học kỳ", cboHocKy, fLabel, fInput));
        form.add(fieldBox("SL tối đa", txtSoLuongToiDa, fLabel, fInput));
        form.add(comboBox("Trạng thái", cboTrangThai, fLabel, fInput));

        JPanel actionBar = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        actionBar.setOpaque(false);
        actionBar.setBorder(new EmptyBorder(0, 14, 10, 14));

        stylePrimary(btnThem);
        stylePrimary(btnSua);
        styleDanger(btnXoa);
        styleNeutral(btnLamMoi);

        actionBar.add(btnThem);
        actionBar.add(btnSua);
        actionBar.add(btnXoa);
        actionBar.add(btnLamMoi);

        inputWrap.add(form, BorderLayout.CENTER);
        inputWrap.add(actionBar, BorderLayout.SOUTH);

        JPanel tableWrap = new JPanel(new BorderLayout());
        tableWrap.setBackground(Color.WHITE);
        tableWrap.setBorder(BorderFactory.createTitledBorder("Danh sách lớp học phần"));

        String[] cols = {
                "Mã LHP", "Mã môn", "Tên môn", "Số TC",
                "Mã GV", "Học kỳ", "SL tối đa", "Đã ĐK", "Trạng thái"
        };

        tableModel = new DefaultTableModel(cols, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };

        tblLopHocPhan = new JTable(tableModel);

        tblLopHocPhan.setRowHeight(30);
        tblLopHocPhan.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        tblLopHocPhan.setGridColor(new Color(230, 230, 230));
        tblLopHocPhan.setShowGrid(true);
        tblLopHocPhan.setSelectionBackground(new Color(210, 225, 245));
        tblLopHocPhan.setSelectionForeground(Color.BLACK);

        JTableHeader header = tblLopHocPhan.getTableHeader();
        header.setFont(new Font("Segoe UI", Font.BOLD, 13));
        header.setPreferredSize(new Dimension(header.getPreferredSize().width, 34));

        DefaultTableCellRenderer center = new DefaultTableCellRenderer();
        center.setHorizontalAlignment(SwingConstants.CENTER);
        tblLopHocPhan.getColumnModel().getColumn(3).setCellRenderer(center); // so tc
        tblLopHocPhan.getColumnModel().getColumn(5).setCellRenderer(center); // hoc ky
        tblLopHocPhan.getColumnModel().getColumn(6).setCellRenderer(center); // sl toi da
        tblLopHocPhan.getColumnModel().getColumn(7).setCellRenderer(center); // da dk
        tblLopHocPhan.getColumnModel().getColumn(8).setCellRenderer(center); // trang thai

        tblLopHocPhan.getColumnModel().getColumn(0).setPreferredWidth(120);
        tblLopHocPhan.getColumnModel().getColumn(1).setPreferredWidth(110);
        tblLopHocPhan.getColumnModel().getColumn(2).setPreferredWidth(340);
        tblLopHocPhan.getColumnModel().getColumn(3).setPreferredWidth(80);
        tblLopHocPhan.getColumnModel().getColumn(4).setPreferredWidth(110);
        tblLopHocPhan.getColumnModel().getColumn(5).setPreferredWidth(110);
        tblLopHocPhan.getColumnModel().getColumn(6).setPreferredWidth(110);
        tblLopHocPhan.getColumnModel().getColumn(7).setPreferredWidth(90);
        tblLopHocPhan.getColumnModel().getColumn(8).setPreferredWidth(140);

        JScrollPane scroll = new JScrollPane(tblLopHocPhan);
        scroll.setBorder(new EmptyBorder(10, 10, 10, 10));
        scroll.getViewport().setBackground(Color.WHITE);

        tableWrap.add(scroll, BorderLayout.CENTER);

        JPanel centerWrap = new JPanel();
        centerWrap.setLayout(new BoxLayout(centerWrap, BoxLayout.Y_AXIS));
        centerWrap.setOpaque(false);

        centerWrap.add(inputWrap);
        centerWrap.add(Box.createVerticalStrut(12));
        centerWrap.add(tableWrap);

        root.add(topBar, BorderLayout.NORTH);
        root.add(centerWrap, BorderLayout.CENTER);
    }

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
        field.setBorder(BorderFactory.createCompoundBorder(
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
