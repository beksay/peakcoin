package org.infosystema.peakcoin.dao.impl;

import javax.enterprise.event.Event;
import javax.persistence.EntityManager;

import org.infosystema.peakcoin.dao.OrderTransportDao;
import org.infosystema.peakcoin.domain.OrderTransport;

/**
 * 
 * @author Kuttubek Aidaraliev
 *
 */

public class OrderTransportDaoImpl extends GenericDaoImpl<OrderTransport, Integer> implements OrderTransportDao {

	public OrderTransportDaoImpl(EntityManager entityManager, Event<OrderTransport> eventSource) {
		super(entityManager, eventSource);
	}

}
