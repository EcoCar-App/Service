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

	public Node(){
		this(new byte [] {0}, (byte)0, "unknown", null);		
	}

	public Node(byte [] path, byte id, Node previous){
		this(path, id, "unknown_component", previous);		
	}

	public Node(byte [] path, byte id, String name, Node previous){
		this.id = id;
		this.path = path;
		this.previous = previous;
		this.setName(name);
		this.values = new Value();
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

	public Node getPrevious(){
		return this.previous;
	}

	private Node getNextDirectory(){
		return this.nextDirectory;
	}

	public Node getSameDirectory(){
		return this.sameDirectory;
	}

	public Node getNextNode(byte id){
		Node current = this.getNextDirectory();

		while(current != null){
			if(current.id == id){
				return current;
			}
			current = current.sameDirectory;
		}

		//throw NodeNotFoundException
		return current;
	}

	public Node getEmptyNode(){
		Node current = this.nextDirectory;
		while(current != null){
			current = current.sameDirectory;
		}
		return current;
	}

	public Value getValues(){
		return this.values;
	}

	public Value getEmptyValue(){

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




	/*++++++++++++++++++++++++++++++SET-METHODEN+++++++++++++++++++++++++++++*/

	private void setName(String name){
		int counter = 2;
		while(this.previous != null && previous.existName(name)){
			name += counter;
			counter++;
		}
		this.name = name;
	}

	private boolean existName(String name){
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

	public void add(byte [] path, byte id, String name){	
		Node newNode = this.getEmptyNode();

		newNode = new Node(path, id, name, this);

		if(root == null){
			root = newNode;
		}
	}

	public void addValue(byte type, byte id, String name){
		values.add(type, id, name, this);
	}

	public void newValue(Value value){
		//muss eleganter geloest werden!
		if(this.values.getValue() == null){
			this.values = value;
			System.out.println("true");
		}else{
			values.getLast().setNext(value);
		}	}


	/*+++++++++++++++REFRESH_METHODEN+++++++++++++++++*/

	public Vector<Byte> refresh(){
		Value currentValue = values;

		Vector <Byte> commandSet = new Vector <Byte>();

		while(currentValue != null){
			commandSet.addAll(refresh(currentValue));
			currentValue = currentValue.getNext();
		}
		commandSet.addAll(refresh(this));
		return commandSet;
	}

	private Vector <Byte> refresh(Value value){
		return value.refresh();
	}

	private Vector <Byte> refresh(Node node){
		Node current = node.nextDirectory;
		Vector <Byte> commandLine = new Vector <Byte> ();
		while(current != null){
			commandLine.addAll(current.refresh());
			current = current.sameDirectory;
		}
		return commandLine;
	}
}
