<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>

<spring:message var="applicationName" code="applicationName" htmlEscape="false"/>

<spring:url var="bootstrapCssUrl" value="/resources/css/bootstrap.css"/>
<spring:url var="standardCssUrl" value="/resources/css/standard.css"/>


<!DOCTYPE HTML>

<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html;charset=UTF-8">
        <meta http-equiv="X-UA-Compatible" content="IE=8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link rel="stylesheet" href="${bootstrapCssUrl}" type="text/css" media="screen"/>
        <link rel="stylesheet" href="${standardCssUrl}" type="text/css" media="screen"/>
        <title>${applicationName}</title>
	</head>

	<body>
        <div class="container">
			<div id="headerWrapper">
	            <tiles:insertAttribute name="header" ignore="true"/>
			</div>
			<div id="wrapper">
	            <tiles:insertAttribute name="menu" ignore="true"/>
				<div id="main">
					<tiles:insertAttribute name="body"/>
					<tiles:insertAttribute name="footer" ignore="true"/>
				</div>
			</div>
		</div>
	</body>
</html>