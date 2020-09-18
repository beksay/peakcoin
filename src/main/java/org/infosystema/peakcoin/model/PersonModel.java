package org.infosystema.peakcoin.model;

import java.util.List;

import org.infosystema.peakcoin.beans.FilterExample;
import org.infosystema.peakcoin.domain.Person;
import org.infosystema.peakcoin.service.PersonService;

/**
 * 
 * @author Kuttubek Aidaraliev
 *
 */

public class PersonModel extends BaseModel<PersonService, Person, Integer> {

	private static final long serialVersionUID = -4871106869905562775L;

	public PersonModel(List<FilterExample> filters, PersonService service) {
		super(filters, service);
	}
	
	@Override
	protected Integer getKey(String key) {
		return Integer.parseInt(key);
	}
	
}
