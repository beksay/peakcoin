package org.infosystema.peakcoin.service;

import javax.ejb.Local;

import org.infosystema.peakcoin.domain.Person;

/**
 * 
 * @author Kuttubek Aidaraliev
 *
 */

@Local
public interface PersonService extends GenericService<Person, Integer> {

}
