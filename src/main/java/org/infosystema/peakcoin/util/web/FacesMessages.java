package org.infosystema.peakcoin.util.web;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

/**
 * 
 * @author Kuttubek Aidaraliev
 *
 */

public class FacesMessages {

	public static void addMessage(String summary, String detail, String componentId) {
		FacesContext context = FacesContext.getCurrentInstance();
		FacesMessage message = new FacesMessage(summary, detail);
		context.addMessage(componentId, message);
	}
	
}

