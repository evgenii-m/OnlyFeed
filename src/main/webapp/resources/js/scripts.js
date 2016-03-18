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

$(document).ready(function() {
	
//	$('.summary').each(function(i, elem) {
//		var descSummary = $(elem).text();
//		$(elem).text(descSummary);
//	});

});