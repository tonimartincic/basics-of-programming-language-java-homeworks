package hr.fer.zemris.java.p12.servlets;

import java.io.IOException;
import java.io.Serializable;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Class extends {@link HttpServlet} and represents servlet which redirects to the servlet {@link RenderPollsServlet}.
 * 
 * @author Toni Martinčić
 * @version 1.0
 */
@WebServlet(name="r", urlPatterns={"/index.html"})
public class RedirectServlet extends HttpServlet {
	
	/**
	 * Universal version identifier for a {@link Serializable} class.
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.sendRedirect(req.getContextPath() + "/servleti/index.html");
	}
}
