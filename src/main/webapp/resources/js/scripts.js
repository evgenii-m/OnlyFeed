/**
 * 
 */

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
				$("div.feed-tab-list").append(response);
			}
		},
		error: function(error) {
			console.log("error");
			console.log(error);
		}
	});
}


$(document).ready(function() {
//	$.ajax({
//		url: "feed/tab",
//		type: "get",
////		dataType: "html",
//		success: function(response) {
//			console.log("success");
//			console.log(response);
////			$('div.feed-tab-list').add($(response).html());
//		},
//		error: function(error) {
//			console.log("error");
//			console.log(error);
//		}
//	});
//	
//	$('div.feed-tab-list')
	
//	$('.summary').each(function(i, elem) {
//		var descSummary = $(elem).text();
//		$(elem).text(descSummary);
//	});

});