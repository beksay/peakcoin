package org.infosystema.peakcoin.service.impl;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;

import org.infosystema.peakcoin.dao.OrderLocationDao;
import org.infosystema.peakcoin.dao.impl.OrderLocationDaoImpl;
import org.infosystema.peakcoin.domain.OrderLocation;
import org.infosystema.peakcoin.service.OrderLocationService;

/**
 * 
 * @author Kuttubek Aidaraliev
 *
 */

@Stateless
@Local(OrderLocationService.class)
@TransactionManagement(TransactionManagementType.CONTAINER)
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class OrderLocationServiceImpl extends GenericServiceImpl<OrderLocation, Integer, OrderLocationDao> implements OrderLocationService {

	@Override
	protected OrderLocationDao getDao() {
		return new OrderLocationDaoImpl(em, se);
	}

}
