package structures;

import java.util.*;

/**
 * This class implements an HTML DOM Tree. Each node of the tree is a TagNode, with fields for
 * tag/text, first child and sibling.
 *
 */
public class Tree {

	/**
	 * Root node
	 */
	TagNode root=null;

	/**
	 * Scanner used to read input HTML file when building the tree
	 */
	Scanner sc;

	/**
	 * Initializes this tree object with scanner for input HTML file
	 *
	 * @param sc Scanner for input HTML file
	 */
	public Tree(Scanner sc) {
		this.sc = sc;
		root = null;
	}

	/**
	 * Builds the DOM tree from input HTML file, through scanner passed
	 * in to the constructor and stored in the sc field of this object.
	 *
	 * The root of the tree that is built is referenced by the root field of this object.
	 */
	public void build() {
		/** COMPLETE THIS METHOD **/
		String current = null;
		Stack <TagNode> tag = new Stack<TagNode>();


		if(sc.hasNextLine()) {
			current= sc.nextLine();

		}
		else {
			return;
		}
		root = new TagNode("html", null, null);
		tag.push(root);


		while (sc.hasNextLine()) {
			//this will hold the current tag or text that we are in
			current = sc.nextLine();
/**
 * if statement to see if it has > or not and if it contains a closing / or not
 */
			if (current.contains(">") && current.contains("<") && current.contains("/")) {

				tag.pop();//pop this out of the stack because we cannot have a /
			}


			else if (current.contains(">") && current.contains("<") && !current.contains("/")) {



				//this is to see if the first child is null if so add the tag to the first child
				if (tag.peek().firstChild == null) {
					current=current.replace(">", "");

					current=current.replace("<", "");

					TagNode replace = new TagNode(current,null,null);

					tag.peek().firstChild = replace;

					tag.push(replace);
				}

				else {


					//if the tags first child is not null then that mean it has siblings in the same level
					TagNode Findsibling = tag.peek().firstChild;

					//this will get to the appropriate sibling pointer by checking all the pointers in the level and getting to the right most one


					while (Findsibling.sibling != null) {
						Findsibling = Findsibling.sibling;
					}


					current=current.replace(">", "");
					current=current.replace("<", "");
					TagNode replace = new TagNode(current,null,null);
					Findsibling.sibling = replace;
					tag.push(replace);
				}
			}

			else {



				if (tag.peek().firstChild == null) {
					tag.peek().firstChild = new TagNode(current, null, null);
				} else {


					//if the first child is not null then find the sibling pointer furthest to the right on the level
					TagNode FindSibling = tag.peek().firstChild;

					while (FindSibling.sibling != null) {
						FindSibling = FindSibling.sibling;
					}

					FindSibling.sibling = new TagNode(current, null, null);
				}
			}




		}






























	}

	/**
	 * Replaces all occurrences of an old tag in the DOM tree with a new tag
	 *
	 * @param oldTag Old tag
	 * @param newTag Replacement tag
	 */
	public void replaceTag(String oldTag, String newTag) {
		/** COMPLETE THIS METHOD **/


////		 TagNode Child = root.firstChild;
////		 TagNode sib= root.sibling;
////
//
//
////		 //this for loop is to go through all the first childs in the linked lists and replace tags if they match
////		 while(Child!=null ) {
////
////
////
////			 if(Child.tag.equals(oldTag)) {
////				 Child.tag=newTag;
////
////
////
////			 }
////
////			while(Child.sibling!=null) {
////
////
////				 if(Child.sibling.tag.equals(oldTag)) {
////					 Child.sibling.tag=newTag;
////
////
////
////				 }
////				 while()
////
////
////				 Child=Child.sibling;
////
////			}
////
////
////
////
////
////			 Child= Child.firstChild;
////
////
////
////			 }
////



		replaceRecurse(oldTag,newTag,root.firstChild);



	}
	private static void replaceRecurse(String oldTag,String newTag, TagNode current) {

		//base case
		if(current==null) {return;	}
		//checck if tag matches the and replaces where necessary
		if(current.tag.equals(oldTag)) {
			current.tag=newTag;
		}
		//will traverse through firstchild until it hits null pointer than it will return the whatver it has and then it will go onto next recursive call
		replaceRecurse(oldTag,newTag,current.firstChild);

		//will traverse through siblings of the childs on the same level and return whatever it has when it encounters a null sibling and go back
		//on on to the first child pointers and so on until both are null and the recursive calls end
		replaceRecurse(oldTag,newTag,current.sibling);
	}





	/**
	 * Boldfaces every column of the given row of the table in the DOM tree. The boldface (b)
	 * tag appears directly under the td tag of every column of this row.
	 *
	 * @param row Row to bold, first row is numbered 1 (not 0).
	 */
	public void boldRow(int row) {
		/** COMPLETE THIS METHOD **/
		//since there is one table we use this method to count the number of "tr" encountered andd once it matches the row number than we modify the tree
traverseSearch(root,root.firstChild,row,0);


	}



	private static void traverseSearch(TagNode prev,TagNode current,int row,int count) {
//if the tree is empty
		if(current==null) {
			return;
		}

		//if a row is encountered than just increment that count by 1 until it reaches the row
		if(current.tag.equals("tr")) {
			count++;
		}

		//if the count is equal to the road inputted and the next node is text and not a tag then modify the prev tree node and insert b into the tag
		//then make it point to the next node current
		if(count==row && current.firstChild==null) {
			prev.firstChild= new TagNode("b",current,null);
		}

		traverseSearch(current,current.firstChild,row,count);
		traverseSearch(current,current.sibling,row,count);

	}

	/**
	 * Remove all occurrences of a tag from the DOM tree. If the tag is p, em, or b, all occurrences of the tag
	 * are removed. If the tag is ol or ul, then All occurrences of such a tag are removed from the tree, and,
	 * in addition, all the li tags immediately under the removed tag are converted to p tags.
	 *
	 * @param tag Tag to be removed, can be p, em, b, ol, or ul
	 */
	public void removeTag(String tag) {
		/** COMPLETE THIS METHOD **/


		if(tag==null){
			return;
		}


		removesTag(tag,root,root.firstChild);




	}

	private static void removesTag(String tagtoDelete,TagNode prev,TagNode Current){



		if(Current == null){
			return;
		}
		if(prev == null){
			return;
		}

		/**
		 * this if statement will remove all the occurrences of tags and change pointers to the approriate places
		 */
		if(Current.tag.equals("p") ||Current.tag.equals("em") ||Current.tag.equals("b") ){


			/**
			 * checks to see if the first child is null
			 * and if it is not then the p tag lies in the first child
			 */
			if(prev.firstChild!=null){

				/**
				 * check first child of the current tag and see if it exists
				 */

				if(Current.firstChild!=null){
					/**
					 * need to change pointer of prev first child to point to current first child
					 *
					 * check to see if the first child of the firstchild fo current is null
					 * if it is then we have text in that node meaning there is no firstchild
					 *
					 * need to change the pointer
					 */

					/**
					 * if the first child of the first child of current is null then
					 * it is text
					 */
					if(Current.firstChild.firstChild==null){
						/**
						 * since the first child is null then i need to make prev.firstchild point to Current.firstchild
						 *
						 */
							prev.firstChild=Current.firstChild;
						/**
						 * since the child sibling isnt null we need to set the prev.firstchild sibling to
						 * current sibling
						 */

						if(Current.sibling!=null) {
								prev.firstChild.sibling = Current.sibling;
							}

						/**
						 * now need to set pointers to siblings of current.firstchild node
						 *
						 */

						if(Current.sibling.firstChild!=null){
							//point current sibling first child to
							Current.sibling.firstChild=Current.firstChild.sibling;

						}






					}










				}
				/**
				 * check the sibling of the current tag to see if it exists
				 *
				 */
				else if (Current.sibling!=null){

				}

			}














			}















		removesTag(tagtoDelete,Current,Current.firstChild);
		removesTag(tagtoDelete,Current,Current.sibling);



		}















	/**
	 * Adds a tag around all occurrences of a word in the DOM tree.
	 *
	 * @param word Word around which tag is to be added
	 * @param tag Tag to be added
	 */
	public void addTag(String word, String tag) {
		/** COMPLETE THIS METHOD **/

		if(tag.equals("em")||tag.equals("b")) {

			tagging(word,tag,root,root.firstChild);
		}
		else
		{
			throw new IllegalArgumentException("cannot add tag");
		}







	}
//now to solve the problem with having multiple things in one node like mutiple matches
	private static void tagging(String phrase, String tags, TagNode prev,TagNode current) {
		if(current==null) {
			return;
		}


		String[] split = current.tag.split(" ");//will split string by the spaces in the array

		String build="";

		String checkforend ="";
		boolean didmatch=false;

		boolean firstelem = false;

		TagNode first = new TagNode(phrase,null,null);

		TagNode sib= new TagNode(tags,first,null);

		boolean foundfirst=false;

boolean punct =false;



		int count =0; // number of times that the phrase shows up in the phrase
/**
 * try and figure how to do two occurences on the same line
 * possible solution make the tag nodes be created in the if statements
 */
		for(int i=0;i<split.length;i++){




			if(split[i].contains(phrase+".")){
				first.tag=phrase+"." ;
				punct=true;
			}
			else if(split[i].contains(phrase+"!")){
				first.tag=phrase+"!" ;
				punct=true;
			}
			else if(split[i].contains(phrase+"?")){
				first.tag=phrase+"?" ;
				punct=true;
			}
			else if(split[i].contains(phrase+";")){
				first.tag=phrase+";" ;
				punct=true;
			}
			else if(split[i].contains(phrase+":")){
				first.tag=phrase+":" ;
				punct=true;
			}



			//if word is encountred in phrase then chang pointers and add the tag



			if(split[i].equalsIgnoreCase(phrase) || punct==true ){

				didmatch =true;

				count++;
				//if there is stuff before the actual word was found include into the tree

				if(!build.equals("") && count == 1){

//					TagNode first = new TagNode(phrase,null,null);
//
//					TagNode sib= new TagNode(tags,first,null);

					prev.firstChild= new TagNode(build,null,sib);
					build="";

					punct=false;

				}
				/**
				 * if there is more than one occurrence of the word in the sentence then you cannot use the prev node
				 */
				else if(!build.equals("") && count != 1){



					//this node will hold the word after tagging make sure it points to nothing
					TagNode closedword = new TagNode(phrase,null,null);
					//this node is the node that needs to be added to connect to the trees so it adds the tag before the word
					TagNode tmp = new TagNode(tags,closedword,null);
					sib.sibling= new TagNode(build,null,tmp);
					build ="";
				}
				else {
					/**
					 * if the word happens to be the first word than use pointer
					 */



					 foundfirst=true;


					firstelem =true;


//
				punct=false;
				}

				continue;
			}

			punct=false;

			build=build+" "+split[i];

		}

			if(didmatch){

				//if the word looking for is in the first spot do this
				if(firstelem){

					TagNode sibRest = new TagNode(build,null,null);
					prev.firstChild = new TagNode(tags, first, sibRest);
				}
				//if it is not in the first spot
				if(!build.equals("")){

sib.sibling= new TagNode(build,null,null);

				}
			}



		tagging(phrase,tags,current,current.firstChild);
		tagging(phrase,tags,current,current.sibling);
	}



	/**
	 * Gets the HTML represented by this DOM tree. The returned string includes
	 * new lines, so that when it is printed, it will be identical to the
	 * input file from which the DOM tree was built.
	 *
	 * @return HTML string, including new lines.
	 */
	public String getHTML() {
		StringBuilder sb = new StringBuilder();
		getHTML(root, sb);
		return sb.toString();
	}

	private void getHTML(TagNode root, StringBuilder sb) {
		for (TagNode ptr=root; ptr != null;ptr=ptr.sibling) {
			if (ptr.firstChild == null) {
				sb.append(ptr.tag);
				sb.append("\n");
			} else {
				sb.append("<");
				sb.append(ptr.tag);
				sb.append(">\n");
				getHTML(ptr.firstChild, sb);
				sb.append("</");
				sb.append(ptr.tag);
				sb.append(">\n");
			}
		}
	}

	/**
	 * Prints the DOM tree.
	 *
	 */
	public void print() {
		print(root, 1);
	}

	private void print(TagNode root, int level) {
		for (TagNode ptr=root; ptr != null;ptr=ptr.sibling) {
			for (int i=0; i < level-1; i++) {
				System.out.print("      ");
			};
			if (root != this.root) {
				System.out.print("|----");
			} else {
				System.out.print("     ");
			}
			System.out.println(ptr.tag);
			if (ptr.firstChild != null) {
				print(ptr.firstChild, level+1);
			}
		}
	}
}