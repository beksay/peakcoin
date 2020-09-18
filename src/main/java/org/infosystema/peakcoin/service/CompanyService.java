package org.infosystema.peakcoin.service;

import javax.ejb.Local;

import org.infosystema.peakcoin.domain.Company;

/**
 * 
 * @author Kuttubek Aidaraliev
 *
 */

@Local
public interface CompanyService extends GenericService<Company, Integer> {

}
