/**
 *
 */
package com.tocea.corolla.widgets.gravatar;

import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.Model;

/**
 * This class defines a gravatar panel.
 *
 * @author sleroy
 *
 */
public class GravatarPanel extends Panel {

	private final int	size;

	public GravatarPanel(final String _id, final String _email, final int _size) {
		super(_id, Model.of(_email));
		this.size = _size;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.apache.wicket.MarkupContainer#onInitialize()
	 */
	@Override
	protected void onInitialize() {

		super.onInitialize();
		this.add(new GravatarImage("pic",this.getDefaultModelObjectAsString(),  GravatarDefaultImage.IDENTICON, this.size));
	}

}
