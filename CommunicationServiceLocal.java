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

		//sendCommand((byte)commands.get("INIT"));

		byte[] input = collectInput().getBytes();

		analyseHeader(Arrays.copyOfRange(input, 0, HEADER_LENGTH - 1));

		for(int i = HEADER_LENGTH; i < input.length; i+=input[i]){
			initializeNode(Arrays.copyOfRange(input, 0, input[i]));
		}
	}

	private void initializeNode(byte [] input){
		int type = input [1];
		int depth = input [2];
		byte [] path = Arrays.copyOfRange(input, 3, 3+depth);
		Node newNode = new Node();
	}

	private void initializeValue(){

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
