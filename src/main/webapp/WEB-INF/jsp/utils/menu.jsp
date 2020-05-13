<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<c:url var="index" value="/" />
<c:url var="connectPollster" value="/sondeur/connexion" />
<c:url var="newPollster" value="/sondeur/nouveau" />
<c:url var="createPollster" value="/sondeur/creer" />
<c:url var="profilPollster" value="/sondeur/profile" />
<c:url var="deconectPollster" value="/sondeur/deconnexion" />
<c:url var="newSurvey" value="/sondage/nouveau" />
<c:url var="createSurvey" value="/sondage/creer" />
<c:url var="finishCreateSurvey" value="/sondage/completer" />
<c:url var="editSurvey" value="/sondage/edit" />
<c:url var="listSurvey" value="/sondage/liste" />
<c:url var="delSurvey" value="/sondage/supprimer" />
<c:url var="about" value="/apropos" />

<nav class="navbar navbar-expand-lg navbar-light" style="background-color: #e3f2fd;">
    <a class="navbar-brand" href="<c:url value = "${index}"/>">POLL </a>
    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
    </button>

    <div class="collapse navbar-collapse" id="navbarSupportedContent">
        <ul class="navbar-nav mr-auto">
            <li <c:if test="${path != createSurvey}"> class="nav-item"</c:if>
                <c:if test="${path == createSurvey}"> class="nav-item active"</c:if>>
                <a class="nav-link" <c:if test="${user != null && user.connected}"> href="${newSurvey}" </c:if>
                        <c:if test="${user == null || !user.connected}"> href="${index}" </c:if>>
                    Créer un sondage 
                </a>
            </li>
            <li <c:if test="${path != newPollster}"> class="nav-item"</c:if>
                <c:if test="${path == newPollster}"> class="nav-item active"</c:if>>
                <a class="nav-link" <c:if test="${user != null && user.connected}"> href="${newPollster}" </c:if>
                        <c:if test="${user == null || !user.connected}"> href="${index}" </c:if>>
                    Créer un sondeur 
                </a>
            </li>
            <li <c:if test="${path != listSurvey}"> class="nav-item"</c:if>
                <c:if test="${path == listSurvey}"> class="nav-item active"</c:if>>
                <a class="nav-link" <c:if test="${user != null && user.connected}"> href="${listSurvey}" </c:if>
                        <c:if test="${user == null || !user.connected}"> href="${index}" </c:if>>
                    Mes sondages 
                </a>
            </li>
            <li <c:if test="${path != about}"> class="nav-item"</c:if>
                <c:if test="${path == about}"> class="nav-item active"</c:if>>
                <a class="nav-link" href="${about}">
                    À propos
                </a>
            </li>
        </ul>
        <c:if test="${user == null || !user.connected}">
            <form class="form-inline my-2 my-lg-0 " method="post" action="<c:url value = "${connectPollster}"/>">
                <input type="email" name="email" class="form-control mr-sm-2" placeholder="Email" aria-label="Search"/>
                <input type="password" name="password" class="form-control mr-sm-2" placeholder="Password" aria-label="Search"/>
                <button type="submit" class="btn btn-primary text-white">Se connecter</button>
            </form>
        </c:if>
        <c:if test="${user != null && user.connected}">
            <a class="btn btn-outline-primary btn-sm leftShift" href="<c:url value = "${profilPollster}"/>">Mon profil</a>
            <a class="btn btn-outline-danger btn-sm" href="<c:url value = "${deconectPollster}"/>">Se deconnecter</a>
        </c:if>
    </div>
</nav>

<%@ include file="/WEB-INF/jsp/utils/error.jsp"%>
