function userTable() {
	$('#users').dataTable(
	        {
	            "bProcessing" : true,
	            "ajax" : "/rest/users/allwithrole",
	            "columns" : [ { "data" : "gravatar" }, { "data" : "login" },
	                    { "data" : "firstName" }, { "data" : "lastName" },
	                    { "data" : "email" }, { "data" : "role" } ],
	            "fnRowCallback" : function(nRow, data, iDisplayIndex) {
		            
		            gravatarColumn(nRow, data.gravatar)
		            loginColumn(nRow, data.login)

		            return nRow;
	            }, });
}

function loginColumn(nRow, login) {
	var link = $("<a  />");
	link.attr('href', '/ui/admin/users/edit/' + login);
	link.text(login);
	$('td:eq(1)', nRow).html(link);
}

function gravatarColumn(nRow, gravatar) {
	var img = $("<img  />");
	img
	        .attr('src', "http://www.gravatar.com/avatar/" + gravatar
	                + "?s=32");
	img.attr('class', 'img-responsive')
	$('td:eq(0)', nRow).html(img);
}