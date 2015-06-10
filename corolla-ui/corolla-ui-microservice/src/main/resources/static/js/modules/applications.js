function applicationTable() {
	// $('.user-delete').click(function(e) {
	// alert("gni")
	// });
	$('#users').dataTable();
	
	$("[data-toggle=tooltip]").tooltip();
	
	$('button.appDelete').on('click', function(e) {
		e.preventDefault();
		var id = $(this).closest('tr').data('id');
		$('#modal').data('id', id).modal('show');
	});
	
	define_yesModalButton_clickAction(function() {
		
		var id = $('#modal').data('id');
		
		/*rest_deleteUser(id, function(data, status) {
			consoleLog("User " + id + " removed")
			$('[data-id=' + id + ']').remove();
		});*/
		//restAPI.deleteApplication(id, function(data, status) {
		restAPI.applications.delete(id, function(data, status) {
			consoleLog("Application "+id+" removed");
			$('[data-id='+id+']').remove();
		});
		
		$('#modal').modal('hide');
	});
}

