package org.infosystema.peakcoin.service.impl;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;

import org.infosystema.peakcoin.dao.PersonDao;
import org.infosystema.peakcoin.dao.impl.PersonDaoImpl;
import org.infosystema.peakcoin.domain.Person;
import org.infosystema.peakcoin.service.PersonService;

/**
 * 
 * @author Kuttubek Aidaraliev
 *
 */

@Stateless
@Local(PersonService.class)
@TransactionManagement(TransactionManagementType.CONTAINER)
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class PersonServiceImpl extends GenericServiceImpl<Person, Integer, PersonDao> implements PersonService {

	@Override
	protected PersonDao getDao() {
		return new PersonDaoImpl(em, se);
	}

}
