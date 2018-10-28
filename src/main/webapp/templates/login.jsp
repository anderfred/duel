<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="java.util.Date" %>
<%@ page import="logic.ContextListener" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <style>
        <%@ include file="/css/style.css" %>
    </style>
    <title>Duel</title>
</head>
<body>
<div class="page-wrapper">
    <div class="header">
        <h1>Duel Test</h1>
        <p>Параметры нового игрока:</p>
        <p>Здоровье:100, Рейтинг:0, Урон:10</p>
        <br><br>
        <h1></h1>
    </div>
    <h1 class="error">${error}</h1>
    <br><br>

    <div>
        <form method="POST">
            <label>Имя пользователя:</label>
            <input type="text" name="login" placeholder="Введите имя" required>

            <label>Пароль:</label>
            <input type="password" name="password" placeholder="Введите пароль" required>

            <button class="button" type="submit">Войти</button>
        </form>
    </div>
    <% Date date = new Date();
        date = new Date(date.getTime() - ContextListener.date.get().getTime());%>


</div>
<h2 class="stat">Page <%=date.getTime()%>ms,req(${sqlCount})${sqlTime}ms</h2>
</body>
</html>