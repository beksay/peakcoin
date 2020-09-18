package org.infosystema.peakcoin.dao.impl;

import javax.enterprise.event.Event;
import javax.persistence.EntityManager;

import org.infosystema.peakcoin.dao.AttachmentDao;
import org.infosystema.peakcoin.domain.Attachment;

/**
 * 
 * @author Kuttubek Aidaraliev
 *
 */

public class AttachmentDaoImpl extends GenericDaoImpl<Attachment, Integer> implements AttachmentDao {

	public AttachmentDaoImpl(EntityManager entityManager, Event<Attachment> event) {
		super(entityManager, event);
	}

}
