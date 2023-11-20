package preprocessor;

import java.util.HashMap;


import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.Map.Entry;

import geometry_objects.points.Point;
import geometry_objects.points.PointDatabase;
import preprocessor.delegates.ImplicitPointPreprocessor;
import geometry_objects.Segment;
import utilities.math.*;

public class Preprocessor
{
	// The explicit points provided to us by the user.
	// This database will also be modified to include the implicit
	// points (i.e., all points in the figure).
	protected PointDatabase _pointDatabase;

	// Minimal ('Base') segments provided by the user
	protected Set<Segment> _givenSegments;

	// The set of implicitly defined points caused by segments
	// at implicit points.
	protected Set<Point> _implicitPoints;

	// The set of implicitly defined segments resulting from implicit points.
	protected Set<Segment> _implicitSegments;

	// Given all explicit and implicit points, we have a set of
	// segments that contain no other subsegments; these are minimal ('base') segments
	// That is, minimal segments uniquely define the figure.
	protected Set<Segment> _allMinimalSegments;

	// A collection of non-basic segments
	protected Set<Segment> _nonMinimalSegments;

	// A collection of all possible segments: maximal, minimal, and everything in between
	// For lookup capability, we use a map; each <key, value> has the same segment object
	// That is, key == value. 
	protected Map<Segment, Segment> _segmentDatabase;
	public Map<Segment, Segment> getAllSegments() { return _segmentDatabase; }

	public Preprocessor(PointDatabase points, Set<Segment> segments)
	{
		_pointDatabase  = points;
		_givenSegments = segments;
		
		_segmentDatabase = new HashMap<Segment, Segment>();
		
		analyze();
	}

	/**
	 * Invoke the precomputation procedure.
	 */
	public void analyze()
	{
		//
		// Implicit Points
		//
		_implicitPoints = ImplicitPointPreprocessor.compute(_pointDatabase, _givenSegments.stream().toList());

		//
		// Implicit Segments attributed to implicit points
		//
		_implicitSegments = computeImplicitBaseSegments(_implicitPoints);

		//
		// Combine the given minimal segments and implicit segments into a true set of minimal segments
		//     *givenSegments may not be minimal
		//     * implicitSegmen
		//
		_allMinimalSegments = identifyAllMinimalSegments(_implicitPoints, _givenSegments, _implicitSegments);

		//
		// Construct all segments inductively from the base segments
		//
		_nonMinimalSegments = constructAllNonMinimalSegments(_allMinimalSegments);

		//
		// Combine minimal and non-minimal into one package: our database
		//
		_allMinimalSegments.forEach((segment) -> _segmentDatabase.put(segment, segment));
		_nonMinimalSegments.forEach((segment) -> _segmentDatabase.put(segment, segment));
	}
	
	/**
	 * If two segments cross at an unnamed point, the result is an implicit point.
	 * 
	 * This new implicit point may be found on any of the existing segments (possibly
	 * with others implicit points on the same segment).
	 * This results in new, minimal sub-segments.
	 *
	 * For example,   A----*-------*----*---B will result in 4 new base segments.
	 *
	 * @param impPoints -- implicit points computed from segment intersections
	 * @return a set of implicitly defined segments
	 */
	public Set<Segment> computeImplicitBaseSegments(Set<Point> impPtSet){
		Set<Segment> subSegSet = new LinkedHashSet<Segment>();
		Set<Segment> finalSubSegSet = new LinkedHashSet<Segment>();
		Set<Segment> segmentsToRemove = new LinkedHashSet<Segment>();
		
//		System.out.println(98765);
		
		for (Segment s : _givenSegments) {
			SortedSet<Point> impPtsOnS=new TreeSet<Point>(s.collectOrderedPointsOnSegment(impPtSet));
			for (Point p: impPtsOnS) {
				if (!s.getPoint1().equals(p) && !s.getPoint2().equals(p)) {
					subSegSet.add(new Segment(s.getPoint1(), p));
					subSegSet.add(new Segment(s.getPoint2(), p));
				}
			}
			for (Point impPts: impPtsOnS) {
				for(Point impPts2 : impPtsOnS) {
					if (!impPts.equals(impPts2)) subSegSet.add(new Segment(impPts, impPts2));
				}
			}
			finalSubSegSet.addAll(subSegSet);
			for (Segment subSeg: subSegSet) {
				for (Point p: impPtsOnS) {
					if (subSeg.pointLiesBetweenEndpoints(p)) segmentsToRemove.add(subSeg);
				}
			}
		}
		finalSubSegSet.removeAll(segmentsToRemove);
//		for (Segment s: finalSubSegSet) {
//			System.out.println(s.getPoint1().getName() + s.getPoint2().getName() + ", ");
//		}
		return finalSubSegSet;
	}

	/**
	 * From the 'given' segments we remove any non-minimal segment.
	 * 
	 * @param impPoints -- the implicit points for the figure
	 * @param givenSegments -- segments provided by the user
	 * @param minimalImpSegments -- minimal implicit segments computed from the implicit points
	 * @return -- a 
	 */
	public Set<Segment> identifyAllMinimalSegments(Set<Point> implicitPoints, Set<Segment> givenSegments, Set<Segment> implicitSegments){
		Set<Segment> allMinimalSegs = new HashSet<Segment>(givenSegments);
		Set<Segment> segmentsToRemove=new LinkedHashSet<Segment>();
		Set<Point> pd=_pointDatabase.getPoints();
//		for (Point pt: pd) {
//			System.out.println(pt.getName());
//		}
//		for (Segment s: givenSegments) {
//			System.out.println(s.getPoint1().getX() + s.getPoint2().getName() + ", " + s.getPoint1().getY() );
//		}
//		System.out.print(AB.pointLiesBetweenEndpoints(A));
		for (Segment s : givenSegments) {
			for (Point pt: pd) {

				if (s.pointLiesBetweenEndpoints(pt)){
//					System.out.println(489);
					allMinimalSegs.addAll(computeImplicitBaseSegments(pd));
				}
			}
				
		}
		
		for (Segment s:givenSegments) {
			for (Point impPt: pd) {
				if (s.pointLiesBetweenEndpoints(impPt)) segmentsToRemove.add(s);
			}
		}
//		System.out.println();
//		System.out.println();
//		System.out.println(segmentsToRemove.size());
		allMinimalSegs.removeAll(segmentsToRemove);
//		System.out.println();
//		System.out.println();
//		System.out.println(allMinimalSegs.size());
		allMinimalSegs.addAll(implicitSegments);
//		for (Segment s: allMinimalSegs) {
//			System.out.println(s.getPoint1().getName() + s.getPoint2().getName());
//		}
//		System.out.println(76);
		return allMinimalSegs;
		
		
//		//a minimal segment does not have an intersecting segment 
//		Set<Segment> implicitSeg = new HashSet<Segment>();
//		//combination of implicit and explicit >> if segment has no points on it then add it
//		for (Segment g : givenSegments) {
//			if (implicitSeg != null) {
//				givenSegments.addAll(implicitSeg);
//			}
//		}
//		//account for implicit segments that do not exist like BX, CX, etc.
//		if (givenSegments.containsAll(implicitSeg)) return null;
//		if (!givenSegments.containsAll(implicitSeg)) {
//			givenSegments.addAll(implicitSeg);
//			return givenSegments;
//		}
//		
//		return null;
	}
		
	/**
	 * Given a set of minimal segments, build non-minimal segments by appending
	 * minimal segments (one at a time).
	 * 
	 * (Recursive construction of segments.)
	 */	
	public Set<Segment> constructAllNonMinimalSegments(Set<Segment> minSegs){
		Set<Segment> segSet= new LinkedHashSet<>();
		return constructAllNonMinimalSegments(minSegs, minSegs, segSet);
	}
	private Set<Segment> constructAllNonMinimalSegments(Set<Segment> minList, Set<Segment> workList, Set<Segment> additionalSegs){
//		for (Segment s: workList) {
//			System.out.println(s.getPoint1().getName() + s.getPoint2().getName());
//		}
		
		if(workList.isEmpty()) {
//			System.out.println(additionalSegs.size());
//			for (Segment s: additionalSegs) {
//				System.out.println(s.getPoint1().getName() + s.getPoint2().getName());
//			}
			return additionalSegs;
		}
		Point _C=new Point("*_C",  2.04,3.932196162046908);
		Point _B=new Point("*_B", 2.04,3.99);
		Point B=new Point("B", 2.04, 5.5);
		Point F=new Point("F", 2.04, 2.08);
		
//		System.out.println(workList.size());
		Set<Segment> newWorkList = new LinkedHashSet<>();
		for (Segment s : minList) {
			for (Segment s2 : workList) {
				if (s.equals(new Segment(B,_B)) && s2.equals(new Segment(_B,_C))) {
					System.out.println(s.getPoint2().equals(s2.getPoint1()));
				}
//				if (s.equals(new Segment(F,_C))) System.out.println("yes B");
				Segment newSeg = combine(s, s2);

				if (newSeg != null && !additionalSegs.contains(newSeg) && !minList.contains(newSeg)) {
					
					newWorkList.add(newSeg);
					additionalSegs.add(newSeg);
				}
			}
		}
		return constructAllNonMinimalSegments(minList, newWorkList, additionalSegs);
		
//		Set<Segment> minimumList = new HashSet<Segment>();
//		if (workList == null) {
//			//why not just return additionalSegs? What does the minimuList do?
//			if (minimumList != null) {
//				minimumList.addAll(additionalSegs);
//			System.out.println(minimumList.size());
//			return minimumList;
//			}
//		}
//		Set<Segment> newWorkList= new LinkedHashSet<>();
//		for (Segment s: minimumList) {
//			for (Segment s2: workList) {
//				Segment newSeg=combine(s, s2);
//				if (newSeg!=null) {
//					System.out.println("1");
//					newWorkList.add(newSeg);
//					additionalSegs.add(newSeg);
//				}
//			}
//		}
//		System.out.print(newWorkList.size());
//		return newWorkList;
		
		
	}

	//
	// Our goal is to stitch together segments that are on the same line:
	//                       A---------B----------C
	// resulting in the segment AC.
	//
	// To do so we ask:
	//    * Are each segment on the same (infinite) line?
	//    * If so, do they share an endpoint?
	// If both criteria are satisfied we have a new segment.
	private Segment combine(Segment seg, Segment minimal){
		Point _C=new Point("*_C",  2.04,3.932196162046908);
		Point _B=new Point("*_B", 2.04,3.99);
		Point B=new Point("B", 2.04, 5.5);
		Point F=new Point("F", 2.04, 2.08);
		
		if (seg.isCollinearWith(minimal) && seg.coincideWithoutOverlap(minimal)) {
			//stitch the two together
			if (seg.sharedVertex(minimal) != null) {
				//if (MathUtilities.doubleEquals(seg.getPoint1().getY(), seg.getPoint2().getY())){
				return new Segment(seg.getPoint1(), seg.getPoint2());
				//}
			}
		}
		
//		if (seg.equals(minimal)) {
//			if (seg.equals(new Segment(B,_B)) && minimal.equals(new Segment(_B,_C))) System.out.println(0);
//			return null;
//		}
//		if (!MathUtilities.doubleEquals(seg.slope(), minimal.slope())) {
//			if (seg.equals(new Segment(B,_B)) && minimal.equals(new Segment(_B,_C))) System.out.println(minimal.slope());
//			return null;
//		}
//		if(seg.getPoint1().equals(minimal.getPoint1())) {
//			if (seg.equals(new Segment(B,_B)) && minimal.equals(new Segment(_B,_C)))  System.out.println(2);
//			return new Segment(seg.getPoint2(), minimal.getPoint2());
//		}
//		else if (seg.getPoint1().equals(minimal.getPoint2())){
//			if (seg.equals(new Segment(B,_B)) && minimal.equals(new Segment(_B,_C))) System.out.println(3);
//			return new Segment(seg.getPoint2(), minimal.getPoint1());
//		}
//		else if (seg.getPoint2().equals(minimal.getPoint1())){
//			
//			if (seg.equals(new Segment(B,_B)) && minimal.equals(new Segment(_B,_C))) System.out.println(4);
//			return new Segment(seg.getPoint1(), minimal.getPoint2());
//		}
//		else if (seg.getPoint2().equals(minimal.getPoint2())) {
//			//System.out.println(5);
//			return new Segment(seg.getPoint1(), minimal.getPoint1());
//		}
		return null;		
	}
}
