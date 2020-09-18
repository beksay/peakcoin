package org.infosystema.peakcoin.service.impl;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;

import org.infosystema.peakcoin.dao.OrderGuideDao;
import org.infosystema.peakcoin.dao.impl.OrderGuideDaoImpl;
import org.infosystema.peakcoin.domain.OrderGuide;
import org.infosystema.peakcoin.service.OrderGuideService;

/**
 * 
 * @author Kuttubek Aidaraliev
 *
 */

@Stateless
@Local(OrderGuideService.class)
@TransactionManagement(TransactionManagementType.CONTAINER)
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class OrderGuideServiceImpl extends GenericServiceImpl<OrderGuide, Integer, OrderGuideDao> implements OrderGuideService {

	@Override
	protected OrderGuideDao getDao() {
		return new OrderGuideDaoImpl(em, se);
	}

}
