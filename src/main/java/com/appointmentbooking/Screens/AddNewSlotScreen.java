package com.appointmentbooking.Screens;

import com.appointmentbooking.backend.AppointmentSlot;
import com.appointmentbooking.backend.AppointmentSlotService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;

public class AddNewSlotScreen extends NavigationScreen implements Navigationable {
    private JPanel mainPanel;
    private JPanel topPanel;
    private JPanel bottomPanel;
    private JPanel leftPanel;
    private JPanel rightPanel;
    private JPanel headingAndFormPanel;
    private JLabel heading;
    private JPanel formPanel;
    private JTextField starTimeField;
    private JTextField endTimeField;
    private JButton cancelBtn;
    private JButton saveBtn;

    private final AppointmentSlotService appointmentSlotService;

    AddNewSlotScreen(AppointmentSlotService appointmentSlotService) {
        this.appointmentSlotService = appointmentSlotService;
        cancelBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearFields();
                showSlotsScreen();
            }
        });
        saveBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    AppointmentSlot newSlot = new AppointmentSlot(
                            Integer.parseInt(starTimeField.getText()),
                            Integer.parseInt(endTimeField.getText())
                    );
                    appointmentSlotService.addNewAppointment(newSlot);
                    clearFields();
                    showSlotsScreen();
                } catch (Exception exp) {
                    StringBuilder errorMessage = new StringBuilder();
                    errorMessage.append("ERR: Object not valid:");
                    errorMessage.append(exp.toString());
                    System.out.println(errorMessage.toString());
                    JOptionPane.showMessageDialog(mainPanel,
                            errorMessage.toString(),
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
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

    }

    @Override
    public void repaint() {

    }

    private void clearFields() {
        starTimeField.setText("");
        endTimeField.setText("");
    }

    private void showSlotsScreen() {
        UiUtils.showScreen(appContainerPanel, screens, parentPanelName);
    }
}
