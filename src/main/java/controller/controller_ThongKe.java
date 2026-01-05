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

        // ===== Styles =====
        CellStyle headerStyle = wb.createCellStyle();
        Font headerFont = wb.createFont();
        headerFont.setBold(true);
        headerStyle.setFont(headerFont);
        headerStyle.setAlignment(HorizontalAlignment.CENTER);
        headerStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        headerStyle.setFillForegroundColor(IndexedColors.LIGHT_GREEN.getIndex());
        headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        headerStyle.setBorderTop(BorderStyle.THIN);
        headerStyle.setBorderBottom(BorderStyle.THIN);
        headerStyle.setBorderLeft(BorderStyle.THIN);
        headerStyle.setBorderRight(BorderStyle.THIN);

        CellStyle textStyle = wb.createCellStyle();
        textStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        textStyle.setAlignment(HorizontalAlignment.LEFT);
        textStyle.setBorderTop(BorderStyle.THIN);
        textStyle.setBorderBottom(BorderStyle.THIN);
        textStyle.setBorderLeft(BorderStyle.THIN);
        textStyle.setBorderRight(BorderStyle.THIN);

        CellStyle numberStyle = wb.createCellStyle();
        numberStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        numberStyle.setAlignment(HorizontalAlignment.CENTER);
        numberStyle.setBorderTop(BorderStyle.THIN);
        numberStyle.setBorderBottom(BorderStyle.THIN);
        numberStyle.setBorderLeft(BorderStyle.THIN);
        numberStyle.setBorderRight(BorderStyle.THIN);

        int r = 0;

        // ===== Header row =====
        Row header = sheet.createRow(r++);
        header.setHeightInPoints(20);

        String[] cols = {"Học kỳ", "Năm học", "Tổng lượt đăng ký", "Môn học", "Số SV"};
        for (int c = 0; c < cols.length; c++) {
            Cell cell = header.createCell(c);
            cell.setCellValue(cols[c]);
            cell.setCellStyle(headerStyle);
        }

        // ===== Data rows =====
        // Mỗi môn là 1 dòng, nhưng Học kỳ/Năm học/Tổng lượt giống nhau
        if (cacheList == null || cacheList.isEmpty()) {
            Row row = sheet.createRow(r++);
            Cell c0 = row.createCell(0); c0.setCellValue(cacheHocKy); c0.setCellStyle(textStyle);
            Cell c1 = row.createCell(1); c1.setCellValue(cacheNamHoc == null ? "" : cacheNamHoc); c1.setCellStyle(textStyle);
            Cell c2 = row.createCell(2); c2.setCellValue(cacheTong); c2.setCellStyle(numberStyle);
            Cell c3 = row.createCell(3); c3.setCellValue(""); c3.setCellStyle(textStyle);
            Cell c4 = row.createCell(4); c4.setCellValue(0); c4.setCellStyle(numberStyle);
        } else {
            for (ThongKeMon tk : cacheList) {
                Row row = sheet.createRow(r++);

                Cell c0 = row.createCell(0); // Học kỳ
                c0.setCellValue(cacheHocKy);
                c0.setCellStyle(textStyle);

                Cell c1 = row.createCell(1); // Năm học
                c1.setCellValue(cacheNamHoc == null ? "" : cacheNamHoc);
                c1.setCellStyle(textStyle);

                Cell c2 = row.createCell(2); // Tổng lượt đăng ký
                c2.setCellValue(cacheTong);
                c2.setCellStyle(numberStyle);

                Cell c3 = row.createCell(3); // Môn học
                c3.setCellValue(tk.tenMon);
                c3.setCellStyle(textStyle);

                Cell c4 = row.createCell(4); // Số SV
                c4.setCellValue(tk.soSv);
                c4.setCellStyle(numberStyle);
            }
        }

        // ===== Auto size columns =====
        for (int c = 0; c < cols.length; c++) sheet.autoSizeColumn(c);

        // Freeze header
        sheet.createFreezePane(0, 1);

        try (FileOutputStream fos = new FileOutputStream(filePath.toFile())) {
            wb.write(fos);
        }
    }
}
}