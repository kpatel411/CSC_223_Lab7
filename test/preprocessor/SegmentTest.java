package preprocessor;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.*;

import java.util.HashSet;

import org.junit.jupiter.api.Test;

import geometry_objects.Segment;
import geometry_objects.points.Point;

class SegmentTest {

	Point A = new Point(1, 2);
	Point B = new Point(2, 2);
	Point C = new Point(3.5, 2);
	Point D = new Point(4, 2);
	Point E = new Point(4.75, 2);
	
	Point F = new Point(5, 4);
	Point G = new Point(4.5, 2);
	
	Point W = new Point(4, 10);
	Point X = new Point(3, 9);
	Point Y = new Point(4, 8);
	Point Z = new Point(2, 10);
	
	Point P = new Point(9, 10);
	Point Q = new Point(8, 9);
	Point R = new Point(9, 8);

	@Test
	void TestCoincideWithoutOverlap() {
		//no overlap, has empty space between
		Segment AB = new Segment(A, B);
		Segment CD = new Segment(C, D);
		assertTrue(AB.coincideWithoutOverlap(CD));
		//share a point
		Segment DE = new Segment(D, E);
		assertTrue(CD.coincideWithoutOverlap(DE));
		//overlapping segments
		Segment EG = new Segment(E, G);
		assertFalse(DE.coincideWithoutOverlap(EG));
		
		//some cases concerning overlap
		//  \  /
		//   \/
		//    \
		//     \
		Segment ZY = new Segment(Z, Y);
		Segment XW = new Segment(X, W);
		assertFalse(ZY.coincideWithoutOverlap(XW));
		
		//    /
		//   /
		//  /
		//  \
		//   \
		//    \
		Segment PQ = new Segment(P, Q);
		Segment QR = new Segment(Q, R);
		assertTrue(PQ.coincideWithoutOverlap(QR));
		
	}
	
	@Test
	void TestCollectOrderedPointsOnSegment() {
		Segment AC = new Segment(A, C);
		HashSet<Point> setA = new HashSet<Point>(); 
			setA.add(A);
			setA.add(C);
			setA.add(Q);
		HashSet<Point> setB = new HashSet<Point>(); 
			setB.add(A);
			setB.add(B);
			setB.add(C);
		// a point not on the segment
		assertNotEquals(AC.collectOrderedPointsOnSegment(setA),setB);
		// same segment and points
		HashSet<Point> setC = new HashSet<Point>(); 
		setC.add(A);
		setC.add(C);
		setC.add(Q);
		HashSet<Point> setD = new HashSet<Point>(); 
		setD.add(A);
		setD.add(B);
		setD.add(C);
		setD.add(Q);
		assertNotEquals(AC.collectOrderedPointsOnSegment(setA),setC);
		assertEquals(AC.collectOrderedPointsOnSegment(setB), setB);
		assertEquals(AC.collectOrderedPointsOnSegment(setD), setB);
	}
}
