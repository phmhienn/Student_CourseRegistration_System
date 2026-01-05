/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
/**
 *
 * @author Penguin
 */

import controller.controller_Dangnhap;
import javax.swing.SwingUtilities;
import view.view_Dangnhap;
public class Student_CourseRegistration_Systeam {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            view_Dangnhap v = new view_Dangnhap();
            new controller_Dangnhap(v);
            v.setVisible(true);
        });
    }
}
