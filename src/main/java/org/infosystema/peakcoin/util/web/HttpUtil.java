package org.infosystema.peakcoin.util.web;

import javax.servlet.http.HttpServletRequest;

import org.infosystema.peakcoin.singleton.Configuration;

/***
 * 
 * @author Kuttubek Aidaraliev
 *
 */

public class HttpUtil {
	
	public static String getContextUrl(HttpServletRequest req) {
		return Configuration.getInstance().getProperty("address");		
	}

}
