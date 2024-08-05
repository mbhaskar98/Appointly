package com.appointmentbooking.backend;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

class AppointmentSlotRepository {
    private static final String TABLE_NAME = "appointments";
    private static final String KEY_ID = "id";
    private static final String KEY_START_TIME = "start_time";
    private static final String KEY_END_TIME = "end_time";
    private static final String KEY_PATIENT_NAME = "patient_name";
    private static final String KEY_DESCRIPTION = "description";
    private static final String KEY_IS_AVAILABLE = "is_available";

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

    private static final String INSERT_APPOINTMENT_SQL = String.format(
            """
                    INSERT INTO %s
                    (%s, %s, %s, %s, %s, %s)
                    VALUES (?, ?, ?, ?, ?, ?)
                    """,
            TABLE_NAME, KEY_ID, KEY_START_TIME, KEY_END_TIME, KEY_PATIENT_NAME, KEY_DESCRIPTION, KEY_IS_AVAILABLE
    );

    private static final String UPDATE_APPOINTMENT_SQL = String.format(
            """
                    UPDATE %s SET
                    %s = ?,
                    %s = ?,
                    %s = ?,
                    %s = ?,
                    %s = ?
                    WHERE %s = ?
                    """,
            TABLE_NAME, KEY_START_TIME, KEY_END_TIME, KEY_PATIENT_NAME, KEY_DESCRIPTION, KEY_IS_AVAILABLE, KEY_ID
    );

    private static final String SELECT_ONE_APPOINTMENT_SQL = "SELECT * FROM " + TABLE_NAME + " WHERE id = ?";

    private static final String SELECT_ALL_APPOINTMENTS_SQL =
            "SELECT * FROM " + TABLE_NAME;

    private static final String DELETE_APPOINTMENT_SQL = "DELETE FROM " + TABLE_NAME + " WHERE id = ?";

    private static final String DROP_TABLE_SQL = "DROP TABLE " + TABLE_NAME;

    AppointmentSlotRepository() {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(CREATE_TABLE_SQL)) {
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("ERR: DB init " + e.toString());
        }
    }

    AppointmentSlot getAppointmentSlotById(String id) {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SELECT_ONE_APPOINTMENT_SQL)) {
            stmt.setString(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return getAppointFromResultSet(rs);
            }
        } catch (SQLException e) {
            System.out.println("ERR: Get appointment by id:" + e.toString());
        }
        return null;
    }

    List<AppointmentSlot> getAllAppointmentSlots() {
        List<AppointmentSlot> appointmentSlots = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(SELECT_ALL_APPOINTMENTS_SQL)) {
            while (rs.next()) {
                appointmentSlots.add(getAppointFromResultSet(rs));
            }
        } catch (SQLException e) {
            System.out.println("ERR: Get All appointments:" + e.toString());
        }
        return appointmentSlots;
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
            System.out.println("ERR: Insert an appointment:" + e.toString());

        }
        return result;
    }

    boolean updateAppointmentSlot(AppointmentSlot appointmentSlot) {
        boolean result = false;
        if (getAppointmentSlotById(getID(appointmentSlot)) == null) {
            return result;
        }
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(UPDATE_APPOINTMENT_SQL)) {
            stmt.setInt(1, appointmentSlot.startTime());
            stmt.setInt(2, appointmentSlot.endTime());
            stmt.setString(3, appointmentSlot.patientName());
            stmt.setString(4, appointmentSlot.description());
            stmt.setBoolean(5, appointmentSlot.isAvailable());
            stmt.setString(6, getID(appointmentSlot));
            stmt.executeUpdate();
            result = true;
        } catch (SQLException e) {
            System.out.println("ERR: Insert an appointment" + e.toString());

        }
        return result;
    }

    void deleteAppointment(AppointmentSlot appointmentSlot) {
        String id = getID(appointmentSlot);
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(DELETE_APPOINTMENT_SQL)) {
            stmt.setString(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("ERR: delete appointment:" + e.toString());
        }

    }

    void deleteDB() {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(DROP_TABLE_SQL)) {
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("ERR: delete db:" + e.toString());
        }
    }

    String getID(AppointmentSlot appointmentSlot) {
        return String.valueOf(appointmentSlot.startTime()) + "-" + String.valueOf(appointmentSlot.endTime());
    }

    private AppointmentSlot getAppointFromResultSet(ResultSet rs) throws SQLException {
        AppointmentSlot appointmentSlot = new AppointmentSlot(
                rs.getInt(KEY_START_TIME),
                rs.getInt(KEY_END_TIME),
                rs.getString(KEY_PATIENT_NAME),
                rs.getString(KEY_DESCRIPTION),
                rs.getBoolean(KEY_IS_AVAILABLE)
        );

        return appointmentSlot;
    }
}
