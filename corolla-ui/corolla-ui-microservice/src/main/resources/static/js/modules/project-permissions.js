var SELECT_ENTITY_ID 	= "select[name=entityId]";
var SELECT_ENTITY_TYPE 	= "select[name=entityType]";

/**
 * Initialize form
 */
function initProjectMemberForm() {
	
	$(SELECT_ENTITY_TYPE).change(function() {
		var entityType = $(SELECT_ENTITY_TYPE).val();
		selectEntity(entityType);
	});
	
	var entityType = $(SELECT_ENTITY_TYPE).val();
	selectEntity(entityType);

}

/**
 * Initialize select2 widget for selecting an entity
 * when the entity type has been changed
 * @param entityType : the new entity type
 */
function selectEntity(entityType) {
	
	if (entityType == 'USER') {
		
		restAPI.users.all(function(data) {
			var items = [];
			$.each(data.data, function(i, v) {
				items.push({ 
					id: v.id, 
					text: v.firstName+' '+v.lastName 
				});
			});
			updateSelect2(SELECT_ENTITY_ID, items);
			setEntityID();
		});
		
	}else{
		
		restAPI.userGroups.all(function(data) {
			var items = [];
			$.each(data, function(i, v) {
				items.push({ 
					id: v.id, 
					text: v.name 
				});
			});
			updateSelect2(SELECT_ENTITY_ID, items);
			setEntityID();
		});
		
	}
	
}

function setEntityID() {
	
	var originValue = $(SELECT_ENTITY_ID).data('originvalue');
	if (originValue) {
		console.log(originValue);
		$(SELECT_ENTITY_ID).val(originValue);
		$(SELECT_ENTITY_ID).trigger('change');
	}	
	
}

function updateSelect2(selector, items) {
	
	$(selector).html('');
	$(selector).trigger('change');
	
	$(selector).select2({
		data: items
	});
	$(selector).trigger('change');
	
}
