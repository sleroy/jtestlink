/**
 *
 */
package com.tocea.corolla.ui.widgets.gravatar

import org.apache.wicket.markup.html.panel.Panel

/**
 * This class defines a gravatar panel.
 *
 * @author sleroy
 *
 */
class GravatarPanel extends Panel {

	int	size

	GravatarPanel(_id, _emailModel, _size) {
		super(_id, _emailModel)
		this.size = _size
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.apache.wicket.MarkupContainer#onInitialize()
	 */
	@Override
	protected void onInitialize() {

		super.onInitialize()
		this.add(
				new GravatarImage(
				"pic",
				this.defaultModel,
				GravatarDefaultImage.IDENTICON,
				this.size)
				)
	}
}
