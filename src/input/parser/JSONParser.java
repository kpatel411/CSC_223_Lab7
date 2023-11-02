/**
 * @author Grace Warren, Khushi Patel, and Wick Martin 
 * JSONParser: takes JSON file and stores information to be read in FigureNode.
 * takes strings and node objects to be returned as a complete figure.
 */

package input.parser;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;
import input.builder.DefaultBuilder;
import input.components.*;
import input.components.point.PointNode;
import input.components.point.PointNodeDatabase;
import input.components.segments.SegmentNodeDatabase;
import input.exception.ParseException;

/**
 * Public class JSONParser: takes a JSON file as input and stores information to be read in FigureNode.
 * Takes strings and node objects to be returned as a complete figure.
 */
public class JSONParser
{
	protected ComponentNode  _astRoot;
	protected DefaultBuilder _builder;

	public JSONParser(DefaultBuilder builder)
	{
		_astRoot = null;
		_builder = builder;
	}

	private void error(String message)
	{
		throw new ParseException("Parse error: " + message);
	}

	public ComponentNode parse(String str) throws ParseException
	{
		//Parsing is accomplished via the JSONTokenizer class 
		JSONTokener tokenizer = new JSONTokener(str);
		JSONObject  JSONroot = (JSONObject)tokenizer.nextValue();

		//Checks for validity/existence of figure
		if (!JSONroot.has("Figure")) {
			error("No figure found.");
		}

		JSONObject figure = JSONroot.getJSONObject("Figure");

		//Creates figure description
		String description = figure.getString("Description");
		JSONArray pndb = figure.getJSONArray("Points");
		JSONArray sndb = figure.getJSONArray("Segments");

		//Creates figure PointNodeDatabase and SegmentNodeDatabase
		PointNodeDatabase pointNodeDatabase = readsPNDB(pndb);
		SegmentNodeDatabase segmentNodeDatabase = readsSNDB(sndb, pointNodeDatabase);
		
		//Creates FigureNode using _builder
		_astRoot = _builder.buildFigureNode(description, pointNodeDatabase, segmentNodeDatabase);
		return _astRoot;
	}

	/**
	 * readsPNDB method
	 * @param pndbArray is an array of PointNode objects
	 * @return a PointNodeDatabase as constructed with _builder
	 */
	public PointNodeDatabase readsPNDB(JSONArray pndbArray) {

		List<PointNode> pointNodeDB = new ArrayList<PointNode>();
		
		if (pndbArray == null) return _builder.buildPointDatabaseNode(pointNodeDB);
		
		//loops through the PointNode objects
		for (int index = 0; index < pndbArray.length(); index++) {
			//creates PointNode by calling readsPoint, adds to database if valid
			PointNode point = readsPoint((JSONObject)pndbArray.get(index));
			if (point != null) pointNodeDB.add(point);
		}
		return _builder.buildPointDatabaseNode(pointNodeDB);
	}
	
	/**
	 * readsPoint() method
	 * @param pointObj contains a single PointNode object
	 * @return a PointNode as constructed with _builder
	 */
	public PointNode readsPoint(JSONObject pointObj) {
		//gets the components of the PointNode object and builds a PointNode
		String name = pointObj.getString(JSON_Constants.JSON_NAME);
		double x = pointObj.getDouble(JSON_Constants.JSON_X);
		double y = pointObj.getDouble(JSON_Constants.JSON_Y);
		return _builder.buildPointNode(name, x, y);
	}
	
	/**
	 * readsSNDB() method
	 * @param sndbArray contains an array of SegmentNodes
	 * @param pndb contains the PointNodeDatabase of this FigureNode
	 * @return a SegmentNodeDatabase as constructed with _builder
	 */
	public SegmentNodeDatabase readsSNDB(JSONArray sndbArray, PointNodeDatabase pndb) {
		SegmentNodeDatabase segmentNodeDB = _builder.buildSegmentNodeDatabase();
		
		if (sndbArray == null) return segmentNodeDB;
		
		//loops through each adjacency list, creating PointNode objects as it goes
		for (Object adjList : sndbArray) {
			//get the key of an adjacency list, and get that node
			JSONObject currentAdjList = (JSONObject) adjList;
			String key = currentAdjList.keys().next();
			
			if (pndb == null) return null;
			PointNode pointOne = pndb.getNodeByName(key);
			//null check before pointOne and addAdjacency
			
			//make a list of all the edges
			JSONArray segmentPoints = currentAdjList.getJSONArray(key);
			List<PointNode> edgeList = readsSegment(segmentPoints, pndb);
			
			//add those edges as an adjacency list 
			if (edgeList != null) {
				segmentNodeDB.addAdgacencyList(pointOne, edgeList);
				edgeList.clear();
			}
		}
		return segmentNodeDB;
	}
	
	/**
	 * readsSegment() method
	 * @param edges contains a list of PointNodes which connect to the key of an adjacency list
	 * @param pndb contains the PointNodeDatabase of this FigureNode
	 * @return a SegmentNode as constructed with _builder
	 */
	public List<PointNode> readsSegment(JSONArray edges, PointNodeDatabase pndb) {
		List<PointNode> edgePoints = new ArrayList<PointNode>();
		
		//loop through each edge node for a given adjacency list
		for(Object edge : edges) {
			//create a PointNode, and add it to a list 
			String currentEdge = (String) edge;
			PointNode currPoint = pndb.getNodeByName(currentEdge);
			edgePoints.add(currPoint);
		}
		return edgePoints;
	}
}