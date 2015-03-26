package com.tocea.corolla.widgets.datatable

import org.apache.wicket.extensions.markup.html.repeater.data.table.DefaultDataTable
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn
import org.apache.wicket.extensions.markup.html.repeater.data.table.ISortableDataProvider
import org.apache.wicket.model.IModel
import org.apache.wicket.model.Model

class DefaultTableModelWithCaption<T, S> extends DefaultDataTable<T, S> {

	String	captionName

	DefaultTableModelWithCaption(final String _id,
	final List<? extends IColumn<T, S>> _columns,
	final ISortableDataProvider<T, S> _dataProvider,
	final int _rowsPerPage, final String _captionName) {

		super(_id, _columns, _dataProvider, _rowsPerPage)
		this.captionName = _captionName
	}

	@Override
	protected IModel<String> getCaptionModel() {

		return Model.of(this.captionName)
	}
}