package org.infosystema.peakcoin.model;

import java.util.List;

import org.infosystema.peakcoin.beans.FilterExample;
import org.infosystema.peakcoin.domain.Company;
import org.infosystema.peakcoin.service.CompanyService;

/**
 * 
 * @author Kuttubek Aidaraliev
 *
 */

public class CompanyModel extends BaseModel<CompanyService, Company, Integer> {

	private static final long serialVersionUID = -4871106869905562775L;

	public CompanyModel(List<FilterExample> filters, CompanyService service) {
		super(filters, service);
	}
	
	@Override
	protected Integer getKey(String key) {
		return Integer.parseInt(key);
	}
	
}
