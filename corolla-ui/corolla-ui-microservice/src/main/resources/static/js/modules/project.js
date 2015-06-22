
var ITEMS_TREEVIEW = '.project-items-tree-view';

var SUNBURST = '.project-items-sunburst';

$(document).ready(function() {
	
	$(".textarea").wysihtml5();
	
	$(ITEMS_TREEVIEW).jstree({
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
	
	function format_jstree_data(data) {
		
		var obj = { name: data.text, size: 1, children: [] }
		$.each(data.children, function(i,v) {
			obj.children.push(format_jstree_data(v));
		});
		return obj;
	}

	function getNodes() {
		return $(ITEMS_TREEVIEW).data().jstree.get_json();
	}
	
	function toggleNode(nodes, name) {
		$.each(nodes, function(i,node) {
			if (node.text == name) {
				console.log('toggle node '+name);
				$(ITEMS_TREEVIEW).jstree(true).select_node(node);
			}else{
				if (node.children) {
					toggleNode(node.children, name);
				}
			}
		});
	}
	
	function drawSunburst() {
		
		var width = $(SUNBURST).parent().width() * 0.7;
		console.log( width );
		
		var treeview_data = getNodes();
		
		var data = { name: 'Projects', children: [] };
		$.each(treeview_data, function(i, v) {
			data.children.push(format_jstree_data(v));
		});
		console.log(data);
		
		new SunburstBuilder()
				.setRoot(SUNBURST)
				.setHeight(width)
				.setWidth(width * 0.9)
				//.setURL('/resources/sunburst_sample.json')
				.onClick(function(data) {
					$(ITEMS_TREEVIEW).jstree(true).deselect_all();
					$(ITEMS_TREEVIEW).jstree(true).close_all();
					toggleNode(getNodes(), data.name);
					console.log(data);
				})
				.build(data);
		
	}
	
	$(ITEMS_TREEVIEW).bind("loaded.jstree", function(e, data) {
		drawSunburst();
	});
	
	$(window).resize(function() {
		$(SUNBURST).html('');
		drawSunburst();
	});
	
});