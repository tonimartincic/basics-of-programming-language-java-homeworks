package hr.fer.zemris.java.hw16;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import hr.fer.zemris.java.hw16.vectors.DocumentVector;

/**
 * Class contains all the words from all the files from the folder which path is accepted in the 
 * constructor of the class.
 * 
 * @author Toni Martinčić
 * @version 1.0
 */
public class Vocabulary {

	/**
	 * Path to the file with the stop words.
	 */
	private String stopWordsPath;
	
	/**
	 * Path to the directory with all the articles.
	 */
	private String articlesPath;
	
	/**
	 * Set which contains stop words.
	 */
	private Set<String> stopWords = new HashSet<>();
	
	/**
	 * Set which contains all the vocabulary words.
	 */
	private Set<String> vocabularyWords = new HashSet<>();

	/**
	 * Map which maps each word from the vocabulary to the instance of the {@link WordData}.
	 */
	private Map<String, WordData> wordDataMap = new HashMap<>();
	
	/**
	 * Number of documents in the accepted folder.
	 */
	private int numberOfDocuments;
	
	/**
	 * Set of document paths.
	 */
	private Set<Path> documents = new HashSet<>();
	
	/**
	 * List of {@link DocumentVector}.
	 */
	private List<DocumentVector> documentVectors = new ArrayList<>();
	
	/** 
	 * Constructor which accepts two arguments; articlesPath and stopWordsPath.
	 * 
	 * @param articlesPath path to the folder with articles
	 * @param stopWordsPath path to the file with the stop words
	 * @throws Exception if anything goes wrong in the initialize method
	 */
	public Vocabulary(String articlesPath, String stopWordsPath) throws Exception {
		this.articlesPath = articlesPath;
		this.stopWordsPath = stopWordsPath;
		
		initialize();
	}
	
	/**
	 * Method which initializes the vocabulary.
	 * 
	 * @throws Exception if anything goes wrong in the method
	 */
	private void initialize() throws Exception {
		stopWords.addAll(Files.readAllLines(Paths.get(stopWordsPath)));
		getVocabularyWords();
		createDocumentVectors();
	}
	
	/**
	 * Method gets all the vocabulary words.
	 * 
	 * @throws Exception if there is exception while walking the file tree
	 */
	private void getVocabularyWords() throws Exception {
		MyFileVisitor myFileVisitor = new MyFileVisitor();
		
		try {
			Files.walkFileTree(Paths.get(articlesPath), myFileVisitor);
		} catch (IOException e) {
			throw new Exception("Pogreška tijekom obilaženja datoteka.");
		}
	}
	
	/**
	 * Method returns number of the words in the vocabulary.
	 * 
	 * @return number of the words in the vocabulary
	 */
	public int vocabularySize() {
		return vocabularyWords.size();
	}
	
	/**
	 * Method checks if the vocabulary contain the accepted word.
	 * 
	 * @param word accepted word
	 * @return true if the vocabulary contain the accepted word, false otherwise
	 */
	public boolean contains(String word) {
		return vocabularyWords.contains(word);
	}
	
	/**
	 * Method creates all the instances of {@link DocumentVector}.
	 */
	private void createDocumentVectors() {
		for(Path document : documents) {
			double[] values = new double[vocabularyWords.size()];
			
			for(String word : vocabularyWords) {
				WordData wordData = wordDataMap.get(word);
				
				int index = wordData.getIndex();
				int tf = wordData.getNumOfWordsInDoc().get(document) == null ? 0 : wordData.getNumOfWordsInDoc().get(document);
				double idf = Math.log10(wordData.getDocsWhichContainThisWord().size() * 1.0 / numberOfDocuments);
				
				values[index] = tf * idf;
			}
			
			DocumentVector documentVector = new DocumentVector(document, values);
			documentVectors.add(documentVector);
		}	
	}

	/**
	 * Method gets set of the {@link ResultRecord} for the accepted words. As argument is accepted
	 * map which maps word to number of the appearances of that word.
	 * 
	 * @param words map which maps word to number of the appearances of that word
	 * @return set of the {@link ResultRecord} for the accepted words
	 */
	public Set<ResultRecord> getResultsForWords(Map<String, Integer> words) {
		DocumentVector documentVector = getDocumentVector(words);
		
		Set<ResultRecord> results = new TreeSet<>();
		for(DocumentVector docVector : documentVectors) {
			double similiarity = docVector.calculateSimilarity(documentVector);
			
			ResultRecord resultRecord = new ResultRecord(similiarity, docVector.getPath());
			results.add(resultRecord);
		}
		
		Set<ResultRecord> firstTen = new TreeSet<>();
		int i = 0;
		for(ResultRecord resultRecord : results) {
			if(resultRecord.getSimilarity() > 0) {
				firstTen.add(resultRecord);
				i++;
			}
			
			if(i == 10) {
				break;
			}
		}
		
		return firstTen;
	}
	
	/**
	 * Method gets {@link DocumentVector} for the accepted words. As argument is accepted
	 * map which maps word to number of the appearances of that word.
	 * 
	 * @param words map which maps word to number of the appearances of that word
	 * @return instance of {@link DocumentVector}
	 */
	private DocumentVector getDocumentVector(Map<String, Integer> words) {
		double[] values = new double[vocabularyWords.size()];
		for(Map.Entry<String, Integer> entry : words.entrySet()) {
			WordData wordData = wordDataMap.get(entry.getKey());
			
			int index = wordData.getIndex();
			int tf = entry.getValue();
			double idf = Math.log10(wordData.getDocsWhichContainThisWord().size() * 1.0 / numberOfDocuments);
			
			values[index] = tf * idf;
		}
		
		return new DocumentVector(null, values);
	}
	
	/**
	 * Class extends {@link SimpleFileVisitor} and represents visitor which from each visited file gets
	 * all the words and adds them into the vocabulary.
	 * 
	 * @author Toni Martinčić
	 * @version 1.0
	 */
	private class MyFileVisitor extends SimpleFileVisitor<Path> {
		
		/**
		 * Current index of the words in the {@link DocumentVector}.
		 */
		private int currentIndex;
			
		@Override
		public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {	
			if(!Files.isReadable(file)) {
				return FileVisitResult.CONTINUE;
			}
			
			numberOfDocuments++;
			documents.add(file);
			
			List<String> allLines = Files.readAllLines(file);
			
			for(String line : allLines) {
				Pattern p = Pattern.compile("[a-zA-ZšđžčćŠĐŽČĆ]+");
				Matcher m = p.matcher(line);
				
				while(m.find()) {
					String word = m.group().toLowerCase();
					
					if(stopWords.contains(word)) {
						continue;
					}
					
					if(vocabularyWords.add(word)) {
						wordDataMap.put(word, new WordData(currentIndex, file));
						
						currentIndex++;
						continue;
					} 
					
					wordDataMap.get(word).getDocsWhichContainThisWord().add(file);
					wordDataMap.get(word).getNumOfWordsInDoc().merge(file, 1, Integer::sum);
				}
			}
			
			return FileVisitResult.CONTINUE;
		}	
		
	}
	
}
