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
import org.infosystema.peakcoin.model.DictionaryTypeModel;
import org.infosystema.peakcoin.service.DictionaryTypeService;

/**
 * 
 * @author Kuttubek Aidaraliev
 *
 */

@Logged
@ManagedBean
@ViewScoped
public class DictionaryTypeList extends BaseController implements Serializable {
	
	private static final long serialVersionUID = -6100072166946495229L;
	@EJB
	private DictionaryTypeService service;
	private DictionaryTypeModel model;
	
	private String name;
	
	@PostConstruct
	private void init() {
		filterData();
	}
	
	public void filterData() {
		List<FilterExample> filters = new ArrayList<>();
		if(name != null) filters.add(new FilterExample("name",'%'+ name + '%', InequalityConstants.LIKE));
		model = new DictionaryTypeModel(filters, service);
	}
	
	public String clearData() {
		name = null;
		init();	
		return null;
	}

	public DictionaryTypeService getService() {
		return service;
	}
	
	public void setService(DictionaryTypeService service) {
		this.service = service;
	}
	
    public DictionaryTypeModel getModel() {
		return model;
	}
    
    public void setModel(DictionaryTypeModel model) {
		this.model = model;
	}
    
    public String getName() {
		return name;
	}
    
    public void setName(String name) {
		this.name = name;
	}
    
}
