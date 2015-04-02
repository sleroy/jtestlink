/**
 *
 */
package com.tocea.corolla.widgets.renderer

import org.apache.commons.math3.stat.descriptive.summary.Product
import org.apache.wicket.markup.html.form.ChoiceRenderer


/**
 * @author sleroy
 *
 */
class ProductChoiceRenderer extends ChoiceRenderer<Product> {

	def Object getDisplayValue(final Product _object) {

		return _object.getName()
	}

}