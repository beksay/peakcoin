package org.infosystema.peakcoin.dao.impl;

import javax.enterprise.event.Event;
import javax.persistence.EntityManager;

import org.infosystema.peakcoin.dao.CarparkDao;
import org.infosystema.peakcoin.domain.Carpark;

/**
 * 
 * @author Kuttubek Aidaraliev
 *
 */

public class CarparkDaoImpl extends GenericDaoImpl<Carpark, Integer> implements CarparkDao {

	public CarparkDaoImpl(EntityManager entityManager, Event<Carpark> eventSource) {
		super(entityManager, eventSource);
	}

}
