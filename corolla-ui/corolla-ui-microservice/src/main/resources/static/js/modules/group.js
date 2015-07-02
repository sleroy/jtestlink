
var SELECTED_USERS = '#selected-users';
var INPUT_USER_IDS = 'input[name=userIds]';

function initGroupForm() {
	
	$('.pick-user').click(function() {
		addUser();
		return false;
	});
	
	bindButtons();
	
	/*
	 * Fix bug with the String list retrieved from thymeleaf
	 */
	var s = $(INPUT_USER_IDS).val();
	s = s
		.replace('[', '')
		.replace(']', '')
		.replace(/ +?/g, '');
	$(INPUT_USER_IDS).val(s);
	
}


function bindButtons() {
	
	$('.btnDelete').off('click');
	$('.btnDelete').click(function() {
		var tr = $(this).closest('tr');
		removeUserId(tr.data('id'));
		tr.remove();
		return false;
	});
	
}

function getUserIds() {
	
	var s = $(INPUT_USER_IDS).val();
	
	if (!s || s == '') {
		return [];
	}
	
	return s.split(',');
	
}


function addUserId(id) {
	
	var ids = getUserIds();
	ids.push(id);
	
	$(INPUT_USER_IDS).val(ids);		
	
}

function removeUserId(id) {
	
	var newIds = [];
	$.each(getUserIds(), function(i, v) {
		if (v != id) {
			newIds.push(v);
		}
	});

	$(INPUT_USER_IDS).val(newIds);	
}

function addUser() {
	
	selectUserModal.onSelect(function(login, line) {
		
		console.log(login);
		console.log(line);

		if ($.inArray(login, getUserIds()) == -1) {
					
			var newLine = line.clone();
			
			while(newLine.find('td')[4]) {
				newLine.find('td')[4].remove();
			}
			
			newLine.append(
				'<td><a href="#" class="btn btn-danger btnDelete"><i class="fa fa-trash" /></a></td>'
			);
				
			$(SELECTED_USERS).append(newLine);
			
			addUserId(login);
			
			bindButtons();
			
		}
		
		selectUserModal.hide();
	});
	
	selectUserModal.show();
	
}