package org.infosystema.peakcoin.conversation;

import javax.enterprise.context.ConversationScoped;
import javax.inject.Named;

import org.infosystema.peakcoin.annotation.Logged;
import org.infosystema.peakcoin.controller.base.Conversational;
import org.infosystema.peakcoin.domain.Transport;

/**
 * 
 * @author Kuttubek Aidaraliev
 *
 */
@Logged
@Named
@ConversationScoped
public class ConversationTransport extends Conversational {
	
	private static final long serialVersionUID = -6100072166946495229L;
	
	private Transport transport;
	
	public Transport getTransport() {
		return transport;
	}

	public void setTransport(Transport transport) {
		this.transport = transport;
	}
}
