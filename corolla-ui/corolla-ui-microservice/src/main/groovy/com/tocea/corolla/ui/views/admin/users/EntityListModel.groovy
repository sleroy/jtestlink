/**
 *
 */
package com.tocea.corolla.ui.views.admin.users

import org.apache.wicket.model.util.ListModel

/**
 * @author sleroy
 *
 */
public class EntityListModel extends ListModel<Integer> {

	public EntityListModel(final List<?> items) {
		List<Integer> ids = items.collect {
			it -> it.id
		}
		setObject ids
	}
}
