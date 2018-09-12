package hr.fer.zemris.java.hw16.vectors;

import java.nio.file.Path;

/**
 * Class extends {@link Vector} and represents document vector which contains document path.
 * 
 * @author Toni Martinčić
 * @version 1.0
 */
public class DocumentVector extends Vector {
	
	/**
	 * Document path.
	 */
	private Path path;
	
	/**
	 * Constructor which accepts two arguments; path and values.
	 * 
	 * @param path document path
	 * @param values vector components
	 */
	public DocumentVector(Path path, double[] values) {
		super(values);
		this.path = path;
	}
	
	/**
	 * Getter for the path.
	 * 
	 * @return path
	 */
	public Path getPath() {
		return path;
	}

	/**
	 * Setter for the path.
	 * 
	 * @param path document path
	 */
	public void setPath(Path path) {
		this.path = path;
	}
	
	/**
	 * Method returns similarity between this document vector and document vector accepted
	 * as argument.
	 * 
	 * @param other other documnent vector
	 * @return similarity between this document vector and document vector accepted as argument
	 */
	public double calculateSimilarity(DocumentVector other) {
		return this.cos(other);
	}
	
}
