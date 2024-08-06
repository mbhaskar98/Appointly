package com.appointmentbooking.Screens;

import com.appointmentbooking.Constants;

import javax.swing.*;
import java.awt.*;

public class AppContainer {

    private JPanel appContainerPanel;

    public void startApp() {
        // Main frame
        JFrame frame = new JFrame(Constants.APP_NAME);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(new Dimension(1024, 720));

        // Setup child screens
        HomeScreen abc = new HomeScreen();
        Navigationable homeScreen = new HomeScreen();
        Navigationable slotsScreen = new SlotsScreen();
        homeScreen.setupScreen(appContainerPanel,
                "appContainer",
                "slotsScreen"
        );
        slotsScreen.setupScreen(appContainerPanel,
                "homeScreen",
                "");
        appContainerPanel.add(homeScreen.getMainPanel(), "homeScreen");
        appContainerPanel.add(slotsScreen.getMainPanel(), "slotsScreen");

        // Show app
        frame.setContentPane(appContainerPanel);
        frame.setVisible(true);
    }

}
