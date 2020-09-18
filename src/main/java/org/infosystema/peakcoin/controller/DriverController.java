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

import org.apache.commons.io.IOUtils;
import org.infosystema.peakcoin.annotation.Logged;
import org.infosystema.peakcoin.beans.FilterExample;
import org.infosystema.peakcoin.conversation.ConversationDriver;
import org.infosystema.peakcoin.domain.Attachment;
import org.infosystema.peakcoin.domain.Country;
import org.infosystema.peakcoin.domain.Driver;
import org.infosystema.peakcoin.dto.AttachmentBinaryDTO;
import org.infosystema.peakcoin.enums.DriverStatus;
import org.infosystema.peakcoin.service.AttachmentService;
import org.infosystema.peakcoin.service.CountryService;
import org.infosystema.peakcoin.service.DriverService;
import org.infosystema.peakcoin.util.Translit;
import org.infosystema.peakcoin.util.Util;
import org.infosystema.peakcoin.util.web.FacesMessages;
import org.infosystema.peakcoin.util.web.LoginUtil;
import org.infosystema.peakcoin.util.web.Messages;
import org.infosystema.peakcoin.validator.EntityValidator;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.UploadedFile;

@Logged
@ManagedBean
@ViewScoped
public class DriverController {

	@EJB
	private DriverService service;
	@EJB
	AttachmentService atService;
	@EJB
	private CountryService countryService;
	
	@Inject
	private EntityValidator validator;
	@Inject
	private LoginUtil loginUtil;
	@Inject
	private ConversationDriver conversation;	
	private AttachmentBinaryDTO passport;
	private AttachmentBinaryDTO license;
	private List<Attachment> removedFiles = new ArrayList<Attachment>();
	
	private Driver driver;
    
	@PostConstruct
	public void init() {
		driver=conversation.getDriver();
		if (driver==null) driver= new Driver();
		passport=conversation.getPassport();
		license=conversation.getLicense();
	}
	
	public String add() {
		driver = new Driver(); 
		conversation.setDriver(driver);
		passport = null;
		license = null;
		return form();
	}
	
	public String edit(Driver driver) {
		this.driver = driver;
		try {
			if (driver.getPassport() != null)
				passport = Util.createAttachmentDTO(driver.getPassport());
			else
				passport = null;
		} catch (Exception e) {
			passport = null;
		}
		try {
			if (driver.getLicense() != null)
				license = Util.createAttachmentDTO(driver.getLicense());
			else
				license = null;
		} catch (Exception e) {
			license = null;
		}
		conversation.setPassport(passport);
		conversation.setLicense(license);
		conversation.setDriver(driver);
		return form();
	}
	
	public String save() {
		System.out.println(driver);
		if(driver == null){
			FacesMessages.addMessage(Messages.getMessage("invalidData"), Messages.getMessage("invalidData"), null);
			return null;
		}
		
		List<Driver> drivers = service.findByProperty("pin", driver.getPin());
    	if(drivers.size()>0){
    		FacesContext.getCurrentInstance().addMessage("form", new FacesMessage( FacesMessage.SEVERITY_ERROR,  Messages.getMessage("pinIsAlreadyExists"), null) );
			return null;
    	}
	
		if(passport != null) {
    		Attachment attachment = new Attachment();
			attachment = createAttachment(passport);
			passport.setAttachment(attachment);
			try {
				attachment = passport.getAttachment().getId() == null ? atService.saveFromDTO(passport) : passport.getAttachment();
				driver.setPassport(attachment);
				attachment = new Attachment();
			} catch (IOException e) {e.printStackTrace();}			
		}else {
			FacesContext.getCurrentInstance().addMessage("form", new FacesMessage( FacesMessage.SEVERITY_ERROR,  Messages.getMessage("passportRequired"), null) );
			return null;
		}
		if(license != null) {
    		Attachment attachment = new Attachment();
			attachment = createAttachment(license);
			license.setAttachment(attachment);
			try {
				attachment = license.getAttachment().getId() == null ? atService.saveFromDTO(license) : license.getAttachment();
				driver.setLicense(attachment);
				attachment = new Attachment();
			} catch (IOException e) {e.printStackTrace();}			
		}else {
			FacesContext.getCurrentInstance().addMessage("form", new FacesMessage( FacesMessage.SEVERITY_ERROR,  Messages.getMessage("licenseRequired"), null) );
			return null;
		}
		
		//driver.setCompany(loginUtil.getCurrentUser().getPerson().getCompany());
		driver.setDateCreated(new Date());
		validator.validate(driver);
		if(!FacesContext.getCurrentInstance().getMessageList().isEmpty()) return null;

		if (driver.getId() == null) {
			driver.setStatus(DriverStatus.IN_PROGRES);
			service.persist(driver);
		} else {
			service.merge(driver);
		}
		
		conversation.setDriver(null);

	    return cancel();  
	}
	
	public void onRowSelect(SelectEvent event) throws IOException {
		driver=(Driver) event.getObject();
		conversation.setDriver(driver);
        FacesContext.getCurrentInstance().getExternalContext().redirect("/peakcoin/view/driver/driver_preview.xhtml?cid="+conversation.getId());
        
    }
	
	public String delete(Driver c) {
		System.out.println(c);
		service.remove(c);
		return cancel();
	}
	
	public String cancel() {
		driver = null;
		return list();
	}
	
	private String list() {
		return "/view/driver/driver_list.xhtml?faces-redirect=true";
	}
	
	private String form() {
		return "/view/driver/driver_form.xhtml";
	}
	
	public List<Country> getCountryList() {
		List<FilterExample> examples = new ArrayList<>();
		return countryService.findByExample(0, 10, examples);
	}
	
	public void removePassport() {		
		if(passport.getAttachment() != null && passport.getAttachment().getId() != null) removedFiles.add(passport.getAttachment());
		passport = null;
		driver.setPassport(null);
	}
	
	public void removeLicense() {		
		if(license.getAttachment() != null && license.getAttachment().getId() != null) removedFiles.add(license.getAttachment());
		license = null;
		driver.setLicense(null);
	}
    
    public void assertRemovedFiles() {
		if(removedFiles.isEmpty()) return;
		
		for (Attachment attachment : removedFiles) {
			atService.remove(attachment);
		}
		
		removedFiles.clear();
	}
	
    public void handleFileUploadLicense(FileUploadEvent event) throws IOException { 
    	
    	String fileName = Translit.translit(event.getFile().getFileName());
    	license = createFileBinary(event.getFile());
    	
        FacesMessage msg = new FacesMessage(Messages.getMessage("fileSuccessfullyUploaded").replaceAll("\\{0\\}", fileName));  
        FacesContext.getCurrentInstance().addMessage(null, msg);  
    }  
    
    public void handleFileUploadPassport(FileUploadEvent event) throws IOException { 
    	
    	String fileName = Translit.translit(event.getFile().getFileName());
    	passport = createFileBinary(event.getFile());
    	
        FacesMessage msg = new FacesMessage(Messages.getMessage("fileSuccessfullyUploaded").replaceAll("\\{0\\}", fileName));  
        FacesContext.getCurrentInstance().addMessage(null, msg);  
    } 
    
    private AttachmentBinaryDTO createFileBinary(UploadedFile file) throws IOException {
    	AttachmentBinaryDTO binary = new AttachmentBinaryDTO();
		binary.setName(Translit.translit(file.getFileName()));
		binary.setMimeType(file.getContentType());
		binary.setBody(IOUtils.toByteArray(file.getInputstream()));
		
		return binary;
	}
    
    private Attachment createAttachment(AttachmentBinaryDTO binary) {
		if(binary.getAttachment() != null && binary.getAttachment().getId() != null) return binary.getAttachment();
		Attachment attachment = new Attachment();
		attachment.setFileName(binary.getName());
		attachment.setLocked(false);
		attachment.setPublicInfo(true);
		attachment.setData(binary.getBody());
		return attachment;
	}

	public Driver getDriver() {
		return driver;
	}
	
	public void setDriver(Driver driver) {
		this.driver = driver;
	}

	public ConversationDriver getConversation() {
		return conversation;
	}
	
	public void setConversation(ConversationDriver conversation) {
		this.conversation = conversation;
	}

	public AttachmentBinaryDTO getPassport() {
		return passport;
	}

	public void setPassport(AttachmentBinaryDTO passport) {
		this.passport = passport;
	}

	public AttachmentBinaryDTO getLicense() {
		return license;
	}

	public void setLicense(AttachmentBinaryDTO license) {
		this.license = license;
	}
}
