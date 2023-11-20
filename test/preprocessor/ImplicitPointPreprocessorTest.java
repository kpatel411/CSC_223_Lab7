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

//	@Test
//	void verticalLines(){
//		//A---B--C---D is vertical
//		Point A=new Point("A", 0, -2);
//		Point B=new Point("B", 0, -3);
//		Point C=new Point("C", 0, -5);
//		Point D=new Point("D", 0, -7);
//		
//		PointDatabase pd = new PointDatabase();
//		pd.put("A", 0, -2);
//		pd.put("B", 0, -3);
//		pd.put("C", 0, -5);
//		pd.put("D", 0, -7);
//		
//		List<Segment> segList=new ArrayList<Segment>();
//		segList.add(new Segment (A, D));
//		segList.add(new Segment (B, C));
//		
//		Set<Point> impPts=ImplicitPointPreprocessor.compute(pd, segList);
//		assertTrue(impPts.isEmpty());
//		
//	}
//	@Test
//	void HorizontalLines(){
//		//A---B--C---D
//		Point A=new Point("A", 4, 7);
//		Point B=new Point("B", 64, 7);
//		Point C=new Point("C", 82, 7);
//		Point D=new Point("D", 89, 7);
//		
//		PointDatabase pd = new PointDatabase();
//		pd.put("A", 4, 7);
//		pd.put("B", 64, 7);
//		pd.put("C", 82, 7);
//		pd.put("D", 89, 7);
//		
//		List<Segment> segList=new ArrayList<Segment>();
//		segList.add(new Segment (A, D));
//		segList.add(new Segment (B, C));
//		
//		Set<Point> impPts=ImplicitPointPreprocessor.compute(pd, segList);
//		assertTrue(impPts.isEmpty());
//	}
//	@Test
//	void XwithLines(){
//		/**
//		*	    I
//		*   D   |
//		*    \	|	B
//		* H - \----/-G
//		* 	   \| /
//		*	    \/
//		* E-----/\----F    
//		*      /| \
//		*     A |  C
//		*       J
//		*/
//		Point A=new Point("A", -1, 0);
//		Point B=new Point("B", 3, 3);
//		Point C=new Point("C", 3, 0);
//		Point D=new Point("D", -2, 4);
//		Point E=new Point("E", -2, 1);
//		Point F=new Point("F", 4, 1);
//		Point G=new Point("G", 3, 2);
//		Point H=new Point("H", -1, 2);
//		Point I=new Point("I", 1, 5);
//		Point J=new Point("J", 1, -1);
//		
//		PointDatabase pd = new PointDatabase();
//		pd.put("A", -1, 0);
//		pd.put("B", 3, 3);
//		pd.put("C", 3, 0);
//		pd.put("D", -2, 4);
//		pd.put("E", -2, 1);
//		pd.put("F", 4, 1);
//		pd.put("G", 3, 2);
//		pd.put("H", -1, 2);
//		pd.put("I", 1, 5);
//		pd.put("J", 1, -1);
//		
//		List<Segment> segList=new ArrayList<Segment>();
//		segList.add(new Segment (A, B));
//		segList.add(new Segment (C, D));
//		segList.add(new Segment (E, F));
//		segList.add(new Segment (H, G));
//		segList.add(new Segment (I, J));
//		
//		Set<Point> impPts=ImplicitPointPreprocessor.compute(pd, segList);
//		assertFalse(impPts.isEmpty());
//		assertEquals(9, impPts.size());
//	}
	
	
	@Test
	void RectangleTest(){
		Point A=new Point("A", -.5, 5.5);
		Point B=new Point("B", 2.04, 5.5);
		Point C=new Point("C", 4.19, 5.5);
		Point D=new Point("D", 4.19, 3.99);
		Point E=new Point("E", 4.19, 2.08);
		Point F=new Point("F", 2.04, 2.08);
		Point G=new Point("G", -.5, 2.08);
		Point H=new Point("H", -.5, 3.99);
		
		PointDatabase pd= new PointDatabase();
		pd.put("A", -.5, 5.5);
		pd.put("B", 2.04, 5.5);
		pd.put("C", 4.19, 5.5);
		pd.put("D", 4.19, 3.99);
		pd.put("E", 4.19, 2.08);
		pd.put("F", 2.04, 2.08);
		pd.put("G", -.5, 2.08);
		pd.put("H", -.5, 3.99);
		
		Set<Segment> segmentList=new LinkedHashSet<Segment>();
		
		Segment AG = new Segment (A, G);
		segmentList.add(new Segment (A, C));
		segmentList.add(new Segment (A, G));
		segmentList.add(new Segment (B, F));
		segmentList.add(new Segment (C, E));
		segmentList.add(new Segment (C, G));
		segmentList.add(new Segment (D, H));
		segmentList.add(new Segment (E, G));
		
		Preprocessor pp = new Preprocessor(pd, segmentList);
		
		assertEquals(3, pp._implicitPoints.size());
		
		Set<Segment> iSegments = pp.computeImplicitBaseSegments(pp._implicitPoints);
		Set<Segment> minimalSegments = pp.identifyAllMinimalSegments(pp._implicitPoints, segmentList, iSegments);
		assertEquals(9, iSegments.size());
		assertEquals(17, minimalSegments.size());
		
		Set<Segment> computedNonMinimalSegments = 
		pp.constructAllNonMinimalSegments(pp.identifyAllMinimalSegments(pp._implicitPoints, segmentList, iSegments));
						
		assertEquals(13, computedNonMinimalSegments.size());

		assertEquals(30, pp._segmentDatabase.size());
		
	}


}
