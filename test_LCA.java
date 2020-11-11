import static org.junit.Assert.*;

import org.junit.jupiter.api.Test;

public class LCATest {

	@Test
	public void testNodeConstructor() {
		Node tmp = new Node(1);
		assertNotNull("test Node constructor: ", tmp);
	}

	@Test
	public void testEmptyTree() {
		LCA lca = new LCA();
		assertNull("test to find LCA when tree is empty: ", lca.findLCA(1, 4));
		assertEquals("test to find LCA when tree is empty: ", null, lca.findLCA(1, 4));
	}

	@Test
	public void testCommon() {
		// test tree structure:
		// 1
		// 2 3
		// 4 5 6 7
		LCA lca = new LCA();
		lca.root = new Node(1);
		lca.root.left = new Node(2);
		lca.root.right = new Node(3);
		lca.root.left.left = new Node(4);
		lca.root.left.right = new Node(5);
		lca.root.right.left = new Node(6);
		lca.root.right.right = new Node(7);

		assertEquals("LCA of 2 and 3: ", 1, lca.findLCA(2, 3).data);
		assertEquals("LCA of 6 and 7: ", 3, lca.findLCA(6, 7).data);
		assertEquals("LCA of 4 and 5: ", 2, lca.findLCA(4, 5).data);
		assertEquals("LCA of 4 and 7: ", 1, lca.findLCA(4, 7).data);
	}
  
 	@Test
	public void testForNonExistentNodes() {
		// test tree structure:
		// 1
		// 2 3
		// 4 5 6 7

		LCA lca = new LCA();
		lca.root = new Node(1);
		lca.root.left = new Node(2);
		lca.root.right = new Node(3);
		lca.root.left.left = new Node(4);
		lca.root.left.right = new Node(5);
		lca.root.right.left = new Node(6);
		lca.root.right.right = new Node(7);

		assertEquals("test to find ancestors of non-existent nodes in populated tree: ", null, lca.findLCA(8, 9));
		assertEquals("test to find ancestors of non-existent nodes in populated tree: ", null, lca.findLCA(23, 100));
	}
	
	@Test
	public void testSameNodes() {
		LCA tree = new LCA();
		tree.root = new Node(8);
		tree.root.left = new Node(8);
		tree.root.right = new Node(8);
		tree.root.left.left = new Node(8);
		tree.root.left.right = new Node(8);
		
		assertEquals("LCA of 8 and 8: ", 8, tree.functionLCA(8, 8));
	}
	
	@Test 
	public void unevenTree() { 
		LCA tree = new LCA();
		tree.root = new Node(1);
		tree.root.right = new Node(2);
		tree.root.right.right = new Node(3);
		
		assertEquals("LCA of uneven tree:", 1, tree.functionLCA(3, 1));
		assertEquals("LCA of uneven tree:", 2, tree.functionLCA(2, 3));	
		
	}
}
