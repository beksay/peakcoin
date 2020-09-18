package org.infosystema.peakcoin.dao.impl;

import javax.enterprise.event.Event;
import javax.persistence.EntityManager;

import org.infosystema.peakcoin.dao.OrderLocationDao;
import org.infosystema.peakcoin.domain.OrderLocation;

/**
 * 
 * @author Kuttubek Aidaraliev
 *
 */

public class OrderLocationDaoImpl extends GenericDaoImpl<OrderLocation, Integer> implements OrderLocationDao {

	public OrderLocationDaoImpl(EntityManager entityManager, Event<OrderLocation> eventSource) {
		super(entityManager, eventSource);
	}

}
