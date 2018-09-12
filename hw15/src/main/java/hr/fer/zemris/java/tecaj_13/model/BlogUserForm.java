package hr.fer.zemris.java.tecaj_13.model;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import hr.fer.zemris.java.tecaj_13.dao.DAOProvider;
import hr.fer.zemris.java.tecaj_13.utils.Util;

/**
 * Class represents form for the {@link BlogUser}.
 * 
 * @author Toni Martinčić
 * @version 1.0
 */
public class BlogUserForm {
	
	/**
	 * Id.
	 */
	private String id;
	
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
	 * Password.
	 */
	private String password;
	
	/**
	 * Map of errors.
	 */
	Map<String, String> errors = new HashMap<>();
	
	/**
	 * Method gets error for the accepted name.
	 * 
	 * @param name name
	 * @return error for the accepted name
	 */
	public String getError(String name) {
		return errors.get(name);
	}
	
	/**
	 * Method checks are there errors
	 * 
	 * @return true if there are errors, false otherwise
	 */
	public boolean containsErrors() {
		return !errors.isEmpty();
	}
	
	/**
	 * Method checks if there is error for the accepted name.
	 * 
	 * @param name name
	 * @return true if there is error for the accepted name, false otherwise
	 */
	public boolean hasError(String name) {
		return errors.containsKey(name);
	}
	
	/**
	 * Method fills data from HttpRequest.
	 * 
	 * @param req
	 */
	public void fillFromHttpRequest(HttpServletRequest req) {
		this.id = prepareString(req.getParameter("id"));
		this.firstName = prepareString(req.getParameter("firstName"));
		this.lastName = prepareString(req.getParameter("lastName"));
		this.nick = prepareString(req.getParameter("nick"));
		this.email = prepareString(req.getParameter("email"));
		this.password = prepareString(req.getParameter("password"));
	}

	/**
	 * Fill data from {@link BlogUser}.
	 * 
	 * @param blogUser {@link BlogUser}
	 */
	public void fillFromBlogUser(BlogUser blogUser) {
		if(blogUser.getId() == null) {
			this.id = "";
		} else {
			this.id = blogUser.getId().toString();
		}
		
		this.firstName = blogUser.getFirstName();
		this.lastName = blogUser.getLastName();
		this.nick = blogUser.getNick();
		this.email = blogUser.getEmail();	
	}

	/**
	 * Fill {@link BlogUser}.
	 * 
	 * @param blogUser {@link BlogUser}.
	 */
	public void fillBlogUser(BlogUser blogUser) {
		if(this.id.isEmpty()) {
			blogUser.setId(null);
		} else {
			blogUser.setId(Long.valueOf(this.id));
		}
		
		blogUser.setFirstName(this.firstName);
		blogUser.setLastName(this.lastName);
		blogUser.setNick(this.nick);
		blogUser.setEmail(this.email);
		blogUser.setPasswordHash(Util.getPasswordHash(this.password));
	}
	
	/**
	 * Method checks if data is valid.
	 */
	public void validate() {
		errors.clear();
		
		if(!this.id.isEmpty()) {
			try {
				Long.parseLong(this.id);
			} catch(NumberFormatException ex) {
				errors.put("id", "Id is not valid.");
			}
		}
		
		if(this.firstName.isEmpty()) {
			errors.put("firstName", "First name is obligatory!");
		}
		
		if(this.lastName.isEmpty()) {
			errors.put("lastName", "Last name is obligatory!");
		}
		
		if(this.nick.isEmpty()) {
			errors.put("nick", "Nick is obligatory!");
		}
		
		if(DAOProvider.getDAO().doesNickAlreadyExist(nick)) {
			errors.put("nick", "Nick already exist!");
		}

		if(this.email.isEmpty()) {
			errors.put("email", "EMail is obligatory!");
		} else {
			int l = email.length();
			int p = email.indexOf('@');
			if(l<3 || p==-1 || p==0 || p==l-1) {
				errors.put("email", "EMail is not valid.");
			}
		}
		
		if(this.password.isEmpty()) {
			errors.put("password", "Password is obligatory!");
		}
	}
	
	/**
	 * Method prepares string.
	 * 
	 * @param s string
	 * @return prepared string
	 */
	private String prepareString(String s) {
		if(s==null) return "";
		return s.trim();
	}

	/**
	 * Getter for the id.
	 * 
	 * @return id
	 */
	public String getId() {
		return id;
	}

	/**
	 * Setter for the id.
	 * 
	 * @param id
	 */
	public void setId(String id) {
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
	 * Getter for the password.
	 * 
	 * @return password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * Setter for the password.
	 * 
	 * @param password password
	 */
	public void setPassword(String password) {
		this.password = password;
	}
}
