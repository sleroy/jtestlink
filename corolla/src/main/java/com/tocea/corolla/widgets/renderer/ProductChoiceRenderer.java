/**
 *
 */
package com.tocea.corolla.widgets.renderer;

import org.apache.wicket.markup.html.form.ChoiceRenderer;

import com.tocea.corolla.products.domain.Product;

/**
 * @author sleroy
 *
 */
public class ProductChoiceRenderer extends ChoiceRenderer<Product> {

	/* (non-Javadoc)
	 * @see org.apache.wicket.markup.html.form.ChoiceRenderer#getDisplayValue(java.lang.Object)
	 */
	@Override
	public Object getDisplayValue(final Product _object) {

		return _object.getName();
	}

}