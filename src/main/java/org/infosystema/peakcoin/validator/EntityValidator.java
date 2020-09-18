package org.infosystema.peakcoin.validator;

import java.io.Serializable;
import java.util.Set;

import javax.annotation.ManagedBean;
import javax.annotation.Resource;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ApplicationScoped;
import javax.faces.context.FacesContext;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.infosystema.peakcoin.domain.PersistentEntity;
import org.infosystema.peakcoin.util.web.Messages;

/**
 * 
 * @author Kuttubek Aidaraliev
 *
 */

@ManagedBean
@ApplicationScoped
public class EntityValidator implements Serializable {

	private static final long serialVersionUID = 3965753309416473677L;
	
	@Resource 
	private Validator validator;
	
	public <T extends PersistentEntity<ID>, ID extends Serializable> void validate(T entity) {
		Set<ConstraintViolation<T>> contraints = validator.validate(entity);
		
		if(!contraints.isEmpty()){
			for (ConstraintViolation<T> constraintViolation : contraints) {
				String message = constraintViolation.getMessage();
				System.out.println(constraintViolation.getPropertyPath());
				System.out.println(constraintViolation.getInvalidValue());
				System.out.println(message + " -- " + constraintViolation.getMessageTemplate());
				FacesMessage facesMessage = new FacesMessage(Messages.getMessage(message));
				FacesContext.getCurrentInstance().addMessage(null, facesMessage);
			}
		}
	}
	
}

