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
import org.infosystema.peakcoin.model.CompanyModel;
import org.infosystema.peakcoin.service.CompanyService;

/**
 * 
 * @author Kuttubek Aidaraliev
 *
 */

@Logged
@ManagedBean
@ViewScoped
public class CompanyList extends BaseController implements Serializable {
	
	private static final long serialVersionUID = -6100072166946495229L;
	@EJB
	private CompanyService service;
	private CompanyModel model;
	
	private String companyName;
	
	@PostConstruct
	private void init() {
		filterData();
	}
	
	public void filterData() {
		List<FilterExample> filters = new ArrayList<>();
		if (companyName != null && !companyName.isEmpty()) {
			filters.add(new FilterExample("companyName", '%' + companyName + '%', InequalityConstants.LIKE));
		}
		model = new CompanyModel(filters, service);
	}
	
	public String clearData() {
		companyName = null;
		
		init();
		
		return null;
	}

	public CompanyModel getModel() {
		return model;
	}

	public void setModel(CompanyModel model) {
		this.model = model;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

    
}
