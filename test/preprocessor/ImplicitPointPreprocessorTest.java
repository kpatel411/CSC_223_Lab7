package preprocessor;

import static org.junit.jupiter.api.Assertions.*;

import java.util.LinkedHashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

import geometry_objects.Segment;
import geometry_objects.points.Point;
import geometry_objects.points.PointDatabase;
import preprocessor.delegates.ImplicitPointPreprocessor;

class ImplicitPointPreprocessorTest {

	@Test
	void test() {
		//creates points
		Point A=new Point("A", 0, 4);
		Point B=new Point("B", -1, 2);
		Point C=new Point("C", 1, 2);
		Point D=new Point("D", -2, 0);
		Point E=new Point("E", 2, 0);
				
//		Segment AB=new Segment(A,B);
//		Segment AC=new Segment(A,C);
//		Segment BC=new Segment(B,C);
//		Segment BD=new Segment(B,D);
		
		//creates segments BE and CD
		Segment BE=new Segment(B,E);
		Segment CD=new Segment(C,D);
				
				
//		Segment CE=new Segment(C,E);
//		Segment DE=new Segment(D,E);
				
		
		//creates and then adds points to database
		PointDatabase pd= new PointDatabase();
		pd.put("A", 0, 4);
		pd.put("B", -1, 2);
		pd.put("C", 1, 2);
		pd.put("D", -2, 0);
		pd.put("E", 2, 0);
		//adds segments to segment list
		Set<Segment> segSet=new LinkedHashSet<Segment>();
//		segList.add(AB);
//		segList.add(AC);
//		segList.add(BC);
//		segList.add(BD);
		segSet.add(BE);
		segSet.add(CD);
//		segList.add(CE);
//		segList.add(DE);
		Set<Point> implicitPoints = ImplicitPointPreprocessor.compute(pd, segSet.stream().toList());
		//prints implicitPoints
		System.out.println(implicitPoints.toString());
	}

}
