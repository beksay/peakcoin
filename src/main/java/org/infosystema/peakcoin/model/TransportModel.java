package org.infosystema.peakcoin.model;

import java.util.List;

import org.infosystema.peakcoin.beans.FilterExample;
import org.infosystema.peakcoin.domain.Transport;
import org.infosystema.peakcoin.service.TransportService;

/**
 * 
 * @author Kuttubek Aidaraliev
 *
 */

public class TransportModel extends BaseModel<TransportService, Transport, Integer> {

	private static final long serialVersionUID = -4871106869905562775L;

	public TransportModel(List<FilterExample> filters, TransportService service) {
		super(filters, service);
	}
	
	@Override
	protected Integer getKey(String key) {
		return Integer.parseInt(key);
	}
	
}
