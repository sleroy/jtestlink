/**
 *
 */
package com.tocea.corolla.widgets.sidemenu;

import java.io.Serializable;

import org.apache.wicket.request.mapper.parameter.PageParameters;

/**
 * This class defines a menu item.
 *
 * @author sleroy
 *
 */
public class MenuItem  implements Serializable{
	private Class<?>		page;

	private String			icon;

	private String			text;

	private PageParameters	pageParameters;

	public MenuItem(final Class<?> _page, final String _icon, final String _text) {
		super();
		this.page = _page;
		this.icon = _icon;
		this.text = _text;
	}

	public MenuItem(final Class<?> _page, final String _icon,
			final String _text, final PageParameters _pageParameters) {
		super();
		this.page = _page;
		this.icon = _icon;
		this.text = _text;
		this.pageParameters = _pageParameters;
	}

	public String getIcon() {
		return this.icon;
	}

	public Class<?> getPage() {
		return this.page;
	}

	public PageParameters getPageParameters() {
		return this.pageParameters;
	}

	public String getText() {
		return this.text;
	}

	public void setIcon(final String _icon) {
		this.icon = _icon;
	}

	public void setPage(final Class<?> _page) {
		this.page = _page;
	}

	public void setPageParameters(final PageParameters _pageParameters) {
		this.pageParameters = _pageParameters;
	}

	public void setText(final String _text) {
		this.text = _text;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "MenuItem [page=" + this.page + ", icon=" + this.icon + ", text=" + this.text
				+ ", pageParameters=" + this.pageParameters + "]";
	}
}
