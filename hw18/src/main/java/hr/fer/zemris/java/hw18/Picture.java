package hr.fer.zemris.java.hw18;

import java.util.Set;

/**
 * Class represents picture and contains all the data for the picture. It contains picture name,
 * picture description and the picture tags.
 * 
 * @author Toni Martinčić
 * @version 1.0
 */
public class Picture {
	
	/**
	 * Path to the directory with the images.
	 */
	private static final String IMAGE_PATH = "src/main/webapp/WEB-INF/slike";
	
	/**
	 * Path to the directory with the thumbnails.
	 */
	private static final String THUMBNAILS_PATH = "src/main/webapp/WEB-INF/thumbnails";
	
	/**
	 * Relative path.
	 */
	private static final String RELATIVE_PATH = "WEB-INF/thumbnails";
	
	/**
	 * Picture name.
	 */
	private String name;
	
	/**
	 * Picture description.
	 */
	private String description;
	
	/**
	 * Picture tags.
	 */
	private Set<String> tags;
	
	/**
	 * Image path.
	 */
	private String imagePath;
	
	/**
	 * Thumbnail path.
	 */
	private String thumbnailPath;
	
	/**
	 * Relative path.
	 */
    private String relativePath;

    /**
     * Constructor which accepts three arguments; name, description and tags.
     * 
     * @param name picture name
     * @param description picture description
     * @param tags picture tags
     * @throws IllegalArgumentException if the name is null or empty, description is null or empty
     * tags are null or empty
     */
	public Picture(String name, String description, Set<String> tags) {
		if(name == null || name.isEmpty()) {
			throw new IllegalArgumentException("Name can not be null or empty string.");
		}
		
		if(description == null || description.isEmpty()) {
			throw new IllegalArgumentException("Description can not be null or empty string.");
		}
		
		if(tags == null || tags.isEmpty()) {
			throw new IllegalArgumentException("Tags can not be null or empty.");
		}
		
		this.name = name;
		this.description = description;
		this.tags = tags;
		
		this.imagePath = IMAGE_PATH + "/" + name;
		this.thumbnailPath = THUMBNAILS_PATH + "/" + name;
		this.relativePath = RELATIVE_PATH + "/" + name;
	}

	/**
	 * Getter for the name.
	 * 
	 * @return name
	 */ 
	public String getName() {
		return name;
	}

	/**
	 * Setter for the name.
	 * 
	 * @param name picture name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Getter for the description.
	 * 
	 * @return description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Setter for the description.
	 * 
	 * @param description
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * Getter for the tags.
	 * 
	 * @return tags
	 */
	public Set<String> getTags() {
		return tags;
	}

	/**
	 * Setter for the tags.
	 * 
	 * @param tags picture tags
	 */
	public void setTags(Set<String> tags) {
		this.tags = tags;
	}
	
	/**
	 * Getter for the image path.
	 * 
	 * @return image path
	 */
	public String getImagePath() {
		return imagePath;
	}
	
	/**
	 * Setter for the image path.
	 * 
	 * @param imagePath image path
	 */ 
	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}
	
	/**
	 * Getter for the thumbnail path.
	 * 
	 * @return thumbnail path
	 */
	public String getThumbnailPath() {
		return thumbnailPath;
	}
	
	/**
	 * Setter for the thumbnail path.
	 * 
	 * @param thumbnailPath thumbnail path
	 */
	public void setThumbnailPath(String thumbnailPath) {
		this.thumbnailPath = thumbnailPath;
	}
	
	/**
	 * Getter for the relative path.
	 * 
	 * @return relative path
	 */
	public String getRelativePath() {
		return relativePath;
	}
	
	/**
	 * Setter for the relative path
	 * 
	 * @param relativePath relative path
	 */
	public void setRelativePath(String relativePath) {
		this.relativePath = relativePath;
	}

}
