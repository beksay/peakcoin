package org.infosystema.peakcoin.controller.user;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
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
import org.infosystema.peakcoin.beans.InequalityConstants;
import org.infosystema.peakcoin.beans.SortEnum;
import org.infosystema.peakcoin.controller.base.Conversational;
import org.infosystema.peakcoin.domain.Attachment;
import org.infosystema.peakcoin.domain.City;
import org.infosystema.peakcoin.domain.Country;
import org.infosystema.peakcoin.domain.Person;
import org.infosystema.peakcoin.domain.User;
import org.infosystema.peakcoin.dto.AttachmentBinaryDTO;
import org.infosystema.peakcoin.enums.CarparkStatus;
import org.infosystema.peakcoin.enums.CompanyStatus;
import org.infosystema.peakcoin.enums.CookStatus;
import org.infosystema.peakcoin.enums.GuideStatus;
import org.infosystema.peakcoin.service.AttachmentService;
import org.infosystema.peakcoin.service.CityService;
import org.infosystema.peakcoin.service.CountryService;
import org.infosystema.peakcoin.service.PersonService;
import org.infosystema.peakcoin.service.RoleService;
import org.infosystema.peakcoin.service.UserService;
import org.infosystema.peakcoin.util.Translit;
import org.infosystema.peakcoin.util.Util;
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
public class ProfileController extends Conversational {

	private static final long serialVersionUID = 5651758429305872940L;
	
	@EJB
	private UserService service;
	@EJB
	private RoleService roleService;
	@EJB
	private PersonService personService;
	@EJB
	AttachmentService atService;
	@EJB
	private CountryService countryService;
	@EJB
	private CityService cityService;
	
	private User user;
	private Person person;
	private Boolean editProfile;
	
	private AttachmentBinaryDTO image;
	private List<Attachment> removedFiles = new ArrayList<Attachment>();

	@PostConstruct
	public void init() {
		if (user==null)	user= new User();
		if (person==null) person= new Person();
		editProfile = false;
	}
	
	public String change() {
		editProfile = true;
		return null;
	}
	
	public String cancel() {
		editProfile=false;
		return null;
	}
	
	public String save() {
		List<User> users = service.findByProperty("email", user.getEmail());
    	if(users.equals(0)){
    		FacesContext.getCurrentInstance().addMessage("form", new FacesMessage( FacesMessage.SEVERITY_ERROR,  Messages.getMessage("emailIsAlreadyExists"), null) );
			return null;
    	}
    	if(image != null) {
    		Attachment attachment = new Attachment();
			attachment = createAttachment(image);
			image.setAttachment(attachment);
			try {
				attachment = image.getAttachment().getId() == null ? atService.saveFromDTO(image) : image.getAttachment();
				person.setPicture(attachment);
				attachment = new Attachment();
			} catch (IOException e) {e.printStackTrace();}			
		}
		
		user.setPerson(person);
		if(!FacesContext.getCurrentInstance().getMessageList().isEmpty()) return null;
		personService.merge(user.getPerson());
		service.merge(user);
		
		FacesContext.getCurrentInstance().addMessage("form", new FacesMessage( FacesMessage.SEVERITY_INFO,  Messages.getMessage("dataDaved"), null) );
		editProfile = false;
		return null;
	}
	
	public String goProfile(User user) {
		this.user = user;
		person = personService.findById(user.getPerson().getId(), false);
		return profileList();
	}
	
	public String goCompany(User user) {
		List<FilterExample> userFilter = new ArrayList<>();
		userFilter.add(new FilterExample("id", user.getId(), InequalityConstants.EQUAL));
	    userFilter.add(new FilterExample("roles", new HashSet<>(roleService.findByProperty("name", "company")), InequalityConstants.MEMBER_OF));
	    
	    List<User> users = service.findByExample(0, 10, SortEnum.ASCENDING, userFilter, "id", new String[]{"roles"});
	    if (users.size()>0) {
	    	 try {
 				if (person.getPicture() != null)
 					image = Util.createAttachmentDTO(person.getPicture());
 				else
 					image = null;
 			} catch (Exception e) {
 				image = null;
 			}
	    	return companyList();
		} else {
			return companyReq();
		}
		
	}
	
	public List<Country> getCountryList() {
		List<FilterExample> examples = new ArrayList<>();
		return countryService.findByExample(0, 10, examples);
	}
	
	public List<City> getCityList() {
		List<FilterExample> examples = new ArrayList<>();
		if (person.getCountry() !=null) {
			examples.add(new FilterExample("country", person.getCountry(), InequalityConstants.EQUAL));
		}else {
			examples.add(new FilterExample("id", -1, InequalityConstants.EQUAL));
		}
		return cityService.findByExample(0, 10, examples);
	}
	
	public Boolean isCompanyActivated(User user) {
		List<FilterExample> userFilter = new ArrayList<>();
		userFilter.add(new FilterExample("id", user.getPerson().getId(), InequalityConstants.EQUAL));
		userFilter.add(new FilterExample("company.status", CompanyStatus.ACTIVE, InequalityConstants.EQUAL));
		Long total = personService.countByExample(userFilter);
	    if (total>0) {
	        return true;
	    } else {
			return false;
		}	
	}
	
	public Boolean isCarparkActivated(User user) {
		List<FilterExample> userFilter = new ArrayList<>();
		userFilter.add(new FilterExample("id", user.getPerson().getId(), InequalityConstants.EQUAL));
		userFilter.add(new FilterExample("carpark.status", CarparkStatus.ACTIVE, InequalityConstants.EQUAL));
		Long total = personService.countByExample(userFilter);
	    if (total>0) {
	        return true;
	    } else {
			return false;
		}	
	}
	
	public Boolean isGuideActivated(User user) {
		List<FilterExample> userFilter = new ArrayList<>();
		userFilter.add(new FilterExample("id", user.getPerson().getId(), InequalityConstants.EQUAL));
		userFilter.add(new FilterExample("guide.status", GuideStatus.ACTIVE, InequalityConstants.EQUAL));
		Long total = personService.countByExample(userFilter);
	    if (total>0) {
	        return true;
	    } else {
			return false;
		}	
	}
	
	public Boolean isCookActivated(User user) {
		List<FilterExample> userFilter = new ArrayList<>();
		userFilter.add(new FilterExample("id", user.getPerson().getId(), InequalityConstants.EQUAL));
		userFilter.add(new FilterExample("cook.status", CookStatus.ACTIVE, InequalityConstants.EQUAL));
		Long total = personService.countByExample(userFilter);
	    if (total>0) {
	        return true;
	    } else {
			return false;
		}	
	}
	
	private String profileList() {
		return "/view/profile/profile_list.xhtml";
	}
	
	private String companyList() {
		return "/view/company/company_list.xhtml";
	}
	
	private String companyReq() {
		return "/view/company/company_req.xhtml";
	}
	
	public String mainForm() {
		return "/view/main.xhtml";
	}
	
	public void removeImage() {		
		if(image.getAttachment() != null && image.getAttachment().getId() != null) removedFiles.add(image.getAttachment());
		image = null;
		person.setPicture(null);
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
    
    public AttachmentBinaryDTO getImage() {
		return image;
	}
    
    public void setImage(AttachmentBinaryDTO image) {
		this.image = image;
	}
	
	public User getUser() {
		return user;
	}
	
	public void setUser(User user) {
		this.user = user;
	}
	
	public Boolean getEditProfile() {
		return editProfile;
	}
	
	public void setEditProfile(Boolean editProfile) {
		this.editProfile = editProfile;
	}
	public Person getPerson() {
		return person;
	}
	public void setPerson(Person person) {
		this.person = person;
	}
	
}

