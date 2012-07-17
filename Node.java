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

	private Value values = new Value();			//Zeiger auf Liste an Values
	//evtl auch als Hashtable moeglich: unklar


	/*+++++++++++++++Konstruktoren++++++++++++++++++++++*/

	//Standardkonstruktor
	//wird in der regel nicht aufgerufen
	public Node(){
		this((byte)0, new byte [] {0}, "unknown", null);		
	}

	//Konstruktor Name unbekannt
	//im Falle das der Pfad in der Hashtable fehlt
	public Node(byte id, byte [] path, Node previous){
		this(id, path, "unknown_component", previous);		
	}

	//Konstruktor Name bekannt, Previous wird mituebergeben
	public Node(byte id, byte [] path, String name, Node previous){
		this.id = id;
		this.path = path;
		this.previous = previous;
		this.setName(name);
	}


	/*+++++++++++++++GET_METHODEN++++++++++++++++++++++*/

	private String getName(){
		return this.name;
	}

	private byte getID(){
		return this.id;
	}

	public byte [] getPath(){
		return this.path.clone();
	}


	//gibt die Liste der Kindknoten zurueck
	private Node getChildList(){
		return this.nextDirectory;
	}

	//gibt die Liste der Knoten mit gleichem Vaterknoten zurueck
	private Node getNodeList(){
		return this.sameDirectory;
	}

	//gibt die naechsten freien Kindknoten zurueck
	private Node getFreeChildNode(){
		Node current = this.nextDirectory;
		while(current != null){
			current = current.sameDirectory;
		}
		return current;
	}

	//gibt den Vaterknoten zurueck
	private Node getPrevious(){
		return this.previous;
	}

	//gibt die Liste der Werte zurueck
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

	//ueberprueft, ob der Name in der Directory schon vorhanden ist
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

	//fuegt einen Kindknoten hinzu
	private void add(byte [] path, byte id, String name){	
		Node current = this.getFreeChildNode();
		current = new Node(id, path, name, this);
	}


	/*+++++++++++++++REMOVE_METHODE++++++++++++++++++++++*/

	//entfernt den Knoten und alle Kondknoten (nicht notwendig)
	private void remove(){
		this.previous = null;
	}

	/*+++++++++++++++PREVIOUS_METHODEN+++++++++++++*/






	/*+++++++++++++++SEARCH_METHODEN+++++++++++++++++*/

	//sucht den passenden Kindknoten anhand der ID
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

	//TODO: saemtliche Values des aktuellen und der UnterKnoten werden rekursiv aktuallisiert
	//Das Feld mit pfad und Befehl wird zurückgegeben
	private byte [] refresh(){
		return null;
	}

	private byte [] refreshValues(){
		Value current = this.values;
		
		while(current != null){
			byte [] commandField = current.refresh();

		}
		
		return null;

	}

}
