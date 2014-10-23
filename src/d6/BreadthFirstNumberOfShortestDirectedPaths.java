package d6;

import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.introcs.In;
import edu.princeton.cs.introcs.StdOut;

/*************************************************************************
 *  Compilation:  javac BreadthFirstDirectedPaths.java
 *  Execution:    java BreadthFirstDirectedPaths V E
 *  Dependencies: Digraph.java Queue.java Stack.java
 *
 *  Run breadth first search on a digraph.
 *  Runs in O(E + V) time.
 *
 *  % java BreadthFirstDirectedPaths tinyDG.txt 3
 *  3 to 0 (2):  3->2->0
 *  3 to 1 (3):  3->2->0->1
 *  3 to 2 (1):  3->2
 *  3 to 3 (0):  3
 *  3 to 4 (2):  3->5->4
 *  3 to 5 (1):  3->5
 *  3 to 6 (-):  not connected
 *  3 to 7 (-):  not connected
 *  3 to 8 (-):  not connected
 *  3 to 9 (-):  not connected
 *  3 to 10 (-):  not connected
 *  3 to 11 (-):  not connected
 *  3 to 12 (-):  not connected
 *
 *************************************************************************/

/**
 *  The <tt>BreadthDirectedFirstPaths</tt> class represents a data type for finding
 *  shortest paths (number of edges) from a source vertex <em>s</em>
 *  (or set of source vertices) to every other vertex in the digraph.
 *  <p>
 *  This implementation uses breadth-first search.
 *  The constructor takes time proportional to <em>V</em> + <em>E</em>,
 *  where <em>V</em> is the number of vertices and <em>E</em> is the number of edges.
 *  It uses extra space (not including the digraph) proportional to <em>V</em>.
 *  <p>
 *  For additional documentation, see <a href="/algs4/41graph">Section 4.1</a> of
 *  <i>Algorithms, 4th Edition</i> by Robert Sedgewick and Kevin Wayne.
 *
 *  @author Robert Sedgewick
 *  @author Kevin Wayne
 */
public class BreadthFirstNumberOfShortestDirectedPaths {
    private static final int INFINITY = Integer.MAX_VALUE;
    private boolean[] marked;  // marked[v] = is there an s->v path?
    private int[] edgeTo;      // edgeTo[v] = last edge on shortest s->v path
    private int[] distTo;      // distTo[v] = length of shortest s->v path
    private int counter = 0;

    /**
     * Computes the shortest path from <tt>s</tt> and every other vertex in graph <tt>G</tt>.
     * @param G the digraph
     * @param s the source vertex
     */

    public BreadthFirstNumberOfShortestDirectedPaths(Digraph G, int v, int w) {
        marked = new boolean[G.V()];
        distTo = new int[G.V()];
        edgeTo = new int[G.V()];
        for (int i = 0; i < G.V(); i++) distTo[i] = INFINITY;
        bfs(G, v, w);
    }
    

    private void  bfs(Digraph G, int v, int w) {    	
    	int shortestDist = INFINITY;
    	counter = 0;
    	
    	if(v == w) {
    		counter = 1;
    		return;
    	}
    	
        Queue<Integer> q = new Queue<Integer>();
        marked[v] = true;
        distTo[v] = 0;
        q.enqueue(v);
        while (!q.isEmpty()) {
            int n = q.dequeue();
            for (int j : G.adj(n)) {
                if (distTo[j] >= distTo[n] + 1) {
                    edgeTo[j] = n;
                    distTo[j] = distTo[n] + 1;
                    
                    if (j == w) {
						if (distTo[j] < shortestDist) {
							//StdOut.println("w-1 = " + n);
							shortestDist = distTo[j];
							counter = 1;
						}
						else if (distTo[j] == shortestDist) {
							//StdOut.println("w-1 = " + n);
							counter++;
						}
					}
                    else {
                    	q.enqueue(j);
                    	marked[j] = true;
                    }
                }
            }
        }
    }




    
    public int numberOfShortestPaths() {
        return counter;
    }

    

    /**
     * Unit tests the <tt>BreadthFirstDirectedPaths</tt> data type.
     */
    public static void main(String[] args) {
        In in = new In();
        Digraph G = new Digraph(in);
        // StdOut.println(G);

        int numberOfChecks = in.readInt();
        for (int i = 0; i < numberOfChecks; i++) {
        	int v = in.readInt(), w = in.readInt();
        	BreadthFirstNumberOfShortestDirectedPaths bfs = new BreadthFirstNumberOfShortestDirectedPaths(G, v ,w);
        	StdOut.println(bfs.numberOfShortestPaths());
		}

    }


}

