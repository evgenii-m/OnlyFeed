<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<spring:message var="backActionIconTitle" code="feed.backActionIconTitle"/>
<spring:message var="openOriginalIconTitle" code="feed.openOriginalIconTitle"/>
<spring:message var="showMoreNewsButton" code="feed.showMoreNewsButton"/>


<div class="feed-container">
	<div class="feed-list feed-item-list">
    </div>
    <button class="btn btn-default show-more-button" type="button" style="display: none;">
        ${showMoreNewsButton}
    </button>
</div>

<div class="feed-tab-panel">
	<div class="tool-pane">
        <div id="feed-details-actions" style="display: none;">
            <div class="float-left">
			    <span id="back-to-tabs-action" class="glyphicon glyphicon-arrow-left action-icon"
                        title="${backActionIconTitle}" aria-hidden="true"></span>
            </div>
            <div class="float-right">
                <a id="open-original-action" class="glyphicon glyphicon-new-window action-icon" 
                        aria-hidden="true" target="_blank" title="${openOriginalIconTitle}"></a>
			    <span id="add-tab-action" class="glyphicon glyphicon-plus action-icon"
			            aria-hidden="true"></span>
		    </div>
        </div>
	</div>
	
	<ul class="feed-tab-list sortable" aria-dropeffect="move">
	</ul>
	
	<div class="feed-item-details" style="display: none;">
	    <div class="pub-info">
	        <span class="source-name"></span>
	        <span class="author"></span>
	        <span class="published-date"></span>
	    </div>
	    <div class="title"></div>
	    <div class="description"></div>
	</div>
</div>
	

<script type="text/javascript">
    var pageSize = "${pageSize}";
    var feedViewType = "${feedViewType}";
    var feedSortingType = "${feedSortingType}";
    var feedFilterType = "${feedFilterType}";

    var feedUrl = "<spring:url value='/feed/'/>";
    var feedSettingsUrl = "<spring:url value='/feed/settings/'/>";
    var feedItemUrl = "<spring:url value='/feed/item/'/>";
    var feedTabUrl = "<spring:url value='/feed/tab/'/>";
    var feedTabMoveUrl = "<spring:url value='/feed/tab/move/'/>";

    var refreshErrorMessage = "<spring:message code='feed.refreshErrorMessage'/>";
    var noFeedItemsMessage = "<spring:message code='feed.noFeedItemsMessage'/>";
    var noFeedTabsMessage = "<spring:message code='feed.noFeedTabsMessage'/>";
    var addTabActionIconTitle = "<spring:message code='feed.addTabActionIconTitle'/>";
    var removeTabActionIconTitle = "<spring:message code='feed.removeTabActionIconTitle'/>";
    var refreshIconTitle = "<spring:message code='header.refreshIconTitle'/>";
    var settingsIconTitle = "<spring:message code='header.settingsIconTitle'/>";
    var viewLabel = "<spring:message code='header.viewLabel'/>";
    var compactViewLabel = "<spring:message code='header.compactViewLabel'/>";
    var extendedViewLabel = "<spring:message code='header.extendedViewLabel'/>";
    var sortingLabel = "<spring:message code='header.sortingLabel'/>";
    var newestSortingLabel = "<spring:message code='header.newestSortingLabel'/>";
    var oldestSortingLabel = "<spring:message code='header.oldestSortingLabel'/>";
    var filterLabel = "<spring:message code='header.filterLabel'/>";
    var allFilterLabel = "<spring:message code='header.allFilterLabel'/>";
    var unreadFilterLabel = "<spring:message code='header.unreadFilterLabel'/>";
    var readFilterLabel = "<spring:message code='header.readFilterLabel'/>";
    var latestDayFilterLabel = "<spring:message code='header.latestDayFilterLabel'/>";
    
    $(document).ready(function() {
    	setFeedToolPane();
    	displayFeedItems();
        displayFeedTabList();
	});
</script>
