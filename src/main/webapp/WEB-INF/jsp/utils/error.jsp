<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<c:if test="${user.asError()}">
    <c:forEach items="${user.errorMessages}" var="errorMessage">
        <div class="alert alert-danger">
            <c:out value="${errorMessage}"/>
            <c:out value="${user.cleanErrors()}"/>
        </div>
    </c:forEach>
</c:if>