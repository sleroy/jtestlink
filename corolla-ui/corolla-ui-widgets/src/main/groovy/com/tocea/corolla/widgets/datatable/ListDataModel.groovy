package com.tocea.corolla.widgets.datatable

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
class ListDataModel<T extends Serializable> extends ListDataProvider<T> implements
ISortableDataProvider<T, String> {



	def ISortState<String> getSortState() {


		return new SingleSortState<String>()
	}
}
