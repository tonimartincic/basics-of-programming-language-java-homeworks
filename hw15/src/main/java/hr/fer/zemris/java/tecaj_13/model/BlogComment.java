package hr.fer.zemris.java.tecaj_13.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Class represents blog comment.
 * 
 * @author Toni Martinčić
 * @version 1.0
 */
@Entity
@Table(name="blog_comments")
public class BlogComment {

	/**
	 * Blog comment id.
	 */
	private Long id;
	
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
	 * @param id id
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Getter for the {@link BlogEntry}.
	 * 
	 * @return {@link BlogEntry}
	 */
	@ManyToOne
	@JoinColumn(nullable=false)
	public BlogEntry getBlogEntry() {
		return blogEntry;
	}
	
	/**
	 * Setter for the {@link BlogEntry}
	 * 
	 * @param blogEntry {@link BlogEntry}
	 */
	public void setBlogEntry(BlogEntry blogEntry) {
		this.blogEntry = blogEntry;
	}

	/**
	 * Getter for the user's email.
	 * 
	 * @return user's email
	 */
	@Column(length=100,nullable=false)
	public String getUsersEMail() {
		return usersEMail;
	}

	/**
	 * Setter for the user's email.
	 * 
	 * @param usersEMail user's email
	 */
	public void setUsersEMail(String usersEMail) {
		this.usersEMail = usersEMail;
	}

	/**
	 * Getter for the message.
	 * 
	 * @return message
	 */
	@Column(length=4096,nullable=false)
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
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable=false)
	public Date getPostedOn() {
		return postedOn;
	}

	/**
	 * Setter for the postedOn.
	 * 
	 * @param postedOn when the comment is posted.
	 */
	public void setPostedOn(Date postedOn) {
		this.postedOn = postedOn;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		BlogComment other = (BlogComment) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}