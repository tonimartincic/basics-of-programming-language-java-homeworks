package hr.fer.zemris.java.servlets;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jfree.chart.JFreeChart;

import hr.fer.zemris.java.jfreecharts.PieChart;

/**
 * Class extends {@link HttpServlet} and represents servlet which creates simple pie chart which
 * data is OS usage.
 * 
 * @author Toni Martinčić
 * @version 1.0
 */
@WebServlet(name="ri", urlPatterns={"/reportImage"})
public class ReportImage extends HttpServlet {

	/**
	 * Universal version identifier for a {@link Serializable} class.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Width of the image.
	 */
	private static final int IMAGE_WIDTH = 800;
	
	/**
	 * Height of the image.
	 */
	private static final int IMAGE_HEIGHT = 600;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		PieChart pieChart = new PieChart("OS usage", null);
		JFreeChart jFreeChart = pieChart.getChart();
		
		BufferedImage bufferedImage= jFreeChart.createBufferedImage(IMAGE_WIDTH, IMAGE_HEIGHT);

		File outputfile = new File("src/main/webapp/pictures/image.png");
		ImageIO.write(bufferedImage, "png", outputfile);

		req.getRequestDispatcher("WEB-INF/pages/report.jsp").forward(req, resp);
	}
}