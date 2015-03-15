package com.tocea.corolla.views;

import java.util.List;

import org.apache.wicket.bootstrap.Bootstrap;
import org.apache.wicket.markup.head.CssHeaderItem;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.JavaScriptHeaderItem;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.StatelessForm;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.resource.CssResourceReference;
import org.apache.wicket.request.resource.JavaScriptResourceReference;
import org.apache.wicket.spring.injection.annot.SpringBean;

import com.tocea.corolla.products.domain.Product;
import com.tocea.corolla.views.api.IViewAPI;
import com.tocea.corolla.views.bean.ProductSelectionBean;
import com.tocea.corolla.widgets.renderer.ProductChoiceRenderer;
import com.tocea.corolla.widgets.sidemenu.SideMenuPanel;

/**
 * Layout page
 *
 * @author Sylvain Leroy
 *
 */
public abstract class LayoutPage extends WebPage {

	@SpringBean
	protected IViewAPI				viewAPI;

	private ProductSelectionBean	productSelection;

	public LayoutPage() {
		super();

	}

	@Override
	public void renderHead(final IHeaderResponse response) {
		super.renderHead(response);

		// this.addCss(response, "static/css/bootstrap.css");
		// this.addCss(response, "static/css/styles.css");
		// this.addJs(response, "js/bootstrap.js");

		Bootstrap.renderHeadPlain(response);
	}

	/**
	 * @param response
	 * @param css1
	 */
	protected void addCss(final IHeaderResponse response, final String css1) {
		final CssResourceReference cssResourceReference = new CssResourceReference(WicketWebApplication.class,
		                                                                           css1);
		response.render(CssHeaderItem.forReference(cssResourceReference));
	}

	/**
	 * @param response
	 * @param css1
	 */
	protected void addJs(final IHeaderResponse response, final String css1) {
		final JavaScriptResourceReference jsResourceReference = new JavaScriptResourceReference(WicketWebApplication.class,
		                                                                                        css1);
		response.render(JavaScriptHeaderItem.forReference(jsResourceReference));
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.apache.wicket.Page#onInitialize()
	 */
	@Override
	protected void onInitialize() {

		super.onInitialize();

		this.setStatelessHint(true);


		this.add(this.useSideMenu());

		final Form<?> form = new StatelessForm<Void>("productSelectForm") {
			@Override
			protected void onSubmit() {

				LayoutPage.this.info("Selected product : "
						+ LayoutPage.this.productSelection.getProduct());

			}
		};

		// Drop down to select a projet
		final ProductChoiceRenderer productChoiceRenderer = new ProductChoiceRenderer();
		form.add(new DropDownChoice<Product>("productSelect",
				new PropertyModel<Product>(this.productSelection, "product"),
				new LoadableDetachableModel<List<Product>>() {
			@Override
			protected List<Product> load() {
				return LayoutPage.this.viewAPI.getProducts();
			}
		},
		productChoiceRenderer));
		this.add(form);



	}

	/**
	 * Returns the side menu for this page.
	 *
	 * @return the side menu.
	 */
	protected abstract SideMenuPanel useSideMenu();
}