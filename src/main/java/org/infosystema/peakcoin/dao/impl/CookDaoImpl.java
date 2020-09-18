package org.infosystema.peakcoin.dao.impl;

import javax.enterprise.event.Event;
import javax.persistence.EntityManager;

import org.infosystema.peakcoin.dao.CookDao;
import org.infosystema.peakcoin.domain.Cook;

/**
 * 
 * @author Kuttubek Aidaraliev
 *
 */

public class CookDaoImpl extends GenericDaoImpl<Cook, Integer> implements CookDao {

	public CookDaoImpl(EntityManager entityManager, Event<Cook> eventSource) {
		super(entityManager, eventSource);
	}

}
