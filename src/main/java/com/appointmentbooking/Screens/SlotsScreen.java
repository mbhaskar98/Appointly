package com.appointmentbooking.Screens;

import com.appointmentbooking.backend.AppointmentSlotService;

import javax.swing.*;

public class SlotsScreen extends NavigationScreen implements Navigationable {
    private JPanel slotsPanel;
    private JLabel lblGoBack;

    private final AppointmentSlotService appointmentSlotService = new AppointmentSlotService();


    public SlotsScreen() {

//        lblGoBack.addMouseListener(new MouseAdapter() {
//            @Override
//            public void mouseClicked(MouseEvent e) {
//                super.mouseClicked(e);
//                CardLayout cl = (CardLayout) (appContainerPanel.getLayout());
//                cl.show(appContainerPanel, parentPanelName);
//            }
//        });
    }


    @Override
    public JPanel getMainPanel() {
        return slotsPanel;
    }

    @Override
    public void setupScreen(JPanel appContainerPanel, String parentPanelName, String childPanelName) {
        this.appContainerPanel = appContainerPanel;
        this.parentPanelName = parentPanelName;
        this.childPanelName = childPanelName;

    }

}
