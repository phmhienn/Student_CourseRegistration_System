/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package View;

/**
 *
 * @author HUY
 */
import Controller.LopHocPhanController;
import Model.ComboItem;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.List;
import java.util.Map;
public class FrmQuanLyLopHocPhan extends JFrame {
    public JTextField txtMaLHP;
    public JTextField txtSoLuongToiDa;
    public JTextField txtSoLuongDaDK;

    public JComboBox<ComboItem> cboMonHoc;
    public JComboBox<ComboItem> cboGiangVien;
    public JComboBox<ComboItem> cboHocKy;
    public JComboBox<String> cboTrangThai;

    public JTable tblLHP;

    public JButton btnMoLop;
    public JButton btnSua;
    public JButton btnXoa;
    public JButton btnXuatExcel;
    public JButton btnLamMoi;

    // cache dữ liệu join để fill lại combobox
    public List<Map<String, Object>> _cacheRows;

    public FrmQuanLyLopHocPhan() {
        initUI();
        txtSoLuongDaDK.setEditable(false);
        new LopHocPhanController(this);
    }

    private void initUI() {
        setTitle("Quản lý lớp học phần");
        setSize(950, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JPanel pnlMain = new JPanel();
        pnlMain.setLayout(new BoxLayout(pnlMain, BoxLayout.Y_AXIS));
        add(pnlMain);

        // ================= FORM =================
        JPanel pnlForm = new JPanel();
        pnlForm.setBorder(BorderFactory.createTitledBorder("Thông tin lớp học phần"));
        pnlForm.setLayout(new GroupLayout(pnlForm));
        pnlMain.add(pnlForm);

        GroupLayout gl = (GroupLayout) pnlForm.getLayout();
        gl.setAutoCreateGaps(true);
        gl.setAutoCreateContainerGaps(true);

        JLabel lblMa = new JLabel("Mã LHP:");
        JLabel lblMon = new JLabel("Môn học:");
        JLabel lblGV = new JLabel("Giảng viên:");
        JLabel lblHK = new JLabel("Học kỳ:");
        JLabel lblSLTD = new JLabel("SL tối đa:");
        JLabel lblSLDK = new JLabel("SL đã ĐK:");
        JLabel lblTT = new JLabel("Trạng thái:");

        txtMaLHP = new JTextField();
        txtSoLuongToiDa = new JTextField();
        txtSoLuongDaDK = new JTextField("0");

        cboMonHoc = new JComboBox<>();
        cboGiangVien = new JComboBox<>();
        cboHocKy = new JComboBox<>();
        cboTrangThai = new JComboBox<>();

        gl.setHorizontalGroup(
            gl.createSequentialGroup()
                .addGroup(gl.createParallelGroup()
                    .addComponent(lblMa)
                    .addComponent(lblMon)
                    .addComponent(lblSLTD)
                    .addComponent(lblTT))
                .addGroup(gl.createParallelGroup()
                    .addComponent(txtMaLHP)
                    .addComponent(cboMonHoc)
                    .addComponent(txtSoLuongToiDa)
                    .addComponent(cboTrangThai))
                .addGroup(gl.createParallelGroup()
                    .addComponent(lblGV)
                    .addComponent(lblHK)
                    .addComponent(lblSLDK))
                .addGroup(gl.createParallelGroup()
                    .addComponent(cboGiangVien)
                    .addComponent(cboHocKy)
                    .addComponent(txtSoLuongDaDK))
        );

        gl.setVerticalGroup(
            gl.createSequentialGroup()
                .addGroup(gl.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(lblMa).addComponent(txtMaLHP)
                    .addComponent(lblGV).addComponent(cboGiangVien))
                .addGroup(gl.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(lblMon).addComponent(cboMonHoc)
                    .addComponent(lblHK).addComponent(cboHocKy))
                .addGroup(gl.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(lblSLTD).addComponent(txtSoLuongToiDa)
                    .addComponent(lblSLDK).addComponent(txtSoLuongDaDK))
                .addGroup(gl.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(lblTT).addComponent(cboTrangThai))
        );

        // ================= BUTTON =================
        JPanel pnlBtn = new JPanel();
        btnMoLop = new JButton("Mở lớp");
        btnSua = new JButton("Sửa lớp");
        btnXoa = new JButton("Xóa lớp");
        btnXuatExcel = new JButton("Xuất Excel");
        btnLamMoi = new JButton("Làm mới");

        pnlBtn.add(btnMoLop);
        pnlBtn.add(btnSua);
        pnlBtn.add(btnXoa);
        pnlBtn.add(btnXuatExcel);
        pnlBtn.add(btnLamMoi);
        pnlMain.add(pnlBtn);

        // ================= TABLE =================
        tblLHP = new JTable();
        tblLHP.setModel(new DefaultTableModel(
            new Object[][]{},
            new String[]{"Mã LHP", "Môn", "Giảng viên", "Học kỳ", "SL tối đa", "SL đã ĐK", "Trạng thái"}
        ));
        JScrollPane scroll = new JScrollPane(tblLHP);
        pnlMain.add(scroll);
    }

    // ====== TIỆN ÍCH ======
    public void clearForm() {
        txtMaLHP.setText("");
        txtSoLuongToiDa.setText("");
        txtSoLuongDaDK.setText("0");
        cboTrangThai.setSelectedIndex(0);
        tblLHP.clearSelection();
    }

    public void setSelectedComboById(JComboBox<?> cbo, String id) {
        for (int i = 0; i < cbo.getItemCount(); i++) {
            Object o = cbo.getItemAt(i);
            if (o instanceof ComboItem it) {
                if (it.getId().equals(id)) {
                    cbo.setSelectedIndex(i);
                    return;
                }
            }
        }
    }
}
