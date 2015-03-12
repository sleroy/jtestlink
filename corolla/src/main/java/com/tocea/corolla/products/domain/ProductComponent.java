package com.tocea.corolla.products.domain;

import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.validator.constraints.NotBlank;

import com.tocea.corolla.customfields.domain.CustomField;

/**
 * This class declares a component that is a part of a product.
 *
 * @author sleroy
 *
 */
@Entity()
@Table(name = "product_architectures")
public class ProductComponent implements Serializable {

	@Id
	@GeneratedValue
	private Integer					id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "owner_id")
	private Product					owner;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "componenttype_id")
	private ProductComponentType	type;

	@NotBlank
	@Column(nullable = false, length = 128)
	private String					name;

	@Lob
	@Column(nullable = false)
	private String					description;

	@ManyToOne
	@JoinColumn(name = "parent_id")
	private ProductComponent		parent;

	@OneToMany(mappedBy = "componentOwner")
	private List<CustomField>		customFields;

	@OneToMany(mappedBy = "parent", cascade = CascadeType.ALL)
	private Set<ProductComponent>	children	= new HashSet<ProductComponent>();

	/**
	 * @return the children
	 */
	public Set<ProductComponent> getChildren() {
		return this.children;
	}

	/**
	 * @return the customFields
	 */
	public List<CustomField> getCustomFields() {
		return this.customFields;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return this.description;
	}

	/**
	 * @return the id
	 */
	public Integer getId() {
		return this.id;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * @return the owner
	 */
	public Product getOwner() {
		return this.owner;
	}

	/**
	 * @return the parent
	 */
	public ProductComponent getParent() {
		return this.parent;
	}

	/**
	 * @return the type
	 */
	public ProductComponentType getType() {
		return this.type;
	}

	/**
	 * @param _children
	 *            the children to set
	 */
	public void setChildren(final Set<ProductComponent> _children) {
		this.children = _children;
	}

	/**
	 * @param _customFields
	 *            the customFields to set
	 */
	public void setCustomFields(final List<CustomField> _customFields) {
		this.customFields = _customFields;
	}

	/**
	 * @param _description
	 *            the description to set
	 */
	public void setDescription(final String _description) {
		this.description = _description;
	}

	/**
	 * @param _id
	 *            the id to set
	 */
	public void setId(final Integer _id) {
		this.id = _id;
	}

	/**
	 * @param _name
	 *            the name to set
	 */
	public void setName(final String _name) {
		this.name = _name;
	}

	/**
	 * @param _owner
	 *            the owner to set
	 */
	public void setOwner(final Product _owner) {
		this.owner = _owner;
	}

	/**
	 * @param _parent
	 *            the parent to set
	 */
	public void setParent(final ProductComponent _parent) {
		this.parent = _parent;
	}

	/**
	 * @param _type
	 *            the type to set
	 */
	public void setType(final ProductComponentType _type) {
		this.type = _type;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "ProductComponent [id=" + this.id + ", owner=" + this.owner + ", type="
				+ this.type + ", name=" + this.name + ", description=" + this.description
				+ ", parent=" + this.parent + ", customFields=" + this.customFields
				+ ", children=" + this.children + "]";
	}

}