var PROJECTS_TREEVIEW = '.projects-tree-view';

var jsTreeManager = new JsTreeManager(PROJECTS_TREEVIEW);

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
						var node = jsTreeManager.extractNode(data);
					    jsTreeManager.addFolder(v.id, node);
					}
			}
			folderEditActions[v.id] = {
					label: v.name,
					icon: v.icon,
					action : function(data) {
						var node = jsTreeManager.extractNode(data);
					    changeFolderType(v.id, node);
					},
					_disabled: function(data) {
						var node = jsTreeManager.extractNode(data);
					    var currentType = v.id;
						return jsTreeManager.getType(node) == currentType;
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
			'check_callback': jsTreeManager.callbackHandler
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
								var node = jsTreeManager.extractNode(data);
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
						var node = jsTreeManager.extractNode(data);
					    var projectID = jsTreeManager.getProjectID(node);
					    if (!projectID) {
					    	jsTreeManager.editNode(node);
					    }
					}
				},
				
				"type" : {
					label: "Type",
					icon: "fa fa-tag",
					submenu: folderEditActions,
					_disabled: function(data) {
						var node = jsTreeManager.extractNode(data);
					    var projectID = jsTreeManager.getProjectID(node);
					    return projectID;
					}
				},
				
				"delete" : {
					label : "Delete",
					icon : "fa fa-trash",
					action : function(data) {
						var node = jsTreeManager.extractNode(data);
					    var ID = jsTreeManager.getNodeID(node);
					    restAPI.portfolio.remove(ID, function(data) {
					    	jsTreeManager.deleteNode(node);
					    });
					}
				}
			}
		},
		'types' : types
	});
	
	/**
	 * Action triggered when clicking on a node
	 */
	jsTreeManager.setSelectAction(function(node, key) {
		if (key) {
			document.location = '/ui/portfolio/manager/' + key
		}
	});
	
	/**
	 * Action triggered when a new folder has been added in the JsTree
	 */
	jsTreeManager.setCreateAction(function(node, text, typeID, parentID) {
		restAPI.portfolio.folders.add(text, typeID, parentID, function(data) {
			console.log("created node with text: "+text);
			console.log(data);
			if (data && data.id) {
				jsTreeManager.setNodeID(node, data.id);
			}else{
				jsTreeManager.deleteNode(node);
			}
		});
	});
	
	/**
	 * Action triggered when renaming a text node
	 */
	jsTreeManager.setEditAction(function(node, nodeID, text) {
		if (nodeID) {
	    	restAPI.portfolio.folders.edit(nodeID, text, function(data) {
	    		console.log("edited node #"+nodeID+" with text: "+text);
	    	});
    	}
	});
	
	/**
	 * Action triggered when a node has been moved in the JsTree
	 */
	jsTreeManager.setMoveAction(function(nodeID, parentID) {
		restAPI.portfolio.move(nodeID, parentID, function() {
			console.log("moved node #"+nodeID+" into node #"+parentID);
		});
	});

}

/**
 * Change the type of a folder node
 * @param typeID
 * @param node
 */
function changeFolderType(typeID, node) {
	var ID = jsTreeManager.getNodeID(node);
	restAPI.portfolio.folders.changeType(ID, typeID, function(data) {
		jsTreeManager.setType(node, typeID);
	});
}