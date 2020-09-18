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
import org.infosystema.peakcoin.model.LocationModel;
import org.infosystema.peakcoin.service.LocationService;

/**
 * 
 * @author Kuttubek Aidaraliev
 *
 */

@Logged
@ManagedBean
@ViewScoped
public class LocationList extends BaseController implements Serializable {
	
	private static final long serialVersionUID = -6100072166946495229L;
	@EJB
	private LocationService service;
	private LocationModel model;
	@Inject
	private OblastController oblastController;
	
	private String name;
	
	@PostConstruct
	private void init() {
		filterData();
	}
	
	public void filterData() {
		List<FilterExample> filters = new ArrayList<>();
		filters.add(new FilterExample("oblast",oblastController.getOblast(), InequalityConstants.EQUAL));
		if(name != null) filters.add(new FilterExample("name",'%'+ name + '%', InequalityConstants.LIKE));
		model = new LocationModel(filters, service);
		model.setFetchProperties(new String[]{"attachments"});
	}
	
	public String clearData() {
		name = null;
		init();	
		return null;
	}

	public LocationService getService() {
		return service;
	}
	
	public void setService(LocationService service) {
		this.service = service;
	}
	
    public LocationModel getModel() {
		return model;
	}
    
    public void setModel(LocationModel model) {
		this.model = model;
	}
    
    public String getName() {
		return name;
	}
    
    public void setName(String name) {
		this.name = name;
	}

	public OblastController getOblastController() {
		return oblastController;
	}

	public void setOblastController(OblastController oblastController) {
		this.oblastController = oblastController;
	}


}
