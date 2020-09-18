package org.infosystema.peakcoin.controller.user;

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
import org.infosystema.peakcoin.model.GuideModel;
import org.infosystema.peakcoin.service.GuideService;

/**
 * 
 * @author Kuttubek Aidaraliev
 *
 */

@Logged
@ManagedBean
@ViewScoped
public class GuideList extends BaseController implements Serializable {
	
	private static final long serialVersionUID = -6100072166946495229L;
	@EJB
	private GuideService service;
	private GuideModel model;
	
	private String searchString;
	
	@PostConstruct
	private void init() {
		filterData();
	}
	
	public void filterData() {
		List<FilterExample> filters = new ArrayList<>();
		if (searchString != null && !searchString.isEmpty()) {
			filters.add(new FilterExample("GuideName", '%' + searchString + '%', InequalityConstants.LIKE));
		}
		model = new GuideModel(filters, service);
	}
	
	public String clearData() {
		searchString = null;
		
		init();
		
		return null;
	}

	public GuideModel getModel() {
		return model;
	}

	public void setModel(GuideModel model) {
		this.model = model;
	}

	public String getSearchString() {
		return searchString;
	}

	public void setSearchString(String searchString) {
		this.searchString = searchString;
	}

    
}
