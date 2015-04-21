package com.tocea.corolla.ui.widgets.actions

import java.lang.invoke.MethodHandleImpl.BindCaller.T
import java.util.List

import org.apache.wicket.ajax.AjaxRequestTarget
import org.apache.wicket.markup.html.WebPage
import org.apache.wicket.model.IModel

import com.google.common.collect.Lists
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
abstract class AbstractDeleteAction<T extends Serializable> implements
IDeleteAction<T> {

	private static final String	DIALOGDELETE	= "dialogdelete"

	private final WebPage		page

	private final String		wicketId

	private IModel<List<T>>		liste

	private MessageDialog		dialog

	private T					value

	public AbstractDeleteAction(final WebPage _page,
	final IModel<List<T>> _liste) {
		this(_page, DIALOGDELETE, _liste)
	}

	public AbstractDeleteAction(final WebPage _page, final String _wicketId,
	final IModel<List<T>> _liste) {

		this.page = _page
		this.wicketId = _wicketId
		this.liste = _liste

		this.dialog = new MessageDialog(wicketId,
				page.getString("global.popup.warning.title"),
				page.getString("global.popup.warning.delete.confirm"),
				DialogButtons.OK_CANCEL,
				DialogIcon.WARN) {

					@Override
					public void onClose(final AjaxRequestTarget art,
							final DialogButton button) {

						if (button != null && button.toString().equals(LBL_OK)) {
							AbstractDeleteAction.this.deleteAction(AbstractDeleteAction.this.value)
							art.add(AbstractDeleteAction.this.page)
						}
					}
				}
		page.add(this.dialog)
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * org.komea.product.wicket.widget.ajaxlink.api.IStatelessUserAction#doAction
	 * (org.apache.wicket.ajax.AjaxRequestTarget, java.lang.Object)
	 */
	@Override
	public void doAction(final AjaxRequestTarget _target, final T _value) {
		this.value = _value

		this.openDialog(_target, _value)
	}

	/**
	 * @return the page
	 */
	public WebPage getPage() {
		return this.page
	}

	/**
	 * @return the wicketId
	 */
	public String getWicketId() {
		return this.wicketId
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * org.komea.product.wicket.widget.ajaxlink.api.IDeleteAction#delete(java
	 * .io.Serializable)
	 */
	protected void deleteAction(final T _item) {
		final List<T> itemList = Lists.newArrayList(this.liste.getObject())
		itemList.remove(_item)
		this.liste.setObject(itemList)
	}

	/**
	 * Opends thze dialog.
	 *
	 * @param _target
	 */
	protected void openDialog(final AjaxRequestTarget _target, final T _value) {
		this.dialog.open(_target)
	}
}