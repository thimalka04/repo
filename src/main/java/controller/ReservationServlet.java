package controller;

import dao.BookDAO;
import dao.ReservationDAO;
import model.Reservation;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import java.io.IOException;


@WebServlet(name = "ReservationServlet", value = "/reserve")
public class ReservationServlet extends HttpServlet {
    private ReservationDAO reservationDAO;
    private BookDAO bookDAO;

    @Override
    public void init() throws ServletException {

        reservationDAO = new ReservationDAO();
        bookDAO = new BookDAO();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String studentName = request.getParameter("studentName");
        String studentId = request.getParameter("studentId");
        int bookId = Integer.parseInt(request.getParameter("bookId"));

        // Create reservation object
        Reservation reservation = new Reservation();
        reservation.setStudentName(studentName);
        reservation.setStudentId(studentId);
        reservation.setBookId(bookId);

        try {
            // Process reservation
            boolean reservationSuccess = reservationDAO.reserveBook(reservation);
            boolean statusUpdateSuccess = bookDAO.updateBookStatus(bookId, "Reserved");

            if (reservationSuccess && statusUpdateSuccess) {
                response.sendRedirect("success.jsp");
            } else {
                request.setAttribute("error", "Failed to complete reservation");
                request.setAttribute("bookId", bookId);
                request.getRequestDispatcher("/reserveForm.jsp").forward(request, response);
            }
        } catch (Exception e) {
            request.setAttribute("error", "Reservation error: " + e.getMessage());
            request.setAttribute("bookId", bookId);
            request.getRequestDispatcher("/reserveForm.jsp").forward(request, response);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");

        if ("cancel".equals(action)) {
            int reservationId = Integer.parseInt(request.getParameter("id"));
            try {
                Reservation reservation = reservationDAO.getReservationById(reservationId);
                if (reservation != null) {
                    boolean cancelSuccess = reservationDAO.cancelReservation(reservationId);
                    boolean statusSuccess = bookDAO.updateBookStatus(reservation.getBookId(), "Available");

                    if (cancelSuccess && statusSuccess) {
                        request.setAttribute("message", "Reservation cancelled successfully");
                    } else {
                        request.setAttribute("error", "Failed to cancel reservation");
                    }
                }
            } catch (Exception e) {
                request.setAttribute("error", "Error cancelling reservation: " + e.getMessage());
            }
            request.getRequestDispatcher("/reservationStatus.jsp").forward(request, response);
        }
    }
}
