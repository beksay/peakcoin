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
import org.infosystema.peakcoin.model.CountryModel;
import org.infosystema.peakcoin.service.CountryService;

/**
 * 
 * @author Kuttubek Aidaraliev
 *
 */

@Logged
@ManagedBean
@ViewScoped
public class CountryList extends BaseController implements Serializable {
	
	private static final long serialVersionUID = -6100072166946495229L;
	@EJB
	private CountryService service;
	private CountryModel model;
	
	private String name;
	
	@PostConstruct
	private void init() {
		filterData();
	}
	
	public void filterData() {
		List<FilterExample> filters = new ArrayList<>();
		if(name != null) filters.add(new FilterExample("name",'%'+ name + '%', InequalityConstants.LIKE));
		model = new CountryModel(filters, service);
	}
	
	public String clearData() {
		name = null;
		init();	
		return null;
	}

	public CountryService getService() {
		return service;
	}
	
	public void setService(CountryService service) {
		this.service = service;
	}
	
    public CountryModel getModel() {
		return model;
	}
    
    public void setModel(CountryModel model) {
		this.model = model;
	}
    
    public String getName() {
		return name;
	}
    
    public void setName(String name) {
		this.name = name;
	}
    
}
