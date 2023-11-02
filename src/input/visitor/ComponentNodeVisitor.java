package input.visitor;

import input.components.*;
import input.components.point.*;
import input.components.segments.SegmentNode;
import input.components.segments.SegmentNodeDatabase;

/**
 * All of the following are ComponentNode Classes
 *    * FigureNode
 *    * PointNode
 *    * PointNodeDatabase
 *    * SegmentNodeDatabase
 *    
 *    Depending on implementation, SegmentNode may be a ComponentNode.
 *    
 *    For each ComponentNode, we need a visit method as established in this interface.
 */
public interface ComponentNodeVisitor
{
	Object visitFigureNode(FigureNode node, Object o);

	Object visitSegmentDatabaseNode(SegmentNodeDatabase node, Object o);
	
	Object visitSegmentNode(SegmentNode node, Object o);
	
	Object visitPointNode(PointNode node, Object o);

	Object visitPointNodeDatabase(PointNodeDatabase node, Object o);
}