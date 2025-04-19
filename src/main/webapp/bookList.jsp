<%-- 
    Document   : bookList
    Created on : Apr 11, 2025, 5:05:26 PM
    Author     : dilshan
--%>


<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Available Books</title>
    <style>
        body { font-family: Arial, sans-serif; margin: 20px; }
        .container { max-width: 800px; margin: 0 auto; }
        table { width: 100%; border-collapse: collapse; margin-top: 20px; }
        th, td { padding: 8px; text-align: left; border-bottom: 1px solid #ddd; }
        th { background-color: #f2f2f2; }
        a { text-decoration: none; color: #0066cc; }
    </style>
</head>
<body>
<div class="container">
    <h1>Available Books</h1>

    <a href="searchBook.jsp">Back to Search</a>

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
</div>
</body>
</html>