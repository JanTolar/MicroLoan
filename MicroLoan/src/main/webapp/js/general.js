$(document).on({
	ajaxStart : function() {
		$("body").addClass("loading");
	},
	ajaxStop : function() {
		$("body").removeClass("loading");
	}
});

function getFormJSON(formName) {
	var formArray = $('#' + formName).serializeArray();
	var returnArray = {};
	for (var i = 0; i < formArray.length; i++){
		returnArray[formArray[i]['name']] = formArray[i]['value'];
	}
	return JSON.stringify(returnArray);
}