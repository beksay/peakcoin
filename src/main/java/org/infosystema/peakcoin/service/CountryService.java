package org.infosystema.peakcoin.service;

import javax.ejb.Local;

import org.infosystema.peakcoin.domain.Country;

/**
 * 
 * @author Kuttubek Aidaraliev
 *
 */

@Local
public interface CountryService extends GenericService<Country, Integer> {

}
