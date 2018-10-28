<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="java.util.Date" %>
<%@ page import="logic.ContextListener" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="refresh" content="1">
    <style>
        <%@ include file="/css/style.css" %>
    </style>
    <title>Title</title>
</head>
<body>
<div class="page-wrapper">
    <div class="header"><h1>Duel Test</h1>
        <br><br></div>
    <br><br>
    <h1>Ожидание противника...</h1>
</div>
<% Date date = new Date();
    date = new Date(date.getTime() - ContextListener.date.get().getTime());%>

<h2 class="stat">Page <%=date.getTime()%>ms,req(${sqlCount})${sqlTime}ms</h2>
</body>
</html>