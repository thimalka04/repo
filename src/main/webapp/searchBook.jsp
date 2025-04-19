<%-- 
    Document   : searchBook.
    Created on : Apr 11, 2025, 5:04:51 PM
    Author     : dilshan
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Library Book Search</title>
    <style>
        body { font-family: Arial, sans-serif; margin: 20px; }
        .container { max-width: 800px; margin: 0 auto; }
        .error { color: red; }
        .success { color: green; }
        table { width: 100%; border-collapse: collapse; margin-top: 20px; }
        th, td { padding: 8px; text-align: left; border-bottom: 1px solid #ddd; }
        th { background-color: #f2f2f2; }
    </style>
</head>
<body>
<div class="container">
    <h1>Library Book Search</h1>

    <form action="BookServlet" method="post">
        <input type="text" name="title" placeholder="Enter book title" required>
        <input type="submit" value="Search">
    </form>

    <c:if test="${not empty error}">
        <p class="error">${error}</p>
    </c:if>

    <c:if test="${not empty message}">
        <p class="success">${message}</p>
    </c:if>

    <c:if test="${not empty bookList}">
        <h2>Search Results</h2>
        <table>
            <tr>
                <th>Title</th>
                <th>Author</th>
                <th>Status</th>
                <th>Action</th>
            </tr>
            <c:forEach items="${bookList}" var="book">
                <tr>
                    <td>${book.title}</td>
                    <td>${book.author}</td>
                    <td>${book.status}</td>
                    <td>
                        <c:if test="${book.status eq 'Available'}">
                            <a href="reserveForm.jsp?bookId=${book.id}">Reserve</a>
                        </c:if>
                    </td>
                </tr>
            </c:forEach>
        </table>
    </c:if>
</div>
</body>
</html>