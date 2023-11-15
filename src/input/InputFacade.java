package input;

import java.util.AbstractMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import geometry_objects.points.Point;
import geometry_objects.points.PointDatabase;
import geometry_objects.Segment;
import input.builder.GeometryBuilder;
import input.components.FigureNode;
import input.components.point.PointNode;
import input.components.point.PointNodeDatabase;
import input.components.segments.SegmentNode;
import input.components.segments.SegmentNodeDatabase;
import input.parser.JSONParser;

public class InputFacade
{
	/**
	 * A utility method to acquire a figure from the given JSON file:
	 *     Constructs a parser
	 *     Acquries an input file string.
	 *     Parses the file.
	 *
	 * @param filepath -- the path/name defining the input file
	 * @return a FigureNode object corresponding to the input file.
	 */
	public static FigureNode extractFigure(String filepath)
	{
		//        // TODO
		//		//so are we calling parser on a JSON object?
		//		//call the file we want to read and then store the info into a JSON object?
		//		JSONParser fileParser = new JSONParser(new GeometryBuilder());
		//		//THIS IS WRONG...you need to find the file then read
		//		String figureStr = utilities.io.FileUtilities.readFileFilterComments(filepath); 
		//		JSONParser parser = parser.parse(figureStr);
		//		return toGeometryRepresentation(fileParser.);


		JSONParser parser = new JSONParser(new GeometryBuilder());

		String figureStr = utilities.io.FileUtilities.readFileFilterComments(filepath);

		return (FigureNode) parser.parse(figureStr);

	}

	/**
	 * 1) Convert the PointNode and SegmentNode objects to a Point and Segment objects 
	 *    (those classes have more meaningful, geometric functionality).
	 * 2) Return the points and segments as a pair.
	 *
	 * @param fig -- a populated FigureNode object corresponding to a geometry figure
	 * @return a point database and a set of segments
	 */
	public static Map.Entry<PointDatabase, Set<Segment>> toGeometryRepresentation(FigureNode fig)
	{
		PointNodeDatabase pndb = fig.getPointsDatabase();//convert from pointNodeDB to pointdatabase
		SegmentNodeDatabase sndb = fig.getSegments(); //get all segments w/ helper methods
		
		PointDatabase newPDB = convertToPoints (pndb);		
		Set<Segment> newSDB = convertToSegments (sndb, newPDB);
		return new AbstractMap.SimpleEntry<PointDatabase, Set<Segment>>(newPDB, newSDB);
	}

	private static PointDatabase convertToPoints(PointNodeDatabase pndb) {
		PointDatabase newPD = new PointDatabase();
		List<String> pndbNameList = pndb.getAllNodeNames();
		
		//for each name in the PointNodeDatabase
		for (String name: pndbNameList) {
			//getNodeByName(), and create a Point with the same information 
			PointNode node = pndb.getNodeByName(name);
			newPD.put(node.getName(), node.getX(), node.getY());
		}
		return newPD;
	}

	private static Set<Segment> convertToSegments(SegmentNodeDatabase sndb, PointDatabase pdb) {
		Set<Segment> segments = new HashSet<Segment>();
		
		//for each segment in the SegmentNodeDatabase, create Point objects with the same X and Y values 
		for (SegmentNode segNode: sndb.asSegmentList()) {
			Point point1 = pdb.getPoint(new Point(segNode.getPoint1().getX(), segNode.getPoint1().getY()));
			Point point2 = pdb.getPoint(new Point(segNode.getPoint2().getX(), segNode.getPoint2().getY()));
			//if the PointDatabase contains these values, add the segment
			if (pdb.getPoint(point1) != null && pdb.getPoint(point2) != null) {
				segments.add(new Segment(point1, point2));
			}
		}
		return segments;
	}
}