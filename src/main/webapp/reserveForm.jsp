<%-- 
    Document   : reserveForm
    Created on : Apr 11, 2025, 5:05:55 PM
    Author     : dilshan
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Reserve Book</title>
    <style>
        body { font-family: Arial, sans-serif; margin: 20px; }
        .container { max-width: 500px; margin: 0 auto; }
        .form-group { margin-bottom: 15px; }
        label { display: inline-block; width: 120px; }
        input[type="text"] { padding: 8px; width: 200px; }
        .error { color: red; }
        .button { padding: 8px 15px; background-color: #0066cc; color: white; border: none; cursor: pointer; }
    </style>
</head>
<body>
<div class="container">
    <h1>Reserve Book</h1>

    <c:if test="${not empty error}">
        <p class="error">${error}</p>
    </c:if>

    <form action="reserve" method="post">
        <input type="hidden" name="bookId" value="${param.bookId}">

        <div class="form-group">
            <label for="studentName">Student Name:</label>
            <input type="text" id="studentName" name="studentName" required>
        </div>

        <div class="form-group">
            <label for="studentId">Student ID:</label>
            <input type="text" id="studentId" name="studentId" required>
        </div>

        <div class="form-group">
            <input type="submit" value="Reserve" class="button">
            <a href="searchBook.jsp" style="margin-left: 10px;">Cancel</a>
        </div>
    </form>
</div>
</body>
</html>