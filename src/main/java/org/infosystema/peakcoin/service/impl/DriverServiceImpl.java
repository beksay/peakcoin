package org.infosystema.peakcoin.service.impl;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;

import org.infosystema.peakcoin.dao.DriverDao;
import org.infosystema.peakcoin.dao.impl.DriverDaoImpl;
import org.infosystema.peakcoin.domain.Driver;
import org.infosystema.peakcoin.service.DriverService;

/**
 * 
 * @author Kuttubek Aidaraliev
 *
 */

@Stateless
@Local(DriverService.class)
@TransactionManagement(TransactionManagementType.CONTAINER)
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class DriverServiceImpl extends GenericServiceImpl<Driver, Integer, DriverDao> implements DriverService {

	@Override
	protected DriverDao getDao() {
		return new DriverDaoImpl(em, se);
	}

}
