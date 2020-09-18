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

import org.infosystema.peakcoin.conversation.ConversationCountry;
import org.infosystema.peakcoin.domain.Country;
import org.infosystema.peakcoin.service.CountryService;
import org.infosystema.peakcoin.util.web.FacesMessages;
import org.infosystema.peakcoin.util.web.Messages;
import org.infosystema.peakcoin.validator.EntityValidator;
import org.primefaces.event.SelectEvent;


@ManagedBean
@ViewScoped
public class CountryController {

	@EJB
	private CountryService countryService;
	@Inject
	private EntityValidator validator;
	@Inject
	private ConversationCountry conversation;	
	
	private Country country;
    
	@PostConstruct
	public void init() {
		country=conversation.getCountry();
		if (country==null)	country= new Country();
	}
	
	public String add() {
		country = new Country();
		conversation.setCountry(country);
		return form();
	}
	
	public String edit(Country country) {
		this.country = country;
		conversation.setCountry(country);
		return form();
	}
	
	public String save() {
		System.out.println(country);
		if(country == null){
			FacesMessages.addMessage(Messages.getMessage("invalidData"), Messages.getMessage("invalidData"), null);
			return null;
		}
		List<Country> identifiers = countryService.findByProperty("name", country.getName());
    	if(identifiers.size()>0){
    		FacesContext.getCurrentInstance().addMessage("form", new FacesMessage( FacesMessage.SEVERITY_ERROR,  Messages.getMessage("nameAlreadyExists"), null) );
			return null;
    	}
		validator.validate(country);
		if(!FacesContext.getCurrentInstance().getMessageList().isEmpty()) return null;
		country = country.getId() == null ? countryService.persist(country) : countryService.merge(country);
		
		conversation.setCountry(null);
		
	    return cancel();  
		
	}
	
	public void onRowSelect(SelectEvent event) throws IOException {
		country=(Country) event.getObject();
		conversation.setCountry(country);
        FacesContext.getCurrentInstance().getExternalContext().redirect("/peakcoin/view/country/oblast_list.xhtml?cid="+conversation.getId());
        
    }
	
	public String delete(Country c) {
		System.out.println(c);
		countryService.remove(c);
		return cancel();
	}
	
	public String cancel() {
		country = null;
		return list();
	}
	
	private String list() {
		return "/view/country/country_list.xhtml?faces-redirect=true";
	}
	
	private String form() {
		return "/view/country/country_form.xhtml";
	}
	
	public Country getCountry() {
		return country;
	}
	
	public void setCountry(Country country) {
		this.country = country;
	}

}
