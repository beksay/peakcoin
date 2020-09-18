package org.infosystema.peakcoin.controller;

import java.io.IOException;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

import org.infosystema.peakcoin.conversation.ConversationOblast;
import org.infosystema.peakcoin.domain.Oblast;
import org.infosystema.peakcoin.service.OblastService;
import org.infosystema.peakcoin.util.web.FacesMessages;
import org.infosystema.peakcoin.util.web.Messages;
import org.infosystema.peakcoin.validator.EntityValidator;
import org.primefaces.event.SelectEvent;


@ManagedBean
@ViewScoped
public class OblastController {

	@EJB
	private OblastService oblastService;
	@Inject
	private EntityValidator validator;
	@Inject
	private ConversationOblast conversation;	
	@Inject
	private CountryController countryController;
	
	private Oblast oblast;
    
	@PostConstruct
	public void init() {
		oblast=conversation.getOblast();
		if (oblast==null)	oblast= new Oblast();
	}
	
	public String add() {
		oblast = new Oblast();
		conversation.setOblast(oblast);
		return form();
	}
	
	public String edit(Oblast oblast) {
		this.oblast = oblast;
		conversation.setOblast(oblast);
		return form();
	}
	
	public String save() {
		System.out.println(oblast);
		if(oblast == null){
			FacesMessages.addMessage(Messages.getMessage("invalidData"), Messages.getMessage("invalidData"), null);
			return null;
		}
		List<Oblast> identifiers = oblastService.findByProperty("name", oblast.getName());
    	if(identifiers.size()>0){
    		FacesContext.getCurrentInstance().addMessage("form", new FacesMessage( FacesMessage.SEVERITY_ERROR,  Messages.getMessage("nameAlreadyExists"), null) );
			return null;
    	}
    	
    	oblast.setCountry(countryController.getCountry());
		validator.validate(oblast);
		if(!FacesContext.getCurrentInstance().getMessageList().isEmpty()) return null;
		oblast = oblast.getId() == null ? oblastService.persist(oblast) : oblastService.merge(oblast);
		
		conversation.setOblast(null);
		
	    return cancel();  
		
	}
	
	public void onRowSelect(SelectEvent event) throws IOException {
		oblast=(Oblast) event.getObject();
		conversation.setOblast(oblast);
        FacesContext.getCurrentInstance().getExternalContext().redirect("/peakcoin/view/country/location_list.xhtml?cid="+conversation.getId());
        
    }
	
	public String delete(Oblast c) {
		System.out.println(c);
		oblastService.remove(c);
		return cancel();
	}
	
	public String cancel() {
		oblast = null;
		return list();
	}
	
	private String list() {
		return "/view/country/oblast_list.xhtml?faces-redirect=true";
	}
	
	private String form() {
		return "/view/country/oblast_form.xhtml";
	}

	public CountryController getCountryController() {
		return countryController;
	}

	public void setCountryController(CountryController countryController) {
		this.countryController = countryController;
	}

	public Oblast getOblast() {
		return oblast;
	}

	public void setOblast(Oblast oblast) {
		this.oblast = oblast;
	}

}
