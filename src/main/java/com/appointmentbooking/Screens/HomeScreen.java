package com.appointmentbooking.Screens;

import com.appointmentbooking.Constants;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
                            String childPanelName) {
        this.appContainerPanel = appContainerPanel;
        this.parentPanelName = parentPanelName;
        this.childPanelName = childPanelName;
    }

    @Override
    public JPanel getMainPanel() {
        return homePanel;
    }
}
