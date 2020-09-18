package org.infosystema.peakcoin.util;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Set;

import org.infosystema.peakcoin.domain.Attachment;
import org.infosystema.peakcoin.dto.AttachmentBinaryDTO;

/**
 * 
 * @author Kuttubek Aidaraliev
 *
 */

public class Util {

	public static List<AttachmentBinaryDTO> getFiles(Set<Attachment> attachments) {
		List<AttachmentBinaryDTO> list = new ArrayList<>();
		for (Attachment attachment : attachments) {
			try {
				list.add(createAttachmentDTO(attachment));
			} catch (Exception e) {
				e.printStackTrace();
				continue;
			}
		}
		return list;
	}

	public static AttachmentBinaryDTO createAttachmentDTO(Attachment attachment) {

		try {
			AttachmentBinaryDTO attachmentBinaryDTO = new AttachmentBinaryDTO();
			attachmentBinaryDTO.setName(attachment.getFileName());
			attachmentBinaryDTO.setRepositoryName(attachment.getRepositoryName());
			attachmentBinaryDTO.setAttachment(attachment);
			
			return attachmentBinaryDTO;
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
			return null;
		}
	}
	
	public static int getYearsBetweenDates(Date first, Date second) {
	    Calendar firstCal = GregorianCalendar.getInstance();
	    Calendar secondCal = GregorianCalendar.getInstance();

	    firstCal.setTime(first);
	    secondCal.setTime(second);

	    secondCal.add(Calendar.DAY_OF_YEAR, 1 - firstCal.get(Calendar.DAY_OF_YEAR));

	    return secondCal.get(Calendar.YEAR) - firstCal.get(Calendar.YEAR);
	}
	
	public static int getYearsBetweenDateAndNow(Date date) {
		return getYearsBetweenDates(date, new Date());
	}
		
}