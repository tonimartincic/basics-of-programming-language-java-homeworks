package hr.fer.zemris.java.p12.servlets;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.p12.dao.DAOProvider;
import hr.fer.zemris.java.p12.model.Poll;
import hr.fer.zemris.java.p12.model.PollOption;

/**
 * Class extends {@link HttpServlet} and represents servlet which gets informations for the poll and
 * calls jsp which render poll options for the poll.
 * 
 * @author Toni Martinčić
 * @version 1.0
 */
@WebServlet(name="gs", urlPatterns={"/servleti/glasanje"})
public class GlasanjeServlet extends HttpServlet {
	
	/**
	 * Universal version identifier for a {@link Serializable} class.
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		long pollID = Long.parseLong(req.getParameter("pollID"));
		
		Poll poll = DAOProvider.getDao().getPoll(pollID);
		req.getSession().setAttribute("poll", poll);
		
		List<PollOption> pollOptions = DAOProvider.getDao().getListOfPollOptions(pollID, "id");
		req.getSession().setAttribute("pollOptions", pollOptions);
		
		req.getRequestDispatcher("/WEB-INF/pages/glasanjeIndex.jsp").forward(req, resp);
	}
}
