package org.infosystema.peakcoin.service.impl;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;

import org.infosystema.peakcoin.dao.CountryDao;
import org.infosystema.peakcoin.dao.impl.CountryDaoImpl;
import org.infosystema.peakcoin.domain.Country;
import org.infosystema.peakcoin.service.CountryService;

/**
 * 
 * @author Kuttubek Aidaraliev
 *
 */

@Stateless
@Local(CountryService.class)
@TransactionManagement(TransactionManagementType.CONTAINER)
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class CountryServiceImpl extends GenericServiceImpl<Country, Integer, CountryDao> implements CountryService {

	@Override
	protected CountryDao getDao() {
		return new CountryDaoImpl(em, se);
	}

}
