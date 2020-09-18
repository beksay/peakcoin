package org.infosystema.peakcoin.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

import org.infosystema.peakcoin.annotation.Logged;
import org.infosystema.peakcoin.beans.FilterExample;
import org.infosystema.peakcoin.beans.InequalityConstants;
import org.infosystema.peakcoin.controller.base.BaseController;
import org.infosystema.peakcoin.model.TransportModel;
import org.infosystema.peakcoin.service.TransportService;
import org.infosystema.peakcoin.util.web.LoginUtil;

/**
 * 
 * @author Kuttubek Aidaraliev
 *
 */

@Logged
@ManagedBean
@ViewScoped
public class TransportList extends BaseController implements Serializable {
	
	private static final long serialVersionUID = -6100072166946495229L;
	@EJB
	private TransportService service;
	private TransportModel model;
	@Inject
	private LoginUtil loginUtil;
	
	private String number;
	
	@PostConstruct
	private void init() {
		filterData();
	}
	
	public void filterData() {
		List<FilterExample> filters = new ArrayList<>();
		if (number != null && !number.isEmpty()) {
			filters.add(new FilterExample("number", '%' + number + '%', InequalityConstants.LIKE));
		}
		//filters.add(new FilterExample("company", loginUtil.getCurrentUser().getPerson().getCompany(), InequalityConstants.EQUAL));
		model = new TransportModel(filters, service);
	}
	
	public String clearData() {
		number = null;
		init();
		
		return null;
	}

	public TransportModel getModel() {
		return model;
	}
	
	public void setModel(TransportModel model) {
		this.model = model;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

    
}
