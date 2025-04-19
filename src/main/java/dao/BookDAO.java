/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;




import model.Book;
import utill.DatabaseConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookDAO {
    // SQL queries as constants
    private static final String SEARCH_BOOKS = "SELECT * FROM books WHERE title LIKE ?";
    private static final String GET_BOOK_BY_ID = "SELECT * FROM books WHERE id = ?";
    private static final String UPDATE_STATUS = "UPDATE books SET status = ? WHERE id = ?";
    private static final String GET_AVAILABLE_BOOKS = "SELECT * FROM books WHERE status = 'Available'";

    public List<Book> searchBooks(String title) {
        List<Book> books = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SEARCH_BOOKS)) {

            stmt.setString(1, "%" + title + "%");
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Book book = mapResultSetToBook(rs);
                books.add(book);
            }
        } catch (SQLException e) {
            System.err.println("Error searching books: " + e.getMessage());
        }
        return books;
    }

    public Book getBookById(int id) {
        Book book = null;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(GET_BOOK_BY_ID)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                book = mapResultSetToBook(rs);
            }
        } catch (SQLException e) {
            System.err.println("Error getting book by ID: " + e.getMessage());
        }
        return book;
    }

    public boolean updateBookStatus(int bookId, String status) {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(UPDATE_STATUS)) {

            stmt.setString(1, status);
            stmt.setInt(2, bookId);

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.err.println("Error updating book status: " + e.getMessage());
            return false;
        }
    }

    public List<Book> getAvailableBooks() {
        List<Book> books = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(GET_AVAILABLE_BOOKS);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Book book = mapResultSetToBook(rs);
                books.add(book);
            }
        } catch (SQLException e) {
            System.err.println("Error getting available books: " + e.getMessage());
        }
        return books;
    }

    // Helper method to map ResultSet to Book object
    private Book mapResultSetToBook(ResultSet rs) throws SQLException {
        Book book = new Book();
        book.setId(rs.getInt("id"));
        book.setTitle(rs.getString("title"));
        book.setAuthor(rs.getString("author"));
        book.setStatus(rs.getString("status"));
        return book;
    }
}
