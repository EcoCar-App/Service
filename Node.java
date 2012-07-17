package com.example.communicationservice;
import java.util.*;

public class Node {

	private String name;			//Name der Fahrzeugkomponente
	
	private byte id;			//Zugehoerige Byte der Komponente
	
	private byte [] path;			//Pfad im Baum
	
	private Node previous;			//Zeiger auf den vorherigen Knoten
						//nicht zwingend notwendig: unklar
	private Node nextDirectory;		//Zeiger auf Knoten der naechsten Ebene
	
	private Node sameDirectory;		//Zeiger auf Knoten der selben Ebene
	
	private Value values;			//Zeiger auf Liste an Values
						//evtl auch als Hashtable mšglich: unklar
	
        private static int counter = 1;	        //Counter Namensgebung z.B. Akuzelle10
	
	
	/*+++++++++++++++Konstruktoren++++++++++++++++++++++*/
	
	//Standardkonstruktor
	//wird in der regel nicht aufgerufen
	public Node(){
		this((byte)0, new byte [] {0}, "unknown");		
	}
	
	//Konstruktor Name unbekannt
	//im Falle das der Pfad in der Hashtable fehlt
	public Node(byte id, byte [] path){
		this.id = id;
		this.path = path;
		Node previous = this.getPrevious();
		String name = "unknown_component_of_" + previous.name +counter;
		
	}
	
	//Konstruktor Name bekannt
	public Node(byte id, byte [] path, String name){
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
	
	/*+++++++++++++++SET_METHODEN++++++++++++++++++++++*/

	//setzt Namen des Attributs inkl. einer Nummerierung bei Doppelbelegung
	private void setName(String name){
		int counter = 2;
		while(previous.nameExist(name)){
			name += counter;
			counter++;
		}
		this.name = name;
		
	}
	
	//ueberprueft, ob der Name in der Directory schon verhanden ist
	private boolean nameExist(String name){
		Node current = previous.nextDirectory;
		
		while(current.sameDirectory != null){
			if(name.equals(current.name)){
				return true;
			}
			current = current.sameDirectory;
		}
		return false;
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
