/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package View;

/**
 *
 * @author HUY
 */
import Controller.ThongTinSinhVienController;

import javax.swing.*;
public class FrmThongTinSinhVien extends JFrame {
    // readonly
    public JTextField txtMaSv = new JTextField();
    public JTextField txtHoTen = new JTextField();
    public JTextField txtNgaySinh = new JTextField();
    public JTextField txtGioiTinh = new JTextField();
    public JTextField txtKhoaHoc = new JTextField();
    public JTextField txtTrangThai = new JTextField();

    // CTDT + Khoa readonly
    public JTextField txtMaCtdt = new JTextField();
    public JTextField txtTenCtdt = new JTextField();
    public JTextField txtBacDaoTao = new JTextField();
    public JTextField txtTenKhoa = new JTextField();

    // editable
    public JTextField txtEmail = new JTextField();
    public JTextField txtSoDienThoai = new JTextField();
    public JTextArea  txtDiaChi = new JTextArea(3, 20);

    public JButton btnLuu = new JButton("Lưu");
    public JButton btnTaiLai = new JButton("Tải lại");

    public FrmThongTinSinhVien(String maSv) {
        initUI();
        new ThongTinSinhVienController(this, maSv);
    }

    private void initUI() {
        setTitle("Thông tin cá nhân sinh viên");
        setSize(900, 520);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        // readonly fields
        for (JTextField t : new JTextField[]{txtMaSv, txtHoTen, txtNgaySinh, txtGioiTinh, txtKhoaHoc, txtTrangThai,
                txtMaCtdt, txtTenCtdt, txtBacDaoTao, txtTenKhoa}) {
            t.setEditable(false);
        }

        JScrollPane spDiaChi = new JScrollPane(txtDiaChi);

        JPanel p = new JPanel();
        p.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        GroupLayout gl = new GroupLayout(p);
        p.setLayout(gl);
        gl.setAutoCreateGaps(true);
        gl.setAutoCreateContainerGaps(true);

        JLabel lMaSv = new JLabel("Mã SV:");
        JLabel lHoTen = new JLabel("Họ tên:");
        JLabel lNgaySinh = new JLabel("Ngày sinh:");
        JLabel lGioiTinh = new JLabel("Giới tính:");
        JLabel lKhoaHoc = new JLabel("Khóa học:");
        JLabel lTrangThai = new JLabel("Trạng thái:");

        JLabel lMaCtdt = new JLabel("Mã CTĐT:");
        JLabel lTenCtdt = new JLabel("Tên CTĐT:");
        JLabel lBac = new JLabel("Bậc đào tạo:");
        JLabel lKhoa = new JLabel("Khoa:");

        JLabel lEmail = new JLabel("Email (sửa):");
        JLabel lSdt = new JLabel("SĐT (sửa):");
        JLabel lDiaChi = new JLabel("Địa chỉ (sửa):");

        JPanel pnlBtn = new JPanel();
        pnlBtn.add(btnLuu);
        pnlBtn.add(btnTaiLai);

        gl.setHorizontalGroup(
            gl.createParallelGroup()
              .addGroup(gl.createSequentialGroup()
                  .addGroup(gl.createParallelGroup()
                      .addComponent(lMaSv).addComponent(lHoTen).addComponent(lNgaySinh)
                      .addComponent(lMaCtdt).addComponent(lTenCtdt).addComponent(lBac)
                      .addComponent(lEmail).addComponent(lDiaChi))
                  .addGroup(gl.createParallelGroup()
                      .addComponent(txtMaSv).addComponent(txtHoTen).addComponent(txtNgaySinh)
                      .addComponent(txtMaCtdt).addComponent(txtTenCtdt).addComponent(txtBacDaoTao)
                      .addComponent(txtEmail).addComponent(spDiaChi))
                  .addGroup(gl.createParallelGroup()
                      .addComponent(lGioiTinh).addComponent(lKhoaHoc).addComponent(lTrangThai)
                      .addComponent(lKhoa).addComponent(lSdt))
                  .addGroup(gl.createParallelGroup()
                      .addComponent(txtGioiTinh).addComponent(txtKhoaHoc).addComponent(txtTrangThai)
                      .addComponent(txtTenKhoa).addComponent(txtSoDienThoai))
              )
              .addComponent(pnlBtn)
        );

        gl.setVerticalGroup(
            gl.createSequentialGroup()
              .addGroup(gl.createParallelGroup(GroupLayout.Alignment.BASELINE)
                  .addComponent(lMaSv).addComponent(txtMaSv)
                  .addComponent(lGioiTinh).addComponent(txtGioiTinh))
              .addGroup(gl.createParallelGroup(GroupLayout.Alignment.BASELINE)
                  .addComponent(lHoTen).addComponent(txtHoTen)
                  .addComponent(lKhoaHoc).addComponent(txtKhoaHoc))
              .addGroup(gl.createParallelGroup(GroupLayout.Alignment.BASELINE)
                  .addComponent(lNgaySinh).addComponent(txtNgaySinh)
                  .addComponent(lTrangThai).addComponent(txtTrangThai))

              .addGap(10)

              .addGroup(gl.createParallelGroup(GroupLayout.Alignment.BASELINE)
                  .addComponent(lMaCtdt).addComponent(txtMaCtdt)
                  .addComponent(lKhoa).addComponent(txtTenKhoa))
              .addGroup(gl.createParallelGroup(GroupLayout.Alignment.BASELINE)
                  .addComponent(lTenCtdt).addComponent(txtTenCtdt))
              .addGroup(gl.createParallelGroup(GroupLayout.Alignment.BASELINE)
                  .addComponent(lBac).addComponent(txtBacDaoTao))

              .addGap(10)

              .addGroup(gl.createParallelGroup(GroupLayout.Alignment.BASELINE)
                  .addComponent(lEmail).addComponent(txtEmail)
                  .addComponent(lSdt).addComponent(txtSoDienThoai))
              .addGroup(gl.createParallelGroup()
                  .addComponent(lDiaChi).addComponent(spDiaChi))

              .addGap(10)
              .addComponent(pnlBtn)
        );

        setContentPane(p);
    }
}
