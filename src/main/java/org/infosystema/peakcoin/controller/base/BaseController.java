package org.infosystema.peakcoin.controller.base;

/**
 * 
 * @author Kuttubek Aidaraliev
 *
 */

public abstract class BaseController {
	
	protected String getRootErrorMessage(Exception e) {
        String errorMessage = "Registration failed. See server log for more information";
        if (e == null) {
            return errorMessage;
        }

        Throwable t = e;
        while (t != null) {
            errorMessage = t.getLocalizedMessage();
            t = t.getCause();
        }
        return errorMessage;
    }

}
