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
        <c:choose>
            <c:when test="${survey.terminated}">
                <h1 class="title topShift" style="text-align: center">Résultat du sondage</h1>
                <c:choose>
                    <c:when test="${survey.resultObtained}">
                        <div class="row topShift">
                            <div class="col">
                                <div class="form-group">
                                    <label for="name">Nom:</label>
                                    <input id="name" class="form-control" type="text" placeholder="${survey.name}" readonly>
                                </div>
                            </div>
                            <div class="col">
                                <div class="form-group">
                                    <label for="endDate">Date de fin:</label>
                                    <input id="endDate" class="form-control" type="date" value="${survey.getStringEndDate("yyyy-MM-dd")}" readonly>
                                </div>
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="description">Description:</label>
                            <textarea id="description" class="form-control" placeholder="${survey.description}" readonly></textarea>
                        </div>
                        <div id="options">
                            <div class="card-body">
                                <c:if test="${survey.items.size() > 0}">
                                    <c:forEach items="${survey.items}" var="item" varStatus="cptItem">
                                        <div class="card topShift">
                                            <div class="card-body">
                                                <label for="descriptions[${cptItem.count}]">Description:</label>
                                                <textarea id="descriptions[${cptItem.count}]" class="form-control" placeholder="${item.description}" readonly></textarea>
                                            </div>
                                            <c:forEach items="${survey.respondents}" var="respondent" varStatus="cptResp">
                                                <c:if test="${respondent.finalItem.id == item.id}">
                                                    <p>${respondent.email}</p>
                                                </c:if>
                                            </c:forEach>
                                        </div>
                                    </c:forEach>
                                </c:if>
                            </div>
                        </div>
                        <a href="${notifyAllRespondentsForFinalItem}?id=${survey.id}" class="btn btn-outline-warning" role="button" aria-pressed="true">Notifier tout le monde</a>
                    </c:when>
                    <c:otherwise>
                        <p style="text-align: center">Le tirage des resultat n'a pas encore été fait.</p>
                        <form action="${resultSurvey}" method="post" class="">
                            <input type="hidden" name="id" value="${survey.id}">
                            <button type="submit" class="btn btn-outline-warning">Obtenir les résultats</button>
                        </form>
                    </c:otherwise>
                </c:choose>
            </c:when>
            <c:otherwise>
                <h1 class="title topShift" style="text-align: center">Le sondage n'est pas finis</h1>
                <p style="text-align: center">Il le sera le ${survey.getStringEndDate("dd/MM/yyyy")} à ${survey.getStringEndDate("hh:mm:ss")}. Vous pouvez cependant modifier sa date de fin.</p>
            </c:otherwise>
        </c:choose>
    </div>
</body>
</html>



