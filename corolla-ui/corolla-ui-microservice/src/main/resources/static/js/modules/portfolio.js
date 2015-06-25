
var PROJECTS_TREEVIEW = '.projects-tree-view';
var PROJECTS_SUNBURST = '.projects-sunburst';

$(document).ready(function() {
	
	/*
	 * Initialize Bootstrap WYSIHTML5 widget
	 */
	$(".textarea").wysihtml5();
	
	/*
	 * Initialize Select2 widget
	 */
	$('.select2').select2({
		minimumResultsForSearch: Infinity
	});
	
	/*
	 * Initialize DatePicker widget
	 */
	$('.datepicker').datepicker()
	
	/*
	 * Initialize TagManager widget
	 */
	$(".tm-input").tagsManager({
		 prefilled: ["Komea", "Java", "Spring", "KPI Manager", "Dashboard"],
		 AjaxPush: null,
		 AjaxPushAllTags: true
	});
	
	//$('.resizable').resizable();
	
	/*
	 * Initialize JsTree widget
	 */
	$(PROJECTS_TREEVIEW).jstree({
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
            	    	'icon': 'http://lorempixel.com/16/16/'
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
		"plugins": ["dnd", "contextmenu", "types", "search"],
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
			'default': { icon: 'glyphicon glyphicon-folder-open' }
		}
	}).on("select_node.jstree", function (e, data) {		
		var key = data.instance.get_node(data.node, true).children('a').data('key');
		if (key) {
			document.location = '/ui/portfolio/'+key
		}else{
			data.instance.toggle_node(data.node);
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
		return $(PROJECTS_TREEVIEW).data().jstree.get_json();
	}
	
	function toggleNode(nodes, name) {
		$.each(nodes, function(i,node) {
			if (node.text == name) {
				console.log('toggle node '+name);
				$(PROJECTS_TREEVIEW).jstree(true).select_node(node);
			}else{
				if (node.children) {
					toggleNode(node.children, name);
				}
			}
		});
	}
	
	function drawSunburst() {
		
		var width = $(PROJECTS_SUNBURST).parent().parent().width() * 0.7;
		console.log( width );
		
		var treeview_data = getNodes();
		
		var data = { name: 'Projects', children: [] };
		$.each(treeview_data, function(i, v) {
			data.children.push(format_jstree_data(v));
		});
		console.log(data);
		
		new SunburstBuilder()
				.setRoot(PROJECTS_SUNBURST)
				.setHeight(width)
				.setWidth(width * 0.9)
				//.setURL('/resources/sunburst_sample.json')
				.onClick(function(data) {
					$(PROJECTS_TREEVIEW).jstree(true).deselect_all();
					$(PROJECTS_TREEVIEW).jstree(true).close_all();
					toggleNode(getNodes(), data.name);
					console.log(data);
				})
				.build(data);
		
	}

    $(PROJECTS_TREEVIEW).bind("loaded.jstree", function(e, data) {
    	drawSunburst();
    	//$(PROJECTS_TREEVIEW).off("click.jstree", ".jstree-anchor");
    	//addMenu();
    });
    
   /* $(PROJECTS_TREEVIEW).bind("after_open.jstree", function(e, data) {
    	addMenu();
    });
    
    var addMenu = function() {
    	$('.jstree-node').append('<a href="#test" class="label label-success">Requirements</a>');
    }*/
	
	$(window).resize(function() {
		$(PROJECTS_SUNBURST).html('');
		drawSunburst();
	});
	
});