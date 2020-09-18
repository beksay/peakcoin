package org.infosystema.peakcoin.service.impl;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;

import org.infosystema.peakcoin.dao.OrderTypeDao;
import org.infosystema.peakcoin.dao.impl.OrderTypeDaoImpl;
import org.infosystema.peakcoin.domain.OrderType;
import org.infosystema.peakcoin.service.OrderTypeService;

/**
 * 
 * @author Kuttubek Aidaraliev
 *
 */

@Stateless
@Local(OrderTypeService.class)
@TransactionManagement(TransactionManagementType.CONTAINER)
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class OrderTypeServiceImpl extends GenericServiceImpl<OrderType, Integer, OrderTypeDao> implements OrderTypeService {

	@Override
	protected OrderTypeDao getDao() {
		return new OrderTypeDaoImpl(em, se);
	}

}
