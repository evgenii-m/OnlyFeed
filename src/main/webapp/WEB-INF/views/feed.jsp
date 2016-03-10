<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<div>
    <c:if test="${not empty feedItemList}">
        <c:forEach items="${feedItemList}" var="feedItem">
            <div class="feed-item">
                <b>${feedItem.title}</b>
                <br>
                ${feedItem.publishedDate}
                <br>
                <p>${feedItem.description}</p>
                <br>
                <a href="${feedItem.link}">Read More</a>
            </div>
        </c:forEach>
    </c:if>
</div>