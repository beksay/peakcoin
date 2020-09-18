package org.infosystema.peakcoin.service.impl;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;

import org.infosystema.peakcoin.dao.OrdersDao;
import org.infosystema.peakcoin.dao.impl.OrdersDaoImpl;
import org.infosystema.peakcoin.domain.Orders;
import org.infosystema.peakcoin.service.OrdersService;

/**
 * 
 * @author Kuttubek Aidaraliev
 *
 */

@Stateless
@Local(OrdersService.class)
@TransactionManagement(TransactionManagementType.CONTAINER)
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class OrdersServiceImpl extends GenericServiceImpl<Orders, Integer, OrdersDao> implements OrdersService {

	@Override
	protected OrdersDao getDao() {
		return new OrdersDaoImpl(em, se);
	}

}
