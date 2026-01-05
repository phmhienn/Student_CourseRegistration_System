package view;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

public class view_QuanLyLopHocPhan extends JFrame {

    /* ===================== INPUT ===================== */
    public JTextField txtMaLHP = new JTextField();
    public JTextField txtSoLuong = new JTextField();
    public JTextField txtPhong = new JTextField();
    public JTextField txtTim = new JTextField();

    /* ===================== COMBO ===================== */
    public JComboBox cboGiangVien = new JComboBox();
    public JComboBox<String> cboMonHoc = new JComboBox<>();
    public JComboBox<String> cboHocKy = new JComboBox<>();
    public JComboBox<String> cboThu = new JComboBox<>(new String[]{
            "Thứ 2","Thứ 3","Thứ 4","Thứ 5","Thứ 6","Thứ 7","Chủ nhật"
    });
    public JComboBox<String> cboCaHoc = new JComboBox<>(new String[]{
            "Ca 1","Ca 2","Ca 3","Ca 4","Ca 5","Ca 6","Ca 7","Ca 8","Ca 9","Ca 10","Ca 11","Ca 12"
    });

    /* ===================== BUTTON ===================== */
    public JButton btnThem = new JButton("Thêm");
    public JButton btnSua = new JButton("Sửa");
    public JButton btnXoa = new JButton("Xoá");
    public JButton btnLamMoi = new JButton("Làm mới");
    public JButton btnTim = new JButton("Tìm kiếm");
    public JButton btnNhapExcel = new JButton("Nhập Excel");
    public JButton btnXuatExcel = new JButton("Xuất Excel");
    public JButton btnQuayLai = new JButton("Quay lại");

    /* ===================== TABLE ===================== */
    public DefaultTableModel dtm = new DefaultTableModel(
            new String[]{
                    "Mã LHP","Mã môn","Tên môn","Học kỳ",
                    "Giảng viên","Số lượng","Thứ","Ca","Phòng"
            }, 0
    ) {
        @Override public boolean isCellEditable(int r, int c) { return false; }
    };

    public JTable tbl = new JTable(dtm);

    public view_QuanLyLopHocPhan() {
        setTitle("Quản lý lớp học phần");
        setSize(1250, 720);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        try { UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); }
        catch (Exception ignored) {}

        Font fTitle = new Font("Segoe UI", Font.BOLD, 18);
        Font fLabel = new Font("Segoe UI", Font.PLAIN, 13);
        Font fInput = new Font("Segoe UI", Font.PLAIN, 13);

        JPanel root = new JPanel(new BorderLayout(12,12));
        root.setBorder(new EmptyBorder(12,12,12,12));
        root.setBackground(new Color(245,246,248));
        setContentPane(root);

        /* ===================== TOP ===================== */
        JPanel top = new JPanel(new BorderLayout());
        top.setBackground(Color.WHITE);
        top.setBorder(new EmptyBorder(10,12,10,12));

        JLabel title = new JLabel("Quản lý lớp học phần", SwingConstants.CENTER);
        title.setFont(fTitle);

        JPanel right = new JPanel(new FlowLayout(FlowLayout.RIGHT,10,0));
        right.setOpaque(false);
        styleNeutral(btnQuayLai);
        right.add(btnQuayLai);

        top.add(title, BorderLayout.CENTER);
        top.add(right, BorderLayout.EAST);

        /* ===================== INPUT ===================== */
        JPanel inputWrap = new JPanel();
        inputWrap.setBackground(Color.WHITE);
        inputWrap.setBorder(BorderFactory.createTitledBorder("Thông tin lớp học phần"));
        inputWrap.setLayout(new BoxLayout(inputWrap, BoxLayout.Y_AXIS));

        // ROW 1
        JPanel row1 = new JPanel();
        row1.setLayout(new BoxLayout(row1, BoxLayout.X_AXIS));
        row1.setOpaque(false);
        row1.setBorder(new EmptyBorder(10,16,6,16));

        row1.add(fieldBox("Mã LHP", txtMaLHP, fLabel, fInput));
        row1.add(Box.createHorizontalStrut(18));
        row1.add(comboBox("Môn học", cboMonHoc, fLabel, fInput));
        row1.add(Box.createHorizontalStrut(18));
        row1.add(comboBox("Học kỳ", cboHocKy, fLabel, fInput));
        row1.add(Box.createHorizontalStrut(18));
        row1.add(comboBox("Giảng viên", cboGiangVien, fLabel, fInput));

        // ROW 2
        JPanel row2 = new JPanel();
        row2.setLayout(new BoxLayout(row2, BoxLayout.X_AXIS));
        row2.setOpaque(false);
        row2.setBorder(new EmptyBorder(6,16,6,16));

        row2.add(fieldBox("Số lượng", txtSoLuong, fLabel, fInput));
        row2.add(Box.createHorizontalStrut(18));
        row2.add(comboBox("Thứ", cboThu, fLabel, fInput));
        row2.add(Box.createHorizontalStrut(18));
        row2.add(comboBox("Ca học", cboCaHoc, fLabel, fInput));
        row2.add(Box.createHorizontalStrut(18));
        row2.add(fieldBox("Phòng", txtPhong, fLabel, fInput));

        // ROW 3 BUTTON
        JPanel row3 = new JPanel(new FlowLayout(FlowLayout.LEFT,10,0));
        row3.setOpaque(false);
        row3.setBorder(new EmptyBorder(8,16,12,16));

        stylePrimary(btnThem);
        stylePrimary(btnSua);
        styleDanger(btnXoa);
        styleNeutral(btnLamMoi);
        stylePrimary(btnTim);
        stylePrimary(btnNhapExcel);
        stylePrimary(btnXuatExcel);

        txtTim.setPreferredSize(new Dimension(200,36));

        row3.add(btnThem);
        row3.add(btnSua);
        row3.add(btnXoa);
        row3.add(btnLamMoi);
        row3.add(new JLabel("Từ khoá"));
        row3.add(txtTim);
        row3.add(btnTim);
        row3.add(btnNhapExcel);
        row3.add(btnXuatExcel);

        inputWrap.add(row1);
        inputWrap.add(row2);
        inputWrap.add(row3);

        /* ===================== TABLE ===================== */
        JPanel tableWrap = new JPanel(new BorderLayout());
        tableWrap.setBackground(Color.WHITE);
        tableWrap.setBorder(BorderFactory.createTitledBorder("Danh sách lớp học phần"));

        tbl.setRowHeight(30);
        tbl.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        tbl.setSelectionBackground(new Color(210,225,245));

        JTableHeader header = tbl.getTableHeader();
        header.setFont(new Font("Segoe UI", Font.BOLD, 13));
        header.setPreferredSize(new Dimension(header.getPreferredSize().width, 34));


        JScrollPane sp = new JScrollPane(tbl);
        sp.setBorder(new EmptyBorder(10,10,10,10));
        tableWrap.add(sp, BorderLayout.CENTER);

        JPanel center = new JPanel();
        center.setLayout(new BoxLayout(center, BoxLayout.Y_AXIS));
        center.setOpaque(false);

        center.add(inputWrap);
        center.add(Box.createVerticalStrut(12));
        center.add(tableWrap);

        root.add(top, BorderLayout.NORTH);
        root.add(center, BorderLayout.CENTER);
    }

    private JPanel fieldBox(String label, JTextField field, Font fLabel, Font fInput) {
        JPanel p = new JPanel();
        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
        p.setOpaque(false);

        JLabel l = new JLabel(label);
        l.setFont(fLabel);

        field.setFont(fInput);
        field.setPreferredSize(new Dimension(10,36));
        field.setMaximumSize(new Dimension(Integer.MAX_VALUE,36));

        p.add(l);
        p.add(Box.createVerticalStrut(6));
        p.add(field);
        return p;
    }

    private JPanel comboBox(String label, JComboBox<?> cb, Font fLabel, Font fInput) {
        JPanel p = new JPanel();
        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
        p.setOpaque(false);

        JLabel l = new JLabel(label);
        l.setFont(fLabel);

        cb.setFont(fInput);
        cb.setPreferredSize(new Dimension(10,36));
        cb.setMaximumSize(new Dimension(Integer.MAX_VALUE,36));

        p.add(l);
        p.add(Box.createVerticalStrut(6));
        p.add(cb);
        return p;
    }

    private void stylePrimary(JButton b) {
        styleButton(b, new Color(45,108,223), Color.WHITE, new Color(30,90,200));
    }

    private void styleDanger(JButton b) {
        styleButton(b, new Color(210,55,75), Color.WHITE, new Color(185,40,60));
    }

    private void styleNeutral(JButton b) {
        styleButton(b, new Color(235,235,235), Color.BLACK, new Color(220,220,220));
    }

    private void styleButton(JButton b, Color bg, Color fg, Color hover) {
        b.setFocusPainted(false);
        b.setBorderPainted(false);
        b.setOpaque(true);
        b.setBackground(bg);
        b.setForeground(fg);
        b.setFont(new Font("Segoe UI", Font.BOLD, 13));
        b.setMargin(new Insets(10,18,10,18));
        b.setCursor(new Cursor(Cursor.HAND_CURSOR));

        b.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) { b.setBackground(hover); }
            public void mouseExited(MouseEvent e) { b.setBackground(bg); }
        });
    }
}
