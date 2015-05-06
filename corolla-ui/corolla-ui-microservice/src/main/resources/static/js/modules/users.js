function userTable() {
	// $('.user-delete').click(function(e) {
	// alert("gni")
	// });
	$('#users').dataTable();
	
	$("[data-toggle=tooltip]").tooltip();
	
	$('button.userDelete').on('click', function(e) {
		e.preventDefault();
		var id = $(this).closest('tr').data('id');
		$('#modal').data('id', id).modal('show');
	});
	
	define_yesModalButton_clickAction(function() {
		var id = $('#modal').data('id');
		
		rest_deleteUser(id, function(data, status) {
			consoleLog("User " + id + " removed")
			$('[data-id=' + id + ']').remove();
		});
		
		$('#modal').modal('hide');
	});
}

var rand = function() {
	return Math.random().toString(36).substr(2); // remove `0.`
};

var token = function() {
	return rand() + rand() + rand(); // to make it longer
};

function initFormUserEdit() {
	$(document).ready(function() {
		
		$('#generateTokenButton').click(function(){
			var tokenText = token();
		    $('#activationToken').val(tokenText);
		    
		});
		
		$('#form').parsley();
		
	});
	
}