/**
 * authors: Grace Warren, Khushi Patel, and Wick Martin 
 * JSONParserTest: Tests for JSONParser functionality and returns string format of figure objects.
 */

package input.parser;

import input.builder.DefaultBuilder;
import input.builder.GeometryBuilder;
import static org.junit.jupiter.api.Assertions.*;
import java.util.AbstractMap;
import org.junit.jupiter.api.Test;
import input.components.ComponentNode;
import input.components.FigureNode;
import input.exception.ParseException;
import input.visitor.UnparseVisitor;
public class UnparseVisitorTest {

	public static ComponentNode runFigureParseTest(String filename)
	{
		JSONParser parser = new JSONParser(new DefaultBuilder());

		String figureStr = utilities.io.FileUtilities.readFileFilterComments(filename);

		return parser.parse(figureStr);
	}

	@Test
	void empty_json_string_test()
	{
		JSONParser parser = new JSONParser(new DefaultBuilder());

		assertThrows(ParseException.class, () -> { parser.parse("{}"); });
	}

	@Test
	void single_triangle_test()
	{
		//
		// The input String ("single_triangle.json") assumes the file is
		// located at the top-level of the project. If you move your input
		// files into a folder, update this String with the path:
		//                                       e.g., "my_folder/single_triangle.json"
		//
		ComponentNode node = JSONParserTest.runFigureParseTestDefault("single_triangle.json");

		assertNull(node);
	}
	
	@Test
	void single_triangle_testG()
	{
		//
		// The input String ("single_triangle.json") assumes the file is
		// located at the top-level of the project. If you move your input
		// files into a folder, update this String with the path:
		//                                       e.g., "my_folder/single_triangle.json"
		//
		ComponentNode node = JSONParserTest.runFigureParseTestGeometry("single_triangle.json");

		assertTrue(node instanceof FigureNode);

		StringBuilder sb = new StringBuilder();
		UnparseVisitor unparser = new UnparseVisitor();
		unparser.visitFigureNode((FigureNode)node,
				new AbstractMap.SimpleEntry<StringBuilder, Integer>(sb, 0));
		System.out.println(sb.toString());
	}

	@Test
	void collinear_line_segments_test()
	{
		ComponentNode node = JSONParserTest.runFigureParseTestDefault("collinear_line_segments.json");

		assertNull(node);
	}
	
	@Test
	void collinear_line_segments_testG()
	{
		ComponentNode node = JSONParserTest.runFigureParseTestGeometry("collinear_line_segments.json");

		assertTrue(node instanceof FigureNode);

		StringBuilder sb = new StringBuilder();
		UnparseVisitor unparser = new UnparseVisitor();
		unparser.visitFigureNode((FigureNode)node,
				new AbstractMap.SimpleEntry<StringBuilder, Integer>(sb, 0));
		System.out.println(sb.toString());
	}

	@Test
	void crossing_symmetric_triangle_test()
	{
		ComponentNode node = JSONParserTest.runFigureParseTestDefault("crossing_symmetric_triangle.json");

		assertNull(node);
	}
	
	@Test
	void crossing_symmetric_triangle_testG()
	{
		ComponentNode node = JSONParserTest.runFigureParseTestGeometry("crossing_symmetric_triangle.json");

		assertTrue(node instanceof FigureNode);

		StringBuilder sb = new StringBuilder();
		UnparseVisitor unparser = new UnparseVisitor();
		unparser.visitFigureNode((FigureNode)node,
				new AbstractMap.SimpleEntry<StringBuilder, Integer>(sb, 0));
		System.out.println(sb.toString());
	}

	@Test
	void fully_connected_irregular_polygon_test()
	{
		ComponentNode node = JSONParserTest.runFigureParseTestDefault("fully_connected_irregular_polygon.json");

		assertNull(node);
	}
	
	@Test
	void fully_connected_irregular_polygon_testG()
	{
		ComponentNode node = JSONParserTest.runFigureParseTestGeometry("fully_connected_irregular_polygon.json");

		assertTrue(node instanceof FigureNode);

		StringBuilder sb = new StringBuilder();
		UnparseVisitor unparser = new UnparseVisitor();
		unparser.visitFigureNode((FigureNode)node,
				new AbstractMap.SimpleEntry<StringBuilder, Integer>(sb, 0));
		System.out.println(sb.toString());
	}

	//	    /_\__/_\
	//	   /  .  .  \
	//	  |   /_\   |
	// 	  |__ \|/ __| 
	//     \/_____\/
	//     	
	@Test
	void catWithTriangles_test()
	{
		ComponentNode node = JSONParserTest.runFigureParseTestDefault("catWithTriangles.json");

		assertNull(node);
	}
	
	@Test
	void catWithTriangles_testG()
	{
		ComponentNode node = JSONParserTest.runFigureParseTestGeometry("catWithTriangles.json");

		assertTrue(node instanceof FigureNode);

		StringBuilder sb = new StringBuilder();
		UnparseVisitor unparser = new UnparseVisitor();
		unparser.visitFigureNode((FigureNode)node,
				new AbstractMap.SimpleEntry<StringBuilder, Integer>(sb, 0));
		System.out.println(sb.toString());
	}

	//			______
	//		   |      |
	//		   |	  |
	//		___|______|___
	//		   /     \
	//		  /       \
	//	     |    |\   |
	//		 | 	  |/   |
	//		  \       /
	//	_\_    \_____/    _/_
	//	  \   /        \  /
	//	   \ /          \/
	//		|    	     |
	//		| 	   		 |
	//		 \          /
	//	      \________/
	//	     /         \
	//		/           \
	//	   /             \
	//	  |    	          |
	//	  | 	   		  |
	//	   \             /
	// 	    \___________/


	@Test
	void octogonSnowman_test()
	{
		ComponentNode node = JSONParserTest.runFigureParseTestDefault("octogonSnowman.json");

		assertNull(node);
	}
	
	@Test
	void octogonSnowman_testG()
	{
		ComponentNode node = JSONParserTest.runFigureParseTestGeometry("octogonSnowman.json");

		assertTrue(node instanceof FigureNode);

		StringBuilder sb = new StringBuilder();
		UnparseVisitor unparser = new UnparseVisitor();
		unparser.visitFigureNode((FigureNode)node,
				new AbstractMap.SimpleEntry<StringBuilder, Integer>(sb, 0));
		System.out.println(sb.toString());
	}

	//    ____________
	//   /            \
	// 	/              \
	//  |  	 .    .    |
	//  |              |
	//  |     ____     |
	//  |              |
	//  |          	   |
	//  |          	   |
	//  |              |
	//  |/_\/_\/_\/_\/_|

	@Test
	void pacmanGhost_test()
	{
		ComponentNode node = JSONParserTest.runFigureParseTestDefault("pacmanGhost.json");

		assertNull(node);
	}
	
	@Test
	void pacmanGhost_testG()
	{
		ComponentNode node = JSONParserTest.runFigureParseTestGeometry("pacmanGhost.json");

		assertTrue(node instanceof FigureNode);

		StringBuilder sb = new StringBuilder();
		UnparseVisitor unparser = new UnparseVisitor();
		unparser.visitFigureNode((FigureNode)node,
				new AbstractMap.SimpleEntry<StringBuilder, Integer>(sb, 0));
		System.out.println(sb.toString());
	}
}