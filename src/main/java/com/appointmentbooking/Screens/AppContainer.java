package com.appointmentbooking.Screens;

import com.appointmentbooking.Constants;
import com.appointmentbooking.backend.AppointmentSlotService;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class AppContainer {

    private JPanel appContainerPanel;

    Map<String, Navigationable> screens;

    public static final String HOME_SCREEN_ID = "homeScreen";
    public static final String SLOTS_SCREEN_ID = "slotsScreen";
    public static final String FORM_SCREEN_ID = "formScreen";
    public static final String ADD_NEW_SCREEN_ID = "addNewScreen";

    public void startApp() {
        // Main frame
        JFrame frame = new JFrame(Constants.APP_NAME);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(new Dimension(1024, 720));

        // Setup child screens
        AppointmentSlotService appointmentSlotService = new AppointmentSlotService();

        Navigationable homeScreen = new HomeScreen();
        Navigationable slotsScreen = new SlotsScreen(appointmentSlotService);
        Navigationable formScreen = new FormScreen(appointmentSlotService);
        Navigationable addNewScreen = new AddNewSlotScreen(appointmentSlotService);


        screens = new HashMap<>();
        screens.put(HOME_SCREEN_ID, homeScreen);
        screens.put(SLOTS_SCREEN_ID, slotsScreen);
        screens.put(FORM_SCREEN_ID, formScreen);
        screens.put(ADD_NEW_SCREEN_ID, addNewScreen);

        homeScreen.setupScreen(appContainerPanel,
                "appContainer",
                SLOTS_SCREEN_ID,
                screens
        );
        slotsScreen.setupScreen(appContainerPanel,
                HOME_SCREEN_ID,
                FORM_SCREEN_ID,
                screens);
        formScreen.setupScreen(appContainerPanel,
                SLOTS_SCREEN_ID,
                "",
                screens);
        addNewScreen.setupScreen(appContainerPanel,
                SLOTS_SCREEN_ID,
                "",
                screens);
        appContainerPanel.add(homeScreen.getMainPanel(), HOME_SCREEN_ID);
        appContainerPanel.add(slotsScreen.getMainPanel(), SLOTS_SCREEN_ID);
        appContainerPanel.add(formScreen.getMainPanel(), FORM_SCREEN_ID);
        appContainerPanel.add(addNewScreen.getMainPanel(), ADD_NEW_SCREEN_ID);

        // Show app
        frame.setContentPane(appContainerPanel);
        frame.setVisible(true);
    }

}
