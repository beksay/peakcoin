package org.infosystema.peakcoin.dto;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.activation.DataSource;
import javax.activation.FileTypeMap;
import javax.activation.MimetypesFileTypeMap;

/**
 * 
 * @author Kuttubek Aidaraliev
 *
 */

public class AttachmentDataSource implements DataSource {

	private AttachmentBinaryDTO attachment;

    public AttachmentDataSource(AttachmentBinaryDTO attachment) {
        this.attachment = attachment;
    }

	@Override
	public String getContentType() {
		FileTypeMap mimeTypesMap = MimetypesFileTypeMap.getDefaultFileTypeMap();
		return attachment.getMimeType() != null ? attachment.getMimeType() : mimeTypesMap.getContentType(attachment.getName());
	}

	@Override
	public InputStream getInputStream() throws IOException {
		return new ByteArrayInputStream(attachment.getBody());
	}

	@Override
	public String getName() {
		return attachment.getName();
	}

	@Override
	public OutputStream getOutputStream() throws IOException {
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		outputStream.write(attachment.getBody());
		return outputStream;
	}
}

