package input.components.point;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import input.components.ComponentNode;
import input.visitor.ComponentNodeVisitor;

//import utilities.math.MathUtilities;

public class PointNodeDatabase implements ComponentNode {
	
	protected Set<PointNode> database = new LinkedHashSet<PointNode>();
	
	public PointNodeDatabase() {
		database = new LinkedHashSet<PointNode>();
	}
	
	public PointNodeDatabase(List<PointNode> pointNodeList) {
		database = new LinkedHashSet<PointNode>(pointNodeList);
		//TODO: ask if he wants this to call the other constructor 
	}
	
	public boolean put(PointNode node) {
		if (database.contains(node)) return false;
		database.add(node);
		return true;
		}
	
	public boolean contains(PointNode node) {
		return database.contains(node);
		//loop through database and use point node equals method 
	}
	
	public boolean contains(double x, double y) {
		return contains(new PointNode(x, y));
	}
	
	public String getName(PointNode node) {
		for (PointNode n: database) {
			if (n.equals(node)) return n.getName();
		}
		return null;
	}
	
	public String getName(double x, double y) {
		return getName(new PointNode(x, y));
	}
	
	public PointNode getPoint(PointNode node) {
		for (PointNode n: database) {
			if (n.equals(node)) return n;
		}
		return null;
	}
	
	public PointNode getPoint(double x, double y) {
		return getPoint(new PointNode(x, y));
	}
	
	public PointNode getNodeByName(String nodeName) {
		for (PointNode n: database) {
			if (n.getName().equals(nodeName)) {
				return n;
			}
		}
		return null;
	}
	
	public List<String> getAllNodeNames() {
		List<String> names = new ArrayList<String>();
		for (PointNode node : database) {
			names.add(node.getName());
		}
		return names;
	}

	@Override
	public Object accept(ComponentNodeVisitor visitor, Object o) {
		return visitor.visitPointNodeDatabase(this, o);
	}
}
