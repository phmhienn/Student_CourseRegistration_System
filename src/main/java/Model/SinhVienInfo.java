/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

/**
 *
 * @author HUY
 */
import java.sql.Date;
public class SinhVienInfo {
    private String maSv;
    private String hoTen;
    private Date ngaySinh;
    private String gioiTinh;

    private String email;
    private String soDienThoai;
    private String diaChi;

    private Integer khoaHoc;
    private String trangThai;

    private String maCtdt;
    private String tenCtdt;
    private String bacDaoTao;

    private String maKhoa;
    private String tenKhoa;

    // getter/setter
    public String getMaSv() { return maSv; }
    public void setMaSv(String maSv) { this.maSv = maSv; }

    public String getHoTen() { return hoTen; }
    public void setHoTen(String hoTen) { this.hoTen = hoTen; }

    public Date getNgaySinh() { return ngaySinh; }
    public void setNgaySinh(Date ngaySinh) { this.ngaySinh = ngaySinh; }

    public String getGioiTinh() { return gioiTinh; }
    public void setGioiTinh(String gioiTinh) { this.gioiTinh = gioiTinh; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getSoDienThoai() { return soDienThoai; }
    public void setSoDienThoai(String soDienThoai) { this.soDienThoai = soDienThoai; }

    public String getDiaChi() { return diaChi; }
    public void setDiaChi(String diaChi) { this.diaChi = diaChi; }

    public Integer getKhoaHoc() { return khoaHoc; }
    public void setKhoaHoc(Integer khoaHoc) { this.khoaHoc = khoaHoc; }

    public String getTrangThai() { return trangThai; }
    public void setTrangThai(String trangThai) { this.trangThai = trangThai; }

    public String getMaCtdt() { return maCtdt; }
    public void setMaCtdt(String maCtdt) { this.maCtdt = maCtdt; }

    public String getTenCtdt() { return tenCtdt; }
    public void setTenCtdt(String tenCtdt) { this.tenCtdt = tenCtdt; }

    public String getBacDaoTao() { return bacDaoTao; }
    public void setBacDaoTao(String bacDaoTao) { this.bacDaoTao = bacDaoTao; }

    public String getMaKhoa() { return maKhoa; }
    public void setMaKhoa(String maKhoa) { this.maKhoa = maKhoa; }

    public String getTenKhoa() { return tenKhoa; }
    public void setTenKhoa(String tenKhoa) { this.tenKhoa = tenKhoa; }
}
