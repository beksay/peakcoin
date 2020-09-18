package org.infosystema.peakcoin.controller.user;

import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.ejb.EJB;
import javax.enterprise.context.ConversationScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.infosystema.peakcoin.annotation.Logged;
import org.infosystema.peakcoin.controller.base.Conversational;
import org.infosystema.peakcoin.domain.User;
import org.infosystema.peakcoin.enums.ScopeConstants;
import org.infosystema.peakcoin.service.UserService;
import org.infosystema.peakcoin.util.web.FacesMessages;
import org.infosystema.peakcoin.util.web.FacesScopeQualifier;
import org.infosystema.peakcoin.util.web.LoginUtil;
import org.infosystema.peakcoin.util.web.Messages;
import org.infosystema.peakcoin.util.web.ScopeQualifier;

/**
 * 
 * @author Kuttubek Aidaraliev
 *
 */

@Logged
@Named
@ConversationScoped
public class PasswordChanger extends Conversational {

	private static final long serialVersionUID = 5651758429305872940L;
	private String oldPassword;
	private String newPassword;
	private String confirmPassword;
	
	@EJB
	private UserService service;
	
	@Inject
	private LoginUtil loginUtil;
	
	public PasswordChanger() {}
	
	public String change() {
		User user = new FacesScopeQualifier().getValue(LoginUtil.CURRENT_USER_SESSION_KEY, ScopeConstants.SESSION_SCOPE);
		System.out.println("go to add [change password] by user " + user);
		return "/view/user/change_password.xhtml?faces-redirect=true";
	}
	
	public String cancel() {
		return "/view/profile/profile_list.xhtml?faces-redirect=true";
	}
	
	public String doChange() throws Exception {
		User user = new FacesScopeQualifier().getValue(LoginUtil.CURRENT_USER_SESSION_KEY, ScopeConstants.SESSION_SCOPE);
		System.out.println("go to doAdd [change password] by user " + user);
		String hashPassword = loginUtil.getHashPassword(newPassword);
		
		if(newPassword == null || confirmPassword == null || !newPassword.equals(confirmPassword)){
			FacesMessages.addMessage(Messages.getMessage("passwordNotMatch"), Messages.getMessage("passwordNotMatch"), null);
			return null;
		}
		
		if(oldPassword == null || !user.getPassword().equals(loginUtil.getHashPassword(oldPassword))) {
			FacesMessages.addMessage(Messages.getMessage("invalidPassword"), Messages.getMessage("invalidPassword"), null);
			return null;
		}
		
		Calendar calendar = new GregorianCalendar();
		calendar.add(Calendar.DAY_OF_MONTH, 90);
		
		user.setPassword(hashPassword);
		user.setCountFailed(0);
		user.setDatePasswordExpired(calendar.getTime());
		user = service.merge(user);
		
		ScopeQualifier qualifier = new FacesScopeQualifier();
		qualifier.remove("changePassword", ScopeConstants.SESSION_SCOPE);
		
		
		return cancel();
	}
	
	public String getOldPassword() {
		return oldPassword;
	}
	
	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}
	
	public String getNewPassword() {
		return newPassword;
	}
	
	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}
	
	public String getConfirmPassword() {
		return confirmPassword;
	}
	
	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}
	
}

