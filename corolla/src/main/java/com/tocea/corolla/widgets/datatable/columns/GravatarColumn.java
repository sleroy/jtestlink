/**
 *
 */
package com.tocea.corolla.widgets.datatable.columns;

import org.apache.wicket.extensions.markup.html.repeater.data.grid.ICellPopulator;
import org.apache.wicket.extensions.markup.html.repeater.data.table.AbstractColumn;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.model.IModel;

import com.tocea.corolla.users.domain.User;
import com.tocea.corolla.widgets.gravatar.GravatarPanel;

/**
 * @author sleroy
 *
 */
public class GravatarColumn extends AbstractColumn<User, String> {

	public GravatarColumn(final IModel<String> _displayModel) {
		super(_displayModel);
	}

	public GravatarColumn(final IModel<String> _displayModel, final String _sortProperty) {
		super(_displayModel, _sortProperty);
	}


	/* (non-Javadoc)
	 * @see org.apache.wicket.extensions.markup.html.repeater.data.grid.ICellPopulator#populateItem(org.apache.wicket.markup.repeater.Item, java.lang.String, org.apache.wicket.model.IModel)
	 */
	@Override
	public void populateItem(final Item<ICellPopulator<User>> _cellItem,
			final String _componentId, final IModel<User> _rowModel) {
		_cellItem.add(new GravatarPanel(_componentId, _rowModel.getObject().getEmail(), 32));

	}

}
