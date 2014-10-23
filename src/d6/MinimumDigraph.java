package d6;

import edu.princeton.cs.algs4.BreadthFirstDirectedPaths;
import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.TransitiveClosure;
import edu.princeton.cs.introcs.StdOut;


public class MinimumDigraph {

	/*public static Digraph equivalentMinimumDigraph(Digraph g) {
        Digraph R = new Digraph(g.V());
        Digraph rG = new Digraph(g.reverse());
        for (int v = 0; v < g.E(); v++) {
        	
            for (int w : g.adj(v)) {
            	//TransitiveClosure rT = new TransitiveClosure(R);
            	//if(gbfd.hasPathTo(v) && (!rbfd.hasPathTo(v))) R.addEdge(v, w);
            	//if(rgT.reachable(w, v) != rT.reachable(w, v)) R.addEdge(v, w);
            }
        }
        return R;  
	}*/
	
	public static Digraph equivalentMinimumDigraph(Digraph g) {
        Digraph R = new Digraph(g.V());
        for (int v = g.V()-1; v > -1; v--) {
        	TransitiveClosure rgT = new TransitiveClosure(g.reverse()); 
        	TransitiveClosure gT = new TransitiveClosure(g); 
            for (int w : g.adj(v)) {
            	TransitiveClosure rT = new TransitiveClosure(R);
            	//if(gbfd.hasPathTo(v) && (!rbfd.hasPathTo(v))) R.addEdge(v, w);
            	if(gT.reachable(v, w) != rT.reachable(v, w)){
            		R.addEdge(v, w);
            	}
            }
        }
        return R;  
	}
	

}

	

/*
1  function ReverseDelete(edges[] E)
2    sort E in decreasing order
3    Define an index i ← 0
4    while i < size(E)
5       Define edge ← E[i]
6         delete E[i]
7         if edge.v1 is not connected to edge.v2
8             E[i] ← edge
9         i ← i + 1
10   return edges[] E
*/