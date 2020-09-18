package org.infosystema.peakcoin.model;

import java.util.List;

import org.infosystema.peakcoin.beans.FilterExample;
import org.infosystema.peakcoin.domain.DictionaryType;
import org.infosystema.peakcoin.service.DictionaryTypeService;

/**
 * 
 * @author Kuttubek Aidaraliev
 *
 */

public class DictionaryTypeModel extends BaseModel<DictionaryTypeService, DictionaryType, Integer> {

	private static final long serialVersionUID = -4871106869905562775L;

	public DictionaryTypeModel(List<FilterExample> filters, DictionaryTypeService service) {
		super(filters, service);
	}
	
	@Override
	protected Integer getKey(String key) {
		return Integer.parseInt(key);
	}
	
}
