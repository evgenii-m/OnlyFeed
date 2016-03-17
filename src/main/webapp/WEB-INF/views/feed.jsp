<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<div class="container">
    <c:if test="${not empty feedItemList}">
        <c:forEach items="${feedItemList}" var="feedItem">
            <div class="feed-item">
                <span class="feed-item-title">${feedItem.title}</span>
                <div class="feed-item-content">
                    <div style="background-image: url(${feedItem.imageUrl});" class="feed-item-img"></div>
                    <div class="feed-item-info">
                        <p>${feedItem.description}</p>
                        <span>${feedItem.publishedDate}</span>
                    </div>
                </div>
            </div>
        </c:forEach>
    </c:if>
</div>