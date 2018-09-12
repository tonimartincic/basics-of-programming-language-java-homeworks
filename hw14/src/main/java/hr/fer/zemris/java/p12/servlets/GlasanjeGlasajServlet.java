package hr.fer.zemris.java.p12.servlets;

import java.io.IOException;
import java.io.Serializable;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.p12.dao.DAOProvider;

/**
 * Class extends {@link HttpServlet} and represents servlet which gets one vote and updates the
 * voting results.
 * 
 * @author Toni Martinčić
 * @version 1.0
 */
@WebServlet(name="gg", urlPatterns={"/servleti/glasanje-glasaj"})
public class GlasanjeGlasajServlet extends HttpServlet {
	
	/**
	 * Universal version identifier for a {@link Serializable} class.
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		long id = Long.parseLong(req.getParameter("id"));
		long pollID = Long.parseLong(req.getParameter("pollID"));
		
		DAOProvider.getDao().addVote(pollID, id);
		
		resp.sendRedirect(req.getContextPath() + "/servleti/glasanje-rezultati?pollID=" + pollID);
	}
}
