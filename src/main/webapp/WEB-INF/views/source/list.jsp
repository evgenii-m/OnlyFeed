<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<spring:message var="newFeedSourcePlaceholder" code="source.list.newFeedSourcePlaceholder"/>
<spring:message var="newFeedSourceButton" code="source.list.newFeedSourceButton"/>
<spring:message var="editLink" code="source.list.editLink"/>
<spring:message var="deleteLink" code="source.list.deleteLink"/>
<spring:message var="noFeedSourcesMessage" code="source.list.noFeedSourcesMessage"/>
<spring:message var="feedSamplesPanelTitle" code="source.list.feedSamplesPanelTitle"/>

<spring:url var="feedUrl" value="/feed/"/>
<spring:url var="sourceEditUrl" value="/source/edit/"/>
<spring:url var="sourceDeleteUrl" value="/source/delete/"/>


<div class="feed-sources-container">
    <form:form modelAttribute="newFeedSource" method="post" id="new-feed-source-form" class="form-container">
	    <div class="form-row">
	        <form:input path="url" class="form-control" placeholder="${newFeedSourcePlaceholder}"/>
	        <button type="submit" class="btn btn-sm btn-primary">${newFeedSourceButton}</button>
            <form:errors path="url" class="alert alert-danger error" element="div"/>
        </div>
    </form:form>

	<div class="feed-list" style="margin-top: 30px;">
        <c:if test="${empty feedSources}">
            <h3>${noFeedSourcesMessage}</h3>
        </c:if>
        <c:if test="${not empty feedSources}">
		    <c:forEach items="${feedSources}" var="feedSource">
		        <div class="item" id="${feedSource.id}">
	                <div style="background-image: url(${feedSource.logoUrl});" class="feed-logo"></div>
	                <div class="name">
	                    <a href="${feedUrl}${feedSource.id}">${feedSource.name}</a>
	                </div>
	                <div class="description">
	                    ${feedSource.description}
	                </div>
	                <div class="action-links">
	                    <span class="edit-link">${editLink}</span> | 
	                    <span class="delete-link">${deleteLink}</span>
	                </div>
		        </div>
		    </c:forEach>
	    </c:if>
	</div>
</div>

<div class="feed-samples-panel">
    <div class="panel-header" style="font-weight: bold;">${feedSamplesPanelTitle}</div>
    <div class="panel-content">
        <c:forEach items="${feedSamples}" var="feedSample">
            <div class="item">
                <div style="background-image: url(${feedSample.logoUrl});" class="feed-logo"></div>
                <div class="name">${feedSample.name}</div>
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
                    maxlength: 512
            	}
            }
        });
        
        $(".edit-link").click(function() {
            window.location.href = "${sourceEditUrl}" + $(this).parents(".item").attr("id");
        });
        
        $(".delete-link").click(function() {
        	var feedItem = $(this).parents(".item");
        	$.ajax({
        		url: "${sourceDeleteUrl}" + feedItem.attr("id"),
        		type: "delete",
        		success: function(response) {
        			if (response == true) {
        				feedItem.remove();
        			}
        		},
                error: function(error) {
                    console.log("error");
                    console.log(error);
                }
        	});
        });
    });
</script>