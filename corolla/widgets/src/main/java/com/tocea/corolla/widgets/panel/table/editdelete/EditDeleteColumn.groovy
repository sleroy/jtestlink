package com.tocea.corolla.widgets.panel.table.editdelete;

import java.io.Serializable;

import org.apache.wicket.extensions.markup.html.repeater.data.grid.ICellPopulator;
import org.apache.wicket.extensions.markup.html.repeater.data.table.AbstractColumn;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.tocea.corolla.widget.links.factory.api.ILinkFactory;

/**
 * Defines a column with edit/delete buttons.
 * @author sleroy
 *
 * @param <T>
 * @param <S>
 */
public final class EditDeleteColumn<T extends Serializable, S> extends
AbstractColumn<T, S> {

	private final ILinkFactory<T>	editActionFactory;
	private final ILinkFactory<T>	deleteActionFactory;
	private Predicate<T>			editPredicate;
	private Predicate<T>			deletePredicate;

	public EditDeleteColumn(final String _displayModel,
			final ILinkFactory<T> _editActionFactory,
			final ILinkFactory<T> _deleteActionFactory) {

		this(_displayModel, _editActionFactory, _deleteActionFactory, Predicates.<T> alwaysTrue(), Predicates.<T> alwaysTrue());
	}

	public EditDeleteColumn(final String _displayModel,
			final ILinkFactory<T> _editActionFactory,
			final ILinkFactory<T> _deleteActionFactory,
			final Predicate<T> _editPredicate,
			final Predicate<T> _deletePredicate) {

		super(Model.of(_displayModel));
		this.editActionFactory = _editActionFactory;
		this.deleteActionFactory = _deleteActionFactory;
		this.editPredicate = _editPredicate;
		this.deletePredicate = _deletePredicate;
	}

	@Override
	public void populateItem(final Item<ICellPopulator<T>> _cellItem,
			final String _componentId, final IModel<T> _rowModel) {

		_cellItem.add(new EditDeleteColumnPanel<>(_componentId,
				_rowModel,
				this.deleteActionFactory,
				this.editActionFactory,this.editPredicate, this.deletePredicate));

	}
}