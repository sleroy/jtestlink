function consoleLog(data) {
	if (window.console && console.log) {
		console.log(data);
	}
	
}

function RestAPI() {
	
	var REST_PREFIX = "/rest/";
	
	var call = function(url, callback) {
		$.ajax({
			url: REST_PREFIX + url,
			success: callback,
			error: function(data, status) {
				console.log(data);
				console.log(status);
			}
		});
	}
	
	var post = function(url, data, callback) {
		$.post(REST_PREFIX + url, data, callback);
	}
	
	var postText = function(url, data, callback) {
		$.ajax({
			url			: REST_PREFIX + url,
			method		: 'post',
			contentType	: "text/plain",
			data		: data,
			success 	: callback,
			error		: callback
		});
	}
	
	return {
		
		"users": {
			
			"enable": function(id, callback) {
				call("users/enable/"+id, callback);
			},
			
			"disable": function(id, callback) {
				call("users/disable/"+id, callback);
			},
			
			"delete": function(id, callback) {
				call("users/delete/"+id, callback);
			}
		},
		
		"roles": {
			
			"delete": function(id, callback) {
				call("roles/delete/"+id, callback);
			}
			
		},
		
		"userGroups": {
			
			"delete": function(id, callback) {
				call("groups/delete/"+id, callback);
			}
		},
		
		"portfolio": {
			
			"URL" : REST_PREFIX+"portfolio/jstree",
			
			"jstree": function(callback) {
				call("portfolio/jstree", callback);
			},
			
			"folders": {
				
				"add": function(text, typeID, parentID, callback) {
					var url = "portfolio/folders/add";
					if (parentID) {
						url += '/'+parentID;
					}
					url += '/'+typeID;
					postText(url, text, callback);
				},
				
				"edit": function(nodeID, text, callback) {
					postText("portfolio/folders/edit/"+nodeID, text, callback);
				},
				
				"changeType": function(nodeID, typeID, callback) {
					call("portfolio/folders/edit/type/"+nodeID+"/"+typeID, callback);
				}
				
			},
			
			"move": function(fromID, toID, callback) {
				if (!toID) toID = 0;
				call("portfolio/move/"+fromID+"/"+toID, callback);
			},
			
			"remove": function(nodeID, callback) {
				call("portfolio/remove/"+nodeID, callback);
			}
		
		},
		
		"requirements": {
			
			"jstree": function(projectKey, branchName, callback) {
				call("requirements/tree/"+projectKey+"/"+branchName+"/jstree", callback);
			},
			
			"folders": {
				
				"add": function(projectKey, branchName, text, typeID, parentID, callback) {
					var url = "requirements/tree/"+projectKey+"/"+branchName+"/folders/add";
					if (parentID) {
						url += '/'+parentID;
					}
					url += '/'+typeID;
					postText(url, text, callback);
				},
				
				"edit": function(projectKey, branchName, nodeID, text, callback) {
					postText("requirements/tree/"+projectKey+'/'+branchName+"/folders/edit/"+nodeID, text, callback);
				},
				
				"changeType": function(projectKey, branchName, nodeID, typeID, callback) {
					call("requirements/tree/"+projectKey+'/'+branchName+"/folders/edit/type/"+nodeID+"/"+typeID, callback);
				},
				
				"remove": function(projectKey, branchName, nodeID, callback) {
					call("requirements/tree/"+projectKey+'/'+branchName+"/remove/"+nodeID, callback);
				}
			
			},
			
			"move": function(projectKey, branchName, fromID, toID, callback) {
				if (!toID) toID = 0;
				call("requirements/tree/"+projectKey+'/'+branchName+"/move/"+fromID+"/"+toID, callback);
			}
			
		},
		
		"folderNodeTypes": {
			
			"findAll": function(callback) {
				call("trees/folders/types/all", callback);
			},
			
			"delete": function(typeID, callback) {
				call("/trees/folders/types/delete/"+typeID, callback);
			}
		}

	}
	
}

var restAPI = new RestAPI();