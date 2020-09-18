package org.infosystema.peakcoin.service;

import javax.ejb.Local;

import org.infosystema.peakcoin.domain.Dictionary;

/**
 * 
 * @author Kuttubek Aidaraliev
 *
 */

@Local
public interface DictionaryService extends GenericService<Dictionary, Integer> {

}
