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
            <h1 align="center" class="bigTitle topShift">Mes sondages</h1>
            <c:choose>
                <c:when test="${mySurveys.size() < 1}">
                    <div style="align-self: center;" class="topShiftMd">
                        <p>Vous ne possédez pas encore de sondages.</p>
                    </div>
                </c:when>
                <c:otherwise>
                    <table class="table">
                        <thead class="thead-light">
                        <tr>
                            <th scope="col">Nom</th>
                            <th scope="col">Description</th>
                            <th scope="col">Date de fin</th>
                            <th scope="col">Nombre d'options</th>
                            <th></th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach items="${mySurveys}" var="survey">
                            <tr>
                                <td><c:out value="${survey.name}" /></td>
                                <td><c:out value="${survey.description}" /></td>
                                <td><c:out value="${survey.stringEndDate}" /></td>
                                <td><c:out value="${survey.items.size()}" /></td>
                                <td class="row">
                                    <form action="${editSurvey}" method="post">
                                        <input type="hidden" name="id_survey" value="${survey.id}">
                                        <button type="submit" class="btn btn-light btn-sm">Modifier</button>
                                    </form>
                                    <form action="${delSurvey}" method="post">
                                        <input type="hidden" name="id_survey" value="${survey.id}">
                                        <button type="submit" class="btn btn-danger btn-sm">Supprimer</button>
                                    </form>
                                </td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </c:otherwise>
            </c:choose>
        </div>
    </body>
</html>