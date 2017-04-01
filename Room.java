//Name: Matthew Murphy
//ID: 15147193

import java.util.*;
import java.io.*;

/**
*	An object for a room
*/
public class Room{
	private String roomName;
	private int noLeft;
	private String [] occupancy = new String [2];
	private double [] rates = new double [7];

	/**
	*	Creates Room with all its data
	*	@param The room name
	*	@param The number of that room left
	*	@param The occupancy min and max of the room
	*	@param The rates per day of the room
	*/
	public Room(String roomName, int noLeft, String [] occupancy, double [] rates){
		this.roomName = roomName;
		this.noLeft = noLeft;
		this.occupancy = occupancy;
		this.rates = rates;
	}

	/**
	*	Gets the room name
	*/
	protected String getName(){
		return this.roomName;
	}

	/**
	*	Gets number of rooms left
	*/
	protected int getNoLeft(){
		return this.noLeft;
	}

	/**
	*	Gets Occupancy min and max of room
	*/
	protected String [] getOccupancy(){
		return this.occupancy;
	}

	/**
	*	Gets rates per day of this room
	*/
	protected double [] getRates(){
		return this.rates;
	}

	/**
	*	Gets rate for date i
	*	@param Day that rate is wanted 0 = Monday
	*/
	protected double getRate(int i){
		return rates[i];
	}

	/**
	*	Takes one room away from number of rooms
	*/
	protected void takeOne(){
		noLeft--;
	}

	/**
	*	Adds one to the number of rooms left
	*/
	protected void addOne(){
		noLeft++;
	}

	/**
	*	Sends this rooms information back as a string
	*/
	public String toString(){
		String x = "";
		x += roomName + "," + String.valueOf(noLeft) + ",";
		x += occupancy[0] + "," + occupancy[1] + ",";
		for(int i = 0; i < rates.length; i++){
			x += String.valueOf(rates[i]) + ",";
		} 
		return x;
	}
}