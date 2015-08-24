package com.tocea.corolla.core.plugins.model;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotNull;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlRootElement;

import org.hibernate.validator.constraints.NotBlank;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The Class PluginDefinition describes all the required informations to declare
 * a new plugin.
 */
@XmlRootElement
public class PluginDefinition {
	private static final Logger LOGGER = LoggerFactory.getLogger(PluginDefinition.class);

	/**
	 * Loads a plugin definition.
	 *
	 * @param _nextElement
	 *            the resource
	 * @return the plugin definition.
	 * @throws JAXBException
	 * @throws IOException
	 */
	public static PluginDefinition loadFromXML(final URL _nextElement) throws JAXBException, IOException {
		final JAXBContext jaxbContext = JAXBContext.newInstance(PluginDefinition.class);
		final Unmarshaller m = jaxbContext.createUnmarshaller();
		try (InputStream stream = _nextElement.openStream()) {
			return (PluginDefinition) m.unmarshal(stream);
		} catch (final IOException e) {
			LOGGER.error("Could not load the plugin for the reason {}", e);
		}
		return null;
	}

	@NotBlank
	private String				key;
	@NotBlank
	private String				name;
	@NotBlank
	private String				description;
	@NotBlank
	private String				author;
	@NotBlank
	private String				url;
	@NotBlank
	private String				category;
	@NotNull
	private PluginConditions	conditions	= new PluginConditions();
	@NotNull
	private PluginResources		resources	= new PluginResources();

	private boolean hasAdminSection = false;

	private List<SideMenuItem> sideMenuItems = new ArrayList<SideMenuItem>();

	private List<SideMenuAction> projectMenuItems = new ArrayList<SideMenuAction>();

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

	public String getKey() {
		return key;
	}

	public String getName() {
		return name;
	}

	public List<SideMenuAction> getProjectMenuItems() {
		return projectMenuItems;
	}

	public PluginResources getResources() {
		return resources;
	}

	public List<SideMenuItem> getSideMenuItems() {
		return sideMenuItems;
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

	public void setKey(final String _key) {
		key = _key;
	}

	public void setName(final String _name) {
		name = _name;
	}

	public void setProjectMenuItems(final List<SideMenuAction> _projectMenuItems) {
		projectMenuItems = _projectMenuItems;
	}

	public void setResources(final PluginResources _resources) {
		resources = _resources;
	}

	public void setSideMenuItems(final List<SideMenuItem> _sideMenuActions) {
		sideMenuItems = _sideMenuActions;
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
