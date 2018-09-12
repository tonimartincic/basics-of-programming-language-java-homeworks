package hr.fer.zemris.java.webserver;

/**
 * Class which implements this interface is web worker.
 * 
 * @author Toni Martinčić
 * @version 1.0
 */
public interface IWebWorker {

	/**
	 * Method processes the accepted {@link RequestContext}.
	 * 
	 * @param context instance of {@link RequestContext}
	 * @throws Exception
	 */
	public void processRequest(RequestContext context) throws Exception;

}
