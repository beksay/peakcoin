package org.infosystema.peakcoin.model;

import java.util.List;

import org.infosystema.peakcoin.beans.FilterExample;
import org.infosystema.peakcoin.domain.Country;
import org.infosystema.peakcoin.service.CountryService;

/**
 * 
 * @author Kuttubek Aidaraliev
 *
 */

public class CountryModel extends BaseModel<CountryService, Country, Integer> {

	private static final long serialVersionUID = -4871106869905562775L;

	public CountryModel(List<FilterExample> filters, CountryService service) {
		super(filters, service);
	}
	
	@Override
	protected Integer getKey(String key) {
		return Integer.parseInt(key);
	}
	
}
