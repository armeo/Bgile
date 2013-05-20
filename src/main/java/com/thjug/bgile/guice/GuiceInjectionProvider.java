package com.thjug.bgile.guice;

import javax.servlet.ServletContext;
import com.google.inject.Injector;
import com.sun.faces.spi.InjectionProvider;
import com.sun.faces.spi.InjectionProviderException;
import com.sun.faces.vendor.WebContainerInjectionProvider;

public final class GuiceInjectionProvider implements InjectionProvider {

	/**
	 * default injector provided by the web container.
	 */
	private static final WebContainerInjectionProvider con = new WebContainerInjectionProvider();

	/**
	 * Custom guice injector that will load our modules.
	 */
	private static Injector injector;

	public GuiceInjectionProvider(final ServletContext context) {
		injector = GuiceInjectorFactory.getInjector();
	}

	@Override
	public final void inject(final Object managedBean) throws InjectionProviderException {
		// allow the default injector to inject the bean.
		con.inject(managedBean);
		// then inject with the google injector.
		injector.injectMembers(managedBean);
	}

	@Override
	public final void invokePostConstruct(final Object managedBean) throws InjectionProviderException {
		// don't do anything here for guice, just let the default do its thing
		con.invokePostConstruct(managedBean);
	}

	@Override
	public final void invokePreDestroy(final Object managedBean) throws InjectionProviderException {
		con.invokePreDestroy(managedBean);
	}
}
