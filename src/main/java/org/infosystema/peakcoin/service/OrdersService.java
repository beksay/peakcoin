package org.infosystema.peakcoin.service;

import javax.ejb.Local;

import org.infosystema.peakcoin.domain.Orders;

/**
 * 
 * @author Kuttubek Aidaraliev
 *
 */

@Local
public interface OrdersService extends GenericService<Orders, Integer> {

}
 