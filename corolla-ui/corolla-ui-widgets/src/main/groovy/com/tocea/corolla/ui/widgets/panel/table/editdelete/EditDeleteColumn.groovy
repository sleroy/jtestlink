package com.tocea.corolla.ui.widgets.panel.table.editdelete

import java.lang.invoke.MethodHandleImpl.BindCaller.T

import org.apache.wicket.extensions.markup.html.repeater.data.grid.ICellPopulator
import org.apache.wicket.extensions.markup.html.repeater.data.table.AbstractColumn
import org.apache.wicket.markup.repeater.Item
import org.apache.wicket.model.IModel
import org.apache.wicket.model.Model

import com.google.common.base.Predicate
import com.google.common.base.Predicates
import com.tocea.corolla.ui.widgets.links.factory.api.ILinkFactory

/**
 * Defines a column with edit/delete buttons.
 * @author sleroy
 *
 * @param <T>
 * @param <S>
 */
class EditDeleteColumn<T extends Serializable, S> extends
AbstractColumn<T, S> {

	ILinkFactory<T>	editActionFactory
	ILinkFactory<T>	deleteActionFactory
	Predicate<T>			editPredicate
	Predicate<T>			deletePredicate

	EditDeleteColumn(_displayModel,	_editActionFactory,	_deleteActionFactory) {

		this(_displayModel, _editActionFactory, _deleteActionFactory, Predicates.<T> alwaysTrue(), Predicates.<T> alwaysTrue())
	}

	EditDeleteColumn(_displayModel,	_editActionFactory,	_deleteActionFactory,	_editPredicate,	_deletePredicate) {

		super(Model.of(_displayModel))
		this.editActionFactory = _editActionFactory
		this.deleteActionFactory = _deleteActionFactory
		this.editPredicate = _editPredicate
		this.deletePredicate = _deletePredicate
	}

	def void populateItem(final Item<ICellPopulator<T>> _cellItem,
			final String _componentId, final IModel<T> _rowModel) {

		_cellItem.add(new EditDeleteColumnPanel<>(_componentId,
				_rowModel,
				this.deleteActionFactory,
				this.editActionFactory,this.editPredicate, this.deletePredicate))
	}
}