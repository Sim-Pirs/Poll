<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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
    <h1 class="bigTitle topShift" style="text-align: center" >Accés refusé.</h1>
    <p class="topShift" style="text-align: center" >Votre ticket a expirez, <a href="<c:url value="/sondage/renouvelerAcces?id=${respondent_id}"/>">cliquez ici</a> pour recevoir un nouveau lien.</p>
</div>
</body>
</html>