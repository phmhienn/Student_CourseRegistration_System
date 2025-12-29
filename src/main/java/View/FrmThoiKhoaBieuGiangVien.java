/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package View;

/**
 *
 * @author HUY
 */
import Controller.ThoiKhoaBieuGiangVienController;
import Model.ComboItem;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
public class FrmThoiKhoaBieuGiangVien extends JFrame {
    public JComboBox<ComboItem> cboHocKy = new JComboBox<>();
    public JTable tblTKB = new JTable();

    public FrmThoiKhoaBieuGiangVien(String maGv) {
        setTitle("Thời khóa biểu giảng viên");
        setSize(800, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        JPanel top = new JPanel();
        top.add(new JLabel("Học kỳ:"));
        top.add(cboHocKy);
        add(top, "North");

        tblTKB.setModel(new DefaultTableModel(
            new Object[][]{},
            new String[]{"Môn học", "Lớp HP", "Thứ", "Tiết bắt đầu", "Số tiết", "Phòng"}
        ));
        add(new JScrollPane(tblTKB), "Center");

        new ThoiKhoaBieuGiangVienController(this, maGv);
    }
}
