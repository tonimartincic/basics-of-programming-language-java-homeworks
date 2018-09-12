package hr.fer.zemris.java.webserver;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Class contains all the informations about the server request. It contains parameters, temporary
 * parameters and persistent parameters of the request. It contains list of {@link RCCookie} which are
 * cookies of the request. It contains reference to the output stream and it contains charset and
 * encoding, status code and status text and mime type of request.
 * 
 * @author Toni Martinčić
 * @version 1.0
 */
public class RequestContext {
	
	/**
	 * Default encoding which is used for text encoding.
	 */
	private static final String DEFAULT_ENCODING = "UTF-8";

	/**
	 * Default status code of the request.
	 */
	private static final int DEFAULT_STATUS_CODE = 200;

	/**
	 * Default status text of the request.
	 */
	private static final String DEFAULT_STATUS_TEXT = "OK";

	/**
	 * Default mime type of the request.
	 */
	private static final String DEFAULT_MIME_TYPE = "text/html";

	/**
	 * Output stream.
	 */
	private OutputStream outputStream;
	
	/**
	 * Charset.
	 */
	private Charset charset;
	
	/**
	 * Encoding used for text encoding.
	 */
	public String encoding;
	
	/**
	 * Status code.
	 */
	public int statusCode;
	
	/**
	 * Status text.
	 */
	public String statusText;
	
	/**
	 * Mime type.
	 */
	public String mimeType;
	
	/**
	 * Parameters.
	 */
	private Map<String,String> parameters;
	
	/**
	 * Temporary parameters.
	 */
	private Map<String,String> temporaryParameters;
	
	/**
	 * Persistent parameters.
	 */
	private Map<String,String> persistentParameters;
	
	/**
	 * Output cookies.
	 */
	private List<RCCookie> outputCookies;
	
	/**
	 * Flag which contains information is the header generated.
	 */
	private boolean headerGenerated;
	
	/**
	 * Instance of {@link IDispatcher}.
	 */
	private IDispatcher dispatcher;
	
	/**
	 * Constructor which accepts four arguments; outputStream, parameters, persistentParameters and 
	 * outputCookies and it delegates its job to the other constructor which accepts six arguments;  
	 * outputStream, parameters, persistentParameters, outputCookies, temporaryParameters and
	 * dispatcher. As temporaryParameters and dispatcher it sends null values.
	 * 
	 * @param outputStream output stream
	 * @param parameters parameters
	 * @param persistentParameters persistent parameters
	 * @param outputCookies output cookies
	 */
	public RequestContext(
			OutputStream outputStream, 
			Map<String,String> parameters, 
			Map<String,String> persistentParameters, 
			List<RCCookie> outputCookies) {
		
		this(outputStream, parameters, persistentParameters, outputCookies, null, null);
	}
	
	/**
	 * Constructor which accepts six arguments; outputStream, parameters, persistentParameters,
	 * outputCookies, temporaryParameters and dispatcher.
	 * 
	 * @param outputStream output stream
	 * @param parameters parameters
	 * @param persistentParameters persistent parameters
	 * @param outputCookies output cookies
	 * @param temporaryParameters temporary parameters 
	 * @param dispatcher dispatcher
	 */
	public RequestContext(OutputStream outputStream, 
			Map<String,String> parameters, 
			Map<String,String> persistentParameters, 
			List<RCCookie> outputCookies,
			Map<String,String> temporaryParameters,
			IDispatcher dispatcher) {
		
		checkIfIsNull(outputStream, "outputStream");
		this.outputStream = outputStream;
		
		this.parameters = new HashMap<>();
		if(parameters != null) {
			this.parameters.putAll(parameters);
		}
		
		this.persistentParameters = persistentParameters == null ? new HashMap<>() : persistentParameters;
		
		this.temporaryParameters = temporaryParameters == null ? new HashMap<>() : temporaryParameters;
		
		this.outputCookies = new ArrayList<>();
		if(outputCookies != null) {
			this.outputCookies.addAll(outputCookies);
		}
		
		this.encoding = DEFAULT_ENCODING;
		this.statusCode = DEFAULT_STATUS_CODE;
		this.statusText = DEFAULT_STATUS_TEXT;
		this.mimeType = DEFAULT_MIME_TYPE;
		
		this.headerGenerated = false;
		
		this.dispatcher = dispatcher;
	}
	
	/**
	 * Getter for the dispatcher.
	 * 
	 * @return dispatcher
	 */
	public IDispatcher getDispatcher() {
		return this.dispatcher;
	}

	/**
	 * Getter for the outputStream.
	 * 
	 * @return outputStream
	 */
	public OutputStream getOutputStream() {
		return outputStream;
	}

	/**
	 * Setter for the outputStream.
	 * 
	 * @param outputStream outputStream
	 */
	public void setOutputStream(OutputStream outputStream) {
		checkIfIsNull(outputStream, "outputStream");
		
		this.outputStream = outputStream;
	}

	/**
	 * Getter for the charset.
	 * 
	 * @return charset
	 */
	public Charset getCharset() {
		return charset;
	}

	/**
	 * Setter for the charset.
	 * 
	 * @param charset charset
	 */
	public void setCharset(Charset charset) {
		checkIfIsNull(charset, "charset");
		
		this.charset = charset;
	}
	
	/**
	 * Setter for the encoding.
	 * 
	 * @param encoding encoding
	 */
	public void setEncoding(String encoding) {
		checkIfHeaderIsGenerated();
		checkIfIsNull(encoding, "encoding");
		
		this.encoding = encoding;
	}

	/**
	 * Setter for the statusCode.
	 * 
	 * @param statusCode status code
	 */
	public void setStatusCode(int statusCode) {
		checkIfHeaderIsGenerated();
		
		this.statusCode = statusCode;
	}

	/**
	 * Setter for the statusText.
	 * 
	 * @param statusText status text
	 */
	public void setStatusText(String statusText) {
		checkIfHeaderIsGenerated();
		checkIfIsNull(statusText, "statusText");
		
		this.statusText = statusText;
	}

	/**
	 * Setter for the mimeType.
	 * 
	 * @param mimeType mime type
	 */
	public void setMimeType(String mimeType) {
		checkIfHeaderIsGenerated();
		checkIfIsNull(mimeType, "mimeType");
		
		this.mimeType = mimeType;
	}
	
	/**
	 * Method returns information is the header generated.
	 * 
	 * @return true if header is already generated, false otherwise
	 */
	public boolean isHeaderGenerated() {
		return headerGenerated;
	}
	
	/**
	 * Method accepts instance of {@link RCCookie} and adds it into the list of
	 * output cookies if header is not already generated and the accepted cookie
	 * is not null value
	 * 
	 * @param rcCookie instance of {@link RCCookie}
	 */
	public void addRCCookie(RCCookie rcCookie) {
		checkIfHeaderIsGenerated();
		checkIfIsNull(rcCookie, "rcCookie");
		
		outputCookies.add(rcCookie);
	}
	
	/**
	 * Getter for the outputCookies.
	 * 
	 * @return output cookies
	 */
	public List<RCCookie> getOutputCookies() {
		return outputCookies;
	}

	/**
	 * Setter for the outputCookies.
	 * 
	 * @param outputCookies output cookies
	 */
	public void setOutputCookies(List<RCCookie> outputCookies) {
		checkIfHeaderIsGenerated();
		checkIfIsNull(outputCookies, "outputCookies");
		
		this.outputCookies = outputCookies;
	}

	/**
	 * Getter for the temporaryParameters.
	 * 
	 * @return temporary parameters
	 */
	public Map<String, String> getTemporaryParameters() {
		return temporaryParameters;
	}

	/**
	 * Setter for the temporaryParameters.
	 * 
	 * @param temporaryParameters temporary parameters
	 */
	public void setTemporaryParameters(Map<String, String> temporaryParameters) {
		checkIfIsNull(temporaryParameters, "temporaryParameters");
		
		this.temporaryParameters = temporaryParameters;
	}

	/**
	 * Getter for the persistentParameters.
	 * 
	 * @return persistentParameters
	 */
	public Map<String, String> getPersistentParameters() {
		return persistentParameters;
	}

	/**
	 * Setter for the persistentParameters.
	 * 
	 * @param persistentParameters persistentParameters
	 */
	public void setPersistentParameters(Map<String, String> persistentParameters) {
		checkIfIsNull(persistentParameters, "persistentParameters");
		
		this.persistentParameters = persistentParameters;
	}

	/**
	 * Getter for the parameters.
	 * 
	 * @return parameters.
	 */
	public Map<String, String> getParameters() {
		return parameters;
	}
	
	/**
	 * Getter for the parameter from the paramters for the name accepted
	 * as argument.
	 * 
	 * @param name name of the parameter
	 * @return parameter
	 */
	public String getParameter(String name) {
		return getValueFromMap(parameters, name);
	}

	/**
	 * Getter for the names of the parameters.
	 * 
	 * @return names of the parameters
	 */
	public Set<String> getParameterNames() {
		return getNamesOfAllParameters(parameters);
	}

	/**
	 * Getter for the persistent parameter from the persistent
	 * parameters for the name accepted as argument.
	 * 
	 * @param name name of the persistent parameter
	 * @return persistent parameter
	 */
	public String getPersistentParameter(String name) {
		return getValueFromMap(persistentParameters, name);
	}
	
	/**
	 * Getter for the names of the persistent parameters.
	 * 
	 * @return names of the persistent parameters
	 */
	public Set<String> getPersistentParameterNames() {
		return getNamesOfAllParameters(persistentParameters);
	}

	/**
	 * Method adds persistent parameter with name and value accepted
	 * as arguments to the persistent parameters.
	 * 
	 * @param name name of the persistent parameter
	 * @param value value of the persistent parameter
	 */
	public void setPersistentParameter(String name, String value) {
		checkIfIsNull(name, "name");
		checkIfIsNull(value, "value");
		
		setValueToMap(persistentParameters, name, value);
	}
	
	/**
	 * Method removes persistent parameter which name is name accepted
	 * as argument from persistent parameters.
	 * 
	 * @param name name of the persistent parameter
	 */
	public void removePersistentParameter(String name) {
		checkIfIsNull(name, "name");
		
		removeParameterFromMap(persistentParameters, name);
	}
	
	/**
	 * Method returns temporary parameter from the temporary parameters
	 * for the name accepted as argument.
	 * 
	 * @param name name of the temporary parameter
	 * @return temporary parameter
	 */
	public String getTemporaryParameter(String name) {
		checkIfIsNull(name, "name");
		
		return getValueFromMap(temporaryParameters, name);
	}
	
	/**
	 * Getter for the names of the temporary parameters.
	 * 
	 * @return names of the temporary parameters
	 */
	public Set<String> getTemporaryParameterNames() {
		return getNamesOfAllParameters(temporaryParameters);
	}
	
	/**
	 * Method adds temporary parameter with name and value accepted
	 * as arguments to the persistent parameters.
	 * 
	 * @param name name of the temporary parameter
	 * @param value value of the temporary parameter
	 */
	public void setTemporaryParameter(String name, String value) {
		checkIfIsNull(name, "name");
		checkIfIsNull(value, "value");
		
		setValueToMap(temporaryParameters, name, value);
	}

	/**
	 * Method removes temporary parameter which name is name accepted
	 * as argument from temporary parameters.
	 * 
	 * @param name name of the temporary parameter
	 */
	public void removeTemporaryParameter(String name) {
		removeParameterFromMap(temporaryParameters, name);
	}
	
	/**
	 * Method return value from the accepted map. It gets value from the map
	 * where the key is argument name.
	 * 
	 * @param map accepted map
	 * @param name key
	 * @return value from the map where the key is argument name
	 */
	private String getValueFromMap(Map<String, String> map, String name) {
		checkIfIsNull(map, "map");
		checkIfIsNull(name, "name");
		
		return map.get(name);
	}
	
	/**
	 * Method adds value to the accepted map. Value is argument value and the key is argument
	 * name.
	 * 
	 * @param map accepted map
	 * @param name key
	 * @param value value
	 */
	private void setValueToMap(Map<String, String> map, String name, String value) {
		checkIfIsNull(map, "map");
		checkIfIsNull(name, "name");
		checkIfIsNull(value, "value");
		
		map.put(name, value);
	}

	/**
	 * Method removes value from the accepted map where the key is argument name.
	 * 
	 * @param map accepted map
	 * @param name key
	 */
	private void removeParameterFromMap(Map<String, String> map, String name) {
		checkIfIsNull(map, "map");
		checkIfIsNull(name, "name");
		
		map.remove(name);
	}

	/**
	 * Method returns unmodifiable set of keys for the accepted map.
	 * 
	 * @param map accepted map
	 * @return unmodifiable set of keys for the accepted map
	 */
	private Set<String> getNamesOfAllParameters(Map<String, String> map) {
		checkIfIsNull(map, "map");
		
		return Collections.unmodifiableSet(map.keySet());
	}
	
	/**
	 * Method checks is the accepted object null and throws {@link IllegalArgumentException}
	 * if the accepted object is null.
	 * 
	 * @param argument accepted object
	 * @param str name of the accepted object
	 * @throws IllegalArgumentException if the accepted object is null
	 */
	private static void checkIfIsNull(Object argument, String str) {
		if(argument == null) {
			throw new IllegalArgumentException(
					"Argument " + str + " can not be null value.");
		}
	}
	
	/**
	 * Method checks is the header generated and throws {@link RuntimeException} if it is null.
	 * 
	 * @throws RuntimeException if the header is already generated
	 */
	private void checkIfHeaderIsGenerated() {
		if(headerGenerated) {
			throw new RuntimeException(
					"Can not change the attribute because header is already generated.");
		}
	}

	/**
	 * Method writes accepted data to the output stream.
	 * 
	 * @param data accepted data
	 * @return instance of {@link RequestContext}
	 * @throws IOException if there is exception while writing data to the stream
	 */
	public RequestContext write(byte[] data) throws IOException {
		if(!headerGenerated) {
			String header = generateHeader();
	
			outputStream.write(header.getBytes(StandardCharsets.ISO_8859_1));
			outputStream.flush();
			
			charset = Charset.forName(encoding);
			headerGenerated = true;
		}
		
		outputStream.write(data, 0, data.length);
		outputStream.flush();
		
		return this; 
	}
	
	/**
	 * Method writes accepted string to the output stream.
	 * 
	 * @param text accepted text
	 * @return instance of {@link RequestContext}
	 * @throws IOException if there is exception while writing data to the stream
	 */
	public RequestContext write(String text) throws IOException {
		checkIfIsNull(text, "text");
		
		byte[] data = text.getBytes(encoding);
		
		return write(data);
	}

	/**
	 * Method generates header and returns it as one String. 
	 * 
	 * @return header as one string
	 */
	private String generateHeader() {
		StringBuilder sb = new StringBuilder();
		
		sb.append("HTTP/1.1 ").append(statusCode).append(" ").append(statusText).append("\r\n");
		
		sb.append("Content-Type: ").append(mimeType);
		if(mimeType.startsWith("text/")) {
			sb.append("; charset=").append(encoding);
		}
		sb.append("\r\n");
		
		for(RCCookie rcCookie : outputCookies) {	
			sb.append("Set-Cookie: ");
			sb.append(rcCookie.getName()).append("=").
			   append("\"").append(rcCookie.getValue()).append("\"");
			
			if(rcCookie.getDomain() != null) {
				sb.append("; Domain=").append(rcCookie.getDomain());
			}
			
			if(rcCookie.getPath() != null) {
				sb.append("; Path=").append(rcCookie.getPath());
			}
			
			if(rcCookie.getMaxAge() != null) {
				sb.append("; Max-Age=").append(rcCookie.getMaxAge());
			}
			
			if(rcCookie.getCookieType() != null) {
				sb.append("; " + rcCookie.getCookieType());
			}
			
			sb.append("\r\n");
		}
		
		sb.append("\r\n");
	
		return sb.toString();
	}
	
	/**
	 * Class represents cookie and contains name, value, domain, path, maxAge and type of the cookie.
	 * 
	 * @author Toni Martinčić
	 * @version 1.0
	 */
	public static class RCCookie {
		
		/**
		 * Name of the cookie.
		 */
		private String name;
		
		/**
		 * Value of the cookie.
		 */
		private String value;
		
		/**
		 * Domain of the cookie.
		 */
		private String domain;
		
		/**
		 * Path of the cookie.
		 */
		private String path;
		
		/**
		 * Max age of the cookie.
		 */
		private Integer maxAge;
		
		/**
		 * Type of the cookie.
		 */
		private String cookieType;
		
		/**
		 * Constructor which accepts six arguments; name, value, maxAge, domain, path and 
		 * cookieType.
		 * 
		 * @param name name of the cookie
		 * @param value value of the cookie
		 * @param maxAge max age of the cookie
		 * @param domain domain of the cookie
		 * @param path path of the cookie
		 * @param cookieType type of the cookie
		 */
		public RCCookie(String name, String value, Integer maxAge, String domain, String path, String cookieType) {
			checkArgumentsOfRCCookie(name, value);
			
			this.name = name;
			this.value = value;
			this.domain = domain;
			this.path = path;
			this.maxAge = maxAge;
			this.cookieType = cookieType;
		}

		/**
		 * Method checks if the accepted arguments are null.
		 * 
		 * @param name name of the cookie
		 * @param value value of the cookie
		 */
		private void checkArgumentsOfRCCookie(String name, String value) {
			checkIfIsNull(name, "name");
			checkIfIsNull(value, "value");
		}

		/**
		 * Getter for the cookieType.
		 * 
		 * @return cookieType.
		 */
		public String getCookieType() {
			return cookieType;
		}
		
		/**
		 * Setter for the cookieType.
		 * 
		 * @param cookieType cookie type
		 */
		public void setCookieType(String cookieType) {
			this.cookieType = cookieType;
		}
		
		/**
		 * Getter for the name.
		 * 
		 * @return name
		 */
		public String getName() {
			return name;
		}

		/**
		 * Getter for the value.
		 * 
		 * @return value
		 */
		public String getValue() {
			return value;
		}

		/**
		 * Getter for the domain.
		 * 
		 * @return domain
		 */
		public String getDomain() {
			return domain;
		}

		/**
		 * Getter for the path
		 * 
		 * @return path
		 */
		public String getPath() {
			return path;
		}

		/**
		 * Getter for the maxAge.
		 * 
		 * @return maxAge
		 */
		public Integer getMaxAge() {
			return maxAge;
		}	
	}
}
