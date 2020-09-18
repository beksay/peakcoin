package org.infosystema.peakcoin.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/***
 * 
 * @author Kuttubek Aidaraliev
 *
 */

public class Digest {

	private final String algorithm;
	
	public Digest(String algorithm) {
		this.algorithm = algorithm;
	}
	
	public String doEncypt(String message) throws NoSuchAlgorithmException {
        byte[] dataBytes = message.getBytes();
 
        return doEncypt(dataBytes);
 
	}
	
	public String doEncypt(byte[] dataBytes) throws NoSuchAlgorithmException {
		MessageDigest md = MessageDigest.getInstance(algorithm);
 
        md.update(dataBytes, 0, dataBytes.length);
        byte[] mdbytes = md.digest();
 
        //convert the byte to hex format
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < mdbytes.length; i++) {
          sb.append(Integer.toString((mdbytes[i] & 0xff) + 0x100, 16).substring(1));
        }
 
       return sb.toString();
 
	}

}