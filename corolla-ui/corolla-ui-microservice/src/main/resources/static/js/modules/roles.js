function roleTable() {
	$('#roles').dataTable();
	
	$("[data-toggle=tooltip]").tooltip();
	
	$('button.roleDelete').on('click', function(e) {
		e.preventDefault();
		var id = $(this).closest('tr').data('id');
		$('#modal').data('id', id).modal('show');
	});
	
	define_yesModalButton_clickAction(function() {
		var id = $('#modal').data('id');
		
		rest_deleteRole(id, function(data, status) {
			consoleLog("Role " + id + " removed")
			$('[data-id=' + id + ']').remove();
		});
		
		$('#modal').modal('hide');
	});
}

function initFormRoleEdit() {
	$(document).ready(function() {
		
		$('#form').parsley();
		
	});
	
}