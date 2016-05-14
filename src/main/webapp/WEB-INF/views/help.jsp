<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<spring:url var="feedUrl" value="/feed/"/>
<spring:url var="sourceUrl" value="/source/"/>

<div class="feed-container">
    <h2 style="margin-bottom: 30px;">Добро пожаловать на OnlyFeed!</h2>
    <div class="help-container">
        <h4>OnlyFeed собирает все новости в одном месте</h4> 
        Чтобы начать пользоваться сервисом, перейдите в раздел <a href="${sourceUrl}">Источники новстей</a> и добавьте необходимые 
        источники. Чтобы добавить новый источник, укажите соответствующий URL (поддерживаются источники RSS и Atom), либо кликните 
        по понравившемуся источнику из раздела "Интересные каналы".
        <br>
        Вы можете просматривать общую ленту всех источников на <a href="${feedUrl}">Домашней странице</a>, либо 
        выбрать конкретный источник, кликнув по его названию.
        <br>
        <br>
        <h4>Вы можете сохранять новости во вкладках</h4>
        Для этого в окне просмотра новости кликните на иконку <span class="glyphicon glyphicon-plus" aria-hidden="true"></span>.
        После этого новость будет доступна на панели вкладок, и вы сможете вернуться к ней в любой момент. Чтобы открыть панель 
        вкладок, кликните на иконку <span class="glyphicon glyphicon-bookmark" aria-hidden="true"></span> в шапке сайта. 
        Также вы можете закрепить панель вкладок на странице, кликнув на иконку <span class="glyphicon glyphicon-pushpin" aria-hidden="true"></span>.
        <br>
        <br>
        <h4>Вы можете настраивать новостную ленту</h4>
        Для этого кликните по иконке <span class="glyphicon glyphicon-cog" aria-hidden="true"></span> в шапке сайта. В выпадающем меню
        вы можете изменить вид отображаемой ленты, установить сортировку и фильтры для новостей.
        <br>
        <br>
        <br>
        При возникновении каких-либо вопросов или пожеланий, обращайтесь по адресу support@onlyfeed.ru
    </div>
</div>