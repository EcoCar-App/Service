package com.example.communicationservice;

import java.util.*;

import android.annotation.SuppressLint;
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

	//Initialisieren der im Fahrzeug vorhandenen Daten

	/*+++++++++++++++++++++++++++INITIALIZE_DATA+++++++++++++++++++++++++++++*/

	private void initialize(){

		byte[] input = collectInput().getBytes();
		
		try{
			analyseHeader(Arrays.copyOfRange(input, 0, HEADER_LENGTH - 1));
		}catch(Exception e){	
		}
		
		
		
		for(int i = HEADER_LENGTH; i < input.length; i+=input[i]){
			if(datatype.get(input[i]) == "node"){
				initializeNode(Arrays.copyOfRange(input, i, input[i]));

			}else if(datatype.get(input[i]) == "value"){
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
			current.values.add(type, id, name);
		}

	}
	
	
	/*+++++++++++++++++++++++++++++++TODO++++++++++++++++++++++++++++++++++++*/


	//Verbindungsaufbau mit dem ausgewaehltem Kanal
	public void connect(String chanel){
	}


	//Befehl senden
	public void sendCommand(byte cmd){

	}

	public void analyseHeader(byte [] header){

	}

	public void analyseData(){

	}

	//File Abholen
	public String collectInput(){

		return "Input";
	}


	@Override
	public IBinder onBind(Intent arg0) {
		return null;
	}




}
