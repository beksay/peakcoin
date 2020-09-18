package org.infosystema.peakcoin.service;

import java.io.IOException;

import javax.ejb.Local;

import org.infosystema.peakcoin.domain.Attachment;
import org.infosystema.peakcoin.dto.AttachmentBinaryDTO;

/**
 * 
 * @author Kuttubek Aidaraliev
 *
 */

@Local
public interface AttachmentService extends GenericService<Attachment, Integer> {
	
	Attachment saveFromDTO(AttachmentBinaryDTO binary) throws IOException;

}
