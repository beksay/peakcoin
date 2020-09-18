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
import org.infosystema.peakcoin.model.DriverModel;
import org.infosystema.peakcoin.service.DriverService;
import org.infosystema.peakcoin.util.web.LoginUtil;

/**
 * 
 * @author Kuttubek Aidaraliev
 *
 */

@Logged
@ManagedBean
@ViewScoped
public class DriverList extends BaseController implements Serializable {
	
	private static final long serialVersionUID = -6100072166946495229L;
	@EJB
	private DriverService service;
	private DriverModel model;
	@Inject
	private LoginUtil loginUtil;
	
	private String fio;
	
	@PostConstruct
	private void init() {
		filterData();
	}
	
	public void filterData() {
		List<FilterExample> filters = new ArrayList<>();
		if (fio != null && !fio.isEmpty()) {
			filters.add(new FilterExample("name", '%' + fio + '%', InequalityConstants.LIKE));
		}
		filters.add(new FilterExample("company", loginUtil.getCurrentUser().getPerson(), InequalityConstants.EQUAL));
		model = new DriverModel(filters, service);
	}
	
	public String clearData() {
		fio = null;
		init();
		
		return null;
	}

	public DriverModel getModel() {
		return model;
	}
	
	public void setModel(DriverModel model) {
		this.model = model;
	}

	public String getFio() {
		return fio;
	}

	public void setFio(String fio) {
		this.fio = fio;
	}

    
}
