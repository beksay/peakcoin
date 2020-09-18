package org.infosystema.peakcoin.domain;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.infosystema.peakcoin.enums.GuideStatus;


/**
 * 
 * @author Kuttubek Aidaraliev
 *
 */

@Entity
@Table(name="guide")
public class Guide extends AbstractEntity<Integer>  {
	private static final long serialVersionUID = 1L;
	private Person person;
	private String pin;
	private Boolean translator;
	private Boolean instructor;
	private Boolean dutyCamp;
	private Boolean mountainGuide;
	private Boolean assistant;
	private Boolean cityGuide;
	private Attachment translatorDoc;
	private Attachment instructorDoc;
	private Set<Dictionary> instructorActivities;
	private Integer aboveSea;
	private Integer aboveSeaReady;
	private Attachment mountainDoc;
	private Attachment cityDoc;
	private GuideStatus status;
	private Attachment document;
	private Boolean requerement;

	@Enumerated(EnumType.ORDINAL)
	public GuideStatus getStatus() {
		return status;
	}

	public void setStatus(GuideStatus status) {
		this.status = status;
	}
	
	@ManyToMany(fetch=FetchType.LAZY)
	@JoinTable(
	  name = "guide_instructor_activities", 
	  joinColumns = @JoinColumn(name = "guide_id"), 
	  inverseJoinColumns = @JoinColumn(name = "dictionary_id"))	
	public Set<Dictionary> getInstructorActivities() {
		return instructorActivities;
	}
	
	public void setInstructorActivities(Set<Dictionary> instructorActivities) {
		this.instructorActivities = instructorActivities;
	}

	public String getPin() {
		return pin;
	}

	public void setPin(String pin) {
		this.pin = pin;
	}

	public Boolean getTranslator() {
		return translator;
	}

	public void setTranslator(Boolean translator) {
		this.translator = translator;
	}

	@Column(name="duty_camp")
	public Boolean getDutyCamp() {
		return dutyCamp;
	}

	public void setDutyCamp(Boolean dutyCamp) {
		this.dutyCamp = dutyCamp;
	}

	@Column(name="mountain_guide")
	public Boolean getMountainGuide() {
		return mountainGuide;
	}

	public void setMountainGuide(Boolean mountainGuide) {
		this.mountainGuide = mountainGuide;
	}

	public Boolean getInstructor() {
		return instructor;
	}

	public void setInstructor(Boolean instructor) {
		this.instructor = instructor;
	}

	public Boolean getAssistant() {
		return assistant;
	}

	public void setAssistant(Boolean assistant) {
		this.assistant = assistant;
	}

	@Column(name="city_guide")
	public Boolean getCityGuide() {
		return cityGuide;
	}

	public void setCityGuide(Boolean cityGuide) {
		this.cityGuide = cityGuide;
	}

	@ManyToOne
	@JoinColumn (name="translator_doc_id")
	public Attachment getTranslatorDoc() {
		return translatorDoc;
	}

	public void setTranslatorDoc(Attachment translatorDoc) {
		this.translatorDoc = translatorDoc;
	}

	@ManyToOne
	@JoinColumn (name="instructor_doc_id")
	public Attachment getInstructorDoc() {
		return instructorDoc;
	}

	public void setInstructorDoc(Attachment instructorDoc) {
		this.instructorDoc = instructorDoc;
	}

	@Column(name="above_sea")
	public Integer getAboveSea() {
		return aboveSea;
	}

	public void setAboveSea(Integer aboveSea) {
		this.aboveSea = aboveSea;
	}

	@Column(name="above_sea_ready")
	public Integer getAboveSeaReady() {
		return aboveSeaReady;
	}

	public void setAboveSeaReady(Integer aboveSeaReady) {
		this.aboveSeaReady = aboveSeaReady;
	}

	@ManyToOne
	@JoinColumn (name="mountain_doc_id")
	public Attachment getMountainDoc() {
		return mountainDoc;
	}

	public void setMountainDoc(Attachment mountainDoc) {
		this.mountainDoc = mountainDoc;
	}

	@ManyToOne
	@JoinColumn (name="city_id")
	public Attachment getCityDoc() {
		return cityDoc;
	}

	public void setCityDoc(Attachment cityDoc) {
		this.cityDoc = cityDoc;
	}

	@ManyToOne
	@JoinColumn (name="document_id")
	public Attachment getDocument() {
		return document;
	}

	public void setDocument(Attachment document) {
		this.document = document;
	}

	public Boolean getRequerement() {
		return requerement;
	}

	public void setRequerement(Boolean requerement) {
		this.requerement = requerement;
	}

	@ManyToOne
	@JoinColumn (name="person_id")
	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}

}