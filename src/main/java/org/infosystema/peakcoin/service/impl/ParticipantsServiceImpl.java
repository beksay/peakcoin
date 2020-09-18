package org.infosystema.peakcoin.service.impl;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;

import org.infosystema.peakcoin.dao.ParticipantsDao;
import org.infosystema.peakcoin.dao.impl.ParticipantsDaoImpl;
import org.infosystema.peakcoin.domain.Participants;
import org.infosystema.peakcoin.service.ParticipantsService;

/**
 * 
 * @author Kuttubek Aidaraliev
 *
 */

@Stateless
@Local(ParticipantsService.class)
@TransactionManagement(TransactionManagementType.CONTAINER)
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class ParticipantsServiceImpl extends GenericServiceImpl<Participants, Integer, ParticipantsDao> implements ParticipantsService {

	@Override
	protected ParticipantsDao getDao() {
		return new ParticipantsDaoImpl(em, se);
	}

}
