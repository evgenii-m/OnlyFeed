<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<spring:message var="profileSettingsTitle" code="user.settings.profileSettingsTitle"/>
<spring:message var="pictureLabel" code="register.pictureLabel"/>
<spring:message var="pictureSelectMessage" code="register.pictureSelectMessage"/>
<spring:message var="deleteButton" code="user.settings.deleteButton"/>
<spring:message var="updateButton" code="user.settings.updateButton"/>
<spring:message var="nameLabel" code="register.nameLabel"/>
<spring:message var="emailLabel" code="register.emailLabel"/>
<spring:message var="newsStorageTimeLabel" code="user.settings.newsStorageTimeLabel"/>
<spring:message var="currentPasswordLabel" code="user.settings.currentPasswordLabel"/>
<spring:message var="newPasswordLabel" code="user.settings.newPasswordLabel"/>
<spring:message var="confirmNewPasswordLabel" code="user.settings.confirmNewPasswordLabel"/>
<spring:message var="changePasswordButton" code="user.settings.changePasswordButton"/>
<spring:message var="updatePictureErrorMessage" code="user.settings.updatePictureErrorMessage"/>
<spring:message var="updatePictureSuccessMessage" code="user.settings.updatePictureSuccessMessage"/>
<spring:message var="updateInfoErrorMessage" code="user.settings.updateInfoErrorMessage"/>
<spring:message var="updateInfoSuccessMessage" code="user.settings.updateInfoSuccessMessage"/>

<spring:url var="updatePictureUrl" value="/user/settings/picture"/>
<spring:url var="updateInfoUrl" value="/user/settings/info"/>
<spring:url var="updatePasswordUrl" value="/user/settings/password"/>


<div class="form-container">
    <div class="form-title">${profileSettingsTitle}</div>

    <div class="form-row bottom-border">
        <c:if test="${updatePictureResult == true}">
            <div class="alert alert-success picture-alert">${updatePictureSuccessMessage}</div>
        </c:if>
        <c:if test="${updatePictureResult == false}">
            <div class="alert alert-danger picture-alert">${updatePictureErrorMessage}</div>
        </c:if>
        <form:form modelAttribute="user" action="${updatePictureUrl}" method="post" id="user-picture-form" 
                 enctype="multipart/form-data">
            <form:label path="pictureUrl" class="required-field-label">${pictureLabel}</form:label>
            <div class="user-picture"><img src="${user.pictureUrl}" class="picture-thumbnail"></div>
            <div class="select-picture">
                <input type="file" name="picture" id="picture-file"/>
                <span>${pictureSelectMessage}</span>
                <div class="form-buttons">
                    <button type="button" id="delete-picture-button" class="btn btn-sm btn-danger">${deleteButton}</button>
                    <button type="submit" class="btn btn-sm btn-primary">${updateButton}</button>
                </div>
            </div>
        </form:form>
    </div>
    
    <div class="form-row bottom-border">
        <c:if test="${updateInfoResult == true}">
            <div class="alert alert-success user-info-alert">${updateInfoSuccessMessage}</div>
        </c:if>
        <c:if test="${updateInfoResult == false}">
            <div class="alert alert-danger user-info-alert">${updateInfoErrorMessage}</div>
        </c:if>
        <form:form modelAttribute="user" action="${updateInfoUrl}" method="post" id="user-info-form">
            <div class="form-row">
                <form:label path="name" class="required-field-label">${nameLabel}</form:label>
                <form:input path="name" class="form-control"/>
            </div>
            <div class="form-row">
                <form:label path="email" class="required-field-label">${emailLabel}</form:label>
                <form:input path="email" class="form-control"/>
            </div>
            <div class="form-row">
                <form:label path="newsStorageTime" class="required-field-label">${newsStorageTimeLabel}</form:label>
                <form:select path="newsStorageTime">
                    <form:options items="${newsStorageTimeList}"/>
                </form:select>
            </div>
            <div class="form-buttons">
                <button type="submit" class="btn btn-sm btn-primary">${updateButton}</button>
            </div>
        </form:form> 
    </div>
    
    <div class="form-row">
        <form:form modelAttribute="user" method="post" id="user-password-form">
            <div class="form-row">
                <form:label path="password" class="required-field-label">${currentPasswordLabel}</form:label>
                <form:password path="password" class="form-control"/>
                <form:errors path="password" class="alert alert-danger error" element="div"/>
            </div>
            <div class="form-row">
                <form:label path="password" class="required-field-label">${newPasswordLabel}</form:label>
                <form:password path="password" class="form-control"/>
                <form:errors path="password" class="alert alert-danger error" element="div"/>
            </div>
            <div class="form-row">
                <label for="confirmPassword" class="required-field-label">${confirmNewPasswordLabel}</label>
                <input type="password" id="confirmPassword" name="confirmPassword" class="form-control"/>
            </div>
            <div class="form-buttons">
                <button type="button" class="btn btn-sm btn-primary">${changePasswordButton}</button>
            </div>
        </form:form>
    </div>
</div>


<script type="text/javascript">
    var updatePictureUrl = "${updatePictureUrl}";
    var deletePictureErrorMessage = "<spring:message code='user.settings.deletePictureErrorMessage'/>";
    var deletePictureSuccessMessage = "<spring:message code='user.settings.deletePictureSuccessMessage'/>";

    $(document).ready(function() {               
        document.getElementById('picture-file').addEventListener('change', handleFileSelect, false);
        
        $("#delete-picture-button").click(function() {
        	deleteUserPicture();
        });
        
        $("#user-info-form").validate({
            errorElement: "div",
            errorPlacement: function(error, element) {
            	$(".user-info-alert").remove();
                error.addClass("alert alert-danger");
                error.appendTo(element.parent());
            },
            focusInvalid: true,
            onkeyup: false,
            rules: {
                name: {
                    required: true,
                    minlength: 2,
                    maxlength: 100
                },
                email: {
                    required: true,
                    email: true,
                    maxlength: 64,
                    remote: {
                        url: "<spring:url value='/user/settings/email/available'/>",
                        type: "get",
                        data: {
                            email: function () {
                                return $("#email").val();
                            }
                        }
                    }
                }
            },
            messages: {
                email: {
                    remote: "<spring:message code='validation.emailAlreadyUsed'/>"
                }
            }
        });
    });
</script>