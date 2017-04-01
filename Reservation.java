//Name: Matthew Murphy
//ID: 15147193

import java.util.*;
import java.io.*;
import java.text.*;

/**
*	An object for a reservation
*/
public class Reservation{
	private int resNum;
	private String resName;
	private String resType;
	private GregorianCalendar checkInDate;
	private int noOfNights;
	private int noOfRooms;
	private ArrayList <Room> rooms;
	private boolean breakfast;
	private double cost;
	private double deposit;
	private boolean checkedIn;
	private boolean checkedOut;
	private boolean canceled;
	private boolean refunded;
	
	/**
	*	Creates Reservation with all its data except cancled and refunded which defaults to false
	*	@param The reservation number
	*	@param The reservation name
	*	@param The reservation type
	*	@param The check in date
	*	@param The number of nights
	*	@param The number of rooms
	*	@param The array list of rooms
	*	@param The boolean to say if they wanted breakfast
	*	@param The deposit
	*	@param The boolean to say if they checked in
	*	@param The boolean to say if they checked out
	*	@param The boolean to say if they got refunded
	*/
	public Reservation(int resNum, String resName, String resType, GregorianCalendar checkInDate,
			int noOfNights, int noOfRooms, ArrayList <Room> rooms, boolean breakfast, double deposit, boolean checkedIn, boolean checkedOut){
		this.resNum = resNum;
		this.resName = resName;
		this.resType = resType;
		this.checkInDate = checkInDate;
		this.noOfNights = noOfNights;
		this.noOfRooms = noOfRooms;
		this.rooms = rooms;
		this.breakfast = breakfast;
		this.deposit = deposit;
		this.cost = calCost();
		this.checkedIn = checkedIn;
		this.checkedOut = checkedOut;
		this.canceled = false;
		this.refunded = false;
	}

	/**
	*	Creates Reservation with all its data
	*	@param The reservation number
	*	@param The reservation name
	*	@param The reservation type
	*	@param The check in date
	*	@param The number of nights
	*	@param The number of rooms
	*	@param The array list of rooms
	*	@param The boolean to say if they wanted breakfast
	*	@param The deposit
	*	@param The boolean to say if they checked in
	*	@param The boolean to say if they checked out
	* 	@param The boolean to say if they cancled
	*	@param The boolean to say if they got refunded
	*/
	public Reservation(int resNum, String resName, String resType, GregorianCalendar checkInDate,
			int noOfNights, int noOfRooms, ArrayList <Room> rooms, boolean breakfast, double deposit, boolean checkedIn, boolean checkedOut, boolean canceled, boolean refunded){
		this.resNum = resNum;
		this.resName = resName;
		this.resType = resType;
		this.checkInDate = checkInDate;
		this.noOfNights = noOfNights;
		this.noOfRooms = noOfRooms;
		this.rooms = rooms;
		this.breakfast = breakfast;
		this.deposit = deposit;
		this.cost = calCost();
		this.checkedIn = checkedIn;
		this.checkedOut = checkedOut;
		this.canceled = canceled;
		this.refunded = refunded;
	}

	/**
	*	Sets check in to true
	*/
	protected void checkIn(){
		checkedIn= true;
	}

	/**
	*	Sets check out to true
	*/
	protected void checkOut(){
		checkedOut= true;
	}

	/**
	*	Sets canceled to true
	*/
	protected void cancel(){
		canceled = true;
	}

	/**
	*	Sets refunded to true
	*/
	protected void refund(){
		refunded = true;
	}

	/**
	*	Calculates cost of reservation
	*/
	protected double calCost(){
		double breakfastCost = 5;
		int day = checkInDate.get(GregorianCalendar.DAY_OF_WEEK) - 2;
		double costTemp = 0;
		if(day < 0)
			day = 6;
		for(int i = 0; i < noOfNights; i++){
			if(day > 6)
				day = 0;
			for(int j = 0; j < rooms.size(); j++){
				//System.out.print(day + " DAY\n");
				costTemp += rooms.get(j).getRate(day);
			}
			day++;
		}
		if(breakfast)
			costTemp += breakfastCost * noOfNights;
		if(resType.matches("AP")){
			double fivePrecent = cost * 0.05;
			costTemp -= fivePrecent;
		}
		return costTemp;
	}

	/**
	*	Returns ArrayList of rooms in this reservation
	*/
	protected ArrayList <Room> getRooms(){
		return rooms;
	}

	/**
	*	Returns the number of rooms in this reservation
	*/
	protected int getRoomsSize(){
		return rooms.size();
	}

	/**
	*	Returns the name of room i
	*/
	protected String getRoomName(int i){
		return rooms.get(i).getName();
	}

	/**
	*	Returns the reservation number of this reservation
	*/
	protected int getResNum(){
		return resNum;
	}

	/**
	*	Returns the reservation type of this reservation
	*/
	protected String getType(){
		return resType;
	}

	/**
	*	Returns the reservation check in date of this reservation
	*/
	protected GregorianCalendar getDate(){
		return checkInDate;
	}

	/**
	*	Returns a boolean for cancled
	*/
	protected boolean getCanceled(){
		return canceled;
	}

	/**
	*	Returns a boolean for refunded
	*/
	protected boolean getRefunded(){
		return refunded;
	}

	/**
	*	Returns the cost of this reservation
	*/
	protected double getCost(){
		return cost;
	}

	/**
	*	Sets the cost of this reservation
	*/
	protected void setCost(double cost){
		this.cost = cost;
	}

	/**
	*	Returns the reservation as a string
	*/
	public String toString(){
		String reservationString = String.valueOf(resNum) + ",";
		reservationString += resName + ",";
		reservationString += resType + ",";
		String checkInDateString = checkInDate.get(Calendar.DAY_OF_MONTH) + "/" + (checkInDate.get(Calendar.MONTH) + 1) + "/" + checkInDate.get(Calendar.YEAR);
		reservationString += checkInDateString + ",";
		reservationString += String.valueOf(noOfNights) + ",";
		reservationString += String.valueOf(noOfRooms) + ",";
		reservationString += String.valueOf(breakfast) + ",";
		reservationString += String.valueOf(cost) + ",";
		reservationString += String.valueOf(deposit) + ",";
		reservationString += String.valueOf(checkedIn) + ",";
		reservationString += String.valueOf(checkedOut) + ",";
		reservationString += String.valueOf(canceled) + ",";
		reservationString += String.valueOf(refunded) + ",";
		return reservationString;
	}
}