package poly;

import java.io.IOException;
import java.util.Scanner;

/**
 * This class implements evaluate, add and multiply for polynomials.
 * 
 * @author runb-cs112
 *
 */
public class Polynomial {
	
	/**
	 * Reads a polynomial from an input stream (file or keyboard). The storage format
	 * of the polynomial is:
	 * <pre>
	 *     <coeff> <degree>
	 *     <coeff> <degree>
	 *     ...
	 *     <coeff> <degree>
	 * </pre>
	 * with the guarantee that degrees will be in descending order. For example:
	 * <pre>
	 *      4 5
	 *     -2 3
	 *      2 1
	 *      3 0
	 * </pre>
	 * which represents the polynomial:
	 * <pre>
	 *      4*x^5 - 2*x^3 + 2*x + 3 
	 * </pre>
	 * 
	 * @param sc Scanner from which a polynomial is to be read
	 * @throws IOException If there is any input error in reading the polynomial
	 * @return The polynomial linked list (front node) constructed from coefficients and
	 *         degrees read from scanner
	 */
	public static Node read(Scanner sc) 
	throws IOException {
		Node poly = null;
		while (sc.hasNextLine()) {
			Scanner scLine = new Scanner(sc.nextLine());
			poly = new Node(scLine.nextFloat(), scLine.nextInt(), poly);
			scLine.close();
		}
		return poly;
	}
	
	/**
	 * Returns the sum of two polynomials - DOES NOT change either of the input polynomials.
	 * The returned polynomial MUST have all new nodes. In other words, none of the nodes
	 * of the input polynomials can be in the result.
	 * 
	 * @param poly1 First input polynomial (front of polynomial linked list)
	 * @param poly2 Second input polynomial (front of polynomial linked list
	 * @return A new polynomial which is the sum of the input polynomials - the returned node
	 *         is the front of the result polynomial
	 */
	public static Node add(Node poly1, Node poly2) {
		/** COMPLETE THIS METHOD **/
		// FOLLOWING LINE IS A PLACEHOLDER TO MAKE THIS METHOD COMPILE
		// CHANGE IT AS NEEDED FOR YOUR IMPLEMENTATION
		//Equation for the first polynomial
		Node Equation1=poly1;
		//Equation for the second polynomial
        Node Equation2=poly2;
        Node Summation= null;
        Node hold=null;
        //this will hold the temporary adding
        float tmp =0;

        
        if(Equation1==null) {
        	return Equation2;
        }
        if(Equation2==null) {
        	return Equation1;
        }

        //loops through both equations and check to see if both are null
        while(Equation1!=null && Equation2!=null) {
        	
        	//if the degree is higher when the terms are compared then just place 
        	//the equation down
        	
        	
        	
        	if(Equation2.term.degree <Equation1.term.degree) {
        		
        	//	Summation = new Node(Equation2.term.coeff,Equation2.term.degree,Summation);
        		Summation = new Node(Equation1.term.coeff,Equation1.term.degree,Summation);
        		
        		
        		//goes to next term to see if this term will match the equation
        		
        			Equation2 =Equation2.next;
        		
        		
        		
        	}
        	
        	
        	
        	else if(Equation2.term.degree >Equation1.term.degree) {
        		Summation = new Node(Equation1.term.coeff,Equation1.term.degree,Summation);
        		//goes to next term to see if this term will match the equation
        		
        		Equation1= Equation1.next;
        	}
        		
        	else if(Equation1.term.degree==Equation2.term.degree) {
        		tmp= Equation1.term.coeff + Equation2.term.coeff;
        		if(tmp==0) {
        			break;
        		}
        	
        				Summation = new Node(tmp,Equation1.term.degree,Summation);
        		
        				
        				if(Equation1!=null) {
        				Equation1=Equation1.next;
        				}
        				
        				if(Equation2!=null){
        					Equation2=Equation2.next;
        				}
        				
        		
        		
        	}
        	
        	
        	
        	
        	
        }
        
        
        
        if(Equation1 == null) {
        	
        	Equation2= Equation2.next;
        	while(Equation2!= null) {
        		
        		Summation = new Node(Equation2.term.coeff,Equation2.term.degree,Summation);
        		Equation2 = Equation2.next;
        	}
        }
        
        if(Equation2 == null) {
        	
        	Equation1=Equation1.next;
        	while(Equation1!= null) {
        		
        		Summation = new Node(Equation1.term.coeff,Equation1.term.degree,Summation);
        		Equation1 = Equation1.next;
        	}
        }
        
        
        		while(Summation!=null) {
        			hold = new Node(Summation.term.coeff,Summation.term.degree,hold );
        			Summation = Summation.next;
        		}
    
		return hold;
	}
	
	
	
	/**
	 * Returns the product of two polynomials - DOES NOT change either of the input polynomials.
	 * The returned polynomial MUST have all new nodes. In other words, none of the nodes
	 * of the input polynomials can be in the result.
	 * 
	 * @param poly1 First input polynomial (front of polynomial linked list)
	 * @param poly2 Second input polynomial (front of polynomial linked list)
	 * @return A new polynomial which is the product of the input polynomials - the returned node
	 *         is the front of the result polynomial
	 */
	public static Node multiply(Node poly1, Node poly2) {
		/** COMPLETE THIS METHOD **/
		// FOLLOWING LINE IS A PLACEHOLDER TO MAKE THIS METHOD COMPILE
		// CHANGE IT AS NEEDED FOR YOUR IMPLEMENTATION
		//Equation for the first polynomial
		
			Node Equation1=poly1;
		
			//Equation for the second polynomial
		
			Node Equation2=poly2;
		        
		  	Node Summation= null;
		  	
		  	Node Result=null;
		        
		  
		  	
		   //this will hold the temporary adding
	        float tmp =0;
	        //holds degree
	        
	        int deg =0;
	    
	        if(Equation1==null) {
	              	return Equation2;
	              }
	             
	        if(Equation2==null) {
	              	return Equation1;
	              }


	        //loops through both equations and check to see if both are null
	        while( Equation2!=null) {
	        	
	        	tmp= Equation1.term.coeff*Equation2.term.coeff;
	        	deg = Equation1.term.degree+Equation2.term.degree;
	        	Summation = new Node(tmp,deg,Summation);
	        	
	        	Equation1=Equation1.next;
	        	
	        	if(Equation1==null) {
	        		Equation1=poly1;
	        		Equation2=Equation2.next;
	        	}
	        			}
	        
	        Node Answer =null;
	        Node hold =null;
	        	while(Summation!=null) {
	        		tmp = Summation.term.coeff;
	        		hold=Summation.next;
	        		while(hold!=null) {
	        			
	        			if(Summation.term.degree==hold.term.degree) {
	        				tmp= tmp +hold.term.coeff;
	        				hold.term.coeff=0;	
	        				
	        			}
	        			
	        			hold = hold.next;
	        			
	        		}
	        		
	        		
	        		Answer= new Node(tmp,Summation.term.degree,Answer);
	        		Summation = Summation.next;
	        		
	        		
	        		
	        	}
	        Summation =null;
	        	while(Answer!=null) {
	        		
	        		while(Answer.term.coeff==0) {
	        			Answer=Answer.next;
	        		}
	        		
	        		Summation = new Node(Answer.term.coeff,Answer.term.degree,Summation);
	        		Answer=Answer.next;
	        		
	        		
	        		
	        	}
	        	
	        	Answer= Summation;
	        	Equation1=null;
	        	Equation2=Summation;
	        	tmp =Answer.term.coeff;
	        	deg=Answer.term.degree;
	        	int max =deg;
	        	
	        	
	        	
	        	//find max degree of polynomial	
        		while(Answer.next!=null ) {
        			
        			
        			if(Answer.term.degree>deg) {
        				tmp=Answer.term.coeff;
        				deg=Answer.term.degree;
        			}
        			
        			Answer=Answer.next;
        			
        		}
        		
        		
        		
        		Equation1= new Node(tmp,deg,Equation1);
        		
        		Answer= null;
        		
        		 max=Equation1.term.degree;
        		 
        		 Equation2=null;
        		 //arranges polynomials descending
        		 while(max!=-1) {
        			 
        			 Answer=Summation;
        			 while(Answer!=null) {
        			 if(Answer.term.degree==max) {
        				 Equation2= new Node(Answer.term.coeff,Answer.term.degree,Equation2);
        				 
        			 }
        			 
        			 Answer=Answer.next;
        			 
        			 }
        			 max--;
        			
        			 
        			 
        			 
        		 }
    
			return Equation2 ;

		   
	}
		
	/**
	 * Evaluates a polynomial at a given value.
	 * 
	 * @param poly Polynomial (front of linked list) to be evaluated
	 * @param x Value at which evaluation is to be done
	 * @return Value of polynomial p at x
	 */
	public static float evaluate(Node poly, float x) {
		/** COMPLETE THIS METHOD **/
		// FOLLOWING LINE IS A PLACEHOLDER TO MAKE THIS METHOD COMPILE
		// CHANGE IT AS NEEDED FOR YOUR IMPLEMENTATION
		 float Value=x;
		Node Equation = poly;
		float sum =0;
		
		
		while(Equation!=null) {
			
			sum= (float) (sum + Equation.term.coeff*Math.pow(Value,Equation.term.degree));
			Equation=Equation.next;
		}
		
		
		
		
		
		
		
		
		return sum;
	}
	
	/**
	 * Returns string representation of a polynomial
	 * 
	 * @param poly Polynomial (front of linked list)
	 * @return String representation, in descending order of degrees
	 */
	public static String toString(Node poly) {
		if (poly == null) {
			return "0";
		} 
		
		String retval = poly.term.toString();
		for (Node current = poly.next ; current != null ;
		current = current.next) {
			retval = current.term.toString() + " + " + retval;
		}
		return retval;
	}	
}