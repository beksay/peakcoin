package org.infosystema.peakcoin.dao.impl;

import javax.enterprise.event.Event;
import javax.persistence.EntityManager;

import org.infosystema.peakcoin.dao.DriverDao;
import org.infosystema.peakcoin.domain.Driver;

/**
 * 
 * @author Kuttubek Aidaraliev
 *
 */

public class DriverDaoImpl extends GenericDaoImpl<Driver, Integer> implements DriverDao {

	public DriverDaoImpl(EntityManager entityManager, Event<Driver> eventSource) {
		super(entityManager, eventSource);
	}

}
