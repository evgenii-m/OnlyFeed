<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<spring:message var="projectName" code="header.projectName"/>


<nav class="navbar navbar-inverse navbar-fixed-top">
    <div class="container">
        <div class="navbar-header">
            <a class="navbar-brand" href="#">${projectName}</a>
        </div>
        <div id="navbar" class="navbar-collapse collapse">
	        <ul class="nav navbar-nav">	           
                <li><a href="#">Feed Label</a></li>
            </ul>
	        <ul class="nav navbar-nav navbar-right">
                <li class="dropdown">
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">
                        Actions <span class="caret"></span>
                    </a>
                    <ul class="dropdown-menu">
	                    <li><a>Refresh</a></li>
	                    <li><a>Show Hidden</a></li>
                    </ul>
                </li>
		        <li><a href="#">Feed Sources</a></li>
		        <li><a href="#">User Name</a></li>
	        </ul>
        </div><!--/.nav-collapse -->
    </div>
</nav>