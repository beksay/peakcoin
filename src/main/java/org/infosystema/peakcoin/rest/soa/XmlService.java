package org.infosystema.peakcoin.rest.soa;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.infosystema.peakcoin.dto.rest.KicbRequest;

/**
 * 
 * @author Kuttubek Aidaraliev
 *
 */

@Path("xml")
public interface XmlService {

	@POST
	@Consumes({ MediaType.APPLICATION_XML })
	@Produces(value = { MediaType.APPLICATION_XML })
	@Path("kicb")
	Response kicbRequest(KicbRequest request) throws Exception;

}
