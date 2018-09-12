package hr.fer.zemris.java.p12.servlets;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.List;
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
import hr.fer.zemris.java.p12.model.PollOption;

/**
 * Class extends {@link HttpServlet} and represents servlet which creates pie chart which data is
 * the result of the voting for the best band.
 * 
 * @author Toni Martinčić
 * @version 1.0
 */
@WebServlet(name="ggrafika", urlPatterns={"/servleti/glasanje-grafika"})
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
		List<PollOption> pollOptions = (List<PollOption>) req.getSession().getAttribute("pollOptions");
		
		PieChart pieChart = new PieChart("Rezultati", null, pollOptions);
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
		 * List of {@link PollOption}.
		 */
		private List<PollOption> pollOptions;

		/**
		 * Constructor which accepts four arguments; applicationTitle, chartTitle, idName and idValue.
		 * 
		 * @param applicationTitle application title
		 * @param chartTitle chart title
		 * @param pollOptions list of {@link PollOption}
		 */
	    public PieChart(String applicationTitle, String chartTitle, List<PollOption> pollOptions) {
	    	this.pollOptions = pollOptions;
	    	
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
  
	        for(PollOption pollOption : pollOptions) {
	        	result.setValue(pollOption.getOptionTitle(), pollOption.getVotesCount());
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
