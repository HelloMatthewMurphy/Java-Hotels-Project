//Name: Matthew Murphy
//ID: 15147193

import java.util.*;
import java.io.*;

/**
*	An object that displays the menu for reservations
*/
public class ReservationMenu extends Menu{
	
	private boolean done;
	private Scanner in;
	//private static ArrayList <Reservation> reservations = new ArrayList <Reservation>();
	//private static ArrayList <Hotel> hotels = new ArrayList <Hotel>();
	private static int resNum = 0;
	private String fileName;
	private String [][] fileArray;

	/**
	*	Creates the ReservationMenu object
	*	@Param Arraylist of reservations
	*	@Param File name that is reads the hotels and rooms from
	*/
	public ReservationMenu(ArrayList <Reservation> reservations, String fileName) throws IOException{
		this.done = false;
		this.in = new Scanner(System.in);
		this.reservations = reservations;
		try{
			this.fileArray = loadTable("l4Hotels.csv");
			sortArray();
		}
		catch(IOException exc){
			System.out.println(exc);
		}
	}
	
	/**
	*	Displays the login menu
	*/
	public void menu(){
		String resFile = "Reservations.csv";
		try{
			loadReservationsFile(resFile);
		}
		catch(IOException ex){

		}
		int typeOfUser;
		while (!done)
		{ 
			System.out.println("\nLogin:\n1)Supervisor\n2)Desk Personal\n3)Customer\nQ)Quit");
			String command = in.nextLine().toUpperCase();
			if (command.equals("1")){  
				if(login(1)){
					System.out.print("Correct!\n");
					booking(1);
				}
				else
					System.out.print("Wrong!\n");
			}
			else if (command.equals("2")){
				if(login(2)){
					System.out.print("Correct\n");
					booking(2);
				}
				else
					System.out.print("Wrong!\n");
			}
			else if (command.equals("3")){  
				System.out.print("Welcome\n");
				booking(3);
			}
			 
			else if (command.equals("Q")){ 
				done = true;
			}
			else{
				System.out.print("Invalid input. Please try again.");
			}
		}
	}

	/**
	*	Displays the login menu
	*/
	protected boolean login(int type){
		Scanner in = new Scanner(System.in);
		String passwords [] = {"Pass1", "Pass2"};
		boolean correct = false;
		String outPut = "Enter Password for ";
		if(type == 1){
			outPut += "Supervisor: ";
		}
		else if(type == 2){
			outPut += "Desk Personal: ";
		}
		System.out.println("Password: ");
		String password = in.nextLine();
		if(password.matches(passwords[type -1]))
			correct = true;
		return correct;
	}
	
	/**
	*	Displays booking menu
	*/
	protected void booking(int priority){
		//System.out.print("Your priority is " + priority);
		boolean done = false;
		String output = "\nPlease make a selection:\n\n";
		
		if(priority <= 3){
			output += "1)Make Reservation\n2)Cancle Reservation\n";
			if(priority <= 2){
				output += "3)Check-in\n4)Check-out\n";
				if(priority == 1){
					output += "5)Apply Discount\n6)Analytics\n";
				}
			}
		}
		output += "Q)Quit\n";
		while(!done){
			System.out.print(output);
			String command = in.nextLine();
			try{
				if (command.equals("1")){  
					System.out.print("Make Reservation");
					makeReservation();
					
				}
				else if (command.equals("2")){
					editBoolOfRes(0);
				}
				else if (command.equals("3") && priority <=2){
					editBoolOfRes(1);
				}
				else if (command.equals("4") && priority <= 2){
					editBoolOfRes(2);
				}
				else if (command.equals("5") && priority == 1){
					applyDiscount();
				}
				else if (command.equals("6") && priority == 1){
					System.out.println("Please enter name for Analytics");
					String aName = in.nextLine();
					AnalyticsMenu m2 = new AnalyticsMenu("Analytics.csv", aName);
					m2.menu();
				}
				else if (command.equalsIgnoreCase("Q")){
					done = true;
				}
				else{
					System.out.print("Incorrect input");
				}
			}
			catch (IOException exc){
				System.out.println("Error making reservation: " + exc);
			}
		}
	}

	/**
	*	Gets all information from user to make reservation
	*/
	protected void makeReservation() throws IOException{
		String resName;
		String resType = "";
		GregorianCalendar checkInDate = new GregorianCalendar();
		int noOfNights;
		int noOfRooms = 0;
		ArrayList <Room> rooms = new ArrayList <Room>();
		int noOfAdults = 0;
		int noOfChildren = 0;
		String occupants = "";
		boolean breakfast = false;
		double cost;
		double deposit = 50;

		//Enter Name
		System.out.print("Enter Reservation Name:\n");
		resName = in.nextLine();
		boolean doneType = false;
		//Enter Type and Date
		while(!doneType){
			System.out.println("Enter Reservation Type:\n\tS - Standard\n\tAP - Advanced Purchase");
			String tempType = in.nextLine().toUpperCase();
			if(tempType.equals("S")){
				resType = tempType;
				doneType = true;
				//System.out.println(checkInDate.get(Calendar.DAY_OF_MONTH) + "/" + checkInDate.get(Calendar.MONTH) + "/" + checkInDate.get(Calendar.YEAR));
			}
			else if(tempType.equals("AP")){
				resType = tempType;
				doneType = true;
				checkInDate = pickDate(checkInDate);
				//System.out.println(checkInDate.get(Calendar.DAY_OF_MONTH) + "/" + checkInDate.get(Calendar.MONTH) + "/" + checkInDate.get(Calendar.YEAR));
			}
			else
				System.out.println("Not valid Reservation type");
		}
		//Enter number of nights
		System.out.print("Enter the number of nights you want to stay: ");
		noOfNights = Integer.parseInt(in.nextLine());

		//Printing rooms from Hotel of choice
		//Parameters for hotels: 5: 2-6 4: 6-9 3:9-12
		int hotelNum = 0;
		printTable(fileName, -1);
		boolean finishedHotel = false;
		String [] hotelName = new String [hotels.size()];
		for(int i = 0; i < hotels.size() ; i++){
			hotelName[i] = hotels.get(i).getName();
			System.out.print((i + 1) + ") " + hotelName[i] + "\n");
		}
		while(!finishedHotel){
			System.out.println("What hotel would you like to stay in?: ");
			hotelNum = Integer.parseInt(in.nextLine());
			if(hotelNum > hotels.size() || hotelNum < 0){
				System.out.println("Error: Number too out of bounds");
			}
			else{
				hotelNum--;
				finishedHotel = true;
			}
		}
		printTable(fileName, hotelNum);

		//Picking rooms
		boolean finishedRooms = false;
		while(!finishedRooms){
			noOfRooms++;
			//printTable(fileArray, fileName, hotelNum);
			System.out.println("Please select a room: ");
			boolean doneQ = false;
			int amountOfRooms = hotels.get(hotelNum).getRoomSize();
			String [] roomNames = new String [amountOfRooms];
			Arrays.fill(roomNames, null);
			doneQ = false;
			Room pickedRoom = hotels.get(hotelNum).getRoom(0);
			for(int i = 0; i < amountOfRooms ; i++){
				roomNames[i] = hotels.get(hotelNum).getRoom(i).getName();
				if(hotels.get(hotelNum).getRoom(i).getNoLeft() > 0)
					System.out.print((i + 1) + ") " + roomNames[i] + "\n");
				else
					System.out.print((i + 1) + ") " + roomNames[i] + " **;None left**\n");
			}
			while(!doneQ){
				int roomNumSelected = Integer.parseInt(in.nextLine());
				if(roomNumSelected > amountOfRooms || roomNumSelected < 0){
					System.out.println("Error: Not valid selection");
				}
				else{
					pickedRoom = hotels.get(hotelNum).getRoom(roomNumSelected - 1);
					//hotels.get(hotelNum).getRoom(roomNumSelected - 1).takeOne();
					doneQ = true;
				}
			}

			//Adding Occupancy
			doneQ = false;
			String occupancyTemp = pickedRoom.getOccupancy()[1]; //Getting max
			String [] occupancyMax = occupancyTemp.split("\\+");
			int adultAndChild [] = new int [occupancyMax.length];
			for(int i = 0; i < occupancyMax.length; i++){
				adultAndChild [i] = Integer.parseInt(occupancyMax[i]);
			}
			while(!doneQ){
				System.out.println("How many adults are staying in this room?");
				noOfAdults = Integer.parseInt(in.nextLine());
				System.out.println("How many children are staying in this room?");
				noOfChildren = Integer.parseInt(in.nextLine());
				if(noOfAdults < 1)
					System.out.println("Error: Must have at least 1 adult");
				else if(noOfAdults > adultAndChild[0] || noOfChildren > adultAndChild [1])
					System.out.println("Error: Too many people.");
				else
					doneQ = true;

			}
			rooms.add(pickedRoom);

			//Checking if they want to book more rooms
			System.out.println("Do you want to book another? (y/n)");
			doneQ = false;
			while(!doneQ){
				String tempFinRoom = in.nextLine().toUpperCase();
				if(tempFinRoom.equals("Y")){
					finishedRooms = false;
					doneQ = true;
				}
				else if(tempFinRoom.equals("N")){
					finishedRooms = true;
					boolean breakfastQ = false;
					while(!breakfastQ){
						System.out.println("Would you like breakfast to be included with your stay? (y/n)");
						String breakfastAns = in.nextLine().toUpperCase();
						if(breakfastAns.equals("Y")){
							breakfast = true;
							breakfastQ = true;
						}
						else if(breakfastAns.equals("N")){
							breakfast = false;
							breakfastQ = true;
						}
						else
							System.out.println("Error: y or n only please");
					}
					doneQ = true;
				}
				else
					System.out.println("Error: y or n only please");
			}
		}
		reservations.add(new Reservation(resNum++, resName, resType, checkInDate,
						noOfNights, noOfRooms, rooms, breakfast, deposit, false, false));
		printToReservationFile();
		addRemoveRooms(reservations.get(reservations.size() - 1).getRooms(), -1);
		printTableToFile();
	}

	/**
	*	Edits booleans in reservation
	*/
	protected void editBoolOfRes(int type)throws IOException{
		System.out.println("Please enter Reservation number:");
		int resNumCheck = Integer.parseInt(in.nextLine());
		boolean done = false;
		for(int i = 0; i < reservations.size() || !done; i++){
			if(resNumCheck == reservations.get(i).getResNum()){
				if(type == 0){
					reservations.get(i).cancel();
					//Date 48Hours = new Date(System.currentTimeMillis() - TimeUnit.HOURS.toMillis(48));
					//calendar.get(Calendar.DAY_OF_YEAR);  
					GregorianCalendar todaysDate = new GregorianCalendar();
					if(reservations.get(i).getType().equals("S") && ((todaysDate.get(Calendar.DAY_OF_YEAR) - reservations.get(i).getDate().get(Calendar.DAY_OF_YEAR)) <= 2)){
						System.out.println("Refund Given.");
					}
					else
						System.out.println("Refund not given.");
					addRemoveRooms(reservations.get(i).getRooms(), 1);
				}
				else if(type == 1)
					reservations.get(i).checkIn();
				else{
					reservations.get(i).checkOut();
					addRemoveRooms(reservations.get(i).getRooms(), 1);
				}
				done = true;
			}
		}
		printToReservationFile();
		printTableToFile();
	}

	/**
	*	adds or removes rooms to total room count
	*/
	protected void addRemoveRooms(ArrayList <Room> rooms, int addBack){
		for(int i = 0; i < rooms.size(); i++){
			if(addBack > 1)
				rooms.get(i).addOne();
			else
				rooms.get(i).takeOne();
			String roomNameTemp = rooms.get(i).getName();
			//System.out.println(fileArray[1].length);
			for(int j = 0; j < fileArray[1].length; j++){
				if(roomNameTemp.equals(fileArray[1][j]))
					fileArray[2][j] = String.valueOf(Integer.parseInt(fileArray[2][j]) + addBack);
			}
		}
	}

	/**
	*	Applys a discount to a specific reservation
	*/
	protected void applyDiscount()throws IOException{
		int resNumCheck = 0;
		boolean doneResNum = false;
		while(!doneResNum){
			System.out.println("Please enter Reservation number:");
			resNumCheck = Integer.parseInt(in.nextLine());
			if(resNumCheck < 0 || resNumCheck > reservations.size())
				System.out.println("Error: Please use proper reservation number");
			else
				doneResNum = true;
		}
		double discount = 0.0;
		boolean correctPrecentage = false;
		while(!correctPrecentage){
			System.out.println("Please enter dicount precent:");
			discount = Double.parseDouble(in.nextLine());
			if(discount > 1)
				System.out.println("Value too large. 0.5 = 50%");
			else
				correctPrecentage = true;
		}
		boolean done = false;
		for(int i = 0; i < reservations.size() || !done; i++){
			if(resNumCheck == reservations.get(i).getResNum()){
				double costTemp = reservations.get(i).getCost() * discount;
				reservations.get(i).setCost(reservations.get(i).getCost() - costTemp);
				done = true;
			}
		}
		printToReservationFile();
	}

	/**
	*	Allows user to pick date
	*/
	protected GregorianCalendar pickDate(GregorianCalendar checkInDate){
		int  date [] = {1,1,2016};
		boolean doneDate = false;
		boolean doneQ = false;
		boolean dateCorrect = false;
		while(!doneDate){
			while(!dateCorrect){
				System.out.print("Please enter day: ");
				date [0] = Integer.parseInt(in.nextLine());
				System.out.print("Please enter month: ");
				date [1] = Integer.parseInt(in.nextLine());
				System.out.print("Please enter year: ");
				date [2] = Integer.parseInt(in.nextLine());
				if(checkDate(date [0], date [1], date [2]))
					dateCorrect = true;
				else
					System.out.println("Date is not correct. Make sure its not in the past and the dates exist.");
			}
			//checkInDate.set(date[0], date[1], date[2]);
			checkInDate = convertToDate(date);
			//System.out.println("Is this date ok?(y/n");
			//System.out.println(checkInDate.get(Calendar.DAY_OF_MONTH) + "/" + checkInDate.get(Calendar.MONTH) + "/" + checkInDate.get(Calendar.YEAR));
			//String tempResponce = in.nextLine().toUpperCase();
			doneQ = false;
			while(!doneQ){
				System.out.println("Is this date ok?(y/n)\n" + checkInDate.get(Calendar.DAY_OF_MONTH) + "/" + (checkInDate.get(Calendar.MONTH) + 1) + "/" + checkInDate.get(Calendar.YEAR));
				String tempResponce = in.nextLine().toUpperCase();
				if(tempResponce.equals("Y")){
					doneDate = true;
					doneQ = true;
				}
				else if(tempResponce.equals("N")){
					doneDate = false;
					doneQ = true;
				}
				else
					System.out.println("Incorrect Input. Only type \"y\" or \"n\" ");
			}
		}
		//System.out.println(checkInDate.get(Calendar.DAY_OF_MONTH) + "/" + checkInDate.get(Calendar.MONTH) + "/" + checkInDate.get(Calendar.YEAR));
		return checkInDate;
	}

	/**
	*	Converts int array to date
	*/
	protected GregorianCalendar convertToDate (int [] dates){
		GregorianCalendar date = new GregorianCalendar();
		date.set(GregorianCalendar.DAY_OF_MONTH, dates[0]);
		date.set(GregorianCalendar.MONTH, dates[1] - 1);
		date.set(GregorianCalendar.YEAR, dates[2]);
		return date;
	}

	/**
	*	Checks the date is correct
	*/
	protected boolean checkDate(int d, int m, int y){
		GregorianCalendar todaysDate = new GregorianCalendar();
		int [] daysInMonth = {31,28,31,30,31,30,31,31,30,31,30,31};
		boolean leapYear = ((y % 4 == 0) && (y % 100 != 0) || (y % 400 == 0));
		String result = "";
		boolean correct = true;
		if(leapYear)
			daysInMonth[1]++;
		if(m > 12 || m < 0)
			correct = false;
			//result += "Incorrect month: (1-12)\n";
		else if(d > daysInMonth[m-1] || d < 0)
			correct = false;
			//result += "Incorrect day: (1-" + daysInMonth[m-1] + ")\n";
		else if(d < todaysDate.get(Calendar.DAY_OF_MONTH) && m < todaysDate.get(Calendar.MONTH) && y < todaysDate.get(Calendar.YEAR))
			correct = false;
			//result += "Date is in the past\n";
		//System.out.println(correct);
		return correct;
	}

	/**
	*	Loads hotels file to 2D array
	*/
	protected String[][] loadTable(String fileName)throws IOException{
		int [] xAndY = new int [2];
		try{
			countTable(xAndY, fileName);
		}
		catch(IOException exc){
			System.out.println(exc);
		}
		//System.out.println(xAndY[0] + " " + xAndY[1]);
		String [][] fileArray = new String [xAndY[0]][xAndY[1]];
		FileReader fileIn = new FileReader(fileName);
		BufferedReader lineReader = new BufferedReader(fileIn);
		String nextLine = lineReader.readLine();
		for(int i = 0;(nextLine) != null || i < xAndY[1]; i++){
			String [] tempLine = nextLine.split(",");
			//System.out.println(tempLine.length);
			for(int j = 0; j < tempLine.length; j++){
				if(tempLine[j] == null)
					tempLine[j] = " ";
				fileArray [j][i] = tempLine [j];
			}
			nextLine = lineReader.readLine();
		}
		//System.out.println(fileArray[0][0]);
		fileIn.close();
		lineReader.close();
		return fileArray;
	}

	/**
	*	Counts the x and y for the 2D table
	*/
	protected void countTable(int [] xAndY, String fileName)throws IOException{
		FileReader fileIn = new FileReader(fileName);
		BufferedReader lineReader = new BufferedReader(fileIn);
		String tempLine = lineReader.readLine();
		while((tempLine) != null){
			xAndY [1]++;
			String [] getX = tempLine.split(",", -1);
			xAndY [0] = getX.length;
			tempLine = lineReader.readLine();
		}
		//System.out.println(xAndY[0] + " " + xAndY[1]);
		fileIn.close();
		lineReader.close();
	}

	/**
	*	Prints hotels table to screen
	*/
	protected void printTable(String fileName, int hotelNum)throws IOException{
		if(hotelNum >= 0){
			System.out.println(hotels.get(hotelNum).toString());
		}
		else{
			for(int i = 0; i < hotels.size(); i++){
				System.out.println(hotels.get(i).toString());
			}
		}
	}

	/**
	*	Prints hotels table back to file
	*/
	protected void printTableToFile()throws IOException{
		FileWriter fileOut = new FileWriter("l4Hotels.csv");
		PrintWriter printWriter = new PrintWriter(fileOut);
		System.out.println(fileArray[0][0]);
		for(int i = 0; i < fileArray[1].length; i++){
			for(int j = 0; j < fileArray.length; j++){
				if(fileArray[j][i] != null)
					printWriter.print(fileArray[j][i] + ",");
			}
			if(i != fileArray.length - 1)
				printWriter.print("\n");
		}
		printWriter.close();
		fileOut.close();
	}

	/**
	*	Sorts the hotels table into Hotel objects and room objects
	*/
	protected void sortArray(){
		int hotelCount = -1;
		for(int i = 2; i < fileArray[1].length; i++){
			if(fileArray[0][i].contains("star")){
				hotelCount++;
				hotels.add(new Hotel(fileArray[0][i]));
				String [] tempStringArray = {fileArray[3][i],fileArray[4][i]};
				double [] tempDoubleArray = {Double.parseDouble(fileArray[5][i]), Double.parseDouble(fileArray[6][i]),
											Double.parseDouble(fileArray[7][i]), Double.parseDouble(fileArray[8][i]),
											Double.parseDouble(fileArray[9][i]), Double.parseDouble(fileArray[10][i]),
											Double.parseDouble(fileArray[11][i])};
				hotels.get(hotelCount).addRoom(fileArray[1][i], Integer.parseInt(fileArray[2][i]), tempStringArray, tempDoubleArray);
			}
			else{
				String [] tempStringArray = {fileArray[3][i],fileArray[4][i]};
				double [] tempDoubleArray = {Double.parseDouble(fileArray[5][i]), Double.parseDouble(fileArray[6][i]),
											Double.parseDouble(fileArray[7][i]), Double.parseDouble(fileArray[8][i]),
											Double.parseDouble(fileArray[9][i]), Double.parseDouble(fileArray[10][i]),
											Double.parseDouble(fileArray[11][i])};
				hotels.get(hotelCount).addRoom(fileArray[1][i], Integer.parseInt(fileArray[2][i]), tempStringArray, tempDoubleArray);
			}
		}
	}
 
 	/**
	*	Prints reservations to reservations file
	*/
	protected void printToReservationFile()throws IOException{
		FileWriter fileOut = new FileWriter("Reservations.csv");
		PrintWriter printWriter = new PrintWriter(fileOut);
		String firstLine = "ResID,Name,Type,Check-In Date,no. Nights,no. Rooms,Breakfast,Cost,Deposit,Checked In,Checked Out,Cancled,Refunded,Rooms,";
		printWriter.print(firstLine);
		for(int i = 0; i < reservations.size(); i++){
			printWriter.print("\n");
			printWriter.print(reservations.get(i).toString());
			for(int j = 0; j < reservations.get(i).getRooms().size(); j++){
				printWriter.print(reservations.get(i).getRooms().get(j).getName() + ",");
			}
		}
		printWriter.close();
		fileOut.close();
	}

	/**
	*	Loads reservation file to reservations
	*/
	protected void loadReservationsFile(String fileName)throws IOException{
		FileReader fileIn = new FileReader(fileName);
		BufferedReader lineReader = new BufferedReader(fileIn);
		String tempLine = lineReader.readLine();
		GregorianCalendar todaysDate = new GregorianCalendar();
		while(tempLine != null){
			String [] tempLineParts = tempLine.split(",");
			if(!(tempLineParts[0].equalsIgnoreCase("ResID")) && tempLineParts.length >= 12){
				int numberForLoad = 0;
				int resNumTemp = Integer.parseInt(tempLineParts[numberForLoad++]);
				resNum = resNumTemp + 1;
				String resNameTemp = tempLineParts[numberForLoad++];
				String resTypeTemp = tempLineParts[numberForLoad++];
				GregorianCalendar checkInDateTemp = new GregorianCalendar();
				String [] dateStringTemp = tempLineParts[numberForLoad++].split("/");
				int [] dateIntTemp = new int [dateStringTemp.length];
				for(int i = 0; i < dateStringTemp.length; i++){
					dateIntTemp[i] = Integer.parseInt(dateStringTemp[i]);
				}
				checkInDateTemp = convertToDate(dateIntTemp);
				int noOfNightsTemp = Integer.parseInt(tempLineParts[numberForLoad++]);
				int noOfRoomsTemp = Integer.parseInt(tempLineParts[numberForLoad++]);
				boolean breakfastTemp = Boolean.parseBoolean(tempLineParts[numberForLoad++]);
				double costTemp = Double.parseDouble(tempLineParts[numberForLoad++]);
				double depositTemp = Double.parseDouble(tempLineParts[numberForLoad++]);
				boolean checkedInTemp = Boolean.parseBoolean(tempLineParts[numberForLoad++]);
				boolean checkedOutTemp = Boolean.parseBoolean(tempLineParts[numberForLoad++]);
				boolean canceledOutTemp = Boolean.parseBoolean(tempLineParts[numberForLoad++]);
				boolean refundedTemp = Boolean.parseBoolean(tempLineParts[numberForLoad++]);


				ArrayList <Room> roomsTemp = new ArrayList <Room>();
				for(int i = 0; i < hotels.size(); i++){
					for(int j = 0; j < hotels.get(i).getRoomSize(); j++){
						for(int k = 0; k < noOfRoomsTemp; k++){
							if(tempLineParts[numberForLoad + k].matches(hotels.get(i).getRoom(j).getName()))
								roomsTemp.add(hotels.get(i).getRoom(j));
							}
						}
				}

				reservations.add(new Reservation(resNumTemp, resNameTemp, resTypeTemp, checkInDateTemp,
												noOfNightsTemp, noOfRoomsTemp, roomsTemp, breakfastTemp, depositTemp, checkedInTemp, checkedOutTemp, canceledOutTemp, refundedTemp));
			
			}
			tempLine = lineReader.readLine();
		}
	}
}