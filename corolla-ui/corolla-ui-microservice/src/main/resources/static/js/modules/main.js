
function initMainJS() {
	
	/*
	 * Initialize the main javascript to hide alert box and init ajax error handler
	 */
	$(".ajaxAlert").hide();
	
	$(document).ajaxError(
	        function(event, jqxhr, settings, thrownError) {
		        $(".ajaxAlert").show();
		        $(".ajaxAlert").append(
		                "<strong>Ajax Error</strong> " + settings.url + " -> "
		                        + thrownError + "<br/>");
	        });
	
	/* 
	 * Fix issue with sidebar toggle button disapearing
	 */
    $('.sidebar-toggle').click(function() {
    	$('.sidebar-toggle').removeClass('visible-xs');
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


$(document).ready(function() {
	
	$(document).on('dnd_stop.vakata', function(e, data) {
		console.log('the tree has been updated!')
	});
	
	
	$('.project-releases-tree-view').jstree({
		"core": {
			"animation" : 0,
		    "check_callback" : true,
		    "themes" : { "stripes" : true },
		    'data' : [
               { text: '1.4', icon: 'glyphicon glyphicon-tag' },
               { text: '1.3', icon: 'glyphicon glyphicon-tag' },
               { text: '1.2', icon: 'glyphicon glyphicon-tag' },
               { text: '1.1', icon: 'glyphicon glyphicon-tag' },
               { text: '1.0', icon: 'glyphicon glyphicon-tag' }
		    ]
		},
		"plugins": ["dnd", "contextmenu"],
		"contextmenu": {
			"items": {
				"add": {
					label: "Add",
					action: function(node) {
						var name = node.reference[0].text;
						console.log('adding new element in node: '+name);
					}
				},
				"delete": {
					label: "Delete",
					action: function(node) {
						console.log(node);
						console.log('deleting node: '+node.reference[0].text);
					}
				}
			}
		}
	});
	
});