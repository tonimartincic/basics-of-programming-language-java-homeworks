package hr.fer.zemris.java.fractals;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadFactory;

import hr.fer.zemris.java.fractals.viewer.FractalViewer;
import hr.fer.zemris.java.fractals.viewer.IFractalProducer;
import hr.fer.zemris.java.fractals.viewer.IFractalResultObserver;
import hr.fer.zemris.math.Complex;
import hr.fer.zemris.math.ComplexPolynomial;
import hr.fer.zemris.math.ComplexRootedPolynomial;

/**
 * Class is comamnd line application which accepts from the user complex numbers and then it starts fractal viewer and
 * displays the fractal for the accepted complex numbers.
 * Fractals are derived from Newton-Raphson iteration.
 * 
 * @author Toni Martinčić
 * @version 1.0
 */
public class Newton {
	
	/**
	 * Instance of {@link ComplexPolynomial} of accepted complex numbers.
	 */
	private static ComplexPolynomial polynom;
	
	/**
	 * Derived {@link ComplexPolynomial} of accepted complex numbers.
	 */
	private static ComplexPolynomial derived;
	
	/**
	 * Instance of {@link ComplexRootedPolynomial} of accepted complex numbers.
	 */
	private static ComplexRootedPolynomial rootedPolynom;
	
	/**
	 * Main method from which program starts.
	 * 
	 * @param args arguments of the command line, unused
	 */
	public static void main(String[] args) {
		
		printInstructions();
		
		List<Complex> listOfComplex = new ArrayList<>();
		
		Scanner sc = new Scanner(System.in);
		int counter = 1;
		while(true) {
			System.out.printf("Root %d>", counter);
			counter++;
			
			String line = sc.nextLine();
			
			if(line.equals("done")) {
				break;
			}
			
			Complex nextComplex = null;
			try {
				nextComplex = parseStringToComplex(line);
			} catch(Exception exc) {
				System.out.println("Invalid complex number.");
				
				continue;
			}
			
			listOfComplex.add(nextComplex);
		}
		sc.close();
		
		rootedPolynom = new ComplexRootedPolynomial(
				listOfComplex.toArray(new Complex[listOfComplex.size()]));
		polynom = rootedPolynom.toComplexPolynom();
		derived = polynom.derive();
		
		System.out.println("Image of fractal will appear shortly. Thank you.");
		
		FractalViewer.show(new MyProducer());
	}

	/**
	 * Method parse string accepted from the command line into the complex number.
	 * 
	 * @param s accepted string
	 * @return complex number
	 */
	public static Complex parseStringToComplex(String s) {
		String[] parts = s.split("[+-]");
		
		String[] newArray = new String[3];
		int i = 0;
		for(i = 0; i < parts.length; i++) {
			newArray[i] = parts[i];
		}
		for(int j = i; j < newArray.length; j++) {
			newArray[j] = null;
		}
		
		parts = newArray;
		
		boolean realPositive = true;
		boolean imaginaryPositive = true;
		
		if (s.charAt(0) == '-' && !parts[1].contains("i")) {
		    realPositive = false;
		} else if(s.charAt(0) == '-' && parts[1].contains("i")) {
			imaginaryPositive = false;
		} 
		if (imaginaryPositive && s.substring(1).contains("-")) {
			imaginaryPositive = false;
		}
		
		if(s.charAt(0) == '-') {
			parts[0] = parts[1];
			parts[1] = parts[2];
		}
		
		double real = 0.0;
		double imaginary = 0.0;
		
		if(parts[0] != null && parts[1] != null) { //Both real and imaginary parts exist
			real = Double.parseDouble((realPositive ? "" : "-") + parts[0]);
			
			String imaginaryValue;
			if(parts[1].indexOf('i') == parts[1].length() - 1) {
				imaginaryValue = "1";
			} else {
				imaginaryValue = parts[1].substring(parts[1].indexOf('i') + 1);
			}
			imaginary = Double.parseDouble((imaginaryPositive ? "+" : "-") + imaginaryValue);
		} else if(!parts[0].contains("i")) { //Only real part exists
			real = Double.parseDouble((realPositive ? "" : "-") + parts[0]);
			
			imaginary = 0.0;
		} else { //Only imaginary part exists
			String imaginaryValue;
			if(parts[0].indexOf('i') == parts[0].length() - 1) {
				imaginaryValue = "1";
			} else {
				imaginaryValue = parts[0].substring(parts[0].indexOf('i') + 1);
			}
			
			imaginary = Double.parseDouble((imaginaryPositive ? "+" : "-") + imaginaryValue);
		}
		
		return new Complex(real, imaginary);
	}

	/**
	 * Method prints instructions at the start of the program.
	 */
	private static void printInstructions() {
		System.out.println("Welcome to Newton-Raphson iteration-based fractal viewer.");
		System.out.println("Please enter at least two roots, one root per line. Enter 'done' when done.");
	}
	
	/**
	 * Class implements interface {@link IFractalProducer} and produces fractal for accepted complex numbers.
	 * 
	 * @author Toni Martinčić
	 * @version 1.0
	 */
	public static class MyProducer implements IFractalProducer {
		
		/**
		 * Instance of {@link ExecutorService}.
		 */
		private ExecutorService pool;
		
		/**
		 * List of {@link Future} objects.
		 */
		List<Future<Void>> results;
		
		/**
		 * Constructor which accepts no arguments and creates thread factory which creates daemonic threads and
		 * creates thread pool.
		 */
		public MyProducer() {
			super();
			
			DaemonicThreadFactory daemonicThreadFactory = new DaemonicThreadFactory();
			pool = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors(), daemonicThreadFactory);
			
			results = new ArrayList<>();
		}

		@Override
		public void produce(double reMin, double reMax, double imMin, double imMax, int width, int height,
				long requestNo, IFractalResultObserver observer) {
			
			short[] data = new short[width * height];
			final int numberOfTracks = 8 * Runtime.getRuntime().availableProcessors();
			int numberOfYPerTrack = height / numberOfTracks;
			
			for(int i = 0; i < numberOfTracks; i++) {
				int yMin = i*numberOfYPerTrack;
				int yMax = (i+1)*numberOfYPerTrack-1;
				if(i==numberOfTracks-1) {
					yMax = height-1;
				}
				Job job = new Job(reMin, reMax, imMin, imMax, width, height, yMin, yMax, polynom.order() + 1, data);
				results.add(pool.submit(job));
			}
			
			for(Future<Void> job : results) {
				try {
					job.get();
				} catch (InterruptedException | ExecutionException e) {
				}
			}
			pool.shutdown();
			
			observer.acceptResult(data, (short) (polynom.order() + 1), requestNo);
		}
		
		/**
		 * Class which implements {@link ThreadFactory} and creates threads which are daemonic.
		 * 
		 * @author Toni Martinčić
		 * @version 1.0
		 */
		private static class DaemonicThreadFactory implements ThreadFactory {

			@Override
			public Thread newThread(Runnable r) {
				Thread newThread = new Thread(r);
				newThread.setDaemon(true);
				
				return newThread;
			}
			
		}
		
	}
	
	/**
	 * Class represents job which calculates fractal between accepted ymin and ymax.
	 * 
	 * @author Toni Martinčić
	 * @version 1.0
	 */
	public static class Job implements Callable<Void> {
		
		/**
		 * Maximum number of iterations.
		 */
		private static final int MAX_ITER = 256;
		
		/**
		 * Treshold of convergence.
		 */
		private static final double CONVERGENCE_TRESHOLD = 1E-3;
		
		/**
		 * Treshold of root.
		 */
		private static final double ROOT_TRESHOLD = 2E-3;
		
		/**
		 * Minimum real part.
		 */
		double reMin;
		
		/**
		 * Maximum real part.
		 */
		double reMax;
		
		/**
		 * Minimum imaginary part.
		 */
		double imMin;
		
		/**
		 * Maximum imaginary part.
		 */
		double imMax;
		
		/**
		 * Width of the window.
		 */
		int width;
		
		/**
		 * Height of the window.
		 */
		int height;
		
		/**
		 * Minimum y.
		 */
		int yMin;
		
		/**
		 * Maximum y.
		 */
		int yMax;
		
		/**
		 * Polynomial order
		 */
		int m;
		
		/**
		 * Fractal data.
		 */
		short[] data;
		
		/**
		 * Constructor which accepts ten arguments; minimum real part, maximum real part, minimum imaginary part,
		 * maximum imaginary part, width of the window, height of the window, minimum y, maximum y, polynomial order
		 * and fractal data.
		 * 
		 * @param reMin minimum real part
		 * @param reMax maximum real part
		 * @param imMin minimum imaginary part
		 * @param imMax maximum imaginary part
		 * @param width width of the window
		 * @param height height of the window
		 * @param yMin minimum y
		 * @param yMax maximum y
		 * @param m polynomial order
		 * @param data fractal data
		 */
		public Job(double reMin, double reMax, double imMin, double imMax, int width, int height, int yMin, int yMax,
				int m, short[] data) {
			super();
			this.reMin = reMin;
			this.reMax = reMax;
			this.imMin = imMin;
			this.imMax = imMax;
			this.width = width;
			this.height = height;
			this.yMin = yMin;
			this.yMax = yMax;
			this.m = m;
			this.data = data;
		}

		@Override
		public Void call() throws Exception {
			
			for(int y = yMin; y <= yMax; y++) {
				 for(int x = 0; x < width; x++) {
					 Complex c = mapToComplexPlain(x, y, 0, width, yMin, yMax, reMin, reMax, imMin, imMax);
					 Complex zn = c;
					 int iter = 0;
					
					 double module;
					 Complex zn1;
					 do {
						Complex numerator = polynom.apply(zn);
						Complex denominator = derived.apply(zn);
						Complex fraction = numerator.divide(denominator);
						
						zn1 = zn.sub(fraction);
						module = zn1.sub(zn).module();
						zn = zn1;
						iter++;
					 } while(module > CONVERGENCE_TRESHOLD && iter < MAX_ITER);
					 
					 int index = rootedPolynom.indexOfClosestRootFor(zn1, ROOT_TRESHOLD) + 1;
					  
					 if(index == -1) { 
						 data[y * width + x]= 0; 
					 } else { 
						 data[y * width + x] = (short) index; 
					 }
				 }
			}
			
			return null;
		}

		/**
		 * Method maps accepted x and y to complex plain.
		 * 
		 * @param x x coordinate
		 * @param y y coordinate
		 * @param xMin minimum x
		 * @param xMax maximum x
		 * @param yMin minimum y
		 * @param yMax maximum y
		 * @param reMin minimum real component
		 * @param reMax maximum real component
		 * @param imMin minimum imaginary component
		 * @param imMax maximum imaginary component
		 * @return complex number
		 */
		private Complex mapToComplexPlain(int x, int y, int xMin, int xMax, int yMin, int yMax, double reMin,
				double reMax, double imMin, double imMax) {
			
			double re = x / (width-1.0) * (reMax - reMin) + reMin;
			double im = (height-1.0-y) / (height-1) * (imMax - imMin) + imMin;
		
			return new Complex(re, im);
		}
	}

}
