/**
 *
 */
package com.tocea.corolla.ui.widgets.links.factory

import java.io.Serializable;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.html.link.AbstractLink;
import org.apache.wicket.model.IModel;

import com.tocea.corolla.ui.widgets.links.factory.api.ILinkFactory;
import com.tocea.corolla.ui.widgets.links.factory.api.IStatelessUserAction;

/**
 * This class defines a stateless action ajaxlink factory.
 *
 * @author sleroy
 *
 * @param <T>
 */
public final class StatelessActionAjaxlinkFactory<T extends Serializable>
implements ILinkFactory<T> {

	IStatelessUserAction<T>	userAction



	@Override
	public AbstractLink newLink(final String _widgetID, final IModel<T> _object) {

		return new AjaxLink<T>(_widgetID, _object) {

					@Override
					public void onClick(final AjaxRequestTarget _target) {
						StatelessActionAjaxlinkFactory.this.userAction.doAction(_target,
								(T) this.getDefaultModelObject())

					}
				}
	}
}