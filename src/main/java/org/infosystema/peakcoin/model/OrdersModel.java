package org.infosystema.peakcoin.model;

import java.util.List;

import org.infosystema.peakcoin.beans.FilterExample;
import org.infosystema.peakcoin.domain.Orders;
import org.infosystema.peakcoin.service.OrdersService;

/**
 * 
 * @author Kuttubek Aidaraliev
 *
 */

public class OrdersModel extends BaseModel<OrdersService, Orders, Integer> {

	private static final long serialVersionUID = -4871106869905562775L;

	public OrdersModel(List<FilterExample> filters, OrdersService service) {
		super(filters, service);
	}
	
	@Override
	protected Integer getKey(String key) {
		return Integer.parseInt(key);
	}
	
}
