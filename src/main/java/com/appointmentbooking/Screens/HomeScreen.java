package com.appointmentbooking.Screens;

import com.appointmentbooking.Constants;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;

public class HomeScreen extends NavigationScreen implements Navigationable {
    private JLabel welcomeMessage;
    private JPanel homePanel;
    private JButton btnAppointments;

    HomeScreen() {
        //Setup message
        welcomeMessage.setText("Welcome to " + Constants.APP_NAME);

        //Setup button
        btnAppointments.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CardLayout cl = (CardLayout) (appContainerPanel.getLayout());
                cl.show(appContainerPanel, childPanelName);
            }
        });
    }

    @Override
    public void setupScreen(JPanel appContainerPanel,
                            String parentPanelName,
                            String childPanelName,
                            Map<String, Navigationable> screens) {
        this.appContainerPanel = appContainerPanel;
        this.parentPanelName = parentPanelName;
        this.childPanelName = childPanelName;
        this.screens = screens;
    }

    @Override
    public void repaint() {
        setupScreen(
                appContainerPanel,
                parentPanelName,
                childPanelName,
                screens
        );
    }

    @Override
    public JPanel getMainPanel() {
        return homePanel;
    }
}
