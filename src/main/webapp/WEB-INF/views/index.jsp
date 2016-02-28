<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<div>
    <c:if test="${not empty rssChannelList}">
        <c:forEach items="${rssChannelList}" var="rssChannel">
            <tr>
                <td>${rssChannel.id}</td>
                <td>${rssChannel.name}</td>
                <td>${rssChannel.url}</td>
            </tr>
        </c:forEach>
    </c:if>
</div>