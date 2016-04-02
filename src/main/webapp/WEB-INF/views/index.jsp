<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<spring:message var="projectName" code="projectName"/>
<spring:message var="welcomeText" code="index.welcomeText" arguments="${projectName}"/>
<spring:message var="emailPlaceholder" code="index.emailPlaceholder"/>
<spring:message var="passwordPlaceholder" code="index.passwordPlaceholder"/>
<spring:message var="remembermeCheckbox" code="index.remembermeCheckbox"/>
<spring:message var="loginButton" code="index.loginButton"/>
<spring:message var="noAccountLabel" code="index.noAccountLabel"/>
<spring:message var="registerLink" code="index.registerLink"/>

<spring:url var="loginUrl" value="/j_spring_security_check"/>
<spring:url var="registerUrl" value="/register"/>


<div class="index-container">
	<c:if test="${not empty registerSuccessMessage}">
	    <div class="alert alert-success">${registerSuccessMessage}</div>
	</c:if>
	
	<div class="welcome-panel">
	    <h1>${welcomeText}</h1>
	</div>
    
	<form role="form" action="${loginUrl}" method="post" class="login-form">
	    <c:if test="${not empty loginFailMessage}">
	        <div class="alert alert-danger error">${loginFailMessage}</div>
	    </c:if>
	    <input type="text" name="j_username" class="form-control" placeholder="${emailPlaceholder}" required autofocus>
	    <input type="password" name="j_password" class="form-control" placeholder="${passwordPlaceholder}" required>
	    <div class="checkbox">
            <label><input type="checkbox" value="remember-me"> ${remembermeCheckbox}</label>
	    </div>
	    <button type="submit" name="submit" class="btn btn-primary btn-block">${loginButton}</button>
        <span>${noAccountLabel} <a href="${registerUrl}">${registerLink}</a></span>
    </form>
</div>