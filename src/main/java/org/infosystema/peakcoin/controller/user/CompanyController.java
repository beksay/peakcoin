package org.infosystema.peakcoin.controller.user;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.ConversationScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.commons.io.IOUtils;
import org.infosystema.peakcoin.annotation.Logged;
import org.infosystema.peakcoin.beans.FilterExample;
import org.infosystema.peakcoin.controller.base.Conversational;
import org.infosystema.peakcoin.domain.Attachment;
import org.infosystema.peakcoin.domain.Company;
import org.infosystema.peakcoin.domain.Country;
import org.infosystema.peakcoin.domain.Dictionary;
import org.infosystema.peakcoin.domain.Person;
import org.infosystema.peakcoin.domain.User;
import org.infosystema.peakcoin.dto.AttachmentBinaryDTO;
import org.infosystema.peakcoin.enums.CompanyStatus;
import org.infosystema.peakcoin.enums.DocumentType;
import org.infosystema.peakcoin.enums.FormType;
import org.infosystema.peakcoin.service.AttachmentService;
import org.infosystema.peakcoin.service.CompanyService;
import org.infosystema.peakcoin.service.CountryService;
import org.infosystema.peakcoin.service.PersonService;
import org.infosystema.peakcoin.util.Translit;
import org.infosystema.peakcoin.util.Util;
import org.infosystema.peakcoin.util.web.LoginUtil;
import org.infosystema.peakcoin.util.web.Messages;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

/**
 * 
 * @author Kuttubek Aidaraliev
 *
 */

@Logged
@Named
@ConversationScoped
public class CompanyController extends Conversational {

	private static final long serialVersionUID = 5651758429305872940L;
	
	@EJB
	private PersonService service;
	@EJB
	private CompanyService companyService;
	@EJB
	private CountryService countryService;
	@EJB
	private PersonService personService;
	@EJB
	AttachmentService atService;
	@Inject
	private LoginUtil loginUtil;
	
	private Company company;
	private Boolean editProfile;
	private AttachmentBinaryDTO image;
	private AttachmentBinaryDTO passport;
	private AttachmentBinaryDTO document;
	private Set<Dictionary> events;
	private List<Attachment> removedFiles = new ArrayList<Attachment>();

	@PostConstruct
	public void init() {
		if (company==null) company= new Company();
		editProfile = false;
	}
	
	public String add() {
		company = new Company();
		image = null;
		document = null;
		passport = null;
		return "/view/profile/company/company_form.xhtml";
	}
	
	public String addAgain() {
		return "/view/profile/company/company_form.xhtml";
	}
	
	public String change() {
		editProfile = true;
		return null;
	}
	
	public String startWork(Company company) {
		company.setStatus(CompanyStatus.ACTIVE);
    	companyService.merge(company);
    	editProfile = false;
		return companyList();
	}
	
	public String cancel() {
		editProfile=false;
		return null;
	}
	
	public String save() {
		if (company.getRequerement()==false) {
			FacesContext.getCurrentInstance().addMessage("form", new FacesMessage( FacesMessage.SEVERITY_ERROR,  Messages.getMessage("requerements"), null) );
			return null;
		}
		
		List<Company> companies = companyService.findByProperty("pin", company.getPin());
    	if(companies.size()>0){
    		FacesContext.getCurrentInstance().addMessage("form", new FacesMessage( FacesMessage.SEVERITY_ERROR,  Messages.getMessage("pinIsAlreadyExists"), null) );
			return null;
    	}
    	if(image != null) {
    		Attachment attachment = new Attachment();
			attachment = createAttachment(image);
			image.setAttachment(attachment);
			try {
				attachment = image.getAttachment().getId() == null ? atService.saveFromDTO(image) : image.getAttachment();
				company.setPicture(attachment);
				attachment = new Attachment();
			} catch (IOException e) {e.printStackTrace();}			
		}
    	
    	if(document != null) {
    		Attachment attachment = new Attachment();
			attachment = createAttachment(document);
			document.setAttachment(attachment);
			try {
				attachment = document.getAttachment().getId() == null ? atService.saveFromDTO(document) : document.getAttachment();
				company.setDocument(attachment);
				attachment = new Attachment();
			} catch (IOException e) {e.printStackTrace();}			
		}else {
			FacesContext.getCurrentInstance().addMessage("form", new FacesMessage( FacesMessage.SEVERITY_ERROR,  Messages.getMessage("documentIsRequired"), null) );
			return null;
		}
    	
    	if(passport != null) {
    		Attachment attachment = new Attachment();
			attachment = createAttachment(passport);
			passport.setAttachment(attachment);
			try {
				attachment = passport.getAttachment().getId() == null ? atService.saveFromDTO(passport) : passport.getAttachment();
				company.setPassport(attachment);
				attachment = new Attachment();
			} catch (IOException e) {e.printStackTrace();}			
		}else {
			FacesContext.getCurrentInstance().addMessage("form", new FacesMessage( FacesMessage.SEVERITY_ERROR,  Messages.getMessage("passportIsRequired"), null) );
			return null;
		}
    	company.setEvents(events);
		company.setPeakcoin(new BigDecimal(0));
		company.setPerson(loginUtil.getCurrentUser().getPerson());
		
		if(!FacesContext.getCurrentInstance().getMessageList().isEmpty()) return null;
        if(company.getId() == null ) {
        	company.setStatus(CompanyStatus.IN_PROGRES);
        	companyService.persist(company);
        }else {
        	company.setStatus(CompanyStatus.RE_PROGRES);
        	companyService.merge(company);
		}
        
        Person person = personService.findById(company.getPerson().getId(), false);
    	person.setHasCompany(false);
    	personService.merge(person);

		FacesContext.getCurrentInstance().addMessage("form", new FacesMessage( FacesMessage.SEVERITY_INFO,  Messages.getMessage("dataDaved"), null) );
		editProfile = false;
		return companyInProgres();
	}
	
	public String saveProfile() {
		List<Company> companies = companyService.findByProperty("pin", company.getPin());
    	if(companies.equals(0)){
    		FacesContext.getCurrentInstance().addMessage("form", new FacesMessage( FacesMessage.SEVERITY_ERROR,  Messages.getMessage("pinIsAlreadyExists"), null) );
			return null;
    	}
    	
    	if(image != null) {
    		Attachment attachment = new Attachment();
			attachment = createAttachment(image);
			image.setAttachment(attachment);
			try {
				attachment = image.getAttachment().getId() == null ? atService.saveFromDTO(image) : image.getAttachment();
				company.setPicture(attachment);
				attachment = new Attachment();
			} catch (IOException e) {e.printStackTrace();}			
		}

		if(!FacesContext.getCurrentInstance().getMessageList().isEmpty()) return null;
		companyService.merge(company);
		
		FacesContext.getCurrentInstance().addMessage("form", new FacesMessage( FacesMessage.SEVERITY_INFO,  Messages.getMessage("dataDaved"), null) );
		editProfile = false;
		return null;
	}
	
	public String goCompany(User user) {
	    if (user.getPerson().getHasCompany() ==true) {
	    	 if (company.getStatus().equals(CompanyStatus.ACTIVE)) {
	    		 try {
    				if (company.getDocument() != null)
    					document = Util.createAttachmentDTO(company.getDocument());
    				else
    					document = null;
    			} catch (Exception e) {
    				document = null;
    			}
	    		 try {
	    				if (company.getPassport() != null)
	    					passport = Util.createAttachmentDTO(company.getPassport());
	    				else
	    					passport = null;
	    			} catch (Exception e) {
	    				passport = null;
	    			}
	    		 try {
	    				if (company.getPicture() != null)
	    					image = Util.createAttachmentDTO(company.getPicture());
	    				else
	    					image = null;
	    			} catch (Exception e) {
	    				image = null;
	    			}
	    	 }
	       }
	   return companyList();
	}
	
	public String activateCompany(Company company) {
		company.setStatus(CompanyStatus.ACTIVE);
    	companyService.merge(company);
    	
    	Person person = personService.findById(company.getPerson().getId(), false);
    	person.setHasCompany(true);
    	personService.merge(person);
    	FacesContext.getCurrentInstance().addMessage("form", new FacesMessage( FacesMessage.SEVERITY_INFO,  Messages.getMessage("statusActivated"), null) );
		return companyJournal();
	}
	
	public List<Country> getCountryList() {
		List<FilterExample> examples = new ArrayList<>();
		return countryService.findByExample(0, 10, examples);
	}
	
	public List<FormType> getFormTypeList() {
		return Arrays.asList(FormType.values());
	}
	
	public List<DocumentType> getDocumentTypeList() {
		return Arrays.asList(DocumentType.values());
	}
	
	private String companyJournal() {
		return "/view/profile/company/company_journal.xhtml";
	}
	
	private String companyList() {
		editProfile = false;
		return "/view/profile/company/company_list.xhtml";
	}
	
	private String companyInProgres() {
		return "/view/profile/company/company_in_progres.xhtml";
	}
	
	private String companyReq() {
		return "/view/profile/company/company_req.xhtml";
	}
	
	public String back() {
			company = new Company();
			return companyReq();
	}
	
	public void removeImage() {		
		if(image.getAttachment() != null && image.getAttachment().getId() != null) removedFiles.add(image.getAttachment());
		image = null;
		company.setPicture(null);
	}
	
	public void removeDocument() {		
		if(document.getAttachment() != null && document.getAttachment().getId() != null) removedFiles.add(document.getAttachment());
		document = null;
		company.setDocument(null);
	}
	
	public void removePassport() {		
		if(passport.getAttachment() != null && passport.getAttachment().getId() != null) removedFiles.add(passport.getAttachment());
		passport = null;
		company.setPassport(null);
	}
    
    public void assertRemovedFiles() {
		if(removedFiles.isEmpty()) return;
		
		for (Attachment attachment : removedFiles) {
			atService.remove(attachment);
		}
		
		removedFiles.clear();
	}
	
	// Загрузчик для рисунка
    public void handleFileUploadImage(FileUploadEvent event) throws IOException { 
    	
    	String fileName = Translit.translit(event.getFile().getFileName());
    	image = createFileBinary(event.getFile());
    	
        FacesMessage msg = new FacesMessage(Messages.getMessage("fileSuccessfullyUploaded").replaceAll("\\{0\\}", fileName));  
        FacesContext.getCurrentInstance().addMessage(null, msg);  
    }  
    
	// Загрузчик для рисунка
    public void handleFileUploadPassport(FileUploadEvent event) throws IOException { 
    	
    	String fileName = Translit.translit(event.getFile().getFileName());
    	passport = createFileBinary(event.getFile());
    	
        FacesMessage msg = new FacesMessage(Messages.getMessage("fileSuccessfullyUploaded").replaceAll("\\{0\\}", fileName));  
        FacesContext.getCurrentInstance().addMessage(null, msg);  
    } 
    
    // Загрузчик для рисунка
    public void handleFileUploadDocument(FileUploadEvent event) throws IOException { 
    	
    	String fileName = Translit.translit(event.getFile().getFileName());
    	document = createFileBinary(event.getFile());
    	
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

	public Boolean getEditProfile() {
		return editProfile;
	}
	
	public void setEditProfile(Boolean editProfile) {
		this.editProfile = editProfile;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}
	
	public AttachmentBinaryDTO getImage() {
		return image;
	}
	
	public void setImage(AttachmentBinaryDTO image) {
		this.image = image;
	}

	public AttachmentBinaryDTO getPassport() {
		return passport;
	}

	public void setPassport(AttachmentBinaryDTO passport) {
		this.passport = passport;
	}

	public AttachmentBinaryDTO getDocument() {
		return document;
	}

	public void setDocument(AttachmentBinaryDTO document) {
		this.document = document;
	}

	public Set<Dictionary> getEvents() {
		return events;
	}

	public void setEvents(Set<Dictionary> events) {
		this.events = events;
	}
	
}

