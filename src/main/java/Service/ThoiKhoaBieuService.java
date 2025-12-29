/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Service;

/**
 *
 * @author HUY
 */
import dao.ThoiKhoaBieuDAO;
import Model.ThoiKhoaBieuSV;

import java.util.List;
public class ThoiKhoaBieuService {
    private final ThoiKhoaBieuDAO dao = new ThoiKhoaBieuDAO();

    public List<ThoiKhoaBieuSV> getTKB(String maSv, String maHocKy) {
        if (maSv == null || maSv.isEmpty())
            throw new IllegalArgumentException("Sinh viên chưa đăng nhập!");

        if (maHocKy == null || maHocKy.isEmpty())
            throw new IllegalArgumentException("Chưa chọn học kỳ!");

        return dao.getBySinhVienHocKy(maSv, maHocKy);
    }
}
