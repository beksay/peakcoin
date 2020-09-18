package org.infosystema.peakcoin.service.impl;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;

import org.infosystema.peakcoin.dao.GuideDao;
import org.infosystema.peakcoin.dao.impl.GuideDaoImpl;
import org.infosystema.peakcoin.domain.Guide;
import org.infosystema.peakcoin.service.GuideService;

/**
 * 
 * @author Kuttubek Aidaraliev
 *
 */

@Stateless
@Local(GuideService.class)
@TransactionManagement(TransactionManagementType.CONTAINER)
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class GuideServiceImpl extends GenericServiceImpl<Guide, Integer, GuideDao> implements GuideService {

	@Override
	protected GuideDao getDao() {
		return new GuideDaoImpl(em, se);
	}

}
