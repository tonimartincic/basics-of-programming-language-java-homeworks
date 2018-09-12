package hr.fer.zemris.java.raytracer;

import hr.fer.zemris.java.raytracer.model.IRayTracerProducer;
import hr.fer.zemris.java.raytracer.model.IRayTracerResultObserver;
import hr.fer.zemris.java.raytracer.model.LightSource;
import hr.fer.zemris.java.raytracer.model.Point3D;
import hr.fer.zemris.java.raytracer.model.Ray;
import hr.fer.zemris.java.raytracer.model.RayIntersection;
import hr.fer.zemris.java.raytracer.model.Scene;
import hr.fer.zemris.java.raytracer.viewer.RayTracerViewer;

/**
 * Class renders a 3D world based on a 2D map. It renders scene which contains two bigger spheres and one hundred
 * smaller spheres. Scene contains three light sources.
 * 
 * @author Toni Martinčić
 * @version 1.0
 */
public class RayCaster {

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
				
				short[] rgb = new short[3];
				int offset = 0;
				for (int y = 0; y < height; y++) {
					for (int x = 0; x < width; x++) {
						Point3D screenPoint = screenCorner.add(xAxis.scalarMultiply(x/(width-1.0)*horizontal)).sub(yAxis.scalarMultiply(y/(height-1.0)*vertical));
								
						Ray ray = Ray.fromPoints(eye, screenPoint);
						
					    tracer(scene, ray, rgb);
					    
						red[offset] = rgb[0] > 255 ? 255 : rgb[0];
						green[offset] = rgb[1] > 255 ? 255 : rgb[1];
						blue[offset] = rgb[2] > 255 ? 255 : rgb[2];
						
						offset++;
					}
				}
				
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
