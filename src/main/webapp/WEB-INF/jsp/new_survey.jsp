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
            <h1 class="title topShift" align="center">Création d'un sondage</h1>
            <form:form method="POST" modelAttribute="survey" action="${createSurvey}" cssClass="needs-validation">
                <div class="row">
                    <div class="col">
                        <div class="form-group">
                            <label for="name">Nom:</label>
                            <form:input class="form-control" path="name" required="required"/>
                            <form:errors path="name" cssClass="alert alert-warning"
                                         element="div" />
                        </div>
                    </div>
                    <div class="col">
                        <div class="form-group">
                            <label for="endDate">Date de fin:</label>
                            <form:input type="date" class="form-control"  path="endDate" required="required"/>
                            <form:errors path="endDate" cssClass="alert alert-warning"
                                         element="div" />
                        </div>
                    </div>
                </div>
                <div class="form-group">
                    <label for="description">Description:</label>
                    <form:textarea class="form-control" maxlength="500" path="description" rows="2" required="required"/>
                    <form:errors path="description" cssClass="alert alert-warning"
                                 element="div" />
                </div>
                <div class="form-group">
                    <label for="nbOptions">Nombre de choix:</label>
                    <input type="number" class="form-control" id="nbOptions" min="2"  value="2" name="nbOptions" required="required"/>
                </div>
                <div class="form-group topShift">
                    <button type="submit" class="btn btn-outline-success my-2 my-sm-0">Créer</button>
                </div>
            </form:form>
        </div>
    </body>
</html>



