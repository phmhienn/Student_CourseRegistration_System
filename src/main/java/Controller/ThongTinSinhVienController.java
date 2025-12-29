/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

/**
 *
 * @author HUY
 */
import Model.SinhVienInfo;
import Service.SinhVienService;
import View.FrmThongTinSinhVien;

import javax.swing.*;
public class ThongTinSinhVienController {
    private final FrmThongTinSinhVien view;
    private final String maSv;
    private final SinhVienService service = new SinhVienService();

    public ThongTinSinhVienController(FrmThongTinSinhVien view, String maSv) {
        this.view = view;
        this.maSv = maSv;
        init();
    }

    private void init() {
        bindEvents();
        loadData();
    }

    private void bindEvents() {
        view.btnTaiLai.addActionListener(e -> loadData());
        view.btnLuu.addActionListener(e -> save());
    }

    private void loadData() {
        try {
            SinhVienInfo info = service.getInfo(maSv);

            view.txtMaSv.setText(info.getMaSv());
            view.txtHoTen.setText(info.getHoTen());
            view.txtNgaySinh.setText(info.getNgaySinh() == null ? "" : info.getNgaySinh().toString());
            view.txtGioiTinh.setText(nvl(info.getGioiTinh()));
            view.txtKhoaHoc.setText(info.getKhoaHoc() == null ? "" : String.valueOf(info.getKhoaHoc()));
            view.txtTrangThai.setText(nvl(info.getTrangThai()));

            view.txtMaCtdt.setText(nvl(info.getMaCtdt()));
            view.txtTenCtdt.setText(nvl(info.getTenCtdt()));
            view.txtBacDaoTao.setText(nvl(info.getBacDaoTao()));
            view.txtTenKhoa.setText(nvl(info.getTenKhoa()));

            // editable
            view.txtEmail.setText(nvl(info.getEmail()));
            view.txtSoDienThoai.setText(nvl(info.getSoDienThoai()));
            view.txtDiaChi.setText(nvl(info.getDiaChi()));

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(view, ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void save() {
        try {
            String email = view.txtEmail.getText();
            String sdt = view.txtSoDienThoai.getText();
            String diaChi = view.txtDiaChi.getText();

            boolean ok = service.updateContact(maSv, email, sdt, diaChi);
            if (ok) {
                JOptionPane.showMessageDialog(view, "Cập nhật thành công!");
                loadData();
            } else {
                JOptionPane.showMessageDialog(view, "Không có thay đổi nào!");
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(view, ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private String nvl(String s) { return s == null ? "" : s; }
}
