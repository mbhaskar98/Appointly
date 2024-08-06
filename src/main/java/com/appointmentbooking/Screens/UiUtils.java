package com.appointmentbooking.Screens;

import javax.swing.*;
import java.awt.*;
import java.util.Map;

public class UiUtils {
    static void setScreenBorder(JPanel rootPanel,
                                JPanel mainPanel,
                                JPanel topPanel,
                                JPanel bottomPanel,
                                JPanel leftPanel,
                                JPanel rightPanel) {
        mainPanel.setPreferredSize(rootPanel.getSize());
        topPanel.setPreferredSize(new Dimension(mainPanel.getWidth(), 200));
        bottomPanel.setPreferredSize(new Dimension(mainPanel.getWidth(), 200));
        leftPanel.setPreferredSize(new Dimension(212, mainPanel.getHeight() - (topPanel.getHeight() * 2)));
        rightPanel.setPreferredSize(new Dimension(212, mainPanel.getHeight() - (topPanel.getHeight() * 2)));
    }

    static void showScreen(JPanel appContainerPanel,
                           Map<String, Navigationable> screens,
                           String screeName) {
        Navigationable screen = screens.get(screeName);
        if (screen != null) {
            screen.repaint();
        }
        CardLayout cl = (CardLayout) (appContainerPanel.getLayout());
        cl.show(appContainerPanel, screeName);
    }
}
