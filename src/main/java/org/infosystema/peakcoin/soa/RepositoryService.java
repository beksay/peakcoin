package org.infosystema.peakcoin.soa;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.activation.DataHandler;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.xml.bind.annotation.XmlMimeType;
import javax.xml.ws.BindingType;
import javax.xml.ws.soap.MTOM;

import org.infosystema.peakcoin.dto.CountResponse;
import org.infosystema.peakcoin.dto.DataResponse;
import org.infosystema.peakcoin.dto.IdentifyResponse;
import org.infosystema.peakcoin.dto.InfoResponse;
import org.infosystema.peakcoin.dto.URLResponse;

/**
 * 
 * @author Kuttubek Aidaraliev
 *
 */

@MTOM(enabled=true, threshold=3072)
@WebService(name="RepositoryService", targetNamespace="http://soa.repository.ak.com/")
@BindingType(value = javax.xml.ws.soap.SOAPBinding.SOAP12HTTP_MTOM_BINDING)
@SOAPBinding(style=SOAPBinding.Style.DOCUMENT, use=SOAPBinding.Use.LITERAL, parameterStyle=SOAPBinding.ParameterStyle.WRAPPED)
public interface RepositoryService {
	
	@WebMethod(operationName="save")
	@WebResult(name="response")
	IdentifyResponse save(@WebParam(name="name")String name, @WebParam(name="data") 
	@XmlMimeType("application/octet-stream")DataHandler data) throws IOException;
	
	@WebMethod(operationName="saveWithDate")
	@WebResult(name="response")
	IdentifyResponse save(@WebParam(name="name")String name, @WebParam(name="date")Date date, @WebParam(name="data") 
	@XmlMimeType("application/octet-stream")DataHandler data) throws IOException;
	
	@WebMethod(operationName="saveFromString")
	@WebResult(name="response")
	IdentifyResponse save(@WebParam(name="name")String name, @WebParam(name="date")Date date, @WebParam(name="data") 
	String data) throws IOException;
	
	@WebMethod(operationName="getInfo")
	@WebResult(name="response")
	InfoResponse getInfo(@WebParam(name="id")String id) throws Exception;
	
	@WebMethod(operationName="getURL")
	@WebResult(name="response")
	URLResponse getURL(@WebParam(name="ids") List<String> ids) throws Exception;
	
	@WebMethod(operationName="getData")
	@WebResult(name="response")
	DataResponse getData(@WebParam(name="id")String id);
	
	@WebMethod(operationName="getCount")
	@WebResult(name="response")
	CountResponse getCount();

}
