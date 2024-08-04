package com.appointmentbooking.Screens;

import javax.swing.*;

public interface Navigationable {

    public JPanel getMainPanel();

    public void setupScreen(JPanel appContainerPanel,
                            String parentPanelName,
                            String childPanelName);
}
