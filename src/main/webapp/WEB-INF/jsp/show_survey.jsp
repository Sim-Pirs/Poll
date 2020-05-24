<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@ include file="/WEB-INF/jsp/utils/include.jsp"%>

<c:url var="saveChoices" value="/sondage/repondre" />

<html>
<head>
    <meta charset="UTF-8"/>
    <title>Index</title>
    <link rel="stylesheet" type="text/css" href="/style.css">
    <%@ include file="/WEB-INF/jsp/utils/head-bootstrap.jsp"%>
</head>
<body>
<div class="container">
    <h1 class="title topShift" style="text-align: center">Sondage</h1>
        <div class="row">
            <div class="col">
                <div class="form-group">
                    <label for="name">Nom:</label>
                    <input id="name" class="form-control" type="text" placeholder="${survey.name}" readonly>
                </div>
            </div>
            <div class="col">
                <div class="form-group">
                    <label for="endDate">Date de fin:</label>
                    <input id="endDate" class="form-control" type="date" value="${survey.endDateToGoodFormat}" readonly>
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
                    <form method="post" action="${saveChoices}">
                    <input id="repondent_id" name="repondent_id" type="hidden" value="${respondent.id}"/>
                        <c:forEach items="${survey.items}" var="item" varStatus="cpt">
                            <div class="card topShift">
                                <div class="card-body">
                                    <input id="items_id" name="items_id" type="hidden" value="${item.id}"/>
                                    <label for="descriptions[${cpt.count}]">Description:</label>
                                    <textarea id="descriptions[${cpt.count}]" class="form-control" placeholder="${item.description}" readonly></textarea>

                                    <label class="topShift" for="scores">Classement:</label>
                                    <select id="scores" name="scores" class="form-control">
                                        <option>...</option>
                                        <c:forEach begin="0" end="${survey.items.size() - 1}" var="cptChoice">
                                            <option <c:if test="${scores.get(cpt.index) == cptChoice + 1}">selected="selected"</c:if>>${cptChoice + 1}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                            </div>
                        </c:forEach>
                        <div class="form-group topShift">
                            <button type="submit" class="btn btn-outline-success my-2 my-sm-0">Envoyer</button>
                        </div>
                    </form>
                </c:if>
            </div>
        </div>
</div>
</body>
</html>



