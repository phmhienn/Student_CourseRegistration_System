package view;

import java.awt.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class view_QLdangky extends JFrame {

    public JTextField txtMaMon = new JTextField(15);
    public JTextField txtTenMon = new JTextField(15);
    public JTextField txtSoTinChi = new JTextField(15);

    public JComboBox<String> cbMonHoc = new JComboBox<>();
    public JComboBox<String> cbLop = new JComboBox<>();
    public JComboBox<String> cbPhongHoc = new JComboBox<>();
    public JComboBox<String> cbLichHoc = new JComboBox<>();
    public JComboBox<String> cbHocKy = new JComboBox<>();

    public JButton btnDangKy = new JButton("Đăng ký");
    public JButton btnSua = new JButton("Sửa");
    public JButton btnHuy = new JButton("Hủy");
    public JButton btnTimKiem = new JButton("Tìm kiếm");
    public JButton btnQuaylai = new JButton("Quay lai");

    public JTable tblDangKy = new JTable();

    public view_QLdangky() {

        setTitle("Đăng ký môn học");
        setSize(980, 560);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        Dimension fieldSize = new Dimension(180, 25);
        txtMaMon.setPreferredSize(fieldSize);
        txtTenMon.setPreferredSize(fieldSize);
        txtSoTinChi.setPreferredSize(fieldSize);
        cbLop.setPreferredSize(fieldSize);
        cbLichHoc.setPreferredSize(fieldSize);
        cbPhongHoc.setPreferredSize(fieldSize);
        cbHocKy.setPreferredSize(fieldSize);

        JPanel pnlThongTin = new JPanel(new BorderLayout());
        pnlThongTin.setBorder(
                BorderFactory.createTitledBorder("Thông tin môn học")
        );

        JPanel pnlInput = new JPanel(new GridLayout(4, 2, 40, 12));

        pnlInput.add(panelField("Mã môn", txtMaMon));
        pnlInput.add(panelField("Lớp", cbLop));

        pnlInput.add(panelField("Tên môn", txtTenMon));
        pnlInput.add(panelField("Phòng học", cbPhongHoc));

        pnlInput.add(panelField("Số tín chỉ", txtSoTinChi));
        pnlInput.add(panelField("Lịch học", cbLichHoc));

        pnlInput.add(new JLabel());
        pnlInput.add(panelField("Học kỳ", cbHocKy));

        JPanel pnlButton = new JPanel(new FlowLayout(FlowLayout.CENTER, 25, 8));
        pnlButton.add(btnDangKy);
        pnlButton.add(btnSua);
        pnlButton.add(btnHuy);
        pnlButton.add(btnTimKiem);

        pnlThongTin.add(pnlInput, BorderLayout.CENTER);
        pnlThongTin.add(pnlButton, BorderLayout.SOUTH);

        tblDangKy.setModel(new DefaultTableModel(
                new Object[]{
                        "Mã môn",
                        "Tên môn",
                        "Số tín chỉ",
                        "Lớp",
                        "Lịch học",
                        "Phòng học",
                        "Học kỳ"
                }, 0
        ));

        JScrollPane scrollPane = new JScrollPane(tblDangKy);

        JPanel pnlHeader = new JPanel(new BorderLayout());
        pnlHeader.setPreferredSize(new Dimension(980, 55));
        pnlHeader.setBackground(Color.WHITE);

        JLabel lblTitle = new JLabel("Đăng ký môn học", SwingConstants.CENTER);
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 22));

        JPanel pnlRight = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 12));
        pnlRight.setOpaque(false);

        btnQuaylai.setPreferredSize(new Dimension(100, 30));
        pnlRight.add(btnQuaylai);

        pnlHeader.add(lblTitle, BorderLayout.CENTER);
        pnlHeader.add(pnlRight, BorderLayout.EAST);

        add(pnlHeader, BorderLayout.NORTH);

        JPanel pnlMain = new JPanel(new BorderLayout());
        pnlMain.add(pnlThongTin, BorderLayout.NORTH);
        pnlMain.add(scrollPane, BorderLayout.CENTER);

        add(pnlMain, BorderLayout.CENTER);
    }

    private JPanel panelField(String label, JComponent field) {
        JLabel lbl = new JLabel(label);
        lbl.setPreferredSize(new Dimension(80, 25));

        JPanel p = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 5));
        p.add(lbl);
        p.add(field);
        return p;
    }
}
