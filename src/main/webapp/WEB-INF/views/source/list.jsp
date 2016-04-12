<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<spring:message var="newFeedSourcePlaceholder" code="list.newFeedSourcePlaceholder"/>
<spring:message var="newFeedSourceButton" code="list.newFeedSourceButton"/>
<spring:message var="editLink" code="list.editLink"/>
<spring:message var="deleteLink" code="list.deleteLink"/>

<spring:url var="showFeedUrl" value="/feed"/>
<spring:url var="editFeedSourceUrl" value="/source/edit"/>
<spring:url var="deleteFeedSourceUrl" value="/source/delete"/>

<div class="content-container">
    <form:form modelAttribute="newFeedSource" method="post" id="new-feed-source-form" class="form-container">
	    <div class="form-row">
	        <form:input path="url" class="form-control" placeholder="${newFeedSourcePlaceholder}"/>
	        <button type="submit" class="btn btn-sm btn-primary">${newFeedSourceButton}</button>
            <form:errors path="url" class="alert alert-danger error" element="div"/>
        </div>
    </form:form>

	<div class="feed-list" id="feed-source-list">
	    <c:forEach items="${feedSources}" var="feedSource">
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


<script type="text/javascript">
    $(document).ready(function() {        
        $("#new-feed-source-form").validate({
            errorElement: "div",
            errorPlacement: function(error, element) {
                error.addClass("alert alert-danger");
                error.appendTo(element.parent());
            },
            focusInvalid: true,
            onkeyup: false,
            rules: {
            	url: {
                    required: true,
                    url: true,
                    maxlength: 256
            	}
            }
        });
    });
</script>