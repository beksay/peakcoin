package org.infosystema.peakcoin.controller.base;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import javax.enterprise.context.ApplicationScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.infosystema.peakcoin.annotation.Logged;
import org.infosystema.peakcoin.domain.Attachment;
import org.infosystema.peakcoin.util.Krypto;
import org.infosystema.peakcoin.util.web.HttpUtil;

 


/**
 * 
 * @author Kuttubek Aidaraliev
 *
 */

@Named
@ApplicationScoped
@Logged
public class UtilController {
	
	public <T> List<T> getAsList(Set<T> set) {
		if(set == null) return Collections.emptyList();
		return new ArrayList<T>(set);
	}
	
	public String getDownloadURL(String key) {
		FacesContext context = FacesContext.getCurrentInstance();
		HttpServletRequest request = (HttpServletRequest)context.getExternalContext().getRequest();
		
		return HttpUtil.getContextUrl(request) + "download?key=" + key;
	}
	
	public String generateKeyAliveCurrentSession(Attachment attachment) {
		if(attachment == null) return null;
		
		try {
			FacesContext context = FacesContext.getCurrentInstance();
			HttpServletRequest request = (HttpServletRequest)context.getExternalContext().getRequest();
			HttpSession session = request.getSession();
			Integer id = attachment.getId();
			
			Krypto krypto = new Krypto();
		    krypto.setKey(new byte[]{0x21, 0x10, 0x51, 0x09, 0x08, 0x70, 0x07, 04});
		    String keyValue = krypto.doEncrypt((session.getId() + "@@@@@@@" + id.toString()).getBytes());
		    keyValue = URLEncoder.encode(keyValue, "UTF-8");
		    
		    return keyValue;
		} catch(Exception e){
			e.printStackTrace();
		}
		
		return null;
	}
	
}
