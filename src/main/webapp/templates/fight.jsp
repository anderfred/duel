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
    <title>fight</title>
</head>
<body>
<div class="page-wrapper">
    <div class="header">
        <h1>Duel Test</h1>
        <br><br>
    </div>

    <p class="hero"> ${hero_name}"</p>
    <p>Damage: ${hero_damage}</p>
    <p>Hp: ${hero_health}</p>
    <progress max=${maxHealthHero} value=${hero_health}></progress>
    <p>Rating: ${hero_rating}</p>
    <br><br>
    <form action="" method="POST">
        <button class="buttonRed" type="submit">Ударить!</button>
    </form>
    <br><br>
    <p class="enemy">${enemy_name}</p>
    <p>Damage: ${enemy_damage}</p>
    <p>Hp: ${enemy_health}</p>
    <progress  max=${maxHealthEnemy} value=${enemy_health}></progress>
    <p>Rating: ${enemy_rating}</p>
    <br><br>
    <c:forEach items="${listOfParams}" var="element">
        <p>${element}</p>
    </c:forEach>

</div>
<% Date date = new Date();
    date = new Date(date.getTime() - ContextListener.date.get().getTime());%>

<h2 class="stat">Page <%=date.getTime()%>ms,req(${sqlCount})${sqlTime}ms</h2>
</body>
</html>