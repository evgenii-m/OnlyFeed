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
	            var feedTab = $("<div/>", {
	                id: entry.id,
	                class: "feed-tab"
	            }).appendTo($(".feed-tab-list"));
	            $("<span/>", {
	                class: "source-name",
	                text: entry.feedSource.name
	            }).appendTo(feedTab);
	            $("<span/>", {
	                class: "title",
	                text: "\u00A0|\u00A0" + entry.title
	            }).appendTo(feedTab);
	        });
	        
//	        selectFeedTab($(".feed-tab").first().attr("id"));
	        $(".feed-tab").click(function() {
	            selectFeedTab($(this).attr("id"));
	        });
		},
	    error: function(error) {
	        console.log("error");
	        console.log(error);         
	    }
    });

    $("#back-link").click(function() {
    	$(".feed-tab-detail").hide();
    	$(".feed-tab-list").show();
    	$("#back-link").hide();
    });
}


function selectFeedTab(id) {
    $.ajax({
		url: "feed/tab/" + id,
        type: "get",
        success: function(response) {
        	$("#back-link").show();
        	$(".feed-tab-list").hide();
        	$(".feed-tab-detail").show();
        	$(".feed-tab-detail").children(".title").text(response.title);
        	var pubInfo = $(".feed-tab-detail").children(".pub-info"); 
        	pubInfo.children(".source-name").text(response.feedSource.name);
        	pubInfo.children(".author").html("\u00A0| by <i>" + response.author + "</i>");
        	pubInfo.children(".published-date").text("\u00A0|\u00A0 " + response.publishedDateString);
        	$(".feed-tab-detail").children(".description").html(response.description);
        	$(".feed-tab-detail").scrollTop(0);
        },
        error: function(error) {
            console.log("error");
            console.log(error);         
        }
	});
}


function submitPostRequest(url) {
	var form = document.createElement("form");
	form.setAttribute("method", "post");
	form.setAttribute("action", url);
	document.body.appendChild(form);
	form.submit();
}


function addFeedTab(feedItemId) {
	$.ajax({
		url: "feed/tab",
		type: "post",
		data: { 'id' : feedItemId },
		success: function(response) {
			console.log("success");
			console.log(response);
			if (response != null) {
				$("div.feed-tab-panel").append(response);
			}
		},
		error: function(error) {
			console.log("error");
			console.log(error);
		}
	});
}


