/**
 *
 */
package com.tocea.corolla.widgets.sidemenu;

import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

/**
 * This class defines the side menu that provides entries to navigate.
 *
 * @author sleroy
 *
 */
public class SideMenuPanel extends Panel {
	private final List<MenuItem> menuItems = new ArrayList<MenuItem>();

	public SideMenuPanel(final String _id) {
		super(_id);
	}

	public SideMenuPanel(final String _id, final IModel<?> _model) {
		super(_id, _model);
	}

	public void addMenuItem(final Class<?> _page, final String _icon, final String _text) {
		this.menuItems.add(new MenuItem(_page, _icon, _text));
	}


	/* (non-Javadoc)
	 * @see org.apache.wicket.MarkupContainer#onInitialize()
	 */
	@Override
	protected void onInitialize() {
		super.onInitialize();
		final ListView<MenuItem> menuItemsComponent = new ListView<MenuItem>("menu-side-repeater", this.menuItems) {

			@Override
			protected void populateItem(final ListItem<MenuItem> _arg0) {
				final MenuItem pageLink = _arg0.getModelObject();
				final BookmarkablePageLink bookmarkablePageLink = new BookmarkablePageLink("menu-side-item-link",pageLink.getPage());
				final Label textLabel = new Label("menu-side-item-label", Model.of(pageLink.getText()));
				final Label iconLabel = new Label("menu-side-item-icon");
				iconLabel.add(new AttributeModifier("class", Model.of("glyphicon " + pageLink.getIcon() )));

				bookmarkablePageLink.add(textLabel);
				bookmarkablePageLink.add(iconLabel);

				_arg0.add(bookmarkablePageLink);

			}

		};
		this.add(menuItemsComponent);

	}


}
