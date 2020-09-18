package org.infosystema.peakcoin.service.impl;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;

import org.infosystema.peakcoin.dao.CookDao;
import org.infosystema.peakcoin.dao.impl.CookDaoImpl;
import org.infosystema.peakcoin.domain.Cook;
import org.infosystema.peakcoin.service.CookService;

/**
 * 
 * @author Kuttubek Aidaraliev
 *
 */

@Stateless
@Local(CookService.class)
@TransactionManagement(TransactionManagementType.CONTAINER)
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class CookServiceImpl extends GenericServiceImpl<Cook, Integer, CookDao> implements CookService {

	@Override
	protected CookDao getDao() {
		return new CookDaoImpl(em, se);
	}

}
