<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@ include file="/WEB-INF/jsp/utils/include.jsp"%>

<html>
    <head>
        <meta charset="UTF-8"/>
        <title>Index</title>
        <link rel="stylesheet" type="text/css" href="/style.css">
        <%@ include file="/WEB-INF/jsp/utils/head-bootstrap.jsp"%>
    </head>
    <body>
        <%@ include file="/WEB-INF/jsp/utils/menu.jsp"%>
        <c:if test="${user.asError()}">
            <c:forEach items="${user.errorMessages}" var="errorMessage">
                <div class="alert alert-danger">
                    <c:out value="${errorMessage}"/>
                    <c:out value="${user.cleanErrors()}"/>
                </div>
            </c:forEach>
        </c:if>
        <div class="container middle">
            <h1 align="center" class="bigTitle">POLL</h1>
            <p align="center">- Make sondages great again -</p>
        </div>
    </body>
</html>