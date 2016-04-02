<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>

<!DOCTYPE HTML>

<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html;charset=UTF-8">
        <meta http-equiv="X-UA-Compatible" content="IE=8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link rel="stylesheet" href="<spring:url value="/resources/css/bootstrap.css"/>" type="text/css" media="screen"/>
        <link rel="stylesheet" href="<spring:url value="/resources/css/standard.css"/>" type="text/css" media="screen"/>
        <script src="<spring:url value="/resources/js/jquery/jquery-2.2.2.js"/>"></script>
        <script src="<spring:url value="/resources/js/jquery/validate/1.15.0/jquery.validate.js"/>"></script>
        <script src="<spring:url value="/resources/js/bootstrap.js"/>"></script>
        <script src="<spring:url value="/resources/js/scripts.js"/>"></script>
        <title>Simple Feed</title>
        <script type="text/javascript">
	        $(document).ready(function() {	
	            $.extend($.validator.messages, {
	                required: '<spring:message code="validation.required"/>',
	                minlength: '<spring:message code="validation.minlength"/>',
	                maxlength: '<spring:message code="validation.minlength"/>',
	                url: '<spring:message code="validation.url"/>',
	                email: '<spring:message code="validation.email"/>',
	                equalTo: '<spring:message code="validation.equalTo"/>'
	            });
	        });
        </script>
	</head>

	<body>
        <tiles:insertAttribute name="header"/>
		<div class="container">
			<tiles:insertAttribute name="body"/>
		</div>
	</body>
</html>