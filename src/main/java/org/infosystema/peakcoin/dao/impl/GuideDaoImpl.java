package org.infosystema.peakcoin.dao.impl;

import javax.enterprise.event.Event;
import javax.persistence.EntityManager;

import org.infosystema.peakcoin.dao.GuideDao;
import org.infosystema.peakcoin.domain.Guide;

/**
 * 
 * @author Kuttubek Aidaraliev
 *
 */

public class GuideDaoImpl extends GenericDaoImpl<Guide, Integer> implements GuideDao {

	public GuideDaoImpl(EntityManager entityManager, Event<Guide> eventSource) {
		super(entityManager, eventSource);
	}

}
