package org.infosystema.peakcoin.service.impl;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;

import org.infosystema.peakcoin.dao.LocationDao;
import org.infosystema.peakcoin.dao.impl.LocationDaoImpl;
import org.infosystema.peakcoin.domain.Location;
import org.infosystema.peakcoin.service.LocationService;

/**
 * 
 * @author Kuttubek Aidaraliev
 *
 */

@Stateless
@Local(LocationService.class)
@TransactionManagement(TransactionManagementType.CONTAINER)
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class LocationServiceImpl extends GenericServiceImpl<Location, Integer, LocationDao> implements LocationService {

	@Override
	protected LocationDao getDao() {
		return new LocationDaoImpl(em, se);
	}

}
