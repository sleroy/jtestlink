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