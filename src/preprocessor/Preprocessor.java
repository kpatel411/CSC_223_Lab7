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
	// Minimal (‘Base’) segments provided by the user
	protected Set<Segment> _givenSegments;
	// The set of implicitly defined points caused by segments
	// at implicit points.
	protected Set<Point> _implicitPoints;
	// The set of implicitly defined segments resulting from implicit points.
	protected Set<Segment> _implicitSegments;
	// Given all explicit and implicit points, we have a set of
	// segments that contain no other subsegments; these are minimal (‘base’) segments
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
		System.out.println();
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
		return finalSubSegSet;
	}
	/**
	 * From the ‘given’ segments we remove any non-minimal segment.
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
		for (Segment s : givenSegments) {
			for (Point pt: pd) {
				if (s.pointLiesBetweenEndpoints(pt)){
					allMinimalSegs.addAll(computeImplicitBaseSegments(pd));
				}
			}
				
		}
		
		for (Segment s:givenSegments) {
			for (Point impPt: pd) {
				if (s.pointLiesBetweenEndpoints(impPt)) segmentsToRemove.add(s);
			}
		}
		allMinimalSegs.removeAll(segmentsToRemove);
		allMinimalSegs.addAll(implicitSegments);
		return allMinimalSegs;
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
		
		if(workList.isEmpty()) {
			return additionalSegs;
		}
		Set<Segment> newWorkList = new LinkedHashSet<>();
		for (Segment s : minList) {
			for (Segment s2 : workList) {
				Segment newSeg = combine(s, s2);
				if (newSeg != null && !additionalSegs.contains(newSeg) && !minList.contains(newSeg)) {
					
					newWorkList.add(newSeg);
					additionalSegs.add(newSeg);
				}
			}
		}
		return constructAllNonMinimalSegments(minList, newWorkList, additionalSegs);
		
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
		if (seg.equals(minimal)) {
			return null;
		}
		if (!seg.isCollinearWith(minimal)) {
			return null;
		}
		if(seg.getPoint1().equals(minimal.getPoint1())) {
			return new Segment(seg.getPoint2(), minimal.getPoint2());
		}
		if (seg.getPoint1().equals(minimal.getPoint2())){
			return new Segment(seg.getPoint2(), minimal.getPoint1());
		}
		if (seg.getPoint2().equals(minimal.getPoint1())) {
			return new Segment(seg.getPoint1(), minimal.getPoint2());
		}
		if (seg.getPoint2().equals(minimal.getPoint2())) {
			return new Segment(seg.getPoint1(), minimal.getPoint1());
		}
		return null;		
	}
}