package hr.fer.zemris.java.votingApplication.servlets;

import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Paths;
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
@WebServlet(name="gs", urlPatterns={"/glasanje"})
public class GlasanjeServlet extends HttpServlet {
	
	/**
	 * Universal version identifier for a {@link Serializable} class.
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String fileName = req.getServletContext().getRealPath("/WEB-INF/glasanje-definicija.txt");
		List<String> lines = Files.readAllLines(Paths.get(fileName));
		
		Map<Integer, String> bands = new LinkedHashMap<>();
		for(String line : lines) {
			String[] parts = line.split("\\t");
			
			bands.put(Integer.parseInt(parts[0]), parts[1]);
		}
		
		req.setAttribute("bands", bands);
		
		req.getRequestDispatcher("/WEB-INF/pages/glasanjeIndex.jsp").forward(req, resp);
	}
}
