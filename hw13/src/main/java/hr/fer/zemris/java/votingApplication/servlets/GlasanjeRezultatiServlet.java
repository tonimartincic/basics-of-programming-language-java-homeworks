package hr.fer.zemris.java.votingApplication.servlets;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Class extends {@link HttpServlet} and represents servlet
 * 
 * @author Toni Martinčić
 * @version 1.0
 */
@WebServlet(name="gr", urlPatterns={"/glasanje-rezultati"})
public class GlasanjeRezultatiServlet extends HttpServlet {

	/**
	 * Universal version identifier for a {@link Serializable} class.
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String resultFileName = req.getServletContext().getRealPath("/WEB-INF/glasanje-rezultati.txt");
		File results = new File(resultFileName);
		
		String definitionFileName = req.getServletContext().getRealPath("/WEB-INF/glasanje-definicija.txt");
		List<String> lines = Files.readAllLines(Paths.get(definitionFileName));
		
		if(!Files.exists(Paths.get(resultFileName))) {
			writeLinesToResults(results, lines);
		}
		
		List<String> resultLines = getResultLines(results, lines);
		
		Map<String, Integer> idNumOfVotes = createMapFromResultLines(resultLines);
		Map<String, Integer> idNumOfVotesSorted = getSortedMap(idNumOfVotes);
		Map<String, String> idName = createMapFromLines(lines);
		Map<String, String> bandSong = getBandSongMap(lines, idNumOfVotes);
		
		req.getSession().setAttribute("idNumOfVotesSorted", idNumOfVotesSorted);
		req.getSession().setAttribute("idName", idName);
		req.getSession().setAttribute("bandSong", bandSong);
		
		req.getRequestDispatcher("/WEB-INF/pages/glasanjeRez.jsp").forward(req, resp);
	}

	/**
	 * Method returns map which maps band names to it songs.
	 * 
	 * @param lines all lines from the file which contains data for the bands
	 * @param idNumOfVotes map which maps ids of the bands to the number of it votes
	 * @return map which maps band names to it songs
	 */
	private Map<String, String> getBandSongMap(List<String> lines, Map<String, Integer> idNumOfVotes) {
		Map<String, String> bandSong= new HashMap<>();
		
		int max = 0;
		for(Map.Entry<String, Integer> entry : idNumOfVotes.entrySet()) {
			if(entry.getValue() > max) {
				max = entry.getValue();
			}
		}
		
		for(String line : lines) {
			String[] parts = line.split("\\t");
			
			if(idNumOfVotes.get(parts[0]) == max) {
				bandSong.put(parts[1], parts[2]);
			}
		}
		
		return bandSong;
	}

	/**
	 * Method returns map which maps ids of the bands to it names.
	 * 
	 * @param lines all lines from the file which contains data for the bands
	 * @return map which maps ids of the bands to it names
	 */
	private Map<String, String> createMapFromLines(List<String> lines) {
		Map<String, String> idName = new LinkedHashMap<>();
		
		for(String line : lines) {
			String[] parts = line.split("\\t");
			
			idName.put(parts[0], parts[1]);
		}
		
		return idName;
	}

	/**
	 * Method accepts map and returns map with the same data as accepted map but sorted by values.
	 * 
	 * @param idNumOfVotes unsorted map
	 * @return sorted map
	 */
	private Map<String, Integer> getSortedMap(Map<String, Integer> idNumOfVotes) {
		Map<String, Integer> idNumOfVotesSorted = new LinkedHashMap<>();
		idNumOfVotes.entrySet()
					.stream()
					.sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
		  			.forEach(e -> idNumOfVotesSorted.put(e.getKey(), e.getValue()));
		return idNumOfVotesSorted;
	}

	/**
	 * Method creates map which maps ids of the bands to the number of it votes.
	 * 
	 * @param resultLines all lines of the result file
	 * @return map which maps ids of the bands to the number of it votes
	 */
	private Map<String, Integer> createMapFromResultLines(List<String> resultLines) {
		Map<String, Integer> idNumOfVotes = new HashMap<>();
		for(String line : resultLines) {
			String[] parts = line.split("\\t");
			
			idNumOfVotes.put(parts[0], Integer.valueOf(parts[1]));
		}
		return idNumOfVotes;
	}

	/**
	 * Method gets all lines from result file.
	 * 
	 * @param results result file
	 * @param lines data
	 * @return list of lines of the result file
	 * @throws IOException if there is error while reading from the file
	 * @throws FileNotFoundException if file is not found
	 */
	private List<String> getResultLines(File results, List<String> lines) 
			throws IOException, FileNotFoundException {
		
		List<String> resultLines = Files.readAllLines(results.toPath());
		if(resultLines.isEmpty() || resultLines.size() == 1 && resultLines.get(0).equals("")) {
			writeLinesToResults(results, lines);
		}
		resultLines = Files.readAllLines(results.toPath());
		return resultLines;
	}

	/**
	 * Method writes all accepted lines into the result file.
	 * 
	 * @param results result file
	 * @param lines data
	 * @throws FileNotFoundException if file is not found
	 * @throws IOException if there is error while writing into the file
	 */
	private void writeLinesToResults(File results, List<String> lines) throws FileNotFoundException, IOException {
		FileOutputStream fos = new FileOutputStream(results);
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));
		
		for(String line : lines) {
			String[] parts = line.split("\\t");
			
			String resultLine = parts[0] + "\t" + "0";
			
			bw.write(resultLine);
			bw.newLine();
		}
		bw.flush();
		bw.close();
	}	
}
