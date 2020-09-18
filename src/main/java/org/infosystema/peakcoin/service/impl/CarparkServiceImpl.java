package org.infosystema.peakcoin.service.impl;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;

import org.infosystema.peakcoin.dao.CarparkDao;
import org.infosystema.peakcoin.dao.impl.CarparkDaoImpl;
import org.infosystema.peakcoin.domain.Carpark;
import org.infosystema.peakcoin.service.CarparkService;

/**
 * 
 * @author Kuttubek Aidaraliev
 *
 */

@Stateless
@Local(CarparkService.class)
@TransactionManagement(TransactionManagementType.CONTAINER)
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class CarparkServiceImpl extends GenericServiceImpl<Carpark, Integer, CarparkDao> implements CarparkService {

	@Override
	protected CarparkDao getDao() {
		return new CarparkDaoImpl(em, se);
	}

}
