package org.infosystema.peakcoin.service.impl;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;

import org.infosystema.peakcoin.dao.OperatorDao;
import org.infosystema.peakcoin.dao.impl.OperatorDaoImpl;
import org.infosystema.peakcoin.domain.Operator;
import org.infosystema.peakcoin.service.OperatorService;

/**
 * 
 * @author Kuttubek Aidaraliev
 *
 */

@Stateless
@Local(OperatorService.class)
@TransactionManagement(TransactionManagementType.CONTAINER)
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class OperatorServiceImpl extends GenericServiceImpl<Operator, Integer, OperatorDao> implements OperatorService {

	@Override
	protected OperatorDao getDao() {
		return new OperatorDaoImpl(em, se);
	}

}
