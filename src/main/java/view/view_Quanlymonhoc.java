package view;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

public class view_Quanlymonhoc extends JFrame {

    public JTextField txtMaMon = new JTextField();
    public JTextField txtTenMon = new JTextField();
    public JTextField txtTinChi = new JTextField();
    public JTextField txtTim = new JTextField();

    public JButton btnThem = new JButton("Thêm");
    public JButton btnSua = new JButton("Sửa");
    public JButton btnXoa = new JButton("Xoá");
    public JButton btnLamMoi = new JButton("Làm mới");

    public JButton btnTim = new JButton("Tìm kiếm");
    public JButton btnXuatExcel = new JButton("Xuất Excel");
    public JButton btnNhapExcel = new JButton("Nhập Excel");

    public JButton btnQuayLai = new JButton("Quay lại");

    public DefaultTableModel dtm = new DefaultTableModel(
            new String[]{"Mã môn", "Tên môn", "Số tín chỉ"}, 0
    ) {
        @Override public boolean isCellEditable(int row, int column) { return false; }
    };
    public JTable tbl = new JTable(dtm);

    public view_Quanlymonhoc() {
        setTitle("Quản lý môn học");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(1050, 650);
        setMinimumSize(new Dimension(980, 560));
        setLocationRelativeTo(null);

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

        JLabel lblTitle = new JLabel("Quản lý môn học", SwingConstants.CENTER);
        lblTitle.setFont(fTitle);

        JPanel rightTop = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        rightTop.setOpaque(false);
        styleNeutral(btnQuayLai);
        rightTop.add(btnQuayLai);

        topBar.add(lblTitle, BorderLayout.CENTER);
        topBar.add(rightTop, BorderLayout.EAST);

        JPanel inputWrap = new JPanel();
        inputWrap.setBackground(Color.WHITE);
        inputWrap.setBorder(BorderFactory.createTitledBorder("Thông tin môn học"));
        inputWrap.setLayout(new BoxLayout(inputWrap, BoxLayout.Y_AXIS));

        JPanel row1 = new JPanel(new GridLayout(1, 3, 18, 0));
        row1.setOpaque(false);
        row1.setBorder(new EmptyBorder(10, 16, 6, 16));
        row1.add(fieldBox("Mã môn", txtMaMon, fLabel, fInput));
        row1.add(fieldBox("Tên môn", txtTenMon, fLabel, fInput));
        row1.add(fieldBox("Số tín chỉ", txtTinChi, fLabel, fInput));

        JPanel row2 = new JPanel(new BorderLayout());
        row2.setOpaque(false);
        row2.setBorder(new EmptyBorder(8, 16, 12, 16));

        JPanel left = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        left.setOpaque(false);

        stylePrimary(btnThem);
        stylePrimary(btnSua);
        styleDanger(btnXoa);
        styleNeutral(btnLamMoi);
        stylePrimary(btnTim);

        JLabel lblKey = new JLabel("Từ khoá");
        lblKey.setFont(fLabel);

        txtTim.setFont(fInput);
        txtTim.setPreferredSize(new Dimension(195, 36));
        txtTim.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200)),
                new EmptyBorder(6, 8, 6, 8)
        ));

        left.add(btnThem);
        left.add(btnSua);
        left.add(btnXoa);
        left.add(btnLamMoi);
        left.add(Box.createHorizontalStrut(18));
        left.add(lblKey);
        left.add(txtTim);
        left.add(btnTim);

        JPanel right = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        right.setOpaque(false);

        stylePrimary(btnXuatExcel);
        styleNeutral(btnNhapExcel);

        right.add(btnXuatExcel);
        right.add(btnNhapExcel);

        row2.add(left, BorderLayout.WEST);
        row2.add(right, BorderLayout.EAST);

        inputWrap.add(row1);
        inputWrap.add(row2);

        JPanel tableWrap = new JPanel(new BorderLayout());
        tableWrap.setBackground(Color.WHITE);
        tableWrap.setBorder(BorderFactory.createTitledBorder("Danh sách môn học"));

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
        tbl.getColumnModel().getColumn(2).setCellRenderer(center);

        tbl.getColumnModel().getColumn(0).setPreferredWidth(160);
        tbl.getColumnModel().getColumn(1).setPreferredWidth(640);
        tbl.getColumnModel().getColumn(2).setPreferredWidth(120);

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
            @Override public void mouseExited(MouseEvent e)  { b.setBackground(normal); }
        });
    }
}
