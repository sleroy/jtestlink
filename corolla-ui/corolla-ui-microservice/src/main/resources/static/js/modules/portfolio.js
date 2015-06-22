
var PROJECTS_TREEVIEW = '.projects-tree-view';
var PROJECTS_SUNBURST = '.projects-sunburst';

$(document).ready(function() {
	
	$(".textarea").wysihtml5();
	
	$(PROJECTS_TREEVIEW).jstree({
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
		
		var width = $(PROJECTS_SUNBURST).parent().width() * 0.7;
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
				.setWidth(width)
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
    });
	
	$(window).resize(function() {
		$(PROJECTS_SUNBURST).html('');
		drawSunburst();
	});
	
});