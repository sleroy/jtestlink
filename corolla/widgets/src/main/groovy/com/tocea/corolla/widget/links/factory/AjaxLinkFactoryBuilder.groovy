/**
 *
 */
package com.tocea.corolla.widget.links.factory

import com.tocea.corolla.widget.links.factory.api.ILinkFactory
import com.tocea.corolla.widget.links.factory.api.IStatelessUserAction
import com.tocea.corolla.widget.links.factory.api.IUserActionFactory

/**
 * This class is a builder to produce ajax links factories.
 *
 * @author sleroy
 *
 */
class AjaxLinkFactoryBuilder {
	/**
	 * Builds a regular ajaxlink factory with stateful user actions.
	 * @param _userActionFactory
	 * @return
	 */
	def static <T extends Serializable> ILinkFactory<T> newAjaxLinkFactory(
			final IUserActionFactory<T> _userActionFactory) {
		return new AjaxLinkUserActionFactory<>(_userActionFactory)
	}

	/**
	 * Builds a ajaxlink factory from stateless action.
	 * @param _userAction
	 * @return
	 */
	def static <T extends Serializable> ILinkFactory<T> newStatelessAjaxLinkFactory(
			final IStatelessUserAction<T> _userAction) {
		return new StatelessActionAjaxlinkFactory<T>(_userAction)
	}

}