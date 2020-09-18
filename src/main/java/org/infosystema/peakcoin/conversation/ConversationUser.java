package org.infosystema.peakcoin.conversation;

import javax.enterprise.context.ConversationScoped;
import javax.inject.Named;

import org.infosystema.peakcoin.annotation.Logged;
import org.infosystema.peakcoin.controller.base.Conversational;
import org.infosystema.peakcoin.domain.Company;
import org.infosystema.peakcoin.domain.Person;
import org.infosystema.peakcoin.domain.Role;
import org.infosystema.peakcoin.domain.User;

/**
 * 
 * @author Kuttubek Aidaraliev
 *
 */
@Logged
@Named
@ConversationScoped
public class ConversationUser extends Conversational {
	
	private static final long serialVersionUID = -6100072166946495229L;
	
	private User user;
	private Person person;
	private Role role;
	private Company company;

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}
	
	public Company getCompany() {
		return company;
	}
	
	public void setCompany(Company company) {
		this.company = company;
	}
}
