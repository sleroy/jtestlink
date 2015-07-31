
var ITEMS_TREEVIEW = '.project-items-tree-view';
var SUNBURST = '.project-items-sunburst';

function initRequirementView() {
	
	/*
	 * Initialize Bootstrap WYSIHTML5
	 */
	$(".textarea").wysihtml5();
	
	/*
	 * Initialize TagManager widget
	 */
	$(".tm-input").tagsManager({
		 prefilled: ["KPI", "CRUD Operation"],
		 AjaxPush: null,
		 AjaxPushAllTags: true
	});
	
	$(".tm-input").on('tm:afterPush', function(e, tag) {
		console.log(tag + " was pushed!");
	});
	
	$(".tm-input").on('tm:afterSplice', function(e, tag) {
		console.log(tag + " was removed!");
	});
	
	/*
	 * Handle click on a revision
	 */
	$('#revisions tr td.toggle').click(function() {
		var link = $(this).parent().data('href');
		if (link) {
			document.location = link;
		}
	});
	
	/*
	 * Retrieves JsTree data
	 */
	restAPI.requirements.jstree("corolla", "Master", initJsTree);
	
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
				$(ITEMS_TREEVIEW).jstree(true).toggle_node(node);
			}else{
				if (node.children) {
					toggleNode(node.children, name);
				}
			}
		});
	}
	
	function drawSunburst() {
		
		/* Clear the div content */
		$(SUNBURST).html('');
		
		/* Get the container's width */
		var width = $(SUNBURST).parent().parent().width() * 0.7;
		console.log( width );
		
		/* Retrieve the treeview data */
		var treeview_data = getNodes();
		
		/* Format treeview data to sunburst expected data format */
		var data = { name: 'Projects', children: [] };
		$.each(treeview_data, function(i, v) {
			data.children.push(format_jstree_data(v));
		});
		console.log(data);
		
		/* build the sunburst */
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
	
	/* Draw the sunburst when the user clicks on the tab Requirements */
	$('.toggle-sunburst').click(function() {
//		drawSunburst();
	});
	
	/* Draw the sunburst when the treeview is completely loaded */
	$(ITEMS_TREEVIEW).bind("loaded.jstree", function(e, data) {
		$(ITEMS_TREEVIEW).jstree(true).toggle_node(getNodes()[0]);
//		drawSunburst();
	});
	
	/* Refresh the sunburst when the window is being resized */
	$(window).resize(function() {
//		drawSunburst();
	});
	
}

/**
 * Initializes JsTree widget
 * @param data
 */
function initJsTree(data) {
	
	$(ITEMS_TREEVIEW).jstree({
		"core": {
			"animation" : 0,
		    "check_callback" : true,
		    "themes" : { "stripes" : true },
		    'data' : data
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
	
	/**
	 * Action triggered when clicking on a node
	 */
	$(ITEMS_TREEVIEW).on("select_node.jstree", function(e, data) {
		if (!data || !data.event || data.event.handleObj.type == 'contextmenu') {
			return;
		}
		var key = data.instance.get_node(data.node, true).children('a')
				.data('key');
		if (key) {
			document.location = '/ui/requirements/'+pageData.projectKey+'/'+pageData.branchName+'/'+ key
		} else {
			data.instance.toggle_node(data.node);
		}
	});
	
}

/**
 * expand all branches in treeview
 */
function expandTreeview() {
	
	$(ITEMS_TREEVIEW).jstree(true).open_all();
	
}

/**
 * collapse treeview
 */
function collapseTreeview() {
	
	$(ITEMS_TREEVIEW).jstree(true).close_all();
	
}
