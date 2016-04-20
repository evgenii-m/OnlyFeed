<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<spring:message var="projectName" code="projectName"/>
<spring:message var="logoutLabel" code="header.logoutLabel"/>
<spring:message var="showAllSourcesLabel" code="header.showAllSourcesLabel"/>
<spring:message var="viewLabel" code="header.viewLabel"/>
<spring:message var="compactViewLabel" code="header.compactViewLabel"/>
<spring:message var="extendedViewLabel" code="header.extendedViewLabel"/>
<spring:message var="sortingLabel" code="header.sortingLabel"/>
<spring:message var="newestSortingLabel" code="header.newestSortingLabel"/>
<spring:message var="oldestSortingLabel" code="header.oldestSortingLabel"/>
<spring:message var="filterLabel" code="header.filterLabel"/>
<spring:message var="allFilterLabel" code="header.allFilterLabel"/>
<spring:message var="unreadFilterLabel" code="header.unreadFilterLabel"/>
<spring:message var="readFilterLabel" code="header.readFilterLabel"/>
<spring:message var="latestDayFilterLabel" code="header.latestDayFilterLabel"/>

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
                    <li onclick="window.location.href='${sourceUrl}'">${showAllSourcesLabel}</li>
                    <c:if test="${not empty currentFeedSource}">
                        <li class="glyphicon glyphicon-menu-right delimiter" aria-hidden="true"></li>
                        <li id="fs-${currentFeedSource.id}">${currentFeedSource.name}</li>
                    </c:if>
                </ul>
                <ul class="nav navbar-nav navbar-right">
                    <li class="glyphicon glyphicon-refresh action-icon" aria-hidden="true"></li>
                    <li class="dropdown">
                        <span class="dropdown-toggle glyphicon glyphicon-cog action-icon" data-toggle="dropdown" 
                                role="button" aria-hidden="true" aria-haspopup="true" aria-expanded="true"></span>
                        <ul id="settings-menu" class="dropdown-menu">
                            <li class="dropdown-header">${viewLabel}</li>
                            <li id="view-type-0" class="view-type-item">${compactViewLabel}</li>
                            <li id="view-type-1" class="view-type-item">${extendedViewLabel}</li>
                            <li class="dropdown-header">${sortingLabel}</li>
                            <li id="sorting-type-0" class="sorting-type-item">${newestSortingLabel}</li>
                            <li id="sorting-type-1" class="sorting-type-item">${oldestSortingLabel}</li>
                            <li class="dropdown-header">${filterLabel}</li>
                            <li id="filter-type-0" class="sorting-type-item">${allFilterLabel}</li>
                            <li id="filter-type-1" class="sorting-type-item">${unreadFilterLabel}</li>
                            <li id="filter-type-2" class="sorting-type-item">${readFilterLabel}</li>
                            <li id="filter-type-3" class="sorting-type-item">${latestDayFilterLabel}</li>
                        </ul>
                    </li>
                    <li><a href="#"><sec:authentication property="principal.username"/></a></li>
                    <li><a href="${logoutUrl}">${logoutLabel}</a></li>
                </ul>
            </sec:authorize>
        </div> <!--/.nav-collapse -->
    </div>
</nav>
