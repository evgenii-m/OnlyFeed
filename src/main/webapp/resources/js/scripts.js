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