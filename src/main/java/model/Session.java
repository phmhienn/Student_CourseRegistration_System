/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author Dvtt
 */
public class Session {
    private final String tenDangNhap;
    private final String vaiTro;     // SV/GV/AD
    private final String maLienKet;  // SV001/GV001/null

    public Session(String tenDangNhap, String vaiTro, String maLienKet) {
        this.tenDangNhap = tenDangNhap;
        this.vaiTro = vaiTro;
        this.maLienKet = maLienKet;
    }

    public String getTenDangNhap() { return tenDangNhap; }
    public String getVaiTro() { return vaiTro; }
    public String getMaLienKet() { return maLienKet; }
}
