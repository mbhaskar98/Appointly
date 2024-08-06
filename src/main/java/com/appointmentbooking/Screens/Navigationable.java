package com.appointmentbooking.Screens;

import javax.swing.*;
import java.util.Map;

public interface Navigationable {

    public JPanel getMainPanel();

    public void setupScreen(JPanel appContainerPanel,
                            String parentPanelName,
                            String childPanelName,
                            Map<String, Navigationable> screens);

    public void repaint();
}
