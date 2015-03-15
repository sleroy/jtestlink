/**
 *
 */
package com.tocea.corolla.widgets.gravatar;

import org.apache.wicket.request.resource.DynamicImageResource;

/**
 * @author sleroy
 *
 */
public final class GravatarImageResource extends DynamicImageResource
{


	private final String               email;
	private final int                  expectedSize;
	private final GravatarDefaultImage defaultImage;



	/**
	 * @param _email
	 * @param _expectedSize
	 * @param _defaultImage
	 */
	public GravatarImageResource(
			final String _email,
			final int _expectedSize,
			final GravatarDefaultImage _defaultImage) {


		this.email = _email;
		this.expectedSize = _expectedSize;
		this.defaultImage = _defaultImage;
	}


	@Override
	protected byte[] getImageData(final Attributes _attributes) {


		final Gravatar gravatar = new Gravatar();
		gravatar.setDefaultImage(this.defaultImage);
		gravatar.setRating(GravatarRating.GENERAL_AUDIENCES);
		gravatar.setSize(this.expectedSize);

		return gravatar.download(this.email);
	}
}