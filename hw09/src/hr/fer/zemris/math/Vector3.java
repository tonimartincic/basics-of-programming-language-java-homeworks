package hr.fer.zemris.math;

/**
 * Class represents vector model. Vector is unmodifiable and contains three components. All operations which are
 * called on vector return new vector which is the result of the operation.
 * 
 * Class contains method which gets norm of the vector, method which returns normalized vector, methods which adds and
 * subs two vectors, methods for scalar and vector product of the vectors, method which scale vector with scalar and
 * method which returns cos angle of the vector.
 * 
 * @author Toni Martinčić
 * @version 1.0
 */
public class Vector3 {
	
	/**
	 * X component of the vector.
	 */
	private final double x;
	
	/**
	 * Y component of the vector.
	 */
	private final double y;
	
	/**
	 * Z component of the vector.
	 */
	private final double z;
	
	/**
	 * Constructor which accepts three arguments, x, y and z components of the vector.
	 * 
	 * @param x x component of the vector
	 * @param y y component of the vector
	 * @param z z component of the vector
	 */
	public Vector3(double x, double y, double z) {
		super();
		
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	/**
	 * Method calculates and returns norm of the vector.
	 * 
	 * @return norm of the vector
	 */
	public double norm() {
		return Math.sqrt(x*x + y*y + z*z);
	}
	
	/**
	 * Method returns this normalized vector.
	 * 
	 * @return this normalized vector
	 */
	public Vector3 normalized() {
		return new Vector3(x / norm(), y / norm(), z / norm());
	}
	
	/**
	 * Method adds this vector and the accepted vector and creates and returns new vector which is the result of
	 * adding this and the accepted vector.
	 * 
	 * @param other accepted vector
	 * @return vector which is the result of adding this and the accepted vector
	 * @throws IllegalArgumentException if accepted vector is null value
	 */
	public Vector3 add(Vector3 other) {
		if(other == null) {
			throw new IllegalArgumentException("Other vector can not be null.");
		}
		
		return new Vector3(x + other.getX(), y + other.getY(), z + other.getZ());
	}
	
	/**
	 * Method subtracts this vector and the accepted vector and creates and returns new vector which is the result of
	 * subtracting this and the accepted vector.
	 * 
	 * @param other accepted vector
	 * @return vector which is the result of subtracting this and the accepted vector
	 * @throws IllegalArgumentException if accepted vector is null value
	 */
	public Vector3 sub(Vector3 other) {
		if(other == null) {
			throw new IllegalArgumentException("Other vector can not be null.");
		}
		
		return new Vector3(x - other.getX(), y - other.getY(), z - other.getZ());
	}
	
	/**
	 * Method calculates and returns scalar product of this vector and the accepted vector.
	 * 
	 * @param other accepted vector
	 * @return scalar product of this vector and the accepted vector
	 * @throws IllegalArgumentException if accepted vector is null value
	 */
	public double dot(Vector3 other) {
		if(other == null) {
			throw new IllegalArgumentException("Other vector can not be null.");
		}
		
		return x * other.getX() + y * other.getY() + z * other.getZ();
	}
	
	/**
	 * Method calculates and returns vector product of this vector and the accepted vector.
	 * 
	 * @param other accepted vector
	 * @return vector product of this vector and the accepted vector
	 * @throws IllegalArgumentException if accepted vector is null value
	 */
	public Vector3 cross(Vector3 other) {
		if(other == null) {
			throw new IllegalArgumentException("Other vector can not be null.");
		}
		
		double newX = y * other.z - z * other.y;
		double newY = z * other.x - x * other.z;
		double newZ = x * other.y - y * other.x;
		
		return new Vector3(newX, newY, newZ);
	}
	
	/**
	 * Method scales this vector with accepted scalar and returns new vector which is result of this operation.
	 * 
	 * @param s accepted scalar
	 * @return new vector which is the result of scaling this vector with accepted scalar
	 */
	public Vector3 scale(double s) {
		return new Vector3(x * s, y * s, z * s);
	}
	
	/**
	 * Method calculates and returns cos angle between this and the accepted vectors.
	 * 
	 * @param other accepted vectors
	 * @return cos angle between this and the accepted vectors
	 * @throws IllegalArgumentException if accepted vector is null value
	 */
	public double cosAngle(Vector3 other) {
		if(other == null) {
			throw new IllegalArgumentException("Other vector can not be null.");
		}
		
		return this.dot(other) / (this.norm() * other.norm());
	}
	
	/**
	 * Getter for the x component.
	 * 
	 * @return x component
	 */
	public double getX() {
		return x;
	}
	
	/**
	 * Getter for the y component.
	 * 
	 * @return y component
	 */
	public double getY() {
		return y;
	}
	
	/**
	 * Getter for the z component.
	 * 
	 * @return z component
	 */
	public double getZ() {
		return z;
	}
	
	/**
	 * Method converts this vector into the array which contains all the components of the vector.
	 * 
	 * @return array which contains all the components of the vector
	 */
	public double[] toArray() {
		return new double[]{x, y, z};
	}
	
	@Override
	public String toString() {
		return String.format("(%f, %f, %f)", x, y, z);
	}

}
