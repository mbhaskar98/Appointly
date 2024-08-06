package com.appointmentbooking.Screens;

import com.appointmentbooking.backend.AppointmentSlot;

public class StateManager {
    static private AppointmentSlot currentSelectedSlot;

    static void setCurrentSelectedSlot(AppointmentSlot currentSelectedSlot) {
        synchronized (StateManager.class) {
            StateManager.currentSelectedSlot = currentSelectedSlot;
        }
    }

    static AppointmentSlot getCurrentSelectedSlot() {
        synchronized (StateManager.class) {
            return StateManager.currentSelectedSlot;
        }
    }
}
