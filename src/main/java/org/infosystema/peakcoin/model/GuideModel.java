package org.infosystema.peakcoin.model;

import java.util.List;

import org.infosystema.peakcoin.beans.FilterExample;
import org.infosystema.peakcoin.domain.Guide;
import org.infosystema.peakcoin.service.GuideService;

/**
 * 
 * @author Kuttubek Aidaraliev
 *
 */

public class GuideModel extends BaseModel<GuideService, Guide, Integer> {

	private static final long serialVersionUID = -4871106869905562775L;

	public GuideModel(List<FilterExample> filters, GuideService service) {
		super(filters, service);
	}
	
	@Override
	protected Integer getKey(String key) {
		return Integer.parseInt(key);
	}
	
}
