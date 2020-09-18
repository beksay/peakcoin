package org.infosystema.peakcoin.controller;

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
import javax.transaction.SystemException;

import org.infosystema.peakcoin.annotation.Logged;
import org.infosystema.peakcoin.beans.FilterExample;
import org.infosystema.peakcoin.beans.InequalityConstants;
import org.infosystema.peakcoin.controller.base.Conversational;
import org.infosystema.peakcoin.controller.base.FileUploadController;
import org.infosystema.peakcoin.conversation.ConversationOblast;
import org.infosystema.peakcoin.domain.Attachment;
import org.infosystema.peakcoin.domain.Dictionary;
import org.infosystema.peakcoin.domain.Location;
import org.infosystema.peakcoin.service.DictionaryService;
import org.infosystema.peakcoin.service.LocationService;
import org.infosystema.peakcoin.util.Util;
import org.infosystema.peakcoin.util.web.FacesMessages;
import org.infosystema.peakcoin.util.web.Messages;
import org.infosystema.peakcoin.validator.EntityValidator;


@Logged
@Named
@ConversationScoped
public class LocationController extends Conversational{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@EJB
	private LocationService locationService;
	@EJB
	private DictionaryService dictService;
	
	@Inject
	private EntityValidator validator;
	@Inject
	private ConversationOblast conversationOblast;
	@Inject
	private FileUploadController fileUploadController;
	
	private Location location;
    
	@PostConstruct
	public void init() {
		if (location==null)	location= new Location();
	}
	
	public String add() {
		location = new Location();
		return form();
	}
	
	public String edit(Location location) {
		this.location = locationService.getByIdWithFields(location.getId(), new String[]{"attachments"});
		fileUploadController.setFiles(Util.getFiles(location.getAttachments()));
		return form();
	}
	
	public String save() throws Exception {
		System.out.println(location);
		if(location == null){
			FacesMessages.addMessage(Messages.getMessage("invalidData"), Messages.getMessage("invalidData"), null);
			return null;
		}
		List<Location> identifiers = locationService.findByProperty("name", location.getName());
    	if(identifiers.size()>0){
    		FacesContext.getCurrentInstance().addMessage("form", new FacesMessage( FacesMessage.SEVERITY_ERROR,  Messages.getMessage("nameAlreadyExists"), null) );
			return null;
    	}
    	Set<Attachment> attachments = new HashSet<>();
		try {
			attachments.addAll(fileUploadController.convert());
		} catch (SystemException e) {
			e.printStackTrace();
		}
		location.setAttachments(attachments);
    	location.setOblast(conversationOblast.getOblast());
		validator.validate(location);
		if(!FacesContext.getCurrentInstance().getMessageList().isEmpty()) return null;
		location = location.getId() == null ? locationService.persist(location) : locationService.merge(location);
	
	    return cancel();  
		
	}
	
	public String delete(Location c) {
		System.out.println(c);
		locationService.remove(c);
		return cancel();
	}
	
	public String cancel() {
		location = null;
		return list();
	}
	
	private String list() {
		return "/view/country/location_list.xhtml?faces-redirect=true";
	}
	
	private String form() {
		return "/view/country/location_form.xhtml";
	}
	
	public List<Dictionary> getCategoryList() {
		List<FilterExample> examples = new ArrayList<>();
		examples.add(new FilterExample("dictionaryType.id", 8, InequalityConstants.EQUAL));
		return dictService.findByExample(0, 10, examples);
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	public ConversationOblast getConversationOblast() {
		return conversationOblast;
	}

	public void setConversationOblast(ConversationOblast conversationOblast) {
		this.conversationOblast = conversationOblast;
	}

}
