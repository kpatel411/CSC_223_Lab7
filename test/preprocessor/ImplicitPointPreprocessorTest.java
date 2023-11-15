package preprocessor;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.junit.jupiter.api.Test;

import geometry_objects.Segment;
import geometry_objects.points.Point;
import geometry_objects.points.PointDatabase;
import preprocessor.delegates.ImplicitPointPreprocessor;

class ImplicitPointPreprocessorTest {

	@Test
	void basicTest() {
		//creates points
		Point A=new Point("A", 0, 4);
		Point B=new Point("B", -1, 2);
		Point C=new Point("C", 1, 2);
		Point D=new Point("D", -2, 0);
		Point E=new Point("E", 2, 0);
		
		//creates and then adds points to database
		PointDatabase pd= new PointDatabase();
		pd.put("A", 0, 4);
		pd.put("B", -1, 2);
		pd.put("C", 1, 2);
		pd.put("D", -2, 0);
		pd.put("E", 2, 0);
		//adds segments to segment list
		Set<Segment> segmentList=new LinkedHashSet<Segment>();
		segmentList.add(new Segment(A,B));
		segmentList.add(new Segment(A,C));
		segmentList.add(new Segment(B,C));
		segmentList.add(new Segment(B,D));
		segmentList.add(new Segment(B,E));
		segmentList.add(new Segment(C,D));
		segmentList.add(new Segment(C,E));
		segmentList.add(new Segment(D,E));
		Preprocessor pp = new Preprocessor(pd, segmentList);
		
		Set<Point> impPts= pp._implicitPoints;
		assertEquals(1, impPts.size());
		
		Set<Segment> iSegments = pp.computeImplicitBaseSegments(impPts);
		assertEquals(4, iSegments.size());
		
		Set<Segment> minimalSegments = pp.identifyAllMinimalSegments(impPts, segmentList, iSegments);
		assertEquals(10, minimalSegments.size());
		
		Set<Segment> computedNonMinimalSegments = 
		pp.constructAllNonMinimalSegments(minimalSegments);
		
		assertEquals(4, computedNonMinimalSegments.size());
		
		for (Entry<Segment, Segment> s: pp._segmentDatabase.entrySet()) {
			System.out.print(s.getKey().getPoint1().getName() + s.getKey().getPoint2().getName() + ", ");
		}
	}
	@Test
	void AlvinTest() {
		Point A=new Point("A", 0, 0);
		Point B=new Point("B", 4, 0);
		Point C=new Point("C", 6, 3);
		Point D=new Point("D", 3, 7);
		Point E=new Point("E", -2, 4);
		Point F=new Point("F", 26, 0);
		
		PointDatabase pd= new PointDatabase();
		pd.put("A", 0, 0);
		pd.put("B", 4, 0);
		pd.put("C", 6, 3);
		pd.put("D", 3, 7);
		pd.put("E", -2, 4);
		pd.put("F", 26, 0);
		
		Set<Segment> segmentList=new LinkedHashSet<Segment>();
		segmentList.add(new Segment(A, B));
		segmentList.add(new Segment(A, C));
		segmentList.add(new Segment(A, D));
		segmentList.add(new Segment(A, E));
		segmentList.add(new Segment(B, C));
		segmentList.add(new Segment(B, D));
		segmentList.add(new Segment(B, E));
		segmentList.add(new Segment(C, D));
		segmentList.add(new Segment(C, E));
		segmentList.add(new Segment(D, E));
		
		Preprocessor pp = new Preprocessor(pd, segmentList);
		
		// 5 new implied points inside the pentagon
		assertEquals(5, pp._implicitPoints.size());
		
		Set<Segment> iSegments = pp.computeImplicitBaseSegments(pp._implicitPoints);
		Set<Segment> minimalSegments = pp.identifyAllMinimalSegments(pp._implicitPoints, segmentList, iSegments);
		assertEquals(15, iSegments.size());
		assertEquals(20, minimalSegments.size());
		
		Set<Segment> computedNonMinimalSegments = pp.constructAllNonMinimalSegments(minimalSegments);
		
		//
		// All Segments will consist of the new 15 non-minimal segments.
		//
		assertEquals(15, computedNonMinimalSegments.size());
		
	}
//	@Test
//	void RectangleTest(){
//		Point A=new Point("A", -.5, 5.5);
//		Point B=new Point("B", 2.04, 5.5);
//		Point C=new Point("C", 4.19, 5.5);
//		Point D=new Point("D", 4.19, 3.99);
//		Point E=new Point("E", 4.19, 2.08);
//		Point F=new Point("F", 2.04, 2.08);
//		Point G=new Point("G", -.5, 2.08);
//		Point H=new Point("H", -.5, 3.99);
//		
//		PointDatabase pd= new PointDatabase();
//		pd.put("A", -.5, 5.5);
//		pd.put("B", 2.04, 5.5);
//		pd.put("C", 4.19, 5.5);
//		pd.put("D", 4.19, 3.99);
//		pd.put("E", 4.19, 2.08);
//		pd.put("F", 2.04, 2.08);
//		pd.put("G", -.5, 2.08);
//		pd.put("H", -.5, 3.99);
//		
//		Set<Segment> segmentList=new LinkedHashSet<Segment>();
//		
//		Segment AG = new Segment (A, G);
//		segmentList.add(new Segment (A, C));
//		segmentList.add(new Segment (A, G));
//		segmentList.add(new Segment (B, F));
//		segmentList.add(new Segment (C, E));
//		segmentList.add(new Segment (C, G));
//		segmentList.add(new Segment (D, H));
//		segmentList.add(new Segment (E, G));
//		
//		Preprocessor pp = new Preprocessor(pd, segmentList);
//		
//		System.out.println(AG.pointLiesBetweenEndpoints(H));
//		// 5 new implied points inside the pentagon
//		assertEquals(3, pp._implicitPoints.size());
////		for (Point p : pp._implicitPoints) {
////			System.out.println(450);
////			System.out.println(p.getX() +"," +p.getY());
////		}
//		
//		Set<Segment> iSegments = pp.computeImplicitBaseSegments(pp._implicitPoints);
//		Set<Segment> minimalSegments = pp.identifyAllMinimalSegments(pp._implicitPoints, segmentList, iSegments);
//		assertEquals(9, iSegments.size());
//		assertEquals(17, minimalSegments.size());
//		
//		Set<Segment> computedNonMinimalSegments = 
//		pp.constructAllNonMinimalSegments(pp.identifyAllMinimalSegments(pp._implicitPoints, segmentList, iSegments));
//						
//		assertEquals(10, computedNonMinimalSegments.size());
//		//
//		// All Segments will consist of the new 15 non-minimal segments.
//		//
//		assertEquals(15, computedNonMinimalSegments.size());
//		
//	}


}
