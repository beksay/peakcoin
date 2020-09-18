package org.infosystema.peakcoin.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.infosystema.peakcoin.beans.FilterExample;
import org.infosystema.peakcoin.beans.SortEnum;
import org.infosystema.peakcoin.domain.PersistentEntity;
import org.infosystema.peakcoin.service.GenericService;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

/**
 * 
 * @author Kuttubek Aidaraliev
 *
 */

public abstract class BaseModel<S extends GenericService<E, ID>, E extends PersistentEntity<ID>, ID extends Serializable> extends LazyDataModel<E> {

	private static final long serialVersionUID = 1575455424649374631L;
	
	protected List<FilterExample> filters = new ArrayList<>();
	protected S service;
	
	private int rowCount;
	private String[] fetchProperties;
	private List<E> data = Collections.emptyList();
	
	protected abstract ID getKey(String key);

	public BaseModel(List<FilterExample> filters, S service) {
		this.filters = filters;
		this.service = service;
		initRowCount();
	}
	
	@Override
	public Object getRowKey(E entity) {
		return entity.getId();
	}
	
	@Override
	public E getRowData(String key) {
		try {
			ID id = getKey(key);
			if(data == null) return null;
			
			for (E e : data) {
				if(e.getId().equals(id)) return e;
			}
		} catch(Exception e){
			e.printStackTrace();
			return null;
		}
		
		return null;
	}
	@Override	
	 public List<E> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String,Object> filters) {
		try {
			sortField = sortField == null ? "id" : sortField;
			
			System.out.println(sortField + " " + sortOrder);
			System.out.println(filters);
			
			SortEnum sortEnum = sortOrder == null || sortOrder.equals(SortOrder.DESCENDING) ? SortEnum.DESCENDING : SortEnum.ASCENDING;
			data = this.filters == null || this.filters.isEmpty() 
					? getEntries(first, first + pageSize, sortEnum, sortField)
					: getByExample(first, first + pageSize, sortEnum, sortField); 
			
			System.out.println(data);
			return data;
		} catch(Exception e){
			return Collections.emptyList();
		}
	}
	
	private void initRowCount(){
		try {
			rowCount = this.filters == null || this.filters.isEmpty()
					? (int)service.countAll()
					: service.countByExample(filters).intValue();
					System.out.println(rowCount);
		} catch (Exception e) {
			e.printStackTrace();
			rowCount = 0;
		}
	}
	
	private List<E> getEntries(int first, int pageSize, SortEnum sortEnum, String sortField) {
		System.out.println(fetchProperties);
		return fetchProperties == null 
					? service.findEntries(first, first + pageSize, sortField, sortEnum)
					: service.findEntries(first, first + pageSize, sortField, sortEnum, fetchProperties);
	}		

	private List<E> getByExample(int first, int pageSize, SortEnum sortEnum, String sortField) {
		System.out.println(fetchProperties);
		return fetchProperties == null 
					? service.findByExample(first, first + pageSize, sortEnum, this.filters, sortField)
					: service.findByExample(first, first + pageSize, sortEnum, this.filters, sortField, fetchProperties);
	}
	
	@Override
	public int getRowCount() {
		return rowCount;
	}
	
	public void setFilters(List<FilterExample> filters) {
		this.filters = filters;
		initRowCount();
	}
	
	public List<FilterExample> getFilters() {
		return filters;
	}
	
	public String[] getFetchProperties() {
		return fetchProperties;
	}
	
	public void setFetchProperties(String[] fetchProperties) {
		this.fetchProperties = fetchProperties;
	}
	
}
