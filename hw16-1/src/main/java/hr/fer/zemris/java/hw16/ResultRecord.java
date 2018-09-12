package hr.fer.zemris.java.hw16;

import java.nio.file.Path;

/**
 * Class represents one record of result records of query command.
 * 
 * @author Toni Martinčić
 * @version 1.0
 */
public class ResultRecord implements Comparable<ResultRecord> {

	/**
	 * Similarity.
	 */
	private double similarity;
	
	/**
	 * Document path.
	 */
	private Path path;

	/**
	 * Constructor which accepts two arguments; similarity and path.
	 * 
	 * @param similarity similarity
	 * @param path document path
	 */
	public ResultRecord(double similarity, Path path) {
		if(similarity < 0.0 || similarity > 1.0) {
			throw new IllegalArgumentException("Similarity must be between 0 and 1.");
		}
		
		if(path == null) {
			throw new IllegalArgumentException("Path can not be null");
		}
		
		this.similarity = similarity;
		this.path = path;
	}

	/**
	 * Getter for the similarity.
	 * 
	 * @return similarity
	 */ 
	public double getSimilarity() {
		return similarity;
	}

	/**
	 * Setter for the similarity.
	 * 
	 * @param similarity similarity
	 */
	public void setSimilarity(double similarity) {
		this.similarity = similarity;
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
	 * @param path path
	 */
	public void setPath(Path path) {
		this.path = path;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		
		sb.append("(").append(String.format("%.4f", similarity)).append(") ");
		sb.append(path.toAbsolutePath());
		
		return sb.toString();
	}

	@Override
	public int compareTo(ResultRecord o) {
		Double thisValue = similarity;
		Double otherValue = o.getSimilarity();
		
		return otherValue.compareTo(thisValue);
	}
	
}
