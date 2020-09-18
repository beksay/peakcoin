package org.infosystema.peakcoin.model;

import java.util.List;

import org.infosystema.peakcoin.beans.FilterExample;
import org.infosystema.peakcoin.domain.Driver;
import org.infosystema.peakcoin.service.DriverService;

/**
 * 
 * @author Kuttubek Aidaraliev
 *
 */

public class DriverModel extends BaseModel<DriverService, Driver, Integer> {

	private static final long serialVersionUID = -4871106869905562775L;

	public DriverModel(List<FilterExample> filters, DriverService service) {
		super(filters, service);
	}
	
	@Override
	protected Integer getKey(String key) {
		return Integer.parseInt(key);
	}
	
}
