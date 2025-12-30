package view;

import javax.swing.*;
import java.awt.*;

public class view_ThoiKhoaBieu extends JPanel {

    public view_ThoiKhoaBieu() {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        JLabel lbl = new JLabel("THỜI KHOÁ BIỂU", SwingConstants.CENTER);
        lbl.setFont(new Font("Segoe UI", Font.BOLD, 22));

        add(lbl, BorderLayout.NORTH);
    }
}
