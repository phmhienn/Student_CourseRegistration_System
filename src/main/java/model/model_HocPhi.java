/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author Dvtt
 */
public class model_HocPhi {

    private String maSV;
    private String maHocKy;
    private int tongTinChi;
    private long tongTien;
    private String trangThai;

    public model_HocPhi() {}

    public model_HocPhi(String maSV, String maHocKy,
                         int tongTinChi, long tongTien,
                         String trangThai) {
        this.maSV = maSV;
        this.maHocKy = maHocKy;
        this.tongTinChi = tongTinChi;
        this.tongTien = tongTien;
        this.trangThai = trangThai;
    }

    public String getMaSV() {return maSV;}
    public String getMaHocKy() {return maHocKy;}
    public int getTongTinChi() {return tongTinChi;}
    public long getTongTien() {return tongTien;}
    public String getTrangThai() {return trangThai;}
}
