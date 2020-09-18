package org.infosystema.peakcoin.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
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
import org.infosystema.peakcoin.beans.InequalityConstants;
import org.infosystema.peakcoin.conversation.ConversationOrder;
import org.infosystema.peakcoin.domain.Company;
import org.infosystema.peakcoin.domain.Orders;
import org.infosystema.peakcoin.domain.Participants;
import org.infosystema.peakcoin.domain.Person;
import org.infosystema.peakcoin.domain.User;
import org.infosystema.peakcoin.service.CompanyService;
import org.infosystema.peakcoin.service.OrdersService;
import org.infosystema.peakcoin.service.ParticipantsService;
import org.infosystema.peakcoin.service.PersonService;
import org.infosystema.peakcoin.service.UserService;
import org.infosystema.peakcoin.util.web.FacesMessages;
import org.infosystema.peakcoin.util.web.LoginUtil;
import org.infosystema.peakcoin.util.web.Messages;

@Logged
@ManagedBean
@ViewScoped
public class HikingController {

	@EJB
	private OrdersService service;
	@EJB
	private ParticipantsService participantsService;
	@EJB
	private PersonService personService;
	@EJB
	private CompanyService companyService;
	@EJB
	private UserService userService;
	@Inject
	private LoginUtil loginUtil;
	@Inject
	private ConversationOrder conversation;	

	private Orders orders;
	private Company company;
	private Participants participants;
	private Person person;
    
	@PostConstruct
	public void init() {
		orders=conversation.getOrders();
		if (orders==null)	orders= new Orders();
		company=conversation.getCompany();
		participants=conversation.getParticipants();
		if (participants==null)	participants= new Participants();
		person=conversation.getPerson();
		if (person==null)	person= new Person();
	}

	
	public String preview(Orders orders) {
		this.orders = orders;
		conversation.setOrders(orders);
		return form();
	}
	
	public String linkCompany(Orders orders) {
		this.setCompany(orders.getCompany());
		this.orders = orders;
		conversation.setOrders(orders);
		conversation.setCompany(company);
		return companyForm();
	}
	
	public String joinJourney(Orders orders) {
		person = loginUtil.getCurrentUser().getPerson();
		if (person.getPeakcoin().compareTo(orders.getPeakcoin()) > 0) {
			System.out.println("========youDontHaveEnoughtPeakCoin===");
			FacesContext.getCurrentInstance().addMessage("form", new FacesMessage( FacesMessage.SEVERITY_ERROR,  Messages.getMessage("youDontHaveEnoughtPeakCoin"), null) );
			return null;
		}	
		
		List<FilterExample> filters = new ArrayList<>();
		filters.add(new FilterExample("person", loginUtil.getCurrentUser().getPerson(), InequalityConstants.EQUAL));
		filters.add(new FilterExample("orders", orders, InequalityConstants.EQUAL));
		List<Participants> checkParticipant = participantsService.findByExample(0, 10, filters);
    	if(!checkParticipant.isEmpty()){
    		FacesContext.getCurrentInstance().addMessage("form", new FacesMessage( FacesMessage.SEVERITY_ERROR,  Messages.getMessage("youAlreadyJoinedToThisJourney"), null) );
			return null;
    	}
    	List<FilterExample> checkFilter = new ArrayList<>();
    	checkFilter.add(new FilterExample("orders", orders, InequalityConstants.EQUAL));
    	Long quantity = participantsService.countByExample(checkFilter);
    	if (quantity>=orders.getQuantity()) {
    		FacesContext.getCurrentInstance().addMessage("form", new FacesMessage( FacesMessage.SEVERITY_ERROR,  Messages.getMessage("noOrderToJoin"), null) );
			return null;
		}
 	
    	BigDecimal personPeak = person.getPeakcoin();
    	BigDecimal orderPeak = orders.getPeakcoin();
    	/*if (personPeak.compareTo(orderPeak) == 1) {
    		FacesContext.getCurrentInstance().addMessage("form", new FacesMessage( FacesMessage.SEVERITY_ERROR,  Messages.getMessage("peakcoinNotEnought"), null) );
			return null;
		}*/
    	System.out.println("personPeak==" + personPeak);
    	System.out.println("orderPeak==" + orderPeak);
    	person.setPeakcoin(personPeak.subtract(orderPeak));
    	personService.merge(person);
    	
    	orders.getCompany().setPeakcoin(orders.getCompany().getPeakcoin().add(orderPeak));
    	companyService.merge(orders.getCompany());
    	
    	participants.setDate(new Date());
    	participants.setOrders(orders);
    	participants.setPerson(loginUtil.getCurrentUser().getPerson());
    	participantsService.persist(participants);
		return null;
	}
	
	public String save() {
		System.out.println(orders);
		if(orders == null){
			FacesMessages.addMessage(Messages.getMessage("invalidData"), Messages.getMessage("invalidData"), null);
			return null;
		}
		if (orders.getId() == null) {
			service.persist(orders);
		} else {
			service.merge(orders);
		}
		
		conversation.setOrders(null);

	    return cancel();  
	}
	
	public String cancel() {
		orders = null;
		return list();
	}
	
	private String list() {
		return "/view/hiking/hiking_list.xhtml?faces-redirect=true";
	}
	
	private String form() {
		return "/view/hiking/hiking_preview.xhtml";
	}
	
	private String companyForm() {
		return "/view/hiking/company_info.xhtml";
	}
	
	public List<Participants> getParticipantsList(Orders orders) {
		List<FilterExample> examples = new ArrayList<>();
		examples.add(new FilterExample("orders", orders, InequalityConstants.EQUAL));
		return participantsService.findByExample(0, 1000, examples);
	}
	
	public Long getParticipantsNumber(Orders orders) {
		List<FilterExample> examples = new ArrayList<>();
		examples.add(new FilterExample("orders", orders, InequalityConstants.EQUAL));
		return participantsService.countByExample(examples);
	}
	
	public Boolean isJoinedJourney(Orders orders) {
		List<FilterExample> filters = new ArrayList<>();
		filters.add(new FilterExample("person", loginUtil.getCurrentUser().getPerson(), InequalityConstants.EQUAL));
		filters.add(new FilterExample("orders", orders, InequalityConstants.EQUAL));
		Long checkParticipant = participantsService.countByExample(filters);
    	if(checkParticipant>0){
    		return true;
    	}else {
			return false;
		}
	}
	
	public Orders getOrders() {
		return orders;
	}

	public void setOrders(Orders orders) {
		this.orders = orders;
	}
	
	public ConversationOrder getConversation() {
		return conversation;
	}
	
	public void setConversation(ConversationOrder conversation) {
		this.conversation = conversation;
	}


	public Company getCompany() {
		return company;
	}


	public void setCompany(Company company) {
		this.company = company;
	}


	public Participants getParticipants() {
		return participants;
	}


	public void setParticipants(Participants participants) {
		this.participants = participants;
	}


	public Person getPerson() {
		return person;
	}


	public void setPerson(Person person) {
		this.person = person;
	}
}
