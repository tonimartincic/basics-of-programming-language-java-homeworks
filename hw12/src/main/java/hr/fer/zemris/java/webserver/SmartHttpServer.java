package hr.fer.zemris.java.webserver;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PushbackInputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.BiConsumer;

import hr.fer.zemris.java.custom.scripting.exec.SmartScriptEngine;
import hr.fer.zemris.java.custom.scripting.nodes.DocumentNode;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParser;
import hr.fer.zemris.java.webserver.RequestContext.RCCookie;

/**
 * Class contains main method and accepts one argument which is path to the file with 
 * server properties. If there is no arguments given or if there more than one argument 
 * given than path to the file is "config/server.properties".
 * 
 * When the program is started it creates instance of {@link ServerThread} and starts it.
 * Server thread creates {@link ServerSocket} accepts instances of {@link Socket}. For every accepted 
 * {@link Socket} it creates instance of {@link ClientWorker} and in constructor it gives it accepted 
 * {@link Socket}.
 * 
 * @author Toni Martinčić
 * @version 1.0
 */
public class SmartHttpServer {
	
	/**
	 * Number of characters of generated random sid.
	 */
	private static final int NUMBER_OF_CHARACTERS_OF_SID = 20;
	
	/**
	 * Number of uppercase letters in ASCII table.
	 */
	private static final int NUMBER_OF_LETTERS = 26;
	
	/**
	 * Number of miliseconds that {@link RemoverThread} sleep. 
	 */
	private static final long NUMBER_OF_MILIS_THAT_REMOVER_THREAD_SLEEP = 300_000;

	/**
	 * Adress.
	 */
	private String address;
	
	/**
	 * Port.
	 */
	private int port;
	
	/**
	 * Number of worker threads.
	 */
	private int workerThreads;
	
	/**
	 * Session timeout.
	 */
	private int sessionTimeout;
	
	/**
	 * Map which contains mime types.
	 */
	private Map<String, String> mimeTypes = new HashMap<String, String>();
	
	/**
	 * Instance of {@link ServerThread}.
	 */
	private ServerThread serverThread;
	
	/**
	 * Instance of {@link RemoverThread}.
	 */
	private RemoverThread removerThread;
	
	/**
	 * Instance of {@link ExecutorService}.
	 */
	private ExecutorService threadPool;
	
	/**
	 * Path to the document root of the server.
	 */
	private Path documentRoot;
	
	/**
	 * Path to the mime confirufation.
	 */
	private Path mimeConfig;
	
	/**
	 * Path to the file with workers.
	 */
	private Path workers;
	
	/**
	 * Map which values are instances of {@link IWebWorker}
	 */
	private Map<String, IWebWorker> workersMap = new HashMap<>();
	
	/**
	 * Map which values are instances of {@link SessionMapEntry}.
	 */
	private Map<String, SessionMapEntry> sessions = new HashMap<>();

	/**
	 * Instance of {@link Random} which is used for generating random session id.
	 */
	private volatile Random sessionRandom = new Random();

	
	/**
	 * Method from which program starts. Method accepts one argument which is path to the file with 
	 * server properties.
	 * 
	 * @param args arguments of command line
	 */
	public static void main(String[] args) {	
		String path;
		
		if(args.length != 1) {
			path = "config/server.properties";
		} else {
			path = args[0];
		}
		
		Scanner sc = new Scanner(System.in);
		
		try {
			SmartHttpServer smartHttpServer = new SmartHttpServer(path);
			
			System.out.println("Enter 'start' to start or 'stop' to stop the server.");
			while(true) {
				System.out.print("> ");
				
				String input = "";
				
				if(sc.hasNextLine()) {
					input = sc.nextLine();
				}
			
				if(input.toLowerCase().equals("start")) {
					smartHttpServer.start();
					
					System.out.println("Server started");
				} else if(input.toLowerCase().equals("stop")) {
					smartHttpServer.stop();
					
					System.out.println("Server stopped");
					
					break;
				} else {
					System.out.println("Invalid input");
				}
			}	
		} catch (IOException exc) {
			System.out.println("Erorr: " + exc.getMessage());
		}
		
		sc.close();
	}
	
	/**
	 * Constructor which accepts one argument, name of the file with server configuration.
	 * 
	 * @param configFileName name of the file with server configuration
	 * @throws IOException if there is exception while reading or writing from stream
	 */
	public SmartHttpServer(String configFileName) throws IOException {
		super();
		
		checkConfigFileName(configFileName);
		Properties serverProperties = getProperties(configFileName);
		
		address = serverProperties.getProperty("server.address");
		try {
			port = Integer.parseInt(serverProperties.getProperty("server.port"));
			workerThreads = Integer.parseInt(serverProperties.getProperty("server.workerThreads"));
			sessionTimeout = Integer.parseInt(serverProperties.getProperty("session.timeout"));
		} catch(NumberFormatException exc) {
			throw new IllegalArgumentException(exc);
		}
		
		documentRoot = Paths.get(serverProperties.getProperty("server.documentRoot"));
		mimeConfig = Paths.get(serverProperties.getProperty("server.mimeConfig"));
		workers = Paths.get(serverProperties.getProperty("server.workers"));
		
		checkMimeConfigAndWorkers();
		
		Properties mimeProperties = getProperties(mimeConfig.toString());
		putAllFromTo(mimeProperties, mimeTypes);
		
		Properties workersProperties = getProperties(workers.toString());
		addWorkersToMap(workersProperties);
		
		serverThread = new ServerThread();
		
		removerThread = new RemoverThread();
		removerThread.setDaemon(true);
	}

	
	/**
	 * Method adds workers to map of workers.
	 * 
	 * @param workersProperties instance of {@link Properties} which contains informations about the workers
	 */
	private void addWorkersToMap(Properties workersProperties) {
		workersProperties.forEach(new BiConsumer<Object, Object>() {
			
			@Override
			public void accept(Object t, Object u) {
				String path = (String) t;
				if(workersMap.containsKey(path)) {
					throw new IllegalArgumentException(
							"In server.workers can not be multiple lines with same path.");
				}
				
				String fqcn = (String) u;
				
				Class<?> referenceToClass = null;
				try {
					referenceToClass = this.getClass().getClassLoader().loadClass(fqcn);
				} catch (ClassNotFoundException e) {
					System.out.println("Error: " + e.getMessage());
				}
				
				Object newObject = null;
				try {
					newObject = referenceToClass.newInstance();
				} catch (InstantiationException e) {
					System.out.println("Error: " + e.getMessage());
				} catch (IllegalAccessException e) {
					System.out.println("Error: " + e.getMessage());
				}
				
				IWebWorker iww = (IWebWorker) newObject;
				workersMap.put(path, iww);
			}
			
		});
	}

	
	/**
	 * Method checks is the configFileName valid. Invalid name is if the argument is null or file
	 * does not exist.
	 * 
	 * @param configFileName name of the file with server configuration
	 * @throws IllegalArgumentException if the argument is null or file
	 * does not exist.
	 */
	private void checkConfigFileName(String configFileName) {
		if(configFileName == null) {
			throw new IllegalArgumentException("Argument configFileName can not be null value.");
		}
		
		if(!Files.exists(Paths.get(configFileName))) {
			throw new IllegalArgumentException("File does not exist.");
		}
	}
	
	/**
	 * Method checks if the files with mime and workers configurations exist.
	 * 
	 * @throws IllegalArgumentException if the file with mime or workers configurations does not exist
	 */
	private void checkMimeConfigAndWorkers() {
		if(!Files.exists(mimeConfig)) {
			throw new IllegalArgumentException("File for mimeConfig does not exist.");
		}
		
		if(!Files.exists(workers)) {
			throw new IllegalArgumentException("File for workers does not exist.");
		}
	}

	
	/**
	 * Method accepts name of the file with the server configuration and returns instance of
	 * {@link Properties} which contains informations about the server configuration.
	 * 
	 * @param configFileName name of the file with the server configuration
	 * @return instance of {@link Properties}
	 * @throws IOException if there is exception while reading or writing into stream
	 */
	private static Properties getProperties(String configFileName) throws IOException {
		Properties properties = new Properties();
		InputStream inputStream = Files.newInputStream(Paths.get(configFileName), StandardOpenOption.READ);	
		
		properties.load(inputStream);
		inputStream.close();
		
		return properties;
	}

	
	/**
	 * Method puts all from accepted {@link Properties} to the accepted map
	 * 
	 * @param properties accepted {@link Properties}
	 * @param map accepted map
	 */
	private void putAllFromTo(Properties properties, Map<String, String> map) {
		properties.forEach(new BiConsumer<Object, Object>() {
			@Override
			public void accept(Object t, Object u) {
				map.put((String)t, (String)u);
			}
		});
	}
	
	
	/**
	 * Method starts the {@link ServerThread} and the {@link RemoverThread} and creates instance of
	 * {@link ExecutorService}.
	 */
	protected synchronized void start() {
		if(!serverThread.isAlive()) {
			serverThread.start();
			removerThread.start();
			
			threadPool = Executors.newFixedThreadPool(workerThreads);
		}
	}

	/**
	 * Method terminates {@link ServerThread} and {@link RemoverThread} and shutdowns 
	 * the {@link ExecutorService}.
	 */
	protected synchronized void stop() {
		serverThread.terminate();
		removerThread.terminate();
		
		threadPool.shutdown();
	}
	
	
	/**
	 * Class extends {@link Thread} and represents thread which removes each {@link SessionMapEntry}
	 * from sessions map which are expired.
	 * 
	 * @author Toni Martinčić
	 * @version 1.0
	 */
	protected class RemoverThread extends Thread {
		
		
		/**
		 * Flag which contain information is the stop of the thread requested.
		 */
		private volatile boolean stopRequested;
		
		
		/**
		 * Constructor which accepts no arguments and sets value of the stopRequested to the false.
		 */
		public RemoverThread() {
			super();
			
			this.stopRequested = false;
		}
		
		@Override
		public void run() {
			while(true) {
				if(stopRequested) {
					break;
				}
				
				List<String> sidsOfSessionsToRemove = new ArrayList<>();
				
				for(Map.Entry<String, SessionMapEntry> entry : sessions.entrySet()) {
					if(entry.getValue().validUntil < new Date().getTime()) {
						sidsOfSessionsToRemove.add(entry.getKey());
					}
				}
				
				for(String sid : sidsOfSessionsToRemove) {
					sessions.remove(sid);
				}
				
				try {
					Thread.sleep(NUMBER_OF_MILIS_THAT_REMOVER_THREAD_SLEEP);
				} catch (InterruptedException ignorable) {
				}	
			}
		}
		
		/**
		 * Method sets value of the flag to the true.
		 */
		public void terminate() {
			stopRequested = true;
		}
	}
	
	/**
	 * Class extends {@link Thread} and represents thread which creates {@link ServerSocket} and accepts
	 * instances of {@link Socket} and creates instances of {@link ClientWorker} with accepted {@link Socket}.
	 * 
	 * @author Toni Martinčić
	 * @version 1.0
	 */
	protected class ServerThread extends Thread {
		
		/**
		 * Flag which contain information is the stop of the thread requested.
		 */
		private volatile boolean stopRequested;
		
		/**
		 * Constructor which accepts no arguments and sets value of the stopRequested to the false.
		 */
		public ServerThread() {
			super();
			
			this.stopRequested = false;
		}
		
		@Override
		public void run() {
			ServerSocket serverSocket;
			
			try {
				serverSocket = new ServerSocket();
				serverSocket.bind(new InetSocketAddress((InetAddress)null, port));
				serverSocket.setSoTimeout(sessionTimeout);
				
				while(true) {
					try {
						Socket client = serverSocket.accept();
						
						ClientWorker cw = new ClientWorker(client);				
						threadPool.submit(cw);
					} catch(SocketTimeoutException exc) {
						if(stopRequested) {
							break;
						}
					}
				}
			} catch(IOException exc) {
				System.out.println("Erorr: " + exc.getMessage());
			}
		}
	
		/**
		 * Method sets value of the flag to the true.
		 */
		public void terminate() {
			stopRequested = true;
		}
	}

	
	/**
	 * Class implements {@link Runnable} and {@link IDispatcher} and accepts instances od {@link Socket}
	 * from {@link ServerThread}.
	 * 
	 * @author Toni Martinčić
	 * @version 1.0
	 */
	private class ClientWorker implements Runnable, IDispatcher {
		
		
		/**
		 * Instance of {@link Socket}.
		 */
		private Socket csocket;
		
		/**
		 * Instance of {@link PushbackInputStream}.
		 */
		private PushbackInputStream istream;
		
		/**
		 * Instance of {@link OutputStream}.
		 */
		private OutputStream ostream;
		
		/**
		 * Version. 
		 */
		private String version;
		
		/**
		 * Method.
		 */
		private String method;
		
		/**
		 * Map which contain parameters.
		 */
		private Map<String, String> params = new HashMap<String, String>();
		
		/**
		 * Map which contain temporary parameters.
		 */
		private Map<String, String> tempParams = new HashMap<String, String>();
		
		/**
		 * Map which contain persistent parameters.
		 */
		private Map<String, String> permParams = new HashMap<String, String>();
		
		/**
		 * List which contain cookies.
		 */
		private List<RCCookie> outputCookies = new ArrayList<RequestContext.RCCookie>();
		
		
		/**
		 * Session id.
		 */
		private String SID;

		
		/**
		 * Instance of {@link RequestContext}.
		 */
		private RequestContext context;
		
		/**
		 * Constructor which accepts 
		 * 
		 * @param csocket
		 */
		public ClientWorker(Socket csocket) {
			super();
			
			this.csocket = csocket;
		}
		
		@Override
		public void run() {
			try {
				obtainStreams();
				
				List<String> request = readRequest();
				
				if(request.size() <= 1) {
					sendError(ostream, 400, "Bad request");
					
					return;
				}
				
				String[] firstLine = request.isEmpty() ? null : request.get(0).split(" ");
				if(firstLine==null || firstLine.length != 3) {
					sendError(ostream, 400, "Bad request");
					
					return;
				}
		
				method = firstLine[0].toUpperCase();
				if(!method.equals("GET")) {
					sendError(ostream, 400, "Method Not Allowed");
					
					return;
				}
				
				version = firstLine[2].toUpperCase();
				if((!version.equals("HTTP/1.0")) && (!version.equals("HTTP/1.1"))) {
					sendError(ostream, 400, "HTTP Version Not Supported");
					
					return;
				}
				
				String requestedPath = firstLine[1];
				if(!requestedPath.contains(documentRoot.toString())) {
					requestedPath = documentRoot.toString() + requestedPath;
				}

				
				String[] parts = requestedPath.split("[?]");
				
				String path = parts[0]; 
				String paramString = ""; 
				if(parts.length > 1) {
					paramString = parts[1];
				}
				
				checkSession(request);
				parseParameters(paramString);
				internalDispatchRequest(path, true);
				
			} catch(IOException exc) {
				System.out.println("Erorr: " + exc.getMessage());
			} catch(Exception exc) {
				exc.printStackTrace();
			}
		}
		
		/**
		 * Method obtains the streams.
		 * 
		 * @throws IOException
		 */
		private void obtainStreams() throws IOException {
			this.istream = new PushbackInputStream(csocket.getInputStream());
			this.ostream = new BufferedOutputStream(csocket.getOutputStream());
		}

		/**
		 * Method reads the request.
		 * 
		 * @return request
		 * @throws IOException if there is exception while reading from stream
		 */
		private List<String> readRequest() throws IOException {
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			int state = 0;
	l:		while(true) {
				int b = istream.read();
				if(b==-1) return null;
				if(b!=13) {
					bos.write(b);
				}
				switch(state) {
				case 0: 
					if(b==13) { state=1; } else if(b==10) state=4;
					break;
				case 1: 
					if(b==10) { state=2; } else state=0;
					break;
				case 2: 
					if(b==13) { state=3; } else state=0;
					break;
				case 3: 
					if(b==10) { break l; } else state=0;
					break;
				case 4: 
					if(b==10) { break l; } else state=0;
					break;
				}
			}
		
			byte[] request = bos.toByteArray();
			String requestStr = new String(
					request, 
					StandardCharsets.US_ASCII
				);
			
			return extractHeaders(requestStr);
		}
		
		/**
		 * Method extracts and returns the header from the accepted request.
		 * 
		 * @param requestHeader accepted request
		 * @return extracted headers
		 */
		private List<String> extractHeaders(String requestHeader) {
			List<String> headers = new ArrayList<String>();
			String currentLine = null;
			
			for(String s : requestHeader.split("\n")) {
				if(s.isEmpty()) break;
				char c = s.charAt(0);
				if(c==9 || c==32) {
					currentLine += s;
				} else {
					if(currentLine != null) {
						headers.add(currentLine);
					}
					currentLine = s;
				}
			}
			
			if(!currentLine.isEmpty()) {
				headers.add(currentLine);
			}
			
			return headers;
		}
		
		/**
		 * Method sends error to the server.
		 * 
		 * @param cos instance of {@link OutputStream}
		 * @param statusCode status code
		 * @param statusText status text
		 * @throws IOException if there is exception while reading or writing into the stream
		 */
		private void sendError(OutputStream cos, int statusCode, String statusText) throws IOException {
			cos.write(
					("HTTP/1.1 "+statusCode+" "+statusText+"\r\n"+
					"Server: simple java server\r\n"+
					"Content-Type: text/plain;charset=UTF-8\r\n"+
					"Content-Length: 0\r\n"+
					"Connection: close\r\n"+
					"\r\n").getBytes(StandardCharsets.US_ASCII)
				);
				
				cos.flush();
		}
		
		/**
		 * Method parse the accepted parameters.
		 * 
		 * @param paramString accepted parameters
		 */
		private void parseParameters(String paramString) {
			if(paramString.equals("")) {
				return;
			}
			
			String[] pairs = paramString.split("[&]");
			
			for(String pair : pairs) {
				String[] parts = pair.split("[=]");
				
				if(parts.length != 2) {
					continue;
				}
				
				params.put(parts[0], parts[1]);
			}
		}

		/**
		 * Check session for cookies.
		 * 
		 * @param request accepted request.
		 */
		private synchronized void checkSession(List<String> request) {
			String sidCandidate = getSidCandidate(request);
		
			if (sidCandidate == null) {	
				generateSessionEntry();
				
				return;
			} 
			
			SessionMapEntry sessionMapEntry = sessions.get(sidCandidate);
			if (sessionMapEntry == null) {
				generateSessionEntry();
				
				return;
			} 
			
			if (sessionMapEntry.validUntil < new Date().getTime()) {
				sessions.remove(sidCandidate);
				
				generateSessionEntry();
			} else {
				sessionMapEntry.validUntil = ((long) (new Date().getTime() + sessionTimeout * 1000));
				
				this.permParams = sessionMapEntry.map;
				this.SID = sessionMapEntry.sid;
			}
		}
		
		/**
		 * Method gets the sid candidate or the null value if the sid candidate does not exist.
		 * 
		 * @param request accepted request
		 * @return sid candidate or the null value if the sid candidate does not exist
		 */
		private String getSidCandidate(List<String> request) {
			for (String headerLine : request) {
				if(!headerLine.startsWith("Cookie:")) {
					continue;
				}
				
				String[] cookies = headerLine.substring(headerLine.indexOf(':') + 2).split("[;]");
				
				for(int i = 0, n = cookies.length; i < n; i++) {
					String[] cookieParts = cookies[i].split("[=]");
					
					if(cookieParts[0].equals("sid")) {
						String sidCandidate = cookieParts[1].substring(1, cookieParts[1].length() - 1);
						
						return sidCandidate;
					}
				}
			}
			
			return null;
		}

		/**
		 * Method generates the instance of {@link SessionMapEntry}.
		 */
		public void generateSessionEntry() {
			SID = getRandomSid();
			long validUntil = new Date().getTime() + sessionTimeout * 1000;
			
			SessionMapEntry entry = new SessionMapEntry(SID, validUntil, permParams);
			sessions.put(SID, entry);
			
			outputCookies.add(new RequestContext.RCCookie("sid", SID, null, address	, "/", "HttpOnly"));
		}
		
		/**
		 * Method generates and returns the random sid.
		 * 
		 * @return generated sid
		 */
		private String getRandomSid() {
			String sid = "";
			
			for(int i = 0; i < NUMBER_OF_CHARACTERS_OF_SID; i++) {
				char newChar = (char)('A' + sessionRandom.nextInt(NUMBER_OF_LETTERS));
				
				sid += newChar;
			}
			
			return sid;
		}
		
		/**
		 * Processes the request based on accepted urlPath.
		 * 
		 * @param urlPath accepted urlPath
		 * @param directCall flag which contain information is the call of the method direct from
		 * this class or not
		 * @throws Exception
		 */
		public void internalDispatchRequest(String urlPath, boolean directCall) throws Exception {
			if(urlPath.matches("/private.*") && directCall == true) {
				sendError(ostream, 404, "Forbidden path");
				
				closeSocketAndOStream();
				return;
			}
			
			if(urlPath.matches(".*/ext/.*")) {
				workerDoJob(urlPath);
				
				closeSocketAndOStream();
				return;
			}
			
			IWebWorker iWebWorker = workersMap.get(urlPath.substring(urlPath.lastIndexOf('/')));
			
			if(iWebWorker != null) {
				createContexIfNeeded();
				
				iWebWorker.processRequest(context);
				
				closeSocketAndOStream();
				return;
			}
			
			if(!isPathOK(Paths.get(urlPath))) {
				return;
			}
			
			String extension = urlPath.substring(urlPath.lastIndexOf('.') + 1);
			
			if(extension.equals("smscr")) {
				executeScript(urlPath);
				
				closeSocketAndOStream();
				return;
			}
			
			String mimeType = mimeTypes.get(extension);
			if(mimeType == null) {
				mimeType = "application/octet-stream";
			}
		
			createContexIfNeeded();
			context.setMimeType(mimeType);
			
			writeDataFromPath(urlPath);
			closeSocketAndOStream();
		}

		/**
		 * Worker which name is accepted as argument does the request.
		 * 
		 * @param urlPath name of the worker
		 * @throws Exception
		 */
		private void workerDoJob(String urlPath) throws Exception {
			String absPath = "hr.fer.zemris.java.webserver.workers.";
			absPath += urlPath.substring(urlPath.lastIndexOf("/") + 1);

			Class<?> referenceToClass = null;
			try {
				referenceToClass = this.getClass().getClassLoader().loadClass(absPath.toString());
				 
			} catch (ClassNotFoundException e) {
				System.out.println("Error: " + e.getMessage());
			}
			
			Object newObject = null;
			try {
				newObject = referenceToClass.newInstance();
			} catch (InstantiationException | IllegalAccessException e) {
				System.out.println("Error: " + e.getMessage());
			} catch (Exception e) {
				System.out.println("Error: " + e.getMessage());
			}
			
			IWebWorker iww = (IWebWorker) newObject;
			
			createContexIfNeeded();
			
			iww.processRequest(context);
		}
		
		/**
		 * Method executes the script with the {@link SmartScriptEngine}. Path to the script is accepted
		 * as argument.
		 * 
		 * @param urlPath path to the script
		 * @throws IOException if there is exception while reading or writing into stream
		 */
		private void executeScript(String urlPath) throws IOException {
			byte[] data = Files.readAllBytes(Paths.get(urlPath));
			String dataAsString = new String(data);
			
			SmartScriptParser smartScriptParser = new SmartScriptParser(dataAsString);
			DocumentNode documentNode = smartScriptParser.getDocumentNode();
			
			createContexIfNeeded();
			
			SmartScriptEngine smartScriptEngine = new SmartScriptEngine(documentNode, context);
			smartScriptEngine.execute();
		}
		
		/**
		 * Method writes data from the file which path is accepted as argument to the output stream
		 * of the {@link RequestContext}.
		 * 
		 * @param urlPath path of the file accepted as argument
		 * @throws IOException if there is exception while reading or writing into stream
		 */
		private void writeDataFromPath(String urlPath) throws IOException {
			InputStream is = Files.newInputStream(Paths.get(urlPath), StandardOpenOption.READ);
			byte[] buff = new byte[1024];
			while(true) {
				int r = is.read(buff);
				if(r<1) break;
				
				byte[] temp = Arrays.copyOf(buff, r);
				context.write(temp);
			}
		}
		
		/**
		 * Method closes socket and output stream.
		 */
		private void closeSocketAndOStream() throws IOException {
			csocket.close();
			ostream.close();
		}
		
		/**
		 * Method creates {@link RequestContext} if the current instance is null.
		 */
		private void createContexIfNeeded() {
			if(context == null) {
				context = new RequestContext(
						ostream, params, permParams, outputCookies, tempParams, this);
			}
		}
		
		@Override
		public void dispatchRequest(String urlPath) throws Exception {
			if(!urlPath.contains(documentRoot.toString())) {
				urlPath = documentRoot.toString() + urlPath;
			}
			
			internalDispatchRequest(urlPath, false);
		}

		/**
		 * Method checks is the accepted path valid. Valid path starts with document root. Valid file
		 * exist, is readable and regular file
		 * 
		 * @param p accepted path
		 * @return true if the path is valid, false otherwise
		 * @throws IOException if there is exception while reading or writing into stream
		 */
		private boolean isPathOK(Path p) throws IOException {	
			if(!p.startsWith(documentRoot)) {
				sendError(ostream, 403, "Forbidden path");
				
				return false;
			}
			
			if(!Files.exists(p)) {
				sendError(ostream, 404, "File does not exist");
				
				return false;
			}
			
			if(!Files.isRegularFile(p)) {
				sendError(ostream, 404, "File is not regular file");
				
				return false;
			}
			
			if(!Files.isReadable(p)) {
				sendError(ostream, 404, "File is not readable");
				
				return false;
			}
			
			return true;
		}
	}
	
	/**
	 * Class contains informations for one session in the map sessions. It contains session id,
	 * map which keys are names of persistent parameters of the cookies and the values are 
	 * values of the persistent parameters of the cookies and number of milis between 1.1.1970 
	 * and the max age of the cookie
	 * 
	 * @author Toni Martinčić
	 * @version 1.0
	 */
	private static class SessionMapEntry {
		
		/**
		 * Session id.
		 */
		private String sid;
		
		/**
		 * Number of milis between 1.1.1970 and the max age of the cookie.
		 */
		private long validUntil;
		
		/**
		 * Map which keys are names of persistent parameters of the cookies and the values are 
		 * values of the persistent parameters of the cookies.
		 */
		private Map<String,String> map;
		
		/**
		 * Constructor which accepts three arguments; sid, validUntil and map.
		 * 
		 * @param sid session id
		 * @param validUntil number of milis between 1.1.1970 and the max age of the cookie
		 * @param map map
		 */
		public SessionMapEntry(String sid, long validUntil, Map<String, String> map) {
			super();
			
			this.sid = sid;
			this.validUntil = validUntil;
			this.map = map;
		}
	}	
}
