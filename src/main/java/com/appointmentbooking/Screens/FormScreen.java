package com.appointmentbooking.Screens;

import com.appointmentbooking.backend.AppointmentSlot;
import com.appointmentbooking.backend.AppointmentSlotService;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;

public class FormScreen extends NavigationScreen implements Navigationable {
    private JPanel mainPanel;
    private JPanel topPanel;
    private JPanel bottomPanel;
    private JPanel leftPanel;
    private JPanel rightPanel;
    private JPanel headingAndFormPanel;
    private JPanel formPanel;
    private JLabel heading;
    private JButton deleteBtn;
    private JButton saveBtn;
    private JTextField timeField;
    private JTextField patientNameField;
    private JTextField descField;
    private JButton cancelBtn;
    private JButton clearBtn;

    private AppointmentSlot currentSlot;

    private final AppointmentSlotService appointmentSlotService;

    FormScreen(AppointmentSlotService appointmentSlotService) {
        this.appointmentSlotService = appointmentSlotService;
        // Can't change time once created
        timeField.setEditable(false);
        saveBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    AppointmentSlot updatedSlot = new AppointmentSlot(
                            currentSlot.startTime(),
                            currentSlot.endTime(),
                            patientNameField.getText(),
                            descField.getText(),
                            false
                    );
                    updateAndGotoSlotsScreen(updatedSlot);
                } catch (Exception exp) {
                    StringBuilder errorMessage = new StringBuilder();
                    errorMessage.append("ERR: Update failed:");
                    errorMessage.append(exp.toString());
                    System.out.println(errorMessage.toString());
                    JOptionPane.showMessageDialog(mainPanel,
                            errorMessage.toString(),
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        deleteBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                appointmentSlotService.deleteAppointment(currentSlot);
                showSlotsScreen();
            }
        });
        cancelBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showSlotsScreen();
            }
        });
        clearBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    AppointmentSlot updatedSlot = new AppointmentSlot(
                            currentSlot.startTime(),
                            currentSlot.endTime()
                    );
                    updateAndGotoSlotsScreen(updatedSlot);
                } catch (Exception exp) {
                    StringBuilder errorMessage = new StringBuilder();
                    errorMessage.append("ERR: Update failed:");
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

        if (currentSlot != null) {
            timeField.setText(appointmentSlotService.getID(currentSlot));
            patientNameField.setText(currentSlot.patientName());
            descField.setText(currentSlot.description());
        }

    }

    @Override
    public void repaint() {
        currentSlot = StateManager.getCurrentSelectedSlot();
        setupScreen(appContainerPanel,
                parentPanelName,
                childPanelName,
                screens);
    }

    private void showSlotsScreen() {
        StateManager.setCurrentSelectedSlot(null);
        UiUtils.showScreen(appContainerPanel, screens, parentPanelName);
    }

    private void updateAndGotoSlotsScreen(AppointmentSlot updatedSlot) {
        try {
            appointmentSlotService.updateAppointment(updatedSlot);
            showSlotsScreen();
        } catch (Exception exp) {
            StringBuilder errorMessage = new StringBuilder();
            errorMessage.append("ERR: Update failed:");
            errorMessage.append(exp.toString());
            System.out.println(errorMessage.toString());
            JOptionPane.showMessageDialog(mainPanel,
                    errorMessage.toString(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
}
