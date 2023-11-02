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
<<<<<<< Updated upstream
=======
<<<<<<< HEAD

        for (Segment s1: givenSegments) {
        	for (Segment s2: givenSegments) {
        		if(!s1.equals(s2)) {
        			Point p = SegmentIntersectionDelegate.findIntersection(s1, s2);
        			if (p==null) return null;
        			if (givenPoints.getPoint(p)==null) {
        				implicitPoints.add(p);
        				givenPoints.put(Point.ANONYMOUS, p.getX(), p.getY());
        			}
=======
>>>>>>> Stashed changes
		//loop twice to find any two segments
        for (Segment segment1: givenSegments) {
        	for (Segment segment2: givenSegments) {
        		//if the segments are not equal then find an intersection
        		if(!segment1.equals(segment2)) {
        			Point p = SegmentIntersectionDelegate.findIntersection(segment1, segment2);
        			if (p == null) return null;
        			// add the point of intersection since it does not exist in the PointDatabase
        			if (givenPoints.getPoint(p) == null) implicitPoints.add(p);
<<<<<<< Updated upstream
=======
>>>>>>> ceb95d7e8b4858a09a6474ae2470fb5861920674
>>>>>>> Stashed changes
        		}
        	}
        }
        return implicitPoints;
	}
}

//make sure to not analyze repeats 