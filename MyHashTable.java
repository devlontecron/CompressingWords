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



public class MyHashTable<K, V> {
	List<Node> myTable = new ArrayList<Node>();
	List<Integer> Probs = new ArrayList<Integer>();
	double tableCapacity;
	double entCount = 0;
	
	private class Node{
		
		K K;
		V V;
		
		public Node (K Key, V Value){
			K = Key;
			V = Value;
		}
	}
	
	MyHashTable (int capacity){	
		myTable = new ArrayList<Node>(capacity);
		tableCapacity = capacity;
		for(int c = 0; c<capacity; c++){
			myTable.add(null);
		}
		Probs.add(0);
		
	}
		
	void put(K searchKey, V newValue){	
		int intKey = hash(searchKey);
		int probCount=0;
		if(containsKey(searchKey)){
			while( !myTable.get(intKey).K.equals(searchKey)){
				intKey++;
				intKey = intKey % myTable.size();
			}
			myTable.get(intKey).V = newValue;
			
		} else{

		entCount++;
		if(myTable.get(intKey)==(null)){
			Node newNode = new Node(searchKey,newValue);
			myTable.set(intKey, newNode);
			Probs.set(0, Probs.get(0)+1);
		} else{
			
			while(myTable.get(intKey)!=null){
				intKey++;
				intKey = intKey % myTable.size();
				probCount++;
			}
			Node newNode = new Node(searchKey,newValue);
			myTable.set(intKey, newNode);
		}
		
		while(Probs.size()<=probCount){
			Probs.add(0);
		}
		Probs.set(probCount, Probs.get(probCount)+1);
		}
		
	}
	
	V get(K searchKey){
		int intKey = hash(searchKey);
		V tempV = null;
		boolean hasKey = false;
		
		if(myTable.get(intKey)== null){
			System.out.println("ERROR: Value not found");
		}else {
			while(myTable.get(intKey)!= null && hasKey == false){
				if(myTable.get(intKey).K.equals(searchKey)){
					tempV = myTable.get(intKey).V;
					hasKey =true;
				}else{
					intKey++;
					intKey = intKey% myTable.size();
			}
		}
		}
		return tempV;
	}
	
	boolean containsKey(K searchKey){

		boolean hasKey = false;
		int intKey = hash(searchKey);
		if(myTable.get(intKey)== null){
			hasKey=false;
		}else {
			while(myTable.get(intKey)!= null && hasKey == false){

				if(myTable.get(intKey).K.equals(searchKey)){

					hasKey = true;
				}else{

					intKey++;
					intKey = intKey % myTable.size();
			}
		}
		}
		return hasKey;
	}
	
	public void stats(){

		System.out.print("Hash Table Stats \n================ \n");
		System.out.println("Number of Entries: " + entCount);
		System.out.println("Number of Buckets: " + tableCapacity);
//make hash table.		
		
		System.out.println("Histogram of Probes: " +Probs.toString());
		
		double fill = (entCount/tableCapacity)*100;
		System.out.println("Fill Percentage: " + fill);
		
		System.out.println("Max Linear Prob: " + Probs.size());
		int totalProbs = 0;
		double avg = 0;
		for(int x = 0; x<Probs.size(); x++){
			totalProbs = totalProbs + Probs.get(x);
		}
		avg = totalProbs/Probs.size();
		System.out.println("Avg Linear Prob: " + avg);
	
	}
	
	private int hash(K key){

		int myHashCode = Math.abs(key.hashCode()) % myTable.size(); 		

	return myHashCode;
	}

	public String toString(){
		StringBuilder sb = new StringBuilder();
		for(int t = 0; t<myTable.size(); t++){
			if(myTable.get(t)!= null){
				sb.append(myTable.get(t).K);
				sb.append('=');
				sb.append(myTable.get(t).V);
				sb.append(' ');
			}
		}
		return sb.toString();
	}

}
