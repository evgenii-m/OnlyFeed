<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<spring:message var="formTitle" code="list.formTitle"/>
<spring:message var="addButton" code="list.addButton"/>
<spring:message var="editLink" code="list.editLink"/>
<spring:message var="deleteLink" code="list.deleteLink"/>

<spring:url var="showFeedUrl" value="/feed"/>
<spring:url var="editFeedSourceUrl" value="/source/edit"/>
<spring:url var="deleteFeedSourceUrl" value="/source/delete"/>

<div class="content-container">
    <form:form modelAttribute="newFeedSource" method="post" class="new-feed-source-form">
        <div class="feed-form-title">${formTitle}</div>
        <div class="feed-form-row">
	        <form:input path="url" class="form-control" value=""/>
	        <button type="submit" class="btn btn-sm btn-primary">${addButton}</button>
        </div>
        <form:errors path="url" class="alert alert-danger error" element="div"/>
    </form:form>

	<div class="feed-source-list">
	    <c:forEach items="${feedSourceList}" var="feedSource">
	        <div class="item">
                <div style="background-image: url(${feedSource.logoUrl});" class="feed-logo"></div>
                <div class="name">
                    <span onclick="location.href='${showFeedUrl}'/${feedSource.id}">${feedSource.name}</span>
                </div>
                <div class="description">
                    ${feedSource.description}
                </div>
                <div class="action-links">
                    <span onclick="location.href='${editFeedSourceUrl}/${feedSource.id}'">${editLink}</span> | 
                    <span onclick="submitPostRequest('${deleteFeedSourceUrl}/${feedSource.id}')">${deleteLink}</span>
                </div>
	        </div>
	    </c:forEach>
	</div>
</div>