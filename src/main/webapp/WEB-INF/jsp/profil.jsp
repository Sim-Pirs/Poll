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
        <h1 class="title topShift" style="text-align: center" >Page personnel</h1>
        <form method="POST" action="#" >
            <fieldset disabled>
                <div class="form-group">
                    <label for="firstName">Prénom:</label>
                    <input id="firstName" class="form-control" placeholder="${user.pollster.firstName}" />
                </div>

                <div class="form-group">
                    <label for="lastName">Nom:</label>
                    <input id="lastName" class="form-control" placeholder="${user.pollster.lastName}" />
                </div>

                <div class="form-group">
                    <label for="email">Email:</label>
                    <input id="email" class="form-control" placeholder="${user.pollster.email}" />
                </div>

                <div class="form-group">
                    <label for="password">Mot de passe:</label>
                    <input id="password" class="form-control" placeholder="*************" />
                </div>
            </fieldset>
        </form>
    </div>
</body>
</html>