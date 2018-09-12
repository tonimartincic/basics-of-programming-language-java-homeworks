package hr.fer.zemris.java.hw18.servlets;

import java.io.IOException;
import java.io.Serializable;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import hr.fer.zemris.java.hw18.util.Util;

/**
 * Class extends {@link HttpServlet} and represents servlet which gets all the tags.
 * 
 * @author Toni Martinčić
 * @version 1.0
 */
@WebServlet(urlPatterns={"/servlets/getTags"})
public class GetTagsServlet extends HttpServlet {
	
	/**
	 * Universal version identifier for a {@link Serializable} class.
	 */
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Util util = (Util) getServletContext().getAttribute("util");
		
		Set<String> tags = util.getAllTags();
		String[] array = tags.toArray(new String[tags.size()]);
		
		resp.setContentType("application/json;charset=UTF-8"); 
		
		Gson gson = new Gson();
		String jsonText = gson.toJson(array);
		
		resp.getWriter().write(jsonText);
		
		resp.getWriter().flush();
	}
	
}
