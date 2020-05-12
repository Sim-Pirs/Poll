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
    <form:form method="POST" modelAttribute="survey" action="${finishCreateSurvey}" cssClass="needs-validation">
        <form:input path="id" type="hidden" value="${survey.id}"/>
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


        <div class="card">
            <div class="card-header">
                Options
            </div>
            <div id="options">
                <div class="card-body">
                    <c:forEach begin="0" end="${nbOptions - 1}" var="cpt">
                        <div class="card topShift">
                            <div class="card-body">
                                <label for="items[${cpt}].description">Description:</label>
                                <form:textarea class="form-control" maxlength="500" path="items[${cpt}].description" rows="2" /><!--required="required"-->
                                <form:errors path="items[${cpt}].description" cssClass="alert alert-warning" element="div" />

                                <div class="form-row topShift">
                                    <div class="input-group col-md-6 input-group-sm">
                                        <div class="input-group-prepend">
                                            <span class="input-group-text">Min</span>
                                        </div>
                                        <form:input type="number" min="0" class="form-control" path="items[${cpt}].nbPersMin" /><!--required="required"-->
                                        <form:errors path="items[${cpt}].nbPersMin" cssClass="alert alert-warning" element="div" />
                                    </div>
                                    <div class="input-group col-md-6 input-group-sm">
                                        <div class="input-group-prepend">
                                            <span class="input-group-text">Max</span>
                                        </div>
                                        <form:input type="number" min="1" class="form-control" path="items[${cpt}].nbPersMax" /><!--required="required"-->
                                        <form:errors path="items[${cpt}].nbPersMax" cssClass="alert alert-warning" element="div" />
                                    </div>
                                </div>
                                <div class="input-group input-group-sm topShift">
                                    <div class="input-group-prepend">
                                        <span class="input-group-text">Tags</span>
                                    </div>
                                    <form:input type="text" class="form-control" path="items[${cpt}].tags" multiple="multiple"/><!--required="required"-->
                                    <form:errors path="items[${cpt}].tags" cssClass="alert alert-warning" element="div" />
                                </div>
                            </div>
                        </div>
                    </c:forEach>
                </div>
            </div>
        </div>
        <div class="form-group topShift">
            <button type="submit" class="btn btn-outline-success my-2 my-sm-0">Submit</button>
        </div>
    </form:form>

</div>
</body>
</html>

