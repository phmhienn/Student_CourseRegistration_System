/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author Dvtt
 */
public class view_Sinhvien extends JFrame{
    public JLabel lblInfo = new JLabel("");
    public JButton btnDangXuat = new JButton("Dang xuat");

    public view_Sinhvien() {
        setTitle("Sinh vien");
        setSize(500, 200);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JPanel p = new JPanel(new BorderLayout(10,10));
        p.setBorder(BorderFactory.createEmptyBorder(12,12,12,12));

        p.add(lblInfo, BorderLayout.CENTER);

        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        bottom.add(btnDangXuat);
        p.add(bottom, BorderLayout.SOUTH);

        setContentPane(p);
    }
}
