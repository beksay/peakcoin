package org.infosystema.peakcoin.conversation;

import java.util.List;

import javax.enterprise.context.ConversationScoped;
import javax.inject.Named;

import org.infosystema.peakcoin.annotation.Logged;
import org.infosystema.peakcoin.controller.base.Conversational;
import org.infosystema.peakcoin.domain.Company;
import org.infosystema.peakcoin.domain.Country;
import org.infosystema.peakcoin.domain.Oblast;
import org.infosystema.peakcoin.domain.OrderGuide;
import org.infosystema.peakcoin.domain.OrderLocation;
import org.infosystema.peakcoin.domain.OrderTransport;
import org.infosystema.peakcoin.domain.Orders;
import org.infosystema.peakcoin.domain.Participants;
import org.infosystema.peakcoin.domain.Person;
import org.infosystema.peakcoin.dto.AttachmentBinaryDTO;

/**
 * 
 * @author Kuttubek Aidaraliev
 *
 */
@Logged
@Named
@ConversationScoped
public class ConversationOrder extends Conversational {
	
	private static final long serialVersionUID = -6100072166946495229L;
	
	private Orders orders;
	private AttachmentBinaryDTO image;
	private Company company;
	private Participants participants;
	private Person person;
	private OrderLocation location;
	private OrderGuide guide;
	private OrderTransport transport;
	private Country country;
	private Oblast oblast;
	private List<OrderLocation> locationList;
	private List<OrderGuide> guideList;
	private List<OrderTransport> transportList;

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

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public Participants getParticipants() {
		return participants;
	}

	public void setParticipants(Participants participants) {
		this.participants = participants;
	}

	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}

	public OrderLocation getLocation() {
		return location;
	}

	public void setLocation(OrderLocation location) {
		this.location = location;
	}

	public OrderGuide getGuide() {
		return guide;
	}

	public void setGuide(OrderGuide guide) {
		this.guide = guide;
	}

	public OrderTransport getTransport() {
		return transport;
	}

	public void setTransport(OrderTransport transport) {
		this.transport = transport;
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

	public List<OrderLocation> getLocationList() {
		return locationList;
	}

	public void setLocationList(List<OrderLocation> locationList) {
		this.locationList = locationList;
	}

	public List<OrderGuide> getGuideList() {
		return guideList;
	}

	public void setGuideList(List<OrderGuide> guideList) {
		this.guideList = guideList;
	}

	public List<OrderTransport> getTransportList() {
		return transportList;
	}

	public void setTransportList(List<OrderTransport> transportList) {
		this.transportList = transportList;
	}
}
