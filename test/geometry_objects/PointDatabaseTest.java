package geometry_objects;

import static org.junit.Assert.*;
import org.junit.jupiter.api.Test;
import geometry_objects.points.Point;
import geometry_objects.points.PointDatabase;
//import input.components.point.Point;
//import input.components.point.PointDatabase;

public class PointDatabaseTest {
	
	@Test
	void testSize() {
		PointDatabase testDatabase = new PointDatabase();
		testDatabase.put("A", 3.0, 6.0);
		testDatabase.put("B", 2.0, 4.0);
		testDatabase.put("C", 4.1, 4.2);
		testDatabase.put("D", 5, 6.9);
		assertEquals(4, testDatabase.size());
		
		PointDatabase testDatabaseEmpty = new PointDatabase();
		assertEquals(0, testDatabaseEmpty.size());
		
		PointDatabase testDatabaseNull = new PointDatabase();
		testDatabaseNull.put(null, 0, 0);
		assertEquals(1, testDatabaseNull.size());
	}
	
	@Test
	void testPut() {
		PointDatabase testDatabase = new PointDatabase();
		testDatabase.put("A", 3.0, 6.0);
		testDatabase.put("B", 2.0, 4.0);
		testDatabase.put("C", 4.1, 4.2);
		testDatabase.put("D", 5, 6.9);

		assertEquals(4, testDatabase.size());
		assertEquals(testDatabase.getName(new Point(3.0, 6.0)), "A"); 
		assertNotEquals(testDatabase.getName(new Point(2.0, 6.0)), "A"); 
	}
	
	@Test
	void testGetNameDouble() {
		Point d = new Point(5, 7);
		
		PointDatabase testDatabase = new PointDatabase();
		testDatabase.put("A", 3.0, 6.0);
		testDatabase.put("B", 6.0, 0);
		testDatabase.put("C", 4, 4.0001);
		
		assertEquals(testDatabase.getName(3.0, 6.0), "A"); 
		assertNotEquals(testDatabase.getName(4, 3), "C"); 
		assertEquals(testDatabase.getName(4, 4), null); 
		assertEquals(testDatabase.getName(0, 0), null); 
		assertEquals(d.getName(), Point.ANONYMOUS);
	}
	
	@Test
	void testGetNamePoint() {
		Point d = new Point(5, 7);
		
		PointDatabase testDatabase = new PointDatabase();
		testDatabase.put("A", 3.0, 6.0);
		testDatabase.put("B", 6.0, 0);
		testDatabase.put("C", 4, 4.0001);
		
		//Test cases for getName(Point node)
		assertEquals(testDatabase.getName(new Point(3.0,6)), "A"); 
		assertNotEquals(testDatabase.getName(new Point(6,0.0)), "C");
		
		//Test cases for getName(double x, double y)
		assertEquals(testDatabase.getName(3, 6), "A");
		assertNotEquals(testDatabase.getName(6, 0), "C");
		assertEquals(testDatabase.getName(0, 0), null); //point that does not exist 
	}
	
	// ALL THE NULL CASES BELOW DO NOT PASS
	
	@Test 
	void testGetPointString() {

		PointDatabase testDatabase = new PointDatabase();
		testDatabase.put("A", 3.0, 6.0);
		testDatabase.put("B", 6.0, 0);
		testDatabase.put(null, 0, 0);
		
		//Test cases for getName(Point node)
		assertEquals(testDatabase.getPoint(new Point(3, 6)).getName() , "A");
		assertNotEquals(testDatabase.getPoint(new Point(6, 0)).getName() , "A");
		assertNotNull(testDatabase.getPoint(new Point(0, 0)).getName());
	}
	
	@Test 
	void testGetPointPoint() {
		
		PointDatabase testDatabase = new PointDatabase();
		testDatabase.put("A", 3.0, 6.0);
		testDatabase.put("B", 2.0, 4.0);
		testDatabase.put("C", 4.1, 4.2);
		testDatabase.put("D", 5, 6.9);
		
		assertEquals(testDatabase.getPoint(new Point(3, 6)).getName() , "A");
		assertNotEquals(testDatabase.getPoint(new Point(2.0, 4.0)).getName(), "A");
		assertNull(testDatabase.getPoint(new Point(0, 0)));
		
	}
	
	@Test 
	void testGetPointDouble() {
		
		PointDatabase testDatabase = new PointDatabase();
		testDatabase.put("A", 3.0, 6.0);
		testDatabase.put("B", 2.0, 4.0);
		testDatabase.put("C", 4.1, 4.2);
		testDatabase.put("D", 5, 6.9);
		
		assertEquals(testDatabase.getPoint(new Point(3.0, 6.0)).getName() , "A");
		assertNotEquals(testDatabase.getPoint(new Point(2, 4)).getName(), "A");
		assertNull(testDatabase.getPoint(new Point(0, 0)));
	}
	
}
