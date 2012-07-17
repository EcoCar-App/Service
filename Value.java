package com.example.communicationservice;
import java.util.*;

public class Value{

	private byte id;			//ID des Attributs
	private String name;		//Name des Attributs
	private Date time;			//mšglicher Zeitstempel
	private Object value;		//Object in dem der entpr. Datentyp gespeichert wird
	private Value next;			//Zeiger auf den naechsten Wert
	private Node father;		//Zeiger auf Vaterknoten

	
	/*+++++++++++++++Konstruktoren++++++++++++++++++++++*/

	public Value(){

	}
	public Value(byte id, String name, Object value){
		this.id = id;
		this.name = name;
		this.value = value;
	}

	public Value(byte id, String name, Object value, Date zeitstempel){
		this(id, name, value);
		this.time = zeitstempel;

	}

	/*+++++++++++++++GET_METHODEN++++++++++++++++++++++*/

	public byte getID(){
		return this.id;
	}
	
	public String getName(){
		return this.name;
	}
	
	public Date getTime(){
		return this.time;
	}
	
	public Object getValue(){
		return this.value;
	}
	
	public Value getNext(){
		return this.next;
	}
	
	public Node getFather(){
		return this.father;
	}
	
	
	/*+++++++++++++++SEARCH_METHODEN+++++++++++++++*/

	private Value searchValue(byte id){
		return null;
	}

	private Value lastPointer(Value values){
		return null;
	}
	
	private Value getLastValue() {
		return null;
	}

	
	/*+++++++++++++++++REFRESH+++++++++++++++++++*/

	
	//erzeugt das GET-Protokoll fuer ein Object Value
	public byte [] refresh(){
		
		byte [] path = father.getPath();
		byte depth = (byte) path.length;
		byte command = (byte) 2; //muss noch ermittelt werden
		byte length = (byte) (depth + (byte) 4);
		byte [] commandSet = new byte [length];
		
		for(int i = 0; i < commandSet.length; i++){
			switch(i){
			case 0: 
				commandSet[i] = length;
			case 1: 
				commandSet[i] = command;
			case 2: 
				commandSet[i] = depth;
			default: 
				if(i != length-1){
					commandSet[i] = path[i- 3];
				}else{
					commandSet[i] = this.id;
				}
				
			}
		}
		return commandSet.clone();
	}

	
	/*+++++++++++++++ADD_METHODEN++++++++++++++++++++++*/

	private void add(byte type, byte[] path, byte id) {

		switch(type){
		
		}
	}
	//fuer jeden Datentyp wird die enspr. Methode aufgerufen
	//bisher nur primitve Datentypen
	private void addBoolean(byte id, String name) {
		this.next = new Value(id, name, new Boolean(null));

	}
	private void addByte(byte id, String name) {
		this.next = new Value(id, name, new Byte(null));
	}
	
	private void addChar(byte id, String name){
		char value = 0;
		this.next = new Value(id, name, value);
	}
	
	private void addDouble(byte id, String name){
		this.next = new Value(id, name, new Double(null));
	}
	
	private void addFloat(byte id, String name){
		this.next = new Value(id, name, new Float(null));
	}
	
	private void addInteger(byte id, String name){
		this.next = new Value(id, name, new Integer(null));
	}

	private void addLong(byte id, String name){
		this.next = new Value(id, name, new Long(null));
	}
	private void addShort(byte id, String name){
		this.next = new Value(id, name, new Short(null));
	}

}
