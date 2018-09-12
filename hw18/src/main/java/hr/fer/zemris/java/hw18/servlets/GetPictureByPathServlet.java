package hr.fer.zemris.java.hw18.servlets;

import java.io.IOException;
import java.io.Serializable;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import hr.fer.zemris.java.hw18.Picture;
import hr.fer.zemris.java.hw18.util.Util;

/**
 * Class extends {@link HttpServlet} and represents servlet which gets {@link Picture} for the accepted
 * path.
 * 
 * @author Toni Martinčić
 * @version 1.0
 */
@WebServlet(urlPatterns={"servlets/getPictureByPath"})
public class GetPictureByPathServlet extends HttpServlet {
	
	/**
	 * Universal version identifier for a {@link Serializable} class.
	 */
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Util util = (Util) getServletContext().getAttribute("util");
		
		String pictureName = req.getParameter("pictureName");
		
		Picture picture = util.getPictureByName(pictureName);
		
		resp.setContentType("application/json;charset=UTF-8"); 
		
		Gson gson = new Gson();
		String jsonText = gson.toJson(picture);
		
		resp.getWriter().write(jsonText);
		
		resp.getWriter().flush();
	}

}
