package com.appointmentbooking.utils;

import javax.swing.*;
import java.awt.*;

public class UiUtils {
    public static void showPanel(String frameName,
                                 JPanel panel) {
        JFrame frame = new JFrame(frameName);
        frame.setContentPane(panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(new Dimension(1024, 720));
        frame.setVisible(true);
    }
}
