<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<div class="content-container">
    <div class="feed-item-list">
        <div class="header-panel">
            <div class="title">Title</div>
        </div>
	    <c:if test="${not empty feedItemList}">
	        <c:forEach items="${feedItemList}" var="feedItem">
	            <div class="item">
		            <div style="background-image: url(${feedItem.imageUrl});" class="feed-logo"></div>
		            <div class="feed-content">
			            <div class="feed-info">
			                <div class="title">
			                    <span>${feedItem.title}</span>
			                </div>
				            <div class="summary">
				                ${feedItem.summary}
				            </div>
	                    </div>
	                    <div class="pub-info">
	                        ${feedItem.author} / ${feedItem.publishedDateString}
	                    </div>
                    </div>
	            </div>
	        </c:forEach>
	    </c:if>
    </div>
</div>