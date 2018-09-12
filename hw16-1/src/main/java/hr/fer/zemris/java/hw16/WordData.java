package hr.fer.zemris.java.hw16;

import java.nio.file.Path;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import hr.fer.zemris.java.hw16.vectors.DocumentVector;

/**
 * Class contains data for the one word from vocabulary.
 * 
 * @author Toni Martinčić
 * @version 1.0
 */
public class WordData {
	
	/**
	 * Index of word in {@link DocumentVector}.
	 */
	private int index;
	
	/**
	 * Set of documents which contain the word.
	 */
	private Set<Path> docsWhichContainThisWord = new HashSet<>();
	
	/**
	 * Map which maps document to the number of instances of word in document.
	 */
	private Map<Path, Integer> numOfWordsInDoc = new HashMap<>();

	/**
	 * Constructor which accepts two arguments; word index in {@link DocumentVector} and document path.
	 * 
	 * @param index index in {@link DocumentVector}
	 * @param path document path
	 */
	public WordData(int index, Path path) {
		this.index = index;
		docsWhichContainThisWord.add(path);
		numOfWordsInDoc.put(path, 1);
	}

	/**
	 * Getter for the index.
	 * 
	 * @return index
	 */
	public int getIndex() {
		return index;
	}

	/**
	 * Setter for the index.
	 * 
	 * @param index index
	 */
	public void setIndex(int index) {
		this.index = index;
	}

	/**
	 * Method returns set of documents which contain word.
	 * 
	 * @return set of documents which contain word
	 */
	public Set<Path> getDocsWhichContainThisWord() {
		return docsWhichContainThisWord;
	}

	/**
	 * Method sets set of documents which contain word.
	 * 
	 * @param docsWhichContainThisWord set of documents which contain word
	 */ 
	public void setDocsWhichContainThisWord(Set<Path> docsWhichContainThisWord) {
		this.docsWhichContainThisWord = docsWhichContainThisWord;
	}

	/**
	 * Method returns map which maps document to the number of instances of word in that document.
	 * 
	 * @return map which maps document to the number of instances of word in that document
	 */
	public Map<Path, Integer> getNumOfWordsInDoc() {
		return numOfWordsInDoc;
	}

	/**
	 * Method sets map which maps document to the number of instances of word in that document.
	 * 
	 * @param numOfWordsInDoc map which maps document to the number of instances of word in that document
	 */
	public void setNumOfWordsInDoc(Map<Path, Integer> numOfWordsInDoc) {
		this.numOfWordsInDoc = numOfWordsInDoc;
	}
	
}
