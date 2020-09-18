package org.infosystema.peakcoin.model;

import java.io.Serializable;
import java.util.List;

import org.infosystema.peakcoin.beans.FilterExample;
import org.infosystema.peakcoin.domain.PersistentEntity;
import org.infosystema.peakcoin.service.GenericService;

/**
 * 
 * @author Kuttubek Aidaraliev
 *
 */

public abstract class SummableModel<S extends GenericService<E, ID>, E extends PersistentEntity<ID>, ID extends Serializable, N extends Number> 
	extends BaseModel<S, E, ID> {

	private static final long serialVersionUID = -8860901319533580761L;
	
	private N sum;
	
	public SummableModel(List<FilterExample> filters, S service) {
		super(filters, service);
	}
	
	public abstract Class<N> getSumClass();
	
	public void initTotalSum(String sumProperty) {
		try {
			sum = service.sumByExample(sumProperty, getSumClass(), filters);
			System.out.println("sum = " + sum);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public N getSum() {
		return sum;
	}
	
	public void setSum(N sum) {
		this.sum = sum;
	}

}
