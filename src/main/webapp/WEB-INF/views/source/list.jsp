<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<spring:message var="newFeedSourcePlaceholder" code="source.list.newFeedSourcePlaceholder"/>
<spring:message var="newFeedSourceButton" code="source.list.newFeedSourceButton"/>
<spring:message var="editLink" code="source.list.editLink"/>
<spring:message var="deleteLink" code="source.list.deleteLink"/>
<spring:message var="noFeedSourcesMessage" code="source.list.noFeedSourcesMessage"/>

<spring:url var="feedUrl" value="/feed/"/>
<spring:url var="sourceEditUrl" value="/source/edit/"/>
<spring:url var="sourceDeleteUrl" value="/source/delete/"/>

<div class="content-container">
    <form:form modelAttribute="newFeedSource" method="post" id="new-feed-source-form" class="form-container">
	    <div class="form-row">
	        <form:input path="url" class="form-control" placeholder="${newFeedSourcePlaceholder}"/>
	        <button type="submit" class="btn btn-sm btn-primary">${newFeedSourceButton}</button>
            <form:errors path="url" class="alert alert-danger error" element="div"/>
        </div>
    </form:form>

	<div class="feed-list" id="feed-source-list">
        <c:if test="${empty feedSources}">
            <h2><i>${noFeedSourcesMessage}</i></h2>
        </c:if>
        <c:if test="${not empty feedSources}">
		    <c:forEach items="${feedSources}" var="feedSource">
		        <div class="item" id="${feedSource.id}">
	                <div style="background-image: url(${feedSource.logoUrl});" class="feed-logo"></div>
	                <div class="name">
	                    <span class="show-feeds-link">${feedSource.name}</span>
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

        $(".show-feeds-link").click(function() {
        	window.location.href = "${feedUrl}" + $(this).parents(".item").attr("id");
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