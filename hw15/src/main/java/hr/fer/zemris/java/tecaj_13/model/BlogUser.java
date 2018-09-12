package hr.fer.zemris.java.tecaj_13.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

/**
 * Class represents blog user.
 * 
 * @author Toni Martinčić
 * @version 1.0
 */
@Entity
@Table(name="blog_users")
public class BlogUser {
	
	/**
	 * Id.
	 */
	private Long id;
	
	/**
	 * First name.
	 */
	private String firstName;
	
	/**
	 * Last name.
	 */
	private String lastName;
	
	/**
	 * Nick.
	 */
	private String nick;
	
	/**
	 * Email.
	 */
	private String email;
	
	/**
	 * Password hash.
	 */
	private String passwordHash;
	
	/**
	 * Entries.
	 */
	private List<BlogEntry> entries = new ArrayList<>();

	/**
	 * Getter for the id.
	 * 
	 * @return id
	 */
	@Id @GeneratedValue
	public Long getId() {
		return id;
	}

	/**
	 * Setter for the id.
	 * 
	 * @param id
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Getter for the first name.
	 * 
	 * @return first name
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * Setter for the first name.
	 * 
	 * @param firstName first name
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * Getter for the last name.
	 * 
	 * @return last name
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * Setter for the last name.
	 * 
	 * @param lastName last name
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * Getter for the nick.
	 * 
	 * @return nick
	 */
	@Column(unique=true)
	public String getNick() {
		return nick;
	}

	/**
	 * Setter for the nick.
	 * 
	 * @param nick nick
	 */
	public void setNick(String nick) {
		this.nick = nick;
	}

	/**
	 * Getter for the email.
	 * 
	 * @return email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * Setter for the email.
	 * 
	 * @param email email
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * Getter for the password hash.
	 * 
	 * @return password hash
	 */
	public String getPasswordHash() {
		return passwordHash;
	}

	/**
	 * Setter for the password hash.
	 * 
	 * @param passwordHash password hahs
	 */
	public void setPasswordHash(String passwordHash) {
		this.passwordHash = passwordHash;
	}
	
	/**
	 * Getter for the entries
	 * 
	 * @return entries
	 */
	@OneToMany(mappedBy="creator", fetch=FetchType.LAZY, cascade=CascadeType.PERSIST, orphanRemoval=true)
	@OrderBy("createdAt")
	public List<BlogEntry> getEntries() {
		return entries;
	}

	/**
	 * Setter for the entries.
	 * 
	 * @param entries entries
	 */
	public void setEntries(List<BlogEntry> entries) {
		this.entries = entries;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BlogUser other = (BlogUser) obj;
		if (id != other.id)
			return false;
		return true;
	}		
}
