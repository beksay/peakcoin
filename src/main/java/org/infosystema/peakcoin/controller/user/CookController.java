package org.infosystema.peakcoin.controller.user;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.ConversationScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import org.infosystema.peakcoin.annotation.Logged;
import org.infosystema.peakcoin.beans.FilterExample;
import org.infosystema.peakcoin.beans.InequalityConstants;
import org.infosystema.peakcoin.controller.base.Conversational;
import org.infosystema.peakcoin.domain.Cook;
import org.infosystema.peakcoin.domain.Dictionary;
import org.infosystema.peakcoin.domain.Person;
import org.infosystema.peakcoin.domain.User;
import org.infosystema.peakcoin.enums.CookStatus;
import org.infosystema.peakcoin.service.AttachmentService;
import org.infosystema.peakcoin.service.CookService;
import org.infosystema.peakcoin.service.DictionaryService;
import org.infosystema.peakcoin.service.PersonService;
import org.infosystema.peakcoin.service.RoleService;
import org.infosystema.peakcoin.util.web.LoginUtil;
import org.infosystema.peakcoin.util.web.Messages;

/**
 * 
 * @author Kuttubek Aidaraliev
 *
 */

@Logged
@Named
@ConversationScoped
public class CookController extends Conversational {

	private static final long serialVersionUID = 5651758429305872940L;
	
	@EJB
	private PersonService service;
	@EJB
	private RoleService roleService;
	@EJB
	private CookService cookService;
	@EJB
	private DictionaryService dictService;
	@EJB
	AttachmentService atService;
	@Inject
	private LoginUtil loginUtil;
	
	private Person person;
	private Cook cook;
	private Boolean editProfile;
	private Set<Dictionary> foods;

	@PostConstruct
	public void init() {
		if (person==null) person= loginUtil.getCurrentUser().getPerson();
		if(foods==null) foods = new HashSet<>();
		if (cook==null) cook= new Cook();
		editProfile = false;
	}
	
	public String add() {
		cook = new Cook();
		foods = new HashSet<>();
		return "/view/profile/cook/cook_form.xhtml";
	}
	
	public String addAgain() {
		foods = new HashSet<>();
		return "/view/profile/cook/cook_form.xhtml";
	}
	
	public String change() {
		editProfile = true;
		return null;
	}
	
	public String startWork(Cook cook) {
		cook.setStatus(CookStatus.ACTIVE);
		cookService.merge(cook);
    	editProfile = false;
		return cookList();
	}
	
	public String cancel() {
		editProfile=false;
		return null;
	}
	
	public String save() {
		person = loginUtil.getCurrentUser().getPerson();

		cook.setFoods(foods);
		if(!FacesContext.getCurrentInstance().getMessageList().isEmpty()) return null;
        if(cook.getId() == null ) {
        	cook.setStatus(CookStatus.IN_PROGRES);
        	cookService.persist(cook);
        }else {
        	cook.setStatus(CookStatus.RE_PROGRES);
        	cookService.merge(cook);
		}
        person.setCook(cook);
		service.merge(person);
		
		FacesContext.getCurrentInstance().addMessage("form", new FacesMessage( FacesMessage.SEVERITY_INFO,  Messages.getMessage("dataDaved"), null) );
		editProfile = false;
		return cookInProgres();
	}
	
	public String saveProfile() {
		cook.setFoods(foods);
		if(!FacesContext.getCurrentInstance().getMessageList().isEmpty()) return null;
		cookService.merge(cook);
		cook = cookService.getByIdWithFields(person.getCook().getId(), new String[] {"foods"});
		FacesContext.getCurrentInstance().addMessage("form", new FacesMessage( FacesMessage.SEVERITY_INFO,  Messages.getMessage("dataDaved"), null) );
		editProfile = false;
		return null;
	}
	
	public String goCook(User user) {
	    if (user.getPerson().getCook().getId() !=null) {
	    	cook = cookService.getByIdWithFields(user.getPerson().getCook().getId(), new String[] {"foods"});
	    	foods = new HashSet<>();
			foods.addAll(cook.getFoods());
	    }
	  return cookList();
	}
	
	public List<Dictionary> getFoodsList() {
		List<FilterExample> examples = new ArrayList<>();
		examples.add(new FilterExample("dictionaryType.id", 3, InequalityConstants.EQUAL));
		return dictService.findByExample(0, 10, examples);
	}
	
	public String activate(Cook cook) {
		cook.setStatus(CookStatus.ACTIVE);
		cookService.merge(cook);
    	FacesContext.getCurrentInstance().addMessage("form", new FacesMessage( FacesMessage.SEVERITY_INFO,  Messages.getMessage("statusActivated"), null) );
		return cookJournal();
	}
	
	private String cookList() {
		editProfile = false;
		return "/view/profile/cook/cook_list.xhtml";
	}
	
	private String cookJournal() {
		return "/view/profile/cook/cook_journal.xhtml";
	}
	
	private String cookReq() {
		return "/view/profile/cook/cook_req.xhtml";
	}

	private String cookInProgres() {
		return "/view/profile/cook/cook_in_progres.xhtml";
	}
	
	
	public String back() {
		cook = new Cook();
		return cookReq();	
	}
	
	public Boolean getEditProfile() {
		return editProfile;
	}
	
	public void setEditProfile(Boolean editProfile) {
		this.editProfile = editProfile;
	}

	public Cook getCook() {
		return cook;
	}

	public void setCook(Cook cook) {
		this.cook = cook;
	}

	public Set<Dictionary> getFoods() {
		return foods;
	}

	public void setFoods(Set<Dictionary> foods) {
		this.foods = foods;
	}

	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}
	
}

