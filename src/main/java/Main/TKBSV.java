/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Main;

/**
 *
 * @author HUY
 */
import View.FrmThoiKhoaBieuSinhVien;
import javax.swing.SwingUtilities;
public class TKBSV {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            // TEST: giả lập sinh viên đang đăng nhập
            String maSv = "SV001";

            new FrmThoiKhoaBieuSinhVien(maSv).setVisible(true);
        });
    }
}
