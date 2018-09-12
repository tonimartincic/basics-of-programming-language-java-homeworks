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
 * Class extends {@link HttpServlet} and represents servlet which gets poll results and calls jsp
 * which renders poll results.
 * 
 * @author Toni Martinčić
 * @version 1.0
 */
@WebServlet(name="gr", urlPatterns={"/servleti/glasanje-rezultati"})
public class GlasanjeRezultatiServlet extends HttpServlet {

	/**
	 * Universal version identifier for a {@link Serializable} class.
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		long pollID = Long.parseLong(req.getParameter("pollID"));
		
		List<PollOption> pollOptions = DAOProvider.getDao().getListOfPollOptions(pollID, "votesCount");
		req.getSession().setAttribute("pollOptions", pollOptions);
		
		Poll poll = DAOProvider.getDao().getPoll(pollID);
		req.getSession().setAttribute("poll", poll);
		
		List<PollOption> bestPollOptions = DAOProvider.getDao().getOptionsWithMostVotes(pollID);
		req.getSession().setAttribute("bestPollOptions", bestPollOptions);
		
		req.getRequestDispatcher("/WEB-INF/pages/glasanjeRez.jsp").forward(req, resp);
	}
}
