package hr.fer.zemris.java.jfreecharts;

import java.io.Serializable;

import javax.swing.JFrame;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot3D;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;
import org.jfree.util.Rotation;

/**
 * Class extends {@link JFrame} and represents pie chart which data is OS usage.
 * 
 * @author Toni Martinčić
 * @version 1.0
 */
public class PieChart extends JFrame {

	/**
	 * Universal version identifier for a {@link Serializable} class.
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Instance of {@link JFreeChart}.
	 */
	private JFreeChart chart;

	/**
	 * Constructor which accepts two arguments; applicationTitle and chartTitle.
	 * 
	 * @param applicationTitle application title
	 * @param chartTitle chart title
	 */
    public PieChart(String applicationTitle, String chartTitle) {
        super(applicationTitle);
       
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
        result.setValue("Linux", 29);
        result.setValue("Mac", 20);
        result.setValue("Windows", 51);
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
