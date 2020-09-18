package org.infosystema.peakcoin.service.impl;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;

import org.infosystema.peakcoin.dao.CityDao;
import org.infosystema.peakcoin.dao.impl.CityDaoImpl;
import org.infosystema.peakcoin.domain.City;
import org.infosystema.peakcoin.service.CityService;

/**
 * 
 * @author Kuttubek Aidaraliev
 *
 */

@Stateless
@Local(CityService.class)
@TransactionManagement(TransactionManagementType.CONTAINER)
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class CityServiceImpl extends GenericServiceImpl<City, Integer, CityDao> implements CityService {

	@Override
	protected CityDao getDao() {
		return new CityDaoImpl(em, se);
	}

}
