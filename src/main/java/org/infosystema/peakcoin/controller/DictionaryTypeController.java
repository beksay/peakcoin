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

import org.infosystema.peakcoin.conversation.ConversationDictType;
import org.infosystema.peakcoin.domain.DictionaryType;
import org.infosystema.peakcoin.service.DictionaryTypeService;
import org.infosystema.peakcoin.util.web.FacesMessages;
import org.infosystema.peakcoin.util.web.Messages;
import org.infosystema.peakcoin.validator.EntityValidator;
import org.primefaces.event.SelectEvent;


@ManagedBean
@ViewScoped
public class DictionaryTypeController {

	@EJB
	private DictionaryTypeService dictionaryTypeService;
	@Inject
	private EntityValidator validator;
	@Inject
	private ConversationDictType conversation;	
	
	private DictionaryType dictionaryType;
    
	@PostConstruct
	public void init() {
		dictionaryType=conversation.getDictionaryType();
		if (dictionaryType==null)	dictionaryType= new DictionaryType();
	}
	
	public String add() {
		dictionaryType = new DictionaryType();
		conversation.setDictionaryType(dictionaryType);
		return form();
	}
	
	public String edit(DictionaryType dictionaryType) {
		this.dictionaryType = dictionaryType;
		conversation.setDictionaryType(dictionaryType);
		return form();
	}
	
	public String save() {
		System.out.println(dictionaryType);
		if(dictionaryType == null){
			FacesMessages.addMessage(Messages.getMessage("invalidData"), Messages.getMessage("invalidData"), null);
			return null;
		}
		List<DictionaryType> identifiers = dictionaryTypeService.findByProperty("name", dictionaryType.getName());
    	if(identifiers.equals(0)){
    		FacesContext.getCurrentInstance().addMessage("form", new FacesMessage( FacesMessage.SEVERITY_ERROR,  Messages.getMessage("nameAlreadyExists"), null) );
			return null;
    	}
		validator.validate(dictionaryType);
		if(!FacesContext.getCurrentInstance().getMessageList().isEmpty()) return null;
		dictionaryType = dictionaryType.getId() == null ? dictionaryTypeService.persist(dictionaryType) : dictionaryTypeService.merge(dictionaryType);
		
		conversation.setDictionaryType(null);
		
	    return cancel();  
		
	}
	
	public void onRowSelect(SelectEvent event) throws IOException {
		dictionaryType=(DictionaryType) event.getObject();
		conversation.setDictionaryType(dictionaryType);
        FacesContext.getCurrentInstance().getExternalContext().redirect("/peakcoin/view/dictionary/dictionary_list.xhtml?cid="+conversation.getId());
        
    }
	
	public String delete(DictionaryType c) {
		System.out.println(c);
		dictionaryTypeService.remove(c);
		return cancel();
	}
	
	public String cancel() {
		dictionaryType = null;
		return list();
	}
	
	private String list() {
		return "/view/dictionary/dict_type_list.xhtml?faces-redirect=true";
	}
	
	private String form() {
		return "/view/dictionary/dict_type_form.xhtml";
	}
	
	public DictionaryType getDictionaryType() {
		return dictionaryType;
	}
	
	public void setDictionaryType(DictionaryType dictionaryType) {
		this.dictionaryType = dictionaryType;
	}

}
