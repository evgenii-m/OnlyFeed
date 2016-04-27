function displayFeedItems() {
	refreshFeed();
	
	$(".feed-item-list").on("click", ".item", function() {
		var feedItemId = $(this).attr("id").replace(/\D/g, '');
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
	        	} else {
	        		$(".show-more-button").show();
	        	}
	        } else {
	    		$("<span/>", {
	    			class: "alert alert-warning",
	    			role: "alert",
	    			html: noFeedItemsMessage
	    		}).appendTo($(".feed-container"));
	        }
        },
        error: function(error) {
            console.log("Server error");
            console.log(error);        	
        }
    });
}



function appendFeedItem(feedItem) {
	var item = $("<div/>", {
        id: "fi-" +feedItem.id,
        class: "item"
    }).appendTo($(".feed-item-list"));
	if (feedViewType == 0) {
		$(".feed-item-list").addClass("compact");
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
        	if (feedItem != null) {
        		$(".feed-item-details").attr("id", "fid-" + feedItem.id);
        		$(".feed-item-details").children(".title").text(feedItem.title);
        		var pubInfo = $(".feed-item-details").children(".pub-info"); 
        		pubInfo.children(".source-name").text(feedItem.feedSource.name);
        		if (feedItem.author != null) {
        			pubInfo.children(".author").html("\u00A0| by <i>" + feedItem.author + "</i>\u00A0");
        		}
        		pubInfo.children(".published-date").text("\u00A0|\u00A0" + feedItem.publishedDateString);
        		$(".feed-item-details").children(".description").html(feedItem.description);
        		$(".feed-item-details").scrollTop(0);

        		$(".feed-tab-list").hide();
        		$(".feed-item-details").show();
            	
        		$("#feed-details-actions").show();
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
        error: function(error) {
            console.log("Server error");
            console.log(error);
        }
	});
}





function displayFeedTabList() {
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
//	    			class: "alert alert-warning",
//	    			role: "alert",
//	    			html: noFeedTabsMessage
//	    		}).appendTo($(".feed-tab-panel"));
	        }
	        $("ul.sortable").sortable({
	            forcePlaceholderSize: true
	        }).bind("sortupdate", function(e, ui) {
	        	moveFeedTab(ui.oldElementIndex, ui.elementIndex);
	        });
		},
	    error: function(error) {
	        console.log("Server error");
	        console.log(error);         
	    }
    });
    

    $(".feed-tab-list").on("mouseenter", ".feed-tab", function() {
    	var feedTabText = $(this).children(".feed-tab-text");
    	feedTabText.width(feedTabText.width() - 26);
    	$(this).children(".remove-action-icon").show();    	
    });
    $(".feed-tab-list").on("mouseleave", ".feed-tab", function() {
    	var feedTabText = $(this).children(".feed-tab-text");
    	feedTabText.width(feedTabText.width() + 26);
    	$(this).children(".remove-action-icon").hide();	     
    });
    
    $(".feed-tab-list").on("click", ".feed-tab-text", function() {
        var feedItemId = $(this).parents(".feed-tab").attr("id").replace(/\D/g, '');
        displayFeedItemDetails(feedItemId);
    });
    
    $(".feed-tab-list").on("click", ".remove-action-icon", function() {
        var feedTab = $(this).parents(".feed-tab");
        removeFeedTab(feedTab);
    });

    $("#back-to-tabs-action").click(function() {
    	$(".feed-item-details").hide();
    	$("#feed-details-actions").hide();
    	$(".feed-tab-list").show();
    });
    
    $("#add-tab-action").click(function() {
    	var feedItemId = $(".feed-item-details").attr("id").replace(/\D/g, '');
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
		error: function(error) {
			console.log("Server error");
			console.log(error);
		}
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
		error: function(error) {
            console.log("Server error");
            console.log(error);
		}
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
		error: function(error) {
	        console.log("Server error");
	        console.log(error);	        			
		}
	});
}




function setFeedToolPane() {
	var navbarToolPane = $("ul.navbar-nav.tool-pane");
	navbarToolPane.append('<li id="refresh-action" class="glyphicon glyphicon-refresh action-icon" '
			+ 'title=' + refreshIconTitle + ' aria-hidden="true"></li>');
	
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
	
	$("#refresh-action").click(function() {
		refreshFeed();
	});
	
	$(".view-type-item").click(function() {
		changeFeedViewType($(this).attr("id").replace(/\D/g, ''));
	});
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
			error: function(error) {
				console.log("Server error");
				console.log(error);				
			}
		});
	}
}


function refreshFeed() {
	if ($(".loading-indicator").length != 0) {
		return;
	}
	
	$(".feed-container .alert-warning").remove();
	$(".show-more-button").hide();
	$(".feed-item-list").empty();
	$(".feed-container").append($("<div/>", { 
		class: "loading-indicator",
		text: "Loading..."
	}));
	$.ajax({
		url: window.location + "/refresh",
		type: "post",
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
		error: function(error) {
			console.log("Server error");
			console.log(error);			
		}
	});
}



function handleFileSelect(event) {
    var file = event.target.files[0];
    // Only process image files
    if (!file.type.match('image.*')) {
    	console.log("Selected file not image");
    	return;
    }

    // Closure to capture the file information
    var reader = new FileReader();
    reader.onload = (function(theFile) {
    	return function(e) {
    		// Render picture
    		$(".picture-thumbnail").attr("src", e.target.result);
    		$(".picture-thumbnail").attr("title", escape(theFile.name));
    	};
    })(file);

    // Read in the image file as a data URL.
    reader.readAsDataURL(file);
}


function deleteUserPicture() {
	$(".picture-alert").remove();
	$.ajax({
		url: updatePictureUrl,
		method: "delete",
		success: function(response) {
			if (response == null) {
                var errorAlert = $("<div/>", {
                	class: "alert alert-danger picture-alert",
                	html: deletePictureErrorMessage
                });
                $("#user-picture-form").before(errorAlert);
				console.log("Error when delete user picture!");
				return;
			}
			$(".picture-thumbnail").attr("src", response);
			$(".user-picture").css("background-image", "url(" + response + ")");
            var successAlert = $("<div/>", {
            	class: "alert alert-success picture-alert",
            	html: deletePictureSuccessMessage
            });
            $("#user-picture-form").before(successAlert);
		},
		error: function(error) {
	        console.log("Server error");
	        console.log(error);	        			
		}
	});	
}

