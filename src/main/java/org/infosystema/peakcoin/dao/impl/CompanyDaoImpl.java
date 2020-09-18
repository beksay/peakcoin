package org.infosystema.peakcoin.dao.impl;

import javax.enterprise.event.Event;
import javax.persistence.EntityManager;

import org.infosystema.peakcoin.dao.CompanyDao;
import org.infosystema.peakcoin.domain.Company;

/**
 * 
 * @author Kuttubek Aidaraliev
 *
 */

public class CompanyDaoImpl extends GenericDaoImpl<Company, Integer> implements CompanyDao {

	public CompanyDaoImpl(EntityManager entityManager, Event<Company> eventSource) {
		super(entityManager, eventSource);
	}

}
