/**
 *
 */
package com.tocea.corolla.ui.widgets.listchoices

import org.apache.wicket.markup.html.form.DropDownChoice
import org.apache.wicket.model.IModel
import org.apache.wicket.model.util.ListModel

/**
 * This class defines a boolean list choice.
 * @author sleroy
 *
 */

class BooleanListChoice extends DropDownChoice<Boolean> {
	public BooleanListChoice(String _formID, IModel<Boolean> _objectModel) {
		this(
		_formID,
		_objectModel,
		new BooleanChoiceRenderer()
		)
	}

	public BooleanListChoice(String _formID,
	IModel<Boolean> _objectModel,
	final BooleanChoiceRenderer renderer) {
		super(_formID, _objectModel, new ListModel<Boolean>([Boolean.FALSE, Boolean.TRUE]), renderer)
		setNullValid false
	}
}

