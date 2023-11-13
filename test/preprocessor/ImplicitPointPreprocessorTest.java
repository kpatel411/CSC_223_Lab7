package preprocessor;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Test;

import geometry_objects.Segment;
import geometry_objects.points.Point;
import geometry_objects.points.PointDatabase;
import preprocessor.delegates.ImplicitPointPreprocessor;

class ImplicitPointPreprocessorTest {

//	@Test
//	void basicTest() {
//		//creates points
//		Point A=new Point("A", 0, 4);
//		Point B=new Point("B", -1, 2);
//		Point C=new Point("C", 1, 2);
//		Point D=new Point("D", -2, 0);
//		Point E=new Point("E", 2, 0);
//		
//		//creates and then adds points to database
//		PointDatabase pd= new PointDatabase();
//		pd.put("A", 0, 4);
//		pd.put("B", -1, 2);
//		pd.put("C", 1, 2);
//		pd.put("D", -2, 0);
//		pd.put("E", 2, 0);
//		//adds segments to segment list
//		ArrayList<Segment> segmentList=new ArrayList<Segment>();
//		segmentList.add(new Segment(A,B));
//		segmentList.add(new Segment(A,C));
//		segmentList.add(new Segment(B,C));
//		segmentList.add(new Segment(B,D));
//		segmentList.add(new Segment(B,E));
//		segmentList.add(new Segment(C,D));
//		segmentList.add(new Segment(C,E));
//		segmentList.add(new Segment(D,E));
//		Set<Point> implicitPoints = ImplicitPointPreprocessor.compute(pd, segmentList);
//		//prints implicitPoints
//		//System.out.println(implicitPoints.toString());
//		assertEquals(implicitPoints.size(), 1);
//	}
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
		
		ArrayList<Segment> segmentList=new ArrayList<Segment>();
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
		
		Set<Point> implicitPoints = ImplicitPointPreprocessor.compute(pd, segmentList);
		assertEquals(implicitPoints.size(), 5);
		
	}

}
