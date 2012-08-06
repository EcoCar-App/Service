package com.example.communicationservice;
import java.util.*;

public class Node {
	
	private byte [] path;			//Pfad im Baum

	private byte id;				//Zugehoerige Byte der Komponente

	private String name;			//Name der Fahrzeugkomponente

	private Node previous;			//Zeiger auf den vorherigen Knoten

	private Node nextDirectory;		//Zeiger auf Knoten der naechsten Ebene

	private Node sameDirectory;		//Zeiger auf Knoten der selben Ebene

	private Value values;			//Zeiger auf Liste an Values
									//evtl auch als Hashtable moeglich: unklar
	private static Node root;		//Wurzel des Datenbaums


	/*+++++++++++++++++++++++++++++KONSTRUKTOREN+++++++++++++++++++++++++++++*/

	//Standardkonstruktor
	//wird in der regel nicht aufgerufen
	public Node(){
		this(new byte [] {0}, (byte)0, "unknown", null);		
	}

	//Konstruktor Name unbekannt
	//im Falle das der Pfad in der Hashtable fehlt
	public Node(byte [] path, byte id, Node previous){
		this(path, id, "unknown_component", previous);		
	}

	//Konstruktor Name bekannt, Previous wird mituebergeben
	public Node(byte [] path, byte id, String name, Node previous){
		this.id = id;
		this.path = path;
		this.previous = previous;
		this.setName(name);
	}


	/*++++++++++++++++++++++++++++++GET-METHODEN+++++++++++++++++++++++++++++*/

	public byte [] getPath(){
		return this.path.clone();
	}
	
	public byte getID(){
		return this.id;
	}
	
	public String getName(){
		return this.name;
	}


	//gibt die Liste der Kindknoten zurueck
	private Node getChildList(){
		return this.nextDirectory;
	}

	//gibt die Liste der Knoten mit gleichem Vaterknoten zurueck
	public Node getNodeList(){
		return this.sameDirectory;
	}

	//gibt die naechsten freien Kindknoten zurueck
	public Node getEmptyChildNode(){
		Node current = this.nextDirectory;
		while(current != null){
			current = current.sameDirectory;
		}
		return current;
	}

	//gibt den Vaterknoten zurueck
	public Node getPrevious(){
		return this.previous;
	}

	//gibt die Liste der Werte zurueck
	public Value getValues(){
		return this.values;
	}

	public Value getLastValue(){
		
		return values.getLast();
	}
	
	public Value getValue(byte id){
		Value value = this.values;
		while(value.getID() != id && value != null){
			value = value.getNext();
		}
		if(value == null){
			//throw Exception
		}
		
		return value;
	}
	

	/**sucht den passenden Kindknoten anhand der ID*/
	public Node getNextNode(byte id){
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
	
	/*++++++++++++++++++++++++++++++SET-METHODEN+++++++++++++++++++++++++++++*/

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
	
	public void setValue(byte id, byte [] input){
		this.getValue(id).setValue(input);
	}


	/*++++++++++++++++++++++++++++++ADD-METHODEN+++++++++++++++++++++++++++++*/

	//fuegt einen Kindknoten hinzu
	public void add(byte [] path, byte id, String name){	
		Node newNode = this.getEmptyChildNode();
		newNode = new Node(path, id, name, this);
	}

	public void addValue(byte type, byte id, String name){
		Value newValue = getLastValue();
		newValue.add(type, id, name, this);
	}
	

	/*+++++++++++++++REFRESH_METHODEN+++++++++++++++++*/

	//TODO: saemtliche Values des aktuellen und der UnterKnoten werden rekursiv aktuallisiert
	//Das Feld mit pfad und Befehl wird zurückgegeben
	public Vector refresh(){
		Value currentValue = values;
		
		Vector commandSet = new Vector();
		
		while(currentValue != null){
			commandSet.add(refresh(currentValue));
			currentValue = currentValue.getNext();
		}
		commandSet.add(refresh(this));
		return null;
	}
	
	private Vector refresh(Value value){
		return value.refresh();
	}

	private Vector refresh(Node node){
		Node current = node.nextDirectory;
		Vector commandLine = new Vector();
		while(current != null){
			commandLine.add(current.refresh());
			current = current.sameDirectory;
		}
		return commandLine;
	}
}
