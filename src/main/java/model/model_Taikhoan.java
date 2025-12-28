/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author Dvtt
 */
public class model_Taikhoan {
    private String tenDangNhap;
    private String matKhau;
    private String maVaiTro;
    private String maLienKet;

    public model_Taikhoan() {}

    public model_Taikhoan(String tenDangNhap, String matKhau, String maVaiTro, String maLienKet) {
        this.tenDangNhap = tenDangNhap;
        this.matKhau = matKhau;
        this.maVaiTro = maVaiTro;
        this.maLienKet = maLienKet;
    }

    public String getTenDangNhap() { return tenDangNhap; }
    public void setTenDangNhap(String tenDangNhap) { this.tenDangNhap = tenDangNhap; }

    public String getMatKhau() { return matKhau; }
    public void setMatKhau(String matKhau) { this.matKhau = matKhau; }

    public String getMaVaiTro() { return maVaiTro; }
    public void setMaVaiTro(String maVaiTro) { this.maVaiTro = maVaiTro; }

    public String getMaLienKet() { return maLienKet; }
    public void setMaLienKet(String maLienKet) { this.maLienKet = maLienKet; }
}
