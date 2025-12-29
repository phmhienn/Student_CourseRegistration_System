/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Main;

/**
 *
 * @author HUY
 */
import javax.swing.SwingUtilities;
import View.FrmQuanLyLopHocPhan;
public class QLLHP {
     public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new FrmQuanLyLopHocPhan().setVisible(true);
        });
    }
}
