/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;


import model.Reservation;
import utill.DatabaseConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReservationDAO {
    // SQL queries as constants
    private static final String INSERT_RESERVATION =
            "INSERT INTO reservations (student_name, student_id, book_id) VALUES (?, ?, ?)";
    private static final String GET_RESERVATION_BY_ID =
            "SELECT * FROM reservations WHERE id = ?";
    private static final String GET_RESERVATIONS_BY_STUDENT_ID =
            "SELECT * FROM reservations WHERE student_id = ?";
    private static final String GET_ALL_RESERVATIONS =
            "SELECT * FROM reservations";
    private static final String DELETE_RESERVATION =
            "DELETE FROM reservations WHERE id = ?";

    public boolean reserveBook(Reservation reservation) {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(INSERT_RESERVATION,
                     Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, reservation.getStudentName());
            stmt.setString(2, reservation.getStudentId());
            stmt.setInt(3, reservation.getBookId());

            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        reservation.setId(generatedKeys.getInt(1));
                    }
                }
                return true;
            }
        } catch (SQLException e) {
            System.err.println("Error reserving book: " + e.getMessage());
        }
        return false;
    }

    public Reservation getReservationById(int id) {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(GET_RESERVATION_BY_ID)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return mapResultSetToReservation(rs);
            }
        } catch (SQLException e) {
            System.err.println("Error getting reservation: " + e.getMessage());
        }
        return null;
    }

    public List<Reservation> getReservationsByStudentId(String studentId) {
        List<Reservation> reservations = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(GET_RESERVATIONS_BY_STUDENT_ID)) {

            stmt.setString(1, studentId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                reservations.add(mapResultSetToReservation(rs));
            }
        } catch (SQLException e) {
            System.err.println("Error getting student reservations: " + e.getMessage());
        }
        return reservations;
    }

    public List<Reservation> getAllReservations() {
        List<Reservation> reservations = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(GET_ALL_RESERVATIONS)) {

            while (rs.next()) {
                reservations.add(mapResultSetToReservation(rs));
            }
        } catch (SQLException e) {
            System.err.println("Error getting all reservations: " + e.getMessage());
        }
        return reservations;
    }

    public boolean cancelReservation(int reservationId) {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(DELETE_RESERVATION)) {

            stmt.setInt(1, reservationId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error canceling reservation: " + e.getMessage());
            return false;
        }
    }

    // Helper method to map ResultSet to Reservation object
    private Reservation mapResultSetToReservation(ResultSet rs) throws SQLException {
        Reservation reservation = new Reservation();
        reservation.setId(rs.getInt("id"));
        reservation.setStudentName(rs.getString("student_name"));
        reservation.setStudentId(rs.getString("student_id"));
        reservation.setBookId(rs.getInt("book_id"));
        reservation.setReservationDate(rs.getTimestamp("reservation_date"));
        return reservation;
    }
}