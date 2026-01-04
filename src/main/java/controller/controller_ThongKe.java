/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

/**
 *
 * @author HUY
 */
import model.model_ThongKe;
import model.model_ThongKe.ThongKeMon;
import view.view_ThongKe;

import javax.swing.*;
import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.Path;
import java.util.List;

// Apache POI
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class controller_ThongKe {

    private final view_ThongKe view;
    private final model_ThongKe model;

    // cache để export
    private String cacheHocKy;
    private String cacheNamHoc;
    private int cacheTong;
    private List<ThongKeMon> cacheList;

    public controller_ThongKe(view_ThongKe view) {
        this.view = view;
        this.model = new model_ThongKe();

        init();
        bindEvents();
    }

    private void init() {
        try {
            view.cboHocKy.removeAllItems();
            for (String hk : model.getDsMaHocKy()) view.cboHocKy.addItem(hk);

            if (view.cboHocKy.getItemCount() > 0) {
                view.cboHocKy.setSelectedIndex(0);
                updateNamHocField();
                thongKe();
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(view, "Lỗi load học kỳ: " + e.getMessage());
        }
    }

    private void bindEvents() {
        view.cboHocKy.addActionListener(e -> updateNamHocField());
        view.btnThongKe.addActionListener(e -> thongKe());
        view.btnLamMoi.addActionListener(e -> lamMoi());
        view.btnQuayLai.addActionListener(e -> view.dispose());
        view.btnXuatExcel.addActionListener(e -> xuatExcel());
    }

    private void updateNamHocField() {
        String hk = (String) view.cboHocKy.getSelectedItem();
        if (hk == null) return;
        try {
            String namHoc = model.getNamHocByHocKy(hk);
            view.txtNamHoc.setText(namHoc);
        } catch (Exception ex) {
            view.txtNamHoc.setText("");
        }
    }

    private void lamMoi() {
        view.txtTongLuot.setText("0");
        view.clearTable();
        cacheHocKy = null;
        cacheNamHoc = null;
        cacheTong = 0;
        cacheList = null;
    }

    private void thongKe() {
        String hk = (String) view.cboHocKy.getSelectedItem();
        if (hk == null) return;

        try {
            String namHoc = model.getNamHocByHocKy(hk);
            int tong = model.getTongLuotDangKy(hk);
            List<ThongKeMon> list = model.getThongKeTheoMon(hk);

            view.txtNamHoc.setText(namHoc);
            view.txtTongLuot.setText(String.valueOf(tong));

            view.clearTable();
            for (ThongKeMon r : list) {
                view.addRow(r.tenMon, r.soSv);
            }

            // cache để export
            cacheHocKy = hk;
            cacheNamHoc = namHoc;
            cacheTong = tong;
            cacheList = list;

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(view, "Lỗi thống kê: " + ex.getMessage());
        }
    }

    private void xuatExcel() {
        if (cacheHocKy == null || cacheList == null) {
            JOptionPane.showMessageDialog(view, "Chưa có dữ liệu. Bấm Thống kê trước.");
            return;
        }

        JFileChooser chooser = new JFileChooser();
        chooser.setDialogTitle("Chọn nơi lưu file Excel");
        chooser.setSelectedFile(new File("ThongKe_" + cacheHocKy + ".xlsx"));

        int res = chooser.showSaveDialog(view);
        if (res != JFileChooser.APPROVE_OPTION) return;

        File f = chooser.getSelectedFile();
        String p = f.getAbsolutePath();
        if (!p.toLowerCase().endsWith(".xlsx")) f = new File(p + ".xlsx");

        try {
            writeExcel(Path.of(f.getAbsolutePath()));
            JOptionPane.showMessageDialog(view, "Xuất Excel thành công:\n" + f.getAbsolutePath());
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(view, "Xuất Excel lỗi: " + ex.getMessage());
        }
    }

    private void writeExcel(Path filePath) throws Exception {
        try (Workbook wb = new XSSFWorkbook()) {
            Sheet sheet = wb.createSheet("ThongKe_" + cacheHocKy);

            int r = 0;

            Row title = sheet.createRow(r++);
            title.createCell(0).setCellValue("THỐNG KÊ ĐĂNG KÝ TÍN CHỈ");

            Row info1 = sheet.createRow(r++);
            info1.createCell(0).setCellValue("Học kỳ");
            info1.createCell(1).setCellValue(cacheHocKy);

            Row info2 = sheet.createRow(r++);
            info2.createCell(0).setCellValue("Năm học");
            info2.createCell(1).setCellValue(cacheNamHoc == null ? "" : cacheNamHoc);

            Row info3 = sheet.createRow(r++);
            info3.createCell(0).setCellValue("Tổng lượt đăng ký");
            info3.createCell(1).setCellValue(cacheTong);

            r++; // dòng trống

            Row header = sheet.createRow(r++);
            header.createCell(0).setCellValue("Môn học");
            header.createCell(1).setCellValue("Số SV");

            for (ThongKeMon row : cacheList) {
                Row rr = sheet.createRow(r++);
                rr.createCell(0).setCellValue(row.tenMon);
                rr.createCell(1).setCellValue(row.soSv);
            }

            sheet.autoSizeColumn(0);
            sheet.autoSizeColumn(1);

            try (FileOutputStream fos = new FileOutputStream(filePath.toFile())) {
                wb.write(fos);
            }
        }
    }
}