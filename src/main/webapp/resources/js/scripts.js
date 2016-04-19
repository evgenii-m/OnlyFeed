function displayFeedItems() {
	showMoreFeedItems(0);

    $(".feed-item-list").on("click", ".select-item-action", function() {
    	var feedItemId = $(this).parents(".item").attr("id").replace(/\D/g, '');
    	displayFeedItemDetails(feedItemId);
    });
    
    $(".show-more-button").click(function() {
        var pageIndex = ($(".feed-item-list").children(".item").length / pageSize) + 1;
        console.log(pageIndex);
    	showMoreFeedItems(pageIndex);
    });
}


function showMoreFeedItems(pageIndex) {
    $.ajax({
        url: window.location + "/page/" + pageIndex,
        type: "get",
        success: function(response) {
	        if (response != null) {
	        	response.forEach(function(entry) {
	        		appendFeedItem(entry);
		        });
	        } else {
	        	console.log("Failed to get feed items page");
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
						class: "select-item-action",
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



function displayFeedTabList() {
    $.ajax({
	    url: feedTabUrl,
	    type: "get",
	    success: function(response) {
	        response.forEach(function(entry) {
	        	appendFeedTabToList(entry);
	        });
	        
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

    $("#back-action").click(function() {
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
	$.ajax({
		url: feedTabUrl + feedTab.index(),
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
