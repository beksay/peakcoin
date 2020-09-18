package org.infosystema.peakcoin.controller.base;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.enterprise.context.Conversation;
import javax.inject.Inject;

/**
 * 
 * @author Kuttubek Aidaraliev
 *
 */
public abstract class Conversational implements Serializable {
	
	private static final long serialVersionUID = 282062606859800901L;
	
	@Inject
	private Conversation conversation;
	
	@PostConstruct
	public void initialize() {
		if(conversation.isTransient()){ 
			conversation.begin();
			long DEFAULT_TIMEOUT =3600000L;
			conversation.setTimeout(DEFAULT_TIMEOUT);
		}
		
	}
	
    public void closeConversation() {
		if(!conversation.isTransient()) conversation.end();
	}
    
    public String getId(){
    	return conversation.getId();
    }

}
