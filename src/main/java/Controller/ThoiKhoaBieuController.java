/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

/**
 *
 * @author HUY
 */
import dao.DanhMucDAO;
import Model.ComboItem;
import Model.ThoiKhoaBieuSV;
import Service.ThoiKhoaBieuService;
import View.FrmThoiKhoaBieuSinhVien;

import javax.swing.table.DefaultTableModel;
import java.util.List;
public class ThoiKhoaBieuController {
    private final FrmThoiKhoaBieuSinhVien view;
    private final String maSv;
    private final ThoiKhoaBieuService service = new ThoiKhoaBieuService();
    private final DanhMucDAO dmDao = new DanhMucDAO();

    public ThoiKhoaBieuController(FrmThoiKhoaBieuSinhVien view, String maSv) {
        this.view = view;
        this.maSv = maSv;
        init();
    }

    private void init() {
        loadHocKy();
        bindEvents();
    }

    private void loadHocKy() {
        view.cboHocKy.removeAllItems();
        for (ComboItem hk : dmDao.getHocKy()) {
            view.cboHocKy.addItem(hk);
        }
    }

    private void bindEvents() {
        view.cboHocKy.addActionListener(e -> loadTKB());
    }

    private void loadTKB() {
        ComboItem hk = (ComboItem) view.cboHocKy.getSelectedItem();
        if (hk == null) return;

        List<ThoiKhoaBieuSV> list = service.getTKB(maSv, hk.getId());

        DefaultTableModel model = (DefaultTableModel) view.tblTKB.getModel();
        model.setRowCount(0);

        for (ThoiKhoaBieuSV t : list) {
            model.addRow(new Object[]{
                t.getTenMon(),
                t.getMaLhp(),
                "Thứ " + t.getThu(),
                t.getTietBatDau(),
                t.getSoTiet(),
                t.getPhongHoc()
            });
        }
    }
}
