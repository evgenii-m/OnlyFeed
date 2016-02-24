<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<spring:message var="appName" code="application_name" htmlEscape="false"/>
<spring:message var="labelWelcome" code="welcome_label" arguments="${appName}"/>
<spring:message var="labelAddChannel" code="label_add_channel"/>

<h3>${labelWelcome}</h3>
<br/>

<form:form method="post" modelAttribute="newRssChannel">
    <form:input path="name"/>
    <form:input path="url"/>
    <button type="submit">${labelAddChannel}</button>
</form:form>

<div>
    <c:if test="not empty rssChannelList">
        <c:forEach items="${rssChannelList}" var="rssChannel">
            <tr>
                <td>${rssChannel.id}</td>
                <td>${rssChannel.name}</td>
                <td>${rssChannel.url}</td>
            </tr>
        </c:forEach>
    </c:if>
</div>