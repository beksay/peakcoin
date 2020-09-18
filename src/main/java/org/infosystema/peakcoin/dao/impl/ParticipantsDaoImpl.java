package org.infosystema.peakcoin.dao.impl;

import javax.enterprise.event.Event;
import javax.persistence.EntityManager;

import org.infosystema.peakcoin.dao.ParticipantsDao;
import org.infosystema.peakcoin.domain.Participants;

/**
 * 
 * @author Kuttubek Aidaraliev
 *
 */

public class ParticipantsDaoImpl extends GenericDaoImpl<Participants, Integer> implements ParticipantsDao {

	public ParticipantsDaoImpl(EntityManager entityManager, Event<Participants> eventSource) {
		super(entityManager, eventSource);
	}

}
