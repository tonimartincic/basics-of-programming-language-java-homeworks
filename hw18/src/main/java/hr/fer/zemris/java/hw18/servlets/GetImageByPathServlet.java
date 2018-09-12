package hr.fer.zemris.java.hw18.servlets;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Class extends {@link HttpServlet} and represents servlet which gets the image for the accepted
 * image path.
 * 
 * @author Toni Martinčić
 * @version 1.0
 */
@WebServlet(urlPatterns={"/servlets/getImageByPath"})
public class GetImageByPathServlet extends HttpServlet {
	
	/**
	 * Universal version identifier for a {@link Serializable} class.
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Max width of the image.
	 */
	private static final int MAX_WIDTH = 1200;
	
	/**
	 * Max height of the image.
	 */
	private static final int MAX_HEIGHT = 800;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String path = req.getParameter("path");
	
		Image thumbnailTemp = ImageIO.read(new File(path));
		
		int width = thumbnailTemp.getWidth(null);
		int height = thumbnailTemp.getHeight(null);
				
		if(thumbnailTemp.getWidth(null) > MAX_WIDTH || thumbnailTemp.getHeight(null) > MAX_HEIGHT) {
			double factor1 = thumbnailTemp.getWidth(null) * 1.0 / MAX_WIDTH;
			double factor2 = thumbnailTemp.getHeight(null) * 1.0 / MAX_HEIGHT;
			
			double factor = factor1 > factor2 ? factor1 : factor2;
			
			width /= factor;
			height /= factor;
		}
		
		Image thumbnail = thumbnailTemp.getScaledInstance(width, height, BufferedImage.SCALE_SMOOTH);
		
		resp.setContentType("image/png");
		resp.setHeader("Content-Disposition", "attachment; filename=rezultati.png");
	
		OutputStream outputStream = resp.getOutputStream();
		
		BufferedImage bufferedImage = new BufferedImage(thumbnail.getWidth(null), thumbnail.getHeight(null), BufferedImage.TYPE_3BYTE_BGR);
		Graphics2D bImageGraphics = bufferedImage.createGraphics();
		bImageGraphics.drawImage(thumbnail, null, null);
		
		ImageIO.write(bufferedImage, "png", outputStream);
	}

}
