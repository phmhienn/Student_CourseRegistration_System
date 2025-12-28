/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

/**
 *
 * @author Dvtt
 */
public class view_Dangnhap extends JFrame{
    public JTextField txtUser = new JTextField();
    public JPasswordField txtPass = new JPasswordField();
    public JButton btnLogin = new JButton("Dang nhap");
    public JButton btnExit = new JButton("Thoat");
    public JLabel lblMsg = new JLabel(" ");

    public view_Dangnhap() {
        setTitle("Dang nhap");
        setSize(420, 220);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JPanel root = new JPanel();
        root.setLayout(new BorderLayout(10,10));
        root.setBorder(BorderFactory.createEmptyBorder(12,12,12,12));

        JPanel form = new JPanel();
        form.setLayout(new GridLayout(2,2,8,8));

        form.add(new JLabel("Ten dang nhap:"));
        form.add(txtUser);
        form.add(new JLabel("Mat khau:"));
        form.add(txtPass);

        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        bottom.add(btnLogin);
        bottom.add(btnExit);

        lblMsg.setForeground(Color.RED);

        root.add(form, BorderLayout.CENTER);
        root.add(bottom, BorderLayout.SOUTH);
        root.add(lblMsg, BorderLayout.NORTH);

        setContentPane(root);
    }
}
