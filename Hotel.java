//Name: Matthew Murphy
//ID: 15147193

import java.util.*;

/**
*	An object for a hotel
*/
public class Hotel{
	private String hotelName;
	private ArrayList <Room> rooms;

	/**
	*	Creates Hotel with name and an arraylist for rooms
	*	@param The Hotel name
	*/
	public Hotel(String hotelName){
		this.hotelName = hotelName;
		this.rooms = new ArrayList <Room>();
	}

	/**
	*	Adds room to room arraylist
	*	@param The room name
	*	@param The number of that room left
	*	@param The occupancy min and max of the room
	*	@param The rates per day of the room
	*/
	public void addRoom(String roomName, int noLeft, String [] occupancy, double [] rates){
		rooms.add(new Room(roomName, noLeft, occupancy, rates));
	}

	/**
	*	Gets the name of the hotel
	*/
	public String getName(){
		return this.hotelName;
	}

	/**
	*	Gets room i form the rooms arraylist
	*/
	public Room getRoom(int i){
		return rooms.get(i);
	}

	/**
	*	Gets the number of room types in the hotel
	*/
	public int getRoomSize(){
		return rooms.size();
	}

	/**
	*	Sends this hotel information back as a string
	*/
	public String toString(){
		String x = "";
		int daysInWeek = 7;
		x += hotelName + "\tRoom Name|| Rooms Left|| Occupancy Min| Occupancy Max|| Rates\n\t\t\t\t\t\t\t\t\tMon| Tues| Wed| Thurs| Fri| Sat\n\t";
		for(int i = 0; i < rooms.size(); i++){
			x += rooms.get(i).getName() + "|| " + rooms.get(i).getNoLeft() + "|| " + rooms.get(i).getOccupancy()[0] + "| " + rooms.get(i).getOccupancy()[1] + "|| ";
			for(int j = 0; j < daysInWeek; j++)
				x +=  rooms.get(i).getRate(j) + "| ";
			x += "\n\t";
		}
		return x;
	}
}