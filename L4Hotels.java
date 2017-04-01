//Name: Matthew Murphy
//ID: 15147193

import java.util.*;
import java.io.*;

/**
*	Main class that creates the ReservationMenu objects
*/
public class L4Hotels{
	/**
	*	Creates and calls ReservationMenu
	*/
	public static void main(String[]args) throws IOException{
		String filename = "l4Hotels.csv";
		ArrayList <Reservation> reservations = new ArrayList <Reservation>();
		ReservationMenu m1 = new ReservationMenu(reservations, filename);
		m1.menu();
	}
}