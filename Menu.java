//Name: Matthew Murphy
//ID: 15147193

import java.util.*;
import java.io.*;

/**
*	An abstract bject for a menu
*/
public abstract class Menu{
	protected static ArrayList <Reservation> reservations = new ArrayList <Reservation>();
	protected static ArrayList <Hotel> hotels = new ArrayList <Hotel>();

	public abstract void menu();
}