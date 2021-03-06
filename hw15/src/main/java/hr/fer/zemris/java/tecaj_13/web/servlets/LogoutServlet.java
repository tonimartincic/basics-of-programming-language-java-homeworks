package hr.fer.zemris.java.tecaj_13.web.servlets;

import java.io.IOException;
import java.io.Serializable;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Class represents servlet which logs out the user.
 * 
 * @author Toni Martinčić
 * @version 1.0
 */
@WebServlet("/servleti/logout")
public class LogoutServlet extends HttpServlet {
	
	/**
	 * Universal version identifier for a {@link Serializable} class.
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.getSession().invalidate();
		
		resp.sendRedirect(req.getContextPath() + "/servleti/main");
	}
}
