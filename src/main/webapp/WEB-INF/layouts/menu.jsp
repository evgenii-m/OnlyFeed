<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>


<div id="menu">
    <form:form commandName="feedChannelBlank">
        <div>
            <label for="name">Name</label>
            <form:input path="name"/>
        </div>
        <div>
            <label for="url">URL</label>
            <form:input path="url"/>
        </div>
        <input type="submit" value="Add"/>
    </form:form>
</div>