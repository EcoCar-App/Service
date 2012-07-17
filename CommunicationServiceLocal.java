package com.example.communicationservice;

import java.util.*;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class CommunicationServiceLocal extends Service{
	static final int HEADER_LENGTH = 4;
	static Node data = new Node();
	static Hashtable commands = new Hashtable();
	//Hashtable in dem die Knoten zugeordnet, wobei jede Zuordnung
	//eine Hashtable mit Attributen enthält
	//Dabei ist für den Index 0 immer der Name des Knotens gemeint
	static Hashtable structure = new Hashtable();
	//Hashtable für den Datentyp. 255 Werte reichen
	static Hashtable datatype = new Hashtable();

	private String getNodeName(byte [] path){
		Hashtable attribute = (Hashtable) structure.get(Arrays.toString(path));
		return (String) attribute.get("[0]");
	}

	private String getCommand(byte [] cmd){
		return (String) commands.get(Arrays.toString(cmd));
	}


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
	//Verbindungsaufbau mit dem ausgewähltem Kanal
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

		return "Stream";
	}







	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}




}
