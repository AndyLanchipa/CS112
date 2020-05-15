package lse;

import java.io.*;
import java.util.*;

/**
 * This class builds an index of keywords. Each keyword maps to a set of pages in
 * which it occurs, with frequency of occurrence in each page.
 *
 */
public class LittleSearchEngine {

	/**
	 * This is a hash table of all keywords. The key is the actual keyword, and the associated value is
	 * an array list of all occurrences of the keyword in documents. The array list is maintained in
	 * DESCENDING order of frequencies.
	 */
	HashMap<String,ArrayList<Occurrence>> keywordsIndex;

	/**
	 * The hash set of all noise words.
	 */
	HashSet<String> noiseWords;

	/**
	 * Creates the keyWordsIndex and noiseWords hash tables.
	 */
	public LittleSearchEngine() {
		keywordsIndex = new HashMap<String,ArrayList<Occurrence>>(1000,2.0f);
		noiseWords = new HashSet<String>(100,2.0f);
	}

	/**
	 * Scans a document, and loads all keywords found into a hash table of keyword occurrences
	 * in the document. Uses the getKeyWord method to separate keywords from other words.
	 *
	 * @param docFile Name of the document file to be scanned and loaded
	 * @return Hash table of keywords in the given document, each associated with an Occurrence object
	 * @throws FileNotFoundException If the document file is not found on disk
	 */
	public HashMap<String,Occurrence> loadKeywordsFromDocument(String docFile)
			throws FileNotFoundException {
		/** COMPLETE THIS METHOD **/

		// following line is a placeholder to make the program compile
		// you should modify it as needed when you write your code



		//this will check if the file even exists
		if(docFile==null){
			return null;
		}

		Scanner file = new Scanner(new File(docFile));

		HashMap<String,Occurrence> keys = new HashMap<String,Occurrence>();

		while(file.hasNext()){
			String word = getKeyword(file.next());
			//if the word is null go to next line
			if(word==null){
				continue;
			}
			/**
			 * now i will have to input the words into the hasmap
			 * i will need to determine the occurrences of the word
			 * so i will need an if statement if it appears more than once
			 */
			if(keys.containsKey(word)){

				//since the hashmap contains the key we make an occurrence object to get the key and reference the words properties

				Occurrence number = keys.get(word);

				//change the frequency of the word by 1
				number.frequency++;
				

			}

			else{
				//if the word isnt in the map set its occurrence to 1
				Occurrence first = new Occurrence(docFile,1);
				//put the word and frequency into the hashmap
				keys.put(word,first);
			}

			





		}



		return keys;
	}

	/**
	 * Merges the keywords for a single document into the master keywordsIndex
	 * hash table. For each keyword, its Occurrence in the current document
	 * must be inserted in the correct place (according to descending order of
	 * frequency) in the same keyword's Occurrence list in the master hash table.
	 * This is done by calling the insertLastOccurrence method.
	 *
	 * @param kws Keywords hash table for a document
	 */
	public void mergeKeywords(HashMap<String,Occurrence> kws) {
		/** COMPLETE THIS METHOD **/


		Set<String> keys = kws.keySet();  // Set is of type String since keys are Strings
		Iterator<String> iterator = keys.iterator();
		
		while (iterator.hasNext()) {
			
			String key=iterator.next();


			ArrayList<Occurrence> occurrence = new ArrayList<Occurrence>();

			/**
			 * if the key is found in teh keywords index then have the
			 * occurrence arraylist point to the array list of the corresponding key.
			 */ 
			if(keywordsIndex.containsKey(key)){
				occurrence= keywordsIndex.get(key);
			}



			occurrence.add(kws.get(key));//add the key to the corresponding key in kwords index
			insertLastOccurrence(occurrence);//this will organize the freq in descending order of frequency
			keywordsIndex.put(key,occurrence);//put the key word into the master hashmap
			



		}



	}

	/**
	 * Given a word, returns it as a keyword if it passes the keyword test,
	 * otherwise returns null. A keyword is any word that, after being stripped of any
	 * trailing punctuation(s), consists only of alphabetic letters, and is not
	 * a noise word. All words are treated in a case-INsensitive manner.
	 *
	 * Punctuation characters are the following: '.', ',', '?', ':', ';' and '!'
	 * NO OTHER CHARACTER SHOULD COUNT AS PUNCTUATION
	 *
	 * If a word has multiple trailing punctuation characters, they must all be stripped
	 * So "word!!" will become "word", and "word?!?!" will also become "word"
	 *
	 * See assignment description for examples
	 *
	 * @param word Candidate word
	 * @return Keyword (word without trailing punctuation, LOWER CASE)
	 */
	public String getKeyword(String word) {
		/** COMPLETE THIS METHOD **/




		/**
		 * if the word is null or is less than or equal to 0
		 */
		if(word.equals(null) || word.length()<=0){
			return null;
		}


		String currentword =word.toLowerCase();//since the word needed to be returned is lowercase make it lowercase


		/**
		 * now the idea is to take out all the punctuation of the word
		 * so to do this put the word into a char array and then remake a new string without punctuation
		 */

		char[] wordchar =currentword.toCharArray();

		/**
		 * need to check if the word contains numbers if it does return null
		 *
		 */

		for(int i=0;i<wordchar.length;i++){

			//so if the word has a number return null
			if(Character.isDigit(wordchar[i])){
				return null;
			}
			if(!Character.isLetter(wordchar[i])){
				return null;
			}
//			if(!Character.isWhitespace(wordchar[i])){
//				return null;
//			}
		}


		//if the word lowercased contains keyword return null;
		if(noiseWords.contains(currentword)){
			return null;
		}



		StringBuilder curr = new StringBuilder(currentword);

		for(int i =0; i<curr.length();i++){

			//if the word contains and punc then delete it from the string with string builder

			if(wordchar[i]=='.'||wordchar[i]==','||wordchar[i]=='?'||wordchar[i]==':'||wordchar[i]=='!'||wordchar[i]==';'){
				//deleting the punctuation from the string
				curr.deleteCharAt(i);

			}


		}
		//if the trailing punctuation is taken out and it is a null word then return null
		if(noiseWords.contains(curr)){
			return null;
		}

		currentword=  curr.toString();

		//if the word is a keyowrd than return it
		return currentword;
	}

	/**
	 * Inserts the last occurrence in the parameter list in the correct position in the
	 * list, based on ordering occurrences on descending frequencies. The elements
	 * 0..n-2 in the list are already in the correct order. Insertion is done by
	 * first finding the correct spot using binary search, then inserting at that spot.
	 *
	 * @param occs List of Occurrences
	 * @return Sequence of mid point indexes in the input list checked by the binary search process,
	 *         null if the size of the input list is 1. This returned array list is only used to test
	 *         your code - it is not used elsewhere in the program.
	 */
	public ArrayList<Integer> insertLastOccurrence(ArrayList<Occurrence> occs) {
		/** COMPLETE THIS METHOD **/

		/**
		 * this is if the list is just one number
		 */
		if(occs.size()<2){
			return null;
		}

		ArrayList<Integer> midpoints = new ArrayList<Integer>();

		ArrayList<Integer> freq= new ArrayList<Integer>();//this will hold the frequencies

		/**
		 * storing all frequencies of the occurrences besides the last one to the arrayt list
		 */

		for(int i =0;i<occs.size()-1;i++){
			freq.add(occs.get(i).frequency);
		}
		int target= occs.get(occs.size()-1).frequency;//get the target frequency

		int low= 0;
		int high = freq.size()-1;

		midpoints= BinarySearch(low,high,target,freq);//this will do a binary search on the list of freq look for the last occurrence and get the mid points

		/**
		 * now i need to  rearrange the order of occs since i got the mid points from binary search
		 */

		Occurrence tmp = occs.get(occs.size()-1);//store the last occ into an object tmp
		occs.remove(occs.size()-1);//remove the occurrence
		//since we now have the last occurrence and the correct mid point we can place it  into the correct spot on the list

		occs.add(midpoints.get(midpoints.size()-1), tmp);

		return midpoints;





	}


	/**
	 * method for binary search
	 * @return
	 */
	private static ArrayList<Integer> BinarySearch(int low,int high, int target, ArrayList<Integer> freq){
		//list of mids that will be returned
		ArrayList<Integer> midpoint = new ArrayList<Integer>();
		if(freq.isEmpty()){
			return null;
		}



		while(low<= high){
			int mid = (low+high)/2;

			midpoint.add(mid);


			//if the target is found in the freq array list then break
			if(target == freq.get(mid)){

				break;
			}

			if(target<freq.get(mid)){
				high= mid-1;
			}
			else{
				low= mid+1;
			}





		}




		return midpoint;
	}

	/**
	 * This method indexes all keywords found in all the input documents. When this
	 * method is done, the keywordsIndex hash table will be filled with all keywords,
	 * each of which is associated with an array list of Occurrence objects, arranged
	 * in decreasing frequencies of occurrence.
	 *
	 * @param docsFile Name of file that has a list of all the document file names, one name per line
	 * @param noiseWordsFile Name of file that has a list of noise words, one noise word per line
	 * @throws FileNotFoundException If there is a problem locating any of the input files on disk
	 */
	public void makeIndex(String docsFile, String noiseWordsFile)
			throws FileNotFoundException {
		// load noise words to hash table
		Scanner sc = new Scanner(new File(noiseWordsFile));
		while (sc.hasNext()) {
			String word = sc.next();
			noiseWords.add(word);
		}

		// index all keywords
		sc = new Scanner(new File(docsFile));
		while (sc.hasNext()) {
			String docFile = sc.next();
			HashMap<String,Occurrence> kws = loadKeywordsFromDocument(docFile);
			mergeKeywords(kws);
		}
		sc.close();
	}

	/**
	 * Search result for "kw1 or kw2". A document is in the result set if kw1 or kw2 occurs in that
	 * document. Result set is arranged in descending order of document frequencies.
	 *
	 * Note that a matching document will only appear once in the result.
	 *
	 * Ties in frequency values are broken in favor of the first keyword.
	 * That is, if kw1 is in doc1 with frequency f1, and kw2 is in doc2 also with the same
	 * frequency f1, then doc1 will take precedence over doc2 in the result.
	 *
	 * The result set is limited to 5 entries. If there are no matches at all, result is null.
	 *
	 * See assignment description for examples
	 *
	 * @param kw1 First keyword
	 * @param kw1 Second keyword
	 * @return List of documents in which either kw1 or kw2 occurs, arranged in descending order of
	 *         frequencies. The result size is limited to 5 documents. If there are no matches,
	 *         returns null or empty array list.
	 */
	public ArrayList<String> top5search(String kw1, String kw2) {
		/** COMPLETE THIS METHOD **/

		// following line is a placeholder to make the program compile
		// you should modify it as needed when you write your code
		
		if(kw1==null & kw2==null) {
			return null;
			
		}
		
		if(!keywordsIndex.containsKey(kw1)|| !keywordsIndex.containsKey(kw2)) {
			return null;
		}
		
		
		
		ArrayList<String> top = new ArrayList<String>();
		ArrayList<String> docs = new ArrayList<String>();
		ArrayList<Integer> freq = new ArrayList<Integer>();
		
	
		ArrayList<Occurrence> keyword1 = keywordsIndex.get(kw1);
		
		ArrayList<Occurrence> keyword2 = keywordsIndex.get(kw2);
		
	
		
		
		/**
		 * this gets all the frequencies of the documents
		 * and adds it to an array lisat
		 */
		for(int i=0;i<keyword1.size();i++) {
			
			freq.add(keyword1.get(i).frequency);//will store the frequencys of every document that has the key in this array
			docs.add(keyword1.get(i).document);// this stores all the docs in keyword 1
		
		}
		
		
		
		

for(int i=0;i<keyword2.size();i++) {
		
			if(	docs.contains(keyword2.get(i).document)) {
				continue;
			}
			else {
				docs.add(keyword2.get(i).document);
				freq.add(keyword2.get(i).frequency);
			}
			
		
		}

for(int i=0;i<keyword2.size();i++) {
	
	for(int k=0;k<docs.size();k++) {
		
		if(keyword2.get(i).document.equals(docs.get(k))) {//if document name of the keyword 2 find the position of that document in docs.
			
			//given the position in docs add the frequency from freq arrayt list
			
			freq.set(k, freq.get(k)+keyword2.get(i).frequency);
			
			
			
			
		}
	}
	

}

ArrayList<Integer> freqorder = new ArrayList<Integer>(freq);

Collections.sort(freqorder, Collections.reverseOrder());//descending order freq now match frequencies

/**
 * match freq to names and print list
 * 
 */
int count=0;

for(int i=0;i<freqorder.size();i++) {
	if(count==5) {
		break;
	}
	
	for(int k=0;k<freq.size();k++) {
		if(freqorder.get(i)== freq.get(k)) {
			top.add(docs.get(k));
			count++;
			freq.set(i, 0);
			
			break;
		}
	}
	
	
}



		
		
		
		
		
		
		
		
		
		
		return top;

	}



}