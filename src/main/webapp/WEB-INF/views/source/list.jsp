<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<spring:message var="newFeedSourceFormTitleText" code="newFeedSourceFormTitleText"/>
<spring:message var="newFeedSourceFormButtonText" code="newFeedSourceFormButtonText"/>

<div class="container">
    <form:form modelAttribute="newFeedSource" method="post" class="new-feed-source-form">
        <b>${newFeedSourceFormTitleText}</b>
        <div class="new-feed-source-input">
	        <form:input path="url" value=""/>
	        <button type="submit">${newFeedSourceFormButtonText}</button>
        </div>
        <div>
            <form:errors path="url"/>
        </div>
    </form:form>
	
	<div class="feed-source-list">
	    <c:forEach items="${feedSourceList}" var="feedSource">
	        <div class="item">
	            <span class="name">${feedSource.name}</span>
	        </div>
	    </c:forEach>
	</div>
</div>