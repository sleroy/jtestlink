
function initMainJS() {
	
	/*
	 * Initialize the main javascript to hide alert box and init ajax error handler
	 */
	$(".ajaxAlert").hide();
	$(document).ajaxError(function(event, jqxhr, settings, thrownError) {
		$(".ajaxAlert").show();
		$(".ajaxAlert").append(
		                "<strong>Ajax Error</strong> "
						+ settings.url + " -> "
		                + thrownError + "<br/>"
		);
	});
	
	/* 
	 * Fix issue with sidebar toggle button disapearing
	 */
    $('.sidebar-toggle').click(function() {
    	
    	$('.sidebar-toggle').removeClass('visible-xs');
    	
    	$(window).trigger('resize');
    	
//    	var hidden = $('body').hasClass('sidebar-collapse');
//    	sessionStorage.setItem('sidebar-collapse', hidden);
    });
    
//    var sidebarHidden = sessionStorage.getItem('sidebar-collapse');
//    console.log(sidebarHidden);
//    if (sidebarHidden && sidebarHidden == 'true') {
//    	//$('body').addClass('sidebar-collapse');
//    	document.getElementsByTagName('body')[0].className += " sidebar-collapse";
//    }
    
    /*
     * Initialize select2 components within the page
     */
    $('.select2').select2();
    
    /*
     * Set default date format for datepicker widgets
     */
    $.fn.datepicker.defaults.format = "dd/mm/yyyy";
    
}

function define_yesModalButton_clickAction(action) {
	$('#btnModalYes').click(action);
}

/**
 * Object for interacting with the modal
 * asking for a confirmation before deleting
 */
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

/**
 * Object for interacting with the modal
 * to quickly change project
 */
function ChangeProjectModal() {
	
	var modalSelector = "#modal-change-project";
	var treeviewSelector = '.change-project-treeview';
	
	$(treeviewSelector).jstree({
		"core": {
			"animation" : 0,
		    "check_callback" : true,
		    "themes" : { "stripes" : true },
		    'data' : [
               {
            	   'text': 'Corolla',
            	   'icon': 'http://lorempixel.com/16/16/'
               }, {
            	   'text': 'Komea',
            	   'children': [{ 
            	    	'text': 'Komea Rest API',
            	    	'icon': 'http://lorempixel.com/16/16/',
            	    	'a_attr': {
            	    		'data-key': 'komea-rest-api'
            	    	}
            	    }, {
            	    	'text': 'Komea Dashboard',
            	    	'icon': 'http://lorempixel.com/16/16/',
            	    	'a_attr': {
            	    		'data-key': 'komea-dashboard'
            	    	}
            	    }]
               } 
		    ]
		},
		"plugins": ["types", "search"],
		'types': {
			'default': { icon: 'glyphicon glyphicon-folder-open' }
		}
	});
	
	return {
		
		'show': function() {
			$(modalSelector).modal('show');
		},

		'hide': function() {
			$(modalSelector).modal('hide');
		},
		
		'onSelect': function(callback) {
			$(treeviewSelector).bind("select_node.jstree", function(e, data) {
				var key = data.instance.get_node(data.node, true).children('a').data('key');
				if (key) {
					callback(key);
				}
			});
		}
	}
	
}

/*
 * Create modal instances
 */
var deleteModal = new DeleteModal();
var changeProjectModal = new ChangeProjectModal();

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

/**
 * Automatically toggle a tab
 * if the tab id is specified in the URL
 */
function tabRooting() {
	
	var target = window.location.hash; 
	if(target) {		
		$.each($('.nav-tabs li'), function(i, elt){
			var toggleButton = $(elt).find('a');
			if (toggleButton.attr('href') == target) {
				toggleButton.click();
			}
		});
	}
	
}