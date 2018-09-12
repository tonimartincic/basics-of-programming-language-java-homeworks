package hr.fer.zemris.java.tecaj_13.model;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

/**
 * Class represents form for the {@link BlogComment}.
 * 
 * @author Toni Martinčić
 * @version 1.0
 */
public class BlogCommentForm {
	
	/**
	 * Blog comment id.
	 */
	private String id;
	
	/**
	 * {@link BlogEntry} of blog comment.
	 */
	private BlogEntry blogEntry;
	
	/**
	 * Mail of the user which posted this comment.
	 */
	private String usersEMail;
	
	/**
	 * Comment message.
	 */
	private String message;
	
	/**
	 * When the comment is posted.
	 */
	private Date postedOn;

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
		this.message = prepareString(req.getParameter("message"));
	}
	
	/**
	 * Method fills {@link BlogComment}.
	 * 
	 * @param blogComment {@link BlogComment}
	 */
	public void fillBlogComment(BlogComment blogComment) {
		if(this.id == null || this.id.isEmpty()) {
			blogComment.setId(null);
		} else {
			blogComment.setId(Long.valueOf(this.id));
		}
		blogComment.setBlogEntry(blogEntry);
		blogComment.setMessage(message);
		blogComment.setPostedOn(postedOn);
		blogComment.setUsersEMail(usersEMail);
	}
	
	/**
	 * Method gets data from {@link BlogComment}.
	 * 
	 * @param blogComment {@link BlogComment}
	 */
	public void fillFromBlogComment(BlogComment blogComment) {
		this.id = blogComment.getId().toString();
		this.blogEntry = blogComment.getBlogEntry();
		this.message = blogComment.getMessage();
		this.postedOn = blogComment.getPostedOn();
		this.usersEMail = blogComment.getUsersEMail();
	}

	/**
	 * Method checks if data is valid.
	 */
	public void validate() {
		errors.clear();
		
		if(this.message.isEmpty()) {
			errors.put("message", "Message is obligatory!");
		} 
	}
	
	/**
	 * Method prepares string
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
	 * @param id id
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * Getter for the {@link BlogEntry}.
	 * 
	 * @return {@link BlogEntry}
	 */
	public BlogEntry getBlogEntry() {
		return blogEntry;
	}
	
	/**
	 * Setter for the {@link BlogEntry}.
	 * 
	 * @param blogEntry {@link BlogEntry}
	 */
	public void setBlogEntry(BlogEntry blogEntry) {
		this.blogEntry = blogEntry;
	}

	/**
	 * Getter for the email.
	 * 
	 * @return email
	 */
	public String getUsersEMail() {
		return usersEMail;
	}

	/**
	 * Setter for the email.
	 * 
	 * @param usersEMail email
	 */
	public void setUsersEMail(String usersEMail) {
		this.usersEMail = usersEMail;
	}

	/**
	 * Getter for the message.
	 * 
	 * @return message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * Setter for the message.
	 * 
	 * @param message message
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * Getter for the postedOn.
	 * 
	 * @return postedOn
	 */
	public Date getPostedOn() {
		return postedOn;
	}

	/**
	 * Setter for the postedOn.
	 * 
	 * @param postedOn postedOn
	 */
	public void setPostedOn(Date postedOn) {
		this.postedOn = postedOn;
	}
}
