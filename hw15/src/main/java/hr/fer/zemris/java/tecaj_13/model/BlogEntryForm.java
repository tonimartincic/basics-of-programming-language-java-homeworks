package hr.fer.zemris.java.tecaj_13.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

/**
 * Class represents form for the {@link BlogEntry}.
 * 
 * @author Toni Martinčić
 * @version 1.0
 */
public class BlogEntryForm {
	
	/**
	 * Id.
	 */
	private String id;
	
	/**
	 * Comments.
	 */
	private List<BlogComment> comments = new ArrayList<>();
	
	/**
	 * When the {@link BlogEntry} is created.
	 */
	private Date createdAt;
	
	/**
	 * When the {@link BlogEntry} is last modified.
	 */
	private Date lastModifiedAt;
	
	/**
	 * Title.
	 */
	private String title;
	
	/**
	 * Text.
	 */
	private String text;
	
	/**
	 * Creator of the {@link BlogEntry}.
	 */
	private BlogUser creator;

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
		this.title = prepareString(req.getParameter("title"));
		this.text = prepareString(req.getParameter("text"));
	}
	
	/**
	 * Method fills {@link BlogEntry} with data.
	 * 
	 * @param blogEntry {@link BlogEntry}
	 */
	public void fillBlogEntry(BlogEntry blogEntry) {
		if(this.id == null || this.id.isEmpty()) {
			blogEntry.setId(null);
		} else {
			blogEntry.setId(Long.valueOf(this.id));
		}
		blogEntry.setComments(this.comments);
		blogEntry.setCreatedAt(this.createdAt);
		blogEntry.setLastModifiedAt(lastModifiedAt);
		blogEntry.setTitle(this.title);
		blogEntry.setText(this.text);
		blogEntry.setCreator(creator);
	}
	
	/**
	 * Method gets data from {@link BlogEntry}.
	 * 
	 * @param blogEntry
	 */
	public void fillFromBlogEntry(BlogEntry blogEntry) {
		this.id = blogEntry.getId().toString();
		this.comments = blogEntry.getComments();
		this.createdAt = blogEntry.getCreatedAt();
		this.lastModifiedAt = blogEntry.getLastModifiedAt();
		this.title = blogEntry.getTitle();
		this.text = blogEntry.getText();
		this.creator = blogEntry.getCreator();
	}

	/**
	 * Method checks if data is valid.
	 */
	public void validate() {
		errors.clear();
		
		if(this.title.isEmpty()) {
			errors.put("title", "Title is obligatory!");
		} 

		if(this.text.isEmpty()) {
			errors.put("text", "Text is obligatory!");
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
	 * Getter for the comments.
	 * 
	 * @return comments
	 */
	public List<BlogComment> getComments() {
		return comments;
	}

	/**
	 * Setter for the comments.
	 * 
	 * @param comments comments
	 */
	public void setComments(List<BlogComment> comments) {
		this.comments = comments;
	}

	/**
	 * Getter for the title.
	 * 
	 * @return title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Setter for the title.
	 * 
	 * @param title title
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * Getter for the text.
	 * 
	 * @return text
	 */
	public String getText() {
		return text;
	}

	/**
	 * Setter for the text.
	 * 
	 * @param text text
	 */
	public void setText(String text) {
		this.text = text;
	}

	/**
	 * Getter for the creator.
	 * 
	 * @return creator
	 */
	public BlogUser getCreator() {
		return creator;
	}

	/**
	 * Setter for the creator.
	 * 
	 * @param creator creator
	 */
	public void setCreator(BlogUser creator) {
		this.creator = creator;
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
	 * Getter for the createdAt.
	 * 
	 * @return createdAt
	 */
	public Date getCreatedAt() {
		return createdAt;
	}

	/**
	 * Setter for the createdAt.
	 * 
	 * @param createdAt createdAt
	 */
	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	/**
	 * Getter for the lastModifiedAt.
	 * 
	 * @return lastModifiedAt
	 */
	public Date getLastModifiedAt() {
		return lastModifiedAt;
	}

	/**
	 * Setter for the lastModifiedAt.
	 * 
	 * @param lastModifiedAt lastModifiedAt
	 */
	public void setLastModifiedAt(Date lastModifiedAt) {
		this.lastModifiedAt = lastModifiedAt;
	}	
}
