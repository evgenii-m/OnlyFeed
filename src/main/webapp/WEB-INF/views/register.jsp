<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<spring:message var="formTitle" code="register.formTitle"/>
<spring:message var="pictureLabel" code="register.pictureLabel"/>
<spring:message var="nameLabel" code="register.nameLabel"/>
<spring:message var="emailLabel" code="register.emailLabel"/>
<spring:message var="passwordLabel" code="register.passwordLabel"/>
<spring:message var="confirmPasswordLabel" code="register.confirmPasswordLabel"/>
<spring:message var="cancelButton" code="register.cancelButton"/>
<spring:message var="registerButton" code="register.registerButton"/>

<spring:url var="indexUrl" value="/"/>


<form:form modelAttribute="user" method="post" id="register-form" class="form-container">
    <div class="form-title">${formTitle}</div>
    <div class="form-row">
        <form:label path="pictureUrl">${pictureLabel}</form:label>
        <form:hidden path="pictureUrl" value="${user.pictureUrl}"/>
        <div style="background-image: url(${user.pictureUrl});" class="user-picture"></div>
        <form:errors path="pictureUrl" class="alert alert-danger error" element="div"/>
    </div>
    <div class="form-row">
	    <form:label path="name" class="required-field-label">${nameLabel}</form:label>
	    <form:input path="name" class="form-control"/>
	    <form:errors path="name" class="alert alert-danger error" element="div"/>
	</div>
    <div class="form-row">
        <form:label path="email" class="required-field-label">${emailLabel}</form:label>
        <form:input path="email" class="form-control"/>
        <form:errors path="email" class="alert alert-danger error" element="div"/>
    </div>
	<div class="form-row">
	    <form:label path="password" class="required-field-label">${passwordLabel}</form:label>
	    <form:password path="password" class="form-control"/>
	    <form:errors path="password" class="alert alert-danger error" element="div"/>
	</div>
    <div class="form-row">
        <label for="confirmPassword" class="required-field-label">${confirmPasswordLabel}</label>
        <input type="password" id="confirmPassword" name="confirmPassword" class="form-control"/>
    </div>
    <div class="form-buttons">
        <button type="button" onclick="location.href='${indexUrl}'" class="btn btn-warning">${cancelButton}</button>
        <button type="submit" onclick="registerUser()" class="btn btn-primary">${registerButton}</button>
    </div>
</form:form>

<script type="text/javascript">
	$(document).ready(function() {        
	    $("#register-form").validate({
	        errorElement: "div",
	        errorPlacement: function(error, element) {
	            error.addClass("alert alert-danger");
	            error.appendTo(element.parent());
	        },
	        focusInvalid: true,
	        onkeyup: false,
	        rules: {
	            pictureUrl: {
	                required: true,
	                url: true,
	                maxlength: 256
	            },
	            name: {
	                required: true,
	                minlength: 2,
	                maxlength: 100
	            },
	            email: {
	                required: true,
	                email: true,
	                maxlength: 100
	            },
	            password: {
	                required: true,
	                minlength: 6,
	                maxlength: 50
	            },
	            confirmPassword: {
	                required: true,
	                minlength: 6,
	                maxlength: 50,
	                equalTo: "#password"
	            }
	        }
	    });
	});
</script>
