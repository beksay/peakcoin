package org.infosystema.peakcoin.conversion;

import java.io.Serializable;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

import org.infosystema.peakcoin.dao.GenericDao;
import org.infosystema.peakcoin.domain.PersistentEntity;
import org.infosystema.peakcoin.service.GenericService;

/**
 * 
 * @author Kuttubek Aidaraliev
 *
 */

public abstract class EntityConvertor<T extends PersistentEntity<ID>, ID extends Serializable, D extends GenericDao<T, ID>, 
			S extends GenericService<T, ID>> implements Converter {
	
	public EntityConvertor() {}
	
	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String key) {
		ID id = getID(key);
		if(id != null){
			System.out.println(getClass());
			T entity = getService().findById(id, false);
			System.out.println(entity);
			return entity;
		}
		return null;
	}
	
	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if(value instanceof PersistentEntity){
			@SuppressWarnings("unchecked")
			PersistentEntity<ID> entity = (PersistentEntity<ID>)value;
			return entity.getId().toString();
		}
		return null;
	}
	
	protected abstract S getService();
	protected abstract ID getID(String key);

}
