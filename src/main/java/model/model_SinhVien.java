/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author Dvtt
 */
import java.util.Date;

public class model_SinhVien {
    private String maSV;
    private String hoTen;
    private Date ngaySinh;
    private String gioiTinh;
    private String lop;
    private String khoa;

    public model_SinhVien() {}

    public model_SinhVien(String maSV, String hoTen, Date ngaySinh,
                          String gioiTinh, String lop, String khoa) {
        this.maSV = maSV;
        this.hoTen = hoTen;
        this.ngaySinh = ngaySinh;
        this.gioiTinh = gioiTinh;
        this.lop = lop;
        this.khoa = khoa;
    }

    public String getMaSV() { return maSV; }
    public String getHoTen() { return hoTen; }
    public Date getNgaySinh() { return ngaySinh; }
    public String getGioiTinh() { return gioiTinh; }
    public String getLop() { return lop; }
    public String getKhoa() { return khoa; }
}
