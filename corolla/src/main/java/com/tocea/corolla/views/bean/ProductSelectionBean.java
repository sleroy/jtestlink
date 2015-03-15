/**
 *
 */
package com.tocea.corolla.views.bean;

import com.tocea.corolla.products.domain.Product;

/**
 * @author sleroy
 *
 */
public class ProductSelectionBean {
	private Product product;

	/**
	 * @return the product
	 */
	public Product getProduct() {
		return this.product;
	}

	/**
	 * @param _product the product to set
	 */
	public void setProduct(final Product _product) {
		this.product = _product;
	}
}
