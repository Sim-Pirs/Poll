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
        <div class="container">
            <h1 align="center" class="bigTitle">Liste des sondages</h1>
            <table class="table table-striped">
                <thead class="thead-dark">
                <tr>
                    <th scope="col">Id</th>
                    <th scope="col">Nom</th>
                    <th scope="col">Description</th>
                    <th scope="col">Date de fin</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${surveys}" var="survey">
                    <tr>
                        <td><c:out value="${survey.id}" /></td>
                        <td><c:out value="${survey.name}" /></td>
                        <td><c:out value="${survey.description}" /></td>
                        <td><c:out value="${survey.endDate}" /></td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </body>
</html>