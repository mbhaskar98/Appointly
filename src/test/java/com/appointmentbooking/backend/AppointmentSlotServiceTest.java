package com.appointmentbooking.backend;

import org.junit.AfterClass;
import org.junit.Test;

import static org.junit.Assert.*;

public class AppointmentSlotServiceTest {
    private static final AppointmentSlotService appointmentSlotService = new AppointmentSlotService();

    @AfterClass
    public static void tearDownClass() {
        appointmentSlotService.deleteDB();
    }

    @Test
    public void testAddMultipleEntries() {
        try {
            appointmentSlotService.addNewAppointment(new AppointmentSlot(1, 2, "", ""));
            appointmentSlotService.addNewAppointment(new AppointmentSlot(2, 3, "some patient", "", false));
        } catch (Exception e) {
            System.out.println("ERR: while adding new data:" + e.toString());
        }
        assertEquals(appointmentSlotService.getAllAppointments().size(), 2);

    }
}
