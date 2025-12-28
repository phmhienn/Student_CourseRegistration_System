/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package view;

import java.awt.FlowLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author Dvtt
 */
public class view_Giangvien extends JFrame{
    public JLabel lblInfo = new JLabel("");
    public JButton btnDangXuat = new JButton("Dang xuat");

    public view_Giangvien() {
        setTitle("Giang vien");
        setSize(400, 200);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panel.add(btnDangXuat);
        panel.add(lblInfo);

        setContentPane(panel);
    }
}
