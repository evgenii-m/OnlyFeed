/**
 * 
 */

$(document).ready(function() {
});


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
    	$(this).children(".remove-link").show();    	
    });
    $(".feed-tab-list").on("mouseleave", ".feed-tab", function() {
    	var feedTabText = $(this).children(".feed-tab-text");
    	feedTabText.width(feedTabText.width() + 26);
    	$(this).children(".remove-link").hide();	     
    });
    
    $(".feed-tab-list").on("click", ".feed-tab-text", function() {
        var feedItemId = $(this).parents(".feed-tab").attr("id").replace(/\D/g, '');
        displayFeedItemDetails(feedItemId);
    });
    
    $(".feed-tab-list").on("click", ".remove-link", function() {
        var feedTab = $(this).parents(".feed-tab");
        removeFeedTab(feedTab);
    });

    $("#back-link").click(function() {
    	$(".feed-item-details").hide();
    	$("#feed-details-links").hide();
    	$(".feed-tab-list").show();
    });
    
    $("#add-tab-link").click(function() {
    	if ($(this).hasClass("link-blocked") != true) {
	    	var feedItemId = $(".feed-item-details").attr("id").replace(/\D/g, '');
	    	addFeedTab(feedItemId);
    	}
    });
}


function appendFeedTabToList(feedItem) {
    var feedTab = $("<li/>", {
        id: "ft-" +feedItem.id,
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
    	class: "glyphicon glyphicon-remove remove-link",
    	title: removeTabLinkTitle
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
    			$("#add-tab-link").addClass("link-blocked");
    			$("#add-tab-link").attr("title", tabAddedLinkTitle);
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
			feedTab.remove();
		    $("ul.sortable").sortable("reload");
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
            	$("#feed-details-links").show();
        		if ($(".feed-tab-list").find(".feed-tab#ft-" + feedItemId).length != 0) {
        			$("#add-tab-link").addClass("link-blocked");
        			$("#add-tab-link").attr("title", tabAddedLinkTitle);
        		} else {
        			$("#add-tab-link").removeClass("link-blocked");
        			$("#add-tab-link").attr("title", addTabLinkTitle);        			
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

