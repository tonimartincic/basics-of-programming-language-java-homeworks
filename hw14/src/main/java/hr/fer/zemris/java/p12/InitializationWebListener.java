package hr.fer.zemris.java.p12;

import java.beans.PropertyVetoException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.sql.SQLException;
import java.util.Properties;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.mchange.v2.c3p0.DataSources;

import hr.fer.zemris.java.p12.dao.DAOProvider;
import hr.fer.zemris.java.p12.dao.sql.SQLConnectionProvider;

/**
 * Class implements {@link ServletContextListener} which in its method contextInitialized creates
 * instance of {@link ComboPooledDataSource} and creates tables polls and polloptions in database
 * and fills them with data if it is needed.
 * 
 * @author Toni Martinčić
 * @version 1.0
 */
@WebListener
public class InitializationWebListener implements ServletContextListener {

	/**
	 * Virtual path to the properties file with database settings.
	 */
	private static final String VIRTUAL_PATH = "/WEB-INF/dbsettings.properties";
	
	@Override
	public void contextInitialized(ServletContextEvent sce) {
		
		String connectionURL = getConnectionURL(sce);
	
		ComboPooledDataSource cpds = new ComboPooledDataSource();
		try {
			cpds.setDriverClass("org.apache.derby.jdbc.ClientDriver");
		} catch (PropertyVetoException exc) {
			throw new RuntimeException("Error while initialization the pool.", exc);
		}
		cpds.setJdbcUrl(connectionURL);
		
		sce.getServletContext().setAttribute("hr.fer.zemris.dbpool", cpds);
		
		try {
			SQLConnectionProvider.setConnection(cpds.getConnection());
			
			DAOProvider.getDao().createPollsTableIfNeeded();
			DAOProvider.getDao().createPollOptionsTableIfNeeded();
			DAOProvider.getDao().fillTablesIfNeeded();
			
			SQLConnectionProvider.setConnection(null);
		} catch (SQLException e) {
			throw new RuntimeException("Database is not available.");
		}
	}
	
	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		ComboPooledDataSource cpds = (ComboPooledDataSource)sce.getServletContext().getAttribute("hr.fer.zemris.dbpool");
		if(cpds!=null) {
			try {
				DataSources.destroy(cpds);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * Method reads database properties from the properties file and creates and returns connection url 
	 * connection as string.
	 * 
	 * @param sce {@link ServletContextEvent}
	 * @return connection url
	 */
	private String getConnectionURL(ServletContextEvent sce) {
		String realPath = sce.getServletContext().getRealPath(VIRTUAL_PATH);
		if(!Files.exists(Paths.get(realPath))) {
			throw new RuntimeException("Properties file with database settings expected.");
		}
		
		Properties properties = getProperties(realPath);
		
		String host = properties.getProperty("host");
		String port = properties.getProperty("port");
		String name = properties.getProperty("name");
		String user = properties.getProperty("user");
		String password = properties.getProperty("password");
		
		if(host == null || port == null || name == null || user == null || password == null) {
			throw new RuntimeException("Data of properties file is not valid.");
		}
		
		StringBuilder sb = new StringBuilder();
		
		sb.append("jdbc:derby://");
		sb.append(host).append(":").append(port).append("/");
		sb.append(name);
		sb.append(";user=").append(user).append(";password=").append(password);
		
		return sb.toString();
	}

	/**
	 * Method gets database properties from the file which name is accepted as argument of the method.
	 * 
	 * @param propertiesFileName name of the properties file with database settings
	 * @return properties for the database settings
	 */
	private static Properties getProperties(String propertiesFileName) {
		Properties properties = new Properties();
		
		try(InputStream inputStream = Files.newInputStream(Paths.get(propertiesFileName), StandardOpenOption.READ)) {
			properties.load(inputStream);
		} catch (IOException exc) {
			throw new RuntimeException("Error while reading properties.", exc);
		} 
		
		return properties;
	}
}