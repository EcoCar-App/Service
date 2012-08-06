package com.example.communicationservice;
import android.annotation.SuppressLint;
import java.util.*;

public class Value{

	
	/*++++++++++++++++++++++++++++++ATTRIBUTE++++++++++++++++++++++++++++++++*/
	
	private byte type;			//gibt den Datentyp von value an

	private byte id;			//ID des Attributs
	
	private String name;		//Name des Attributs
	
	private Date time;			//mšglicher Zeitstempel
	
	private Node father;		//Zeiger auf Vaterknoten

	private Object value;		//Object in dem der entpr. Datentyp gespeichert wird
	
	private static Value head;	//Zeiger auf das erste Element der Liste

	private Value next;			//Zeiger auf den naechsten Wert
	
	
	/*++++++++++++++++++++++++++++KONSTRUKTOREN++++++++++++++++++++++++++++++*/

	public Value(){

	}
	public Value(byte id, String name, Object value, Node father, byte type){
		this.id = id;
		this.name = name;
		this.value = value;
		this.father = father;
		this.type = type;
	}

	public Value(byte id, String name, Object value, Node father, byte type, Date time){
		this(id, name, value, father, type);
		this.time = time;
	}

	/*++++++++++++++++++++++++++++++GET_METHODEN+++++++++++++++++++++++++++++*/

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
	
	public byte getType(){
		return this.type;
	}
	
	public Value getHead(){
		return this.head;
	}
	
	//sucht in der Liste das passende Object zur ID
	private Value getValue(byte id){
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
	public Value getLast(){
		Value current = this;
		while(current != null){
			current = current.next;
		}
		return current;
	}

	
	/*+++++++++++++++++++++++++++++++SET_METHODEN++++++++++++++++++++++++++++*/
	
	public void setValue(byte [] input){
		
		if(this != null){
			//throw Exception
		}
		
		switch(this.type){
		case CommunicationServiceLocal.BOOLEAN:
			setBoolean(input);
		case CommunicationServiceLocal.BYTE:
			setByte(input);
		case CommunicationServiceLocal.CHAR:
			setCharacter(input);
		case CommunicationServiceLocal.DOUBLE:
			setDouble(input);
		case CommunicationServiceLocal.FLOAT:
			setFloat(input);
		case CommunicationServiceLocal.INTEGER:
			setInteger(input);
		case CommunicationServiceLocal.LONG:
			setLong(input);
		case CommunicationServiceLocal.SHORT:
			setShort(input);
		default:
			//throw Exception...
		}
	}
	
	public void setValue(byte [] input, Date time){
		this.time = time;
		setValue(input);
	}
	
	
	public void setBoolean(byte [] input){
		if(input.length > 1){
			//Exception, kein Boolean
		}else if(input[0] == 0){
			this.value = false;
		}else if(input [0] == 1){
			this.value = true;
		}
	}
	
	public void setByte(byte [] input){
		if(input.length > 1){
			//Exception, kein Byte
		}else{
			this.value = input[0];
		}
	}
	
	public void setCharacter(byte [] input){
		
	}
	
	public void setDouble(byte [] input){
		
	}
	
	public void setFloat(byte [] input){
		
	}
	
	public void setInteger(byte [] input){
		
	}
	
	public void setLong(byte [] input){
		
	}
	
	public void setShort(byte [] input){
		
	}
	
	

	
	/*+++++++++++++++++++++++++++++++++REFRESH+++++++++++++++++++++++++++++++*/

	
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

	/*++++++++++++++++++++++++++++++++ADD_METHODEN+++++++++++++++++++++++++++*/

	//Oberste Add-Methode: ruft die entspr. Methode auf
	public void add(byte type, byte id, String name, Node father) {
		if(this != null){
			//throw Exception
		}
		switch(type){
		case CommunicationServiceLocal.BOOLEAN:
			addBoolean(id, name, father, type);
		case CommunicationServiceLocal.BYTE:
			addByte(id, name, father, type);
		case CommunicationServiceLocal.CHAR:
			addCharacter(id, name, father, type);
		case CommunicationServiceLocal.DOUBLE:
			addDouble(id, name, father, type);
		case CommunicationServiceLocal.FLOAT:
			addFloat(id, name, father, type);
		case CommunicationServiceLocal.INTEGER:
			addInteger(id, name, father, type);
		case CommunicationServiceLocal.LONG:
			addLong(id, name, father, type);
		case CommunicationServiceLocal.SHORT:
			addShort(id,name, father, type);
		default:
			//throw Exception...
		}
	}
	
	//fuer jeden Datentyp wird die enspr. Methode aufgerufen
	//bisher nur primitve Datentypen
	//Inittialisieru2ng muss ueberarbeitet werden
	private void addBoolean(byte id, String name, Node father, byte type) {
		Value newValue = this;
		newValue = new Value(id, name, new Boolean(null), father, type);
		
	}
	private void addByte(byte id, String name, Node father, byte type) {
		Value newValue = this;
		newValue = new Value(id, name, new Byte(null), father, type);
	}
	
	private void addCharacter(byte id, String name, Node father, byte type){
		Value newValue = this;
		char value = 0;
		newValue = new Value(id, name, value, father, type);
	}
	
	private void addDouble(byte id, String name, Node father, byte type){
		Value newValue = this;
		newValue = new Value(id, name, new Double(null), father, type);
	}
	
	private void addFloat(byte id, String name, Node father, byte type){
		Value newValue = this;
		newValue = new Value(id, name, new Float(null), father, type);
	}
	
	private void addInteger(byte id, String name, Node father, byte type){
		Value newValue = this;
		newValue = new Value(id, name, new Integer(null), father, type);
	}

	private void addLong(byte id, String name, Node father, byte type){
		Value newValue = this;
		newValue = new Value(id, name, new Long(null), father, type);
	}
	private void addShort(byte id, String name, Node father, byte type){
		Value newValue = this;
		newValue = new Value(id, name, new Short(null), father, type);
	}
}
