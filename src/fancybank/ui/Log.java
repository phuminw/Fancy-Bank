package fancybank.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Log extends JFrame {
    public Log(String log, Component parent) {
        setTitle("Log");
        setLocationRelativeTo(parent);
        JPanel panel = new JPanel();
        JScrollPane scrlTxt = new JScrollPane(panel);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        String[] lines = log.split("\n");
        for (int i = 0; i < lines.length; ++i) {
            panel.add(new JLabel(lines[i]));
        }
        add(scrlTxt);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        pack();
        setVisible(true);
    }
}