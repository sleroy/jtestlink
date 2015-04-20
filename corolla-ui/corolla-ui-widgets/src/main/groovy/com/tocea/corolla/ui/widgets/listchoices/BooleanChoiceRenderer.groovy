/**
 *
 */
package com.tocea.corolla.ui.widgets.listchoices

import org.apache.wicket.markup.html.form.ChoiceRenderer


/**
 * Display a boolean value adapted for a listchoice.
 * @author sleroy
 *
 */
class BooleanChoiceRenderer extends ChoiceRenderer<Boolean>{

	def String trueDisplay = "Enabled"
	def String falseDisplay =" Disabled"

	/**
	 * Display a value.
	 */
	@Override
	public Object getDisplayValue(Boolean object) {
		if(object== null)
			super.getDisplayValue(object)

		return object ? trueDisplay : falseDisplay
	}
}
