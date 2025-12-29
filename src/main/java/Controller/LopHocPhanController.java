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
import dao.LopHocPhanDAO;
import Model.ComboItem;
import Model.LopHocPhan;
import Service.LopHocPhanService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.List;
import java.util.Map;

import View.FrmQuanLyLopHocPhan;
public class LopHocPhanController {
        private FrmQuanLyLopHocPhan view;
    private DanhMucDAO dmDao = new DanhMucDAO();
    private LopHocPhanDAO lhpDao = new LopHocPhanDAO();
    private LopHocPhanService service = new LopHocPhanService();

    public LopHocPhanController(FrmQuanLyLopHocPhan view) {
        this.view = view;
        init();
    }

    private void init() {
        loadCombos();
        loadTable();
        bindEvents();
    }

    private void loadCombos() {
        view.cboMonHoc.removeAllItems();
        for (ComboItem it : dmDao.getMonHoc()) view.cboMonHoc.addItem(it);

        view.cboGiangVien.removeAllItems();
        for (ComboItem it : dmDao.getGiangVien()) view.cboGiangVien.addItem(it);

        view.cboHocKy.removeAllItems();
        for (ComboItem it : dmDao.getHocKy()) view.cboHocKy.addItem(it);

        view.cboTrangThai.removeAllItems();
        view.cboTrangThai.addItem("mở");
        view.cboTrangThai.addItem("đóng");
    }

    public void loadTable() {
        String[] cols = {"Mã LHP", "Môn", "Giảng viên", "Học kỳ", "SL tối đa", "SL đã ĐK", "Trạng thái"};
        DefaultTableModel model = new DefaultTableModel(cols, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };

        List<Map<String, Object>> rows = lhpDao.findAllJoin();
        for (Map<String, Object> r : rows) {
            model.addRow(new Object[]{
                    r.get("ma_lhp"),
                    r.get("ten_mon"),
                    r.get("ten_gv"),
                    r.get("ten_hoc_ky"),
                    r.get("so_toi_da"),
                    r.get("so_da_dk"),
                    r.get("trang_thai")
            });
        }
        view.tblLHP.setModel(model);

        // lưu list join cho việc click row set lại combo (nếu muốn bạn có thể lưu rows vào view)
        view._cacheRows = rows;
    }

    private void bindEvents() {
        view.btnMoLop.addActionListener(e -> moLop());
        view.btnSua.addActionListener(e -> sua());
        view.btnXoa.addActionListener(e -> xoa());
        view.btnXuatExcel.addActionListener(e -> xuatExcel());
        view.btnLamMoi.addActionListener(e -> { view.clearForm(); loadTable(); });

        view.tblLHP.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) fillFormFromSelectedRow();
        });
    }

    private LopHocPhan readForm() {
        LopHocPhan lhp = new LopHocPhan();
        lhp.setMaLhp(view.txtMaLHP.getText().trim());
        lhp.setSoLuongToiDa(Integer.parseInt(view.txtSoLuongToiDa.getText().trim()));
        lhp.setTrangThai(view.cboTrangThai.getSelectedItem().toString());

        ComboItem mon = (ComboItem) view.cboMonHoc.getSelectedItem();
        ComboItem gv  = (ComboItem) view.cboGiangVien.getSelectedItem();
        ComboItem hk  = (ComboItem) view.cboHocKy.getSelectedItem();

        lhp.setMaMon(mon.getId());
        lhp.setMaGv(gv.getId());
        lhp.setMaHocKy(hk.getId());
        return lhp;
    }

    private void moLop() {
        try {
            LopHocPhan lhp = readForm();
            if (service.moLop(lhp)) {
                JOptionPane.showMessageDialog(view, "Mở lớp thành công!");
                loadTable();
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(view, ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void sua() {
        try {
            LopHocPhan lhp = readForm();
            if (service.sua(lhp)) {
                JOptionPane.showMessageDialog(view, "Sửa thành công!");
                loadTable();
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(view, ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void xoa() {
        try {
            int row = view.tblLHP.getSelectedRow();
            if (row < 0) throw new IllegalArgumentException("Chọn 1 lớp để xóa!");
            String ma = view.tblLHP.getValueAt(row, 0).toString();

            int ok = JOptionPane.showConfirmDialog(view, "Xóa lớp " + ma + " ?", "Xác nhận", JOptionPane.YES_NO_OPTION);
            if (ok == JOptionPane.YES_OPTION) {
                if (service.xoa(ma)) {
                    JOptionPane.showMessageDialog(view, "Xóa thành công!");
                    loadTable();
                    view.clearForm();
                }
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(view, ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void xuatExcel() {
    try {
        JFileChooser fc = new JFileChooser();
        fc.setDialogTitle("Chọn nơi lưu file Excel");
        fc.setSelectedFile(new java.io.File("lop_hoc_phan.xlsx"));

        int choose = fc.showSaveDialog(view);
        if (choose == JFileChooser.APPROVE_OPTION) {
            String path = fc.getSelectedFile().getAbsolutePath();

            // nếu người dùng chưa gõ .xlsx thì tự thêm
            if (!path.toLowerCase().endsWith(".xlsx")) path += ".xlsx";

            dao.Excel.exportJTableToXlsx(view.tblLHP, path);
            JOptionPane.showMessageDialog(view, "Xuất Excel thành công!\n" + path);
        }
    } catch (Exception ex) {
        JOptionPane.showMessageDialog(view, ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
    }

    
}private void fillFormFromSelectedRow() {
        int row = view.tblLHP.getSelectedRow();
        if (row < 0) return;

        String ma = view.tblLHP.getValueAt(row, 0).toString();
        view.txtMaLHP.setText(ma);

        // lấy mã thật từ cacheRows (vì table đang show tên)
        Map<String, Object> data = view._cacheRows.get(row);
        view.setSelectedComboById(view.cboMonHoc, data.get("ma_mon").toString());
        view.setSelectedComboById(view.cboGiangVien, data.get("ma_gv").toString());
        view.setSelectedComboById(view.cboHocKy, data.get("ma_hoc_ky").toString());

        view.txtSoLuongToiDa.setText(String.valueOf(data.get("so_toi_da")));
        view.txtSoLuongDaDK.setText(String.valueOf(data.get("so_da_dk")));
        view.cboTrangThai.setSelectedItem(data.get("trang_thai").toString());
    }
}