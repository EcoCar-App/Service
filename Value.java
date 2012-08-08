package com.example.communicationservice;

import java.nio.ByteBuffer;
import java.util.*;

public class Value{

	
	/*++++++++++++++++++++++++++++++ATTRIBUTE++++++++++++++++++++++++++++++++*/
	
	private byte type;			//gibt den Datentyp von value an

	private byte id;			//ID des Attributs
	
	private String name;		//Name des Attributs
	
	private Date time;			//mšglicher Zeitstempel
	
	private Node father;		//Zeiger auf Vaterknoten

	private Object value;		//Object in dem der entpr. Datentyp gespeichert wird
	
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
	
	
	
	public Value getValue(byte id){
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
	
	public Value getLast(){
		Value current = this;
		Value last = this;
		while(current != null){
			last = current;
			current = current.next;
		}
		return last;
	}

	
	/*+++++++++++++++++++++++++++++++SET_METHODEN++++++++++++++++++++++++++++*/
	
	public void setValue(byte [] input){
		
		if(this != null){
			//throw Exception
		}
		
		switch(this.type){
		case CommunicationServiceLocal.BOOLEAN:
			setBoolean(input);
			break;
		case CommunicationServiceLocal.BYTE:
			setByte(input);
			break;
		case CommunicationServiceLocal.CHAR:
			setCharacter(input);
			break;
		case CommunicationServiceLocal.DOUBLE:
			setDouble(input);
			break;
		case CommunicationServiceLocal.FLOAT:
			setFloat(input);
			break;
		case CommunicationServiceLocal.INTEGER:
			setInteger(input);
			break;
		case CommunicationServiceLocal.LONG:
			setLong(input);
			break;
		case CommunicationServiceLocal.SHORT:
			setShort(input);
			break;
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
		this.value = ByteBuffer.wrap(input).get();
	}
	
	public void setCharacter(byte [] input){
		this.value = ByteBuffer.wrap(input).getChar();
	}
	
	public void setDouble(byte [] input){
		this.value = ByteBuffer.wrap(input).getDouble();
	}
	
	public void setFloat(byte [] input){
		this.value = ByteBuffer.wrap(input).getFloat();
	}
	
	public void setInteger(byte [] input){
		this.value = ByteBuffer.wrap(input).getInt();
	}
	
	public void setLong(byte [] input){
		this.value = ByteBuffer.wrap(input).getLong();
	}
	
	public void setShort(byte [] input){
		this.value = ByteBuffer.wrap(input).getShort();
	}
	
	public void setNext(Value next){
		this.next = next;
	}
	
	

	
	/*+++++++++++++++++++++++++++++++++REFRESH+++++++++++++++++++++++++++++++*/

	
	public Vector <Byte> refresh(){
		
		byte [] path;

		if(father != null){
			path = father.getPath();
		}else{
			path = new byte[1];
			path[0] = id;

		}
		byte depth = (byte) path.length;
		byte command = (Byte) CommunicationServiceLocal.commands.get("GET");
		byte length = (byte) (depth + (byte) 4);
		
		Vector<Byte> commandLine = new Vector<Byte>();
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
		
		switch(type){
		case CommunicationServiceLocal.BOOLEAN:
			addBoolean(id, name, father, type);
			break;
		case CommunicationServiceLocal.BYTE:
			addByte(id, name, father, type);
			break;
		case CommunicationServiceLocal.CHAR:
			addCharacter(id, name, father, type);
			break;
		case CommunicationServiceLocal.DOUBLE:
			addDouble(id, name, father, type);
			break;
		case CommunicationServiceLocal.FLOAT:
			addFloat(id, name, father, type);
			break;
		case CommunicationServiceLocal.INTEGER:
			addInteger(id, name, father, type);
			break;
		case CommunicationServiceLocal.LONG:
			addLong(id, name, father, type);
			break;
		case CommunicationServiceLocal.SHORT:
			addShort(id,name, father, type);
			break;
		default:
			//throw Exception...
		}
	}
	
	//fuer jeden Datentyp wird die enspr. Methode aufgerufen
	//bisher nur primitve Datentypen
	//Inittialisieru2ng muss ueberarbeitet werden
	private void addBoolean(byte id, String name, Node father, byte type) {
		Value newValue = this.getValue(id);
		newValue = new Value(id, name, new Boolean(null), father, type);
		father.newValue(newValue);
	}
	private void addByte(byte id, String name, Node father, byte type) {
		Value newValue = this;
		newValue = new Value(id, name, new Byte(null), father, type);
		father.newValue(newValue);
	}
	
	private void addCharacter(byte id, String name, Node father, byte type){
		Value newValue = this;
		char value = 0;
		newValue = new Value(id, name, value, father, type);
		father.newValue(newValue);
	}
	
	private void addDouble(byte id, String name, Node father, byte type){
		Value newValue = this;
		newValue = new Value(id, name, new Double(Double.NaN), father, type);
		father.newValue(newValue);
	}
	
	private void addFloat(byte id, String name, Node father, byte type){
		Value newValue = this;
		newValue = new Value(id, name, new Float(Float.NaN), father, type);
		father.newValue(newValue);
	}
	
	private void addInteger(byte id, String name, Node father, byte type){
		Value newValue = this;
		newValue = new Value(id, name, new Integer(0), father, type);
		father.newValue(newValue);
	}

	private void addLong(byte id, String name, Node father, byte type){
		Value newValue = this;
		newValue = new Value(id, name, new Long(0), father, type);
		father.newValue(newValue);
	}
	private void addShort(byte id, String name, Node father, byte type){
		Value newValue = this;
		newValue = new Value(id, name, new Short((short)0), father, type);
		father.newValue(newValue);
	}
}
