<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<spring:message var="copyrightLabel" code="footer.copyrightLabel"/>
<spring:message var="helpLink" code="footer.helpLink"/>
<spring:message var="langEnLink" code="footer.langEnLink"/>
<spring:message var="langRuLink" code="footer.langRuLink"/>

<spring:url var="helpUrl" value="/help/"/>

<div class="footer">
    <span>${copyrightLabel}</span>
    <a href="${helpUrl}">${helpLink}</a>
    <a id="select-lang-en">${langEnLink}</a>
    <a id="select-lang-ru">${langRuLink}</a>
</div>