package org.infosystema.peakcoin.dao.impl;

import javax.enterprise.event.Event;
import javax.persistence.EntityManager;

import org.infosystema.peakcoin.dao.OrderGuideDao;
import org.infosystema.peakcoin.domain.OrderGuide;

/**
 * 
 * @author Kuttubek Aidaraliev
 *
 */

public class OrderGuideDaoImpl extends GenericDaoImpl<OrderGuide, Integer> implements OrderGuideDao {

	public OrderGuideDaoImpl(EntityManager entityManager, Event<OrderGuide> eventSource) {
		super(entityManager, eventSource);
	}

}
