package view;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

public class view_HocPhi extends JFrame {

    public JComboBox<String> cbSinhVien = new JComboBox<>();
    public JComboBox<String> cbHocKy = new JComboBox<>();

    public JTextField txtTongTinChi = new JTextField();
    public JTextField txtTongTien = new JTextField();
    public JTextField txtTrangThai = new JTextField();

    public JButton btnTinhHocPhi = new JButton("Tính học phí");
    public JButton btnLuu = new JButton("Lưu / Cập nhật");
    public JButton btnDaDong = new JButton("Đánh dấu đã đóng");
    public JButton btnXuatExcel = new JButton("Xuất Excel");
    public JButton btnQuayLai = new JButton("Quay lại");

    public DefaultTableModel model = new DefaultTableModel(
            new Object[]{"Mã SV", "Học kỳ", "Tổng TC", "Tổng tiền", "Trạng thái"}, 0
    ) {
        @Override public boolean isCellEditable(int r, int c) { return false; }
    };
    public JTable tblHocPhi = new JTable(model);

    public view_HocPhi() {
        setTitle("Quản lý học phí");
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

        JPanel topBar = new JPanel(new BorderLayout());
        topBar.setBackground(Color.WHITE);
        topBar.setBorder(new EmptyBorder(10, 12, 10, 12));

        JLabel lblTitle = new JLabel("Quản lý học phí", SwingConstants.CENTER);
        lblTitle.setFont(fTitle);

        JPanel topRight = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 0));
        topRight.setOpaque(false);
        styleNeutral(btnQuayLai);
        topRight.add(btnQuayLai);

        topBar.add(lblTitle, BorderLayout.CENTER);
        topBar.add(topRight, BorderLayout.EAST);

        JPanel inputWrap = new JPanel(new BorderLayout(12, 12));
        inputWrap.setBackground(Color.WHITE);
        inputWrap.setBorder(BorderFactory.createTitledBorder("Thông tin học phí"));

        JPanel form = new JPanel(new GridLayout(2, 3, 12, 10));
        form.setOpaque(false);
        form.setBorder(new EmptyBorder(12, 14, 12, 14));

        txtTongTinChi.setEditable(false);
        txtTongTien.setEditable(false);
        txtTrangThai.setEditable(false);
        
        form.add(comboBox("Sinh viên", cbSinhVien, fLabel, fInput));
        form.add(comboBox("Học kỳ", cbHocKy, fLabel, fInput));
        form.add(fieldBox("Trạng thái", txtTrangThai, fLabel, fInput));

        form.add(fieldBox("Tổng tín chỉ", txtTongTinChi, fLabel, fInput));
        form.add(fieldBox("Tổng tiền (VNĐ)", txtTongTien, fLabel, fInput));
        form.add(new JPanel());

        for (Component c : form.getComponents()) {
            if (c instanceof JPanel) ((JPanel) c).setOpaque(false);
        }

        JPanel actionBar = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        actionBar.setOpaque(false);
        actionBar.setBorder(new EmptyBorder(0, 14, 12, 14));

        stylePrimary(btnTinhHocPhi);
        stylePrimary(btnLuu);
        styleSuccess(btnDaDong);
        stylePrimary(btnXuatExcel);

        actionBar.add(btnTinhHocPhi);
        actionBar.add(btnLuu);
        actionBar.add(btnDaDong);
        actionBar.add(btnXuatExcel);

        inputWrap.add(form, BorderLayout.CENTER);
        inputWrap.add(actionBar, BorderLayout.SOUTH);

        JPanel tableWrap = new JPanel(new BorderLayout());
        tableWrap.setBackground(Color.WHITE);
        tableWrap.setBorder(BorderFactory.createTitledBorder("Danh sách học phí"));

        tblHocPhi.setRowHeight(30);
        tblHocPhi.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        tblHocPhi.setGridColor(new Color(230, 230, 230));
        tblHocPhi.setShowGrid(true);
        tblHocPhi.setSelectionBackground(new Color(210, 225, 245));
        tblHocPhi.setSelectionForeground(Color.BLACK);

        JTableHeader header = tblHocPhi.getTableHeader();
        header.setFont(new Font("Segoe UI", Font.BOLD, 13));
        header.setPreferredSize(new Dimension(header.getPreferredSize().width, 34));

        DefaultTableCellRenderer center = new DefaultTableCellRenderer();
        center.setHorizontalAlignment(SwingConstants.CENTER);
        for (int i = 0; i < tblHocPhi.getColumnCount(); i++) {
            tblHocPhi.getColumnModel().getColumn(i).setCellRenderer(center);
        }

        JScrollPane sp = new JScrollPane(tblHocPhi);
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
        styleButton(b, new Color(45, 108, 223), Color.WHITE);
    }

    private void styleSuccess(JButton b) {
        styleButton(b, new Color(40, 170, 95), Color.WHITE);
    }

    private void styleNeutral(JButton b) {
        styleButton(b, new Color(235, 235, 235), new Color(40, 40, 40));
    }

    private void styleButton(JButton b, Color bg, Color fg) {
        b.setFocusPainted(false);
        b.setBorderPainted(false);
        b.setOpaque(true);
        b.setBackground(bg);
        b.setForeground(fg);
        b.setFont(new Font("Segoe UI", Font.BOLD, 13));
        b.setMargin(new Insets(10, 18, 10, 18));
    }
}