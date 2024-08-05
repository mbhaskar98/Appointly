package com.appointmentbooking.backend;

import java.util.Collections;
import java.util.List;

public class AppointmentSlotService {
    /* Can use dependency injection to provide repository object in future in order to unit test this service*/
    private final AppointmentSlotRepository repository = new AppointmentSlotRepository();

    public List<AppointmentSlot> getAllAppointments() {
        List<AppointmentSlot> appointmentSlots = repository.getAllAppointmentSlots();
        Collections.sort(appointmentSlots);
        return appointmentSlots;
    }

    public void addNewAppointment(AppointmentSlot newAppointmentSlot) throws ApplicationException {
        validateAppointment(newAppointmentSlot);
        if (!repository.insertAppointmentSlot(newAppointmentSlot)) {
            throw new ApplicationException("Insert failed");
        }
    }

    public void updateAppointment(AppointmentSlot appointmentSlot) throws ApplicationException {
        validateAppointment(appointmentSlot);
        if (!repository.updateAppointmentSlot(appointmentSlot)) {
            throw new ApplicationException("Update failed");
        }
    }

    public void deleteAppointment(AppointmentSlot appointmentSlot) {
        repository.deleteAppointment(appointmentSlot);
    }

    private void validateAppointment(AppointmentSlot newAppointmentSlot) throws ApplicationException {
        List<AppointmentSlot> appointmentSlots = getAllAppointments();

        int newSlotStartTime = newAppointmentSlot.startTime();
        int newSlotEndTime = newAppointmentSlot.endTime();
        for (AppointmentSlot appointmentSlot : appointmentSlots) {
            if ((appointmentSlot.startTime() <= newSlotStartTime && newSlotStartTime < appointmentSlot.endTime()) ||
                    appointmentSlot.startTime() < newSlotEndTime && newSlotEndTime <= appointmentSlot.endTime()) {
                throw new ApplicationException("Overlapping appointment");
            }
        }
    }

}
