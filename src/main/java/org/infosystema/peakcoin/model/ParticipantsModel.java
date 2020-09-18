package org.infosystema.peakcoin.model;

import java.util.List;

import org.infosystema.peakcoin.beans.FilterExample;
import org.infosystema.peakcoin.domain.Participants;
import org.infosystema.peakcoin.service.ParticipantsService;

/**
 * 
 * @author Kuttubek Aidaraliev
 *
 */

public class ParticipantsModel extends BaseModel<ParticipantsService, Participants, Integer> {

	private static final long serialVersionUID = -4871106869905562775L;

	public ParticipantsModel(List<FilterExample> filters, ParticipantsService service) {
		super(filters, service);
	}
	
	@Override
	protected Integer getKey(String key) {
		return Integer.parseInt(key);
	}
	
}
