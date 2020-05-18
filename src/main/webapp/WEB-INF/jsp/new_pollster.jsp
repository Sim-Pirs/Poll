<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
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
        <c:if test="${success == true}">
            <div class="alert alert-success alert-dismissible fade show" role="alert">
                Nouveau sondeur créé!
                <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
        </c:if>
        <div class="container">
            <h1 class="title topShift" align="center">Création d'un sondeur</h1>


            <form:form method="POST" modelAttribute="pollster" action="${createPollster}" >
                <div class="row">
                    <div class="col">
                        <div class="form-group">
                            <label for="firstName">Prénom:</label>
                            <form:input type="text" class="form-control" path="firstName" />
                            <form:errors path="firstName" cssClass="alert alert-warning"
                                         element="div" />
                        </div>
                    </div>
                    <div class="col">
                        <div class="form-group">
                            <label for="lastName">Nom:</label>
                            <form:input type="text" class="form-control" path="lastName" />
                            <form:errors path="lastName" cssClass="alert alert-warning"
                                         element="div" />
                        </div>
                    </div>
                </div>

                <div class="row">
                    <div class="col col-md-8">
                        <div class="form-group">
                            <label for="email">Email:</label>
                            <form:input type="email" class="form-control" path="email" />
                            <form:errors path="email" cssClass="alert alert-warning"
                                         element="div" />
                        </div>
                    </div>
                    <div class="col col-md-4">
                        <div class="form-group">
                            <label for="password">Mot de passe:</label>
                            <form:input type="password" class="form-control" path="password"/>
                            <form:errors path="password" cssClass="alert alert-warning"
                                         element="div" />
                        </div>
                    </div>
                </div>
                <div class="form-group">
                    <button type="submit" class="btn btn-outline-success my-2 my-sm-0">Créer</button>
                </div>
            </form:form>
        </div>
        </div>
    </body>
</html>