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
	
//	Point H=new Point(“H”, -1, -1);
//	Point I=new Point(“I”, 5, 1);
//	Point J=new Point(“J”, -1, Math.sqrt(2));
//	Point K=new Point(“K”, -1, 3);
//	Point L=new Point(“L”, 2, -2);
//	Point M=new Point(“M”, 4, 2);
//	Point N=new Point(“N”, 10, -1);
//	Point O=new Point(“O”, 4, -1);
//	Point AA=new Point(“AA”, 7, -1);
//	Point BB=new Point(“BB”, 8, -3);
//	Point CC=new Point(“CC”, 3, 3);
	
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
		assertFalse(PQ.coincideWithoutOverlap(QR));
		
		Point A2=new Point(-2, 0);
		Point B2=new Point(0, 3);
		Point C2=new Point(2, 6);
		Point D2=new Point(4, 9);
		Point E2=new Point(-1, 1.5);
		
		Point F2=new Point(-1,0);
		Point G2=new Point(1, 3);
		
		Segment AB2=new Segment(A2, B2);
		Segment CD2=new Segment(C2, D2);
		Segment EC2=new Segment(E2, C2);
		Segment BD2=new Segment(B2, D2);
		Segment FG2=new Segment(F2, G2);
		//tested with a slope
		//A2--E2--B2---C2---D2
		assertTrue (AB2.coincideWithoutOverlap(CD2));
		assertTrue (CD2.coincideWithoutOverlap(AB2));
		assertTrue(AB2.coincideWithoutOverlap(BD2));
		assertTrue(BD2.coincideWithoutOverlap(AB2));
		
		assertFalse(AB2.coincideWithoutOverlap(EC2));
		assertFalse(EC2.coincideWithoutOverlap(AB2));
		
		
		//tests same slope different y-int
		assertTrue(AB2.coincideWithoutOverlap(FG2));
		assertTrue(FG2.coincideWithoutOverlap(AB2));
		
		
//		Segment HI=new Segment(H, I);
//		Segment IN=new Segment(I, N);
//		Segment OAA=new Segment(O, AA);
//		Segment JK=new Segment(J, K);
//		Segment LM=new Segment(L, M);
//		Segment CCBB=new Segment(CC, BB);
//		
//		assertTrue(HI.coincideWithoutOverlap(JK));
//		assertTrue(HI.coincideWithoutOverlap(IN));
//		assertTrue(HI.coincideWithoutOverlap(CCBB));
//		
//		assertFalse(HI.coincideWithoutOverlap(OAA));
//		assertFalse(HI.coincideWithoutOverlap(LM));
		
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
		
		//    /P
		//   /
		//  /Q
		//  \
		//   \
		//    \R
		Segment PQ = new Segment(P, Q);
		HashSet<Point> setX = new HashSet<Point>();
		setX.add(P);
		setX.add(Q);
		HashSet<Point> setY = new HashSet<Point>();
		setX.add(Q);
		setY.add(R);
		assertNotEquals(PQ.collectOrderedPointsOnSegment(setX),setY);				
	}
}