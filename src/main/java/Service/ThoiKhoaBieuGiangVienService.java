/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Service;

/**
 *
 * @author HUY
 */
import dao.ThoiKhoaBieuGiangVienDAO;
import Model.ThoiKhoaBieuGiangVien;

import java.util.List;
public class ThoiKhoaBieuGiangVienService {
    private final ThoiKhoaBieuGiangVienDAO dao = new ThoiKhoaBieuGiangVienDAO();

    public List<ThoiKhoaBieuGiangVien> getTKB(String maGv, String maHocKy) {
        if (maGv == null || maGv.isEmpty())
            throw new IllegalArgumentException("Giảng viên chưa đăng nhập!");

        if (maHocKy == null || maHocKy.isEmpty())
            throw new IllegalArgumentException("Chưa chọn học kỳ!");

        return dao.getByGiangVienHocKy(maGv, maHocKy);
    }
}
