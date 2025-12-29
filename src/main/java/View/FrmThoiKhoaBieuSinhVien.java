/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package View;

/**
 *
 * @author HUY
 */
import Controller.ThoiKhoaBieuController;
import Model.ComboItem;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
public class FrmThoiKhoaBieuSinhVien extends JFrame {
    public JComboBox<ComboItem> cboHocKy = new JComboBox<>();
    public JTable tblTKB = new JTable();

    public FrmThoiKhoaBieuSinhVien(String maSv) {
        setTitle("Thời khóa biểu sinh viên");
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

        new ThoiKhoaBieuController(this, maSv);
    }
}
