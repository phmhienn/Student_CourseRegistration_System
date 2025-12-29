/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Main;

/**
 *
 * @author HUY
 */
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import dao.DBconn;
public class TestDB {
    public static void main(String[] args) {
        try (Connection c = DBconn.getConnection();
             Statement st = c.createStatement();
             ResultSet rs = st.executeQuery("SHOW TABLES")) {

            System.out.println("✅ Kết nối MySQL thành công!");
            System.out.println("📂 Danh sách bảng:");

            while (rs.next()) {
                System.out.println("- " + rs.getString(1));
            }

        } catch (Exception e) {
            System.err.println("❌ Lỗi kết nối DB");
            e.printStackTrace();
        }
    }
}
