/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Service;

/**
 *
 * @author HUY
 */
import dao.SinhVienDAO;
import Model.SinhVienInfo;
public class SinhVienService {
    private final SinhVienDAO dao = new SinhVienDAO();

    public SinhVienInfo getInfo(String maSv) {
        if (maSv == null || maSv.trim().isEmpty())
            throw new IllegalArgumentException("Mã sinh viên không hợp lệ!");

        SinhVienInfo info = dao.findInfoByMaSv(maSv.trim());
        if (info == null) throw new IllegalArgumentException("Không tìm thấy sinh viên: " + maSv);
        return info;
    }

    public boolean updateContact(String maSv, String email, String sdt, String diaChi) {
        if (maSv == null || maSv.trim().isEmpty())
            throw new IllegalArgumentException("Mã sinh viên không hợp lệ!");

        email = email == null ? "" : email.trim();
        sdt = sdt == null ? "" : sdt.trim();
        diaChi = diaChi == null ? "" : diaChi.trim();

        // validate cơ bản
        if (!email.isEmpty() && !email.matches("^[\\w.+-]+@[\\w.-]+\\.[A-Za-z]{2,}$"))
            throw new IllegalArgumentException("Email không đúng định dạng!");

        if (!sdt.isEmpty() && !sdt.matches("^\\d{9,15}$"))
            throw new IllegalArgumentException("SĐT chỉ gồm số (9-15 ký tự)!");

        if (diaChi.length() > 200)
            throw new IllegalArgumentException("Địa chỉ tối đa 200 ký tự!");

        return dao.updateContact(maSv.trim(), email, sdt, diaChi);
    }
}
