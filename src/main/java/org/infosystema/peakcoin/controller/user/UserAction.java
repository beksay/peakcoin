package org.infosystema.peakcoin.controller.user;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

import org.infosystema.peakcoin.annotation.Logged;
import org.infosystema.peakcoin.beans.FilterExample;
import org.infosystema.peakcoin.conversation.ConversationUser;
import org.infosystema.peakcoin.domain.Company;
import org.infosystema.peakcoin.domain.Country;
import org.infosystema.peakcoin.domain.Person;
import org.infosystema.peakcoin.domain.User;
import org.infosystema.peakcoin.enums.UserStatus;
import org.infosystema.peakcoin.service.CompanyService;
import org.infosystema.peakcoin.service.CountryService;
import org.infosystema.peakcoin.service.PersonService;
import org.infosystema.peakcoin.service.UserService;
import org.infosystema.peakcoin.util.web.FacesMessages;
import org.infosystema.peakcoin.util.web.LoginUtil;
import org.infosystema.peakcoin.util.web.Messages;
import org.infosystema.peakcoin.validator.EntityValidator;

@Logged
@ManagedBean
@ViewScoped
public class UserAction {

	@EJB
	private UserService service;
	@EJB
	private PersonService personService;
	@EJB
	private CompanyService companyService;
	@EJB
	private CountryService countryService;
	
	@Inject
	private EntityValidator validator;
	@Inject
	private LoginUtil loginUtil;
	@Inject
	private ConversationUser conversation;	
	
	private User user;
	private Person person;
	private Company company;
    
	@PostConstruct
	public void init() {
		user=conversation.getUser();
		if (user==null)	user= new User();
		person=conversation.getPerson();
		if (person==null)	person= new Person();
		company=conversation.getCompany();
		if (company==null)	company= new Company();
	}
	
	public String add() {
		user = new User();
		conversation.setUser(user);
		return form();
	}
	
	public String edit(User user) {
		this.setPerson(user.getPerson());
		this.user = user;
		conversation.setUser(user);
		conversation.setPerson(person);
		return form();
	}
	
	public String savePerson() {
		System.out.println(user);
		if(user == null){
			FacesMessages.addMessage(Messages.getMessage("invalidData"), Messages.getMessage("invalidData"), null);
			return null;
		}
		List<User> users = service.findByProperty("email", user.getEmail());
    	if(users.equals(0)){
    		FacesContext.getCurrentInstance().addMessage("form", new FacesMessage( FacesMessage.SEVERITY_ERROR,  Messages.getMessage("usernameIsAlreadyExists"), null) );
			return null;
    	}
		
		user.setPerson(person);
		user.setStatus(UserStatus.ACTIVE);
		user.setCountFailed(0);
		validator.validate(user);
		if(!FacesContext.getCurrentInstance().getMessageList().isEmpty()) return null;
		user.setPerson(user.getPerson().getId() == null ? personService.persist(user.getPerson()) : personService.merge(user.getPerson()));
		
		if (user.getId() == null) {
			try {
				String hashPassword = loginUtil.getHashPassword("123");
				user.setPassword(hashPassword);
			} catch (Exception e) {
				e.printStackTrace();
			}
			service.persist(user);
		} else {
			service.merge(user);
		}
		
		conversation.setUser(null);
		conversation.setRole(null);
		

		if(loginUtil.isLogged()) {
		    return cancel();  
		}
		else{
			return listPublic();
		} 
	}

	
	public String delete(User c) {
		System.out.println(c);
		service.remove(c);
		return cancel();
	}
	
	public String cancel() {
		user = null;
		return list();
	}
	
	private String list() {
		return "/view/user/user_journal.xhtml?faces-redirect=true";
	}
	
	private String form() {
		return "/view/user/user_form.xhtml";
	}
	
	private String listPublic() {
		return "/view/user/login.xhtml?faces-redirect=true";
	}
	
	public String returnHome() {
		user = null;
		return home();
	}
	
	public String returnLogin() {
		user = null;
		return listPublic();
	}
	
	public String registerCompany(){
		return "/view/public/registration_company.xhtml";
	}
	
	public String registerTourist(){
		return "/view/public/registration_tourist.xhtml";
	}
	
	private String home(){
		return "/home.xhtml";
	}
	
	public List<Country> getCountryList() {
		List<FilterExample> examples = new ArrayList<>();
		return countryService.findByExample(0, 10, examples);
	}
	
	public void deletePassword(User user){
		user.setPassword("2dcb6c95b1fdda5605aa58356915327d95e8b11ad729d67255aa1b934f7c904467aa47d3cc1590b838162428f15c5bbe1fb45fc351a1e92f9003e0366749c2f8");
		service.merge(user);
		
		FacesMessage message = new FacesMessage(Messages.getMessage("passwordChanged"));
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, message);
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	public Person getPerson() {
		return person;
	}
	
	public void setPerson(Person person) {
		this.person = person;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

}
