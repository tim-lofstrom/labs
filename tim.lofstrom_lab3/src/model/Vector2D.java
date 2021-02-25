package model;

/**
 * Represents a 2D-vector
 * @author kurt
 */
public class Vector2D {

	double x;
	double y;
	
	/**
	 * Creates a vector with x and y
	 * @param x
	 * @param y
	 */
	public Vector2D(double x, double y){
		this.x = x;
		this.y = y;
	}
	
	/**
	 * Creates a unitvector according to an angle
	 * @param angle in radians
	 */
	public Vector2D(double radian){
		x = Math.cos(radian);
		y = Math.sin(radian);
	}
	
	/**
	 * @return double : x -cordinate
	 */
	public double getX(){
		return x;
	}
	
	/**
	 * @return double : y -cordinate
	 */
	public double getY(){
		return y;
	}

	/**
	 * Vector addition
	 * @param v : Vector2D
	 * @return Vector2D : new Vector2D
	 */
	public Vector2D add(Vector2D v) {
		return new Vector2D(x + v.x, y + v.y);
	}
	
	/**
	 * Calculates the dot-product of two vectors
	 * @param v : Vector2D
	 * @return Vector2D : new Vector2D
	 */
	public Vector2D dot(Vector2D v) {
		return new Vector2D(x*v.x, y*v.y);
	}

	/**
	 * Calculate a vector times a scalar
	 * @param scalar: double
	 * @return Vector2D : new Vector2D
	 */
	public Vector2D dot(double scalar) {
		return new Vector2D(x*scalar, y*scalar);
	}

	
	/**
	 * A string representation of the Vector2D
	 */
	public String toString(){
		return Double.toString(x) + ":" + Double.toString(y);
	}
	
	public boolean compare(Vector2D v){
		return (v.getX() == this.x) && (v.getY() == this.y);
	}

	public String toInt() {
		return Double.toString((int)x) + ":" + Double.toString((int)y);
	}
	
}
