/**
 * @author Grace Warren, Khushi Patel, and Wick Martin 
 * FigureNode: reads JSON file and reads parsed information to be returned as a 
 * 		final figure formatted as a StringBuilder.
 * 		The FigureNode object is created by the JSONParser class, and is now navigable as a tree
 */

package input.components;

import input.components.point.PointNodeDatabase;
import input.components.segments.SegmentNodeDatabase;
import input.visitor.ComponentNodeVisitor;

/**
 * A basic figure consists of points, segments, and an optional description
 * Each figure has distinct points and segments (thus unique database objects).
 * 
 *  The FigureNode class implements ComponentNode 
 *  	This is the same generic object created in the JSONParser class
 *  	The JSON File has been parsed and is now navigable as a tree, 
 *  	allowing us to print it in a more readable format with the unparse() method
 */ 
public class FigureNode implements ComponentNode
{
	/**
	 * Instantiation of instance variables _description, _points, and _segments,
	 * 		which contain instances of a String, PointNodeDatabase, and SegmentNodeDatabase,
	 * 		created in JSONParser
	 */
	protected String              _description;
	protected PointNodeDatabase   _points;
	protected SegmentNodeDatabase _segments;

	/**
	 * These methods return each instance variable object, which will be helpful in later labs
	 * @return the Description, PointNodeDatabase, and SegmentNodeDatabase objects
	 */
	public String              getDescription()    { return _description; }
	public PointNodeDatabase   getPointsDatabase() { return _points; }
	public SegmentNodeDatabase getSegments()       { return _segments; }

	/**
	 * FigureNode() constructor 
	 * @param description contains the description of the figure
	 * @param points contains the PointNode points within the figure as a PointNodeDatabase
	 * @param segments contains the SegmentNode segments within the figure as a SegmentNodeDatabase
	 */
	public FigureNode(String description, PointNodeDatabase points, SegmentNodeDatabase segments)
	{
		_description = description;
		_points = points;
		_segments = segments;
	}
	
	@Override 
	public Object accept(ComponentNodeVisitor visitor, Object o) {
		return visitor.visitFigureNode(this, o);
	}
}