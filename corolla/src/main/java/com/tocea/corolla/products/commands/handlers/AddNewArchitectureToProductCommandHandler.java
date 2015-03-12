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
import com.tocea.corolla.products.domain.ProductArchitecture;
import com.tocea.corolla.products.domain.ProductArchitectureType;

/**
 * @author sleroy
 *
 */
@CommandHandler
@Transactional
public class AddNewArchitectureToProductCommandHandler
implements ICommandHandler<AddNewArchitectureToProductCommand, ProductArchitecture> {

	@Autowired
	private IProductDAO					productDAO;

	@Autowired
	private IProductArchitectureTypeDAO	productArchitectureTypeDAO;
	@Autowired
	private IProductArchitectureDAO		productArchitectureDAO;

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * com.tocea.corolla.cqrs.handler.ICommandHandler#handle(java.lang
	 * .Object)
	 */
	@Override
	public ProductArchitecture handle(
			final AddNewArchitectureToProductCommand _command) {
		final Product product = this.productDAO.findOne(_command.getProductID());
		Validate.notNull(product, "Product should exist");
		final ProductArchitectureType productArchitectureType = this.productArchitectureTypeDAO.findOne(_command.getArchitectureTypeID());
		Validate.notNull(	productArchitectureType,
				"Architecture type should exist");
		final List<ProductArchitecture> architectures = product.getArchitectures();
		final ProductArchitecture productArchitecture = new ProductArchitecture();
		productArchitecture.setOwner(product);
		productArchitecture.setType(productArchitectureType);
		architectures.add(productArchitecture);
		product.setArchitectures(architectures);

		this.productArchitectureDAO.save(productArchitecture);
		this.productDAO.save(product);

		return productArchitecture;
	}
}
