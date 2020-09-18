package org.infosystema.peakcoin.service;

import javax.ejb.Local;

import org.infosystema.peakcoin.domain.Location;

/**
 * 
 * @author Kuttubek Aidaraliev
 *
 */

@Local
public interface LocationService extends GenericService<Location, Integer> {

}
