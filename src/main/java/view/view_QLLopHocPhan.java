package view;
import java.awt.*;
import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

public class view_QLLopHocPhan extends JFrame {
    // ===== TEXT FIELD =====
    public JTextField txtMaLHP = new JTextField(15);
    public JTextField txtMaMon = new JTextField(15);
    public JTextField txtTenMon = new JTextField(15);
    public JTextField txtSoTinChi = new JTextField(15);
    public JTextField txtMaGV = new JTextField(15);
    public JTextField txtSoLuongToiDa = new JTextField(15);
    public JTextField txtSoLuongDaDK = new JTextField(15);

    // ===== COMBO =====
    public JComboBox<String> cboHocKy = new JComboBox<>();
    public JComboBox<String> cboTrangThai = new JComboBox<>();

    // ===== BUTTON =====
    public JButton btnThem = new JButton("Thêm Lớp");
    public JButton btnSua = new JButton("Sửa Lớp");
    public JButton btnXoa = new JButton("Xóa Lớp");
    public JButton btnLamMoi = new JButton("Làm Mới");

    // ===== TABLE =====
    public JTable tblLopHocPhan;
    public DefaultTableModel tableModel;

    public view_QLLopHocPhan() {

        setTitle("Quản Lý Lớp Học Phần");
        setSize(1100, 550);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        // ================= PANEL FORM =================
        JPanel pnForm = new JPanel(new GridBagLayout());
        pnForm.setBorder(new TitledBorder("Thông tin lớp học phần"));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 12, 5, 12);
        gbc.anchor = GridBagConstraints.WEST;

        // ---- Cột trái
        addRow(pnForm, gbc, 0, 0, "Mã Lớp Học Phần", txtMaLHP);
        addRow(pnForm, gbc, 1, 0, "Mã Môn", txtMaMon);
        addRow(pnForm, gbc, 2, 0, "Tên Môn", txtTenMon);
        addRow(pnForm, gbc, 3, 0, "Số Tín Chỉ", txtSoTinChi);
        addRow(pnForm, gbc, 4, 0, "Mã Giảng Viên", txtMaGV);

        // ---- Cột phải
        addRow(pnForm, gbc, 0, 2, "Học Kỳ", cboHocKy);
        addRow(pnForm, gbc, 1, 2, "Số Lượng Tối Đa", txtSoLuongToiDa);
        addRow(pnForm, gbc, 2, 2, "Đã Đăng Ký", txtSoLuongDaDK);
        addRow(pnForm, gbc, 3, 2, "Trạng Thái", cboTrangThai);

        // ================= PANEL BUTTON =================
        JPanel pnButton = new JPanel();
        pnButton.setLayout(new BoxLayout(pnButton, BoxLayout.Y_AXIS));
        pnButton.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));

        pnButton.add(btnThem);
        pnButton.add(Box.createVerticalStrut(10));
        pnButton.add(btnSua);
        pnButton.add(Box.createVerticalStrut(10));
        pnButton.add(btnXoa);
        pnButton.add(Box.createVerticalStrut(10));
        pnButton.add(btnLamMoi);

        // ================= TABLE =================
        String[] cols = {
                "Mã LHP", "Mã Môn", "Tên Môn", "Số TC",
                "Mã GV", "Học Kỳ", "SL Tối Đa", "Đã ĐK", "Trạng Thái"
        };

        tableModel = new DefaultTableModel(cols, 0) {
            @Override
            public boolean isCellEditable(int r, int c) {
                return false;
            }
        };

        tblLopHocPhan = new JTable(tableModel);
        JScrollPane scroll = new JScrollPane(tblLopHocPhan);

        // ================= ADD =================
        JPanel pnTop = new JPanel(new BorderLayout());
        pnTop.add(pnForm, BorderLayout.CENTER);
        pnTop.add(pnButton, BorderLayout.EAST);

        add(pnTop, BorderLayout.NORTH);
        add(scroll, BorderLayout.CENTER);
    }

    // ================= HÀM DÙNG CHUNG =================
    private void addRow(JPanel p, GridBagConstraints gbc,
                        int row, int col, String label, JComponent comp) {

        gbc.gridx = col;
        gbc.gridy = row;
        p.add(new JLabel(label), gbc);

        gbc.gridx = col + 1;
        p.add(comp, gbc);
    }
}
