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
    <title>Summary</title>
</head>
<body>
<div class="page-wrapper">
    <div class="header">
        <h1>Duel Test</h1>
        <br><br>
    </div>

    <h1>${message}</h1>
    <p>Характеристики после дуэли:</p>
    <p>${hero_name}</p>
    <p>Урон:${hero_damage}</p>
    <p>Здоровье: ${hero_health}</p>
    <p>Рейтинг: ${hero_rating}</p>
    <form action="/exit" method="POST">
        <button class="buttonRed" type="submit">Выйти</button>
    </form>
</div>
<% Date date = new Date();
    date = new Date(date.getTime() - ContextListener.date.get().getTime());%>

<h2 class="stat">Page <%=date.getTime()%>ms,req(${sqlCount})${sqlTime}ms</h2>
</body>
</html>