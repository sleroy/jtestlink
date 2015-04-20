package com.tocea.corolla.ui.views.admin.users

import org.apache.wicket.ajax.AjaxRequestTarget
import org.apache.wicket.markup.html.WebPage
import org.apache.wicket.model.IModel

import com.googlecode.wicket.jquery.ui.widget.dialog.DialogButton
import com.googlecode.wicket.jquery.ui.widget.dialog.DialogButtons
import com.googlecode.wicket.jquery.ui.widget.dialog.DialogIcon
import com.googlecode.wicket.jquery.ui.widget.dialog.MessageDialog
import com.tocea.corolla.ui.widgets.links.factory.api.IDeleteAction

/**
 * This class defines the basic behaviour to delete items.
 *
 * @author rgalerme
 */
abstract class DialogDeleteAction<T extends Serializable>  implements Serializable {

	def MessageDialog		dialog

	def T					value


	DialogDeleteAction(final WebPage page, final String wicketId, final String title, final String message) {
		this.dialog = new MessageDialog(wicketId,
				title,
				message,
				DialogButtons.OK_CANCEL,
				DialogIcon.WARN) {

					@Override
					public void onClose(final AjaxRequestTarget art,
							final DialogButton button) {

						if (button != null && button.toString().equals(LBL_OK)) {
							DialogDeleteAction.this.deletion(DialogDeleteAction.this.value)
							art.add(page)
						}
					}
				}
		page.add(this.dialog)
	}




	public void doAction(final AjaxRequestTarget _target, final T _value) {
		this.value = _value

		this.dialog.open(_target)
	}

	public void deletion(T _value) {
	}
}