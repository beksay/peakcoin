package org.infosystema.peakcoin.conversation;

import javax.enterprise.context.ConversationScoped;
import javax.inject.Named;

import org.infosystema.peakcoin.annotation.Logged;
import org.infosystema.peakcoin.controller.base.Conversational;
import org.infosystema.peakcoin.domain.Location;

/**
 * 
 * @author Kuttubek Aidaraliev
 *
 */
@Logged
@Named
@ConversationScoped
public class ConversationLocation extends Conversational {
	
	private static final long serialVersionUID = -6100072166946495229L;
	
	private Location location;

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	
}
