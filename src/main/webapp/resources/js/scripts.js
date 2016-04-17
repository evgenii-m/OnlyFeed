/**
 * 
 */

$(document).ready(function() {
});


function displayFeedTabList() {
    $.ajax({
	    url: "feed/tab",
	    type: "get",
	    success: function(response) {
	    	$(".feed-tab-detail").hide();
	        response.forEach(function(entry) {
	        	addFeedTab(entry);
	        });
	        $("ul.sortable").sortable({
	            forcePlaceholderSize: true
	        }).bind("sortupdate", function(e, ui) {
	        	$.ajax({
	        		url: "feed/tab/move",
	        		method: "post",
	        		data: {
	        			"tabOldIndex" : ui.oldElementIndex,
	        			"tabNewIndex" : ui.elementIndex
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
	        });
		},
	    error: function(error) {
	        console.log("Server error");
	        console.log(error);         
	    }
    });
    

    $(".feed-tab-list").on("mouseenter", ".feed-tab", function() {
    	$(this).children(".remove-link").show();    	
    });
    $(".feed-tab-list").on("mouseleave", ".feed-tab", function() {
    	$(this).children(".remove-link").hide();	     
    });
    
    $(".feed-tab-list").on("click", ".feed-tab-text", function() {
        var feedItemId = $(this).parents(".feed-tab").attr("id");
        $.ajax({
    		url: "feed/item/" + feedItemId,
            type: "get",
            success: function(response) {
            	selectFeedTab(response);
            },
            error: function(error) {
                console.log("Server error");
                console.log(error);         
            }
    	});
    });
    
    $(".feed-tab-list").on("click", ".remove-link", function() {
        var feedTab = $(this).parents(".feed-tab");
    	$.ajax({
    		url: "feed/tab/" + feedTab.index(),
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
    });

    $("#back-link").click(function() {
    	$(".feed-tab-detail").hide();
    	$(".feed-tab-list").show();
    	$("#back-link").hide();
    });
}


function appendFeedItemToTabs(feedItemId) {
	if ($(".feed-tab-list").find(".feed-tab#" + feedItemId).length == 0) {
    	$.ajax({
    		url: "feed/tab",
    		type: "post",
    		data: { "feedItemId" : feedItemId },
    		success: function(response) {
    			addFeedTab(response);
    		    $("ul.sortable").sortable("reload");
    			selectFeedTab(response);
    		},
    		error: function(error) {
    			console.log("Server error");
    			console.log(error);
    		}
    	});
	} else {  // tab already added
        $.ajax({
    		url: "feed/item/" + feedItemId,
            type: "get",
            success: function(response) {
            	selectFeedTab(response);
            },
            error: function(error) {
                console.log("Server error");
                console.log(error);         
            }
    	});
	}
}


function addFeedTab(feedItem) {
    var feedTab = $("<li/>", {
        id: feedItem.id,
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
    	class: "glyphicon glyphicon-remove remove-link"
    }).appendTo(feedTab);
}


function selectFeedTab(feedItem) {
	if (feedItem != null) {
		$("#back-link").show();
		$(".feed-tab-list").hide();
		$(".feed-tab-detail").show();
		$(".feed-tab-detail").children(".title").text(feedItem.title);
		var pubInfo = $(".feed-tab-detail").children(".pub-info"); 
		pubInfo.children(".source-name").text(feedItem.feedSource.name);
		if (feedItem.author != null) {
			pubInfo.children(".author").html("\u00A0| by <i>" + feedItem.author + "</i>\u00A0");
		}
		pubInfo.children(".published-date").text("\u00A0|\u00A0" + feedItem.publishedDateString);
		$(".feed-tab-detail").children(".description").html(feedItem.description);
		$(".feed-tab-detail").scrollTop(0);
	} else {
		console.log("feedItem not found");
	}
}

