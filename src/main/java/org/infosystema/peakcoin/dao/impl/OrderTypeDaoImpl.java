package org.infosystema.peakcoin.dao.impl;

import javax.enterprise.event.Event;
import javax.persistence.EntityManager;

import org.infosystema.peakcoin.dao.OrderTypeDao;
import org.infosystema.peakcoin.domain.OrderType;

/**
 * 
 * @author Kuttubek Aidaraliev
 *
 */

public class OrderTypeDaoImpl extends GenericDaoImpl<OrderType, Integer> implements OrderTypeDao {

	public OrderTypeDaoImpl(EntityManager entityManager, Event<OrderType> eventSource) {
		super(entityManager, eventSource);
	}

}
