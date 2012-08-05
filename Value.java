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
	
	//sucht in der Liste das passende Object zur ID
	private Value searchValue(byte id){
		Value current = this;
		while(current != null){
			if(current.id == id){
				return current;
			}
			current = current.next;
		}
		//noValueException
		return current;
	}
	
	//sucht den letzten Zeiger in der Liste
	private Value lastPointer(){
		Value current = this;
		while(current != null){
			current = current.next;
		}
		return current;
	}
	

	
	/*+++++++++++++++++REFRESH+++++++++++++++++++*/

	
	//erzeugt das GET-Protokoll fuer ein Object Value
	public Vector refresh(){
		
		byte [] path = father.getPath();
		byte depth = (byte) path.length;
		byte command = (byte) 2; //muss noch ermittelt werden
		byte length = (byte) (depth + (byte) 4);
		
		Vector commandLine = new Vector();
		commandLine.add(length);
		commandLine.add(command);
		commandLine.add(depth);
		
		for(int i = 0; i < path.length; i++){
			commandLine.add(path[i]);
		}
		commandLine.add(this.id);
		
		return commandLine;
	}

	
	/*+++++++++++++++ADD_METHODEN++++++++++++++++++++++*/

	//Oberste Add-Methode: ruft die entspr. Methode auf
	public void add(byte type, byte id, String name) {
		if(!(this.next == null && this.father != null)){
			//throw Exception
		}
		String typeName = (String) CommunicationServiceLocal.datatype.get(type);
		switch(type){
		case CommunicationServiceLocal.BOOLEAN:
			addBoolean(id, name);
		case CommunicationServiceLocal.BYTE:
			addByte(id, name);
		case CommunicationServiceLocal.CHAR:
			addChar(id, name);
		case CommunicationServiceLocal.DOUBLE:
			addDouble(id, name);
		case CommunicationServiceLocal.FLOAT:
			addFloat(id, name);
		case CommunicationServiceLocal.INTEGER:
			addInteger(id, name);
		case CommunicationServiceLocal.LONG:
			addLong(id, name);
		case CommunicationServiceLocal.SHORT:
			addShort(id,name);
		default:
			//throw Exception...
			
			
		}
	}
	//fuer jeden Datentyp wird die enspr. Methode aufgerufen
	//bisher nur primitve Datentypen
	//Inittialisieru2ng muss ueberarbeitet werden
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
