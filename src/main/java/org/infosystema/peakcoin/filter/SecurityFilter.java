package org.infosystema.peakcoin.filter;

import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.inject.Inject;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.infosystema.peakcoin.domain.User;
import org.infosystema.peakcoin.util.web.LoginUtil;

/**
 * 
 * @author Kuttubek Aidaraliev
 *
 */

public class SecurityFilter implements Filter {
	
	private static final String PUBLIC = "public";
	private static final String LOGGED = "logged";
	private static final String RESOURCE = "/javax.faces.resource";
	private static final String RESOURCES = "/resources/";
	private static final String CHANGE_PASSWORD = "/view/user/change_password.xhtml";
	
	private Map<String[], String> map;
	 
	@Inject
	private LoginUtil loginUtil;
	 
	@Override 
	public void init(FilterConfig config) throws ServletException {
		map = new HashMap<>();
		parseParams(config); 
	} 
	
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		try {
			HttpServletRequest httpServletRequest = (HttpServletRequest)request;
			String url = httpServletRequest.getRequestURI();
			String contentPath = httpServletRequest.getContextPath();
						
			if(url == null) url = "";
			
			System.out.println("url = " + url);
			System.out.println("resource = " + httpServletRequest.getContextPath() + RESOURCE);
			System.out.println(url.startsWith(httpServletRequest.getContextPath() + RESOURCE));
			
			if(url.startsWith(httpServletRequest.getContextPath() + RESOURCE) || url.contains(RESOURCES)){
				chain.doFilter(request, response);
				return;
			}
			
			Boolean deinedAccess = (Boolean)httpServletRequest.getSession().getAttribute("changePassword");
			
			if(url.startsWith(httpServletRequest.getContextPath() + CHANGE_PASSWORD)){
				chain.doFilter(request, response);
				return;
			}
			else if(deinedAccess != null && deinedAccess){
				deinedAccess(response, httpServletRequest, getChangePasswordPage(httpServletRequest));
				return;
			}
			
			for (Entry<String[], String> entry : map.entrySet()) {
				for (String string : entry.getKey()) {
					if(url.matches("^" + contentPath + string + "$")){
						System.out.println("matches" + contentPath + string);
						User user = (User)httpServletRequest.getSession().getAttribute(LoginUtil.CURRENT_USER_SESSION_KEY);
						if(PUBLIC.equals(entry.getValue())){
							chain.doFilter(request, response);
							return;
						}
						else if(user != null && (loginUtil.userHasRole(user, entry.getValue()) || LOGGED.equals(entry.getValue()))){
							chain.doFilter(request, response);
							return;
						}
					}
				}
			}
			
			System.out.println("access deined to page " + url);
			deinedAccess(response, httpServletRequest, getLoginPage(httpServletRequest));
			
		} catch(Exception e){
			e.printStackTrace();
		}
		
		chain.doFilter(request, response);
	}

	private void deinedAccess(ServletResponse response, HttpServletRequest httpServletRequest, String page) throws IOException {
		HttpServletResponse httpServletResponse = (HttpServletResponse) response;
		httpServletResponse.sendRedirect(page);
	}
	
	@Override
	public void destroy() {
		map = null;
	}
	
	private void parseParams(FilterConfig config){
		Enumeration<String> enumeration = config.getInitParameterNames();
		
		while (enumeration.hasMoreElements()) {
			String param = (String) enumeration.nextElement();
			String url = config.getInitParameter(param);
			if(url == null) return;
			
			map.put(url.split("\\|"), param);
		}
	}
	
	private String getLoginPage(HttpServletRequest httpServletRequest){
		return httpServletRequest.getContextPath() + "/view/user/login.xhtml?faces-redirect=true";
	}
	
	private String getChangePasswordPage(HttpServletRequest httpServletRequest){
		return httpServletRequest.getContextPath() + "/view/user/change_password.xhtml?faces-redirect=true";
	}
}