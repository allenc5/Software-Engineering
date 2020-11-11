import java.util.*;

/*
 * public class Directed Acyclic Graph (DAG)
 * A graph in which node edges are directed and cannot form cycles, so you cannot
 * get back to a node by traversing it's edges.
 * 
 * FUNCTIONS:
 * 
 * Digraph(int V): create an empty digraph with V vertices
 *  
 * void addEdge(int v, int w) : Add a directed edge v->w
 * 
 * Iterable<Integer> adj(int v) : Return vertices pointing from v
 * 
 * int V() : number of vertices
 * 
 * boolean hasPth : if path exists between two nodes returns true
 * 
 * String toString() : string representation of the graph
 * 
 */

public class DAG {

	private final int V;
	private final ArrayList<Integer>[] adj;
	private final ArrayList<Integer>[] reverseAdj;			//Need for finding the LCA

	//DAG constructor
	public DAG(int V)
	{
		this.V = V;
		adj = (ArrayList<Integer>[]) new ArrayList[V];
		
		// For LCA
		reverseAdj = (ArrayList<Integer>[]) new ArrayList[V];
		
		//Make an array list for each vertex 
		for (int v = 0; v < V; v++)
		{
			adj[v] = new ArrayList<Integer>();	
		
			//For LCA
			reverseAdj[v] = new ArrayList<Integer>();
		}
	}
	
	//Adds an edge from vertex v to vertex w if conditions met, returns true if edge was successfully added
	public boolean addEdge(int v, int w)
	{
		// acyclic -> Will not create a cycle by adding the edge
		// 1. No self loops 
		// 2. !hasPath w -> v i.e. can't get back to v after following the path from v to w
		
		//Make sure vertexes are both in range (not less than 0 or bigger than number of vertexes in the DAG)
		if(v >= this.V || w >= this.V || v < 0 || w < 0)
		{
			return false;
		}
		
		//Make sure vertexes are different so no self loops
		//Make sure no path exits between w and v (can't get back to v from following path after following path v to w)
		//Make sure v does not already have an edge already pointing to w 
		if(v != w && !hasPath(w, v) && !adj[v].contains(w))
		{
			adj[v].add(w);
			reverseAdj[w].add(v);
			return true;
		}	
		else
		{
			return false;
		}
	}

	//Returns number of vertexes in the DAG
	public int V()
	{
		return V;
	}

	//Returns list of vertices pointing from the vertex v 
	public ArrayList<Integer> adj(int v)
	{ 
		return adj[v];
	}

	//Returns reversed list of vertices pointing from the vertex v 
	public ArrayList<Integer> reverseAdj(int v)
	{
		return reverseAdj[v];
	}

	//Checks if a path exists between to nodes by using depth first search
	public boolean hasPath(int x, int y)
	{
		DirectedDFS dfsObject = new DirectedDFS(this, x);
		return dfsObject.visited(y);
	}

	//lowestCommonAncestor code
	public ArrayList<Integer> lowestCommonAncestor(int x, int y)
	{
		//STEPS TO FIND LCA(s):
		//Mark all X's parents
		//For each of X's parents, check if Y is child
		//if it is
		// 	get distance to X
		// 	get distance to Y
		//
		// if max(xDist, yDist) < currentMaxDist
		// 		empty bag and put in this node
		//
		// if max(xDist, yDist) == currentMaxDist
		//		add this node to bag
		//
		
		ArrayList<Integer> lcas = new ArrayList<Integer>();
		int currentMaxDist = Integer.MAX_VALUE;
		
		
		if(x==y || x>=this.V || y>=this.V || x<0 || y<0) { return lcas; } //If invalid input return empty bag.
		
		DirectedDFS dfsObject = new DirectedDFS(this, x);
		dfsObject.reverseDfs(this, x);
		int xDist, yDist;
		
		for(int v = 0; v < this.V; v++)
		{
		
			if(dfsObject.revVisited(v) && hasPath(v, y))
			{
				xDist = getDistance(v, x);
				yDist = getDistance(v, y);
				
				if(Integer.max(xDist, yDist) < currentMaxDist)
				{		
					lcas = new ArrayList<Integer>();
					lcas.add(v);
					currentMaxDist = Integer.max(xDist, yDist);
				}
				else if(Integer.max(xDist, yDist) == currentMaxDist)
				{
					lcas.add(v);
					currentMaxDist = Integer.max(xDist, yDist);
				}
			}
		}
		return lcas;
	}

	private int getDistance(int x, int target)
	{
	    
			if( x == target) { return 0; }
			else {
		        Queue<Integer> q = new LinkedList<Integer>();
		        int[] distTo = new int[this.V];
		        boolean[] marked = new boolean[this.V];
		        
		        for (int v = 0; v < this.V(); v++)
		        {   distTo[v] = Integer.MAX_VALUE;}
		        
		        distTo[x] = 0;
		        marked[x] = true;
		        q.add(x);
		
		        while (!q.isEmpty()) {
		            int v = q.remove();
		            for (int w : this.adj(v)) {
		                if (!marked[w]) {
		                	
		                	distTo[w] = distTo[v] + 1;
		                    marked[w] = true;
		                    
		                    q.add(w);
		                }
		            }
		        }
		        
		        return distTo[target];
			}
	}

	// Class to create a depth first search object on the directed graph
	private class DirectedDFS
	{
		private boolean[] marked;
		private boolean[] revMarked;
		
		public DirectedDFS(DAG G, int s)
		{
			marked = new boolean[G.V()];
			revMarked = new boolean[G.V()];
			dfs(G, s);
		}
		
		
		//standard depth first search - in the flow of direction.
		private void dfs(DAG G, int v)
		{
			marked[v] = true;
			for (int w : G.adj(v))
			if (!marked[w]) dfs(G, w);
		}
		
		
		//depth first search against the flow of direction - used to find all parents.
		private void reverseDfs(DAG G, int v)
		{
			revMarked[v] = true;
			for (int w : G.reverseAdj(v))
			if (!revMarked[w]) reverseDfs(G, w);
		}
		
		public boolean visited(int v)
		{ return marked[v]; }
		
		public boolean revVisited(int v)
		{ return revMarked[v]; }
	}
}
