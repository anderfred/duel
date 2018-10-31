<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="java.util.Date" %>
<%@ page import="logic.ContextListener" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<head>
    <meta charset="UTF-8">
    <style>
        <%@ include file="/css/style.css" %>
    </style>
    <title>Welcome</title>
</head>
<body>
<div class="page-wrapper">
    <div class="header">
        <h1>Duel Test</h1>
        <br><br><br>
    </div>
    <br><br>
    <h2>Вы вошли как ${name}
    </h2>

    <p>Имя: ${name}</p>
    <p>Рейтинг: ${rating}</p>
    <p>Урон: ${damage}</p>
    <p>Здоровье: ${health}</p>
    <div>
        <br><br>
        <div>
            <form method="POST" action="/exit">
                <button class="buttonRed" type="submit">Выйти</button>
            </form>
            <form method="POST" action="/duel">
                <button class="buttonBlue" type="submit">Дуэль</button>
            </form>
        </div>
    </div>
</div>
<% Date date = new Date();
    date = new Date(date.getTime() - ContextListener.date.get().getTime());%>

<h2 class="stat">Page <%=date.getTime()%>ms,req(${sqlCount})${sqlTime}ms</h2>
</body>
</html>