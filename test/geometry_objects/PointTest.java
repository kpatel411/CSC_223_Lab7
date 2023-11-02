package geometry_objects;

import static org.junit.Assert.*;
import org.junit.jupiter.api.Test;
import geometry_objects.points.Point;


public class PointTest {

		//Test Points for Point(double, double) constructor
		Point a = new Point(3, 6);
		Point b = new Point(2, 4);
		Point c = new Point(4.00045, 5.79034);
		Point d = new Point(9.00458, 7.00983);

		Point e = new Point(0, 6);
		Point f = new Point(2, 4.0);
		Point g = new Point(4.00045, 5.79034);
		Point h = new Point(9.00458, 7.0098);
		
		//Test Points for Point(string, double, double) constructor
		Point ax = new Point("A", 3, 6);
		Point bx = new Point("B", 4.00045, 5.79034);
		Point cx = new Point("C", 9.00458, 7.00983);

		Point dx = new Point("D", 0, 0);
		Point ex = new Point("E", 4.00045, 5.79034);
		Point fx = new Point("X", 9.00458, 7.0098);

		
		@Test
		void testIsUnnamed() {
			Point testPointNull = new Point(null, 0, 0);
			assertTrue(testPointNull.isUnnamed());
			
			Point testPoint = new Point("A", 3.0, 6.0);
			assertFalse(testPoint.isUnnamed());
		}
		
		@Test 
		void testHashCode() {
			Point testPoint = new Point("A", 3, 6);
			assertNotEquals(testPoint.hashCode(), 1);
			
			Point testPointNull = new Point(null, 0, 0);
			assertEquals(testPointNull.hashCode(), 0);
		}
		
		@Test
		void testLexicographicOrdering() {
			Point testPoint1 = new Point(null, 0, 0);
			Point testPoint2 = new Point(null, 0, 1);
			Point testPoint3 = new Point(null, 1, 0);
			assertEquals(testPoint1.compareTo(null), -2);
			assertEquals(testPoint1.compareTo(testPoint2), -1);
			assertEquals(testPoint3.compareTo(testPoint1), 1);
			assertEquals(testPoint1.compareTo(testPoint1), 0);
		}
		
		@Test
		void testCompareTo() {
			Point testPoint1 = new Point(null, 0, 0);
			Point testPoint2 = new Point(null, 0, 1);
			Point testPoint3 = new Point(null, 1, 0);
			assertEquals(testPoint1.compareTo(null), -2);
			assertEquals(testPoint1.compareTo(testPoint2), -1);
			assertEquals(testPoint3.compareTo(testPoint1), 1);
			assertEquals(testPoint1.compareTo(testPoint1), 0);
		}
		
		@Test
		void testEquals() {
			//Tests for Point(double, double) constructor
			assertFalse(a.equals(e));
			assertTrue(b.equals(f));
			assertTrue(c.equals(g));
			assertFalse(d.equals(h));
			//Tests for Point(string, double, double) constructor
			assertFalse(ax.equals(dx));
			assertTrue(bx.equals(ex));
			assertFalse(cx.equals(fx));
		}
}
