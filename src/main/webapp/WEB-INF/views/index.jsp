<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>

<spring:message var="appName" code="application_name" htmlEscape="false"/>
<spring:message var="labelWelcome" code="welcome_label" arguments="${appName}"/>

<h3>${labelWelcome}</h3>