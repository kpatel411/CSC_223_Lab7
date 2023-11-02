package input.visitor;

import org.json.*;
import input.components.FigureNode;
import input.components.point.PointNode;
import input.components.point.PointNodeDatabase;
import input.components.segments.SegmentNode;
import input.components.segments.SegmentNodeDatabase;

/**
 * The purpose of this class is to build JSON objects to be returned as output by adding them 
 * into an array and returning the structure of the JSON file as an object. visitFigureNode puts
 * all elements(description, points, segments) in the figure into a JSON Object. 
 * visitSegementNode and visitPointNode both add rudimentary elements to build a segment and 
 * point respectively. 
 * visitSegementDatabaseNode and visitPointNodeDatabase both take segment and point objects and 
 * add them to a JSON Array object that returns the amount of segments and points used to build a figure. 
 */

//TODO: check with default builder 
//TODO: check that JSONVisitor does create JSONObject properly 
	//see if there's an equals method? depth based?
	//check with original file and produced FigureNode
public class ToJSONVisitor implements ComponentNodeVisitor {
	/**
	 * creates a figure Node object by adding description, points, & segments from file/db
	 */
	@Override
	public Object visitFigureNode(FigureNode node, Object o) {
		JSONObject figureNodeObj = new JSONObject();
		figureNodeObj.put("Description", node.getDescription());
		figureNodeObj.put("Points", node.getPointsDatabase().accept(this, o));
		figureNodeObj.put("Segments", node.getSegments().accept(this, o));	
		return figureNodeObj;
	}

	/**
	 * creates a segment Node object by adding segments from file/db and into an array to be returned as a JSON object
	 */
	@Override
	public Object visitSegmentDatabaseNode(SegmentNodeDatabase node, Object o) {
		JSONArray sndbArray = new JSONArray();
		for (PointNode adjList : node.getAdjLists().keySet()) {
			for (PointNode adjListNameNode : node.getAdjLists().get(adjList)) {
				sndbArray.put(visitSegmentNode(new SegmentNode(adjList, adjListNameNode), o));
			}			
		}
		return sndbArray;
	}
	
	/**
	 * visits a segment Node object by adding 2 points and builds a segment from point db to be returned as a JSON object
	 */
	@Override
	public Object visitSegmentNode(SegmentNode node, Object o) {
		JSONObject segmentObj = new JSONObject();
		segmentObj.put("Node1", node.getPoint1());
		segmentObj.put("Node2", node.getPoint2());
		return segmentObj;
	}

	/**
	 * visits a point Node object by adding a name and 2 coordinates(x,y) and builds a point object to be returned as a JSON object
	 */
	@Override
	public Object visitPointNode(PointNode node, Object o) {
		JSONObject nodeObj = new JSONObject();
		nodeObj.put("Name", node.getName());
		nodeObj.put("X", node.getX());
		nodeObj.put("Y", node.getY());
		return nodeObj;
	}

	/**
	 * visits a point Node object by adding points from file/db and into an array to be returned as a JSON object
	 */
	@Override
	public Object visitPointNodeDatabase(PointNodeDatabase node, Object o) {
		JSONArray pndbArray = new JSONArray();
		for (String pointNodeName : node.getAllNodeNames()) {
			PointNode pn = node.getNodeByName(pointNodeName);
			pndbArray.put(visitPointNode(pn, o));	
		}		
		return pndbArray;
	}
	
}
