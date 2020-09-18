package org.infosystema.peakcoin.controller.user;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

import org.apache.commons.io.IOUtils;
import org.infosystema.peakcoin.beans.FilterExample;
import org.infosystema.peakcoin.beans.Message;
import org.infosystema.peakcoin.conversation.ConversationUser;
import org.infosystema.peakcoin.domain.Country;
import org.infosystema.peakcoin.domain.Person;
import org.infosystema.peakcoin.domain.Role;
import org.infosystema.peakcoin.domain.User;
import org.infosystema.peakcoin.enums.UserStatus;
import org.infosystema.peakcoin.service.CompanyService;
import org.infosystema.peakcoin.service.CountryService;
import org.infosystema.peakcoin.service.PersonService;
import org.infosystema.peakcoin.service.RoleService;
import org.infosystema.peakcoin.service.UserService;
import org.infosystema.peakcoin.util.MailSender;
import org.infosystema.peakcoin.util.PasswordBuilder;
import org.infosystema.peakcoin.util.web.FacesMessages;
import org.infosystema.peakcoin.util.web.LoginUtil;
import org.infosystema.peakcoin.util.web.Messages;
import org.infosystema.peakcoin.validator.EntityValidator;


@ManagedBean
@ViewScoped
public class RegistrationAction {

	@EJB
	private UserService service;
	@EJB
	private PersonService personService;
	@EJB
	private CompanyService companyService;
	@EJB
	private RoleService roleService;
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
    
	@PostConstruct
	public void init() {
		user=conversation.getUser();
		if (user==null)	user= new User();
		person=conversation.getPerson();
		if (person==null)	person= new Person();
	}
	
	public String save() {
		System.out.println(user);
		if(user == null){
			FacesMessages.addMessage(Messages.getMessage("invalidData"), Messages.getMessage("invalidData"), null);
			return null;
		}
		List<User> users = service.findByProperty("email", user.getEmail());
    	if(!users.isEmpty()){
    		FacesContext.getCurrentInstance().addMessage("form", new FacesMessage( FacesMessage.SEVERITY_ERROR,  Messages.getMessage("emailIsAlreadyExists"), null) );
			return null;
    	}
    	
    	if (person.getRequirement()==false) {
    		FacesContext.getCurrentInstance().addMessage("form", new FacesMessage( FacesMessage.SEVERITY_ERROR,  Messages.getMessage("requerementRequired"), null) );
			return null;
		}
    	
    	PasswordBuilder accountBuilder = new PasswordBuilder();
    	accountBuilder.digits(7);
	    
	    String personAccount = accountBuilder.build();
	    
	    List<Person> personAccountCheck = personService.findByProperty("account", personAccount);
    	if(!personAccountCheck.isEmpty()){
    		FacesContext.getCurrentInstance().addMessage("form", new FacesMessage( FacesMessage.SEVERITY_ERROR,  Messages.getMessage("tryAgain"), null) );
			return null;
    	}
 
	    person.setAccount(personAccount);
	    person.setPeakcoin(new BigDecimal(0));
        
		Role addRole = roleService.findById(2, false);
		
		user.setRole(addRole);
		
		Calendar calendar = new GregorianCalendar();
		calendar.add(Calendar.DAY_OF_MONTH, 90);
		
		user.setPerson(person);
		user.setStatus(UserStatus.ACTIVE);
		user.setCountFailed(0);
		user.setDatePasswordExpired(calendar.getTime());
		validator.validate(user);
		if(!FacesContext.getCurrentInstance().getMessageList().isEmpty()) return null;
		
		InputStream stream = this.getClass().getClassLoader().getResourceAsStream("user.template");
		String template = null;
		try {
			template = IOUtils.toString(stream, "UTF-8");
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		PasswordBuilder builder = new PasswordBuilder();
	    builder.lowercase(2)
	            .uppercase(8)
	            .specials(2)
	            .digits(2)
	            .shuffle();
	    
	    String password = builder.build();
	    String body = MessageFormat.format(template, new Object[]{user.getEmail(), user.getEmail(), user.getPerson().getFullname(), password});
	    Message message = new Message();
		message.setEmail(user.getEmail());
		message.setSubject("PeakCoin");
		message.setContent(body);
		
		MailSender.getInstance().asyncSend(message);
        
	    try {
			user.setPassword(loginUtil.getHashPassword(password));
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	    
	    user.setPerson(user.getPerson().getId() == null ? personService.persist(user.getPerson()) : personService.merge(user.getPerson()));
	    
		if (user.getId() == null) {
			service.persist(user);
		} else {
			service.merge(user);
		}
		
		FacesContext.getCurrentInstance().addMessage("login-form", new FacesMessage( FacesMessage.SEVERITY_INFO,  Messages.getMessage("messagesSendToEmail"), null) );
        
		conversation.setUser(null);
		conversation.setRole(null);
		
		return "/view/public/thank_you.xhtml?faces-redirect=true";
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
	
	public String register(){
		return "/view/public/registration.xhtml";
	}
	
	private String home(){
		return "/home.xhtml";
	}
	
	public List<Country> getCountryList() {
		List<FilterExample> examples = new ArrayList<>();
		return countryService.findByExample(0, 10, examples);
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

}
