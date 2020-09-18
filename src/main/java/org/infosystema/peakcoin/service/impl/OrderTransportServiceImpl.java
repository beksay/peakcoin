package org.infosystema.peakcoin.service.impl;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;

import org.infosystema.peakcoin.dao.OrderTransportDao;
import org.infosystema.peakcoin.dao.impl.OrderTransportDaoImpl;
import org.infosystema.peakcoin.domain.OrderTransport;
import org.infosystema.peakcoin.service.OrderTransportService;

/**
 * 
 * @author Kuttubek Aidaraliev
 *
 */

@Stateless
@Local(OrderTransportService.class)
@TransactionManagement(TransactionManagementType.CONTAINER)
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class OrderTransportServiceImpl extends GenericServiceImpl<OrderTransport, Integer, OrderTransportDao> implements OrderTransportService {

	@Override
	protected OrderTransportDao getDao() {
		return new OrderTransportDaoImpl(em, se);
	}

}
