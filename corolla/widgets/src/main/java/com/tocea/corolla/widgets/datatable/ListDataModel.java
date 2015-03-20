package com.tocea.corolla.widgets.datatable;

import java.io.Serializable;
import java.util.List;

import org.apache.wicket.extensions.markup.html.repeater.data.sort.ISortState;
import org.apache.wicket.extensions.markup.html.repeater.data.table.ISortableDataProvider;
import org.apache.wicket.extensions.markup.html.repeater.util.SingleSortState;
import org.apache.wicket.markup.repeater.data.ListDataProvider;

/**
 * Builds a list data model with sort ability.
 * @author sleroy
 *
 * @param <T>
 */
public final class ListDataModel<T extends Serializable> extends ListDataProvider<T> implements
ISortableDataProvider<T, String>
{


	public ListDataModel(final List<T> _modelList) {


		super(_modelList);

	}


	/*
	 * (non-Javadoc)
	 * @see org.apache.wicket.extensions.markup.html.repeater.data.sort.ISortStateLocator#getSortState()
	 */
	@Override
	public ISortState<String> getSortState() {


		return new SingleSortState<String>();
	}


}
