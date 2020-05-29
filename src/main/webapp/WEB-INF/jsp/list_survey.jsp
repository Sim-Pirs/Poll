<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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
            <h1 style="text-align: center" class="bigTitle topShift">Mes sondages</h1>
            <c:choose>
                <c:when test="${mySurveys.size() < 1}">
                    <div style="text-align: center" class="topShiftMd">
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
                                <td><c:choose>
                                    <c:when test="${survey.description.length() > 50}">
                                        <c:out value="${survey.getDescriptionForSize(47)}..." />
                                    </c:when>
                                    <c:otherwise>
                                        <c:out value="${survey.description}" />
                                    </c:otherwise>
                                </c:choose></td>
                                <td><c:out value="${survey.getStringEndDate(\"dd/MM/yyyy\")}" /></td>
                                <td><c:out value="${survey.items.size()}" /></td>
                                <td class="row">
                                    <ul class="nav">
                                        <li class="nav-item">
                                            <form action="${editSurveyRespondents}" method="get">
                                                <input type="hidden" name="id" value="${survey.id}">
                                                <button type="submit" class="btn btn-outline-dark btn-sm">Diffusion</button>
                                            </form>
                                        </li>
                                        <li class="nav-item">
                                            <form action="${editSurvey}" method="get" class="">
                                                <input type="hidden" name="id" value="${survey.id}">
                                                <button type="submit" class="btn btn-outline-dark btn-sm">Modifier</button>
                                            </form>
                                        </li>
                                        <li class="nav-item">
                                            <form action="${resultSurvey}" method="get" class="">
                                                <input type="hidden" name="id" value="${survey.id}">
                                                <button type="submit" class="btn btn-outline-dark btn-sm">Résultat</button>
                                            </form>
                                        </li>
                                        <li class="nav-item">
                                            <form action="${delSurvey}" method="get" class="">
                                                <input type="hidden" name="id" value="${survey.id}">
                                                <button type="submit" class="btn btn-outline-danger btn-sm">Supprimer</button>
                                            </form>
                                        </li>
                                    </ul>
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