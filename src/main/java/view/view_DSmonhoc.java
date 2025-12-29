/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package view;

/**
 *
 * @author thedu
 */
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
public class view_DSmonhoc extends JPanel {
    public JTextField txtTim = new JTextField();
    public JButton btnTim = new JButton("Tìm kiếm");

    public JTable table;
    public DefaultTableModel model;

    public view_DSmonhoc() {

        setLayout(new BorderLayout(8, 8));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        
        JLabel lblTitle = new JLabel("DANH SÁCH MÔN HỌC", JLabel.CENTER);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 26));
        lblTitle.setBorder(BorderFactory.createEmptyBorder(10, 10, 5, 10));
        add(lblTitle, BorderLayout.NORTH);

        
        JPanel pnlCenter = new JPanel(new BorderLayout(5, 5));
        add(pnlCenter, BorderLayout.CENTER);

        
        JPanel pnlSearch = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 5));
        txtTim.setPreferredSize(new Dimension(220, 28));
        pnlSearch.add(txtTim);
        pnlSearch.add(btnTim);

        pnlCenter.add(pnlSearch, BorderLayout.NORTH);

       
        model = new DefaultTableModel(
            new String[]{
                "Mã môn",
                "Tên môn",
                "Số tín chỉ",
                "Số tiết lý thuyết",
                "Số tiết thực hành",
                "Mã khoa"
            }, 0
        );

        table = new JTable(model);
        table.setRowHeight(28);

        JScrollPane scroll = new JScrollPane(table);
        pnlCenter.add(scroll, BorderLayout.CENTER);
    }
}
