<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<spring:message var="title" code="password.reset.title"/>
<spring:message var="emailPlaceholder" code="index.emailPlaceholder"/>
<spring:message var="passwordPlaceholder" code="index.passwordPlaceholder"/>
<spring:message var="sendButton" code="password.reset.sendButton"/>
<spring:message var="applyButton" code="password.reset.applyButton"/>


<div class="index-container">
    <div class="title">${title}</div>
    <div>${message}</div>
    <c:if test="${empty passwordReset}">
        <form:form id="reset-request-form" class="reset-form" method="post" >
            <input type="text" id="email" name="email" class="form-control" placeholder="${emailPlaceholder}" autofocus>
            <button type="submit" name="submit" class="btn btn-primary btn-block">${sendButton}</button>
            <c:if test="${not empty errorMessage}">
                <div class="alert alert-danger error">${errorMessage}</div>
            </c:if>
        </form:form>
    </c:if>
    <c:if test="${not empty passwordReset}">
        <form:form id="reset-password-form" class="reset-form" method="post" >
            <input type="password" id="newPassword" name="newPassword" class="form-control" placeholder="${passwordPlaceholder}" autofocus>
            <button type="submit" name="submit" class="btn btn-primary btn-block">${applyButton}</button>
            <c:if test="${not empty errorMessage}">
                <div class="alert alert-danger error">${errorMessage}</div>
            </c:if>
        </form:form>
    </c:if>
</div>

<script type="text/javascript">
    $(document).ready(function() {
        $("#reset-request-form").validate({
            errorElement: "div",
            errorPlacement: function(error, element) {
                error.addClass("alert alert-danger error");
                error.appendTo(element.parent());
            },
            focusInvalid: true,
            onkeyup: false,
            rules: {
                email: {
                    required: true,
                    email: true,
                    maxlength: 64
                }
            }
        });
        $("#reset-password-form").validate({
            errorElement: "div",
            errorPlacement: function(error, element) {
                error.addClass("alert alert-danger");
                error.appendTo(element.parent());
            },
            focusInvalid: true,
            onkeyup: false,
            rules: {
                newPassword: {
                    required: true,
                    minlength: 6,
                    maxlength: 64
                }
            }
        });
    });
</script>