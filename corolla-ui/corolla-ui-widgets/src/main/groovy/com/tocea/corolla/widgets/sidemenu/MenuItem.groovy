/**
 *
 */
package com.tocea.corolla.widgets.sidemenu

import org.apache.wicket.request.mapper.parameter.PageParameters

/**
 * This class defines a menu item.
 *
 * @author sleroy
 *
 */
class MenuItem  implements Serializable{
	Class<?>		page

	String			icon

	String			text

	PageParameters	pageParameters

	MenuItem(final Class<?> _page, final String _icon, final String _text) {
		this(_page,_icon, _text, new PageParameters())
	}


	MenuItem(final Class<?> _page, final String _icon,
	final String _text, final PageParameters _pageParameters) {
		super()
		this.page = _page
		this.icon = _icon
		this.text = _text
		this.pageParameters = _pageParameters
	}
}
