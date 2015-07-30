var PROJECTS_TREEVIEW = '.projects-tree-view';

function initPortfolio() {

	/*
	 * Initialize Bootstrap WYSIHTML5 widget
	 */
	$(".textarea").wysihtml5();

	/*
	 * Initialize Select2 widget
	 */
	$('.select2').select2({
		minimumResultsForSearch : Infinity
	});

	/*
	 * Initialize DatePicker widget
	 */
	$('.datepicker').datepicker()

	/*
	 * Initialize TagManager widget
	 */
	$(".tm-input").tagsManager({
		prefilled : [ "Komea", "Java", "Spring", "KPI Manager", "Dashboard" ],
		AjaxPush : null,
		AjaxPushAllTags : true
	});

	/*
	 * Initialize JsTree widget
	 */
	restAPI.folderNodeTypes.findAll(function(data) {
		initJsTree(data);
	});
	

	/*
	function format_jstree_data(data) {

		var obj = {
			name : data.text,
			size : 1,
			children : []
		}
		$.each(data.children, function(i, v) {
			obj.children.push(format_jstree_data(v));
		});
		return obj;
	}

	function drawSunburst() {

		var width = $(PROJECTS_SUNBURST).parent().parent().width() * 0.7;
		console.log(width);

		var treeview_data = getNodes();

		var data = {
			name : 'Projects',
			children : []
		};
		$.each(treeview_data.slice(1), function(i, v) {
			data.children.push(format_jstree_data(v));
		});
		console.log(data);

		new SunburstBuilder().setRoot(PROJECTS_SUNBURST).setHeight(width)
				.setWidth(width * 0.9)
				// .setURL('/resources/sunburst_sample.json')
				.onClick(function(data) {
					$(PROJECTS_TREEVIEW).jstree(true).deselect_all();
					$(PROJECTS_TREEVIEW).jstree(true).close_all();
					toggleNode(getNodes().slice(1), data.name);
					console.log(data);
				}).build(data);

	}

	$(PROJECTS_TREEVIEW).bind("loaded.jstree", function(e, data) {
		drawSunburst();
		$(PROJECTS_TREEVIEW).jstree(true).select_node(getNodes()[0]);
		// $(PROJECTS_TREEVIEW).off("click.jstree", ".jstree-anchor");
	});

	$(window).resize(function() {
		$(PROJECTS_SUNBURST).html('');
		drawSunburst();
	});
*/
}

function getNodes() {
	return $(PROJECTS_TREEVIEW).data().jstree.get_json();
}

function toggleNode(nodes, name) {
	$.each(nodes, function(i, node) {
		if (node.text == name) {
			console.log('toggle node ' + name);
			$(PROJECTS_TREEVIEW).jstree(true).select_node(node);
		} else {
			if (node.children) {
				toggleNode(node.children, name);
			}
		}
	});
}

function getNodeID(node) {
	return node && node.a_attr ? node.a_attr['data-nodeID'] : null;
}

function setNodeID(node, id) {
	node.a_attr['data-nodeID'] = id;
	return node;
}

function getProjectID(node) {
	return node && node.a_attr ? node.a_attr['data-projectID'] : null;
}

/*
 * Initialize JsTree widget
 */
function initJsTree(data) {
	
	var types = {};
	var folderActions = {};
	var folderEditActions = {}
	if (data) {
		$.each(data, function(i,v) {
			types[v.id] = { 'icon': v.icon }
			folderActions[v.id] = {
					label: v.name,
					icon: v.icon,
					action : function(data) {
						var inst = $.jstree.reference(data.reference);
					    var node = inst.get_node(data.reference);
					    addFolder(v.id, node);
					}
			}
			folderEditActions[v.id] = {
					label: v.name,
					icon: v.icon,
					action : function(data) {
						var inst = $.jstree.reference(data.reference);
					    var node = inst.get_node(data.reference);
					    changeFolderType(v.id, node);
					},
					_disabled: function(data) {
						var inst = $.jstree.reference(data.reference);
					    var node = inst.get_node(data.reference);
					    var currentType = v.id;
						return $(PROJECTS_TREEVIEW).jstree(true).get_type(node) == currentType;
					}
			}
		});
	}
	
	$(PROJECTS_TREEVIEW).jstree({
		"core" : {
			"animation" : 0,
			"themes" : {
				"stripes" : true
			},
			data: {
				url: restAPI.portfolio.URL,
				dataType: "json"
			},
			'check_callback': function(op, node, parent, pos, more) {
				if (op === "move_node" && more && more.core) {
					var nodeID = getNodeID(node);
					var parentID = getNodeID(parent);
					restAPI.portfolio.move(nodeID, parentID, function() {
						console.log("moved node #"+nodeID+" into node #"+parentID);
					});
				}
				return true;
			}	
		},
		"plugins" : [ "dnd", "contextmenu", "types", "search" ],
		"contextmenu" : {
			"items" : {
				"add" : {
					label : "Add",
					icon : "fa fa-plus",
					submenu: {
						'add_folder':  {
							label: "Folder",
							icon: "glyphicon glyphicon-folder-open",
							submenu: folderActions
						},
						'add_project': {
							label: "Project",
							icon: "fa fa-briefcase",
							action : function(data) {
								var inst = $.jstree.reference(data.reference);
							    var node = inst.get_node(data.reference);
							    var ID = getNodeID(node);
								//...
							}
						}
					}			
				},
				"edit" : {
					label : "Edit",
					icon : "fa fa-pencil",
					shortcut : 113,
					shortcut_label : 'F2',
					action : function(data) {
						var inst = $.jstree.reference(data.reference);
					    var node = inst.get_node(data.reference);
					    var ID = getNodeID(node);
					    var projectID = getProjectID(node);
					    if (!projectID) {
					    	console.log('editing node: ' + ID);
					    	$(PROJECTS_TREEVIEW).jstree(true).edit(node);
					    }
					}
				},
				
				"type" : {
					label: "Type",
					icon: "fa fa-tag",
					submenu: folderEditActions,
					_disabled: function(data) {
						var inst = $.jstree.reference(data.reference);
					    var node = inst.get_node(data.reference);
					    var projectID = getProjectID(node);
					    return projectID;
					}
				},
				
				"delete" : {
					label : "Delete",
					icon : "fa fa-trash",
					action : function(data) {
						var inst = $.jstree.reference(data.reference);
					    var node = inst.get_node(data.reference);
					    var ID = getNodeID(node);
					    restAPI.portfolio.remove(ID, function(data) {
					    	$(PROJECTS_TREEVIEW).jstree(true).delete_node(node);
					    });
					}
				}
			}
		},
		'types' : types /*{
			'default' : {
				icon : 'glyphicon glyphicon-folder-open'
			}
		}*/
	});
	
	/**
	 * Action triggered when clicking on a node
	 */
	$(PROJECTS_TREEVIEW).on("select_node.jstree", function(e, data) {
		if (!data || !data.event || data.event.handleObj.type == 'contextmenu') {
			return;
		}
		var key = data.instance.get_node(data.node, true).children('a')
				.data('key');
		if (key) {
			document.location = '/ui/portfolio/manager/' + key
		} else {
			data.instance.toggle_node(data.node);
		}
	});
	
	/**
	 * Action triggered when renaming a text node
	 */
    $(PROJECTS_TREEVIEW).bind("rename_node.jstree", function (e, data) {
    	var node = data.node;
    	var nodeID = getNodeID(node);
    	var text = data.text;
    	if (nodeID) {
	    	restAPI.portfolio.folders.edit(nodeID, text, function(data) {
	    		console.log("edited node #"+nodeID+" with text: "+text);
	    	});
    	}else{
    		var parentNode = $(PROJECTS_TREEVIEW).jstree(true).get_node(data.node.parent);
    		var parentID = parentNode ? getNodeID(parentNode) : null;
    		var typeID = node.type ? node.type : null;
    		restAPI.portfolio.folders.add(text, typeID, parentID, function(data) {
    			console.log("created node with text: "+text);
    			console.log(data);
    			if (data && data.id) {
    				setNodeID(node, data.id);
    			}else{
    				$(PROJECTS_TREEVIEW).jstree(true).delete_node(node);
    			}
    		});
    	}
    });

}

/**
 * Insert a new node in the JsTree widget
 * @param parentNode
 */
function addFolder(typeID, parentNode) {
	var ID = getNodeID(parentNode);
	console.log('adding new element in node: ' + ID);
	var newNode = $(PROJECTS_TREEVIEW).jstree(true).create_node(parentNode);
	$(PROJECTS_TREEVIEW).jstree(true).set_type(newNode, typeID);
	$(PROJECTS_TREEVIEW).jstree(true).edit(newNode);
}

function changeFolderType(typeID, node) {
	var ID = getNodeID(node);
	restAPI.portfolio.folders.changeType(ID, typeID, function(data) {
		$(PROJECTS_TREEVIEW).jstree(true).set_type(node, typeID);
	});
}

/**
 * expand all branches in treeview
 */
function expandTreeview() {
	
	$(PROJECTS_TREEVIEW).jstree(true).open_all();
	
}

/**
 * collapse treeview
 */
function collapseTreeview() {
	
	$(PROJECTS_TREEVIEW).jstree(true).close_all();
	
}
