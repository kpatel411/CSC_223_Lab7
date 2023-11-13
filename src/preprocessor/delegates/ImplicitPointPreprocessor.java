package preprocessor.delegates;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import geometry_objects.Segment;
import geometry_objects.delegates.intersections.SegmentIntersectionDelegate;
import geometry_objects.points.Point;
import geometry_objects.points.PointDatabase;
public class ImplicitPointPreprocessor
{
	/**
	 * It is possible that some of the defined segments intersect
	 * and points that are not named; we need to capture those
	 * points and name them.
	 *
	 * Algorithm:
	 *    TODO
	 */
	public static Set<Point> compute(PointDatabase givenPoints, List<Segment> givenSegments)
	{
		Set<Point> implicitPoints = new LinkedHashSet<Point>();
		System.out.println(givenPoints.size());
		System.out.println(givenSegments.size());
		for (int i = 0; i < givenSegments.size() - 1; i++) {
			//iterates through the segments that come after the segment at I, ensuring no repetition of segments or pairs of segments
			for (int j = i + 1; j < givenSegments.size(); j++) {
				//checks if implicit point
				if (givenSegments.get(i).toString().equals("AC")) {
					System.out.println(givenSegments.get(j).toString());
				}
				Point p = SegmentIntersectionDelegate.findIntersection(givenSegments.get(i), givenSegments.get(j));
				//if p isn’t null it checks that p doesn’t exist in pointDatabase
				if(p != null && givenPoints.getPoint(p) == null) {
					if (givenSegments.get(i).toString().equals("AC")) System.out.println("1");
					implicitPoints.add(p);
					givenPoints.put(Point.ANONYMOUS, p.getX(), p.getY());
				}
			}
//			System.out.println(givenSegments.get(i).toString());
		}
		for(Point p : implicitPoints) {
			System.out.println("(" + p.getX() + "," + p.getY() + ")");
		}
		return implicitPoints;
	}

}