import static org.junit.Assert.*;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.junit.Test;
import geometry_objects.Segment;
import geometry_objects.points.Point;
import geometry_objects.points.PointDatabase;
import input.InputFacade;
import input.components.FigureNode;

public class InputFacadeTest {
	
	public Map.Entry<PointDatabase, Set<Segment>> createSimpleTriangle() {
		Point A = new Point("A", 0, 0);
		Point B = new Point("B", 1, 1);
		Point C = new Point("C", 1, 0);
		
		PointDatabase trianglePointsDB = new PointDatabase(Arrays.asList(A, B, C));
		trianglePointsDB.put("A", 0, 0);
		trianglePointsDB.put("B", 1, 1);
		trianglePointsDB.put("C", 1, 0);
		
		Set<Segment> triangleSegments = new HashSet<Segment>();
		triangleSegments.add(new Segment(A, B));
		triangleSegments.add(new Segment(B, C));
		triangleSegments.add(new Segment(A, C));
		
		Map.Entry<PointDatabase, Set<Segment>> testTriangleMap = new AbstractMap.SimpleEntry<PointDatabase, Set<Segment>>(trianglePointsDB, triangleSegments);
		return testTriangleMap;
	}
	
	@Test 
	public void extractFigureTest() {
		FigureNode singleTriangle = InputFacade.extractFigure("single_triangle.json");
		assertTrue(singleTriangle instanceof FigureNode);
	}
	
	@Test
	public void testTriangleToGeoRepresentation() {
		AbstractMap.Entry<PointDatabase, Set<Segment>> testMap = createSimpleTriangle();
		AbstractMap.Entry<PointDatabase, Set<Segment>> geometryMap = InputFacade.toGeometryRepresentation(InputFacade.extractFigure("single_triangle.json"));
		PointDatabase testPointDB = testMap.getKey();
		PointDatabase geoPointDB = geometryMap.getKey();
		Set<Segment> testSegment = testMap.getValue();
		Set<Segment> geoSegment = geometryMap.getValue();
		
		assertEquals(0, testPointDB.getPoint("A").compareTo(geoPointDB.getPoint("A")));
		assertEquals(0, testPointDB.getPoint("B").compareTo(geoPointDB.getPoint("B")));
		assertEquals(0, testPointDB.getPoint("C").compareTo(geoPointDB.getPoint("C")));
		
		assertTrue(testSegment.containsAll(geoSegment));
		assertTrue(geoSegment.containsAll(testSegment));
	}
}
