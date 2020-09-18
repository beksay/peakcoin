package org.infosystema.peakcoin.service.impl;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;

import org.infosystema.peakcoin.dao.DictionaryDao;
import org.infosystema.peakcoin.dao.impl.DictionaryDaoImpl;
import org.infosystema.peakcoin.domain.Dictionary;
import org.infosystema.peakcoin.service.DictionaryService;

/**
 * 
 * @author Kuttubek Aidaraliev
 *
 */

@Stateless
@Local(DictionaryService.class)
@TransactionManagement(TransactionManagementType.CONTAINER)
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class DictionaryServiceImpl extends GenericServiceImpl<Dictionary, Integer, DictionaryDao> implements DictionaryService {

	@Override
	protected DictionaryDao getDao() {
		return new DictionaryDaoImpl(em, se);
	}

}
