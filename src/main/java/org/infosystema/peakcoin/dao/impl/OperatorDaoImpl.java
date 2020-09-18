package org.infosystema.peakcoin.dao.impl;

import javax.enterprise.event.Event;
import javax.persistence.EntityManager;

import org.infosystema.peakcoin.dao.OperatorDao;
import org.infosystema.peakcoin.domain.Operator;

/**
 * 
 * @author Kuttubek Aidaraliev
 *
 */

public class OperatorDaoImpl extends GenericDaoImpl<Operator, Integer> implements OperatorDao {

	public OperatorDaoImpl(EntityManager entityManager, Event<Operator> eventSource) {
		super(entityManager, eventSource);
	}

}
