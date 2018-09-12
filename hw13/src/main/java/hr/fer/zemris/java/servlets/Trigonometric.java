package hr.fer.zemris.java.servlets;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Class extends {@link HttpServlet} and represents servlet and creates html table with angles and it
 * sin and cos values.
 * 
 * @author Toni Martinčić
 * @version 1.0
 */
@WebServlet(name="t", urlPatterns={"/trigonometric"})
public class Trigonometric extends HttpServlet {

	/**
	 * Universal version identifier for a {@link Serializable} class.
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Default value for a.
	 */
	private static final Integer DEFAULT_VALUE_FOR_A = 0;
	
	/**
	 * Default value for b.
	 */
	private static final Integer DEFAULT_VALUE_FOR_B = 360;
	
	/**
	 * 
	 * @author Toni Martinčić
	 * @version 1.0
	 */
	public static class AngleInDegreesSinCos {
		
		/**
		 * Angle.
		 */
		private int angle;
		
		/**
		 * Sin of the variable angle.
		 */
		private double sin;
		
		/**
		 * Cos of the variable angle.
		 */
		private double cos;

		/**
		 * Constructor which accepts three arguments; angle, sin and cos.
		 * 
		 * @param angle angle
		 * @param sin sin of the variable angle
		 * @param cos cos of the variable angle
		 */
		public AngleInDegreesSinCos(int angle, double sin, double cos) {
			super();
			
			this.angle = angle;
			this.sin = sin;
			this.cos = cos;
		}

		/**
		 * Getter for the angle.
		 * 
		 * @return angle
		 */
		public int getAngle() {
			return angle;
		}

		/**
		 * Getter for the sin.
		 * 
		 * @return sin
		 */
		public double getSin() {
			return sin;
		}

		/**
		 * Getter for the cos.
		 * 
		 * @return cos
		 */
		public double getCos() {
			return cos;
		}
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Integer a = DEFAULT_VALUE_FOR_A; 
		Integer b = DEFAULT_VALUE_FOR_B;
		
		try{
			a = Integer.valueOf(req.getParameter("a"));
		} catch (Exception ignorable) {
		}
		
		try{
			b = Integer.valueOf(req.getParameter("b"));
		} catch (Exception ignorable) {
		}
		
		if (a > b) {
			Integer tmp = a;
			a = b;
			b = tmp;
		}
		
		if(b > a + 720) {
			b = a + 720;
		}
		
		List<AngleInDegreesSinCos> results = new ArrayList<>();
		
		for(int i = a; i <= b; ++i) {
			results.add(new AngleInDegreesSinCos(i, Math.sin(i * Math.PI / 180) , Math.cos(i * Math.PI / 180)));
		}
		
		req.setAttribute("results", results);
		
		req.getRequestDispatcher("/WEB-INF/pages/trigonometric.jsp").forward(req, resp);
	}
}
