package com.appointmentbooking.backend;


import org.junit.AfterClass;
import org.junit.Test;

import static org.junit.Assert.*;

import java.util.List;

public class AppointmentSlotRespositoryTest {
    private static final AppointmentSlotRespository respository = new AppointmentSlotRespository();

    @AfterClass
    public static void tearDownClass() {
        respository.deleteDB();
    }


    @Test
    public void testGetAll() {
        AppointmentSlot original = new AppointmentSlot(1, 2, "name", "des");
        assertTrue(respository.insertAppointmentSlot(original));
        assertFalse(respository.insertAppointmentSlot(original));
        List<AppointmentSlot> appointmentSlots = respository.getAllAppointmentSlots();
        System.out.println("Size is " + appointmentSlots.size());
        for (AppointmentSlot appointmentSlot : appointmentSlots) {
            System.out.print("ID:" + respository.getID(appointmentSlot));
        }

        assertEquals(1, appointmentSlots.size());
        assertEquals(original, appointmentSlots.get(0));
    }
}
