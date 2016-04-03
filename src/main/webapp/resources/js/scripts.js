/**
 * 
 */

$(document).ready(function() {
	validateRegisterForm();
});


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


function validateRegisterForm() {
    $("#register-form").validate({
    	errorElement: "div",
    	errorPlacement: function(error, element) {
    		error.addClass("alert alert-danger");
    		error.appendTo(element.parent());
    	},
    	focusInvalid: true,
        onkeyup: false,
        rules: {
        	pictureUrl: {
        		required: true,
        		url: true,
        		maxlength: 256
        	},
            name: {
                required: true,
                minlength: 2,
                maxlength: 100
            },
            email: {
                required: true,
                email: true,
                maxlength: 100
            },
            password: {
                required: true,
                minlength: 6,
                maxlength: 50
            },
            confirmPassword: {
                required: true,
                minlength: 6,
                maxlength: 50,
                equalTo: "#password"
            }
        }
    });
}

