package org.infosystema.peakcoin.conversation;

import javax.enterprise.context.ConversationScoped;
import javax.inject.Named;

import org.infosystema.peakcoin.annotation.Logged;
import org.infosystema.peakcoin.controller.base.Conversational;
import org.infosystema.peakcoin.domain.Driver;
import org.infosystema.peakcoin.dto.AttachmentBinaryDTO;

/**
 * 
 * @author Kuttubek Aidaraliev
 *
 */
@Logged
@Named
@ConversationScoped
public class ConversationDriver extends Conversational {
	
	private static final long serialVersionUID = -6100072166946495229L;
	
	private Driver driver;
	private AttachmentBinaryDTO passport;
	private AttachmentBinaryDTO license;

	public Driver getDriver() {
		return driver;
	}

	public void setDriver(Driver driver) {
		this.driver = driver;
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
