/**
 *
 */
package com.tocea.corolla.products.commands.handlers;

import java.util.List;

import javax.transaction.Transactional;

import org.apache.commons.lang3.Validate;
import org.springframework.beans.factory.annotation.Autowired;

import com.tocea.corolla.cqrs.annotations.CommandHandler;
import com.tocea.corolla.cqrs.handler.ICommandHandler;
import com.tocea.corolla.products.commands.AddNewArchitectureToProductCommand;
import com.tocea.corolla.products.dao.IProductArchitectureDAO;
import com.tocea.corolla.products.dao.IProductArchitectureTypeDAO;
import com.tocea.corolla.products.dao.IProductDAO;
import com.tocea.corolla.products.domain.Product;
import com.tocea.corolla.products.domain.ProductComponent;
import com.tocea.corolla.products.domain.ProductComponentType;

/**
 * @author sleroy
 *
 */
@CommandHandler
@Transactional
public class AddNewArchitectureToProductCommandHandler
implements
ICommandHandler<AddNewArchitectureToProductCommand, ProductComponent> {

	@Autowired
	private IProductDAO					productDAO;

	@Autowired
	private IProductArchitectureTypeDAO	productArchitectureTypeDAO;
	@Autowired
	private IProductArchitectureDAO		productArchitectureDAO;

	/*
	 * (non-Javadoc)
	 *
	 * @see com.tocea.corolla.cqrs.handler.ICommandHandler#handle(java.lang
	 * .Object)
	 */
	@Override
	public ProductComponent handle(
			final AddNewArchitectureToProductCommand _command) {
		final Product product = this.productDAO.findOne(_command.getProductID());
		Validate.notNull(product, "Product should exist");
		final ProductComponentType productComponentType = this.productArchitectureTypeDAO.findOne(_command.getArchitectureTypeID());
		Validate.notNull(	productComponentType,
				"Architecture type should exist");

		// Get the parent architecture if available
		ProductComponent parentProductArchitecture = null;
		if (_command.getParentArchitectureID() != null) {
			parentProductArchitecture = this.productArchitectureDAO.findOne(_command.getParentArchitectureID());
			Validate.notNull(parentProductArchitecture, "Parent architecture should exist");
		}

		final ProductComponent productComponent = new ProductComponent();
		productComponent.setOwner(product);
		productComponent.setType(productComponentType);
		productComponent.setParent(parentProductArchitecture);
		this.productArchitectureDAO.save(productComponent);

		if (parentProductArchitecture == null) { // Attach to parent
			final List<ProductComponent> architectures = product.getArchitectures();
			architectures.add(productComponent);
			product.setArchitectures(architectures);
			this.productDAO.save(product);

		} else {
			parentProductArchitecture.getChildren().add(productComponent);
			this.productArchitectureDAO.save(parentProductArchitecture);
		}

		return productComponent;
	}
}
