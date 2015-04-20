package com.tocea.corolla.ui.widgets.datatable

import java.util.List

import groovy.transform.InheritConstructors

import org.apache.wicket.extensions.markup.html.repeater.data.sort.ISortState
import org.apache.wicket.extensions.markup.html.repeater.data.table.ISortableDataProvider
import org.apache.wicket.extensions.markup.html.repeater.util.SingleSortState
import org.apache.wicket.markup.repeater.data.ListDataProvider

/**
 * Builds a list data model with sort ability.
 * @author sleroy
 *
 * @param <T>
 */
@InheritConstructors
class ClosureDataModel<T extends Serializable> extends ListDataProvider<T> implements
ISortableDataProvider<T, String> {

	def Closure<List<T>> closure

	ClosureDataModel(Closure<List<T>> _closure) {
		closure = _closure
	}

	/* (non-Javadoc)
	 * @see org.apache.wicket.markup.repeater.data.ListDataProvider#getData()
	 */
	@Override
	protected List<T> getData() {

		return closure.doCall()
	}

	def ISortState<String> getSortState() {


		return new SingleSortState<String>()
	}
}
