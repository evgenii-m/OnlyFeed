<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<spring:message var="projectName" code="projectName"/>
<spring:message var="feedSourcesLabel" code="header.feedSourcesLabel"/>
<spring:message var="accountSettingsLabel" code="header.accountSettingsLabel"/>
<spring:message var="logoutLabel" code="header.logoutLabel"/>

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
            <c:if test="${not empty user}">
                <ul class="nav navbar-nav navbar-right">
                    <li class="dropdown">
                        <div class="dropdown-toggle user-panel" role="button" data-toggle="dropdown" aria-hidden="true" 
                                aria-haspopup="true" aria-expanded="true">
                            <span>${user.name}</span>
                            <div class="feed-logo user-picture" style="background-image: url(${user.pictureUrl});"></div>
                        </div>
                        <ul class="dropdown-menu user-panel-menu">
                            <li><a href="${sourceUrl}">${feedSourcesLabel}</a></li>
                            <li><a href="${userSettingsUrl}">${accountSettingsLabel}</a></li>
                            <li><a href="${logoutUrl}">${logoutLabel}</a></li>
                        </ul>
                    </li>
                </ul>
                
                <ul class="nav navbar-nav navbar-right toolpane">
                </ul>
            </c:if>
        </div> <!--/.nav-collapse -->
    </div>
</nav>
