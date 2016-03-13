<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<div class="container">
    <form:form modelAttribute="feedSource" method="post" class="edit-feed-source-form">
        <div>
	        <form:label path="name" class="required-field-label">Name</form:label>
	        <form:input path="name" class="form-control"/>
        </div>
        <div>
            <form:label path="url" class="required-field-label">URL</form:label>
            <form:input path="url" class="form-control"/>
        </div>
        <div class="form-buttons">
            <button type="button" class="btn btn-warning">Close</button>
            <button type="submit" class="btn btn-primary">Save</button>
        </div>
    </form:form>
</div>