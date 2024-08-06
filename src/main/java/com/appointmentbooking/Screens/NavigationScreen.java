package com.appointmentbooking.Screens;

import javax.swing.*;
import java.util.HashMap;
import java.util.Map;

public class NavigationScreen {
    protected JPanel appContainerPanel;
    protected String parentPanelName;
    protected String childPanelName;

    protected Map<String, Navigationable> screens;
}
