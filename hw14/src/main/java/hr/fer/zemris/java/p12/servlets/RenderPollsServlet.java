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

/**
 * Class extends {@link HttpServlet} and represents servlet which calls jsp which renders polls.
 * 
 * @author Toni Martinčić
 * @version 1.0
 */
@WebServlet(name="rp", urlPatterns={"/servleti/index.html"})
public class RenderPollsServlet extends HttpServlet {
	
	/**
	 * Universal version identifier for a {@link Serializable} class.
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		List<Poll> polls = DAOProvider.getDao().getBasicListOfPolls();
		
		req.getSession().setAttribute("polls", polls);
		
		req.getRequestDispatcher("/WEB-INF/pages/renderPolls.jsp").forward(req, resp);
	}
}
