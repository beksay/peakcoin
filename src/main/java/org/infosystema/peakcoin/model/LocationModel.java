package org.infosystema.peakcoin.model;

import java.util.List;

import org.infosystema.peakcoin.beans.FilterExample;
import org.infosystema.peakcoin.domain.Location;
import org.infosystema.peakcoin.service.LocationService;

/**
 * 
 * @author Kuttubek Aidaraliev
 *
 */

public class LocationModel extends BaseModel<LocationService, Location, Integer> {

	private static final long serialVersionUID = -4871106869905562775L;

	public LocationModel(List<FilterExample> filters, LocationService service) {
		super(filters, service);
	}
	
	@Override
	protected Integer getKey(String key) {
		return Integer.parseInt(key);
	}
	
}
