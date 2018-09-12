package hr.fer.zemris.java.tecaj_13.model;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import hr.fer.zemris.java.tecaj_13.dao.DAOProvider;
import hr.fer.zemris.java.tecaj_13.utils.Util;

/**
 * Class represent form for the nick and password.
 * 
 * @author Toni Martinčić
 * @version 1.0
 */
public class NickAndPasswordForm {
	
	/**
	 * Nick.
	 */
	private String nick;
	
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
		this.nick = prepareString(req.getParameter("nick"));
		this.password = prepareString(req.getParameter("password"));
	}

	/**
	 * Method checks if data is valid.
	 */
	public void validate() {
		errors.clear();
		
		if(this.nick.isEmpty()) {
			errors.put("nick", "Nick is obligatory!");
		} else if(!DAOProvider.getDAO().doesNickAlreadyExist(nick)) {
			errors.put("nick", "Nick does not exist!");
		}

		if(this.password.isEmpty()) {
			errors.put("password", "Password is obligatory!");
		}
		
		if(errors.isEmpty()) {
			checkNickAndPassword();
		}
	}
	
	/**
	 * Method checks if nick and password are valid.
	 */
	private void checkNickAndPassword() {
		String passwordHash = Util.getPasswordHash(password);
		String passwordHashForNick = DAOProvider.getDAO().getPasswordHashByNick(nick);
		
		if(!passwordHash.equals(passwordHashForNick)) {
			password = null;
			
			errors.put("password", "Invalid password!");
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
