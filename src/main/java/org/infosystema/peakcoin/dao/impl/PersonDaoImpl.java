package org.infosystema.peakcoin.dao.impl;

import javax.enterprise.event.Event;
import javax.persistence.EntityManager;

import org.infosystema.peakcoin.dao.PersonDao;
import org.infosystema.peakcoin.domain.Person;

/**
 * 
 * @author Kuttubek Aidaraliev
 *
 */

public class PersonDaoImpl extends GenericDaoImpl<Person, Integer> implements PersonDao {

	public PersonDaoImpl(EntityManager entityManager, Event<Person> eventSource) {
		super(entityManager, eventSource);
	}

}
