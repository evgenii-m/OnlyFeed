<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%-- <%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %> --%>

<spring:message var="projectName" code="projectName"/>
<spring:message var="welcomeText" code="index.welcomeText" arguments="${projectName}"/>
<spring:message var="emailPlaceholder" code="index.emailPlaceholder"/>
<spring:message var="usernamePlaceholder" code="index.usernamePlaceholder"/>
<spring:message var="passwordPlaceholder" code="index.passwordPlaceholder"/>
<spring:message var="remembermeCheckbox" code="index.remembermeCheckbox"/>
<spring:message var="loginButton" code="index.loginButton"/>

<spring:url var="loginUrl" value="/j_spring_security_check"/>

<div class="index-container">
	<div class="welcome-panel">
	    <h1>${welcomeText}</h1>
	</div>

	<form role="form" action="${loginUrl}" method="post" class="login-form">
<%-- 	    <input type="email" id="j_email" name="j_email" class="form-control" placeholder="${emailPlaceholder}" required autofocus> --%>
	    <input type="text" id="j_username" name="j_username" class="form-control" placeholder="${usernamePlaceholder}" required autofocus>
	    <input type="password" id="j_password" name="j_password" class="form-control" placeholder="${passwordPlaceholder}" required>
<!-- 	    <div class="checkbox"> -->
<%--             <label><input type="checkbox" value="remember-me"> ${remembermeCheckbox}</label> --%>
<!-- 	    </div> -->
	    <button type="submit" name="submit" class="btn btn-lg btn-primary btn-block">${loginButton}</button>
    </form>

<!-- 	<div class="signup-form"> -->
<!-- 	</div> -->
</div>