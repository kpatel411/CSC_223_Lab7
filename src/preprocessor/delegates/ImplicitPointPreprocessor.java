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
	 *    see below
	 */
	public static Set<Point> compute(PointDatabase givenPoints, List<Segment> givenSegments)
	{
		Set<Point> implicitPoints = new LinkedHashSet<Point>();
		for (int i = 0; i < givenSegments.size() - 1; i++) {
			//iterates through the segments that come after the segment at I, ensuring no repetition of segments or pairs of segments
			for (int j = i + 1; j < givenSegments.size(); j++) {
				//checks if implicit point
				Point p = SegmentIntersectionDelegate.findIntersection(givenSegments.get(i), givenSegments.get(j));
				//if p isn’t null it checks that p doesn’t exist in pointDatabase
				if(p != null && givenPoints.getPoint(p) == null) {
					givenPoints.put(Point.ANONYMOUS, p.getX(), p.getY());
					implicitPoints.add(givenPoints.getPoint(p.getX(), p.getY()));
				}
			}
		}


		return implicitPoints;
	}

}