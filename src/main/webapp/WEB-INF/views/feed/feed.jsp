<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<spring:message var="backLinkTitle" code="feed.backLinkTitle"/>
<spring:message var="addTabLinkTitle" code="feed.addTabLinkTitle"/>
<spring:message var="removeTabLinkTitle" code="feed.removeTabLinkTitle"/>
<spring:message var="tabAddedLinkTitle" code="feed.tabAddedLinkTitle"/>
<spring:message var="noFeedsMessage" code="feed.noFeedsMessage"/>

<spring:url var="feedItemUrl" value="/feed/item/"/>
<spring:url var="feedTabUrl" value="/feed/tab/"/>
<spring:url var="feedTabMoveUrl" value="/feed/tab/move/"/>

<div class="feed-container">
	<div class="feed-list" id="feed-item-list">
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
		                        <span class="select-item-link">${feedItem.title}</span>
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
        </c:if>
	</div>
	   
	<div class="feed-tab-panel">
		<div class="tool-pane">
		    <span id="back-link" class="glyphicon glyphicon-arrow-left link" style="display: none;" 
		            title="${backLinkTitle}" aria-hidden="true"></span>
		    <span id="add-tab-link" class="glyphicon glyphicon-plus link" style="display: none; float: right;"
		            title="${addTabLinkTitle}" aria-hidden="true"></span>
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
    var feedTabUrl = "${feedTabUrl}";
    var feedItemUrl = "${feedItemUrl}";
    var feedTabMoveUrl = "${feedTabMoveUrl}";
    var removeTabLinkTitle = "${removeTabLinkTitle}";
    var addTabLinkTitle = "${addTabLinkTitle}";
    var tabAddedLinkTitle = "${tabAddedLinkTitle}";
    
    $(document).ready(function() {
        displayFeedTabList();
        $(".select-item-link").click(function() {
        	var feedItemId = $(this).parents(".item").attr("id").replace(/\D/g, '');
        	displayFeedItemDetails(feedItemId);
       	});
	});
</script>
