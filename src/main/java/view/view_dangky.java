package view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

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

        setLayout(new BorderLayout(8, 8));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        JLabel lblTitle = new JLabel("ĐĂNG KÝ MÔN HỌC", JLabel.CENTER);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 26));
        add(lblTitle, BorderLayout.NORTH);
        
        JPanel pnlForm = new JPanel(new BorderLayout(10, 5));
        pnlForm.setBorder(BorderFactory.createTitledBorder("Thông tin môn học"));
        pnlForm.setPreferredSize(new Dimension(0, 230));

        Dimension fieldSize = new Dimension(220, 26);
        txtMaMon.setPreferredSize(fieldSize);
        txtTenMon.setPreferredSize(fieldSize);
        txtSoTinChi.setPreferredSize(fieldSize);
        cbLop.setPreferredSize(fieldSize);
        cbLichHoc.setPreferredSize(fieldSize);
        cbHocKy.setPreferredSize(fieldSize);
        
        JPanel pnlLeft = new JPanel(new GridBagLayout());
        GridBagConstraints g = new GridBagConstraints();
        g.insets = new Insets(4, 4, 4, 4);
        g.anchor = GridBagConstraints.WEST;

        g.gridx = 0; g.gridy = 0;
        pnlLeft.add(new JLabel("Mã môn"), g);
        g.gridx = 1;
        pnlLeft.add(txtMaMon, g);

        g.gridx = 0; g.gridy = 1;
        pnlLeft.add(new JLabel("Tên môn"), g);
        g.gridx = 1;
        pnlLeft.add(txtTenMon, g);

        g.gridx = 0; g.gridy = 2;
        pnlLeft.add(new JLabel("Số tín chỉ"), g);
        g.gridx = 1;
        pnlLeft.add(txtSoTinChi, g);

        JPanel pnlRight = new JPanel(new GridBagLayout());
        GridBagConstraints g2 = new GridBagConstraints();
        g2.insets = new Insets(4, 4, 4, 4);
        g2.anchor = GridBagConstraints.WEST;

        g2.gridx = 0; g2.gridy = 0;
        pnlRight.add(new JLabel("Lớp học phần"), g2);
        g2.gridx = 1;
        pnlRight.add(cbLop, g2);

        g2.gridx = 0; g2.gridy = 1;
        pnlRight.add(new JLabel("Lịch học"), g2);
        g2.gridx = 1;
        pnlRight.add(cbLichHoc, g2);

        g2.gridx = 0; g2.gridy = 2;
        pnlRight.add(new JLabel("Học kỳ"), g2);
        g2.gridx = 1;
        pnlRight.add(cbHocKy, g2);

        JPanel pnlButton = new JPanel(new FlowLayout(FlowLayout.CENTER, 30, 10));
        Dimension btnSize = new Dimension(120, 32);

        btnDangKy.setPreferredSize(btnSize);
        btnHuy.setPreferredSize(btnSize);
        btnTim.setPreferredSize(btnSize);

        btnDangKy.setFocusPainted(false);
        btnHuy.setFocusPainted(false);
        btnTim.setFocusPainted(false);

        pnlButton.add(btnDangKy);
        pnlButton.add(btnHuy);
        pnlButton.add(btnTim);

        JPanel pnlCenter = new JPanel(new GridLayout(1, 2, 25, 5));
        pnlCenter.add(pnlLeft);
        pnlCenter.add(pnlRight);

        pnlForm.add(pnlCenter, BorderLayout.CENTER);
        pnlForm.add(pnlButton, BorderLayout.SOUTH);
        
        model = new DefaultTableModel(
            new String[]{"Mã môn", "Tên môn", "Số TC", "Lớp", "Lịch học", "Học kỳ"}, 0
        );
        table = new JTable(model);
        table.setRowHeight(26);

        JPanel pnlMain = new JPanel();
        pnlMain.setLayout(new BoxLayout(pnlMain, BoxLayout.Y_AXIS));

        pnlForm.setMaximumSize(new Dimension(Integer.MAX_VALUE, 230));
        pnlMain.add(pnlForm);

        pnlMain.add(Box.createVerticalStrut(10));
        JScrollPane scroll = new JScrollPane(table);
        scroll.setPreferredSize(new Dimension(1150, 260));
        pnlMain.add(scroll);
        add(pnlMain, BorderLayout.CENTER);
    }
}

