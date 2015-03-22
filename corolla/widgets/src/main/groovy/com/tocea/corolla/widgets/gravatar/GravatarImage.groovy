/**
 *
 */

package com.tocea.corolla.widgets.gravatar

import org.apache.wicket.markup.html.image.Image
import org.apache.wicket.model.IModel
import org.apache.wicket.model.Model

/**
 * This class defines a wicket component to display a gravatar picture.
 *
 * @author sleroy
 */
public class GravatarImage extends Image {

	public GravatarImage(final String _id, final String _email,
	final GravatarDefaultImage _defaultImage, final Integer _expectedSize) {

		this(_id, Model.of(_email), _expectedSize, _defaultImage)
	}


	public GravatarImage(final String _id, final IModel<String> _email,
	final GravatarDefaultImage _defaultImage, final Integer _expectedSize) {

		super(_id, new GravatarImageResource(_email, _expectedSize, _defaultImage))
	}
}
