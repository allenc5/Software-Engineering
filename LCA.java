class Node {
	int data;
	Node left, right;

	public Node(int item) {
		data = item;
		left = right = null;
	}
}

public class LCA {
	Node root;

	Node findLCA(int n1, int n2) {
		return findLCA(root, n1, n2);
	}

	Node findLCA(Node node, int n1, int n2) {
		if (node == null)
			return null;

		if (node.data == n1 || node.data == n2)
			return node;

		Node left_lca = findLCA(node.left, n1, n2);
		Node right_lca = findLCA(node.right, n1, n2);

		if (left_lca != null && right_lca != null)
			return node;

		return (left_lca != null) ? left_lca : right_lca;
	}
}
