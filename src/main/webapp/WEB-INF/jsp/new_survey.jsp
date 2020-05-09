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
    <h1 class="title" align="center">Création d'un sondage</h1>
    <form:form method="POST" modelAttribute="survey" action="${createSurvey}" >
        <div class="row">
            <div class="col">
                <div class="form-group">
                    <label for="name">Nom:</label>
                    <form:input class="form-control" path="name" />
                    <form:errors path="name" cssClass="alert alert-warning"
                                 element="div" />
                </div>
            </div>
            <div class="col">
                <div class="form-group">
                    <label for="endDate">Date de fin:</label>
                    <form:input type="date" class="form-control" path="endDate"/>
                    <form:errors path="endDate" cssClass="alert alert-warning"
                                 element="div" />
                </div>
            </div>
        </div>
        <div class="form-group">
            <label for="description">Description:</label>
            <form:textarea class="form-control" path="description" rows="4" />
            <form:errors path="description" cssClass="alert alert-warning"
                         element="div" />
        </div>

        <div class="form-group">
            <button type="submit" class="btn btn-outline-success my-2 my-sm-0">Submit</button>
        </div>
    </form:form>
</div>
</div>
</body>
</html>

