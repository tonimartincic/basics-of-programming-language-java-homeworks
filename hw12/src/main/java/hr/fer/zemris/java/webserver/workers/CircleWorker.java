package hr.fer.zemris.java.webserver.workers;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

import hr.fer.zemris.java.webserver.IWebWorker;
import hr.fer.zemris.java.webserver.RequestContext;

/**
 * 
 * 
 * @author Toni Martinčić
 * @version 1.0
 */
public class CircleWorker implements IWebWorker {

	@Override
	public void processRequest(RequestContext context) throws Exception {
		context.setMimeType("image/png");
		
		BufferedImage bim = new BufferedImage(200, 200, BufferedImage.TYPE_3BYTE_BGR);
		
		Graphics2D g2d = bim.createGraphics();
		
		g2d.setColor(Color.LIGHT_GRAY);
		
		g2d.fillRect(0, 0, bim.getWidth(), bim.getHeight());
		
		g2d.setColor(Color.YELLOW);
		
		g2d.fillOval(0, 0, bim.getWidth(), bim.getHeight());
		
		g2d.dispose();
		
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		
		try {
			ImageIO.write(bim, "png", bos);
			context.write(bos.toByteArray());
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		bos.flush();
		
		byte[] data = bos.toByteArray();
		context.write(data);
	}

}
