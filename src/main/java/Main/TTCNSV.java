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
import View.FrmThongTinSinhVien;
public class TTCNSV {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            // TEST: giả lập sinh viên đang đăng nhập
            String maSv = "SV001";
            new FrmThongTinSinhVien(maSv).setVisible(true);
        });
    }
}
