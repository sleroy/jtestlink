package com.tocea.corolla.core.plugins.model;

/**
 * The Class PluginDefinition describes all the required informations to declare
 * a new plugin.
 */
public class PluginDefinition {
	
	private String name;
	
	private String description;
	
	private String				author;
	private String				url;
	private String				category;
	private PluginConditions	conditions;
	private PluginResources		resources;
	private boolean				hasAdminSection	= false;
	
	public PluginDefinition() {
		super();
	}
	
	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final PluginDefinition other = (PluginDefinition) obj;
		if (name == null) {
			if (other.name != null) {
				return false;
			}
		} else if (!name.equals(other.name)) {
			return false;
		}
		return true;
	}
	
	public String getAuthor() {
		return author;
	}
	
	public String getCategory() {
		return category;
	}
	
	public PluginConditions getConditions() {
		return conditions;
	}
	
	public String getDescription() {
		return description;
	}
	
	public String getName() {
		return name;
	}
	
	public PluginResources getResources() {
		return resources;
	}
	
	public String getUrl() {
		return url;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (name == null ? 0 : name.hashCode());
		return result;
	}
	
	public boolean isHasAdminSection() {
		return hasAdminSection;
	}
	
	public void setAuthor(final String _author) {
		author = _author;
	}
	
	public void setCategory(final String _category) {
		category = _category;
	}
	
	public void setConditions(final PluginConditions _conditions) {
		conditions = _conditions;
	}
	
	public void setDescription(final String _description) {
		description = _description;
	}
	
	public void setHasAdminSection(final boolean _hasAdminSection) {
		hasAdminSection = _hasAdminSection;
	}
	
	public void setName(final String _name) {
		name = _name;
	}
	
	public void setResources(final PluginResources _resources) {
		resources = _resources;
	}
	
	public void setUrl(final String _url) {
		url = _url;
	}
	
	@Override
	public String toString() {
		return "PluginDefinition [name=" + name + ", description=" + description + ", author=" + author + ", url=" + url
		        + ", category=" + category + ", conditions=" + conditions + ", resources=" + resources
		        + ", hasAdminSection=" + hasAdminSection + "]";
	}
	
}
