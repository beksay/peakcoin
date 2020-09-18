package org.infosystema.peakcoin.controller.base;

import java.io.IOException;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.ejb.EJB;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.faces.application.FacesMessage;  
import javax.faces.context.FacesContext;  
  
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.commons.io.IOUtils;
import org.infosystema.peakcoin.domain.Attachment;
import org.infosystema.peakcoin.dto.AttachmentBinaryDTO;
import org.infosystema.peakcoin.service.AttachmentService;
import org.infosystema.peakcoin.util.web.Messages;
import org.primefaces.event.FileUploadEvent;  
import org.primefaces.model.UploadedFile;

/**
 * 
 * @author Kuttubek Aidaraliev
 *
 */

@SuppressWarnings("cdi-not-passivation-capable")
@Named
@ConversationScoped
public class FileUploadController implements Serializable { 
	private static final long serialVersionUID = -388416034659956860L;
	
	@EJB
	AttachmentService service;
	
	private List<AttachmentBinaryDTO> files = new ArrayList<AttachmentBinaryDTO>();
	private List<Attachment> removedFiles = new ArrayList<Attachment>();
	
	@Inject
	private Conversation conversation;
	
	private int max = 20;
    
    public void initialize() {
		if(conversation.isTransient()) conversation.begin();
	}
	
    public void closeConversation() {
		if(!conversation.isTransient()) conversation.end();
	}
    
    public void handleFileUpload(FileUploadEvent event) throws IOException { 
    	if(conversation.isTransient()) conversation.begin();
    	
    	if(files.size() >= max) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(Messages.getMessage("fileLimitMessage")));  
            return;
    	}
    	
    	String fileName = getUTF8Name(event.getFile().getFileName());
    	files.add(createFileBinary(event.getFile()));
    	System.out.println(files);
    	System.out.println("conversation="+conversation.getId());

        FacesMessage msg = new FacesMessage(Messages.getMessage("fileSuccessfullyUploaded").replaceAll("\\{0\\}", fileName));  
        FacesContext.getCurrentInstance().addMessage(null, msg);  
    }  
    
	public Set<Attachment> convert() throws Exception {
		Set<Attachment> attachments = new HashSet<Attachment>();
		
		for (AttachmentBinaryDTO binary : files) {
			if(binary == null) continue;
			Attachment attachment = createAttachment(binary);
			binary.setAttachment(attachment);
			attachment = binary.getAttachment().getId() == null ? service.saveFromDTO(binary) : binary.getAttachment();
			attachments.add(attachment);
			attachment.setData(binary.getBody());
		}
    	files.clear();
		return attachments;
	}
	
	public void remove(String uuid) {
		Set<AttachmentBinaryDTO> binaries = new HashSet<AttachmentBinaryDTO>();
		for (AttachmentBinaryDTO binary : files) {
			if(binary.getUuid().equals(uuid)) binaries.add(binary);
		}
		
		synchronized(files){
			files.removeAll(binaries);
		}
		
		for (AttachmentBinaryDTO binary : binaries) {
			if(binary.getAttachment() != null && binary.getAttachment().getId() != null) removedFiles.add(binary.getAttachment());
		}
	}
	
	public void assertRemovedFiles() {
		if(removedFiles.isEmpty()) return;
		
		for (Attachment attachment : removedFiles) {
			service.remove(attachment);
		}
		
		removedFiles.clear();
	}
	
	private AttachmentBinaryDTO createFileBinary(UploadedFile file) throws IOException {
    	AttachmentBinaryDTO binary = new AttachmentBinaryDTO();
		binary.setName(getUTF8Name(file.getFileName()));
		binary.setMimeType(file.getContentType());
		binary.setBody(IOUtils.toByteArray(file.getInputstream()));
		
		return binary;
	}

	private Attachment createAttachment(AttachmentBinaryDTO binary) {
		if(binary.getAttachment() != null && binary.getAttachment().getId() != null) return binary.getAttachment();
		Attachment attachment = new Attachment();
		attachment.setFileName(binary.getName());
		attachment.setLocked(false);
		return attachment;
	}
    
    public List<AttachmentBinaryDTO> getFiles() {
		return files;
	}
    
    public void setFiles(List<AttachmentBinaryDTO> files) {
		this.files = files;
	}
    
    private String getUTF8Name(String fileName) throws UnsupportedEncodingException{
    	return new String(fileName.getBytes("ISO-8859-1"), "UTF-8");
    }
    
    public int getMax() {
		return max;
	}
    
    public void setMax(int max) {
		this.max = max;
	}
}  
