/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.tocea.corolla.ui.widgets.links.factory.api

import org.apache.wicket.ajax.AjaxRequestTarget

/**
 *
 * @author rgalerme
 */
interface IAjaxEditAction<T> extends Serializable {
	void selected(T _object,final AjaxRequestTarget _target)
}
