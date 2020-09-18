package org.infosystema.peakcoin.service.impl;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;

import org.infosystema.peakcoin.dao.DictionaryTypeDao;
import org.infosystema.peakcoin.dao.impl.DictionaryTypeDaoImpl;
import org.infosystema.peakcoin.domain.DictionaryType;
import org.infosystema.peakcoin.service.DictionaryTypeService;

/**
 * 
 * @author Kuttubek Aidaraliev
 *
 */

@Stateless
@Local(DictionaryTypeService.class)
@TransactionManagement(TransactionManagementType.CONTAINER)
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class DictionaryTypeServiceImpl extends GenericServiceImpl<DictionaryType, Integer, DictionaryTypeDao> implements DictionaryTypeService {

	@Override
	protected DictionaryTypeDao getDao() {
		return new DictionaryTypeDaoImpl(em, se);
	}

}
