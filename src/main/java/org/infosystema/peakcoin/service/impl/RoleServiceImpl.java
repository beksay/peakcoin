package org.infosystema.peakcoin.service.impl;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;

import org.infosystema.peakcoin.dao.RoleDao;
import org.infosystema.peakcoin.dao.impl.RoleDaoImpl;
import org.infosystema.peakcoin.domain.Role;
import org.infosystema.peakcoin.service.RoleService;

/**
 * 
 * @author Kuttubek Aidaraliev
 *
 */

@Stateless
@Local(RoleService.class)
@TransactionManagement(TransactionManagementType.CONTAINER)
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class RoleServiceImpl extends GenericServiceImpl<Role, Integer, RoleDao> implements RoleService {

	@Override
	protected RoleDao getDao() {
		return new RoleDaoImpl(em, se);
	}

}
