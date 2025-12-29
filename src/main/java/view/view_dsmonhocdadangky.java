package view;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

public class view_dsmonhocdadangky extends JPanel {

    public JTextField txtMaMon = new JTextField();
    public JTextField txtTenMon = new JTextField();
    public JButton btnTim = new JButton("Tìm kiếm");

    public JTable table;
    public DefaultTableModel model;

    public view_dsmonhocdadangky() {
        setLayout(new BorderLayout(12, 12));
        setBackground(new Color(245, 246, 248));
        setBorder(new EmptyBorder(12, 12, 12, 12));

        JPanel topBar = new JPanel(new BorderLayout());
        topBar.setBackground(Color.WHITE);
        topBar.setBorder(new EmptyBorder(10, 12, 10, 12));

        JLabel title = new JLabel("Danh sách môn đã đăng ký", SwingConstants.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 18));
        topBar.add(title, BorderLayout.CENTER);

        JPanel searchWrap = new JPanel(new BorderLayout());
        searchWrap.setBackground(Color.WHITE);
        searchWrap.setBorder(BorderFactory.createTitledBorder("Tìm kiếm"));

        JPanel search = new JPanel(new FlowLayout(FlowLayout.LEFT, 12, 10));
        search.setOpaque(false);

        Font fLabel = new Font("Segoe UI", Font.PLAIN, 13);
        Font fInput = new Font("Segoe UI", Font.PLAIN, 13);

        JLabel lbMa = new JLabel("Mã môn");
        lbMa.setFont(fLabel);
        JLabel lbTen = new JLabel("Tên môn");
        lbTen.setFont(fLabel);

        styleTextField(txtMaMon, fInput, 180);
        styleTextField(txtTenMon, fInput, 260);

        stylePrimary(btnTim);

        search.add(lbMa);
        search.add(txtMaMon);
        search.add(lbTen);
        search.add(txtTenMon);
        search.add(btnTim);

        searchWrap.add(search, BorderLayout.WEST);

        JPanel tableWrap = new JPanel(new BorderLayout());
        tableWrap.setBackground(Color.WHITE);
        tableWrap.setBorder(BorderFactory.createTitledBorder("Danh sách"));

        model = new DefaultTableModel(
                new String[]{
                        "Mã môn", "Tên môn", "Số TC", "Lớp", "Lịch học",
                        "Học kỳ", "Ngày bắt đầu", "Ngày kết thúc"
                }, 0
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
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
        table.getColumnModel().getColumn(2).setCellRenderer(center); // Số TC
        table.getColumnModel().getColumn(3).setCellRenderer(center); // Lớp
        table.getColumnModel().getColumn(5).setCellRenderer(center); // Học kỳ
        table.getColumnModel().getColumn(6).setCellRenderer(center); // Ngày bắt đầu
        table.getColumnModel().getColumn(7).setCellRenderer(center); // Ngày kết thúc

        table.getColumnModel().getColumn(0).setPreferredWidth(110);
        table.getColumnModel().getColumn(1).setPreferredWidth(200);
        table.getColumnModel().getColumn(2).setPreferredWidth(80);
        table.getColumnModel().getColumn(3).setPreferredWidth(90);
        table.getColumnModel().getColumn(4).setPreferredWidth(180);
        table.getColumnModel().getColumn(5).setPreferredWidth(90);
        table.getColumnModel().getColumn(6).setPreferredWidth(140);
        table.getColumnModel().getColumn(7).setPreferredWidth(140);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(new EmptyBorder(10, 10, 10, 10));
        scrollPane.getViewport().setBackground(Color.WHITE);

        tableWrap.add(scrollPane, BorderLayout.CENTER);

        JPanel centerWrap = new JPanel();
        centerWrap.setLayout(new BoxLayout(centerWrap, BoxLayout.Y_AXIS));
        centerWrap.setOpaque(false);

        centerWrap.add(searchWrap);
        centerWrap.add(Box.createVerticalStrut(12));
        centerWrap.add(tableWrap);

        add(topBar, BorderLayout.NORTH);
        add(centerWrap, BorderLayout.CENTER);
    }

    private void styleTextField(JTextField t, Font fInput, int width) {
        t.setFont(fInput);
        t.setPreferredSize(new Dimension(width, 36));
        t.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200)),
                new EmptyBorder(6, 8, 6, 8)
        ));
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
