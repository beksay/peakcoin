package org.infosystema.peakcoin.service;

import javax.ejb.Local;

import org.infosystema.peakcoin.domain.User;

/**
 * 
 * @author Kuttubek Aidaraliev
 *
 */

@Local
public interface UserService extends GenericService<User, Integer> {

}
