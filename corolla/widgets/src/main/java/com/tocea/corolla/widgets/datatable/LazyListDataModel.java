package com.tocea.corolla.widgets.datatable;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

import org.apache.wicket.extensions.markup.html.repeater.data.sort.ISortState;
import org.apache.wicket.extensions.markup.html.repeater.data.table.ISortableDataProvider;
import org.apache.wicket.extensions.markup.html.repeater.util.SingleSortState;
import org.apache.wicket.markup.repeater.data.ListDataProvider;
import org.apache.wicket.model.IModel;

/**
 * This class implements a data provider that delegates the loading to an model. This model may be lazy or not. This dataprovider also handles sorting.
 * @author sleroy
 *
 * @param <T>
 */
public final class LazyListDataModel<T extends Serializable> extends ListDataProvider<T> implements
ISortableDataProvider<T, String>
{


	private final IModel<List<T>>	modelList;


	public LazyListDataModel(final IModel<List<T>> _modelList) {


		super(Collections.EMPTY_LIST);
		this.modelList = _modelList;


	}


	@Override
	public ISortState<String> getSortState() {


		return new SingleSortState<String>();
	}


	@Override
	protected List<T> getData() {

		return this.modelList.getObject();
	}


}
