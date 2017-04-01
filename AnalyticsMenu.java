//Name: Matthew Murphy
//ID: 15147193

import java.util.*;
import java.io.*;

/**
*	An object that displays the menu for analytics
*/
public class AnalyticsMenu extends Menu{
	private Scanner in;
	private static int analysisNum = 0;
	private String fileName;
	private String name;
	private double totalEarnings;

	/**
	*	Creates analytics menu
	*/
	public AnalyticsMenu(String fileName, String name){
		in = new Scanner(System.in);
		this.fileName = fileName;
		this.name = name;
		totalEarnings = 0;
	}

	/**
	*	Runs the menu
	*/
	public void menu(){
		System.out.println("What do you want to analyse?\n1)A Hotel\n2)A Room");
		ArrayList <String> list = new ArrayList <String>();
		int responce1 = 0;
		boolean doneQ = false;
		while(!doneQ){
			responce1 = Integer.parseInt(in.nextLine());
			if(responce1 == 1){
				for(int i = 0; i < hotels.size(); i++){
					list.add(((i + 1) + ")" + hotels.get(i).getName()) + "\n");
				}
				doneQ = true;
			}
			else if(responce1 == 2){
				for(int i = 0; i < hotels.size(); i++){
					for(int j = 0; j < hotels.get(i).getRoomSize(); j++){
						list.add(hotels.get(i).getRoom(j).getName());
					}
				}
				doneQ = true;
			}
			else
				System.out.println("Error: Incorrect responce");
		}

		System.out.println("Pick one to analyse\n");
		for(int i = 0; i < list.size(); i++){
			System.out.println((i + 1) + ")" + list.get(i));
		}
		int responce2 = Integer.parseInt(in.nextLine());
		if(responce1 == 1)
			hotelAnalysis(responce2);
		else
			roomAnalysis(list.get(responce2 - 1));
		//System.out.println(hotels.get(responce2 - 1).getName());
	}

	/**
	*	Adds up money made for hotel user picked
	*/
	public void hotelAnalysis(int hotelNum){
		hotelNum--;
		String hotelName = hotels.get(hotelNum).getName();
		String [] roomNames = new String [hotels.get(hotelNum).getRoomSize()];
		for(int i = 0; i < hotels.get(hotelNum).getRoomSize(); i++){
			roomNames[i] = hotels.get(hotelNum).getRoom(i).getName();
		}
		for(int i = 0; i < reservations.size(); i++){
			if(!reservations.get(i).getCanceled() || !reservations.get(i).getRefunded()){
				for(int j = 0; j < roomNames.length; j++){
					for(int k = 0; k < reservations.get(i).getRoomsSize(); k++){
						if(roomNames[j].equalsIgnoreCase(reservations.get(i).getRoomName(k)))
							totalEarnings += reservations.get(i).getCost();
					}
				}
			}
		}
		System.out.println(hotelName + " " + totalEarnings);
	}

	/**
	*	Adds up money made for room user picked
	*/
	public void roomAnalysis(String roomName){
		for(int i = 0; i < reservations.size(); i++){
			if(!reservations.get(i).getCanceled() || !reservations.get(i).getRefunded()){
				for(int j = 0; j < reservations.get(i).getRoomsSize(); j++){
					if(roomName.equalsIgnoreCase(reservations.get(i).getRoomName(j)))
						totalEarnings += reservations.get(i).getCost();
				}
			}
		}
		System.out.println(roomName + " " + totalEarnings);
	}
}