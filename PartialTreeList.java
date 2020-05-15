package app;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.NoSuchElementException;

import structures.Arc;
import structures.Graph;
import structures.MinHeap;
import structures.PartialTree;
import structures.Vertex;

/**
 * Stores partial trees in a circular linked list
 * 
 */
public class PartialTreeList implements Iterable<PartialTree> {
    
	/**
	 * Inner class - to build the partial tree circular linked list 
	 * 
	 */
	public static class Node {
		/**
		 * Partial tree
		 */
		public PartialTree tree;
		
		/**
		 * Next node in linked list
		 */
		public Node next;
		
		/**
		 * Initializes this node by setting the tree part to the given tree,
		 * and setting next part to null
		 * 
		 * @param tree Partial tree
		 */
		public Node(PartialTree tree) {
			this.tree = tree;
			next = null;
		}
	}

	/**
	 * Pointer to last node of the circular linked list
	 */
	private Node rear;
	
	/**
	 * Number of nodes in the CLL
	 */
	private int size;
	
	/**
	 * Initializes this list to empty
	 */
    public PartialTreeList() {
    	rear = null;
    	size = 0;
    }

    /**
     * Adds a new tree to the end of the list
     * 
     * @param tree Tree to be added to the end of the list
     */
    public void append(PartialTree tree) {
    	Node ptr = new Node(tree);
    	if (rear == null) {
    		ptr.next = ptr;
    	} else {
    		ptr.next = rear.next;
    		rear.next = ptr;
    	}
    	rear = ptr;
    	size++;
    }

    /**
	 * Initializes the algorithm by building single-vertex partial trees
	 * 
	 * @param graph Graph for which the MST is to be found
	 * @return The initial partial tree list
	 */
	public static PartialTreeList initialize(Graph graph) {
	
		/* COMPLETE THIS METHOD */
		
		PartialTreeList L = new PartialTreeList(); //step 1
		
		for(int i=0;i<graph.vertices.length;i++) {
			//steps 2
			Vertex v = graph.vertices[i];
			
			PartialTree T = new PartialTree(v);
			
			MinHeap<Arc> P = T.getArcs();
			
			while(v.neighbors!=null) {
				
				
				Arc Arcs = new Arc(v,v.neighbors.vertex,v.neighbors.weight);
			P.insert(Arcs);
			
			
				
				v.neighbors=v.neighbors.next;
	
				
			}
			
			L.append(T);
			
		}
	
		
		
		return L;
	}
	
	/**
	 * Executes the algorithm on a graph, starting with the initial partial tree list
	 * for that graph
	 * 
	 * @param ptlist Initial partial tree list
	 * @return Array list of all arcs that are in the MST - sequence of arcs is irrelevant
	 */
	public static ArrayList<Arc> execute(PartialTreeList ptlist) {
		
		/* COMPLETE THIS METHOD */
		
		ArrayList<Arc> Arcs = new ArrayList<Arc>();
		//remove partial trees until there is one
		
		while(ptlist.size()>1) {
			
			//remove from L and store it in PTX
			PartialTree PTX=ptlist.remove();
			
			Arc PQX=PTX.getArcs().deleteMin();//step 4
			
			//get vertex from PTX min 
			Vertex v2= PQX.getv2();
			//while PTX has something in it
			while(!PTX.getArcs().isEmpty()) {
				/**
				 * if v2 is in PTX
				 */
				if(vertexContains(PTX,v2)) {
					/**
					 * going back to step4 updating and deleting min and updating PQX and v2
					 */
					PQX=PTX.getArcs().deleteMin();
				
					v2=PQX.getv2();
					
				}
				else {
					break;
				}
			
				
				
			}
			
			
			//combiinig ptx and pty add to returning arcs array list and append the resulting tree to L
		
			PartialTree PTY = ptlist.removeTreeContaining(v2);
			PTX.merge(PTY);
			Arcs.add(PQX);
			ptlist.append(PTX);
		
			
			
			
			
		}
		
		
		
		
		
		return Arcs;
		
		
		
		
		
		
		
		
		

		
	}
	
	/**
	 * if vertex 2 is contained in PTX then return true or false
	 * @param root
	 * @param two
	 * @return
	 */
	private static boolean vertexContains(PartialTree root, Vertex two) {
		if(root.getRoot().equals(two.getRoot())) {
			return true;
		}
		else {
			return false;
		}
	}
	
    /**
     * Removes the tree that is at the front of the list.
     * 
     * @return The tree that is removed from the front
     * @throws NoSuchElementException If the list is empty
     */
    public PartialTree remove() 
    throws NoSuchElementException {
    			
    	if (rear == null) {
    		throw new NoSuchElementException("list is empty");
    	}
    	PartialTree ret = rear.next.tree;
    	if (rear.next == rear) {
    		rear = null;
    	} else {
    		rear.next = rear.next.next;
    	}
    	size--;
    	return ret;
    		
    }

    /**
     * Removes the tree in this list that contains a given vertex.
     * 
     * @param vertex Vertex whose tree is to be removed
     * @return The tree that is removed
     * @throws NoSuchElementException If there is no matching tree
     */
    public PartialTree removeTreeContaining(Vertex vertex) 
    throws NoSuchElementException {
    		/* COMPLETE THIS METHOD */
    	
PartialTree treeremove =null;
    	
    	Node pointer =rear.next;
    	Node prev= rear;//this will be to keep track of the prev node so when we delete we will have a reference
    	
    	boolean exists= false;
    	if(rear==null) {
    		throw new NoSuchElementException("no tree!");
    	}
    	
    	/**
    	 * will go through cll and then compare the pointer tree root to the one that needs
    	 * to be removed and sees if they match if they do then store the removed tree in 
    	 * treeremove variable and change pointers
    	 */
    	while(pointer!=rear) {
    		if(isMatch(vertex,pointer)) {
    			//sets the partial tree that needs to be removed to treeremove
    			treeremove =pointer.tree;
    			//changing pointers
    			
    			prev.next=pointer.next;
    			//since we are removing we need to change size
    			size--;
    			exists= true;
    			break;
    		}
    		//traverse through cll
    		prev=pointer;
    		pointer=pointer.next;
    	}
    	//if the pointer is one node and vertex matches
    	if (pointer==rear && pointer.tree.getRoot().name.equals(vertex.getRoot().name)){
			treeremove = pointer.tree;//set tree to be removed to pointer tree
			prev.next=pointer.next;//change pointers
			rear = prev;
			size--;//change size
			
			exists =true;
		}
    	//this would mean that the tree doesnt exist so return nosuchelement exception
    	else if(exists==false){
    		
    		throw new NoSuchElementException("no tree found matches!");
    	}
    	
    	if(size==0) {
    		rear=null;
    	}
    	
    	
    	
    	
    	
    	
    		return treeremove;
     }
    
    
    //this checks if vertex is found in tree
 private static boolean isMatch(Vertex v , Node current) {
    
    	
    	if(current.tree.getRoot().name.equals(v.getRoot().name)) {
    		return true;
    	}
    	else {
    		return false;
    	}
    	
    }
 

    
    
    /**
     * Gives the number of trees in this list
     * 
     * @return Number of trees
     */
    public int size() {
    	return size;
    }
    
    /**
     * Returns an Iterator that can be used to step through the trees in this list.
     * The iterator does NOT support remove.
     * 
     * @return Iterator for this list
     */
    public Iterator<PartialTree> iterator() {
    	return new PartialTreeListIterator(this);
    }
    
    private class PartialTreeListIterator implements Iterator<PartialTree> {
    	
    	private PartialTreeList.Node ptr;
    	private int rest;
    	
    	public PartialTreeListIterator(PartialTreeList target) {
    		rest = target.size;
    		ptr = rest > 0 ? target.rear.next : null;
    	}
    	
    	public PartialTree next() 
    	throws NoSuchElementException {
    		if (rest <= 0) {
    			throw new NoSuchElementException();
    		}
    		PartialTree ret = ptr.tree;
    		ptr = ptr.next;
    		rest--;
    		return ret;
    	}
    	
    	public boolean hasNext() {
    		return rest != 0;
    	}
    	
    	public void remove() 
    	throws UnsupportedOperationException {
    		throw new UnsupportedOperationException();
    	}
    	
    }
}
