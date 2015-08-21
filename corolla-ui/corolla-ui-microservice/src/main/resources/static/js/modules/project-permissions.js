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
		
		$(SELECT_ENTITY_ID).select2({
			ajax: {
				url: restAPI.users.URL,
				dataType: 'json',
				processResults: function(data, page) {
					var items = [];
					$.each(data.data, function(i, v) {
						items.push({ 
							id: v.id, 
							text: v.firstName+' '+v.lastName 
						});
					});
					return { 
						results: items 
					}
				},
				cache: true
			}
		});
		
	}else{
		
		$(SELECT_ENTITY_ID).select2({
			ajax: {
				url: restAPI.userGroups.URL,
				dataType: 'json',
				processResults: function(data, page) {
					var items = [];
					$.each(data, function(i, v) {
						items.push({ 
							id: v.id, 
							text: v.name 
						});
					});
					return { 
						results: items 
					}
				},
				cache: true
			}
		});
		
	}
	
}

