package hr.fer.zemris.java.raytracer;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

import hr.fer.zemris.java.raytracer.model.IRayTracerProducer;
import hr.fer.zemris.java.raytracer.model.IRayTracerResultObserver;
import hr.fer.zemris.java.raytracer.model.LightSource;
import hr.fer.zemris.java.raytracer.model.Point3D;
import hr.fer.zemris.java.raytracer.model.Ray;
import hr.fer.zemris.java.raytracer.model.RayIntersection;
import hr.fer.zemris.java.raytracer.model.Scene;
import hr.fer.zemris.java.raytracer.viewer.RayTracerViewer;

/**
 * Class has same functionality as class {@link RayCaster}. Only difference is that this class works with multiple
 * threads.
 * 
 * @author Toni Martinčić
 * @version 1.0
 */
public class RayCasterParallel {
	
	/**
	 * Main method from which program starts.
	 *  
	 * @param args arguments of the command line, unused
	 */
	public static void main(String[] args) {
		RayTracerViewer.show(getIRayTracerProducer(), new Point3D(10, 0, 0), new Point3D(0, 0, 0),
				new Point3D(0, 0, 10), 20, 20);
	}
	
	/**
	 * Class extends {@link RecursiveAction} and represents job which is calculating data for red, green and blue
	 * color.
	 * 
	 * @author Toni Martinčić
	 * @version 1.0
	 */
	public static class Job extends RecursiveAction {
		/**
		 * Universal version identifier for a Serializable class.
		 */
		private static final long serialVersionUID = 1L;
		
		/**
		 * Point represents position of the eye of the viewer.
		 */
		private Point3D eye;
		
		/**
		 * Horizontal length.
		 */
		private double horizontal;
		
		/**
		 * Vertical length.
		 */
		private double vertical;
		
		/**
		 * Width of the window.
		 */
		private int width;
		
		/**
		 * Height of the window.
		 */
		private int height;
		
		/**
		 * Vector of z axis.
		 */
		private Point3D zAxis;
		
		/**
		 * Vector of y axis.
		 */
		private Point3D yAxis;
		
		/**
		 * Vector of x axis.
		 */
		private Point3D xAxis;
		
		/**
		 * Point represents screen corner.
		 */
		private Point3D screenCorner;
		
		/**
		 * Scene.
		 */
		private Scene scene;
		
		/**
		 * Minimum y.
		 */
		private int yMin;
		
		/**
		 * Maximum y.
		 */
		private int yMax;
		
		/**
		 * Data for red color.
		 */
		private short[] red;
		
		/**
		 * Data for green color.
		 */
		private short[] green;
		
		/**
		 * Data for blue color.
		 */
		private short[] blue;
		
		/**
		 * Treshold.
		 */
		static final int treshold = 16;
		
		/**
		 * Constructor which accepts fifteen arguments; point which represents position of the eye of the viewer,
		 * horizontal length, vertical length, width of the window, height of the window, vector of z axis,
		 * vector of y axis, vector of x axis, point which represents screen corner, scene, minimum y,
		 * maximum y, data for red color, data for green color and data for blue color.
		 * 
		 * @param eye point which represents position of the eye of the viewer
		 * @param horizontal horizontal length
		 * @param vertical vertical length
		 * @param width width of the window
		 * @param height height of the window
		 * @param zAxis vector of z axis
		 * @param yAxis vector of y axis
		 * @param xAxis vector of x axis
		 * @param screenCorner point which represents screen corner
		 * @param scene scene
		 * @param yMin minimum y
		 * @param yMax maximum y
		 * @param red data for red color
		 * @param green data for green color
		 * @param blue data for blue color
		 */
		public Job(Point3D eye, double horizontal, double vertical, int width, int height, Point3D zAxis, Point3D yAxis,
				Point3D xAxis, Point3D screenCorner, Scene scene, int yMin, int yMax, short[] red, short[] green, short[] blue) {
			super();
			
			this.eye = eye;
			this.horizontal = horizontal;
			this.vertical = vertical;
			this.width = width;
			this.height = height;
			this.zAxis = zAxis;
			this.yAxis = yAxis;
			this.xAxis = xAxis;
			this.screenCorner = screenCorner;
			this.scene = scene;
			this.yMin = yMin;
			this.yMax = yMax;
			this.red = red;
			this.green = green;
			this.blue = blue;
		}

		@Override
		protected void compute() {
			if(yMax-yMin+1 <= treshold) {
				computeDirect();
				return;
			}
			invokeAll(
					new Job(eye, horizontal, vertical, width, height, zAxis, yAxis, xAxis, screenCorner, scene, yMin, yMin+(yMax-yMin)/2, red, green, blue),
					new Job(eye, horizontal, vertical, width, height, zAxis, yAxis, xAxis, screenCorner, scene, yMin+(yMax-yMin)/2+1, yMax, red, green, blue)
			);
		}
		
		/**
		 * Method calculates data for red, green and blue color between ymin and ymax.
		 */
		public void computeDirect() {
			short[] rgb = new short[3];
			for (int y = yMin; y <= yMax; y++) {
				for (int x = 0; x < width; x++) {
					Point3D screenPoint = screenCorner.add(xAxis.scalarMultiply(x/(width-1.0)*horizontal)).sub(yAxis.scalarMultiply(y/(height-1.0)*vertical));
							
					Ray ray = Ray.fromPoints(eye, screenPoint);
					
				    tracer(scene, ray, rgb);
				    
					red[y * width + x] = rgb[0] > 255 ? 255 : rgb[0];
					green[y * width + x] = rgb[1] > 255 ? 255 : rgb[1];
					blue[y * width + x] = rgb[2] > 255 ? 255 : rgb[2];
				}
			}
		}
	}
	
	/**
	 * Method creates instance of {@link IRayTracerProducer}.
	 * 
	 * @return instance of {@link IRayTracerProducer}
	 */
	private static IRayTracerProducer getIRayTracerProducer() {
		
		return new IRayTracerProducer() {
			@Override
			public void produce(Point3D eye, Point3D view, Point3D viewUp, double horizontal, double vertical,
					int width, int height, long requestNo, IRayTracerResultObserver observer) {
				
				System.out.println("Započinjem izračune...");
				
				short[] red = new short[width * height];
				short[] green = new short[width * height];
				short[] blue = new short[width * height];
				
				Point3D zAxis = view.sub(eye).normalize();
				Point3D yAxis = viewUp.normalize().sub(zAxis.scalarMultiply(zAxis.scalarProduct(viewUp.normalize()))).normalize(); 
				Point3D xAxis = zAxis.vectorProduct(yAxis).normalize();
				
				Point3D screenCorner = view.sub(xAxis.scalarMultiply(horizontal/2)).add(yAxis.scalarMultiply(vertical/2));
				
				Scene scene = RayTracerViewer.createPredefinedScene();
				
				ForkJoinPool pool = new ForkJoinPool();
				pool.invoke(new Job(eye, horizontal, vertical, width, height, zAxis, yAxis, xAxis, screenCorner, scene, 0, height - 1, red, green, blue));
				pool.shutdown();
				
				System.out.println("Izračuni gotovi...");
				observer.acceptResult(red, green, blue, requestNo);
				System.out.println("Dojava gotova...");
			}
		};
	}
	
	/**
	 * Method determines color for the accepted pixel.
	 * 
	 * @param scene accepted scene
	 * @param ray accepted ray
	 * @param rgb data for colors
	 */
	protected static void tracer(Scene scene , Ray ray , short [] rgb) {
		rgb[0] = 0;
		rgb[1] = 0;
		rgb[2] = 0;
		
		RayIntersection closest = findClosestIntersection(scene, ray);
		
		if(closest==null) {
			return;
		}
		
		determineColorFor(scene, closest, rgb, ray);
	}

	/**
	 * Method determines color for accepted pixel.
	 * 
	 * @param scene scene
	 * @param closest intresection
	 * @param rgb data for colors
	 * @param ray ray
	 */
	private static void determineColorFor(Scene scene, RayIntersection closest, short[] rgb, Ray ray) {
		rgb[0] = 15;
		rgb[1] = 15;
		rgb[2] = 15;
		
		for(int i = 0, n = scene.getLights().size();  i < n; i++) {
			LightSource ls = scene.getLights().get(i);
			Ray newRay = new Ray(ls.getPoint(), closest.getPoint().sub(ls.getPoint()).normalize());
			
			RayIntersection newClosest = findClosestIntersection(scene, newRay);
			
			if(newClosest != null && 
			   newClosest.getDistance() < closest.getPoint().sub(ls.getPoint()).norm() &&
			   closest.getPoint().sub(ls.getPoint()).norm() - newClosest.getDistance() > 1E-5) {
				continue;
			}
			
			addDiffuseAndReflectiveComponents(ls, closest, rgb, ray, newRay);
		}
		
	}

	/**
	 * Method adds diffuse and reflective components.
	 * 
	 * @param ls light source
	 * @param closest ray intersection
	 * @param rgb data for colors
	 * @param ray ray from viewer to the intersection
	 * @param newRay ray from the light source to the intersection
	 */
	private static void addDiffuseAndReflectiveComponents(
			LightSource ls, RayIntersection closest, short[] rgb, Ray ray, Ray newRay) {
		
		Point3D normal = closest.getNormal();
		double nx = normal.x;
		double ny = normal.y;
		double nz = normal.z;
		
		double lx = newRay.direction.negate().x;
		double ly = newRay.direction.negate().y;
		double lz = newRay.direction.negate().z;
		
		double vx = ray.direction.negate().x;
		double vy = ray.direction.negate().y;
		double vz = ray.direction.negate().z;
		
		double Id = lx*nx + ly*ny + lz*nz;
		double l_n_2 = 2 * Id;
		double Idr = Id;
		double Idg = Id;
		double Idb = Id;
		if(Id > 0) {
			Idr *= ls.getR() * closest.getKdr();
			Idg *= ls.getG() * closest.getKdg();
			Idb *= ls.getB() * closest.getKdb();
		} else {
			Idr = 0;
			Idg = 0;
			Idb = 0;
		}
		
		double rx = l_n_2 * nx - lx;
		double ry = l_n_2 * ny - ly;
		double rz = l_n_2 * nz - lz;
		
		double Is = rx*vx + ry*vy + rz*vz;
		double Isr = Is;
		double Isg = Is;
		double Isb = Is;
		if(Is > 0) {
			Isr = ls.getR() * closest.getKrr() * Math.pow(Is, closest.getKrn());
			Isg = ls.getG() * closest.getKrg() * Math.pow(Is, closest.getKrn());
			Isb = ls.getB() * closest.getKrb() * Math.pow(Is, closest.getKrn());
		} else {
			Isr = 0;
			Isg = 0;
			Isb = 0;
		}
		
		double Ir = Idr + Isr;
		double Ig = Idg + Isg;
		double Ib = Idb + Isb;
		
		rgb[0] = (short) (rgb[0] + Ir);
		rgb[1] = (short) (rgb[1] + Ig);
		rgb[2] = (short) (rgb[2] + Ib);
	}

	/**
	 * Method finds and returns the closest intersection of accepted ray and all objects from accepted scene.
	 * 
	 * @param scene accepted scene
	 * @param ray accepted ray
	 * @return closest intersection of accepted ray and all objects from accepted scene
	 */
	private static RayIntersection findClosestIntersection(Scene scene, Ray ray) {
		RayIntersection closestRayIntersection = null;
		
		for(int i = 0, n = scene.getObjects().size(); i < n; i++) {
			RayIntersection newRayIntersection = scene.getObjects().get(i).findClosestRayIntersection(ray);
			
			if(newRayIntersection == null) {
				continue;
			}
			
			if(closestRayIntersection == null) {
				closestRayIntersection = newRayIntersection;
			} else if(newRayIntersection.getDistance() < closestRayIntersection.getDistance()) {
				closestRayIntersection = newRayIntersection;
			}
		}
		
		return closestRayIntersection;
	}


}
