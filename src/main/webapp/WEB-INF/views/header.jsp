<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<spring:message var="projectName" code="projectName"/>
<spring:message var="profileSettingsLabel" code="header.profileSettingsLabel"/>
<spring:message var="logoutLabel" code="header.logoutLabel"/>
<spring:message var="showAllSourcesLabel" code="header.showAllSourcesLabel"/>

<spring:url var="feedUrl" value="/feed/"/>
<spring:url var="sourceUrl" value="/source/"/>
<spring:url var="userSettingsUrl" value="/user/settings/"/>
<spring:url var="logoutUrl" value="/j_spring_security_logout"/>

<nav class="navbar navbar-default navbar-fixed-top">
    <div class="container">
        <div class="navbar-header">
            <a class="navbar-brand" href="${feedUrl}">${projectName}</a>
        </div>
        <div id="navbar" class="navbar-collapse collapse">
            <sec:authorize access="isAuthenticated()">
                <ul class="nav navbar-nav navbar-source">
                    <li onclick="window.location.href='${sourceUrl}'">${showAllSourcesLabel}</li>
                    <c:if test="${not empty currentFeedSource}">
                        <li class="glyphicon glyphicon-menu-right delimiter" aria-hidden="true"></li>
                        <li id="fs-${currentFeedSource.id}">${currentFeedSource.name}</li>
                    </c:if>
                </ul>
                
                <ul class="nav navbar-nav navbar-right">
                    <li class="dropdown">
                        <div class="dropdown-toggle user-panel" role="button" data-toggle="dropdown" aria-hidden="true" 
                                aria-haspopup="true" aria-expanded="true">
                            <span>${user.name}</span>
                            <div class="user-picture"><img src="${user.pictureUrl}"></div>
                        </div>
                        <ul class="dropdown-menu user-panel-menu">
                            <li onclick="window.location.href='${userSettingsUrl}'">${profileSettingsLabel}</li>
                            <li onclick="window.location.href='${logoutUrl}'">${logoutLabel}</li>
                        </ul>
                    </li>
                </ul>
                
                <ul class="nav navbar-nav navbar-right tool-pane">
                </ul>
            </sec:authorize>
        </div> <!--/.nav-collapse -->
    </div>
</nav>
