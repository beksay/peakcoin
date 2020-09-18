package org.infosystema.peakcoin.controller;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.commons.io.IOUtils;
import org.infosystema.peakcoin.beans.FilterExample;
import org.infosystema.peakcoin.beans.InequalityConstants;
import org.infosystema.peakcoin.beans.SortEnum;
import org.infosystema.peakcoin.domain.Attachment;
import org.infosystema.peakcoin.domain.Company;
import org.infosystema.peakcoin.domain.Country;
import org.infosystema.peakcoin.domain.Location;
import org.infosystema.peakcoin.domain.Oblast;
import org.infosystema.peakcoin.domain.OrderGuide;
import org.infosystema.peakcoin.domain.OrderLocation;
import org.infosystema.peakcoin.domain.OrderTransport;
import org.infosystema.peakcoin.domain.OrderType;
import org.infosystema.peakcoin.domain.Orders;
import org.infosystema.peakcoin.domain.Participants;
import org.infosystema.peakcoin.domain.Person;
import org.infosystema.peakcoin.dto.AttachmentBinaryDTO;
import org.infosystema.peakcoin.enums.OrderStatus;
import org.infosystema.peakcoin.service.AttachmentService;
import org.infosystema.peakcoin.service.CompanyService;
import org.infosystema.peakcoin.service.CountryService;
import org.infosystema.peakcoin.service.LocationService;
import org.infosystema.peakcoin.service.OblastService;
import org.infosystema.peakcoin.service.OrderGuideService;
import org.infosystema.peakcoin.service.OrderLocationService;
import org.infosystema.peakcoin.service.OrderTransportService;
import org.infosystema.peakcoin.service.OrderTypeService;
import org.infosystema.peakcoin.service.OrdersService;
import org.infosystema.peakcoin.service.ParticipantsService;
import org.infosystema.peakcoin.util.Translit;
import org.infosystema.peakcoin.util.Util;
import org.infosystema.peakcoin.util.web.FacesMessages;
import org.infosystema.peakcoin.util.web.Messages;
import org.infosystema.peakcoin.validator.EntityValidator;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.UploadedFile;

@Named
@ConversationScoped
public class OrderController implements Serializable {

	private static final long serialVersionUID = 1L;
	@EJB
	private OrdersService service;
	@EJB
	private CompanyService companyService;
	@EJB
	private OrderTypeService orderTypeService;
	@EJB
	AttachmentService atService;
	@EJB
	private ParticipantsService participantsService;
	@EJB
	private CountryService countryService;
	@EJB
	private OblastService oblastService;
	@EJB
	private LocationService locationService;
	@EJB
	private OrderLocationService orderLocationService;
	@EJB
	private OrderGuideService orderGuideService;
	@EJB
	private OrderTransportService orderTransportService;
	
	@Inject
	private EntityValidator validator;

	@Inject
	private AttachmentBinaryDTO image;
	private List<Attachment> removedFiles = new ArrayList<Attachment>();
	
	private OrderLocation location;
	private List<OrderLocation> locationList;
	
	private OrderGuide guide;
	private List<OrderGuide> guideList;
	
	private OrderTransport transport;
	private List<OrderTransport> transportList;
	
	private Orders orders;
	private Country country;
	private Oblast oblast;
	private Company company;
	
	@Inject	
	private Conversation conversation;
    
	@PostConstruct
	public void initialize() {
		orders= new Orders();
		image= new AttachmentBinaryDTO();
		location= new OrderLocation();
		guide= new OrderGuide();
		transport= new OrderTransport();
		if(conversation.isTransient()) conversation.begin();
	}
	
	public void closeConversation() {
		if(!conversation.isTransient()) conversation.end();
	}
	
	public String add(Company company) {
		this.company = company;
		orders = new Orders();
		location= new OrderLocation();
		guide= new OrderGuide();
		transport= new OrderTransport();
		image = null;
		locationList = new ArrayList<>();
		guideList = new ArrayList<>();
		transportList = new ArrayList<>();
		return form();
	}
	
	public String edit(Orders orders) {
		this.orders = orders;
		List<FilterExample> examples=new ArrayList<>();
		examples.add(new FilterExample("orders", orders, InequalityConstants.EQUAL));	
		locationList = orderLocationService.findByExample(0, 20, examples);
		guideList = orderGuideService.findByExample(0, 20, examples);
		transportList = orderTransportService.findByExample(0, 20, examples);
		try {
			if (orders.getPicture() != null)
				image = Util.createAttachmentDTO(orders.getPicture());
			else
				image = null;
		} catch (Exception e) {
			image = null;
		}
		
		return form();
	}
	
	public String save() {
		System.out.println(orders);
		if(orders == null){
			FacesMessages.addMessage(Messages.getMessage("invalidData"), Messages.getMessage("invalidData"), null);
			return null;
		}
		
    	/*if(){
    		FacesContext.getCurrentInstance().addMessage("form", new FacesMessage( FacesMessage.SEVERITY_ERROR,  Messages.getMessage("uDontHavePeak"), null) );
			return null;
    	}*/
	
		if(image != null) {
    		Attachment attachment = new Attachment();
			attachment = createAttachment(image);
			image.setAttachment(attachment);
			try {
				attachment = image.getAttachment().getId() == null ? atService.saveFromDTO(image) : image.getAttachment();
				orders.setPicture(attachment);
				attachment = new Attachment();
			} catch (IOException e) {e.printStackTrace();}			
		}
		
		orders.setCompany(company);
		orders.setDateCreated(new Date());
		validator.validate(orders);
		if(!FacesContext.getCurrentInstance().getMessageList().isEmpty()) return null;

		if (orders.getId() == null) {
			orders.setStatus(OrderStatus.NEW);
			service.persist(orders);
		} else {
			service.merge(orders);
		} 
		
		for (OrderLocation location : getLocationList()) {
			location.setOrders(orders);
			if(location.getId()==null){
				location = orderLocationService.persist(location);
			}
			else{
				location = orderLocationService.merge(location);
			}
		}
		
		for (OrderGuide guide : getGuideList()) {
			guide.setOrders(orders);
			if(guide.getId()==null){
				guide = orderGuideService.persist(guide);
			}
			else{
				guide = orderGuideService.merge(guide);
			}
		}
		
		for (OrderTransport transport : getTransportList()) {
			transport.setOrders(orders);
			if(transport.getId()==null){
				transport = orderTransportService.persist(transport);
			}
			else{
				transport = orderTransportService.merge(transport);
			}
		}
	
	    return cancel();  
	}
	
	public void onRowSelect(SelectEvent event) throws IOException {
		orders=(Orders) event.getObject();
        FacesContext.getCurrentInstance().getExternalContext().redirect("/peakcoin/view/orders/order_preview.xhtml?cid="+conversation.getId());
        
    }
	
	public String delete(Orders c) {
		System.out.println(c);
		service.remove(c);
		closeConversation();
		return cancel();
	}
	
	public String cancel() {
		orders = null;
		closeConversation();
		return list();
	}
	
	private String list() {
		return "/view/orders/orders_list.xhtml?faces-redirect=true";
	}
	
	private String form() {
		return "/view/orders/orders_form.xhtml";
	}
	
	public void addLocation() {	
		System.out.println("location===" + location.getLocation());
		System.out.println("getLocationList()===" + getLocationList());
		locationList.add(location);
		location = new OrderLocation();
	}
	
	public void deleteLocation(Integer locationId) {	
		System.out.println("-----"+locationId);
		if (locationId==null) {
			return;
		}
		getLocationList().remove((int)locationId);
		
	}
	
	public void addGuide() {	
		getGuideList().add(guide);
		guide = new OrderGuide();
	}
	
	public void deleteGuide(Integer guideId) {	
		System.out.println("-----"+guideId);
		if (guideId==null) {
			return;
		}
		getGuideList().remove((int)guideId);
		
	}
	
	public void addTransport() {	
		getTransportList().add(transport);
		transport = new OrderTransport();
	}
	
	public void deleteTransport(Integer transportId) {	
		System.out.println("-----"+transportId);
		if (transportId==null) {
			return;
		}
		getTransportList().remove((int)transportId);
		
	}
	
	public List<Company> addCompanyOrder(Person person) {
		List<FilterExample> examples = new ArrayList<>();
		examples.add(new FilterExample("person", person, InequalityConstants.EQUAL));
		return companyService.findByExample(0, 10, examples);	
	}
	
	public List<Participants> getParticipantsList(Orders orders) {
		List<FilterExample> examples = new ArrayList<>();
		examples.add(new FilterExample("orders", orders, InequalityConstants.EQUAL));
		return participantsService.findByExample(0, 1000, examples);
	}
	
	public Long getParticipantsNumber(Orders orders) {
		List<FilterExample> examples = new ArrayList<>();
		examples.add(new FilterExample("orders", orders, InequalityConstants.EQUAL));
		return participantsService.countByExample(examples);
	}
	
	public List<Country> findCountry() {
		List<FilterExample> examples=new ArrayList<>();
        return countryService.findByExample(0, 20, SortEnum.ASCENDING, examples, "id");
    }
	
	public List<Oblast> findOblast() {
		List<FilterExample> examples=new ArrayList<>();
		if(country!=null) {
			examples.add(new FilterExample("country",country,InequalityConstants.EQUAL));
		}else {
			examples.add(new FilterExample("id",-1,InequalityConstants.EQUAL));
		}
        return oblastService.findByExample(0, 20, SortEnum.ASCENDING, examples, "id");
    }
	
	public List<Location> findLocation(String query) {
		List<FilterExample> examples=new ArrayList<>();
		if(oblast!=null) {
			examples.add(new FilterExample("oblast",oblast,InequalityConstants.EQUAL));
		}else {
			examples.add(new FilterExample("id",-1,InequalityConstants.EQUAL));
		}
		if (query != null && query.length()>0) {
			examples.add(new FilterExample("name","%"+query+"%",InequalityConstants.LIKE));
		}
        return locationService.findByExample(0, 20, SortEnum.ASCENDING, examples, "id");
    }
	
	public void removeImage() {		
		if(image.getAttachment() != null && image.getAttachment().getId() != null) removedFiles.add(image.getAttachment());
		image = null;
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
    
    public List<OrderType> getOrderTypeList() {
		List<FilterExample> examples = new ArrayList<>();
		return orderTypeService.findByExample(0, 10, examples);
	}

	public Orders getOrders() {
		return orders;
	}

	public void setOrders(Orders orders) {
		this.orders = orders;
	}

	public AttachmentBinaryDTO getImage() {
		return image;
	}
	
	public void setImage(AttachmentBinaryDTO image) {
		this.image = image;
	}
	

	public OrderLocation getLocation() {
		return location;
	}

	public void setLocation(OrderLocation location) {
		this.location = location;
	}

	public List<OrderLocation> getLocationList() {
		return locationList;
	}

	public void setLocationList(List<OrderLocation> locationList) {
		this.locationList = locationList;
	}

	public OrderGuide getGuide() {
		return guide;
	}

	public void setGuide(OrderGuide guide) {
		this.guide = guide;
	}

	public List<OrderGuide> getGuideList() {
		return guideList;
	}

	public void setGuideList(List<OrderGuide> guideList) {
		this.guideList = guideList;
	}

	public OrderTransport getTransport() {
		return transport;
	}

	public void setTransport(OrderTransport transport) {
		this.transport = transport;
	}

	public List<OrderTransport> getTransportList() {
		return transportList;
	}

	public void setTransportList(List<OrderTransport> transportList) {
		this.transportList = transportList;
	}

	public Country getCountry() {
		return country;
	}

	public void setCountry(Country country) {
		this.country = country;
	}

	public Oblast getOblast() {
		return oblast;
	}

	public void setOblast(Oblast oblast) {
		this.oblast = oblast;
	}
	
	public Company getCompany() {
		return company;
	}
	
	public void setCompany(Company company) {
		this.company = company;
	}
}
