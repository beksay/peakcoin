package org.infosystema.peakcoin.dao.impl;

import javax.enterprise.event.Event;
import javax.persistence.EntityManager;

import org.infosystema.peakcoin.dao.TransportDao;
import org.infosystema.peakcoin.domain.Transport;

/**
 * 
 * @author Kuttubek Aidaraliev
 *
 */

public class TransportDaoImpl extends GenericDaoImpl<Transport, Integer> implements TransportDao {

	public TransportDaoImpl(EntityManager entityManager, Event<Transport> eventSource) {
		super(entityManager, eventSource);
	}

}
