/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;
import view.view_dsmonhocdadangky;
import model.ConnectDB;
import model.model_dsmonhocdadangky;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import model.Session;
/**
 *
 * @author Dvtt
 */
public class controller_dsmonhocdadangky {
    view_dsmonhocdadangky view;
    private Session session;

    public controller_dsmonhocdadangky(view_dsmonhocdadangky view) {
        this.view = view;
        this.session = session;
        loadAll();
        view.btnTim.addActionListener(e -> tim());
    }

    
    private void loadAll() {
        loadTable(getData("", ""));
    }

    
    private void tim() {
        loadTable(
            getData(
                view.txtMaMon.getText().trim(),
                view.txtTenMon.getText().trim()
            )
        );
    }

    
    private List<model_dsmonhocdadangky> getData(String maMon, String tenMon) {
        List<model_dsmonhocdadangky> list = new ArrayList<>();

        try (Connection c = ConnectDB.getConnection()) {
            String sql = """
                SELECT 
                      m.ma_mon, 
                      m.ten_mon, 
                      m.so_tin_chi,
                      l.ma_lhp,
                      GROUP_CONCAT(
                          CONCAT('Thứ ', t.thu, ' | Tiết ',
                                 t.tiet_bat_dau, '-', t.tiet_bat_dau + t.so_tiet - 1)
                          SEPARATOR ' ; '
                      ) AS lich_hoc,
                      l.ma_hoc_ky,
                      hk.ngay_bat_dau,
                      hk.ngay_ket_thuc
                  FROM DangKyHocPhan dk
                  JOIN LopHocPhan l ON dk.ma_lhp = l.ma_lhp
                  JOIN MonHoc m ON l.ma_mon = m.ma_mon
                  JOIN ThoiKhoaBieu t ON l.ma_lhp = t.ma_lhp
                  JOIN HocKy hk ON l.ma_hoc_ky = hk.ma_hoc_ky
                  WHERE dk.ma_sv = ?
                    AND m.ma_mon LIKE ?
                    AND m.ten_mon LIKE ?
                  GROUP BY 
                      m.ma_mon, m.ten_mon, m.so_tin_chi,
                      l.ma_lhp, l.ma_hoc_ky,
                      hk.ngay_bat_dau, hk.ngay_ket_thuc;
            """;

            PreparedStatement ps = c.prepareStatement(sql);
            ps.setString(1, session.getMaLienKet());
            ps.setString(2, "%" + maMon + "%");
            ps.setString(3, "%" + tenMon + "%");

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                list.add(new model_dsmonhocdadangky(
                    rs.getString("ma_mon"),
                    rs.getString("ten_mon"),
                    rs.getInt("so_tin_chi"),
                    rs.getString("ma_lhp"),
                    rs.getString("lich_hoc"),
                    rs.getString("ma_hoc_ky"),
                    rs.getDate("ngay_bat_dau"),
                    rs.getDate("ngay_ket_thuc")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    
    private void loadTable(List<model_dsmonhocdadangky> list) {
        view.model.setRowCount(0);
        for (model_dsmonhocdadangky m : list) {
            view.model.addRow(new Object[]{
                m.maMon,
                m.tenMon,
                m.soTinChi,
                m.lop,
                m.lichHoc,
                m.hocKy,
                m.ngayBatDau,
                m.ngayKetThuc
            });
        }
    }

   
}
