package org.infosystema.peakcoin.dao.impl;

import javax.enterprise.event.Event;
import javax.persistence.EntityManager;

import org.infosystema.peakcoin.dao.LocationDao;
import org.infosystema.peakcoin.domain.Location;

/**
 * 
 * @author Kuttubek Aidaraliev
 *
 */

public class LocationDaoImpl extends GenericDaoImpl<Location, Integer> implements LocationDao {

	public LocationDaoImpl(EntityManager entityManager, Event<Location> eventSource) {
		super(entityManager, eventSource);
	}

}
