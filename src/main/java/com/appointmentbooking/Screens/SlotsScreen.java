package com.appointmentbooking.Screens;

import com.appointmentbooking.backend.AppointmentSlot;
import com.appointmentbooking.backend.AppointmentSlotService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Map;

public class SlotsScreen extends NavigationScreen implements Navigationable {
    private JPanel mainPanel;
    private JPanel topPanel;
    private JPanel bottomPanel;
    private JPanel leftPanel;
    private JPanel rightPanel;
    private JLabel heading;
    private JPanel listingPanel;
    private JPanel headingAndListingPanel;
    private JPanel slotPanel;
    private JButton slotBtn;
    private JButton addNewBtn;
    private JPanel actionButtonPanel;

    private final AppointmentSlotService appointmentSlotService;

    SlotsScreen(AppointmentSlotService appointmentSlotService) {
        this.appointmentSlotService = appointmentSlotService;
        try {
//            appointmentSlotService.addNewAppointment(new AppointmentSlot(1, 2, "", ""));
//            appointmentSlotService.addNewAppointment(new AppointmentSlot(4, 7, "John Doe", "", false));
//            appointmentSlotService.addNewAppointment(new AppointmentSlot(2, 3, "John Doe", "", false));
//            appointmentSlotService.addNewAppointment(new AppointmentSlot(15, 16, "John Doe", ""));
//            appointmentSlotService.addNewAppointment(new AppointmentSlot(10, 11, "John Doe", ""));
//            appointmentSlotService.addNewAppointment(new AppointmentSlot(12, 13, "John Doe", ""));
//            appointmentSlotService.addNewAppointment(new AppointmentSlot(18, 20, "John Doe", "", false));
//            appointmentSlotService.addNewAppointment(new AppointmentSlot(22, 23, "John Doe", "", false));
        } catch (Exception e) {
            System.out.println("ERR: Error while adding slots:" + e.toString());
        }

        addNewBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showAddNewScreen();
            }
        });
    }

    @Override
    public JPanel getMainPanel() {
        return mainPanel;
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

        UiUtils.setScreenBorder(appContainerPanel,
                mainPanel,
                topPanel,
                bottomPanel,
                leftPanel,
                rightPanel);

        fetchNewData();
    }

    @Override
    public void repaint() {
        setupScreen(
                appContainerPanel,
                parentPanelName,
                childPanelName,
                screens);
    }

    private void fetchNewData() {
        // Clear everything
        listingPanel.removeAll();

        // Fetch latest list and display
        List<AppointmentSlot> appointmentSlots = appointmentSlotService.getAllAppointments();
        for (AppointmentSlot appointmentSlot : appointmentSlots) {
            listingPanel.add(getAppointmentSlotPanel(appointmentSlot));
        }
    }

    private JPanel getAppointmentSlotPanel(AppointmentSlot appointmentSlot) {
        StringBuilder buttonText = new StringBuilder();
        buttonText.append(appointmentSlotService.getID(appointmentSlot));
        if (!appointmentSlot.isAvailable()) {
            buttonText.append("  (Booked)");
        }
        JPanel panel = new JPanel(new GridLayout());
        panel.setPreferredSize(slotPanel.getPreferredSize());
        JButton button = new JButton(buttonText.toString());

        // Add MouseListener to the JButton
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showFormScreen(appointmentSlot);
            }
        });

        panel.add(button);

        return panel;
    }

    private void showAddNewScreen() {
        Navigationable childScreen = screens.get(AppContainer.ADD_NEW_SCREEN_ID);
        CardLayout cl = (CardLayout) (appContainerPanel.getLayout());
        cl.show(appContainerPanel, AppContainer.ADD_NEW_SCREEN_ID);
    }

    private void showFormScreen(AppointmentSlot appointmentSlot) {
        Navigationable childScreen = screens.get(AppContainer.FORM_SCREEN_ID);
        StateManager.setCurrentSelectedSlot(appointmentSlot);
        if (childScreen != null) {
            childScreen.repaint();
        }
        CardLayout cl = (CardLayout) (appContainerPanel.getLayout());
        cl.show(appContainerPanel, AppContainer.FORM_SCREEN_ID);
    }
}
