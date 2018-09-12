package hr.fer.zemris.java.votingApplication.servlets;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.JFrame;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot3D;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;
import org.jfree.util.Rotation;

/**
 * Class extends {@link HttpServlet} and represents servlet which creates pie chart which data is
 * the result of the voting for the best band.
 * 
 * @author Toni Martinčić
 * @version 1.0
 */
@WebServlet(name="ggrafika", urlPatterns={"/glasanje-grafika"})
public class GlasanjeGrafikaServlet extends HttpServlet {
	
	/**
	 * Universal version identifier for a {@link Serializable} class.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Width of the image.
	 */
	private static final int IMAGE_WIDTH = 400;
	
	/**
	 * Height of the image.
	 */
	private static final int IMAGE_HEIGHT = 300;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		@SuppressWarnings("unchecked")
		Map<String, String> idName = (Map<String, String>) req.getSession().getAttribute("idName");
		
		@SuppressWarnings("unchecked")
		Map<String, Integer> idValue = (Map<String, Integer>) req.getSession().getAttribute("idNumOfVotesSorted");
		
		PieChart pieChart = new PieChart("Rezultati", null, idName, idValue);
		JFreeChart jFreeChart = pieChart.getChart();
		
		BufferedImage bufferedImage = jFreeChart.createBufferedImage(IMAGE_WIDTH, IMAGE_HEIGHT);
		
		resp.setContentType("image/png");
		resp.setHeader("Content-Disposition", "attachment; filename=rezultati.png");
	
		OutputStream outputStream = resp.getOutputStream();
		ImageIO.write(bufferedImage, "png", outputStream);
	}
	
	/**
	 * Class extends {@link JFrame} and represents pie chart which data is voting result.
	 * 
	 * @author Toni Martinčić
	 * @version 1.0
	 */
	public static class PieChart extends JFrame {

		/**
		 * Universal version identifier for a {@link Serializable} class.
		 */
		private static final long serialVersionUID = 1L;
		
		/**
		 * Instance of {@link JFreeChart}.
		 */
		private JFreeChart chart;
		
		/**
		 * Map which maps ids of the bands to it names.
		 */
		private Map<String, String> idName;
		
		/**
		 * Map which maps ids of the bands to it number of the votes.
		 */
		private Map<String, Integer> idValue;

		/**
		 * Constructor which accepts four arguments; applicationTitle, chartTitle, idName and idValue.
		 * 
		 * @param applicationTitle application title
		 * @param chartTitle chart title
		 * @param idName map which maps ids of the bands to it names
		 * @param idValue map which maps ids of the bands to it number of the votes
		 */
	    public PieChart(String applicationTitle, String chartTitle, 
	    		        Map<String, String> idName, Map<String, Integer> idValue) {
	    	
	        super(applicationTitle);
	       
	        this.idName = idName;
	        this.idValue = idValue;
	        
	        PieDataset dataset = createDataset();
	        chart = createChart(dataset, chartTitle);   
	    }
	    
	    /**
	     * Getter for the chart.
	     * 
	     * @return chart
	     */
	    public JFreeChart getChart() {
			return chart;
		}

	    /**
	     * Setter for the chart.
	     * 
	     * @param chart chart
	     */
		public void setChart(JFreeChart chart) {
			this.chart = chart;
		}

		/**
		 * Method creates and returnes instance of {@link PieDataset}.
		 * 
		 * @return instance of {@link PieDataset}
		 */
	    private PieDataset createDataset() {
	        DefaultPieDataset result = new DefaultPieDataset();
  
	        for(Map.Entry<String, Integer> entry : idValue.entrySet()) {
	        	result.setValue(idName.get(entry.getKey()), entry.getValue());
	        }
	        
	        return result;
	    }

	    /**
	     * Method creates and returnes instance of {@link JFreeChart}.
	     * 
	     * @param dataset instance of {@link PieDataset}
	     * @param title title
	     * @return instance of {@link JFreeChart}
	     */
	    private JFreeChart createChart(PieDataset dataset, String title) {
	    	JFreeChart chart = ChartFactory.createPieChart3D(
	            title,                  
	            dataset,                
	            true,                   
	            true,
	            false
	        );

	        PiePlot3D plot = (PiePlot3D) chart.getPlot();
	        plot.setStartAngle(290);
	        plot.setDirection(Rotation.CLOCKWISE);
	        plot.setForegroundAlpha(0.5f);
	        return chart;
	    }
	}
}
