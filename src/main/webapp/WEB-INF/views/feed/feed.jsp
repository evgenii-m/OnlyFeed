<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<spring:message var="backActionIconTitle" code="feed.backActionIconTitle"/>
<spring:message var="addTabActionIconTitle" code="feed.addTabActionIconTitle"/>
<spring:message var="removeTabActionIconTitle" code="feed.removeTabActionIconTitle"/>
<spring:message var="tabAddedActionIconTitle" code="feed.tabAddedActionIconTitle"/>
<spring:message var="noFeedsMessage" code="feed.noFeedsMessage"/>
<spring:message var="showMoreNewsButton" code="feed.showMoreNewsButton"/>

<spring:url var="feedItemUrl" value="/feed/item/"/>
<spring:url var="feedTabUrl" value="/feed/tab/"/>
<spring:url var="feedTabMoveUrl" value="/feed/tab/move/"/>

<div class="feed-container">
	<div class="feed-list" id="feed-item-list">
        <c:if test="${not empty refreshErrorMessage}">
            <div class="alert alert-warning alert-dismissible" role="alert">
                <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
                ${refreshErrorMessage}
            </div>
        </c:if>
        <c:if test="${empty feedItems}">
            <h2><i>${noFeedsMessage}</i></h2>
        </c:if>
	    <c:if test="${not empty feedItems}">
	        <c:forEach items="${feedItems}" var="feedItem">
	            <div class="item" id="fi-${feedItem.id}">
	                <div style="background-image: url(${feedItem.imageUrl});" class="feed-logo"></div>
	                <div class="feed-content">
		                <div class="feed-info">
		                    <div class="title">
		                        <span class="select-item-action">${feedItem.title}</span>
		                    </div>
		                    <div class="summary">
		                        ${feedItem.summary}
		                    </div>
		                </div>
		                <div class="pub-info">
		                    <span class="source-name">${feedItem.feedSource.name}</span>
		                    <span class="published-date">&nbsp;|&nbsp;${feedItem.publishedDateString}</span>
		                </div>
	                </div>
	            </div>
	        </c:forEach>
            <button class="btn btn-default show-more-button" type="button">${showMoreNewsButton}</button>
        </c:if>
	</div>
	   
	<div class="feed-tab-panel">
		<div class="tool-pane">
            <div id="feed-details-actions" style="display: none;">
                <div class="float-left">
				    <span id="back-action" class="glyphicon glyphicon-arrow-left action-icon"
	                        title="${backActionIconTitle}" aria-hidden="true"></span>
                </div>
	            <div class="float-right">
                    <a id="open-original-action" class="glyphicon glyphicon-new-window action-icon" 
                            aria-hidden="true" target="_blank"></a>
				    <span id="add-tab-action" class="glyphicon glyphicon-plus action-icon"
				            title="${addTabActionIconTitle}" aria-hidden="true"></span>
			    </div>
            </div>
		</div>
		
		<ul class="feed-tab-list sortable" aria-dropeffect="move">
		</ul>
		
		<div class="feed-item-details" style="display: none;">
		    <div class="pub-info">
		        <span class="source-name"></span>
		        <span class="author"></span>
		        <span class="published-date"></span>
		    </div>
		    <div class="title"></div>
		    <div class="description"></div>
		</div>
	</div>
	
</div>

<script type="text/javascript">
    var pageSize = "${pageSize}";
    var feedTabUrl = "${feedTabUrl}";
    var feedItemUrl = "${feedItemUrl}";
    var feedTabMoveUrl = "${feedTabMoveUrl}";
    var removeTabActionIconTitle = "${removeTabActionIconTitle}";
    var addTabActionIconTitle = "${addTabActionIconTitle}";
    var showMoreNewsButton = "${showMoreNewsButton}";
    
    $(document).ready(function() {
        displayFeedTabList();
        $(".select-item-action").click(function() {
        	var feedItemId = $(this).parents(".item").attr("id").replace(/\D/g, '');
        	displayFeedItemDetails(feedItemId);
       	});
        $(".show-more-button").click(function() {
        	showMoreFeedItems();
        });
	});
</script>
