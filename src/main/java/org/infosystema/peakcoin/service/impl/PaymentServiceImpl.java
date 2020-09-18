package org.infosystema.peakcoin.service.impl;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;

import org.infosystema.peakcoin.dao.PaymentDao;
import org.infosystema.peakcoin.dao.impl.PaymentDaoImpl;
import org.infosystema.peakcoin.domain.Payment;
import org.infosystema.peakcoin.service.PaymentService;

/**
 * 
 * @author Kuttubek Aidaraliev
 *
 */

@Stateless
@Local(PaymentService.class)
@TransactionManagement(TransactionManagementType.CONTAINER)
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class PaymentServiceImpl extends GenericServiceImpl<Payment, Integer, PaymentDao> implements PaymentService {

	@Override
	protected PaymentDao getDao() {
		return new PaymentDaoImpl(em, se);
	}

}
