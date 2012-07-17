package com.example.communicationservice;
import java.util.*;
public class Node {
	
	
	
	private String name;
	private byte id;
	private byte [] path;
	private static int counter = 1;
	private Node previous;
	private Node nextDirectory;	//Zeiger auf Knoten der nächsten Ebene
	private Node sameDirectory;	//Zeiger auf Knoten der selben Ebene
	//evtl auch Hashtable
	private Value values;	//Zeiger auf Values
	
	
	/*+++++++++++++++Konstruktoren++++++++++++++++++++++*/
	//Standardkonstruktor
	public Node(){
		this.id = 0;
		this.name = "head";
		this.path = null;
		
	}
	
	//Konstruktor Name unbekannt
	public Node(byte id, byte [] path, String name){
		this.id = id;
		this.path = path;
		Node previous = this.getPrevious();
		this.name = "unknown_component_of_" + previous.name +counter;
		counter++;
	}
	
	//Konstruktor Name bekannt
	public Node(byte id, byte depth, byte [] path, String name){
		this.id = id;
		this.path = path;
	}
	
	
	/*+++++++++++++++GET_METHODEN++++++++++++++++++++++*/
	
	private String getName(){
		return this.name;
	}
	
	private byte getID(){
		return this.id;
	}
	
	private byte [] getPath(){
		return this.path;
	}
	
	private Node getNextNodes(){
		return this.nextDirectory;
	}
	
	private Node getSameLevel(){
		return this.sameDirectory;
	}
	
	private Node getPrevious(){
		return this.previous;
	}
	
	private Value getValues(){
		return this.values;
	}
	
	
	/*+++++++++++++++ADD_METHODE++++++++++++++++++++++*/
	
	private void add(byte [] path, byte id){	
		
	}
	
	
	/*+++++++++++++++REMOVE_METHODE++++++++++++++++++++++*/
	private void remove(){
		
	}
	
	/*+++++++++++++++PREVIOUS_METHODEN+++++++++++++*/
	
	private Node getPreviousNode(){
		return this.previous;
	}
	
	private Node lastPointer(){
		Node current = this.sameDirectory;
		
		while(current != null){
			current = current.sameDirectory;
		}
		return current;
	}
	
	
	
	/*+++++++++++++++SEARCH_METHODEN+++++++++++++++++*/
	
	private Node getNextNode(byte id){
		Node current = this.nextDirectory;
		
		 while(current != null){
			 if(current.id == id){
				 return current;
			 }
			 current = current.sameDirectory;
		 }
		 
		 //throw NodeNotFoundException
		 return current;
	}
	
	
	/*+++++++++++++++REFRESH_METHODEN+++++++++++++++++*/
	
	//saemtliche Values des aktuellen und der UnterKnoten werden rekursiv aktuallisiert
	private byte [] refresh(){
		return null;
	}
	

}
