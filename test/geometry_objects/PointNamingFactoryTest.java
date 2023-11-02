package geometry_objects;

import static org.junit.Assert.*;
import org.junit.jupiter.api.Test;
import geometry_objects.points.Point;
import geometry_objects.points.PointNamingFactory;

public class PointNamingFactoryTest {
	
	//test the constructor to ensure that it initializes correctly 
	@Test
	void testInitilization() {
		PointNamingFactory PNF = new PointNamingFactory(); 
		
		//asserts that the PNF object is not null
		assertNotNull(PNF);
		
	}
	
	//test put(point pt)
	@Test
	void insertionPointTest() {
		PointNamingFactory PNF = new PointNamingFactory();
		PNF.put(new Point("A", 2.0, 3.0));
		Point putPointTester = new Point("B", 2.0, 4.0);
		PNF.put(putPointTester);
		PNF.put(3.0, 7.0);
		
		assertTrue(PNF.contains(2.0, 3.0)); 
		assertTrue(PNF.contains(new Point(3.0, 7.0))); 
		assertTrue(PNF.contains(putPointTester));
	}
	
	//test put(double x, double y) 
	@Test
	void insertionDoubleTest() { 
		PointNamingFactory PNF = new PointNamingFactory();
		PNF.put(2.0, 5.0); 
		PNF.put(3.0, 6.0);
		PNF.put(4.0, 7.0);
		
		assertTrue(PNF.contains(2.0, 5.0));
		assertTrue(PNF.contains(3.0, 6.0));
		assertTrue(PNF.contains(4.0, 7.0));
	}
	
	@Test
	void getPointTest() {
		PointNamingFactory PNF = new PointNamingFactory();
		Point point1 = new Point(2.0, 5.0);
		Point point2 = new Point(3.0, 6.0);
		Point point3 = new Point(4.0, 7.0);
		PNF.put(point1); 
		PNF.put(point2);
		PNF.put(point3);
		
		assertEquals(PNF.get(point1), point1);
		assertEquals(PNF.get(point2), point2);
		assertEquals(PNF.get(point3), point3);
	}
	
	@Test 
	void getDoubleTest() {
		PointNamingFactory PNF = new PointNamingFactory();
		PNF.put(new Point(2.0, 5.0)); 
		PNF.put(new Point(3.0, 6.0));
		PNF.put(new Point(4.0, 7.0));
		
		assertEquals(PNF.get(2.0, 5.0), new Point(2.0, 5.0));
		assertEquals(PNF.get(3.0, 6.0), new Point(3.0, 6.0));
		assertEquals(PNF.get(4.0, 7.0), new Point(4.0, 7.0));
	}
	
	@Test 
	void containsPointTest() {
		PointNamingFactory PNF = new PointNamingFactory();
		PNF.put(new Point(2.0, 5.0)); 
		PNF.put(new Point(3.0, 6.0));
		PNF.put(new Point(4.0, 7.0));
		
		assertEquals(PNF.get(2.0, 5.0), new Point(2.0, 5.0));
		assertEquals(PNF.get(3.0, 6.0), new Point(3.0, 6.0));
		assertEquals(PNF.get(4.0, 7.0), new Point(4.0, 7.0));
	}
	
	@Test 
	void containsDoubleTest() {
		PointNamingFactory PNF = new PointNamingFactory();
		PNF.put(new Point(2.0, 5.0)); 
		PNF.put(new Point(3.0, 6.0));
		PNF.put(new Point(4.0, 7.0));
		
		assertTrue(PNF.contains(2.0, 5.0));
		assertTrue(PNF.contains(3.0, 6.0));
		assertTrue(PNF.contains(4.0, 7.0));
	}
	
	@Test 
	void getAllPointsTest() {
		PointNamingFactory PNF = new PointNamingFactory();
		PNF.put(new Point(2.0, 5.0)); 
		PNF.put(new Point(3.0, 6.0));
		PNF.put(new Point(4.0, 7.0));
		
		assertEquals(PNF.getAllPoints().size(), 3);
	}
	
	@Test 
	void updateNameGetCurrentNameCombinedTest() {
		PointNamingFactory PNF = new PointNamingFactory();
		PNF.put(new Point(1.0, 28.0));
		PNF.put(new Point(2.0, 29.0));
		PNF.put(new Point(3.0, 30.0));
		PNF.put(new Point(4.0, 31.0));
		PNF.put(new Point(5.0, 32.0));
		PNF.put(new Point(6.0, 33.0));
		PNF.put(new Point(7.0, 34.0));
		PNF.put(new Point(8.0, 35.0));
		PNF.put(new Point(9.0, 36.0));
		PNF.put(new Point(10.0, 37.0));
		PNF.put(new Point(11.0, 38.0));
		PNF.put(new Point(12.0, 39.0));
		PNF.put(new Point(13.0, 40.0));
		PNF.put(new Point(14.0, 41.0));
		PNF.put(new Point(15.0, 42.0));
		PNF.put(new Point(16.0, 43.0));
		PNF.put(new Point(17.0, 44.0));
		PNF.put(new Point(18.0, 45.0));
		PNF.put(new Point(19.0, 46.0));
		PNF.put(new Point(20.0, 47.0));
		PNF.put(new Point(21.0, 48.0));
		PNF.put(new Point(22.0, 49.0));
		PNF.put(new Point(23.0, 50.0));
		PNF.put(new Point(24.0, 51.0));
		PNF.put(new Point(25.0, 52.0));
		PNF.put(new Point(26.0, 53.0));
		PNF.put(new Point(27.0, 54.0));
		PNF.put(new Point("A", 28.0, 29.0));

		assertEquals(PNF.get(new Point(1.0, 28.0)).getName(), "*_A");
		assertEquals(PNF.get(new Point(26.0, 53.0)).getName(), "*_Z");
		assertEquals(PNF.get(new Point(27.0, 54.0)).getName(), "*_AA");
		assertEquals(PNF.get(new Point(28.0, 29.0)).getName(), "A");
	}
	
	
	@Test 
	void clearTest() {
		PointNamingFactory PNF = new PointNamingFactory();
		PNF.put(new Point(2.0, 5.0)); 
		PNF.put(new Point(3.0, 6.0));
		PNF.put(new Point(4.0, 7.0));
		
		PNF.clear();
		assertEquals(PNF.size(), 0);
	}
	
	@Test 
	void sizeTest() {
		PointNamingFactory PNF = new PointNamingFactory();
		PNF.put(new Point(2.0, 5.0)); 
		PNF.put(new Point(3.0, 6.0));
		PNF.put(new Point(4.0, 7.0));
		
		assertEquals(PNF.size(), 3);
	}
}
