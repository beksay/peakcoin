package org.infosystema.peakcoin.controller.user;

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
import org.infosystema.peakcoin.model.UserModel;
import org.infosystema.peakcoin.service.UserService;
import org.infosystema.peakcoin.util.web.LoginUtil;

/**
 * 
 * @author Kuttubek Aidaraliev
 *
 */

@Logged
@ManagedBean
@ViewScoped
public class UserList extends BaseController implements Serializable {
	
	private static final long serialVersionUID = -6100072166946495229L;
	@EJB
	private UserService service;
	private UserModel model;
	@Inject
	private LoginUtil loginUtil;
	
	private String fullName;
	
	@PostConstruct
	private void init() {
		filterData();
	}
	
	public void filterData() {
		List<FilterExample> filters = new ArrayList<>();

		if (fullName != null) {

			filters.add(new FilterExample(true, "person.lastname", '%' + fullName + '%', InequalityConstants.LIKE, true));
			filters.add(new FilterExample(true, "person.firstname", '%' + fullName + '%', InequalityConstants.LIKE, true));
			filters.add(new FilterExample(true, "person.patronymic", '%' + fullName + '%', InequalityConstants.LIKE, true));

		}
		//filters.add(new FilterExample("role.name", "admin", InequalityConstants.NOT_EQUAL));
		model = new UserModel(filters, service);
	}
	
	public String clearData() {
		fullName = null;
		
		init();
		
		return null;
	}

	public UserService getService() {
		return service;
	}

	public void setService(UserService service) {
		this.service = service;
	}

	public UserModel getModel() {
		return model;
	}

	public void setModel(UserModel model) {
		this.model = model;
	}

	public String getFullName() {
		return fullName;
	}
	
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

    
}
