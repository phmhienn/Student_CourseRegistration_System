/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

/**
 *
 * @author HUY
 */
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.swing.JTable;
import java.io.FileOutputStream;
public class Excel {
    public static void exportJTableToXlsx(JTable table, String filePath) {
        try (Workbook wb = new XSSFWorkbook();
             FileOutputStream fos = new FileOutputStream(filePath)) {

            Sheet sheet = wb.createSheet("LopHocPhan");

            // Header style (đơn giản)
            Font headerFont = wb.createFont();
            headerFont.setBold(true);
            CellStyle headerStyle = wb.createCellStyle();
            headerStyle.setFont(headerFont);

            // Header
            Row header = sheet.createRow(0);
            for (int c = 0; c < table.getColumnCount(); c++) {
                Cell cell = header.createCell(c);
                cell.setCellValue(table.getColumnName(c));
                cell.setCellStyle(headerStyle);
            }

            // Data
            for (int r = 0; r < table.getRowCount(); r++) {
                Row row = sheet.createRow(r + 1);
                for (int c = 0; c < table.getColumnCount(); c++) {
                    Object val = table.getValueAt(r, c);
                    Cell cell = row.createCell(c);
                    cell.setCellValue(val == null ? "" : val.toString());
                }
            }

            // autosize
            for (int c = 0; c < table.getColumnCount(); c++) {
                sheet.autoSizeColumn(c);
            }

            wb.write(fos);
        } catch (Exception e) {
            throw new RuntimeException("Xuất Excel lỗi: " + e.getMessage(), e);
        }
    }
}
