<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<div>
    <c:if test="${not empty rssChannelList}">
        <table border="1" cellpadding="10" bordercolor="#111111">
            <thead>
                <tr>
                    <td><b>NAME</b></td>
                    <td><b>URL</b></td>
                </tr>
            </thead>
            <tbody>
		        <c:forEach items="${rssChannelList}" var="rssChannel">
		            <tr>
		                <td>${rssChannel.name}</td>
		                <td>${rssChannel.url}</td>
		            </tr>
		        </c:forEach>
	        </tbody>
        </table>
    </c:if>
</div>