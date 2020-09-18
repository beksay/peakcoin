package org.infosystema.peakcoin.dao.impl;

import javax.enterprise.event.Event;
import javax.persistence.EntityManager;

import org.infosystema.peakcoin.dao.OrdersDao;
import org.infosystema.peakcoin.domain.Orders;

/**
 * 
 * @author Kuttubek Aidaraliev
 *
 */

public class OrdersDaoImpl extends GenericDaoImpl<Orders, Integer> implements OrdersDao {

	public OrdersDaoImpl(EntityManager entityManager, Event<Orders> eventSource) {
		super(entityManager, eventSource);
	}

}
