/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import model.model_Khoa;

/**
 *
 * @author Dvtt
 */
public class view_Quanlymonhoc extends JFrame{
    public JTextField txtMaMon = new JTextField(12);
    public JTextField txtTenMon = new JTextField(18);
    public JTextField txtTinChi = new JTextField(6);

    public JComboBox<model_Khoa> cboKhoa = new JComboBox<>();
    public JTextField txtTim = new JTextField(18);

    public JButton btnThem = new JButton("Them");
    public JButton btnSua = new JButton("Sua");
    public JButton btnXoa = new JButton("Xoa");
    public JButton btnLamMoi = new JButton("Lam moi");

    public JButton btnTim = new JButton("Tim");
    public JButton btnXuatExcel = new JButton("Xuat Excel");
    public JButton btnNhapExcel = new JButton("Nhap Excel");

    public DefaultTableModel dtm = new DefaultTableModel(
            new String[]{"Ma mon","Ten mon","Tin chi","Ma khoa"}, 0
    );
    public JTable tbl = new JTable(dtm);

    public view_Quanlymonhoc() {
        setTitle("Quan ly mon hoc");
        setSize(980, 520);
        setLocationRelativeTo(null);

        JPanel form = new JPanel(new GridLayout(2, 4, 10, 10));
        form.setBorder(BorderFactory.createTitledBorder("Thong tin mon hoc"));
        form.add(new JLabel("Ma mon"));
        form.add(new JLabel("Ten mon"));
        form.add(new JLabel("Tin chi"));
        form.add(new JLabel("Khoa"));

        form.add(txtMaMon);
        form.add(txtTenMon);
        form.add(txtTinChi);
        form.add(cboKhoa);

        JPanel action = new JPanel(new FlowLayout(FlowLayout.LEFT));
        action.add(btnThem);
        action.add(btnSua);
        action.add(btnXoa);
        action.add(btnLamMoi);

        JPanel search = new JPanel(new FlowLayout(FlowLayout.LEFT));
        search.setBorder(BorderFactory.createTitledBorder("Tim kiem"));
        search.add(new JLabel("Tu khoa:"));
        search.add(txtTim);
        search.add(btnTim);
        search.add(btnXuatExcel);
        search.add(btnNhapExcel);

        JScrollPane sp = new JScrollPane(tbl);

        JPanel top = new JPanel();
        top.setLayout(new BoxLayout(top, BoxLayout.Y_AXIS));
        top.add(form);
        top.add(action);
        top.add(search);

        add(top, BorderLayout.NORTH);
        add(sp, BorderLayout.CENTER);
    }
}
