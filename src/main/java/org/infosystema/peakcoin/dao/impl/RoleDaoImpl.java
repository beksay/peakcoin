package org.infosystema.peakcoin.dao.impl;

import javax.enterprise.event.Event;
import javax.persistence.EntityManager;

import org.infosystema.peakcoin.dao.RoleDao;
import org.infosystema.peakcoin.domain.Role;

/**
 * 
 * @author Kuttubek Aidaraliev
 *
 */

public class RoleDaoImpl extends GenericDaoImpl<Role, Integer> implements RoleDao {

	public RoleDaoImpl(EntityManager entityManager, Event<Role> eventSource) {
		super(entityManager, eventSource);
	}

}
