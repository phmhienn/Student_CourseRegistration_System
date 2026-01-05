/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author HUY
 */
public class model_GiangVien {
    private String maGV;
    private String tenGV;
    private String hocVi;
    private String hocHam;
    private String email;
    private String dienThoai;
    private String maKhoa;
    private boolean trangThai;

    public model_GiangVien() {}

    public model_GiangVien(String maGV, String tenGV, String hocVi, String hocHam,
                           String email, String dienThoai, String maKhoa, boolean trangThai) {
        this.maGV = maGV;
        this.tenGV = tenGV;
        this.hocVi = hocVi;
        this.hocHam = hocHam;
        this.email = email;
        this.dienThoai = dienThoai;
        this.maKhoa = maKhoa;
        this.trangThai = trangThai;
    }

    public String getMaGV() { return maGV; }
    public void setMaGV(String maGV) { this.maGV = maGV; }

    public String getTenGV() { return tenGV; }
    public void setTenGV(String tenGV) { this.tenGV = tenGV; }

    public String getHocVi() { return hocVi; }
    public void setHocVi(String hocVi) { this.hocVi = hocVi; }

    public String getHocHam() { return hocHam; }
    public void setHocHam(String hocHam) { this.hocHam = hocHam; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getDienThoai() { return dienThoai; }
    public void setDienThoai(String dienThoai) { this.dienThoai = dienThoai; }

    public String getMaKhoa() { return maKhoa; }
    public void setMaKhoa(String maKhoa) { this.maKhoa = maKhoa; }

    public boolean isTrangThai() { return trangThai; }
    public void setTrangThai(boolean trangThai) { this.trangThai = trangThai; }
}
