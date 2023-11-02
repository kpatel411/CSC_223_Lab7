package input.components.point;

import input.components.ComponentNode;
import utilities.math.MathUtilities;
import input.visitor.ComponentNodeVisitor;

/**
 * A 2D Point (x, y).
 */
public class PointNode implements ComponentNode 
{
	protected static final String ANONYMOUS = "__UNNAMED";

	protected double _x;
	public double getX() { return this._x; }

	protected double _y; 
	public double getY() { return this._y; }

	protected String _name; 
	public String getName() { return _name; }

	/**
	 * Create a new Point with the specified coordinates.
	 * @param x The X coordinate
	 * @param y The Y coordinate
	 */
	public PointNode(double x, double y)
	{
		this._name = ANONYMOUS;
		this._x = x;
		this._y = y;
		//TODO: as if one constructor should call the other or not 
	}

	/**
	 * Create a new Point with the specified coordinates.
	 * @param name -- The name of the point. (Assigned by the UI)
	 * @param x -- The X coordinate
	 * @param y -- The Y coordinate
	 */
	public PointNode(String name, double x, double y)
	{
		this._name = name;
		this._x = x;
		this._y = y;
	}

	@Override
	public int hashCode()
	{
		return Double.valueOf(_x).hashCode() + Double.valueOf(_y).hashCode();
	}

	@Override
	public boolean equals(Object obj)
	{
		if (obj == null) return false;
		if (!(obj instanceof PointNode)) return false;
		PointNode that = (PointNode)obj;
		return MathUtilities.doubleEquals(this._x, that._x) &&
			   MathUtilities.doubleEquals(this._y, that._y);
			
	}

    @Override
    public String toString()
    {
		return String.format("%s(%.0f, %.0f)", this.getName(), this.getX(), this.getY());
	}

	@Override
	public Object accept(ComponentNodeVisitor visitor, Object o) {
		return visitor.visitPointNode(this, o);
	}
}