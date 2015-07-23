function consoleLog(data) {
	if (window.console && console.log) {
		console.log(data);
	}
	
}

function RestAPI() {
	
	var REST_PREFIX = "/rest/";
	
	var call = function(url, callback) {
		$.get(REST_PREFIX + url, callback);
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
			
			"jstree": function(callback) {
				call("portfolio/jstree", callback);
			},
			
			"edit": function(nodeID, text, callback) {
				postText("portfolio/edit/text/"+nodeID, text, callback);
			},
			
			"move": function(fromID, toID, callback) {
				call("portfolio/move/"+fromID+"/"+toID, callback);
			},
			
			"remove": function(nodeID, callback) {
				call("portfolio/remove/"+nodeID, callback);
			}
		
		},
		
		"requirements": {
			
			"jstree": function(projectKey, branchName, callback) {
				call("requirements/tree/jstree/"+projectKey+"/"+branchName, callback);
			}
		}

	}
	
}

var restAPI = new RestAPI();