package com.example.communicationservice;

import java.util.*;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class CommunicationServiceLocal extends Service{

	static final int HEADER_LENGTH = 4;		//Laenge des Headers

	static Node data;						//Wurzel des Datenbaums

	static Hashtable commands;				//Hashtable mit den Befehlen

	static Hashtable structure;				//Pfadsortierte Datenstruktur

	public static Hashtable datatype;				//Identifikation der Datentypen
	
	
	//Object Datentypen
	static final byte NODE = 1;
	static final byte VALUE = 2;
	//primitive Datentypen
	static final byte BOOLEAN = 10;
	static final byte BYTE = 11;
	static final byte CHAR = 12;
	static final byte DOUBLE = 13;
	static final byte FLOAT = 14;
	static final byte INTEGER = 15;
	static final byte LONG = 16;
	static final byte SHORT = 17;

	
	//InputType
	static final byte INIT = 1;
	static final byte DATA = 2;
	static final byte COMMAND = 3;

	
	//Die Pfadsortierten Hashtable structure beinhaltet weitere Hashtables,
	//die die Values der Knoten widerspiegelt.
	//Dabei beinhaltet die zweite Hashtable TableObject, um der ID zwei Werte
	//(name & Datatype) zuornden zu kšnnen. Die ID 0 ist immer der Name des
	//Knotens
	
	public class TableObject{
		byte id;
		String name;
		byte datatype;
		
		TableObject(byte id, String name, byte datatype){
			this.id = id;
			this.name = name;
			this.datatype = datatype;
		}
	}
	
	/*+++++++++++++++++++++++++++GET_METHODEN++++++++++++++++++++++++++++++++*/

	
	private String getNodeName(byte [] path){
		//Die Hashtable "structure" beinhaltet alle Knoten nach Pfad sortiert.
		//Die Knoten werden als Hashtables representiert, wobei
		//Key = 0 den Namen des Knoten entspricht.
		Hashtable attribute = (Hashtable) structure.get(Arrays.toString(path));
		return (String) attribute.get("[0]");
	}

	private Node getNode(byte [] path){
		Node current = data;
		for(int i = 0; i < path.length; i++){
			current = current.getNextNode(path[i]);
		}
		return current;
		
	}
	private String getCommand(byte [] cmd){
		return (String) commands.get(Arrays.toString(cmd));
	}

	/*+++++++++++++++++++++++++INITIALIZE_HASHTABLE++++++++++++++++++++++++++*/

	//Initialisieren der Hashtable anhand der Excel-Tabelle
	
	private void initTable(){
	}


	private void initTable(byte [] prefix){
	}

	/*+++++++++++++++++++++++++++INITIALIZE_DATA+++++++++++++++++++++++++++++*/
	
	//Initialisieren der im Fahrzeug vorhandenen Daten

	private void initialize(byte [] input){

		
		try{
			analyseHeader(Arrays.copyOfRange(input, 0, HEADER_LENGTH - 1));
		}catch(Exception e){	
		}
		
		
		
		for(int i = HEADER_LENGTH; i < input.length; i+=input[i]){
			if(datatype.get(input[i+1]) == "node"){
				initializeNode(Arrays.copyOfRange(input, i, input[i]));

			}else if(datatype.get(input[i+1]) == "value"){
				initializeValue(Arrays.copyOfRange(input, i, input[i]));
			}
		}
	}

	
	private void initializeNode(byte [] input){
		byte length = input [0];
		byte type = input [1];
		byte depth = input [2];
		byte id;
		String name;
		byte [] path = Arrays.copyOfRange(input, 3, 3+depth);
		Node current = getNode(path);
		Hashtable values = (Hashtable) structure.get(Arrays.toString(path));
		
		for(int i = 3 + depth; i < input.length; i++){
			id = input [i];
			TableObject father = (TableObject) values.get("[0]");
			name = father.name;
			current.add(path, id, name);
		}
	}
	
	

	private void initializeValue(byte [] input){
		byte length = input [0];
		byte type = input [1];
		byte depth = input [2];
		byte id;
		String name;
		byte [] path = Arrays.copyOfRange(input, 3, 3+depth);
		Node current = getNode(path);
		Hashtable values = (Hashtable) structure.get(Arrays.toString(path));
		
		for(int i = 3 + depth; i < input.length; i++){
			id = input [i];
			TableObject value = (TableObject) values.get(input[i]);
			name = value.name;
			type = value.datatype;
			current.addValue(type, id, name);
		}

	}
	
	/*+++++++++++++++++++++++++++REFRESH_METHODE+++++++++++++++++++++++++++++*/

	private void refreshNode(byte [] path){
		Node current = this.getNode(path);
		Vector command = current.refresh();
		byte [] commandArray = new byte [command.size()];
		for(int i = 0; i < command.size(); i++){
			String z = (String) command.get(i);
			byte [] y = z.getBytes();
		}
	}
	
	private void refreshValue(byte [] path, byte id){
		
	}
	
	/*+++++++++++++++++++++++++++++++TODO++++++++++++++++++++++++++++++++++++*/


	//Verbindungsaufbau mit dem ausgewaehltem Kanal
	public void connect(String chanel){
	}


	//Befehl senden
	public void sendCommand(byte cmd){

	}
	
	public void analyse(byte [] input){
		byte [] header = Arrays.copyOfRange(input, 0, HEADER_LENGTH - 1);
		byte inputType = analyseHeader(header);
		byte [] restInput = Arrays.copyOfRange(input, HEADER_LENGTH, input.length - 1);
		switch(inputType){
		case INIT:
			initialize(restInput);
		case DATA:
			analyseData(restInput);
		case COMMAND:
			analyseCommand(restInput);
		}
		
	}
	public byte analyseHeader(byte [] header){
		return 0;
	}

	public void analyseData(byte [] input){
		
		for(int i = 0; i < input.length; i += input [i]){
			setData(Arrays.copyOfRange(input, i, input[i] - 1));
		}
	}
	
	public void setData(byte [] input){
		byte length = input [0];
		byte type = input[1];
		byte depth = input [2];
		byte [] path = new byte [depth + 1];
		for (int i = 0; i < depth; i++){
			path[i] = input [i + 3];
		}
		int dataBeginn = path.length;
		byte id = input[path.length + 1];
		Value value = getValue(path, id);
		value.setValue(Arrays.copyOfRange(input, path.length + 2, input.length));
	}
	
	
	public Value getValue(byte [] path, byte id){
		Node node = getNode(path);
		Value value = node.getValue(id);
		return value;
	}
	
	
	public void analyseCommand(byte [] input){
	}

	//File Abholen
	public String collectInput(){

		return "Input";
	}
	
	public byte [] convertToByte(Vector command){
		
		command.add(new Byte((byte)2));
		command.add(new Byte((byte)5));
		command.add(new Byte((byte)100));
		command.add(new Byte((byte)87));
		
		
		
		String o = command.get(0).toString();
		System.out.println(o);
		char [] e = o.toCharArray();
		int h = Character.getNumericValue(e[0]);
	
		
		int k = command.lastIndexOf(command.lastElement());
		
		byte [] feld = new byte [k+1];
		Vector current = command;
		Arrays.fill(feld, (byte)0);
		for(int i = 0; i < k + 1; i++){
			String cmdString = current.get(i).toString();
			char [] cmdArray = cmdString.toCharArray();
			for(int j = 0; j < cmdArray.length; j++){
				byte cmdByte = 0;
				cmdByte = (byte) Character.getNumericValue(cmdArray[j]);
				feld[i] += (byte) (cmdByte *(Math.pow(10, cmdArray.length - j - 1)));
			}
		}
		return feld;
	}


	@Override
	public IBinder onBind(Intent arg0) {
		return null;
	}
}
