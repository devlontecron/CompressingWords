/*
 * TCSS 342 - Spring 2016
 * Assignment 5 - MazeGenerator
 */

/**
 * Main driver Class.
 * 
 * @author Samantha Ong - sjfoh and Devin Durham - d1duram
 * @version 3 May 2016
 *
 */

import java.util.ArrayList;
import java.util.List;


public class CodingTree {
	
	StringBuilder sb = new StringBuilder();
	List<Node> ListWords = new ArrayList<Node>();
	List<String> keys = new ArrayList<String>();
	MyHashTable<String, Integer> HashwordFreq = new MyHashTable<String, Integer>(32768);
	MyHashTable<String, String> codes = new MyHashTable<String, String>(32768);
	String bits;
	
	private class Node{
		
		String Item;
		int freq;
		Node left;
		Node Right;
		
		public Node (String theItem, int theFreq){
			Item = theItem;
			freq = theFreq;
		}
		public Node (int theFreq){	
			freq = theFreq;
		}
		
		public Node(){	
		}

	}
	
	
	CodingTree(String fullText){
	
		readWordFreqs(fullText);
		
		covertMap();

		makeTree();

		readTree(ListWords.get(0));

		toBin(fullText);

		HashwordFreq.stats();

		

	}

	private void covertMap() {
		for(int x = 0; x<keys.size(); x++){
			String tempS = keys.get(x);
			int tempI = HashwordFreq.get(tempS);
			Node temp = new Node(tempS,tempI);
			ListWords.add(temp);
		}
	}

	private void readWordFreqs(String inString){
		int x = 0;
		while(x<inString.length()-1){
			
			char curr = inString.charAt(x);
			StringBuilder sb = new StringBuilder();
	
			if((curr >= '0' && curr <= '9') || (curr >= 'a' && curr <='z') || 
					(curr >= 'A' && curr <='Z') || curr == '-' || curr == '\''){

				while(x<inString.length() && ((curr >= '0' && curr <= '9') || (curr >= 'a' && curr <='z') || 
						(curr >= 'A' && curr <='Z') || curr == '-' || curr == '\'')){
					sb.append(curr);
					x++;
					if(x<inString.length()){
						curr = inString.charAt(x);
					} else{
	
					}
					
				}
			} else{
				sb.append(curr);
				x++;
			}
			//System.out.println("STILL IN TABLE: " + HashwordFreq.containsKey("Author"));
			
			//System.out.println(sb.toString());
			
			//System.out.println(sb.toString());
			
			Boolean isMatch = false;
			//System.out.println("94");
			isMatch = HashwordFreq.containsKey(sb.toString());
			//System.out.println("96");
			//System.out.println(isMatch);
			if(isMatch.equals(false)){

				HashwordFreq.put(sb.toString(), 1);

				keys.add(sb.toString());
				//System.out.println(1);
			} else{

				int temp = HashwordFreq.get(sb.toString());
				temp++;
				//System.out.println(temp);

				HashwordFreq.put(sb.toString(), temp);

			}
			
			
		}
		
		//System.out.println("stuck");
	}
	
	private void makeTree() {
		while (ListWords.size()-1  > 0) {


			Node min1 = new Node();
			Node min2 = new Node();

			min1 = ListWords.get(0);
			for (int y = 0; y < ListWords.size(); y++) {
				if (min1.freq > ListWords.get(y).freq) {
					min1 = ListWords.get(y);
				}
			}
			ListWords.remove(min1);
			
			if (ListWords.size() > 0) {
				min2 = ListWords.get(0);
				for (int y = 0; y < ListWords.size(); y++) {
					if (min2.freq > ListWords.get(y).freq) {
						min2 = ListWords.get(y);
					}
				}
				ListWords.remove(min2);
			}
			
			int par = min1.freq + min2.freq;
			//System.out.println(min1.Item);
			//System.out.println(min2.Item);
			//System.out.println(par);
			Node parNode = new Node(par);
			parNode.left = min1;
			parNode.Right = min2;

			ListWords.add(parNode);
		}
	}
	
	private void readTree(Node root) {
		StringBuilder sbCodes = new StringBuilder();

		if (root.left != null) {
			sb.append("0");
			readTree(root.left);
		}
		if (root.Right != null) {
			sb.append("1");
			readTree(root.Right);
		}
		if (root.left == null && root.Right == null) {
			//System.out.println(root.Item + " " + sb.toString());
			codes.put(root.Item, sb.toString());
			//sbCodes.append(root.Item + "=" + sb.toString() + " \n");
		}
		if (sb.length() != 0) {
			sb.deleteCharAt(sb.length() - 1);
		}
	}
	
	private void toBin(String message) {
		StringBuilder outBin = new StringBuilder();
		int x = 0;
		while(x<message.length()-1){
			char curr = message.charAt(x);
			StringBuilder sb = new StringBuilder();
			
			if((curr >= '0' && curr <= '9') || (curr >= 'a' && curr <='z') || 
					(curr >= 'A' && curr <='Z') || curr == '-' || curr == '\''){

				while(x<message.length() && ((curr >= '0' && curr <= '9') || (curr >= 'a' && curr <='z') || 
						(curr >= 'A' && curr <='Z') || curr == '-' || curr == '\'')){
					sb.append(curr);
					x++;
					if(x<message.length()){
						curr = message.charAt(x);
					} else{
	
					}
					
				}
			} else{
				sb.append(curr);
				x++;
			}
			
			outBin.append(codes.get(sb.toString()));
		}
		bits = outBin.toString();	

	}

}
