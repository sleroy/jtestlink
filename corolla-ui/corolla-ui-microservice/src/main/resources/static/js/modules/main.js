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


$(document).ready(function() {
	
	$('.project-tree-view').jstree({
		"core": {
			"animation" : 0,
		    "check_callback" : true,
		    "themes" : { "stripes" : true },
		    'data' : [
               'Corolla',
               {
            	   'text': 'Komea',
            	   'children': [{ 
            	    	'text': 'Komea Rest API'
            	    }, {
            	    	'text': 'Komea Dashboard',
            	    	'a_attr': {
            	    		'data-key': 'komea-dashboard'
            	    	}
            	    }]
               }
		    ]
		},
		"plugins": ["dnd", "contextmenu", "types"],
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
		},
		'types': {
			'default': { icon: 'glyphicon glyphicon-bookmark' }
		}
	}).on("select_node.jstree", function (e, data) {
		var key = data.instance.get_node(data.node, true).children('a').data('key');
		if (key) {
			document.location = '/ui/projects/'+key
		}
    });
	
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
	
	$('.project-items-tree-view').jstree({
		"core": {
			"animation" : 0,
		    "check_callback" : true,
		    "themes" : { "stripes" : true },
		    'data' : [{
		    	'text': 'Requirements',
		    	'children': [{
		    		'text': 'CRUD KPI Operations',
	            	'children': [{ 
	            	    	'text': 'Add a KPI'
	            	    }, {
	            	    	'text': 'Edit a KPI'
	            	    }, {
	            	    	'text': 'Delete a KPI'
	            	    }]
		    	}]
		    },{
		    	'text': 'Test Cases',
		    	'type': 'testcase',
		    	'children': [{
		    		'text': 'Create a KPI',
		    		'type': 'testcase'
		    	},{
		    		'text': 'Delete a KPI',
		    		'type': 'testcase'
		    	}]
		    }]
		},
		"plugins": ["dnd", "contextmenu", "types"],
		"contextmenu": {
			"items": {
				"add": {
					label: "Add",
					icon: 'glyphicon glyphicon-plus',
					'submenu': {
						'requirement': {
							label: 'Requirement'
						},
						'user_story': {
							label: 'User Story'
						},
						'set' : {
							label: 'Set'
						},
						'test_case' : {
							label: 'Test Case'
						}
					}
				},
				"delete": {
					label: "Delete",
					icon: 'glyphicon glyphicon-remove',
					action: function(node) {
						console.log(node);
						console.log('deleting node: '+node.reference[0].text);
					}
				}
			}
		},
		"types": {
			'default': { icon: 'glyphicon glyphicon-list-alt' },
			'testcase': { icon: 'glyphicon glyphicon-flash' }
		}
	});
	
});