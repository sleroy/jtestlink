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
		
		"applications": {
			
			"delete": function(id, callback) {
				call('applications/delete/'+id, callback);
			}
		}
	}
	
}

var restAPI = new RestAPI();