/**
 *
 */
package com.tocea.corolla.widgets.sidemenu

import groovy.transform.InheritConstructors

import org.apache.wicket.AttributeModifier
import org.apache.wicket.markup.html.basic.Label
import org.apache.wicket.markup.html.link.BookmarkablePageLink
import org.apache.wicket.markup.html.list.ListItem
import org.apache.wicket.markup.html.list.ListView
import org.apache.wicket.markup.html.panel.Panel
import org.apache.wicket.model.Model

/**
 * This class defines the side menu that provides entries to navigate.
 *
 * @author sleroy
 *
 */
@InheritConstructors
class SideMenuPanel extends Panel {
	List<MenuItem> menuItems = new ArrayList<>()

	SideMenuPanel() {
		super("sidemenu")
	}


	def void addMenuItem(_page,  _icon,  _text) {
		this.menuItems.add(new MenuItem(_page, _icon, _text))
	}


	protected void onInitialize() {
		super.onInitialize()
		final ListView<MenuItem> menuItemsComponent = new ListView<MenuItem>("menu-side-repeater", this.menuItems) {

					protected void populateItem(final ListItem<MenuItem> _arg0) {
						final MenuItem pageLink = _arg0.getModelObject()
						final BookmarkablePageLink bookmarkablePageLink = new BookmarkablePageLink("menu-side-item-link",pageLink.getPage())
						final Label textLabel = new Label("menu-side-item-label", Model.of(pageLink.getText()))
						final Label iconLabel = new Label("menu-side-item-icon")
						iconLabel.add(new AttributeModifier("class", Model.of("glyphicon " + pageLink.getIcon() )))

						bookmarkablePageLink.add(textLabel)
						bookmarkablePageLink.add(iconLabel)

						_arg0.add(bookmarkablePageLink)
					}
				}
		this.add(menuItemsComponent)
		addCustomPanel()
	}

	def addCustomPanel() {
		add(new Label("customPanel"))
	}
}
