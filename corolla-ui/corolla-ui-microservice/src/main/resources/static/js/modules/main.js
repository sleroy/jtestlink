/**
 * Initialize the main javascript to hide alert box and init ajax error handler
 */
function initMainJS() {
	$(document).ready(function() {
		$(".ajaxAlert").hide();
	});
	
	$(document).ajaxError(
	        function(event, jqxhr, settings, thrownError) {
		        $(".ajaxAlert").show();
		        $(".ajaxAlert").append(
		                "<strong>Ajax Error</strong> " + settings.url + " -> "
		                        + thrownError + "<br/>");
	        });
}

function define_yesModalButton_clickAction(action) {
	$('#btnModalYes').click(action);
}

function DeleteModal() {
	
	var modalSelector = '#modal';
	var btnYesSelector = '#btnModalYes';
	
	return {
		
		'set': function(key, value) {
			$(modalSelector).data(key, value);
		},
		
		'get': function(key) {
			return $(modalSelector).data(key);
		},
		
		'show': function() {
			$(modalSelector).modal('show');
		},
		
		'hide': function() {
			$(modalSelector).modal('hide');
		},
		
		'onYesAction': function(action) {
			$(btnYesSelector).click(action);
		}
		
	}
	
}

var deleteModal = new DeleteModal();
