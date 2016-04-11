<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<spring:message var="backLink" code="feed.backLink"/>

<div class="feed-container">
	<div class="feed-list" id="feed-item-list">
	    <c:if test="${not empty feedItemList}">
	        <c:forEach items="${feedItemList}" var="feedItem">
	            <div class="item" id="${feedItem.id}">
	                <div style="background-image: url(${feedItem.imageUrl});" class="feed-logo"></div>
	                <div class="feed-content">
		                <div class="feed-info">
		                    <div class="title">
		                        <span class="add-feed-tab-link">${feedItem.title}</span>
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
	       <span id="back-link" class="link" style="display: none;">${backLink}</span>
	   </div>
	   <div class="feed-tab-list">
	   </div>
	   <div class="feed-tab-detail" style="display: none;">
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
    $(document).ready(function() {
        displayFeedTabList();
	});
</script>
