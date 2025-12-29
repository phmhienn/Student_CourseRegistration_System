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
import View.FrmThoiKhoaBieuGiangVien;
public class TKBGV {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            // test: giả lập giảng viên đang đăng nhập
            String maGv = "GV001";
            new FrmThoiKhoaBieuGiangVien(maGv).setVisible(true);
        });
    }
}
