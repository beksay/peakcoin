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
import org.infosystema.peakcoin.model.OblastModel;
import org.infosystema.peakcoin.service.OblastService;

/**
 * 
 * @author Kuttubek Aidaraliev
 *
 */

@Logged
@ManagedBean
@ViewScoped
public class OblastList extends BaseController implements Serializable {
	
	private static final long serialVersionUID = -6100072166946495229L;
	@EJB
	private OblastService service;
	private OblastModel model;
	@Inject
	private CountryController countryController;
	
	private String name;
	
	@PostConstruct
	private void init() {
		filterData();
	}
	
	public void filterData() {
		List<FilterExample> filters = new ArrayList<>();
		filters.add(new FilterExample("country",countryController.getCountry(), InequalityConstants.EQUAL));
		if(name != null) filters.add(new FilterExample("name",'%'+ name + '%', InequalityConstants.LIKE));
		model = new OblastModel(filters, service);
	}
	
	public String clearData() {
		name = null;
		init();	
		return null;
	}

	public OblastService getService() {
		return service;
	}
	
	public void setService(OblastService service) {
		this.service = service;
	}
	
    public OblastModel getModel() {
		return model;
	}
    
    public void setModel(OblastModel model) {
		this.model = model;
	}
    
    public String getName() {
		return name;
	}
    
    public void setName(String name) {
		this.name = name;
	}

	public CountryController getCountryController() {
		return countryController;
	}

	public void setCountryController(CountryController countryController) {
		this.countryController = countryController;
	}


}
