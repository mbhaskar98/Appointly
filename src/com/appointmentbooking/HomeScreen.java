package com.appointmentbooking;
import com.appointmentbooking.utils.UiUtils;

import javax.swing.*;

public class HomeScreen {
    private JLabel welcomeMessage;
    private JPanel homePanel;

    HomeScreen() {
        welcomeMessage.setText("Welcome to " + Constants.APP_NAME);
    }
    public void show() {
        UiUtils.showPanel(Constants.APP_NAME, homePanel);
    }
}
