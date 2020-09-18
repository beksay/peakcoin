package org.infosystema.peakcoin.util;

import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/****
 * 
 * @author Kuttubek Aidaraliev
 *
 */

public class MailSender {
	
	private static String e_mail = "peak.saitkulov@gmail.com";
	private static String name = "PeakCoin";
	private static String password = "PeakCoin123";
	private static String host = "smtp.gmail.com";
	private static String port = "465";
	
	private ExecutorService service = null;
	private static MailSender sender = null; 
	
	private MailSender() {
		service = Executors.newFixedThreadPool(5);
	}
	
	public synchronized static MailSender getInstance() {
		if(sender == null){
			sender = new MailSender();
		}
		return sender;
	}
	
	public synchronized static void destroy() {
		if(sender != null) sender.doDestroy();
	}
	
	public boolean send(org.infosystema.peakcoin.beans.Message m) {
		
		Properties props = new Properties();
		props.put("mail.smtp.user", e_mail);
		props.put("mail.smtp.host", host);
		props.put("mail.smtp.port", port);
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.debug", "false");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.socketFactory.port", port);
		props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		props.put("mail.smtp.socketFactory.fallback", "false");

		Session mailSession = Session.getDefaultInstance(props, null);
		MimeMessage message = new MimeMessage(mailSession);
	      
		
		try {
			message.setFrom(new InternetAddress(e_mail, name));
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(m.getEmail()));
			message.setSubject(m.getSubject());
			message.setContent(m.getContent(), "text/html; charset=UTF-8"); // html text

			message.setSender(new InternetAddress("peak.saitkulov@gmail.com"));

			Transport transport = mailSession.getTransport("smtp");
			//transport.connect(host, Integer.valueOf(port), e_mail, password);
			transport.connect(host, e_mail, password);
			transport.sendMessage(message, message.getRecipients(Message.RecipientType.TO));
			transport.close();

			return true;

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public void asyncSend(final org.infosystema.peakcoin.beans.Message m) {
		service.submit(new Runnable() {
			public void run() {
				send(m);
			}
		});
	}
	
	public void doDestroy() {
		service.shutdown();
		sender = null;
	}
}