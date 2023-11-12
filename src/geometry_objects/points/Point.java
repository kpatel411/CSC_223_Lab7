package geometry_objects.points;

import utilities.math.MathUtilities;

/**
 * A 2D Point (x, y) only.
 * 
 * Points are ordered lexicographically (thus implementing the Comparable interface)
 * 
 * @author xxx
 */
public class Point implements Comparable<Point>
{
	public static final String ANONYMOUS = "__UNNAMED";

	public static final Point ORIGIN;
	static
	{
		ORIGIN = new Point("origin", 0, 0);
	}

	protected double _x;
	public double getX() { return this._x; }

	protected double _y; 
	public double getY() { return this._y; }

	protected String _name; 
	public String getName() { return _name; }

	// BasicPoint objects are named points (from input)
	// ImpliedPoint objects are unnamed points (from input)
	public boolean isGenerated() { return false; }

	/**
	 * Create a new Point with the specified coordinates.
	 * @param x The X coordinate
	 * @param y The Y coordinate
	 */
	public Point(double x, double y)
	{
		this(ANONYMOUS, x, y);
	}

	/**
	 * Create a new Point with the specified coordinates.
	 * @param name -- The name of the point. (Assigned by the UI)
	 * @param x -- The X coordinate
	 * @param y -- The Y coordinate
	 */
	public Point(String name, double x, double y)
	{
		_name = (name == null || name == "") ? ANONYMOUS : name;
		this._x = x;
		this._y = y;
	}

	/**
	 * @return if this point has not user-defined name associated with it
	 */
	public boolean isUnnamed()
	{
		return _name == ANONYMOUS;
	}

	@Override
	public int hashCode()
	{
		return Double.valueOf(MathUtilities.removeLessEpsilon(_x)).hashCode() +
			   Double.valueOf(MathUtilities.removeLessEpsilon(_y)).hashCode();
	}

	/**
	 * 
	 * @param p1 Point 1
	 * @param p2 Point 2
	 * @return Lexicographically: p1 < p2 return -1 : p1 == p2 return 0 : p1 > p2 return 1
	 *         Order of X-coordinates first; order of Y-coordinates second
	 * @throws Exception 
	 */
	public static int LexicographicOrdering(Point p1, Point p2) 
	{
		// TODO
		//use comapareTo to compare which point is greater 
		//go by x then y
		
		if (p1 == null) return -1; 
		if (p2 == null) return 1;
		return p1.compareTo(p2);
	}

	@Override
	public int compareTo(Point that)
	{
		if (that == null) return -2; 
		
		if (this.getX() < that.getX()) return -1;
		if (this.getX() > that.getX()) return 1;
		if (MathUtilities.doubleEquals(this.getX(), that.getX())) {
			if (this.getY() < that.getY()) return -1;
			if (this.getY() > that.getY()) return 1;
			if (MathUtilities.doubleEquals(this.getY(), that.getY())) return 0;
		}
		return -2;
	}
	
	@Override
	public boolean equals(Object obj)
	{
        if (obj == null) return false; 
        if (!(obj instanceof Point)) return false;
        Point that = (Point) obj;
        return MathUtilities.doubleEquals(this._x, that._x) &&
        	   MathUtilities.doubleEquals(this._y, that._y);
        
	}
	public String toString() {
		return this.getName();
	}
}