/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import model.model_Vaitro;

/**
 *
 * @author Dvtt
 */
public class view_Admin extends JFrame{
    public JTextField txtTenDangNhap = new JTextField(18);
    public JTextField txtMatKhau = new JTextField(18);     // ✅ hiện mật khẩu để sửa
    public JComboBox<String> cboVaiTro = new JComboBox<>(new String[]{"SV", "GV", "AD"});
    public JTextField txtMaLienKet = new JTextField(18);

    // buttons
    public JButton btnThem = new JButton("Them");
    public JButton btnSua = new JButton("Sua");
    public JButton btnXoa = new JButton("Xoa");
    public JButton btnLamMoi = new JButton("Lam moi");
    public JButton btnQuanLyMonHoc = new JButton("Quan ly mon hoc");
    public JButton btnDangXuat = new JButton("Dang xuat");

    // table
    public DefaultTableModel dtm = new DefaultTableModel(
            new String[]{"Ten dang nhap","Mat khau","Vai tro","Ma lien ket"}, 0
    );
    public JTable tbl = new JTable(dtm);

    public view_Admin() {
        setTitle("Admin - Quan ly tai khoan");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(980, 520);
        setLocationRelativeTo(null);

        // TOP panel
        JPanel top = new JPanel(new FlowLayout(FlowLayout.LEFT));
        top.add(btnQuanLyMonHoc);
        top.add(btnDangXuat);

        // FORM panel (không dùng gridx/y)
        JPanel form = new JPanel(new GridLayout(2, 4, 10, 10));
        form.setBorder(BorderFactory.createTitledBorder("Thong tin tai khoan"));

        form.add(new JLabel("Ten dang nhap"));
        form.add(new JLabel("Mat khau"));
        form.add(new JLabel("Vai tro"));
        form.add(new JLabel("Ma lien ket"));

        form.add(txtTenDangNhap);
        form.add(txtMatKhau);
        form.add(cboVaiTro);
        form.add(txtMaLienKet);

        // buttons panel
        JPanel actions = new JPanel(new FlowLayout(FlowLayout.LEFT));
        actions.add(btnThem);
        actions.add(btnSua);
        actions.add(btnXoa);
        actions.add(btnLamMoi);

        // table panel
        JScrollPane sp = new JScrollPane(tbl);
        sp.setBorder(BorderFactory.createTitledBorder("Danh sach tai khoan"));

        // layout main
        JPanel center = new JPanel();
        center.setLayout(new BoxLayout(center, BoxLayout.Y_AXIS));
        center.add(form);
        center.add(actions);
        center.add(sp);

        add(top, BorderLayout.NORTH);
        add(center, BorderLayout.CENTER);
    }
}
