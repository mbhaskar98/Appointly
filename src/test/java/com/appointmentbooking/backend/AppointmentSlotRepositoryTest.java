package com.appointmentbooking.backend;


import org.junit.*;

import static org.junit.Assert.*;

import java.util.List;

public class AppointmentSlotRepositoryTest {
    private static final AppointmentSlotRepository repository = new AppointmentSlotRepository();

    @Before
    public void setup() {
        repository.deleteDB();
        repository.initDB();
    }

    @After
    public void tearDownClass() {
        repository.deleteDB();
    }

    private static final AppointmentSlot originalSlot = new AppointmentSlot(1, 2, "name", "des");


    @Test
    public void testInsert() {
        assertTrue(repository.insertAppointmentSlot(originalSlot));
        assertFalse(repository.insertAppointmentSlot(originalSlot));
        List<AppointmentSlot> appointmentSlots = repository.getAllAppointmentSlots();
        System.out.println("Size is " + appointmentSlots.size());
        for (AppointmentSlot appointmentSlot : appointmentSlots) {
            System.out.print("ID:" + repository.getID(appointmentSlot));
        }

        assertEquals(1, appointmentSlots.size());
        assertEquals(originalSlot, appointmentSlots.get(0));
    }

    @Test
    public void testUpdate() {
        assertTrue(repository.insertAppointmentSlot(originalSlot));
        assertEquals(repository.getAllAppointmentSlots().size(), 1);
        AppointmentSlot updatedSlot = new AppointmentSlot(
                originalSlot.startTime(),
                originalSlot.endTime(),
                "newPatient",
                "New Description",
                false
        );
        assertTrue(repository.updateAppointmentSlot(updatedSlot));
        assertEquals(repository.getAllAppointmentSlots().size(), 1);
        assertEquals(repository.getAllAppointmentSlots().get(0), updatedSlot);

        AppointmentSlot invalidUpdateSlot = new AppointmentSlot(
                originalSlot.startTime() + 1,
                originalSlot.endTime() + 1,
                originalSlot.patientName(),
                originalSlot.description(),
                originalSlot.isAvailable()
        );
        assertFalse(repository.updateAppointmentSlot(invalidUpdateSlot));
    }

    @Test
    public void testDeleteById() {
        try {
            assertTrue(repository.insertAppointmentSlot(originalSlot));
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        assertEquals(1, repository.getAllAppointmentSlots().size());
        repository.deleteAppointment(originalSlot);
        assertEquals(0, repository.getAllAppointmentSlots().size());


        assertTrue(repository.insertAppointmentSlot(originalSlot));
        assertEquals(1, repository.getAllAppointmentSlots().size());
        AppointmentSlot invalidSlot = new AppointmentSlot(
                originalSlot.startTime() + 1,
                originalSlot.endTime() + 1,
                originalSlot.patientName(),
                originalSlot.description(),
                originalSlot.isAvailable()
        );
        repository.deleteAppointment(invalidSlot);
        assertEquals(1, repository.getAllAppointmentSlots().size());
    }
}
