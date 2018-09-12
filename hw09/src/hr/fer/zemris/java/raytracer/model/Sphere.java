package hr.fer.zemris.java.raytracer.model;

/**
 * Class extends {@link GraphicalObject} and represents sphere. It contains {@link Point3D} which is center of the
 * sphere and double value which is radius of the sphere.
 * 
 * @author Toni Martinčić
 * @version 1.0
 */
public class Sphere extends GraphicalObject {
	
	/**
	 * Center of the sphere.
	 */
	private Point3D center;
	
	/**
	 * Radius of the sphere.
	 */
	private double radius;
	
	/**
	 * Coefficient for diffusion for the red color.
	 */
	private double kdr;
	
	/**
	 * Coefficient for diffusion for the green color.
	 */
	private double kdg;
	
	/**
	 * Coefficient for diffusion for the blue color.
	 */
	private double kdb;
	
	/**
	 * Coefficient for reflection for the red color.
	 */
	private double krr;
	
	/**
	 * Coefficient for reflection for the green color.
	 */
	private double krg;
	
	/**
	 * Coefficient for reflection for the blue color.
	 */
	private double krb;
	
	/**
	 * Reflective coefficient.
	 */
	private double krn;
	
	/**
	 * Constructor which accepts nine arguments; sphere center, sphere radius, coefficient for diffusion for the red color,
	 * coefficient for diffusion for the green color, coefficient for diffusion for the blue color, coefficient for reflection for the red color,
	 * coefficient for reflection for the green color, coefficient for reflection for the blue color and reflective coefficient.
	 * 
	 * @param center sphere center
	 * @param radius sphere radius
	 * @param kdr coefficient for diffusion for the red color
	 * @param kdg coefficient for diffusion for the green color
	 * @param kdb coefficient for diffusion for the blue color
	 * @param krr coefficient for reflection for the red color
	 * @param krg coefficient for reflection for the green color
	 * @param krb coefficient for reflection for the blue color
	 * @param krn reflective coefficient
	 * @throws IllegalArgumentException if the center is null value or the radius is the negative number
	 */
	public Sphere(Point3D center, double radius, double kdr, double kdg, double kdb, double krr, double krg, double krb, double krn) {
		if(center == null) {
			throw new IllegalArgumentException("Argument center can not be null value.");
		}
		
		if(radius <= 0) {
			throw new IllegalArgumentException("Radius must be positive number.");
		}
		
		this.center = center;
		this.radius = radius;
		this.kdr = kdr;
		this.kdg = kdg;
		this.kdb = kdb;
		this.krr = krr;
		this.krg = krg;
		this.krb = krb;
		this.krn = krn;
	}
	
	public RayIntersection findClosestRayIntersection(Ray ray) {
		Point3D directionOfTheRay = ray.direction;
		double dx = directionOfTheRay.x;
		double dy = directionOfTheRay.y;
		double dz = directionOfTheRay.z;
		
		Point3D startOfTheRay = ray.start;
		double xs = startOfTheRay.x;
		double ys = startOfTheRay.y;
		double zs = startOfTheRay.z;
		
		double x0 = center.x;
		double y0 = center.y;
		double z0 = center.z;
		
		double a = dx*dx + dy*dy + dz*dz;
		double b = 2*dx*(xs-x0) + 2*dy*(ys-y0) + 2*dz*(zs-z0);
		double c = Math.pow(xs-x0, 2.0) + Math.pow(ys-y0, 2.0) + Math.pow(zs-z0, 2.0) - Math.pow(radius, 2.0);
		
		if(b*b - 4*a*c < 0) {
			return null;
		}
		
		double lambda1 = (-b + Math.sqrt(Math.pow(b, 2.0) - 4*a*c)) / (2 * a);
		double lambda2 = (-b - Math.sqrt(Math.pow(b, 2.0) - 4*a*c)) / (2 * a);
		
		double x1 = xs + dx * lambda1;
		double y1 = ys + dy * lambda1;
		double z1 = zs + dz * lambda1;
		
		double x2 = xs + dx * lambda2;
		double y2 = ys + dy * lambda2;
		double z2 = zs + dz * lambda2;
		
		Point3D point1 = new Point3D(x1, y1, z1);
		Point3D point2 = new Point3D(x2, y2, z2);
		
		Point3D normal1 = point1.sub(center).normalize();
		Point3D normal2 = point2.sub(center).normalize();
		
		boolean outer1 = normal1.scalarProduct(directionOfTheRay) <= 0 ? true : false;
		boolean outer2 = normal2.scalarProduct(directionOfTheRay) <= 0 ? true : false;
		
		double distance1 = point1.sub(startOfTheRay).norm();
		double distance2 = point2.sub(startOfTheRay).norm();
		
		Point3D point = distance1 < distance2 ? point1 : point2;
		double distance = distance1 < distance2 ? distance1 : distance2;
		boolean outer = distance1 < distance2 ? outer1 : outer2;
		
		return new SphereRayIntersection(point, distance, outer);
	}
	
	/**
	 * Class extends {@link RayIntersection} and represents the intersection between sphere and the ray.
	 * 
	 * @author Toni Martinčić
	 * @version 1.0
	 */
	private class SphereRayIntersection extends RayIntersection {

		/**
		 * Constructor which accepts three arguments; point of the intersection, distance from start of the ray to the
		 * point of the intersection and the boolean value which contains information is the intersection outer or not.
		 * 
		 * @param point point of the intersection
		 * @param distance distance from start of the ray to the point of the intersection
		 * @param outer information is the intersection outer
		 */
		public SphereRayIntersection(Point3D point, double distance, boolean outer) {
			super(point, distance, outer);
		}
		
		@Override
		public Point3D getNormal() {
			return getPoint().sub(center).normalize();
		}

		@Override
		public double getKdr() {
			return kdr;
		}

		@Override
		public double getKdg() {
			return kdg;
		}

		@Override
		public double getKdb() {
			return kdb;
		}

		@Override
		public double getKrr() {
			return krr;
		}

		@Override
		public double getKrg() {
			return krg;
		}

		@Override
		public double getKrb() {
			return krb;
		}

		@Override
		public double getKrn() {
			return krn;
		}
		
	}

}
