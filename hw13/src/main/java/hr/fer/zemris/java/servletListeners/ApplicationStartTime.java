package hr.fer.zemris.java.servletListeners;

import java.util.Date;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 * Class implements {@link ServletContextListener} and in its method contextInitialized it gets
 * current time and sets it as servlet context attribute.
 * 
 * @author Toni Martinčić
 * @version 1.0
 */
@WebListener
public class ApplicationStartTime implements ServletContextListener {

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		// TODO Auto-generated method stub	
	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		arg0.getServletContext().setAttribute("startTime", new Date());
	}
}
