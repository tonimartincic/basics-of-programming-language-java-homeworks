package hr.fer.zemris.java.hw18.util;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.imageio.ImageIO;

import hr.fer.zemris.java.hw18.Picture;

/**
 * Class contain some helping methods.
 * 
 * @author Toni Martinčić
 * @version 1.0
 */
public class Util {
	
	/**
	 * All tags.
	 */
	private Set<String> allTags;
	
	/**
	 * All pictures.
	 */
	private Set<Picture> pictures;

	/**
	 * Constructor which accepts two arguments; all tags and all pictures.
	 * 
	 * @param allTags all tags
	 * @param pictures all pictures
	 */
	public Util(Set<String> allTags, Set<Picture> pictures) {
		if(allTags == null) {
			throw new IllegalArgumentException("Argument allTag can not be null");
		}
		
		if(pictures == null) {
			throw new IllegalArgumentException("Argument pictures can not be null");
		}
		
		this.allTags = allTags;
		this.pictures = pictures;
	}
	
	/**
	 * Method gets thumbnails for the accepted tag.
	 * 
	 * @param tagName tag
	 * @return thumbnails for the accepted tag
	 * @throws IOException if there is exception while reading or writing into the file
	 */
	public List<String> getThumbnailsPathsForTag(String tagName) throws IOException {
		List<String> thumbnailPaths = new ArrayList<>();
		
		for(Picture picture : pictures) {
			if(!picture.getTags().contains(tagName) && !tagName.equals("Prikaži sve")) {
				continue;
			}
			
			try {
				if(!Files.exists(Paths.get(picture.getThumbnailPath()))) {
					BufferedImage img = new BufferedImage(150, 150, BufferedImage.TYPE_INT_RGB);
					img.createGraphics().drawImage(ImageIO.read(new File(picture.getImagePath())).getScaledInstance(150, 150, Image.SCALE_SMOOTH),0,0,null);
					ImageIO.write(img, "jpg", new File(picture.getThumbnailPath()));
				} 
				
				thumbnailPaths.add(picture.getThumbnailPath());
			} catch(IOException exc) {
				throw new IOException("PATH " + picture.getImagePath());
			}
		}
		
		return thumbnailPaths;
	}
	
	/**
	 * Method gets instance of {@link Picture} for the accepted name.
	 * 
	 * @param pictureName picture name
	 * @return instance of {@link Picture} for the accepted name
	 */
	public Picture getPictureByName(String pictureName) {
		for(Picture picture : pictures) {
			if(picture.getName().substring(0, picture.getName().indexOf('.')).equals(pictureName)) {
				return picture;
			}
		}
		
		return null;
	}
	
	/**
	 * Getter for the all tags.
	 * 
	 * @return all tags
	 */
	public Set<String> getAllTags() {
		return allTags;
	}

	/**
	 * Setter for the all tags.
	 * 
	 * @param allTags
	 */
	public void setAllTags(Set<String> allTags) {
		this.allTags = allTags;
	}
	
	/**
	 * Getter for the all pictures.
	 * 
	 * @return all pictures
	 */
	public Set<Picture> getPictures() {
		return pictures;
	}
	
	/**
	 * Setter for the all pictures.
	 * 
	 * @param pictures all pictures
	 */
	public void setPictures(Set<Picture> pictures) {
		this.pictures = pictures;
	}

}
