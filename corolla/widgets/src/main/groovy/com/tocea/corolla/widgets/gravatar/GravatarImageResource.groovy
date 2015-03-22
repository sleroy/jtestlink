/**
 *
 */
package com.tocea.corolla.widgets.gravatar

import org.apache.wicket.model.IModel
import org.apache.wicket.request.resource.DynamicImageResource
import org.apache.wicket.request.resource.IResource.Attributes

/**
 * @author sleroy
 *
 */
class GravatarImageResource extends DynamicImageResource
{


	IModel<String>               email
	int                  expectedSize
	GravatarDefaultImage defaultImage



	GravatarImageResource(_email,_expectedSize, GravatarDefaultImage _defaultImage) {


		this.email = _email
		this.expectedSize = _expectedSize
		this.defaultImage = _defaultImage
	}


	protected byte[] getImageData(final Attributes _attributes) {


		final Gravatar gravatar = new Gravatar()
		gravatar.defaultImage = this.defaultImage
		gravatar.rating = GravatarRating.GENERAL_AUDIENCES
		gravatar.size = this.expectedSize

		gravatar.download this.email.object
	}
}