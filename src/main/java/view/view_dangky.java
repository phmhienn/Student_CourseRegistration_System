package view;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

public class view_dangky extends JPanel {

    public JTextField txtMaMon = new JTextField();
    public JTextField txtTenMon = new JTextField();
    public JTextField txtSoTinChi = new JTextField();

    public JComboBox<String> cbLop = new JComboBox<>();
    public JComboBox<String> cbLichHoc = new JComboBox<>();
    public JComboBox<String> cbHocKy = new JComboBox<>();

    public JButton btnDangKy = new JButton("Đăng ký");
    public JButton btnHuy = new JButton("Hủy");
    public JButton btnTim = new JButton("Tìm kiếm");

    public JTable table;
    public DefaultTableModel model;

    public view_dangky() {
        setLayout(new BorderLayout(12, 12));
        setBorder(new EmptyBorder(12, 12, 12, 12));
        setBackground(new Color(245, 246, 248));

        Font fTitle = new Font("Segoe UI", Font.BOLD, 18);
        Font fLabel = new Font("Segoe UI", Font.PLAIN, 13);
        Font fInput = new Font("Segoe UI", Font.PLAIN, 13);

        JPanel topBar = new JPanel(new BorderLayout());
        topBar.setBackground(Color.WHITE);
        topBar.setBorder(new EmptyBorder(10, 12, 10, 12));

        JLabel lblTitle = new JLabel("Đăng ký môn học", SwingConstants.CENTER);
        lblTitle.setFont(fTitle);
        topBar.add(lblTitle, BorderLayout.CENTER);

        add(topBar, BorderLayout.NORTH);

        JPanel centerWrap = new JPanel();
        centerWrap.setOpaque(false);
        centerWrap.setLayout(new BoxLayout(centerWrap, BoxLayout.Y_AXIS));

        JPanel inputWrap = new JPanel();
        inputWrap.setBackground(Color.WHITE);
        inputWrap.setBorder(BorderFactory.createTitledBorder("Thông tin đăng ký"));
        inputWrap.setLayout(new BoxLayout(inputWrap, BoxLayout.Y_AXIS));

        JPanel row1 = new JPanel(new GridLayout(1, 3, 18, 0));
        row1.setOpaque(false);
        row1.setBorder(new EmptyBorder(10, 16, 6, 16));

        row1.add(fieldBox("Mã môn", txtMaMon, fLabel, fInput));
        row1.add(fieldBox("Tên môn", txtTenMon, fLabel, fInput));
        row1.add(fieldBox("Số tín chỉ", txtSoTinChi, fLabel, fInput));

        JPanel row2 = new JPanel(new GridLayout(1, 3, 18, 0));
        row2.setOpaque(false);
        row2.setBorder(new EmptyBorder(6, 16, 6, 16));

        row2.add(comboBox("Lớp học phần", cbLop, fLabel, fInput));
        row2.add(comboBox("Lịch học", cbLichHoc, fLabel, fInput));
        row2.add(comboBox("Học kỳ", cbHocKy, fLabel, fInput));

        JPanel row3 = new JPanel(new FlowLayout(FlowLayout.CENTER, 14, 10));
        row3.setOpaque(false);
        row3.setBorder(new EmptyBorder(2, 16, 12, 16));

        stylePrimary(btnDangKy);
        styleDanger(btnHuy);
        styleNeutral(btnTim);

        row3.add(btnDangKy);
        row3.add(btnHuy);
        row3.add(btnTim);

        inputWrap.add(row1);
        inputWrap.add(row2);
        inputWrap.add(row3);

        JPanel tableWrap = new JPanel(new BorderLayout());
        tableWrap.setBackground(Color.WHITE);
        tableWrap.setBorder(BorderFactory.createTitledBorder("Danh sách môn để đăng ký"));

        model = new DefaultTableModel(
                new String[]{"Mã môn", "Tên môn", "Số TC", "Lớp", "Lịch học", "Học kỳ"}, 0
        ) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };

        table = new JTable(model);
        table.setRowHeight(30);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        table.setGridColor(new Color(230, 230, 230));
        table.setShowGrid(true);
        table.setSelectionBackground(new Color(210, 225, 245));
        table.setSelectionForeground(Color.BLACK);

        JTableHeader header = table.getTableHeader();
        header.setFont(new Font("Segoe UI", Font.BOLD, 13));
        header.setPreferredSize(new Dimension(header.getPreferredSize().width, 34));

        DefaultTableCellRenderer center = new DefaultTableCellRenderer();
        center.setHorizontalAlignment(SwingConstants.CENTER);
        table.getColumnModel().getColumn(0).setCellRenderer(center); // ma
        table.getColumnModel().getColumn(2).setCellRenderer(center); // tc
        table.getColumnModel().getColumn(3).setCellRenderer(center); // lop
        table.getColumnModel().getColumn(5).setCellRenderer(center); // hoc ky

        // width
        table.getColumnModel().getColumn(0).setPreferredWidth(120);
        table.getColumnModel().getColumn(1).setPreferredWidth(320);
        table.getColumnModel().getColumn(2).setPreferredWidth(90);
        table.getColumnModel().getColumn(3).setPreferredWidth(130);
        table.getColumnModel().getColumn(4).setPreferredWidth(220);
        table.getColumnModel().getColumn(5).setPreferredWidth(120);

        JScrollPane scroll = new JScrollPane(table);
        scroll.setBorder(new EmptyBorder(10, 10, 10, 10));
        scroll.getViewport().setBackground(Color.WHITE);

        tableWrap.add(scroll, BorderLayout.CENTER);

        centerWrap.add(inputWrap);
        centerWrap.add(Box.createVerticalStrut(12));
        centerWrap.add(tableWrap);

        add(centerWrap, BorderLayout.CENTER);
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
