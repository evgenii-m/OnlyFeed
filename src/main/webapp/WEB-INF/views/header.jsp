<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<spring:message var="projectName" code="projectName"/>
<spring:message var="logoutLabel" code="header.logoutLabel"/>

<spring:url var="logoutUrl" value="/j_spring_security_logout"/>
<spring:url var="feedUrl" value="/feed"/>
<spring:url var="sourceUrl" value="/source"/>


<nav class="navbar navbar-inverse navbar-fixed-top">
    <div class="container">
        <div class="navbar-header">
            <a class="navbar-brand" href="${feedUrl}">${projectName}</a>
        </div>
        <div id="navbar" class="navbar-collapse collapse">
            <sec:authorize access="isAuthenticated()">
                <ul class="nav navbar-nav">
                    <li><a href="${sourceUrl}">Feed Sources</a></li>
<%--                     <c:if test="${not empty feedSourceName}"> --%>
<!--                         <li><a href="#">Feed Label</a></li> -->
<%--                     </c:if> --%>
                </ul>
                <ul class="nav navbar-nav navbar-right">
<!-- 	                <li class="dropdown"> -->
<!-- 	                    <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false"> -->
<!-- 	                        Actions <span class="caret"></span> -->
<!-- 	                    </a> -->
<!-- 	                    <ul class="dropdown-menu"> -->
<!-- 		                    <li><a>Refresh</a></li> -->
<!-- 		                    <li><a>Show Hidden</a></li> -->
<!-- 	                    </ul> -->
<!-- 	                </li> -->
                    <li><a href="#"><sec:authentication property="principal.username"/></a></li>
                    <li><a href="${logoutUrl}">${logoutLabel}</a></li>
                </ul>
            </sec:authorize>
        </div> <!--/.nav-collapse -->
    </div>
</nav>