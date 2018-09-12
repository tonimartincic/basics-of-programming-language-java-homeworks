package hr.fer.zemris.java.tecaj_13.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Cacheable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Class represents blog entry.
 * 
 * @author Toni Martinčić
 * @version 1.0
 */
@Entity
@Table(name="blog_entries")
@NamedQueries({
	@NamedQuery(name="BlogEntry.upit1",query="select b from BlogComment as b where b.blogEntry=:be and b.postedOn>:when")
})
@Cacheable(true)
public class BlogEntry {

	/**
	 * Id.
	 */
	private Long id;
	
	/**
	 * Comments.
	 */
	private List<BlogComment> comments = new ArrayList<>();
	
	/**
	 * When the entry is created.
	 */
	private Date createdAt;
	
	/**
	 * When the entry is last modified.
	 */
	private Date lastModifiedAt;
	
	/**
	 * Entry's title.
	 */
	private String title;
	
	/**
	 * Entry's text.
	 */
	private String text;
	
	/**
	 * Creator of the entry.
	 */
	private BlogUser creator;
	
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
	 * Getter for the comments.
	 * 
	 * @return comments.
	 */
	@OneToMany(mappedBy="blogEntry",fetch=FetchType.LAZY, cascade=CascadeType.PERSIST, orphanRemoval=true)
	@OrderBy("postedOn")
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
	 * Getter for the createdAt.
	 * 
	 * @return createdAt
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable=false)
	public Date getCreatedAt() {
		return createdAt;
	}

	/**
	 * Setter for the createdAt
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
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable=true)
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

	/**
	 * Getter for the title.
	 * 
	 * @return title
	 */
	@Column(length=200,nullable=false)
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
	@Column(length=4096,nullable=false)
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
	@ManyToOne
	@JoinColumn(nullable=false)
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
		BlogEntry other = (BlogEntry) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}