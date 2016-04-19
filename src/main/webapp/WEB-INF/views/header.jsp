<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<spring:message var="projectName" code="projectName"/>
<spring:message var="logoutLabel" code="header.logoutLabel"/>

<spring:url var="logoutUrl" value="/j_spring_security_logout"/>
<spring:url var="feedUrl" value="/feed/"/>
<spring:url var="sourceUrl" value="/source/"/>

<nav class="navbar navbar-default   navbar-fixed-top">
    <div class="container">
        <div class="navbar-header">
            <a class="navbar-brand" href="${feedUrl}">${projectName}</a>
        </div>
        <div id="navbar" class="navbar-collapse collapse">
            <sec:authorize access="isAuthenticated()">
                <ul class="nav navbar-nav navbar-source">
                    <li onclick="window.location.href='${sourceUrl}'">All</li>
                    <c:if test="${not empty currentFeedSource}">
                        <li class="glyphicon glyphicon-menu-right delimiter" aria-hidden="true"></li>
                        <li id="fs-${currentFeedSource.id}">${currentFeedSource.name}</li>
                    </c:if>
                </ul>
                <ul class="nav navbar-nav navbar-right">
                    <li class="glyphicon glyphicon-refresh action-icon"></li>
                    <li class="glyphicon glyphicon-list action-icon"></li>
                    <li><a href="#"><sec:authentication property="principal.username"/></a></li>
                    <li><a href="${logoutUrl}">${logoutLabel}</a></li>
                </ul>
            </sec:authorize>
        </div> <!--/.nav-collapse -->
    </div>
</nav>
