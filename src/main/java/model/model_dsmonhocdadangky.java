/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;
import java.sql.Date;
/**
 *
 * @author Dvtt
 */
public class model_dsmonhocdadangky {
    public String maMon;
    public String tenMon;
    public int soTinChi;
    public String lop;
    public String lichHoc;
    public String hocKy;
    public Date ngayBatDau;
    public Date ngayKetThuc;

    public model_dsmonhocdadangky(String maMon, String tenMon, int soTinChi,
                     String lop, String lichHoc, String hocKy,
                     Date ngayBatDau, Date ngayKetThuc) {
        this.maMon = maMon;
        this.tenMon = tenMon;
        this.soTinChi = soTinChi;
        this.lop = lop;
        this.lichHoc = lichHoc;
        this.hocKy = hocKy;
        this.ngayBatDau = ngayBatDau;
        this.ngayKetThuc = ngayKetThuc;
    }
}
