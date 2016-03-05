<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<div>
    <c:if test="${not empty feedChannelList}">
        <table border="1" cellpadding="10" bordercolor="#111111">
            <thead>
                <tr>
                    <td><b>NAME</b></td>
                    <td><b>URL</b></td>
                </tr>
            </thead>
            <tbody>
		        <c:forEach items="${feedChannelList}" var="feedChannel">
		            <tr>
		                <td>${feedChannel.name}</td>
		                <td>${feedChannel.url}</td>
		            </tr>
		        </c:forEach>
	        </tbody>
        </table>
    </c:if>
</div>

<div>
</div>