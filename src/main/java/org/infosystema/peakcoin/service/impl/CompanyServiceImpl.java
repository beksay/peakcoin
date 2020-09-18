package org.infosystema.peakcoin.service.impl;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;

import org.infosystema.peakcoin.dao.CompanyDao;
import org.infosystema.peakcoin.dao.impl.CompanyDaoImpl;
import org.infosystema.peakcoin.domain.Company;
import org.infosystema.peakcoin.service.CompanyService;

/**
 * 
 * @author Kuttubek Aidaraliev
 *
 */

@Stateless
@Local(CompanyService.class)
@TransactionManagement(TransactionManagementType.CONTAINER)
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class CompanyServiceImpl extends GenericServiceImpl<Company, Integer, CompanyDao> implements CompanyService {

	@Override
	protected CompanyDao getDao() {
		return new CompanyDaoImpl(em, se);
	}

}
