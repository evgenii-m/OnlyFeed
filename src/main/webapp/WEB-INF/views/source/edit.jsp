<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<spring:message var="addFormTitle" code="source.edit.addFormTitle"/>
<spring:message var="editFormTitle" code="source.edit.editFormTitle"/>
<spring:message var="nameLabel" code="source.edit.nameLabel"/>
<spring:message var="urlLabel" code="source.edit.urlLabel"/>
<spring:message var="logoLabel" code="source.edit.logoLabel"/>
<spring:message var="descriptionLabel" code="source.edit.descriptionLabel"/>
<spring:message var="cancelButton" code="source.edit.cancelButton"/>
<spring:message var="saveButton" code="source.edit.saveButton"/>
<spring:message var="feedSourceNotFoundAlert" code="source.edit.feedSourceNotFoundAlert"/>
<spring:message var="pictureSelectMessage" code="register.pictureSelectMessage"/>

<spring:url var="listFeedSourceUrl" value="/source"/>


<c:if test="${empty feedSource}">
    <div class="alert alert-danger not-found-alert">${feedSourceNotFoundAlert}</div>
</c:if>
<c:if test="${not empty feedSource}">
    <spring:eval var="formTitle" expression="feedSource.id == null ? addFormTitle : editFormTitle"/>
	<form:form modelAttribute="feedSource" method="post" class="form-container" id="edit-feed-source-form"
            enctype="multipart/form-data">
	    <div class="form-title">${formTitle}</div>
	    <div class="form-row">
            <form:label path="name" class="required-field-label">${nameLabel}</form:label>
            <form:input path="name" class="form-control"/>
	        <form:errors path="name" class="alert alert-danger error" element="div"/>
	    </div>
	    <div class="form-row">
	        <form:label path="url" class="required-field-label">${urlLabel}</form:label>
	        <form:input path="url" class="form-control"/>
	        <form:errors path="url" class="alert alert-danger error" element="div"/>
	    </div>
	    <div class="form-row">
	        <form:label path="description">${descriptionLabel}</form:label>
	        <form:textarea path="description" class="form-control"/>
	        <form:errors path="description" class="alert alert-danger error" element="div"/>
	    </div>
	    <div class="form-row">
	        <form:label path="logoUrl">${logoLabel}</form:label>
	        <form:hidden path="logoUrl" value="${feedSource.logoUrl}"/>
	        <div style="background-image: url(${feedSource.logoUrl});" class="feed-logo"></div>
            <div class="select-picture" style="width: 250px;">
                <input type="file" name="picture" id="picture-file" style="width: 250px;"/>
                <span>${pictureSelectMessage}</span>
            </div>
	        <form:errors path="logoUrl" class="alert alert-danger error" element="div"/>
	    </div>
	    <div class="form-buttons">
	        <button type="button" onclick="location.href='${listFeedSourceUrl}'" class="btn btn-warning">${cancelButton}</button>
	        <button type="submit" class="btn btn-primary">${saveButton}</button>
	    </div>
	</form:form>
</c:if>


<script type="text/javascript">
    $(document).ready(function() {        
        $("#edit-feed-source-form").validate({
            errorElement: "div",
            errorPlacement: function(error, element) {
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
                url: {
                    required: true,
                    url: true,
                    maxlength: 512
                },
                description: {
                    maxlength: 1000
                },
                logoUrl: {
                    required: true,
                    url: true,
                    maxlength: 512
                }
            }
        });
    });
</script>