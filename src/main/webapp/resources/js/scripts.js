$(document).ready(function() {
	$("#select-lang-en").click(function() {
		$.get("?lang=en_US", function() {
			location.reload();
		});
	});

	$("#select-lang-ru").click(function() {
		$.get("?lang=ru_RU", function() {
			location.reload();
		});
	});
});


function displayFeedItems() {
	refreshFeed();
	
	if (feedPanelPosition == true) {
		pinFeedPanel();
		$(".feed-tab-panel").show();
	} else {
		unpinFeedPanel();
	}
	
	$(".feed-item-list").on("click", ".item", function() {
		var feedItemId = $(this).attr("id").replace(/\D/g, '');
		$("#back-to-tabs-action").hide();
		displayFeedItemDetails(feedItemId);
	});
	
    $(".feed-item-list").on("mouseenter", ".item", function() {
    	var itemTitle = $(this).children(".feed-content").children(".feed-info").children(".title");
    	itemTitle.css("color", "#26B3FF");
    });
    $(".feed-item-list").on("mouseleave", ".item", function() {
    	var itemTitle = $(this).children(".feed-content").children(".feed-info").children(".title");
    	itemTitle.css("color", "#3D3D3D");
    });
    
    $(".show-more-button").click(function() {
        var pageIndex = Math.ceil($(".feed-item-list").children(".item").length / pageSize);
    	showFeedItemsByPage(pageIndex);
    });
    
    
    $(".panel-backdrop").click(hideFeedPanel);
    $(".close-feed-panel-action").click(hideFeedPanel);
    $("#back-to-tabs-action").click(showTabPanel);
    
    $(".pin-feed-panel-action").click(function() {
		feedPanelPosition = !feedPanelPosition;
    	$.ajax({
    		url: feedSettingsUrl + "panelpos/",
    		type: "post",
    		data: { "feedPanelPosition" : feedPanelPosition },
    		success: function(response) {
    			if (response == true) {
    				if (feedPanelPosition == true) {
    					pinFeedPanel();
    				} else {
    					unpinFeedPanel();
    				}
    			} else {
    				console.log("Failed to change feed panel position.");
    			}
    		},
    		error: serverError
    	});
    });
    
    $("#add-tab-action").click(function() {
    	var feedItemId = $(".feed-details-panel").attr("id").replace(/\D/g, '');
    	if ($(this).hasClass("action-icon-blocked") != true) {
	    	addFeedTab(feedItemId);
    	} else {
    		var feedTab = $(".feed-tab-list").find(".feed-tab#ft-" + feedItemId);
    		if (feedTab.length != 0) {
    			removeFeedTab(feedTab);
    			$("#add-tab-action").removeClass("action-icon-blocked");
    			$("#add-tab-action").attr("title", addTabActionIconTitle);         			
    		} else {
    			console.log("Error when remove feed tab");
    		}
    	}
    });
    
    $("#remove-all-tabs-action").click(function() {
    	$.ajax({
			url: feedTabUrl,
			type: "delete",
			success: function(response) {
				if (response == true) {
					$(".feed-tab-list").empty();
				    $("ul.sortable").sortable("reload");	
				} else {
					console.log("Error when add feed tab");
				}
			},
			error: serverError
    	});
    });
}


function pinFeedPanel() {
	$(".feed-container").addClass("narrow");
	$(".feed-panel").addClass("pinned-panel");
	$(".pin-feed-panel-action").addClass("action-icon-blocked");
	$(".close-feed-panel-action").hide();
	$(".panel-backdrop").hide();
	$("body").css("overflow", "auto");
}


function unpinFeedPanel() {
	$(".feed-container").removeClass("narrow");
	$(".feed-panel").removeClass("pinned-panel");
	$(".pin-feed-panel-action").removeClass("action-icon-blocked");	
	$(".close-feed-panel-action").show();
	$(".feed-panel").hide();
} 


function showFeedItemsByPage(pageIndex) {
    $.ajax({
        url: window.location + "/page/" + pageIndex,
        type: "get",
        success: function(response) {
	        if (response != "") {
	        	response.forEach(function(entry) {
        			appendFeedItem(entry);
		        });
	        	if (response.length < pageSize) {
	        		$(".show-more-button").hide();
	        		$(document).off("scroll");
	        	} else {
	        		$(".show-more-button").show();
	        	    $(document).scroll(feedScrolling);
	        	}
	        } else {
        		$(".show-more-button").hide();
        		$(document).off("scroll");
	        }
	        if ($(".feed-item-list").children().size() == 0) {
	    		$("<span/>", {
	    			class: "alert alert-warning",
	    			role: "alert",
	    			html: noFeedItemsMessage
	    		}).appendTo($(".feed-container"));
	        }
        },
        error: serverError
    });
}


function appendFeedItem(feedItem) {
	if (feedViewType == 0) {
		$(".feed-item-list").addClass("compact");
	}
	var item = $("<div/>", {
        id: "fi-" + feedItem.id,
        class: "item"
    }).appendTo($(".feed-item-list"));
	if (feedItem.viewed == true) {
		item.addClass("viewed");
	}
		$("<div/>", {
			style: "background-image: url(" + feedItem.imageUrl + ");",
			class: "feed-logo"
		}).appendTo(item);
		var feedContent = $("<div/>", {
			class: "feed-content"
		}).appendTo(item);
			var feedInfo = $("<div/>", {
				class: "feed-info"
			}).appendTo(feedContent);
				var title = $("<div/>", {
					class: "title"
				}).appendTo(feedInfo);
					$("<span/>", {
						text: feedItem.title
					}).appendTo(title);
				$("<div/>", {
					class: "summary",
					text: feedItem.summary
				}).appendTo(feedInfo);
			var pubInfo = $("<div/>", {
				class: "pub-info"
			}).appendTo(feedContent);
				$("<span/>", {
					class: "source-name",
					text: feedItem.feedSource.name
				}).appendTo(pubInfo);
				$("<span/>", {
					class: "published-date",
					text: "\u00A0|\u00A0" + feedItem.publishedDateString
				}).appendTo(pubInfo);
}


function displayFeedItemDetails(feedItemId) {
    $.ajax({
		url: feedItemUrl + feedItemId,
        type: "get",
        success: function(response) {
        	var feedItem = response;
        	if (feedItem != "") {
        		$("#fi-" + feedItemId).addClass("viewed");
        		$(".feed-details-panel").attr("id", "fid-" + feedItem.id);
        		var panelContent = $(".feed-details-panel").children(".panel-content");
        		panelContent.children(".title").text(feedItem.title);
        		var pubInfo = panelContent.children(".pub-info"); 
        		pubInfo.children(".source-name").children("a").text(feedItem.feedSource.name);
        		pubInfo.children(".source-name").children("a").attr("href", feedUrl + feedItem.feedSource.id);
        		if (feedItem.author != null) {
        			pubInfo.children(".author").html("\u00A0| by <i>" + feedItem.author + "</i>\u00A0");
        		}
        		pubInfo.children(".published-date").text("\u00A0|\u00A0" + feedItem.publishedDateString);
        		panelContent.children(".description").html(feedItem.description);

        		if (feedPanelPosition == false) {
	        		$(".panel-backdrop").show();
	        		$("body").css("overflow", "hidden");
        		}
        		$(".feed-tab-panel").hide();
        		$(".feed-details-panel").show();
        		$(".feed-details-panel").children(".panel-content").scrollTop(0);
        		
            	$("#open-original-action").attr("href", feedItem.link);
        		if ($(".feed-tab-list").find(".feed-tab#ft-" + feedItemId).length != 0) {
        			$("#add-tab-action").addClass("action-icon-blocked");
        			$("#add-tab-action").attr("title", removeTabActionIconTitle);
        		} else {
        			$("#add-tab-action").removeClass("action-icon-blocked");
        			$("#add-tab-action").attr("title", addTabActionIconTitle);
        		}
        	} else {
        		console.log("feedItem not found");
        	}
        },
        error: serverError
	});
}


function feedScrolling() {
	var currentHeight = $(".feed-container").height();
	
	if ($(this).scrollTop() >= (currentHeight - $(window).height() - 100)) {
		$(document).off("scroll");
        var pageIndex = Math.ceil($(".feed-item-list").children(".item").length / pageSize);
    	showFeedItemsByPage(pageIndex);
	}
}




function hideFeedPanel() {
	$(".panel-backdrop").hide();
	$(".feed-panel").hide();
	$("body").css("overflow", "auto");
}



function prepareFeedTabPanel() {
    $.ajax({
	    url: feedTabUrl,
	    type: "get",
	    success: function(response) {
	        if (response != "") {
		        response.forEach(function(entry) {
		        	appendFeedTabToList(entry);
		        });   
	        } else {
//	    		$("<span/>", {
//	    			html: noFeedTabsMessage
//	    		}).appendTo($(".feed-tab-panel"));
	        }
	        $("ul.sortable").sortable({
	            forcePlaceholderSize: true
	        }).bind("sortupdate", function(e, ui) {
	        	moveFeedTab(ui.oldElementIndex, ui.elementIndex);
	        });
		},
	    error: serverError
    });
    

    $(".feed-tab-list").on("mouseenter", ".feed-tab", function() {
    	var feedTabText = $(this).children(".feed-tab-text");
    	feedTabText.width(feedTabText.width() - 30);
    	$(this).children(".remove-action-icon").show();    	
    });
    $(".feed-tab-list").on("mouseleave", ".feed-tab", function() {
    	var feedTabText = $(this).children(".feed-tab-text");
    	feedTabText.width(feedTabText.width() + 30);
    	$(this).children(".remove-action-icon").hide();	     
    });
    
    $(".feed-tab-list").on("click", ".feed-tab-text", function() {
        var feedItemId = $(this).parents(".feed-tab").attr("id").replace(/\D/g, '');
        $("#back-to-tabs-action").show();
        displayFeedItemDetails(feedItemId);
    });
    
    $(".feed-tab-list").on("click", ".remove-action-icon", function() {
        var feedTab = $(this).parents(".feed-tab");
        removeFeedTab(feedTab);
    });
}


function appendFeedTabToList(feedItem) {
    var feedTab = $("<li/>", {
        id: "ft-" + feedItem.id,
        class: "feed-tab",
        draggable: true
    }).appendTo($(".feed-tab-list"));
	    var feedTabText = $("<div/>", {
	    	class: "feed-tab-text"
	    }).appendTo(feedTab);
		    $("<span/>", {
		        class: "source-name",
		        text: feedItem.feedSource.name
		    }).appendTo(feedTabText);
		    $("<span/>", {
		        class: "title",
		        text: "\u00A0|\u00A0" + feedItem.title
		    }).appendTo(feedTabText);
	    $("<span/>", {
	    	class: "glyphicon glyphicon-remove remove-action-icon",
	    	title: removeTabActionIconTitle
	    }).appendTo(feedTab);
}



function addFeedTab(feedItemId) {
	$.ajax({
		url: feedTabUrl,
		type: "post",
		data: { "feedItemId" : feedItemId },
		success: function(response) {
			if (response != null) {
				appendFeedTabToList(response);
			    $("ul.sortable").sortable("reload");
    			$("#add-tab-action").addClass("action-icon-blocked");
    			$("#add-tab-action").attr("title", removeTabActionIconTitle);
			} else {
				console.log("Error when add feed tab");
			}
		},
		error: serverError
	});
}


function removeFeedTab(feedTab) {
	var feedItemId = feedTab.attr("id").replace(/\D/g, '');
	$.ajax({
		url: feedTabUrl + feedItemId,
		type: "delete",
		success: function(response) {
			if (response == true) {
				feedTab.remove();
			    $("ul.sortable").sortable("reload");
			} else {
    			console.log("Error when remove feed tab");
    		}
		},
		error: serverError
	});	
}


function moveFeedTab(tabOldIndex, tabNewIndex) {
	$.ajax({
		url: feedTabMoveUrl,
		method: "post",
		data: {
			"tabOldIndex" : tabOldIndex,
			"tabNewIndex" : tabNewIndex
		},
		success: function(response) {
			if (response != true) {
				console.log("Error when moved feedTab!");
			}
		},
		error: serverError
	});
}




function setFeedToolPane() {
	var navbarSource = $("<ul/>", {
		class: "nav navbar-nav navbar-source"
	}).appendTo($("#navbar"));
	var navbarSourceLi = $("<li/>").appendTo(navbarSource);
	if (currentFeedSourceName == "") {
		navbarSourceLi.text(showAllSourcesLabel);
	} else {
		navbarSourceLi.text(currentFeedSourceName);
	}
	navbarSourceLi.click(function() {
		window.location.href = sourceUrl;		
	});
    
	var navbarToolPane = $("ul.navbar-nav.toolpane");
	navbarToolPane.append('<li id="refresh-action" class="glyphicon glyphicon-refresh action-icon" '
			+ 'title=' + refreshIconTitle + ' aria-hidden="true"></li>');
	navbarToolPane.append('<li id="show-tab-panel-action" class="glyphicon glyphicon-bookmark action-icon" '
			+ 'title=' + '" "' + ' aria-hidden="true"></li>');
	
	var dropdown = $('<li class="dropdown">').appendTo(navbarToolPane);
	dropdown.append('<span class="dropdown-toggle glyphicon glyphicon-cog action-icon" role="button" ' 
			+ 'title=' + settingsIconTitle + ' data-toggle="dropdown" aria-hidden="true" aria-haspopup="true" '
			+ 'aria-expanded="true"></span>');
	var settingsMenu = $('<ul id="settings-menu" class="dropdown-menu">').appendTo(dropdown);
	settingsMenu.append('<li class="dropdown-header">' + viewLabel + '</li>');
	settingsMenu.append('<li id="view-type-0" class="view-type-item">' + compactViewLabel + '</li>');
	settingsMenu.append('<li id="view-type-1" class="view-type-item">' + extendedViewLabel + '</li>');
	settingsMenu.append('<li class="dropdown-header">' + sortingLabel + '</li>');
	settingsMenu.append('<li id="sorting-type-0" class="sorting-type-item">' + newestSortingLabel + '</li>');
	settingsMenu.append('<li id="sorting-type-1" class="sorting-type-item">' + oldestSortingLabel + '</li>');
	settingsMenu.append('<li class="dropdown-header">' + filterLabel + '</li>');
	settingsMenu.append('<li id="filter-type-0" class="filter-type-item">' + allFilterLabel + '</li>');
	settingsMenu.append('<li id="filter-type-1" class="filter-type-item">' + unreadFilterLabel + '</li>');
	settingsMenu.append('<li id="filter-type-2" class="filter-type-item">' + readFilterLabel + '</li>');
	settingsMenu.append('<li id="filter-type-3" class="filter-type-item">' + latestDayFilterLabel + '</li>');
	
	$("#view-type-" + feedViewType).addClass("selected");
	$("#sorting-type-" + feedSortingType).addClass("selected");
	$("#filter-type-" + feedFilterType).addClass("selected");
	
	$("#refresh-action").click(refreshFeed);
	
	$("#show-tab-panel-action").click(showTabPanel);
	
	$(".view-type-item").click(function() {
		changeFeedViewType($(this).attr("id").replace(/\D/g, ''));
	});
	
	$(".sorting-type-item").click(function() {
		changeFeedSortingType($(this).attr("id").replace(/\D/g, ''));
	});
	
	$(".filter-type-item").click(function() {
		changeFeedFilterType($(this).attr("id").replace(/\D/g, ''));
	});
	
}


function showTabPanel() {
	if (feedPanelPosition == false) {
		$("body").css("overflow", "hidden");
		$(".panel-backdrop").show();
	}
	$(".feed-details-panel").hide();
	$(".feed-tab-panel").show();
	$(".feed-tab-panel").children(".panel-content").scrollTop(0);
}


function changeFeedViewType(newFeedViewType) {
	if (newFeedViewType != feedViewType) {
		$.ajax({
			url: feedSettingsUrl + "view/",
			type: "post",
			data: { "feedViewType" : newFeedViewType },
			success: function(response) {
				if (response == true) {
					$("#view-type-" + feedViewType).removeClass("selected");
					feedViewType = newFeedViewType;
					$("#view-type-" + feedViewType).addClass("selected");
					if (feedViewType == 0) {
						$(".feed-item-list").addClass("compact");
					} else {
						$(".feed-item-list").removeClass("compact");
					}						
				}
			},
			error: serverError
		});
	}
}


function changeFeedSortingType(newFeedSortingType) {
	if (newFeedSortingType != feedSortingType) {
		$.ajax({
			url: feedSettingsUrl + "sorting/",
			type: "post",
			data: { "feedSortingType" : newFeedSortingType },
			success: function(response) {
				if (response == true) {
					$("#sorting-type-" + feedSortingType).removeClass("selected");
					feedSortingType = newFeedSortingType;
					$("#sorting-type-" + feedSortingType).addClass("selected");
					refreshFeed();
				} else {
					console.log("Failed to change feed sorting type");
				}
			},
			error: serverError
		});
	}
}


function changeFeedFilterType(newFeedFilterType) {
	if (newFeedFilterType != feedFilterType) {
		$.ajax({
			url: feedSettingsUrl + "filter/",
			type: "post",
			data: { "feedFilterType" : newFeedFilterType },
			success: function(response) {
				if (response == true) {
					$("#filter-type-" + feedFilterType).removeClass("selected");
					feedFilterType = newFeedFilterType;
					$("#filter-type-" + feedFilterType).addClass("selected");
					refreshFeed();
				} else {
					console.log("Failed to change feed filter type");
				}
			},
			error: serverError
		});
	}
}


function resetFeedItemList() {
	if ($(".loading-indicator").length != 0) {
		return false;   // previous download does not complete
	}
	$(document).off("scroll");
	$(".feed-container .alert-warning").remove();
	$(".show-more-button").hide();
	$(".feed-item-list").empty();
	$(".feed-container").append($("<div/>", { 
		class: "loading-indicator",
		text: "Loading..."
	}));
	return true;
}


function refreshFeed() {	
	if (resetFeedItemList()) {
		$.ajax({
			url: feedRefreshUrl,
			type: "post",
			data: { "feedSourceId" : currentFeedSourceId },
			success: function(response) {
				$(".loading-indicator").remove();
				if (response != true) {
					var warning = $("<div/>", {
						class: "alert alert-warning alert-dismissible",
						role: "alert"
					});
					warning.append('<button type="button" class="close" data-dismiss="alert" aria-label="Close">'
							+ '<span aria-hidden="true">&times;</span></button>');
					warning.append(refreshErrorMessage);
					$(".feed-item-list").before(warning);
				}
				showFeedItemsByPage(0);
			},
			error: serverError
		});
	}
}





function deleteUserPicture() {
	$(".update-alert").remove();
	$.ajax({
		url: updatePictureUrl,
		method: "delete",
		success: function(response) {
			if (response == null) {
                var errorAlert = $("<div/>", {
                	class: "alert alert-danger update-alert",
                	html: deletePictureErrorMessage
                });
                $("#user-picture-form").before(errorAlert);
				console.log("Error when delete user picture!");
				return;
			}
			$(".user-picture").css("background-image", "url(" + response + ")");
            var successAlert = $("<div/>", {
            	class: "alert alert-success update-alert",
            	html: deletePictureSuccessMessage
            });
            $("#user-picture-form").before(successAlert);
		},
		error: serverError
	});	
}


function removeUser() {
	$.ajax({
		url: userUrl,
		type: "delete",
		success: function() {
			console.log("delete success!");
			window.location.href = indexUrl;
		},
		error: serverError
	});
}



function serverError(error) {
	console.log("Server error");
	console.log(error);	
}

