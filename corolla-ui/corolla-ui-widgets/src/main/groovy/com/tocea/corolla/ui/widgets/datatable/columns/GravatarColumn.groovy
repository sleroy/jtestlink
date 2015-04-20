/**
 *
 */
package com.tocea.corolla.ui.widgets.datatable.columns

import groovy.transform.InheritConstructors

import org.apache.wicket.extensions.markup.html.repeater.data.grid.ICellPopulator
import org.apache.wicket.extensions.markup.html.repeater.data.table.AbstractColumn
import org.apache.wicket.markup.repeater.Item
import org.apache.wicket.model.IModel
import org.apache.wicket.model.PropertyModel

import com.tocea.corolla.ui.widgets.gravatar.GravatarPanel
import com.tocea.corolla.users.domain.User

@InheritConstructors
class GravatarColumn extends AbstractColumn<User, String> {

	static final int DEFAUT_SIZE = 32


	def void populateItem(final Item<ICellPopulator<User>> _cellItem,
			final String _componentId, final IModel<User> _rowModel) {
		def emailModel = new PropertyModel<String>(_rowModel.object, "email")
		_cellItem.add(new GravatarPanel(_componentId, emailModel, DEFAUT_SIZE))
	}
}
