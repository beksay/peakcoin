package org.infosystema.peakcoin.util.web;

import java.io.Serializable;
import java.util.Locale;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.WeakHashMap;

import javax.enterprise.context.ApplicationScoped;
import javax.faces.context.FacesContext;

/**
 * 
 * @author Kuttubek Aidaraliev
 *
 */

@ApplicationScoped
public class Messages implements Serializable {
	
	private static final long serialVersionUID = -2065651128811043538L;

	private static Map<Locale, ResourceBundle> resources = new WeakHashMap<>();
	
	public static String getMessage(String message) {
		Locale locale = FacesContext.getCurrentInstance().getViewRoot().getLocale();
		ResourceBundle bundle = resources.get(locale);
		if(bundle == null) {
			bundle = ResourceBundle.getBundle("org.infosystema.peakcoin.resources.messages", locale);
			resources.put(locale, bundle);
		}
		
		try {
			 return bundle.getString(message);
		} catch(MissingResourceException e){
			return "??" + message + "??";
		}
	}
	
	public static String getMessage(String message, Locale locale) {
		ResourceBundle bundle = resources.get(locale);
		if(bundle == null) {
			bundle = ResourceBundle.getBundle("org.infosystema.peakcoin.resources.messages", locale);
			resources.put(locale, bundle);
		}
		
		try {
			 return bundle.getString(message);
		} catch(MissingResourceException e){
			return "??" + message + "??";
		}
	}
}

