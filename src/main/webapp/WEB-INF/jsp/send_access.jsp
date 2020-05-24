<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ include file="/WEB-INF/jsp/utils/include.jsp"%>

<html>
<head>
    <meta charset="UTF-8">
    <title>À propos</title>
    <link rel="stylesheet" type="text/css" href="/style.css">
    <%@ include file="/WEB-INF/jsp/utils/head-bootstrap.jsp"%>
</head>
<body>
<div class="container">
    <h1 class="bigTitle topShift" align="center">Accés refusé.</h1>
    <p class="topShift" align="center">Votre ticket a expirez, souhaitez vous recevoir un nouveau lien ?</p>
    <a class="btn btn-outline-primary btn-sm" href="<c:url value = "/sondage/renouvelerAcces?id=${respondent_id}"/>">Oui</a>
</div>
</body>
</html>