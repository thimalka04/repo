/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;


import dao.BookDAO;
import model.Book;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

//@WebServlet(name = "BookServlet", value = "/BookServlet")
@WebServlet("/BookServlet")

public class BookServlet extends HttpServlet {
    private BookDAO bookDAO;  // Corrected from colon to semicolon

    @Override
    public void init() throws ServletException {
        super.init();
        bookDAO = new BookDAO();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String title = request.getParameter("title");

        try {
            List<Book> books = bookDAO.searchBooks(title);
            request.setAttribute("bookList", books);
            request.getRequestDispatcher("/bookList.jsp").forward(request, response);
        } catch (Exception e) {
            request.setAttribute("error", "Error searching books: " + e.getMessage());
            request.getRequestDispatcher("/searchBook.jsp").forward(request, response);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            List<Book> availableBooks = bookDAO.getAvailableBooks();
            request.setAttribute("bookList", availableBooks);
            request.getRequestDispatcher("/bookList.jsp").forward(request, response);
        } catch (Exception e) {
            request.setAttribute("error", "Error retrieving books: " + e.getMessage());
            request.getRequestDispatcher("/searchBook.jsp").forward(request, response);
        }
    }
}