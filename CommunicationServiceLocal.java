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

	static Hashtable datatype;				//Identifikation der Datentypen


	public class ValueTable{
		byte id;
		String name;
		byte datatype;
		
		ValueTable(byte id, String name, byte datatype){
			this.id = id;
			this.name = name;
			this.datatype = datatype;
		}
	}
	
	private String getNodeName(byte [] path){
		//Die Hashtable "structure" beinhaltet alle Knoten nach Pfad sortiert.
		//Die Knoten werden als Hashtables representiert, wobei
		//Key = 0 den Namen des Knoten entspricht.
		Hashtable attribute = (Hashtable) structure.get(Arrays.toString(path));
		return (String) attribute.get("[0]");
	}

	private String getCommand(byte [] cmd){
		return (String) commands.get(Arrays.toString(cmd));
	}

	/*+++++++++++++++INITIALIZE+++++++++++++++++*/

	//Initialisieren der Hashtable anhand der Excel-Tabelle
	private void initTable(){
	}


	private void initTable(byte [] prefix){
	}

	//Initialisieren der im Fahrzeug vorhandenen Daten


	private void initialize(){

		byte[] input = collectInput().getBytes();
		
		try{
			analyseHeader(Arrays.copyOfRange(input, 0, HEADER_LENGTH - 1));
		}catch(Exception e){	
		}
		
		

		for(int i = HEADER_LENGTH; i < input.length; i+=input[i]){
			if(datatype.get(input[i]) == "node"){
				initializeNode(Arrays.copyOfRange(input, 0, input[i]));

			}else if(datatype.get(input[i]) == "value"){
				initializeValue(Arrays.copyOfRange(input, 0, input[i]));
			}
		}
	}

	private void initializeNode(byte [] input){
		int type = input [1];
		int depth = input [2];
		byte [] path = Arrays.copyOfRange(input, 3, 3+depth);
		Node current = data;
		for(int i = 0; i < path.length; i++){
			current = current.getNextNode(path[i]);
		}
		Hashtable values = (Hashtable) structure.get(Arrays.toString(path));
		for(int i = 3 + depth + 1; i < input.length; i++){
			byte id = input [i];
			String name = (String) values.get(input[i]);
			current.add(path, id, name);
		}
		
		
	}
	
	

	private void initializeValue(byte [] input){

	}

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
