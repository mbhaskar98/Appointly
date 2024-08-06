package com.appointmentbooking.backend;

public record AppointmentSlot(int startTime,
                              int endTime,
                              String patientName,
                              String description,
                              boolean isAvailable) implements Comparable<AppointmentSlot> {
    public AppointmentSlot {
        if (startTime < 0 || startTime > 23) {
            throw new IllegalArgumentException("start time should be between 0 and 23");
        }
        if (endTime < 0 || endTime > 23) {
            throw new IllegalArgumentException("end time should be between 0 and 23");
        }
        if (startTime >= endTime) {
            throw new IllegalArgumentException("start time should be less than end time");
        }
        if (!isAvailable && patientName.isEmpty()) {
            throw new IllegalArgumentException("patient name can't be empty");
        }
    }

    public AppointmentSlot(int startTime,
                           int endTime) {
        this(startTime, endTime, "", "", true);
    }

    public AppointmentSlot(int startTime,
                           int endTime,
                           String patientName,
                           String description) {
        this(startTime, endTime, patientName, description, true);
    }

    @Override
    public int compareTo(AppointmentSlot o) {
        if (this.startTime == o.startTime) {
            return Integer.compare(this.endTime, o.endTime);
        }
        return Integer.compare(this.startTime, o.endTime);
    }
}
