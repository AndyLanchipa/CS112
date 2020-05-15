package app;

import java.io.*;
import java.util.*;
import java.util.regex.*;

import structures.Stack;

public class Expression {

	public static String delims = " \t*+-/()[]";
			
    /**
     * Populates the vars list with simple variables, and arrays lists with arrays
     * in the expression. For every variable (simple or array), a SINGLE instance is created 
     * and stored, even if it appears more than once in the expression.
     * At this time, values for all variables and all array items are set to
     * zero - they will be loaded from a file in the loadVariableValues method.
     * 
     * @param expr The expression
     * @param vars The variables array list - already created by the caller
     * @param arrays The arrays array list - already created by the caller
     */
    public static void 
    makeVariableLists(String expr, ArrayList<Variable> vars, ArrayList<Array> arrays) {
    	/** COMPLETE THIS METHOD **/
    	/** DO NOT create new vars and arrays - they are already created before being sent in
    	 ** to this method - you just need to fill them in.
    	 **/
    	String expression = expr; // storing the expression into a string
    	
    	String store="";
    	//goes through whole loop
    	for(int i = 0;i<expression.length(); i++) {
    		
    		if (Character.isWhitespace(expr.charAt(i))) {
    			continue;
    		}
    		//if the current character is notequal to one of the delims then just store the variable name into the store String
    		if(!delims.contains(Character.toString(expression.charAt(i))) && Character.isLetter(expression.charAt(i))) {
    			store+= expression.charAt(i);
    			
    		}
    		//if the current character is equal to one of the delims then that means that the variable is done and so put the variable into the vars array 
    		else if(delims.contains(Character.toString(expression.charAt(i)))) {
    			
    			//if there exist a left bracket that means that there is array to be added to array list
        		 if(expression.charAt(i)=='[') {
        			if(store != "") {
    	    			Array addition = new Array(store);// creates array objects
    	    			
    	    			arrays.add(addition);//adds new array object to the array list 
    	    			
    	    			store="";//resets the store items
        			
        		}
        		
        			}
    			//if nothing is stored in store than you cant add to the vars list because it is not variable
    				if(store != "" ) {
    					
    					//create a new object variable for the new variable to later store into the vars array if its not already there
    					Variable addition = new Variable(store);
    					//if list vars does not contains the stored variable then add it to the vars array list
    					if(!vars.contains(addition)) {
    						vars.add(addition);
    					}
    					
    				}
    				
    				store = "";// this will reset the stored characters so that we wont have a string with previously used characters
    			
    				}
    				
    		}
    	
    	//if there is a variable that shows up and there is no delims after it this will still put the variable into the the variable arraylist
    	
    	Variable addition = new Variable(store);
    	//checks if vars contains the variable and sees that it is not empty
    	if(!vars.contains(addition) && !store.equals("")) {
    		vars.add(addition);//checks to see if the variable is added 
    	}
    	
 
    	
    }
    
    /**
     * Loads values for variables and arrays in the expression
     * 
     * @param sc Scanner for values input
     * @throws IOException If there is a problem with the input 
     * @param vars The variables array list, previously populated by makeVariableLists
     * @param arrays The arrays array list - previously populated by makeVariableLists
     */
    public static void 
    loadVariableValues(Scanner sc, ArrayList<Variable> vars, ArrayList<Array> arrays) 
    throws IOException {
        while (sc.hasNextLine()) {
            StringTokenizer st = new StringTokenizer(sc.nextLine().trim());
            int numTokens = st.countTokens();
            String tok = st.nextToken();
            Variable var = new Variable(tok);
            Array arr = new Array(tok);
            int vari = vars.indexOf(var);
            int arri = arrays.indexOf(arr);
            if (vari == -1 && arri == -1) {
            	continue;
            }
            int num = Integer.parseInt(st.nextToken());
            if (numTokens == 2) { // scalar symbol
                vars.get(vari).value = num;
            } else { // array symbol
            	arr = arrays.get(arri);
            	arr.values = new int[num];
                // following are (index,val) pairs
                while (st.hasMoreTokens()) {
                    tok = st.nextToken();
                    StringTokenizer stt = new StringTokenizer(tok," (,)");
                    int index = Integer.parseInt(stt.nextToken());
                    int val = Integer.parseInt(stt.nextToken());
                    arr.values[index] = val;              
                }
            }
        }
        
    }
    
    /**
     * Evaluates the expression.
     * 
     * @param vars The variables array list, with values for all variables in the expression
     * @param arrays The arrays array list, with values for all array items
     * @return Result of evaluation
     */
    public static float 

    evaluate(String expr, ArrayList<Variable> vars, ArrayList<Array> arrays) {

    	/** COMPLETE THIS METHOD **/

    	// following line just a placeholder for compilation

    	Stack<Integer> number = new Stack<Integer>();// stack that is used to hold real number

 
   // 	Stack<String> variablenames = new Stack<String>();//stack that is used to hold variables in the expression

    
    	Stack<Character> operations = new Stack<Character>();// stack that will hold operations

    	

    	ArrayList<Integer> nums = new ArrayList<Integer>();

    	ArrayList<String> variablenames = new ArrayList<String>();

    	int i=0;

    	int m=0;

    	

    	String ops = "[]()";

   
    	for(i=vars.size()-1;i>=0;i--) {

    		//puts variable names into stack

    		variablenames.add(vars.get(i).name);

    		//puts varible numbers into stack

    		nums.add(vars.get(i).value);

    	}

    	

    	

    	String store="";//store variable names

    	

    	String expression ="";

    	//this loop will replace all values with actual values

    	

    	for(i=0;i<expr.length();i++) {

    		if(Character.isWhitespace(expr.charAt(i))) {

    			continue;

    		}

    		else if(Character.isLetter(expr.charAt(i))) {

    			store+=expr.charAt(i);

    		}

    		else if(Character.isDigit(expr.charAt(i))) {

    			store+=expr.charAt(i);

    		}

    		else if(delims.contains(Character.toString(expr.charAt(i)))) {

    			if(store!="") {

    			for(m=0;m<vars.size();m++) {

    				//if the store variable is equal to any variable then put that value into new expression string

    				if(vars.get(m).name.equals(store)) {

    					expression+= Integer.toString(vars.get(m).value);

    					store="";

    				}

    				

    				

    				

    			}

    			}

    			

    			if(store!="") {

    				expression+=store+expr.charAt(i);

    			}
//edited
    			if(store=="" && delims.contains(Character.toString(expr.charAt(i)))) {

    				expression+=expr.charAt(i);

    			}

    		

    			store="";


    		}

    			

    	}

    	if(store!="") {

    		for(m=0;m<vars.size();m++) {

				//if the store variable is equal to any variable then put that value into new expression string

				if(vars.get(m).name.equals(store)) {

					expression+=Integer.toString(vars.get(m).value);

					store="";

				}

			

		}

    	}

    	if(store!="") {

    		expression+=store;

    	}

 
    




/////////////////////////////////////////////////////////////////////////////////////////////////////////
ArrayList<String> exp = new ArrayList<String>();

store="";
number.clear();
operations.clear();

//this stores all values in expr to arraylist
for( i=0; i<expression.length(); i++) {
	if(Character.isDigit(expression.charAt(i))) {
		store+=expression.charAt(i);
	}
	else if(delims.contains(Character.toString(expression.charAt(i)))) {
		if(store!="") {
		exp.add(store);
		}
		
		exp.add(Character.toString(expression.charAt(i)));
		store="";
	}
	
}

if(store!="") {
	exp.add(store);
	}


//print out what is in array


//(4+7)-8


///////////////////////////////////////////////////////////////////////////////////
















	
	
	
	

String Ans = doesCalc(exp);
	
	
	
	
	
	
	
	//3+(4*7)*8
	

	
	
	


    	return Float.parseFloat(Ans);

    }
    
    //5+7-(8+9*2)
    
    private static String doesCalc(ArrayList<String> exp) {
    	int calc=0;
    	int m=0;
    	
    	int first=0;
    	
    	int second=0;
    	
    	int remove =0;
    	
    	int counter=exp.size();//length of string expression
    	
    	
    	int subStart=0;
    	
    	int i=0;
    	int subEnd=0;
    	
    	int parencounter=0;//ops left in substring in paren
    	
    	boolean prec =false;
    	
    
    	
    	//loop till thre is 1 number left in array list
    	while(counter!=1) {
    		
    	prec=false;
    	
    	/**
    	 * test code
    	 */
 ////////////////////////////////////////////////////////////////////////////   	
    
    	
    	for ( i=0; i<exp.size(); i++) {
        	
    		
    		if(exp.get(i).equals("(")) {
    		
    			subStart=i;
    			
    		}
    		else if(exp.get(i).equals(")")) {
    			subEnd=i;
    		
    		
    			break;
    		}
    	}
    	
    	
    	//if there is a paren
    	if(subEnd!=0) {
    	parencounter=subEnd-subStart+1;
    		
    		//if there is just one expression in the paren then do this
    		if(parencounter==5) {
    		
    		//go through list
    		for(m=subStart;m<=subEnd;m++) {
    			
    			if(exp.get(m).equals(")")|| exp.get(m).equals("(")) {
    				
    				continue;
    			}
    			//if the expression contains a multiply then get number before and after it
				if(exp.get(m).equals("*")|| exp.get(m).equals("/")) {
				prec=true;
						
						//these are the two digits to the left and right of the mult div sign
						first=Integer.parseInt(exp.get(m-1));
						
						second= Integer.parseInt(exp.get(m+1));
						
						
						//if the operator is equal * then mult first and second
						if(exp.get(m).equals("*")) {
							

							
							
							//this will calc the number the two numbers
							
							calc = first * second;
							
							
							
							
							
							//replace the expression string with the new one
							
							remove=m-2;
							
							exp.remove(remove);
							exp.remove(remove);
							exp.remove(remove);
							exp.remove(remove);
							
						//01234
						//3+6+8
						exp.set(remove,Integer.toString(calc));//the solved part is added to the 
						counter-=4;
						
						subStart=0;
						subEnd=0;
							break;
							
							
							
						}
				
						
						
						//if the operator is a division sign then do this
						if(exp.get(m).equals("/")) {
								
							
							calc = first/second;
							
							
							
							
							
							//will remove the numbers used for the problem
							
								remove=m-2;
							
								exp.remove(remove);
								exp.remove(remove);
								exp.remove(remove);
								exp.remove(remove);
								
					
								
							exp.set(remove,Integer.toString(calc));
							
							counter-=4;
							
							subStart=0;
							subEnd=0;
							
							break;
						}
				}
    		}
				
				
				
				
				
				
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    			//if the expression contains a multiply then get number before and after it
    		if(prec==false) {
    		for(m=subStart;m<=subEnd;m++) {
				if(exp.get(m).equals("+")|| exp.get(m).equals("-")) {
				
						
						//these are the two digits to the left and right of the mult div sign
						first=Integer.parseInt(exp.get(m-1));
						
						second= Integer.parseInt(exp.get(m+1));
						
						
						//if the operator is equal * then mult first and second
						if(exp.get(m).equals("+")) {
							

							
							
							//this will calc the number the two numbers
							
							calc = first + second;
							
							
							
							
							
							//replace the expression string with the new one
							
							remove=m-2;
							
							exp.remove(remove);
							exp.remove(remove);
							exp.remove(remove);
							exp.remove(remove);
							
						//01234
						//3+6+8
						exp.set(remove,Integer.toString(calc));//the solved part is added to the 
						counter-=4;
						subStart=0;
						subEnd=0;
							break;
							
							
							
						}
				
						
						
						//if the operator is a division sign then do this
						if(exp.get(m).equals("-")) {
								
							
							calc = first-second;
							
							
							
							
							
							//will remove the numbers used for the problem
							
								remove=m-2;
							
								exp.remove(remove);
								exp.remove(remove);
								exp.remove(remove);
								exp.remove(remove);
								
					
								
							exp.set(remove,Integer.toString(calc));
							
							counter-=4;
							
							subStart=0;
							subEnd=0;
							break;
						}
				}
    			
    		}
    			
    	
    		}
    		}
    		
    		
    		
    		
    	//5+(7+8-(7-(8*7))+8) does not work
    		
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    		
    		
    		//if there are more than one ops in counter than do this
    		while (parencounter>5) {
    			
    		
    			
    			//go through list
        		for(m=subStart;m<=subEnd;m++) {
        			
        			if(exp.get(m).equals(")")|| exp.get(m).equals("(")) {
        				
        				continue;
        			}
        			//if the expression contains a multiply then get number before and after it
    				if(exp.get(m).equals("*")|| exp.get(m).equals("/")) {
    				prec=true;
    						
    						//these are the two digits to the left and right of the mult div sign
    						first=Integer.parseInt(exp.get(m-1));
    						
    						second= Integer.parseInt(exp.get(m+1));
    						
    						
    						//if the operator is equal * then mult first and second
    						if(exp.get(m).equals("*")) {
    							

    							
    							
    							//this will calc the number the two numbers
    							
    							calc = first * second;
    							
    							
    							
    							
    							
    							//replace the expression string with the new one
    							
    							remove=m-1;
    							
    							exp.remove(remove);
    							exp.remove(remove);
    							parencounter-=2;
    							
    						//01234
    						//3+6+8
    						exp.set(remove,Integer.toString(calc));//the solved part is added to the 
    						counter-=2;
    						
    					
    							break;
    							
    							
    							
    						}
    				
    						
    						
    						//if the operator is a division sign then do this
    						if(exp.get(m).equals("/")) {
    								
    							
    							calc = first/second;
    							
    							
    							
    							
    							
    							//will remove the numbers used for the problem
    							
    								remove=m-1;
    							
    								exp.remove(remove);
    								exp.remove(remove);
    								parencounter-=2;
    								
    								
    					
    								
    							exp.set(remove,Integer.toString(calc));
    							
    							counter-=2;
    							
    						
    							
    							break;
    						}
    				}
        		}
    				
    				
    				
    				
    				
    				
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        			//if the expression contains a multiply then get number before and after it
        		if(prec==false) {
        		for(m=subStart;m<=subEnd;m++) {
    				if(exp.get(m).equals("+")|| exp.get(m).equals("-")) {
    				
    						
    						//these are the two digits to the left and right of the mult div sign
    						first=Integer.parseInt(exp.get(m-1));
    						
    						second= Integer.parseInt(exp.get(m+1));
    						
    						
    						//if the operator is equal * then mult first and second
    						if(exp.get(m).equals("+")) {
    							

    							
    							
    							//this will calc the number the two numbers
    							
    							calc = first + second;
    							
    							
    							
    							
    							
    							//replace the expression string with the new one
    							
    							remove=m-1;
    							
    							exp.remove(remove);
    							exp.remove(remove);
    							parencounter-=2;
    							
    						//01234
    						//3+6+8
    						exp.set(remove,Integer.toString(calc));//the solved part is added to the 
    						counter-=2;
    						
    							break;
    							
    							
    							
    						}
    				
    						
    						
    						//if the operator is a division sign then do this
    						if(exp.get(m).equals("-")) {
    								
    							
    							calc = first-second;
    							
    							
    							
    							
    							
    							//will remove the numbers used for the problem
    							
    								remove=m-1;
    							
    								exp.remove(remove);
    								exp.remove(remove);
    								parencounter-=2;
    								
    					
    								
    							exp.set(remove,Integer.toString(calc));
    							
    							counter-=2;
    							
    							
    							break;
    						}
    				}
        			
        		}
        			
        	
        		}
    			
    			
    			
    			
    			
    		}
    		
    		
    	}
    	
    	
    	
    	
    	
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////	
    	
    	
 
    	
    	
    	
    	
    	
    			//this will check the expression to see if there is a mult or div sign
    			
    			
    
    			
    			
    			
    			if(subEnd==0) {
    			for(m=0;m<exp.size();m++) {
    				
    				

    				//if the expression contains a multiply then get number before and after it
    				if(exp.get(m).equals("*")|| exp.get(m).equals("/")) {
    				prec=true;
    						
    						//these are the two digits to the left and right of the mult div sign
    						first=Integer.parseInt(exp.get(m-1));
    						
    						second= Integer.parseInt(exp.get(m+1));
    						
    						
    						//if the operator is equal * then mult first and second
    						if(exp.get(m).equals("*")) {
    							

    							
    							
    							//this will calc the number the two numbers
    							
    							calc = first * second;
    							
    							
    							
    							
    							
    							//replace the expression string with the new one
    							
    							remove=m-1;
    							
    							exp.remove(remove);
    							exp.remove(remove);
    							
    						//01234
    						//3+6+8
    						exp.set(remove,Integer.toString(calc));//the solved part is added to the 
    						counter-=2;
    							break;
    							
    							
    							
    						}
    				
    						
    						
    						//if the operator is a division sign then do this
    						if(exp.get(m).equals("/")) {
    								
    							
    							calc = first/second;
    							
    							
    							
    							
    							
    							//will remove the numbers used for the problem
    							
    								remove=m-1;
    							
    							exp.remove(remove);
    							exp.remove(remove);
    							
    					
    								
    							exp.set(remove,Integer.toString(calc));
    							
    							counter-=2;
    							
    							
    							break;
    						}
    				}
    						

    						
    					
    				}
    			//if no mult or div is found go and add or subtract from the number given
    			if(prec==false) {
    				
    				for(m=0;m<exp.size();m++) {
    					
    				
    				//if the operator is equal to add or subtract
    				if(exp.get(m).equals("+")|| exp.get(m).equals("-")) {
    					
    					
    			//these are the two digits to the left and right of the mult div sign
    			first=Integer.parseInt(exp.get(m-1));

    			second= Integer.parseInt(exp.get(m+1));
    			
    			
    			//if the operator is equal * then mult first and second
    			if(exp.get(m).equals("+")) {
    				
    				
    				//this will calc the number the two numbers
    				
    				calc = first + second;
    				
    				
    				
    				
    				
    				//replace the expression string with the new one
    				
    				remove=m-1;
    				
    				exp.remove(remove);
    				exp.remove(remove);
    				
    		
    			//01234
    			//3+6+8
    			exp.set(remove,Integer.toString(calc));//the solved part is added to the 
    			
    			
    			counter-=2;
    				break;
    				
    				
    				
    			}
    			
    			
    			//if the operator is a division sign then do this
    			if(exp.get(m).equals("-")) {
    					
    				
    				calc = first-second;
    				
    				
    				
    				
    				
    				//will remove the numbers used for the problem
    				
    					remove=m-1;
    				
    				exp.remove(remove);
    				exp.remove(remove);
    				
    		
    					
    				exp.set(remove,Integer.toString(calc));
    				
    				
    				counter-=2;
    				break;
    			}
    					
    				}
    				
    				
    				
    				
    				
    				}
    				
    				
    				
    			}
    			
    			
    			
    			
    			
    			
    			
    			
    	}
    			
    			
    			
    			subStart=0;
    			subEnd=0;
    			
    			
    			
    			
    			
    			
    		}
    		
    		
    		
    	
    	
    	
    	
    	calc = Integer.parseInt(exp.get(0));
    	
    	
    	
    	
    	return Integer.toString(calc);
    	

    	
    }
    
   
}