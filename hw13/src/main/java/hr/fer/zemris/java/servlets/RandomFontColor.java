package hr.fer.zemris.java.servlets;

import java.io.IOException;
import java.io.Serializable;
import java.util.Random;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Class extends {@link HttpServlet} and represents servlet which generates random color which will
 * be used for the color of the font.
 * 
 * @author Toni Martinčić
 * @version 1.0
 */
@WebServlet(name="rfc", urlPatterns={"/randomFontColor"})
public class RandomFontColor extends HttpServlet {
	
	/**
	 * Universal version identifier for a {@link Serializable} class.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Number of digits of the color.
	 */
	private static final int NUM_OF_DIGITS_OF_COLOR = 6;
	
	/**
	 * Instance of the {@link Random}.
	 */
	private static final Random random = new Random(16);

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String randomColor = getRandomColor();
		
		req.getSession().setAttribute("fontColor", randomColor);
		req.getRequestDispatcher("WEB-INF/pages/stories/funny.jsp").forward(req, resp);
	}

	/**
	 * Method gets String representation of the randomly generated color.
	 * 
	 * @return String representation of the randomly generated color
	 */
	private String getRandomColor() {
		StringBuilder sb = new StringBuilder();
		
		sb.append("#");
		for(int i = 0; i < NUM_OF_DIGITS_OF_COLOR; i++) {
			String newDigit = Integer.toHexString(random.nextInt(0x10));
			
			sb.append(newDigit);
		}
		
		return sb.toString();
	}
}
