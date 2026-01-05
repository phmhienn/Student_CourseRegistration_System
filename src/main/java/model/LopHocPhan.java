package model;

public class LopHocPhan {

    // ====== Thuộc tính ======
    private String maLhp;          // mã lớp học phần
    private String maMon;          // mã môn học
    private String tenMon;         // tên môn học
    private String maHocKy;        // mã học kỳ
    private String giangVien;      // giảng viên phụ trách
    private int soLuongToiDa;      // số lượng SV tối đa
    private String thu;            // thứ học (Thứ 2, Thứ 3...)
    private String caHoc;          // ca học
    private String phongHoc;       // phòng học
    private String trangThai;      // trạng thái: Đang mở / Đã đủ / Đã khóa

    // ====== Constructor không tham số ======
    public LopHocPhan() {
    }

    // ====== Constructor đầy đủ ======
    public LopHocPhan(String maLhp, String maMon, String tenMon,
                      String maHocKy, String giangVien, int soLuongToiDa,
                      String thu, String caHoc, String phongHoc, String trangThai) {
        this.maLhp = maLhp;
        this.maMon = maMon;
        this.tenMon = tenMon;
        this.maHocKy = maHocKy;
        this.giangVien = giangVien;
        this.soLuongToiDa = soLuongToiDa;
        this.thu = thu;
        this.caHoc = caHoc;
        this.phongHoc = phongHoc;
        this.trangThai = trangThai;
    }

    // ====== Getter & Setter ======
    public String getMaLhp() {
        return maLhp;
    }

    public void setMaLhp(String maLhp) {
        this.maLhp = maLhp;
    }

    public String getMaMon() {
        return maMon;
    }

    public void setMaMon(String maMon) {
        this.maMon = maMon;
    }

    public String getTenMon() {
        return tenMon;
    }

    public void setTenMon(String tenMon) {
        this.tenMon = tenMon;
    }

    public String getMaHocKy() {
        return maHocKy;
    }

    public void setMaHocKy(String maHocKy) {
        this.maHocKy = maHocKy;
    }

    public String getGiangVien() {
        return giangVien;
    }

    public void setGiangVien(String giangVien) {
        this.giangVien = giangVien;
    }

    public int getSoLuongToiDa() {
        return soLuongToiDa;
    }

    public void setSoLuongToiDa(int soLuongToiDa) {
        this.soLuongToiDa = soLuongToiDa;
    }

    public String getThu() {
        return thu;
    }

    public void setThu(String thu) {
        this.thu = thu;
    }

    public String getCaHoc() {
        return caHoc;
    }

    public void setCaHoc(String caHoc) {
        this.caHoc = caHoc;
    }

    public String getPhongHoc() {
        return phongHoc;
    }

    public void setPhongHoc(String phongHoc) {
        this.phongHoc = phongHoc;
    }

    public String getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }

    // ====== Phương thức hỗ trợ nghiệp vụ ======
    public boolean isDangMo() {
        return "Đang mở".equalsIgnoreCase(trangThai);
    }

    public boolean isDaDu() {
        return "Đã đủ".equalsIgnoreCase(trangThai);
    }

    public boolean isDaKhoa() {
        return "Đã khóa".equalsIgnoreCase(trangThai);
    }
}