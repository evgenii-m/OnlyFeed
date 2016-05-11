<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<spring:message var="accountSettingsTitle" code="user.settings.accountSettingsTitle"/>
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
<spring:message var="updatePasswordErrorMessage" code="user.settings.updatePasswordErrorMessage"/>
<spring:message var="updatePasswordSuccessMessage" code="user.settings.updatePasswordSuccessMessage"/>
<spring:message var="removeAccountButton" code="user.settings.removeAccountButton"/>
<spring:message var="confirmRemoveTitle" code="user.settings.modal.confirmRemoveTitle"/>
<spring:message var="confirmRemoveMessage" code="user.settings.modal.confirmRemoveMessage"/>
<spring:message var="cancelButton" code="user.settings.modal.cancelButton"/>
<spring:message var="removeButton" code="user.settings.modal.removeButton"/>

<spring:url var="updatePictureUrl" value="/user/settings/picture"/>
<spring:url var="updateInfoUrl" value="/user/settings/info"/>
<spring:url var="updatePasswordUrl" value="/user/settings/password"/>


<div class="form-container" style="margin: 0 auto;">
    <div class="form-title">${accountSettingsTitle}</div>

    <div class="form-row bottom-border">
        <c:if test="${updatePictureResult == true}">
            <div class="alert alert-success update-alert">${updatePictureSuccessMessage}</div>
        </c:if>
        <c:if test="${updatePictureResult == false}">
            <div class="alert alert-danger update-alert">${updatePictureErrorMessage}</div>
        </c:if>
        <form:form modelAttribute="user" action="${updatePictureUrl}" method="post" id="user-picture-form" 
                 enctype="multipart/form-data">
            <form:label path="pictureUrl" class="required-field-label">${pictureLabel}</form:label>
            <div class="feed-logo user-picture" style="background-image: url(${user.pictureUrl});"></div>
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
            <div class="alert alert-success update-alert">${updateInfoSuccessMessage}</div>
        </c:if>
        <c:if test="${updateInfoResult == false}">
            <div class="alert alert-danger update-alert">${updateInfoErrorMessage}</div>
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
    
    <div class="form-row bottom-border">
        <c:if test="${updatePasswordResult == true}">
            <div class="alert alert-success update-alert">${updatePasswordSuccessMessage}</div>
        </c:if>
        <c:if test="${updatePasswordResult == false}">
            <div class="alert alert-danger update-alert">${updatePasswordErrorMessage}</div>
        </c:if>
        <form:form action="${updatePasswordUrl}" method="post" id="user-password-form">
            <div class="form-row">
                <label for="curentPassword" class="required-field-label">${currentPasswordLabel}</label>
                <input type="password" id="currentPassword" name="currentPassword" form="user-password-form" class="form-control"/>
            </div>
            <div class="form-row">
                <label for="newPassword" class="required-field-label">${newPasswordLabel}</label>
                <input type="password" id="newPassword" name="newPassword" form="user-password-form" class="form-control"/>
            </div>
            <div class="form-row">
                <label for="confirmNewPassword" class="required-field-label">${confirmNewPasswordLabel}</label>
                <input type="password" id="confirmNewPassword" name="confirmNewPassword" class="form-control"/>
            </div>
            <div class="form-buttons">
                <button type="submit" class="btn btn-sm btn-primary">${changePasswordButton}</button>
            </div>
        </form:form>
    </div>
    
    <div class="form-row" style="width: 100%;">
        <button type="button" data-toggle="modal" data-target="#confirm-remove-modal" class="btn btn-danger" 
                style="display: block; margin: 0 auto;">
            <span class="glyphicon glyphicon-trash" aria-hidden="true"></span> ${removeAccountButton}
        </button>
    </div>
</div>

<!-- Confirm Remove Modal -->
<div id="confirm-remove-modal" class="modal fade" role="dialog">
    <div class="modal-dialog" style="width: 400px;">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal">&times;</button>
                <h4 class="modal-title">${confirmRemoveTitle}</h4>
            </div>
            <div class="modal-body">
                ${confirmRemoveMessage}
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">${cancelButton}</button>
                <button type="button" id="remove-user-button" class="btn btn-danger" data-dismiss="modal">${removeButton}</button>
            </div>
        </div>
    </div>
</div>


<script type="text/javascript">
    var userUrl = "<spring:url value='/user'/>";
    var indexUrl = "<spring:url value='/'/>";
    var updatePictureUrl = "${updatePictureUrl}";
    
    var deletePictureErrorMessage = "<spring:message code='user.settings.deletePictureErrorMessage'/>";
    var deletePictureSuccessMessage = "<spring:message code='user.settings.deletePictureSuccessMessage'/>";

    $(document).ready(function() {
        $("#delete-picture-button").click(function() {
        	deleteUserPicture();
        });
        
        $("#remove-user-button").click(function() {
        	removeUser();
        });
        
        $("#user-info-form").validate({
            errorElement: "div",
            errorPlacement: function(error, element) {
            	$(".update-alert").remove();
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
        
        $("#user-password-form").validate({
            errorElement: "div",
            errorPlacement: function(error, element) {
                $(".update-alert").remove();
                error.addClass("alert alert-danger");
                error.appendTo(element.parent());
            },
            focusInvalid: true,
            onkeyup: false,
            rules: {
                currentPassword: {
                    required: true,
                    minlength: 6,
                    maxlength: 64,
                	remote: {
                        url: "<spring:url value='/user/settings/password/verify'/>",
                        type: "get",
                        data: {
                            password: function () {
                                return $("#currentPassword").val();
                            }
                        }
                	}
                },
                newPassword: {
                    required: true,
                    minlength: 6,
                    maxlength: 64
                },
                confirmNewPassword: {
                    required: true,
                    minlength: 6,
                    maxlength: 64,
                    equalTo: "#newPassword"
                }
            },
            messages: {
                currentPassword: {
                    remote: "<spring:message code='validation.invalidCurrentPassword'/>"
                }
            }
        });
    });
</script>