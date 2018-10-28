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
    <meta http-equiv="refresh" content="30; url=fight">
    <title>Fight</title>
</head>
<body>

<div class="page-wrapper">
    <div class="header"><h1>Duel Test</h1>
        <br><br></div>
    <br><br>
    <h1>Битва начнется через 30 секунд!</h1>
    <div id="countdown">
        <div id="countdown-number"></div>
        <svg>
            <circle r="18" cx="20" cy="20"></circle>
        </svg>
    </div>
    <div class="flex-container">
        <div class="box">
            <p class="hero">Герой</p>
            <p>${hero_name}</p>
            <p>Урон: ${hero_damage}</p>
            <p>Здоровье: ${hero_health}</p>
            <p>Рейтинг:${hero_rating}</p>
        </div>

        <div class="box">
            <p class="enemy">Противник</p>
            <p>${enemy_name}</p>
            <p>Урон: ${enemy_damage}</p>
            <p>Здоровье: ${enemy_health}</p>
            <p>Рейтинг: ${enemy_rating}</p>
        </div>
    </div>
<% Date date = new Date();
    date = new Date(date.getTime() - ContextListener.date.get().getTime());%>

<h2 class="stat">Page <%=date.getTime()%>ms,req(${sqlCount})${sqlTime}ms</h2>

</body>
</html>