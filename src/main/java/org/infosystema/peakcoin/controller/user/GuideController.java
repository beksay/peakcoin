package org.infosystema.peakcoin.controller.user;

import java.io.IOException;
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

import org.apache.commons.io.IOUtils;
import org.infosystema.peakcoin.annotation.Logged;
import org.infosystema.peakcoin.beans.FilterExample;
import org.infosystema.peakcoin.beans.InequalityConstants;
import org.infosystema.peakcoin.controller.base.Conversational;
import org.infosystema.peakcoin.domain.Attachment;
import org.infosystema.peakcoin.domain.Dictionary;
import org.infosystema.peakcoin.domain.Guide;
import org.infosystema.peakcoin.domain.Person;
import org.infosystema.peakcoin.domain.User;
import org.infosystema.peakcoin.dto.AttachmentBinaryDTO;
import org.infosystema.peakcoin.enums.GuideStatus;
import org.infosystema.peakcoin.service.AttachmentService;
import org.infosystema.peakcoin.service.DictionaryService;
import org.infosystema.peakcoin.service.GuideService;
import org.infosystema.peakcoin.service.PersonService;
import org.infosystema.peakcoin.util.Translit;
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
public class GuideController extends Conversational {

	private static final long serialVersionUID = 5651758429305872940L;
	
	@EJB
	private PersonService service;
	@EJB
	private GuideService guideService;
	@EJB
	private DictionaryService dictService;
	@EJB
	AttachmentService atService;
	@Inject
	private LoginUtil loginUtil;

	private Guide guide;
	private Boolean editProfile;
	private Set<Dictionary> instructorActivities;
	
	private AttachmentBinaryDTO translatorDoc;
	private AttachmentBinaryDTO instructorDoc;
	private AttachmentBinaryDTO mountainDoc;
	private AttachmentBinaryDTO cityDoc;
	private AttachmentBinaryDTO document;
	private List<Attachment> removedFiles = new ArrayList<Attachment>();

	@PostConstruct
	public void init() {
		if (guide==null) guide= new Guide();
		if(instructorActivities==null) instructorActivities = new HashSet<>();
		editProfile = false;
	}
	
	public String add() {
		guide = new Guide();
		instructorActivities = new HashSet<>();
		translatorDoc=null;
		instructorDoc=null;
		mountainDoc=null;
		cityDoc=null;
		document=null;
		return "/view/profile/guide/guide_form.xhtml";
	}
	
	public String addAgain() {
		return "/view/profile/guide/guide_form.xhtml";
	}
	
	public String change() {
		editProfile = true;
		return null;
	}
	
	public String startWork(Guide guide) {
		guide.setStatus(GuideStatus.ACTIVE);
		guideService.merge(guide);
    	editProfile = false;
		return guideList();
	}
	
	public String cancel() {
		editProfile=false;
		return null;
	}
	
	public String save() {
		if (guide.getRequerement()==false) {
			FacesContext.getCurrentInstance().addMessage("form", new FacesMessage( FacesMessage.SEVERITY_ERROR,  Messages.getMessage("requerements"), null) );
			return null;
		}
		
		if(translatorDoc != null && guide.getTranslator()==true) {
    		Attachment attachment = new Attachment();
			attachment = createAttachment(translatorDoc);
			translatorDoc.setAttachment(attachment);
			try {
				attachment = translatorDoc.getAttachment().getId() == null ? atService.saveFromDTO(translatorDoc) : translatorDoc.getAttachment();
				guide.setTranslatorDoc(attachment);
				attachment = new Attachment();
			} catch (IOException e) {e.printStackTrace();}			
		}else if(translatorDoc == null && guide.getTranslator()==true){
			FacesContext.getCurrentInstance().addMessage("form", new FacesMessage( FacesMessage.SEVERITY_ERROR,  Messages.getMessage("translatorDocIsRequired"), null) );
			return null;
		}
		if(instructorDoc != null && guide.getInstructor()==true) {
    		Attachment attachment = new Attachment();
			attachment = createAttachment(instructorDoc);
			instructorDoc.setAttachment(attachment);
			try {
				attachment = instructorDoc.getAttachment().getId() == null ? atService.saveFromDTO(instructorDoc) : instructorDoc.getAttachment();
				guide.setInstructorDoc(attachment);
				attachment = new Attachment();
			} catch (IOException e) {e.printStackTrace();}			
		}else if(instructorDoc == null && guide.getInstructor()==true){
			FacesContext.getCurrentInstance().addMessage("form", new FacesMessage( FacesMessage.SEVERITY_ERROR,  Messages.getMessage("instructorDocIsRequired"), null) );
			return null;
		}
		if(mountainDoc != null && guide.getMountainGuide()==true) {
    		Attachment attachment = new Attachment();
			attachment = createAttachment(mountainDoc);
			mountainDoc.setAttachment(attachment);
			try {
				attachment = mountainDoc.getAttachment().getId() == null ? atService.saveFromDTO(mountainDoc) : mountainDoc.getAttachment();
				guide.setMountainDoc(attachment);
				attachment = new Attachment();
			} catch (IOException e) {e.printStackTrace();}			
		}else if(mountainDoc == null && guide.getMountainGuide()==true){
			FacesContext.getCurrentInstance().addMessage("form", new FacesMessage( FacesMessage.SEVERITY_ERROR,  Messages.getMessage("mountainDocIsRequired"), null) );
			return null;
		}
		if(cityDoc != null && guide.getCityGuide()==true) {
    		Attachment attachment = new Attachment();
			attachment = createAttachment(cityDoc);
			cityDoc.setAttachment(attachment);
			try {
				attachment = cityDoc.getAttachment().getId() == null ? atService.saveFromDTO(cityDoc) : cityDoc.getAttachment();
				guide.setCityDoc(attachment);
				attachment = new Attachment();
			} catch (IOException e) {e.printStackTrace();}			
		}else if(cityDoc == null && guide.getCityGuide()==true){
			FacesContext.getCurrentInstance().addMessage("form", new FacesMessage( FacesMessage.SEVERITY_ERROR,  Messages.getMessage("passportIsRequired"), null) );
			return null;
		}
		if(document != null) {
    		Attachment attachment = new Attachment();
			attachment = createAttachment(document);
			document.setAttachment(attachment);
			try {
				attachment = document.getAttachment().getId() == null ? atService.saveFromDTO(document) : document.getAttachment();
				guide.setDocument(attachment);
				attachment = new Attachment();
			} catch (IOException e) {e.printStackTrace();}			
		}else {
			FacesContext.getCurrentInstance().addMessage("form", new FacesMessage( FacesMessage.SEVERITY_ERROR,  Messages.getMessage("passportIsRequired"), null) );
			return null;
		}
			
    	guide.setPerson(loginUtil.getCurrentUser().getPerson());
		guide.setInstructorActivities(instructorActivities);
		if(!FacesContext.getCurrentInstance().getMessageList().isEmpty()) return null;
        if(guide.getId() == null ) {
        	guide.setStatus(GuideStatus.IN_PROGRES);
        	guideService.persist(guide);
        }else {
        	guide.setStatus(GuideStatus.RE_PROGRES);
        	guideService.merge(guide);
		}
        
        Person person = service.findById(guide.getPerson().getId(), false);
    	person.setHasGuide(false);
    	service.merge(person);
		
		FacesContext.getCurrentInstance().addMessage("form", new FacesMessage( FacesMessage.SEVERITY_INFO,  Messages.getMessage("dataDaved"), null) );
		editProfile = false;
		return guideInProgres();
	}
	
	public String saveProfile() {

		if(!FacesContext.getCurrentInstance().getMessageList().isEmpty()) return null;
		guide.setInstructorActivities(instructorActivities);
		guideService.merge(guide);
		guide = guideService.getByIdWithFields(guide.getId(), new String[] {"instructorActivities"});
		FacesContext.getCurrentInstance().addMessage("form", new FacesMessage( FacesMessage.SEVERITY_INFO,  Messages.getMessage("dataDaved"), null) );
		editProfile = false;
		return null;
	}
	
	public String goGuide(User user) {
	    if (guide.getId() !=null) {
	    	guide = guideService.getByIdWithFields(guide.getId(), new String[] {"instructorActivities"});
	    	instructorActivities = new HashSet<>();
	    	instructorActivities.addAll(guide.getInstructorActivities());
	     }
	    return guideList();
	 		
	}
	
	public List<Dictionary> getInstructorActivitiesList() {
		List<FilterExample> examples = new ArrayList<>();
		examples.add(new FilterExample("dictionaryType.id", 6, InequalityConstants.EQUAL));
		return dictService.findByExample(0, 10, examples);
	}
	
	public String activate(Guide guide) {
		guide.setStatus(GuideStatus.ACTIVE);
		guideService.merge(guide);
		
		Person person = service.findById(guide.getPerson().getId(), false);
    	person.setHasCompany(true);
    	service.merge(person);
    	FacesContext.getCurrentInstance().addMessage("form", new FacesMessage( FacesMessage.SEVERITY_INFO,  Messages.getMessage("statusActivated"), null) );
		return guideJournal();
	}
	
	private String guideList() {
		editProfile = false;
		return "/view/profile/guide/guide_list.xhtml";
	}
	
	private String guideJournal() {
		return "/view/profile/guide/guide_journal.xhtml";
	}
	
	private String guideReq() {
		return "/view/profile/guide/guide_req.xhtml";
	}
	
	private String guideInProgres() {
		return "/view/profile/guide/guide_in_progres.xhtml";
	}
	
	public String back() {
		guide = new Guide();
		return guideReq();
	}
	
	public Boolean getEditProfile() {
		return editProfile;
	}
	
	public void setEditProfile(Boolean editProfile) {
		this.editProfile = editProfile;
	}

	public Guide getGuide() {
		return guide;
	}
	
	public void setGuide(Guide guide) {
		this.guide = guide;
	}

	public Set<Dictionary> getInstructorActivities() {
		return instructorActivities;
	}
	
	public void setInstructorActivities(Set<Dictionary> instructorActivities) {
		this.instructorActivities = instructorActivities;
	}

	public AttachmentBinaryDTO getTranslatorDoc() {
		return translatorDoc;
	}

	public void setTranslatorDoc(AttachmentBinaryDTO translatorDoc) {
		this.translatorDoc = translatorDoc;
	}

	public AttachmentBinaryDTO getInstructorDoc() {
		return instructorDoc;
	}

	public void setInstructorDoc(AttachmentBinaryDTO instructorDoc) {
		this.instructorDoc = instructorDoc;
	}

	public AttachmentBinaryDTO getMountainDoc() {
		return mountainDoc;
	}

	public void setMountainDoc(AttachmentBinaryDTO mountainDoc) {
		this.mountainDoc = mountainDoc;
	}

	public AttachmentBinaryDTO getCityDoc() {
		return cityDoc;
	}

	public void setCityDoc(AttachmentBinaryDTO cityDoc) {
		this.cityDoc = cityDoc;
	}

	public AttachmentBinaryDTO getDocument() {
		return document;
	}

	public void setDocument(AttachmentBinaryDTO document) {
		this.document = document;
	}

	public List<Attachment> getRemovedFiles() {
		return removedFiles;
	}

	public void setRemovedFiles(List<Attachment> removedFiles) {
		this.removedFiles = removedFiles;
	}
	
	public void removeTranslatorDoc() {		
		if(translatorDoc.getAttachment() != null && translatorDoc.getAttachment().getId() != null) removedFiles.add(translatorDoc.getAttachment());
		translatorDoc = null;
		guide.setTranslatorDoc(null);
	}
	
	public void removeInstructorDoc() {		
		if(instructorDoc.getAttachment() != null && instructorDoc.getAttachment().getId() != null) removedFiles.add(instructorDoc.getAttachment());
		instructorDoc = null;
		guide.setInstructorDoc(null);
	}
	
	public void removeMountainDoc() {		
		if(mountainDoc.getAttachment() != null && mountainDoc.getAttachment().getId() != null) removedFiles.add(mountainDoc.getAttachment());
		mountainDoc = null;
		guide.setMountainDoc(null);
	}
	
	public void removeCityDoc() {		
		if(cityDoc.getAttachment() != null && cityDoc.getAttachment().getId() != null) removedFiles.add(cityDoc.getAttachment());
		cityDoc = null;
		guide.setCityDoc(null);
	}
	
	public void removeDocument() {		
		if(document.getAttachment() != null && document.getAttachment().getId() != null) removedFiles.add(document.getAttachment());
		document = null;
		guide.setDocument(null);
	}
    
    public void assertRemovedFiles() {
		if(removedFiles.isEmpty()) return;
		for (Attachment attachment : removedFiles) {
			atService.remove(attachment);
		}
		removedFiles.clear();
	}
    
    public void uploadTranslatorDoc(FileUploadEvent event) throws IOException { 
    	String fileName = Translit.translit(event.getFile().getFileName());
    	translatorDoc = createFileBinary(event.getFile());
        FacesMessage msg = new FacesMessage(Messages.getMessage("fileSuccessfullyUploaded").replaceAll("\\{0\\}", fileName));  
        FacesContext.getCurrentInstance().addMessage(null, msg);  
    }  
    
    public void uploadInstructorDoc(FileUploadEvent event) throws IOException { 
    	String fileName = Translit.translit(event.getFile().getFileName());
    	instructorDoc = createFileBinary(event.getFile());
        FacesMessage msg = new FacesMessage(Messages.getMessage("fileSuccessfullyUploaded").replaceAll("\\{0\\}", fileName));  
        FacesContext.getCurrentInstance().addMessage(null, msg);  
    } 
    
    public void uploadMountainDoc(FileUploadEvent event) throws IOException { 
    	String fileName = Translit.translit(event.getFile().getFileName());
    	mountainDoc = createFileBinary(event.getFile());
        FacesMessage msg = new FacesMessage(Messages.getMessage("fileSuccessfullyUploaded").replaceAll("\\{0\\}", fileName));  
        FacesContext.getCurrentInstance().addMessage(null, msg);  
    } 
    
    public void uploadCityDoc(FileUploadEvent event) throws IOException { 
    	String fileName = Translit.translit(event.getFile().getFileName());
    	cityDoc = createFileBinary(event.getFile());
        FacesMessage msg = new FacesMessage(Messages.getMessage("fileSuccessfullyUploaded").replaceAll("\\{0\\}", fileName));  
        FacesContext.getCurrentInstance().addMessage(null, msg);  
    } 
    
    public void uploadDocument(FileUploadEvent event) throws IOException { 
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
	
}

