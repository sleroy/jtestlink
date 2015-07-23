var PROJECTS_TREEVIEW = '.projects-tree-view';
var PROJECTS_SUNBURST = '.projects-sunburst';
var PROJECTS_TREEMAP = '.zoomable-treemap';
var TILES_CONTAINER = '#tiles-container';

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
	restAPI.portfolio.jstree(initJsTree);

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

/*
 * Initialize JsTree widget
 */
function initJsTree(data) {
	
	$(PROJECTS_TREEVIEW).jstree({
		"core" : {
			"animation" : 0,
			"themes" : {
				"stripes" : true
			},
			'data' : data,
			'check_callback': function(op, node, parent, pos, more) {
				if (op === "move_node" && more && more.core) {
					var nodeID = node.a_attr['data-nodeID'];
					var parentID = parent.a_attr['data-nodeID'];	
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
					action : function(data) {
						var inst = $.jstree.reference(data.reference);
					    var node = inst.get_node(data.reference);
					    var ID = node.a_attr['data-nodeID'];
						console.log('adding new element in node: ' + ID);
						var newNode = $(PROJECTS_TREEVIEW).jstree(true).create_node(node);
						$(PROJECTS_TREEVIEW).jstree(true).edit(newNode);
					}
				},
				"delete" : {
					label : "Delete",
					action : function(data) {

						var inst = $.jstree.reference(data.reference);
					    var node = inst.get_node(data.reference);

					    restAPI.portfolio.remove(node.a_attr['data-nodeID'], function(data) {
					    	$(PROJECTS_TREEVIEW).jstree(true).delete_node(node);
					    });
					}
				}
			}
		},
		'types' : {
			'default' : {
				icon : 'glyphicon glyphicon-folder-open'
			}
		}
	}).on("select_node.jstree", function(e, data) {
		if (data.event.handleObj.type == 'contextmenu') {
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

function initTreemapView() {

	function drawTreemap() {

		$(PROJECTS_TREEMAP).html('');

		var width = $(PROJECTS_TREEMAP).width() * 0.99;
		console.log(width);
		var height = width * 0.6;

		new ZoomableTreemapBuilder()
				.setRoot(PROJECTS_TREEMAP)
				.setHeight(height)
				.setWidth(width)
				// .setURL('http://bost.ocks.org/mike/treemap/flare.json')
				.setURL(
						'http://sushanthece.github.io/D3-Zoomable-treemap/portaldata.json')
				.onClick(function(data) {
					console.log(data);
				}).build(null);

	}

	drawTreemap();

	$(window).resize(function() {
		drawTreemap();
	});

}

/**
 * Initialize tiles grid
 */
function initTiles() {

	/**
	 * Initialize tiles container
	 */
	var $container = $('.isotope').isotope({
		itemSelector : '.element-item',
		layoutMode : 'fitRows',
		getSortData : {
			name : '.name',
			symbol : '.symbol',
			number : '.number parseInt',
			category : '[data-category]',
			weight : function(itemElem) {
				var weight = $(itemElem).find('.weight').text();
				return parseFloat(weight.replace(/[\(\)]/g, ''));
			}
		}
	});

	/**
	 * Filter by tags
	 */
	$('.toolbar').on('click', 'span', function() {
		$('.toolbar span').removeClass('active');
		$(this).addClass('active');
		var filterValue;
		if ($(this).data('filter')) {
			filterValue = $(this).data('filter');
		} else {
			filterValue = $(this).text();
		}
		filterValue = filterValue.toLowerCase();
		var filterFn = function() {
			var tags = $(this).data('tags');
			if (tags) {
				tags = tags.toLowerCase();
				return (tags.indexOf(filterValue) > -1);
			}
			return false;
		}
		$container.isotope({
			filter : filterValue == '*' ? filterValue : filterFn
		});
	});

	/**
	 * Handle submit event in search bar
	 */
	$('.search-bar').submit(function() {
		searchTiles($(this).find('#query').val());
		return false;
	});

	/**
	 * Update the displayed items when a key is pressed in the search bar
	 */
	$('.search-bar #query').on('input', function() {
		searchTiles($(this).val());
	});

	/**
	 * Search through the tiles
	 */
	function searchTiles(q) {
		var filterFn;
		$('.toolbar span').removeClass('active');
		$('.toolbar span').first().addClass('active');
		if (q && q != '') {
			q = q.toLowerCase();
			filterFn = function() {
				var name = $(this).find('.name').text();
				if (name) {
					name = name.toLowerCase();
					return (name.indexOf(q) > -1);
				}
			}
		} else {
			filterFn = '*';
		}
		$container.isotope({
			filter : filterFn
		});
	}

	/**
	 * Reorganize the items when the menu is being closed/opened
	 */
	$('.content').resize(function() {
		$container.isotope('layout');
	});

	/**
	 * Handle click event on a tile
	 */
	$('.element-item').click(function() {
		var url = $(this).data('url');
		if (url) {
			window.location = url;
		}
	});

}