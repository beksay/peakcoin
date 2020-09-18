package org.infosystema.peakcoin.listener;

import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;

/**
 * 
 * @author Kuttubek Aidaraliev
 *
 */

public class FacesPhaseListener implements PhaseListener {

	private static final long serialVersionUID = -6662233786609884435L;

	@Override
	public void afterPhase(PhaseEvent event) {
		System.out.println("After :-"+event.getPhaseId());
	} 

	@Override
	public void beforePhase(PhaseEvent event) {
		System.out.println("Before - " + event.getPhaseId().toString()); 
	}

	@Override
	public PhaseId getPhaseId() {
		return PhaseId.ANY_PHASE;
	}
	
}
