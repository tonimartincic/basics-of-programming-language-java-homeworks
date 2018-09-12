package hr.fer.zemris.java.servlets;

import java.io.IOException;
import java.io.Serializable;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Class extends {@link HttpServlet} and represents servlet and sets background color of all .jsp files.
 * 
 * @author Toni Martinčić
 * @version 1.0
 */
@WebServlet(name="sc", urlPatterns={"/setcolor"})
public class SetColor extends HttpServlet {
	
	/**
	 * Universal version identifier for a {@link Serializable} class.
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		String color = (String) req.getParameter("pickedBgCol");
		
		req.getSession().setAttribute("pickedBgCol", color);
		req.getRequestDispatcher("index.jsp").forward(req, resp);
	}

}
