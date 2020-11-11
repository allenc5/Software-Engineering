import unittest
import LCA
from LCA import findLCAUtil
from LCA import findLCA
from LCA import find 
from LCA import Node 


class TestLCA(unittest.TestCase):

	def testNodes(self): #Testing root value in the tree

		root = Node(1)
		self.assertEqual(1, root.key)

		
	def testFindLCA(self):

		root = Node(1)
		root.left = Node(2)
		root.right = Node(3)
		root.left.left = Node(4)
		root.left.right = Node(5)
		root.right.left = Node(6)
		root.right.right = Node(7)

		# test all nodes are None 
		self.assertEqual(LCA.findLCA(None, None, None), None)

		#test when root is euqal to node
		self.assertEqual(LCA.findLCA(root, 1, 1).key, 1)

		#test if n1 is not present
		self.assertEqual(LCA.findLCA(root, None, 2), None)

		#test if n2 is not present
		self.assertEqual(LCA.findLCA(root, 2, None), None)

		#test when node is value is not in tree
		self.assertEqual(LCA.findLCA(root, 2, 13), None)

		#test LCA for different values
		self.assertEqual(findLCA(root, 3, 7).key, 3)
		self.assertEqual(findLCA(root, 4, 6).key, 1)
		self.assertEqual(findLCA(root, 2, 5).key, 2)
		self.assertEqual(findLCA(root, 3, 4).key, 1)
	
	
	def test_empty_tree(self):

        	root = None
        	assert lca(root, 8, 3) is None
		
		
	def test_LCA_none_node(self):
       
        	assert lca(None, 1, 3) is None	



if __name__ == '__main__':
	unittest.main()
