package org.infosystema.peakcoin.service;

import javax.ejb.Local;

import org.infosystema.peakcoin.domain.Payment;

/**
 * 
 * @author Kuttubek Aidaraliev
 *
 */

@Local
public interface PaymentService extends GenericService<Payment, Integer> {

}
