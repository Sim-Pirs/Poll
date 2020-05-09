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
            <h1 class="title" align="center">Création d'un sondeur</h1>


            <form:form method="POST" modelAttribute="pollster" action="${createPollster}" >
                <div class="form-group">
                    <label for="firstName">Prénom:</label>
                    <form:input class="form-control" path="firstName" />
                    <form:errors path="firstName" cssClass="alert alert-warning"
                                 element="div" />
                </div>

                <div class="form-group">
                    <label for="lastName">Nom:</label>
                    <form:input class="form-control" path="lastName" />
                    <form:errors path="lastName" cssClass="alert alert-warning"
                                 element="div" />
                </div>

                <div class="form-group">
                    <label for="email">Email:</label>
                    <form:input class="form-control" path="email" />
                    <form:errors path="email" cssClass="alert alert-warning"
                                 element="div" />
                </div>

                <div class="form-group">
                    <label for="password">Mot de passe:</label>
                    <form:input class="form-control" path="password"/>
                    <form:errors path="password" cssClass="alert alert-warning"
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