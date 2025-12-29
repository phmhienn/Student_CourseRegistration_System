package view;

import java.awt.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class view_dsmonhocdadangky extends JPanel {

    public JTextField txtMaMon = new JTextField();
    public JTextField txtTenMon = new JTextField();
    public JButton btnTim = new JButton("Tìm kiếm");

    public JTable table;
    public DefaultTableModel model;

    public view_dsmonhocdadangky() {

        setLayout(new BorderLayout(8, 8));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        
        JLabel title = new JLabel("Danh sách môn đã đăng ký", JLabel.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 24));
        title.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(title, BorderLayout.NORTH);

        
        JPanel search = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 5));
        txtMaMon.setPreferredSize(new Dimension(150, 26));
        txtTenMon.setPreferredSize(new Dimension(150, 26));

        search.add(new JLabel("Mã môn"));
        search.add(txtMaMon);
        search.add(new JLabel("Tên môn"));
        search.add(txtTenMon);
        search.add(btnTim);

       
        model = new DefaultTableModel(
            new String[]{
                "Mã môn", "Tên môn", "Số TC", "Lớp", "Lịch học",
                "Học kỳ", "Ngày bắt đầu", "Ngày kết thúc"
            }, 0
        );

        table = new JTable(model);
        table.setRowHeight(26);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(5, 10, 10, 10));

        
        JPanel centerPanel = new JPanel(new BorderLayout(0, 15));
        centerPanel.add(search, BorderLayout.NORTH);
        centerPanel.add(scrollPane, BorderLayout.CENTER);

        add(centerPanel, BorderLayout.CENTER);
    }
}
