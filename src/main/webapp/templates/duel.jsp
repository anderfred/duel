<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="java.util.Date" %>
<%@ page import="logic.ContextListener" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>duel</title>
    <style>
        <%@ include file="/css/style.css" %>
    </style>
</head>
<body>
<div class="page-wrapper">
    <div class="header"><h1>Duel Test</h1>
        <br><br></div>
    <br><br>
    <form method="POST" action="/duelWait">
        <p>Имя: ${name}</p>
        <p>Рейтинг: ${rating}</p>
        <p>Урон: ${damage}</p>
        <p>Здоровье: ${health}</p>
        <button class="buttonBlue" type="submit">Готовность</button>
    </form>
</div>
<% Date date = new Date();
    date = new Date(date.getTime() - ContextListener.date.get().getTime());%>

<h2 class="stat">Page <%=date.getTime()%>ms,req(${sqlCount})${sqlTime}ms</h2>
</body>
</html>