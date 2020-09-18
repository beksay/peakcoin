package org.infosystema.peakcoin.domain;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 
 * @author Kuttubek Aidaraliev
 *
 */

@Entity
@Table(name = "operator")
public class Operator extends AbstractEntity<Integer> {
	private static final long serialVersionUID = 1L;
	private String name;
	private String username;
	private String password;

	public Operator() {
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
}