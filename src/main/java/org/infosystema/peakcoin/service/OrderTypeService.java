package org.infosystema.peakcoin.service;

import javax.ejb.Local;

import org.infosystema.peakcoin.domain.OrderType;

/**
 * 
 * @author Kuttubek Aidaraliev
 *
 */

@Local
public interface OrderTypeService extends GenericService<OrderType, Integer> {

}
