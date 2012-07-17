package com.example.communicationservice;
import java.util.*;

public class Value{
	
	byte id;
	String name;
	Date zeitstempel;
	Object value;
	Value next;
	Node vather;
	
	
	public Value(byte id, String name, Object value){
		this.id = id;
		this.name = name;
		this.value = value;
	}
	
	public Value(byte id, String name, Object value, Date zeitstempel){
		this(id, name, value);
		this.zeitstempel = zeitstempel;
		
	}
	
	
	public Value searchValue(byte id){
		return null;
	}
	
	public Value lastPointer(Value values){
		return null;
	}
	
	public void refresh(){
		
	}

	public Value getLastValue() {
		// TODO Auto-generated method stub
		return null;
	}

	public void addValue(byte type, byte[] path, byte id) {
		
		switch(type){
		}
	}
	private void addByte(byte id, String name) {
		this.next = new Value(id, name, new Byte(null));
		
	}
	private void addInteger(byte id, String name) {
		this.next = new Value(id, name, new Integer(null));
	}
	private void addBoolean(byte id, String name) {
		this.next = new Value(id, name, new Boolean(null));
		
	}
	
	
}
