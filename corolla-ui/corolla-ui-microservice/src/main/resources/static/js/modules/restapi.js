var REST_PREFIX = "/rest/";
var PARAM = "#param"

var USER_DELETE_URL = "users/delete/" + PARAM;
var USER_DISABLE_URL = "users/disable/" + PARAM;
var USER_ENABLE_URL = "users/enable/" + PARAM;
var ROLE_DELETE_URL = "roles/delete/" + PARAM;

function consoleLog(data) {
	if (window.console && console.log) {
		console.log(data);
	}
	
}

function rest_get(url, callback) {
	return $.get(REST_PREFIX + url, callback);
}

function rest_deleteUser(id, callback) {
	
	return rest_get(USER_DELETE_URL.replace(PARAM, id), callback);
	
}

function rest_disableUser(id, callback) {
	
	return rest_get(USER_DISABLE_URL.replace(PARAM, id), callback);
	
}

function rest_enableUser(id, callback) {
	
	return rest_get(USER_ENABLE_URL.replace(PARAM, id), callback);
	
}

function rest_deleteRole(id, callback) {
	
	return rest_get(ROLE_DELETE_URL.replace(PARAM, id), callback);
	
}