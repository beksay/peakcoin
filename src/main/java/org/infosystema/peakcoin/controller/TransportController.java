package org.infosystema.peakcoin.controller;

import java.io.IOException;
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
import org.infosystema.peakcoin.conversation.ConversationTransport;
import org.infosystema.peakcoin.domain.Dictionary;
import org.infosystema.peakcoin.domain.Driver;
import org.infosystema.peakcoin.domain.Transport;
import org.infosystema.peakcoin.enums.TransportStatus;
import org.infosystema.peakcoin.service.AttachmentService;
import org.infosystema.peakcoin.service.DictionaryService;
import org.infosystema.peakcoin.service.DriverService;
import org.infosystema.peakcoin.service.TransportService;
import org.infosystema.peakcoin.util.web.FacesMessages;
import org.infosystema.peakcoin.util.web.LoginUtil;
import org.infosystema.peakcoin.util.web.Messages;
import org.infosystema.peakcoin.validator.EntityValidator;
import org.primefaces.event.SelectEvent;

@Logged
@ManagedBean
@ViewScoped
public class TransportController {

	@EJB
	private TransportService service;
	@EJB
	AttachmentService atService;
	@EJB
	private DictionaryService dictService;
	@EJB
	private DriverService driverService;
	
	@Inject
	private EntityValidator validator;
	@Inject
	private LoginUtil loginUtil;
	@Inject
	private ConversationTransport conversation;	
	
	private Transport transport;
    
	@PostConstruct
	public void init() {
		transport=conversation.getTransport();
		if (transport==null) transport= new Transport();
	}
	
	public String add() {
		transport = new Transport(); 
		conversation.setTransport(transport);
		return form();
	}
	
	public String edit(Transport transport) {
		this.transport = transport;
		conversation.setTransport(transport);
		return form();
	}
	
	public String save() {
		System.out.println(transport); 
		if(transport == null){
			FacesMessages.addMessage(Messages.getMessage("invalidData"), Messages.getMessage("invalidData"), null);
			return null;
		}
		
		List<Transport> transports = service.findByProperty("number", transport.getNumber());
    	if(transports.size()>0){
    		FacesContext.getCurrentInstance().addMessage("form", new FacesMessage( FacesMessage.SEVERITY_ERROR,  Messages.getMessage("numberAlreadyExists"), null) );
			return null;
    	}
		
    	//transport.setCompany(loginUtil.getCurrentUser().getPerson().getCompany());
    	transport.setDateCreated(new Date());
		validator.validate(transport);
		if(!FacesContext.getCurrentInstance().getMessageList().isEmpty()) return null;

		if (transport.getId() == null) {
			transport.setStatus(TransportStatus.IN_PROGRES);
			service.persist(transport);
		} else {
			service.merge(transport);
		}
		
		conversation.setTransport(null);

	    return cancel();  
	}
	
	public void onRowSelect(SelectEvent event) throws IOException {
		transport=(Transport) event.getObject();
		conversation.setTransport(transport);
        FacesContext.getCurrentInstance().getExternalContext().redirect("/peakcoin/view/transport/transport_preview.xhtml?cid="+conversation.getId());
        
    }
	
	public String delete(Transport c) {
		System.out.println(c);
		service.remove(c);
		return cancel();
	}
	
	public String cancel() {
		transport = null;
		return list();
	}
	
	private String list() {
		return "/view/transport/transport_list.xhtml?faces-redirect=true";
	}
	
	private String form() {
		return "/view/transport/transport_form.xhtml";
	}
	
	public List<Dictionary> getTransportTypeList() {
		List<FilterExample> examples = new ArrayList<>();
		examples.add(new FilterExample("dictionaryType.id", 1, InequalityConstants.EQUAL));
		return dictService.findByExample(0, 10, examples);
	}
	
	public List<Dictionary> getMarkList() {
		List<FilterExample> examples = new ArrayList<>();
		examples.add(new FilterExample("dictionaryType.id", 7, InequalityConstants.EQUAL));
		return dictService.findByExample(0, 10, examples);
	}
	
	public List<Dictionary> getColorList() {
		List<FilterExample> examples = new ArrayList<>();
		examples.add(new FilterExample("dictionaryType.id", 2, InequalityConstants.EQUAL));
		return dictService.findByExample(0, 10, examples);
	}
	
	public List<Driver> getDriverList() {
		List<FilterExample> examples = new ArrayList<>();
		//examples.add(new FilterExample("company", loginUtil.getCurrentUser().getPerson().getCompany(), InequalityConstants.EQUAL));
		return driverService.findByExample(0, 10, examples);
	}

	public Transport getTransport() {
		return transport;
	}
	
	public void setTransport(Transport transport) {
		this.transport = transport;
	}

	public ConversationTransport getConversation() {
		return conversation;
	}
	
	public void setConversation(ConversationTransport conversation) {
		this.conversation = conversation;
	}
}
