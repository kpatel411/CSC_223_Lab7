package preprocessor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import geometry_objects.points.Point;
import geometry_objects.points.PointDatabase;
import preprocessor.delegates.ImplicitPointPreprocessor;
import geometry_objects.Segment;

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
		//Note to self: IMPLIED minimal segments
		_implicitSegments = computeImplicitBaseSegments(_implicitPoints);

		//
		// Combine the given minimal segments and implicit segments into a true set of minimal segments
		//     *givenSegments may not be minimal
		//     * implicitSegmen
		//
		//Note to self: ALL minimal segments 
		_allMinimalSegments = identifyAllMinimalSegments(_implicitPoints, _givenSegments, _implicitSegments);

		//
		// Construct all segments inductively from the base segments
		//
		//Note to self: ALL NON minimal segments
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
	public Set<Segment> computeImplicitBaseSegments(Set<Point> implicitPoints) {

		Set<Segment> implicitSegments = new HashSet<Segment>();

		//	for each segment in _givenSegments, 
		for (Segment seg : _givenSegments) {
			//	create a list of order implicit points on that segment 
			List<Point> ptsOnSeg = seg.collectOrderedPointsOnSegment(implicitPoints).stream().toList();

			//	for each pair of ordered points on that segment 
			for (int index_1 = 0; index_1 < ptsOnSeg.size() - 1; index_1++) {
				Segment potentialSeg = new Segment(ptsOnSeg.get(index_1), ptsOnSeg.get(index_1 + 1));

				//	if that segment is not already contained in _givenSegments, add it to implicitSegments
				if (!_givenSegments.contains(potentialSeg)) {
					implicitSegments.add(potentialSeg);
				}
			}
		}
		return implicitSegments;
	}

	// then we have the class contain all implicit segment 

	/**
	 * From the 'given' segments we remove any non-minimal segment.
	 * 
	 * @param impPoints -- the implicit points for the figure
	 * @param givenSegments -- segments provided by the user
	 * @param minimalImpSegments -- minimal implicit segments computed from the implicit points
	 * @return -- a 
	 */
	public Set<Segment> identifyAllMinimalSegments(Set<Point> implicitPoints, Set<Segment> givenSegments, Set<Segment> implicitSegments) {
		// add implicitBaseSegments (which are all minimal) to to allMininalSegments
		Set<Segment> allMinimalSegments = new HashSet<Segment>();
		allMinimalSegments.addAll(implicitSegments);

		// loop through the givenSegments
		//		if no points in the database lie on that segment, it must be minimal
		//		add it to allMinimalSegments 
		for (Segment seg : givenSegments) {

			if (seg.collectOrderedPointsOnSegment(_pointDatabase.toSet()).size() == 2) {
				allMinimalSegments.add(seg);
			}
		}
		return allMinimalSegments;
	}

	/**
	 * Given a set of minimal segments, build non-minimal segments by appending
	 * minimal segments (one at a time).
	 * 
	 * (Recursive construction of segments.)
	 */
	public Set<Segment> constructAllNonMinimalSegments(Set<Segment> minimalSegments) {

		Set<Segment> allMinimalSegments = _allMinimalSegments;
		Set<Segment> nonMinimalSegments = new HashSet<Segment>();

		return constructAllNonMinimalSegments(allMinimalSegments.stream().toList(), allMinimalSegments, nonMinimalSegments);
	}

	private Set<Segment> constructAllNonMinimalSegments(List<Segment> allMinimalSegments, Set<Segment> workList, Set<Segment> nonMinimalSegments) {

		//	base case: when the work list is empty, no non-minimal segments have been found
		//			   thus, there are no more to create, return them as is
		if (workList.isEmpty()) return nonMinimalSegments;

		//	cast your workList and nonMinimalSegments to list so they're iterable
		Set<Segment> newWorkList = new HashSet<Segment>();
		List<Segment> wList = workList.stream().toList();

		//for each item in the workList 
				
		for (Segment workSeg : wList) {
			//for each item in the nonMinimalSegments 
			for (Segment minSeg : allMinimalSegments) {
				
				//	if there exists a segment that coincide without overlap
				Segment seg1 = workSeg;
				Segment seg2 = minSeg;
				
				createSegment(seg1, seg2, newWorkList, nonMinimalSegments);
			}
		}
	
		return constructAllNonMinimalSegments(allMinimalSegments, newWorkList, nonMinimalSegments);
	}
	
	public void createSegment(Segment seg1, Segment seg2, Set<Segment> newWorkList, Set<Segment> nonMinimalSegments) {
		
		if (seg1.coincideWithoutOverlap(seg2) && seg2.coincideWithoutOverlap(seg1)) {

			//	for which there is also a shared vertex (meaning the segments touch)
			//	find it, and use the other two segments to create a new segment 
			if (seg1.sharedVertex(seg2) != null) {
				Point point1 = seg1.other(seg1.sharedVertex(seg2));
				Point point2 = seg2.other(seg1.sharedVertex(seg2));

				if (point1 != null && point2 != null) {

					newWorkList.add(new Segment(point1, point2));
					nonMinimalSegments.add(new Segment(point1, point2));
				}
			}
		}
	}
}
