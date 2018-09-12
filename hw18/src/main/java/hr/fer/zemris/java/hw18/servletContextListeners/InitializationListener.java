package hr.fer.zemris.java.hw18.servletContextListeners;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import hr.fer.zemris.java.hw18.Picture;
import hr.fer.zemris.java.hw18.util.Util;

/**
 * Class implements {@link ServletContextListener} and represents listener which gets all the tags
 * and all the pictures. It creates instance of the class {@link Util}.
 * 
 * @author Toni Martinčić
 * @version 1.0
 */
@WebListener
public class InitializationListener implements ServletContextListener {
	
	/**
	 * Path to the file with descriptions.
	 */
	private static final String DESCRIPTION_PATH = "src/main/webapp/WEB-INF/opisnik.txt";
	
	/**
	 * All tags.
	 */
	private Set<String> allTags;

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		try {
			allTags = getAllTags();
		} catch (IOException ignorable) {
			allTags = new HashSet<>();
		}
		
		Set<Picture> pictures;
		try {
			pictures = getPictures();
		} catch (IOException ignorable) {
			pictures = new HashSet<>();
		}
		
		Util util = new Util(allTags, pictures); 
		
		arg0.getServletContext().setAttribute("util", util);
	}
	
	/**
	 * Method gets all tags.
	 *  
	 * @return all tags
	 * @throws IOException if there is exception while reading from the file
	 */
	public Set<String> getAllTags() throws IOException {
		Set<String> set = new LinkedHashSet<>();

		set.add("Prikaži sve");
		
		List<String> allLines = Files.readAllLines(Paths.get(DESCRIPTION_PATH));
		for(int i = 1, n = allLines.size(); i <= n; i++) {
			if(i % 3 != 0) {
				continue;
			}
			
			String[] tags = allLines.get(i - 1).split(",");
			for(String tag : tags) {
				set.add(tag.trim());
			}
		}
		
		return set;
	}
	
	/**
	 * Method gets instance of the class {@link Picture} for every picture.
	 * 
	 * @return set of {@link Picture}
	 * @throws IOException if there is exception while reading from the file
	 */
	public Set<Picture> getPictures() throws IOException {
		Set<Picture> pictures = new HashSet<>();
		
		List<String> allLines = Files.readAllLines(Paths.get(DESCRIPTION_PATH));
		
		String name = null;
		String description = null;
		Set<String> tags = new HashSet<>();
		
		for(int i = 1, n = allLines.size(); i <= n; i++) {
			if(i % 3 == 1) {
				name = allLines.get(i - 1);	
			} else if(i % 3 == 2) {
				description = allLines.get(i - 1);
			} else {
				String[] tagsArray = allLines.get(i - 1).split(",");
				for(String tag : tagsArray) {
					tag = tag.trim();
					tags.add(tag);
				}
				
				Picture picture = new Picture(name, description, tags);
				pictures.add(picture);
				
				name = null;
				description = null;
				tags = new HashSet<>();
			}
		}
		
		return pictures;
	}
	
}
