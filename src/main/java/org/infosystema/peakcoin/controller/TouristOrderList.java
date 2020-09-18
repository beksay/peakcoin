package org.infosystema.peakcoin.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.infosystema.peakcoin.annotation.Logged;
import org.infosystema.peakcoin.beans.FilterExample;
import org.infosystema.peakcoin.beans.InequalityConstants;
import org.infosystema.peakcoin.controller.base.BaseController;
import org.infosystema.peakcoin.model.OrdersModel;
import org.infosystema.peakcoin.service.OrdersService;

/**
 * 
 * @author Kuttubek Aidaraliev
 *
 */

@Logged
@ManagedBean
@ViewScoped
public class TouristOrderList extends BaseController implements Serializable {
	
	private static final long serialVersionUID = -6100072166946495229L;
	@EJB
	private OrdersService service;
	private OrdersModel model;
	
	private String orderName;
	
	@PostConstruct
	private void init() {
		filterData();
	}
	
	public void filterData() {
		List<FilterExample> filters = new ArrayList<>();
		if (orderName != null && !orderName.isEmpty()) {
			filters.add(new FilterExample("name", '%' + orderName + '%', InequalityConstants.LIKE));
		}
		model = new OrdersModel(filters, service);
	}
	
	public String clearData() {
		orderName = null;
		init();
		
		return null;
	}

	public OrdersModel getModel() {
		return model;
	}
	
	public void setModel(OrdersModel model) {
		this.model = model;
	}

	public String getOrderName() {
		return orderName;
	}

	public void setOrderName(String orderName) {
		this.orderName = orderName;
	}

    
}
