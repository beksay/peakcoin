package org.infosystema.peakcoin.controller.user;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.ConversationScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;

import org.apache.commons.io.IOUtils;
import org.infosystema.peakcoin.annotation.Logged;
import org.infosystema.peakcoin.beans.FilterExample;
import org.infosystema.peakcoin.controller.base.Conversational;
import org.infosystema.peakcoin.domain.Attachment;
import org.infosystema.peakcoin.domain.Carpark;
import org.infosystema.peakcoin.domain.Country;
import org.infosystema.peakcoin.domain.Person;
import org.infosystema.peakcoin.domain.User;
import org.infosystema.peakcoin.dto.AttachmentBinaryDTO;
import org.infosystema.peakcoin.enums.CarparkStatus;
import org.infosystema.peakcoin.enums.DocumentType;
import org.infosystema.peakcoin.enums.FormType;
import org.infosystema.peakcoin.service.AttachmentService;
import org.infosystema.peakcoin.service.CarparkService;
import org.infosystema.peakcoin.service.CountryService;
import org.infosystema.peakcoin.service.PersonService;
import org.infosystema.peakcoin.util.Translit;
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
public class CarparkController extends Conversational {

	private static final long serialVersionUID = 5651758429305872940L;
	
	@EJB
	private PersonService service;
	@EJB
	private CarparkService carparkService;
	@EJB
	private CountryService countryService;
	@EJB
	AttachmentService atService;
	
	private Person person;
	private Carpark carpark;
	private Boolean editProfile;
	private AttachmentBinaryDTO document;
	private List<Attachment> removedFiles = new ArrayList<Attachment>();

	@PostConstruct
	public void init() {
		if (person==null) person= new Person();
		if (carpark==null) carpark= new Carpark();
		editProfile = false;
	}
	
	public String add() {
		carpark = new Carpark();
		document = null;
		return "/view/profile/carpark/carpark_form.xhtml";
	}
	
	public String addAgain() {
		return "/view/profile/carpark/carpark_form.xhtml";
	}
	
	public String change() {
		editProfile = true;
		return null;
	}
	
	public String startWork(Carpark carpark) {
		carpark.setStatus(CarparkStatus.ACTIVE);
		carparkService.merge(carpark);
    	editProfile = false;
		return carparkList();
	}
	
	public String cancel() {
		editProfile=false;
		return null;
	}
	
	public String save() {
		List<Carpark> cars = carparkService.findByProperty("pin", carpark.getPin());
    	if(cars.equals(0)){
    		FacesContext.getCurrentInstance().addMessage("form", new FacesMessage( FacesMessage.SEVERITY_ERROR,  Messages.getMessage("pinIsAlreadyExists"), null) );
			return null;
    	}
    	
    	if(document != null) {
    		Attachment attachment = new Attachment();
			attachment = createAttachment(document);
			document.setAttachment(attachment);
			try {
				attachment = document.getAttachment().getId() == null ? atService.saveFromDTO(document) : document.getAttachment();
				carpark.setDocument(attachment);
				attachment = new Attachment();
			} catch (IOException e) {e.printStackTrace();}			
		}else {
			FacesContext.getCurrentInstance().addMessage("form", new FacesMessage( FacesMessage.SEVERITY_ERROR,  Messages.getMessage("documentIsRequired"), null) );
			return null;
		}
    	
    	carpark.setPeakcoin(new BigDecimal(0));
		person.setCarpark(carpark);
		
		if(!FacesContext.getCurrentInstance().getMessageList().isEmpty()) return null;
        if(person.getCarpark().getId() == null ) {
        	carpark.setStatus(CarparkStatus.IN_PROGRES);
        	carparkService.persist(person.getCarpark());
        }else {
        	carpark.setStatus(CarparkStatus.RE_PROGRES);
        	carparkService.merge(person.getCarpark());
		}
		
		service.merge(person);
		
		FacesContext.getCurrentInstance().addMessage("form", new FacesMessage( FacesMessage.SEVERITY_INFO,  Messages.getMessage("dataDaved"), null) );
		editProfile = false;
		return carparkInProgres();
	}
	
	public String saveProfile() {
		List<Carpark> cars = carparkService.findByProperty("pin", carpark.getPin());
    	if(cars.equals(0)){
    		FacesContext.getCurrentInstance().addMessage("form", new FacesMessage( FacesMessage.SEVERITY_ERROR,  Messages.getMessage("pinIsAlreadyExists"), null) );
			return null;
    	}
		if(!FacesContext.getCurrentInstance().getMessageList().isEmpty()) return null;
		carparkService.merge(carpark);
		
		FacesContext.getCurrentInstance().addMessage("form", new FacesMessage( FacesMessage.SEVERITY_INFO,  Messages.getMessage("dataDaved"), null) );
		editProfile = false;
		return null;
	}
	
	public String goCarpark(User user) {
	    if (user.getPerson().getCarpark().getId() !=null) {
	    	carpark = carparkService.findById(user.getPerson().getCarpark().getId(), false);
	    }
	  return carparkList();
	}
	
	public String activate(Carpark carpark) {
		carpark.setStatus(CarparkStatus.ACTIVE);
		carparkService.merge(carpark);
		
    	FacesContext.getCurrentInstance().addMessage("form", new FacesMessage( FacesMessage.SEVERITY_INFO,  Messages.getMessage("statusActivated"), null) );
		return carparkJournal();
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
	
	private String carparkList() {
		editProfile = false;
		return "/view/profile/carpark/carpark_list.xhtml";
	}
	
	private String carparkJournal() {
		return "/view/profile/carpark/carpark_journal.xhtml";
	}
	
	private String carparkReq() {
		return "/view/profile/carpark/carpark_req.xhtml";
	}

	private String carparkInProgres() {
		return "/view/profile/carpark/carpark_in_progres.xhtml";
	}
	
	public String back() {
		carpark = new Carpark();
		return carparkReq();	
	}
	
	public void removeDocument() {		
		if(document.getAttachment() != null && document.getAttachment().getId() != null) removedFiles.add(document.getAttachment());
		document = null;
		carpark.setDocument(null);
	}
    
    public void assertRemovedFiles() {
		if(removedFiles.isEmpty()) return;
		
		for (Attachment attachment : removedFiles) {
			atService.remove(attachment);
		}
		
		removedFiles.clear();
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

	public AttachmentBinaryDTO getDocument() {
		return document;
	}

	public void setDocument(AttachmentBinaryDTO document) {
		this.document = document;
	}

	public Carpark getCarpark() {
		return carpark;
	}

	public void setCarpark(Carpark carpark) {
		this.carpark = carpark;
	}

	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}
	
}

