package org.infosystema.peakcoin.validator;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

import org.infosystema.peakcoin.util.web.Messages;

/**
 * 
 * @author Kuttubek Aidaraliev
 *
 */

@FacesValidator("passwordValidator")
public class PasswordValidator implements Validator {
	
	@Override
	public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
		System.out.println("validate");
		if(value == null) generateError("noSatisfiedPassword");
		
		if(value.toString().length() < 8) generateError("noSatisfiedPassword");
		
		String regex = "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%]).{8,20})";
		
		if(!value.toString().matches(regex)) generateError("noSatisfiedPassword");
	}
	
	public void generateError(String error) {
		FacesMessage message = new FacesMessage(Messages.getMessage(error));
		message.setSeverity(FacesMessage.SEVERITY_ERROR);
		throw new ValidatorException(message);
	}
}
