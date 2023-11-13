package preprocessor;

import java.util.HashMap;


import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;

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
	
	public Set<Segment> computeImplicitBaseSegments(Set<Point> ipSet){
		Set<Segment> impSeg = new LinkedHashSet<Segment>();
		for(Point p : ipSet) {
			for (Segment s : _givenSegments) {
				
				if (s.pointLiesBetweenEndpoints(p)) {
					impSeg.add(new Segment(s.getPoint1(), p));
					impSeg.add(new Segment(s.getPoint2(), p));
				}
			}
		}
		return impSeg;
	}
	public Set<Segment> identifyAllMinimalSegments(Set<Point> implicitPoints, Set<Segment> givenSegments, Set<Segment> implicitSegments){
		Set<Segment> allMinimalSegs = new HashSet<Segment>();
		for (Segment s:givenSegments) {
			for (Point impPt: implicitPoints) {
				if (!s.pointLiesBetweenEndpoints(impPt)) allMinimalSegs.add(s);
			}
		}
		allMinimalSegs.addAll(implicitSegments);
		return allMinimalSegs;
//		
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
	public Set<Segment> constructAllNonMinimalSegments(Set<Segment> minSegs){
		Set<Segment> segSet= new LinkedHashSet<>();
		return constructAllNonMinimalSegments(minSegs, minSegs, segSet);
	}
	private Set<Segment> constructAllNonMinimalSegments(Set<Segment> minList, Set<Segment> workList, Set<Segment> additionalSegs){
		if(workList.isEmpty()) {
			System.out.println(additionalSegs.size());
			return additionalSegs;
		}
//		System.out.println(workList.size());
		Set<Segment> newWorkList = new LinkedHashSet<>();
		for (Segment s : minList) {
			for (Segment s2 : workList) {
				Segment newSeg = combine(s, s2);

				if (newSeg != null && !minList.contains(newSeg)) {
					newWorkList.add(newSeg);
					additionalSegs.add(newSeg);
				}
			}
		}
		for(Segment s : newWorkList) {
//			System.out.println("(" + s.getPoint1().getX() + "," + s.getPoint1().getY() +")" + "," + "(" + s.getPoint2().getX() + "," + s.getPoint2().getY() +")");
		}
		System.out.println();
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

	private Segment combine(Segment seg, Segment minimal){
		if (seg.equals(minimal)) {
//			System.out.println(0);
			return null;
		}
		if (!MathUtilities.doubleEquals(seg.slope(), minimal.slope())) {
//			System.out.println(1);
			return null;
		}
		if(seg.getPoint1().equals(minimal.getPoint1())) {
//			System.out.println(2);
			return new Segment(seg.getPoint2(), minimal.getPoint2());
		}
		else if (seg.getPoint1().equals(minimal.getPoint2())){
//			System.out.println(3);
			return new Segment(seg.getPoint2(), minimal.getPoint1());
		}
		else if (seg.getPoint2().equals(minimal.getPoint1())){
//			System.out.println(4);
			return new Segment(seg.getPoint1(), minimal.getPoint2());
		}
		else if (seg.getPoint2().equals(minimal.getPoint2())) {
//			System.out.println(5);
			return new Segment(seg.getPoint1(), minimal.getPoint1());
		}
		return null;		
	}
}
