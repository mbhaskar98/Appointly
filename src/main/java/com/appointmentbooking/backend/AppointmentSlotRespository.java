package com.appointmentbooking.backend;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

class AppointmentSlotRespository {
    private static final String TABLE_NAME = "appointments";
    private static final String KEY_ID = "id";
    private static final String KEY_START_TIME = "start_time";
    private static final String KEY_END_TIME = "end_time";
    private static final String KEY_PATIENT_NAME = "patient_name";
    private static final String KEY_DESCRIPTION = "description";
    private static final String KEY_IS_AVAILABLE = "is_available";

    //    private static final String CREATE_TABLE_SQL = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (id INTEGER PRIMARY KEY, name TEXT, age INTEGER)";
    private static final String CREATE_TABLE_SQL = String.format(
            """
                    CREATE TABLE IF NOT EXISTS %s (
                    %s STRING PRIMARY KEY,
                    %s INTEGER,
                    %s INTEGER,
                    %s STRING,
                    %s STRING,
                    %s INTEGER NOT NULL DEFAULT 1 CHECK (%s IN (0, 1))
                    )""",
            TABLE_NAME, KEY_ID, KEY_START_TIME, KEY_END_TIME, KEY_PATIENT_NAME, KEY_DESCRIPTION, KEY_IS_AVAILABLE, KEY_IS_AVAILABLE
    );

    //            "INSERT INTO " + TABLE_NAME + " (name, age) VALUES (?, ?)";
    private static final String INSERT_APPOINTMENT_SQL = String.format(
            """
                    INSERT INTO %s
                    (%s, %s, %s, %s, %s, %s)
                    VALUES (?, ?, ?, ?, ?, ?)
                    """,
            TABLE_NAME, KEY_ID, KEY_START_TIME, KEY_END_TIME, KEY_PATIENT_NAME, KEY_DESCRIPTION, KEY_IS_AVAILABLE
    );

    private static final String SELECT_ALL_APPOINTMENTS_SQL =
            "SELECT * FROM " + TABLE_NAME;
//    private static final String UPDATE_PERSON_SQL =
//            "UPDATE " + TABLE_NAME + " SET name = ?, age = ? WHERE id = ?";
//    private static final String DELETE_PERSON_SQL =
//            "DELETE FROM " + TABLE_NAME + " WHERE id = ?";

    private static final String DROP_TABLE_SQL = "DROP TABLE " + TABLE_NAME;

    AppointmentSlotRespository() {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(CREATE_TABLE_SQL)) {
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("ERR: DB init " + e.toString());
        }
    }

    boolean insertAppointmentSlot(AppointmentSlot appointmentSlot) {
        boolean result = false;
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(INSERT_APPOINTMENT_SQL)) {
            stmt.setString(1, String.valueOf(getID(appointmentSlot)));
            stmt.setInt(2, appointmentSlot.startTime());
            stmt.setInt(3, appointmentSlot.endTime());
            stmt.setString(4, appointmentSlot.patientName());
            stmt.setString(5, appointmentSlot.description());
            stmt.setBoolean(6, appointmentSlot.isAvailable());
            stmt.executeUpdate();
            result = true;
        } catch (SQLException e) {
            System.out.println("ERR: Insert an appointment" + e.toString());

        }
        return result;
    }

    List<AppointmentSlot> getAllAppointmentSlots() {
        List<AppointmentSlot> appointmentSlots = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(SELECT_ALL_APPOINTMENTS_SQL)) {
            while (rs.next()) {
                int startTime = rs.getInt(KEY_START_TIME);
                int endTime = rs.getInt(KEY_END_TIME);
                String patientName = rs.getString(KEY_PATIENT_NAME);
                String description = rs.getString(KEY_DESCRIPTION);
                boolean isAvailable = rs.getBoolean(KEY_IS_AVAILABLE);
                appointmentSlots.add(new AppointmentSlot(
                        startTime,
                        endTime,
                        patientName,
                        description,
                        isAvailable
                ));
            }
        } catch (SQLException e) {
            System.out.println("ERR: Get All appointments" + e.toString());
        }
        return appointmentSlots;
    }

//    public void updatePerson(int id, String name, int age) {
//        try (Connection conn = DatabaseConnection.getConnection();
//             PreparedStatement pstmt = conn.prepareStatement(UPDATE_PERSON_SQL)) {
//            pstmt.setString(1, name);
//            pstmt.setInt(2, age);
//            pstmt.setInt(3, id);
//            pstmt.executeUpdate();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }
//
//    public void deletePerson(int id) {
//        try (Connection conn = DatabaseConnection.getConnection();
//             PreparedStatement pstmt = conn.prepareStatement(DELETE_PERSON_SQL)) {
//            pstmt.setInt(1, id);
//            pstmt.executeUpdate();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }

    void deleteDB() {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(DROP_TABLE_SQL)) {
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("ERR: Insert an appointment" + e.toString());
        }
    }

    String getID(AppointmentSlot appointmentSlot) {
        return String.valueOf(appointmentSlot.startTime()) + "-" + String.valueOf(appointmentSlot.endTime());
    }
}
