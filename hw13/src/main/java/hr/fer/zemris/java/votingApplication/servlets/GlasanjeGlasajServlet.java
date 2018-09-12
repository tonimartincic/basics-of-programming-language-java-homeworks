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
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Class extends {@link HttpServlet} and represents servlet which gets one vote and updates the
 * voting results.
 * 
 * @author Toni Martinčić
 * @version 1.0
 */
@WebServlet(name="gg", urlPatterns={"/glasanje-glasaj"})
public class GlasanjeGlasajServlet extends HttpServlet {
	
	/**
	 * Universal version identifier for a {@link Serializable} class.
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String id = req.getParameter("id");
		
		String resultFileName = req.getServletContext().getRealPath("/WEB-INF/glasanje-rezultati.txt");
		File results = new File(resultFileName);
		
		String definitionFileName = req.getServletContext().getRealPath("/WEB-INF/glasanje-definicija.txt");
		List<String> lines = Files.readAllLines(Paths.get(definitionFileName));
		
		if(!Files.exists(Paths.get(resultFileName))) {
			writeDataIntoResults(id, results, lines);	
		} else {
			List<String> resultLines = getResultLines(resultFileName, results, lines);
			
			updateResults(id, results, resultLines);
		}
		
		resp.sendRedirect(req.getContextPath() + "/glasanje-rezultati");
	}

	/**
	 * Method updates the result file.
	 * 
	 * @param id id of the band
	 * @param results result file
	 * @param resultLines all lines of the result file
	 * @throws FileNotFoundException if file is not found
	 * @throws IOException if there is error while writing into the file
	 */
	private void updateResults(String id, File results, List<String> resultLines)
			throws FileNotFoundException, IOException {
		
		FileOutputStream fos = new FileOutputStream(results);
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));
		for(String line : resultLines) {
			String[] parts = line.split("\\t");
			
			if(id.equals(parts[0])) {
				Integer value = Integer.valueOf(parts[1]);
				value++;
				
				line = parts[0] + "\t" + value;
			}
			
			bw.write(line);
			bw.newLine();
		}
		bw.flush();
		bw.close();
	}

	/**
	 * Method writes data into the result file.
	 * 
	 * @param id id of the band
	 * @param results result file
	 * @param lines data
	 * @throws FileNotFoundException if file is not found
	 * @throws IOException if there is error while writing into the file
	 */
	private void writeDataIntoResults(String id, File results, List<String> lines)
			throws FileNotFoundException, IOException {
		
		FileOutputStream fos = new FileOutputStream(results);
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));
		
		for(String line : lines) {
			String[] parts = line.split("\\t");
			
			String resultLine = parts[0] + "\t" + (id.equals(parts[0]) ? "1" : "0");
			
			bw.write(resultLine);
			bw.newLine();
		}
		bw.flush();
		bw.close();
	}

	/**
	 * Method gets all lines from result file.
	 * 
	 * @param resultFileName name of the result file
	 * @param results result file
	 * @param lines data
	 * @return list of lines of the result file
	 * @throws IOException if there is error while reading from the file
	 * @throws FileNotFoundException if file is not found
	 */
	private List<String> getResultLines(String resultFileName, File results, List<String> lines)
			throws IOException, FileNotFoundException {
		
		List<String> resultLines = Files.readAllLines(Paths.get(resultFileName));
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
