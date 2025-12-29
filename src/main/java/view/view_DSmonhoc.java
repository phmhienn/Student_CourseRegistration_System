package view;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

public class view_DSmonhoc extends JPanel {

    public JTextField txtTim = new JTextField();
    public JButton btnTim = new JButton("Tìm kiếm");

    public JTable table;
    public DefaultTableModel model;

    public view_DSmonhoc() {
        setLayout(new BorderLayout(12, 12));
        setBorder(new EmptyBorder(12, 12, 12, 12));
        setBackground(new Color(245, 246, 248));

        Font fTitle = new Font("Segoe UI", Font.BOLD, 18);
        Font fLabel = new Font("Segoe UI", Font.PLAIN, 13);
        Font fInput = new Font("Segoe UI", Font.PLAIN, 13);

        JPanel topBar = new JPanel(new BorderLayout());
        topBar.setBackground(Color.WHITE);
        topBar.setBorder(new EmptyBorder(10, 12, 10, 12));

        JLabel lblTitle = new JLabel("DANH SÁCH MÔN HỌC", SwingConstants.CENTER);
        lblTitle.setFont(fTitle);
        topBar.add(lblTitle, BorderLayout.CENTER);

        JPanel searchWrap = new JPanel(new BorderLayout());
        searchWrap.setBackground(Color.WHITE);
        searchWrap.setBorder(BorderFactory.createTitledBorder("Tìm kiếm"));

        JPanel row = new JPanel(new BorderLayout(12, 0));
        row.setOpaque(false);
        row.setBorder(new EmptyBorder(10, 12, 10, 12));

        JPanel left = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        left.setOpaque(false);

        JLabel lblKey = new JLabel("Từ khoá");
        lblKey.setFont(fLabel);

        txtTim.setFont(fInput);
        txtTim.setPreferredSize(new Dimension(320, 36));
        txtTim.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200)),
                new EmptyBorder(6, 8, 6, 8)
        ));

        stylePrimary(btnTim);

        left.add(lblKey);
        left.add(txtTim);
        left.add(btnTim);

        searchWrap.add(left, BorderLayout.WEST);
        searchWrap.add(row, BorderLayout.CENTER);

        model = new DefaultTableModel(
                new String[]{
                        "Mã môn",
                        "Tên môn",
                        "Số tín chỉ",
                        "Số tiết lý thuyết",
                        "Số tiết thực hành",
                        "Mã khoa"
                }, 0
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

        table.getColumnModel().getColumn(2).setCellRenderer(center);
        table.getColumnModel().getColumn(3).setCellRenderer(center);
        table.getColumnModel().getColumn(4).setCellRenderer(center);
        table.getColumnModel().getColumn(5).setCellRenderer(center);

        table.getColumnModel().getColumn(0).setPreferredWidth(120);
        table.getColumnModel().getColumn(1).setPreferredWidth(220);
        table.getColumnModel().getColumn(2).setPreferredWidth(120);
        table.getColumnModel().getColumn(3).setPreferredWidth(150);
        table.getColumnModel().getColumn(4).setPreferredWidth(150);
        table.getColumnModel().getColumn(5).setPreferredWidth(120);

        JScrollPane scroll = new JScrollPane(table);
        scroll.setBorder(new EmptyBorder(10, 10, 10, 10));
        scroll.getViewport().setBackground(Color.WHITE);

        JPanel tableWrap = new JPanel(new BorderLayout());
        tableWrap.setBackground(Color.WHITE);
        tableWrap.setBorder(BorderFactory.createTitledBorder("Danh sách"));
        tableWrap.add(scroll, BorderLayout.CENTER);

        JPanel centerr = new JPanel();
        centerr.setOpaque(false);
        centerr.setLayout(new BoxLayout(centerr, BoxLayout.Y_AXIS));
        centerr.add(searchWrap);
        centerr.add(Box.createVerticalStrut(12));
        centerr.add(tableWrap);

        add(topBar, BorderLayout.NORTH);
        add(centerr, BorderLayout.CENTER);
    }

    private void stylePrimary(JButton b) {
        styleButton(b, new Color(45, 108, 223), Color.WHITE, new Color(30, 90, 200));
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
