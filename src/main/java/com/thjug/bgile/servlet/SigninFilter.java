/*
 * Attribution
 * CC BY
 * This license lets others distribute, remix, tweak,
 * and build upon your work, even commercially,
 * as long as they credit you for the original creation.
 * This is the most accommodating of licenses offered.
 * Recommended for maximum dissemination and use of licensed materials.
 *
 * http://creativecommons.org/licenses/by/3.0/
 * http://creativecommons.org/licenses/by/3.0/legalcode
 */
package com.thjug.bgile.servlet;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Singleton;

/**
 *
 * @author @nuboat
 */
@Singleton
public final class SigninFilter extends DefaultFilter {

	private static final Logger LOG = LoggerFactory.getLogger(SigninFilter.class);

	@Override
	public void doFilter(final ServletRequest request, final ServletResponse response, final FilterChain chain)
			throws IOException, ServletException {

		final HttpServletRequest httpRequest = (HttpServletRequest) request;
		final String servletpath = httpRequest.getServletPath();
		if (servletpath.contains("javax.faces.resource")) {
			chain.doFilter(request, response);
			return;
		}
		LOG.debug("request: {}", servletpath);

		final Subject currentUser = SecurityUtils.getSubject();
		if (currentUser.isAuthenticated() && servletpath.contains("signin")) {
			redirectToDashboard(request, response);
			return;
		}

		chain.doFilter(request, response);
	}

}
