package hr.fer.zemris.java.webserver;

/**
 * Class which implements this interface is dispatcher.
 * 
 * @author Toni Martinčić
 * @version 1.0
 */
public interface IDispatcher {
	
	/**
	 * Method dispatches the request.
	 * 
	 * @param urlPath accepted url path
	 * @throws Exception
	 */
	void dispatchRequest(String urlPath) throws Exception;

}
