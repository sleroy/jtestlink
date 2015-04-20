/**
 *
 */
package com.tocea.corolla.ui.widgets.renderer

import org.apache.wicket.markup.html.form.ChoiceRenderer

import com.tocea.corolla.products.domain.Product


/**
 * @author sleroy
 *
 */
class ProductChoiceRenderer extends ChoiceRenderer<Product> {

	def Object getDisplayValue(final Product _object) {

		return _object.getName()
	}

}