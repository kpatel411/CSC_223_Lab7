package preprocessor;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.*;

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
	}
	
	@Test
	void TestCollectOrderedPointsOnSegment() {
		
	}
}
