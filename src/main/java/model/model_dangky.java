/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author Dvtt
 */
public class model_dangky {
    public String maSV;
    public String maLHP;
    public String maHocKy;
    public int maTKB;
    public String trangThai;

    public model_dangky() {
    }

    public model_dangky(String maSV, String maLHP, String maHocKy, int maTKB, String trangThai) {
        this.maSV = maSV;
        this.maLHP = maLHP;
        this.maHocKy = maHocKy;
        this.maTKB = maTKB;
        this.trangThai = trangThai;
    }

    public String getMaSV() {
        return maSV;
    }

    public String getMaLHP() {
        return maLHP;
    }

    public String getMaHocKy() {
        return maHocKy;
    }

    public int getMaTKB() {
        return maTKB;
    }

    public String getTrangThai() {
        return trangThai;
    }

    public void setMaSV(String maSV) {
        this.maSV = maSV;
    }

    public void setMaLHP(String maLHP) {
        this.maLHP = maLHP;
    }

    public void setMaHocKy(String maHocKy) {
        this.maHocKy = maHocKy;
    }

    public void setMaTKB(int maTKB) {
        this.maTKB = maTKB;
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }
    
}
