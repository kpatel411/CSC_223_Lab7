package input.components.segments;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import input.components.ComponentNode;
import input.components.point.PointNode;
import input.visitor.ComponentNodeVisitor;

import java.util.Set;

//import input.components.point.PointNode;

public class SegmentNodeDatabase implements ComponentNode {

	protected Map<PointNode, Set<PointNode>> _adjLists; 
	
	public SegmentNodeDatabase() {
		_adjLists = new LinkedHashMap<PointNode, Set<PointNode>>();
	}
	
	public SegmentNodeDatabase(Map<PointNode, Set<PointNode>> aList) {
		_adjLists = new LinkedHashMap<PointNode, Set<PointNode>>(aList); 
		//TODO: again, ask if he wants this constructor to call the other 
	}
	
	public int numUndirectedEdges() {
		int numUnDir = 0;
		for(Entry<PointNode, Set<PointNode>> pair : _adjLists.entrySet()) {
			numUnDir += pair.getValue().size();
			}
		return numUnDir/2; 
		//The number of undirected edges is divided by two here to account for the fact that 
		//there is one undirected edge for each directed edge, which is what i counted in my 
		//numUndirectedEdges method advanced for loop
		}
	
	public void addUndirectedEdge(PointNode pointNodeKey, PointNode pointNodeValue) {
		addDirectedEdge(pointNodeKey, pointNodeValue);
		addDirectedEdge(pointNodeValue, pointNodeKey);
	}
	
	public void addDirectedEdge(PointNode pointNodeKey, PointNode pointNodeValue) {
		if (_adjLists.containsKey(pointNodeKey)) {
			_adjLists.get(pointNodeKey).add(pointNodeValue);
			return;
		}
		Set<PointNode> pointNodeValues = new LinkedHashSet<PointNode>();
		pointNodeValues.add(pointNodeValue);
		_adjLists.put(pointNodeKey, pointNodeValues);
		return;
	}
	
	public void addAdgacencyList(PointNode pNode, List<PointNode> pNodeList) {
		for(PointNode node: pNodeList) {
			addUndirectedEdge(pNode, node);
		}
	}
	
	public List<SegmentNode> asSegmentList() {
		List<SegmentNode> segmentNodeList = new ArrayList<SegmentNode>();
		for (PointNode node: _adjLists.keySet()) {
			for (PointNode nodeOf: _adjLists.get(node)) {
				SegmentNode segNode = new SegmentNode(node, nodeOf);
				segmentNodeList.add(segNode);
			}
		}
		return segmentNodeList;
	}
	
	public List<SegmentNode> asUniqueSegmentList(){
		List<SegmentNode> segNodeArray = new ArrayList<SegmentNode>(new LinkedHashSet<SegmentNode>(asSegmentList()));
		return segNodeArray;
	}
	
	public List<String> edgesAsList(PointNode node) {
		List<String> segmentNodeList = new ArrayList<String>();
		if (_adjLists.get(node) == null) {
			return new ArrayList<String>();
		}
		for (PointNode nodeOf: _adjLists.get(node)) {
			segmentNodeList.add(nodeOf.getName());
		}
		return segmentNodeList;
	}
	
	public  Map<PointNode, Set<PointNode>> getAdjLists() {
		return _adjLists;
	}

	@Override
	public Object accept(ComponentNodeVisitor visitor, Object o) {
		return visitor.visitSegmentDatabaseNode(this, o);
	}
}
