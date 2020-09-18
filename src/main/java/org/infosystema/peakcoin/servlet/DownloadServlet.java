package org.infosystema.peakcoin.servlet;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import javax.activation.MimetypesFileTypeMap;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.io.IOUtils;
import org.infosystema.peakcoin.domain.Attachment;
import org.infosystema.peakcoin.dto.DataResponse;
import org.infosystema.peakcoin.service.AttachmentService;
import org.infosystema.peakcoin.soa.RepositoryServiceFactory;
import org.infosystema.peakcoin.util.Krypto;
import org.infosystema.peakcoin.util.Translit;



/**
 * 
 * @author Kuttubek Aidaraliev
 *
 */

@WebServlet(urlPatterns = {"/download"})
public class DownloadServlet extends HttpServlet {
	
	@EJB
	private AttachmentService service;

	private static final long serialVersionUID = -7626497440520150364L;
	
	public DownloadServlet() {}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		try {
			String key = req.getParameter("key");
			
			if(key == null) {
				resp.sendError(HttpServletResponse.SC_NOT_FOUND);
				return;
			}
			
			Krypto krypto = new Krypto();
		    krypto.setKey(new byte[]{0x21, 0x10, 0x51, 0x09, 0x08, 0x70, 0x07, 04});
		    String value = new String(krypto.doDecrypt(key));
		    String sessionId = value.substring(0, value.indexOf("@@@@@@@"));
		    
		    if(!req.getSession().getId().equals(sessionId)){
		    	resp.sendError(HttpServletResponse.SC_UNAUTHORIZED);
				return;
		    }
		    
		    String v = value.substring(value.indexOf("@@@@@@@") + 7);
		    Integer id = new Integer(v);
		    Attachment attachment = service.findById(id, false);

			DataResponse response = RepositoryServiceFactory.getInstance().getService().getData(attachment.getRepositoryName());
			InputStream stream = response.getData().getInputStream();
			MimetypesFileTypeMap mimeTypesMap = new MimetypesFileTypeMap();
			String contentType = mimeTypesMap.getContentType(attachment.getFileName());
			
			resp.setContentType(contentType);
		    resp.setHeader("Content-Disposition", "attachment; filename=\"" + Translit.translit(attachment.getFileName()) + "\";");
		    OutputStream os = resp.getOutputStream();
		    
		    try {
		        IOUtils.copy(stream, os);
		    } catch (IOException e) {
		        e.printStackTrace();
		    } finally {
		        os.close();
		        stream.close();
		    }
		} catch(Exception e){
			e.printStackTrace();
			throw new ServletException(e);
		}
		
	}
	

}
