package org.infosystema.peakcoin.service.impl;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;

import org.infosystema.peakcoin.dao.OblastDao;
import org.infosystema.peakcoin.dao.impl.OblastDaoImpl;
import org.infosystema.peakcoin.domain.Oblast;
import org.infosystema.peakcoin.service.OblastService;

/**
 * 
 * @author Kuttubek Aidaraliev
 *
 */

@Stateless
@Local(OblastService.class)
@TransactionManagement(TransactionManagementType.CONTAINER)
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class OblastServiceImpl extends GenericServiceImpl<Oblast, Integer, OblastDao> implements OblastService {

	@Override
	protected OblastDao getDao() {
		return new OblastDaoImpl(em, se);
	}

}
