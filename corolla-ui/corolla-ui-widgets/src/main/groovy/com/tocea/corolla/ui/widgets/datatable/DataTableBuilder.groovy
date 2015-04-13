package com.tocea.corolla.ui.widgets.datatable

import org.apache.wicket.AttributeModifier
import org.apache.wicket.extensions.markup.html.repeater.data.table.DataTable
import org.apache.wicket.extensions.markup.html.repeater.data.table.DefaultDataTable
import org.apache.wicket.extensions.markup.html.repeater.data.table.HeadersToolbar
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn
import org.apache.wicket.extensions.markup.html.repeater.data.table.ISortableDataProvider
import org.apache.wicket.extensions.markup.html.repeater.data.table.PropertyColumn
import org.apache.wicket.model.Model

class DataTableBuilder<T extends Serializable, S> {

	/**
	 * New builder to create a table.
	 *
	 * @param _id
	 * @return
	 */
	def static <T extends Serializable, S> DataTableBuilder<T, S> newTable(
			final String _id) {

		return new DataTableBuilder<T, S>(_id)
	}

	String						caption
	List<IColumn<T, S>>			columns		= new ArrayList<IColumn<T, S>>(20)
	ISortableDataProvider<T, S>	dataProvider
	boolean						headers
	String						id			= ""
	int							rowsPerPage	= 5
	String tableCssClass = "table table-striped table-bordered table-hover table-condensed tablesorter"

	DataTableBuilder(final String _id) {

		super()
		this.id = _id
	}

	DataTableBuilder<T,S> addColumn(final IColumn<T, S> _abstractColumn) {

		this.columns.add _abstractColumn
		return this
	}

	DataTableBuilder<T, S> addColumn(final String _columName,
			final String _property) {

		final PropertyColumn<T, S> propertyColumn = new PropertyColumn<>(Model.of(_columName),
				_property)
		this.columns.add propertyColumn
		return this
	}

	DataTableBuilder<T, S> addColumn(final String _columName,
			final String _property, final S _sortProperty) {

		this.columns.add(new PropertyColumn<T, S>(Model.of(_columName),
				_sortProperty,
				_property))
		return this
	}

	DataTableBuilder<T, S> addColumn(final String _columName,
			final String _property, final String _cssClass) {

		this.columns.add(new PropertyColumn<T, S>(Model.of(_columName),
				_property) {

					@Override
					String getCssClass() {

						return _cssClass
					}
				})
		return this
	}

	DataTableBuilder<T, S> addColumn(final String _columName,
			final String _property, final String _cssClass,
			final S _sortProperty) {

		this.columns.add(new PropertyColumn<T, S>(Model.of(_columName),
				_sortProperty,
				_property) {

					@Override
					String getCssClass() {

						return _cssClass
					}
				})
		return this
	}

	/**
	 * Builds the datatable and returns it.
	 */
	DataTable<T, S> build() {

		DataTable<T, S> dataTable = null

		final String captionName = this.caption
		if (captionName != null) {
			dataTable = new DefaultTableModelWithCaption<T, S>(this.id,
					this.columns,
					this.dataProvider,
					this.rowsPerPage,
					captionName)
		} else {
			dataTable = new DefaultDataTable<T, S>(this.id,
					this.columns,
					this.dataProvider,
					this.rowsPerPage)
		}


		dataTable.add(new AttributeModifier("class",
				this.tableCssClass))
		if (this.headers) {
			dataTable.add(new HeadersToolbar<S>(dataTable, null))
		}
		this.id = null
		this.caption = null
		this.columns = new ArrayList<>()
		this.dataProvider = null
		this.headers = false

		return dataTable
	}

	DataTableBuilder<T, S> displayRows(final int _numberRows) {

		this.rowsPerPage = _numberRows
		return this
	}

	/**
	 * @return the tableCssClass
	 */
	String getTableCssClass() {
		return this.tableCssClass
	}

	DataTableBuilder<T, S> withCaption(final String _captionName) {

		this.caption = _captionName
		return this
	}

	DataTableBuilder<T, S> withData(
			final ISortableDataProvider<T, S> _dataProvider) {

		this.dataProvider = _dataProvider
		return this
	}

	DataTableBuilder<T, S> withHeaders() {

		this.headers = true
		return this
	}

	DataTableBuilder withListData(
			final List<T> _items) {

		this.dataProvider = new ListDataModel(_items)
		return this
	}

	/**
	 * @param _tableCssClass the tableCssClass to set
	 * @return
	 */
	DataTableBuilder<T, S> withTableCssClass(final String _tableCssClass) {
		this.tableCssClass = _tableCssClass
		return this
	}
}
