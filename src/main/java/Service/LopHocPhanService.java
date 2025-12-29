/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Service;

/**
 *
 * @author HUY
 */
import dao.LopHocPhanDAO;
import Model.LopHocPhan;
public class LopHocPhanService {
    private LopHocPhanDAO dao = new LopHocPhanDAO();

    public void validate(LopHocPhan lhp) {
        if (lhp.getMaLhp() == null || lhp.getMaLhp().trim().isEmpty())
            throw new IllegalArgumentException("Mã lớp học phần không được rỗng!");
        if (lhp.getSoLuongToiDa() <= 0)
            throw new IllegalArgumentException("Số lượng tối đa phải > 0!");
        if (lhp.getMaMon() == null || lhp.getMaGv() == null || lhp.getMaHocKy() == null)
            throw new IllegalArgumentException("Phải chọn môn học, giảng viên, học kỳ!");
    }

    public boolean moLop(LopHocPhan lhp) {
        validate(lhp);
        // so_luong_da_dang_ky tự tăng bằng trigger khi có đăng ký -> insert lớp không cần set.
        return dao.insert(lhp);
    }

    public boolean sua(LopHocPhan lhp) {
        validate(lhp);
        return dao.update(lhp);
    }

    public boolean xoa(String maLhp) {
        if (maLhp == null || maLhp.isEmpty())
            throw new IllegalArgumentException("Chọn lớp cần xóa!");
        return dao.delete(maLhp);
    }
}
