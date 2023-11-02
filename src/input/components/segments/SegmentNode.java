package input.components.segments;

import input.components.ComponentNode;
import input.components.point.PointNode;
import input.visitor.ComponentNodeVisitor;

//import input.components.point.PointNode;
//import utilities.math.MathUtilities;

/**
 * A utility class only for representing ONE segment
 */
public class SegmentNode implements ComponentNode
{
	protected PointNode _point1;
	protected PointNode _point2;
	
	public PointNode getPoint1() { return _point1; }
	public PointNode getPoint2() { return _point2; }
	
	public SegmentNode(PointNode pt1, PointNode pt2)
	{
		this._point1 = pt1;
		this._point2 = pt2;
		//TODO: figure out how to implement that both points should go both ways 
	}

	@Override
	public boolean equals(Object obj)
	{
		if (obj == null) return false;
		if (!(obj instanceof SegmentNode)) return false;
		SegmentNode that = (SegmentNode)obj;
		return (this.equals(this) && that.equals(that)) && (that.equals(this) && this.equals(that));
	}
	
	@Override
    public String toString()
    {
		return String.format("%s %s", _point1.toString(), _point2.toString());
	}
	@Override
	public Object accept(ComponentNodeVisitor visitor, Object o) {
		return visitor.visitSegmentNode(this, o);
	}
}