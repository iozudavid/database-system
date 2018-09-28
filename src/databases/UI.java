package databases;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class UI {

	private Scanner scanner;
	private Connection conn;
	
	public UI(Connection conn){
		this.conn    = conn;
		this.scanner = new Scanner(System.in);
	}
	
	//to avoid rewriting code
	//by creating 2 types of queries
	public ResultSet getResultFromTable(int cid, String whatToRetrieve, String fromWhereToRetrieve, String whichIsTheCondition){
		try{
			PreparedStatement query;	
			query = conn.prepareStatement(""
				+ "SELECT " + whatToRetrieve
				+ " FROM  "+ fromWhereToRetrieve
				+ " WHERE " + whichIsTheCondition + "?"
				);
			query.setInt(1, cid);
				ResultSet rs = query.executeQuery();
				return rs;
		}catch(SQLException e){
			System.out.println(e.getMessage());
		}
		return null;
	}
	
	public ResultSet getResultCombiningTables(int cid, String whatToRetrieve, String fromWhereToRetrieve, String whichIsTheCondition, String table, String whichIsTheCondition2){
		try{
			PreparedStatement query;	
			query = conn.prepareStatement(""
				+ "SELECT " + whatToRetrieve
				+ " FROM  " + fromWhereToRetrieve
				+ " WHERE " + whichIsTheCondition + " = (SELECT T1." + whichIsTheCondition
				+ "                                     FROM "+ table + " T1"
				+ "                                     WHERE T1." + whichIsTheCondition2 + " = ?)"
				);
			query.setInt(1, cid);
			ResultSet rs = query.executeQuery();
			return rs;
		}catch(SQLException e){
			System.out.println(e.getMessage());
		}
		return null;
	}
			
	//give information about a cracker
	private void reportCracker(String cid) {
		try{
			//used to post a nice message if no such cracker found
			boolean checkIt = false;
			int cidNumber = 0;
					
			try{
				cidNumber = Integer.parseInt(cid);
			}catch(NumberFormatException e){
				System.out.println("Id must be a valid number");
				return;
			}
			
			//retrieve id and name
			ResultSet rsIdAndName = this.getResultFromTable(cidNumber, "cid, name", "Cracker", "cid=");
			//null means bad format
			if(rsIdAndName == null){
				return;
			}
			System.out.println("\n==========CRACKER " + cid + "==========");
			while (rsIdAndName.next()){
				checkIt = true;
				System.out.println("id: " + rsIdAndName.getInt(1));
				System.out.println("name: " + rsIdAndName.getString(2));
			}
			if(!checkIt){
				System.out.println("Ups. No such cracker in table.");
				System.out.println("==========/CRACKER " + cid + "==========\n");
				return;
			}
	
			//retrieve gift
			ResultSet rsGift = this.getResultCombiningTables(cidNumber, "description", "Gift", "gid", "Cracker", "cid");
			while (rsGift.next()){ 
				System.out.println("gift description: "+rsGift.getString(1));
			}
			
			//retrieve joke
			ResultSet rsJoke = this.getResultCombiningTables(cidNumber, "joke", "Joke", "jid", "Cracker", "cid");
			while (rsJoke.next()){ 
				System.out.println("joke: "+rsJoke.getString(1));
			}
	
			//retrieve hat
			ResultSet rsHat = this.getResultCombiningTables(cidNumber, "description", "Hat", "hid", "Cracker", "cid");
			while (rsHat.next()){ 
				System.out.println("hat description: "+rsHat.getString(1));
			}
			
			//saleprice
			ResultSet rsSalePrice = this.getResultFromTable(cidNumber, "saleprice", "Cracker", "cid=");
			float salePrice = 0.0f;
			while (rsSalePrice.next()){
				salePrice = rsSalePrice.getFloat(1);
				System.out.println("saleprice: " + rsSalePrice.getFloat(1));
			}
			
			//costprice
			ResultSet rsJokePrice = this.getResultCombiningTables(cidNumber, "royalty", "Joke", "jid", "Cracker", "cid");
			ResultSet rsGiftPrice = this.getResultCombiningTables(cidNumber, "price",   "Gift", "gid", "Cracker", "cid");
			ResultSet rsHatPrice  = this.getResultCombiningTables(cidNumber, "price",   "Hat",  "hid", "Cracker", "cid");
			float jokePrice = 0.0f;
			float giftPrice = 0.0f;
			float hatPrice  = 0.0f;
			while (rsJokePrice.next())
				jokePrice = rsJokePrice.getFloat(1);
			while(rsGiftPrice.next())
				giftPrice = rsGiftPrice.getFloat(1);
			while(rsHatPrice.next())
				hatPrice  = rsHatPrice.getFloat(1);
			float costPrice = jokePrice + giftPrice + hatPrice;
			String finalCostPrice = String.format("%.2f", costPrice);
			System.out.println("costprice: " + finalCostPrice);
			
			//profit
			float netProfit = salePrice - costPrice;
			String finalNetProfit = String.format("%.2f", netProfit);
			System.out.println("netprofit: " + finalNetProfit);
			
			//mark the end of information
			System.out.println("==========/CRACKER " + cid + "==========\n");
			
		}catch(SQLException e){
			System.out.println(e.getMessage());
		}
		
	}
	
	public void reportJoke(String jid) {
		try{
			
			boolean checkIt = false;
			int jidNumber = 0;
			
			try{
				jidNumber = Integer.parseInt(jid);
			}catch(NumberFormatException e){
				System.out.println("Id must be a valid number");
				return;
			}
			
			//retrieve id and joke
			ResultSet rsIdAndJoke = this.getResultFromTable(jidNumber, "jid, joke", "Joke", "jid=");
			//null means bad format
			if(rsIdAndJoke == null)
				return;
			System.out.println("\n==========JOKE " + jid + "==========");
			while (rsIdAndJoke.next()){ 
				checkIt = true;
				System.out.println("id: " + rsIdAndJoke.getInt(1));
				System.out.println("joke: " + rsIdAndJoke.getString(2));
			}
			if(!checkIt){
				System.out.println("Ups. No such joke in table.");
				System.out.println("==========/JOKE " + jid + "==========\n");
				return;
			}
			
			//retrieve royalty
			ResultSet rsIdAndName = this.getResultFromTable(jidNumber, "royalty", "Joke", "jid=");
			float royalty = 0.0f;
			while (rsIdAndName.next()){ 
				royalty = rsIdAndName.getFloat(1);
			}
			System.out.println("royalty: " + royalty);
			
			//number of use
			ResultSet rsNumberOfUse = this.getResultFromTable(jidNumber, "COUNT(jid)", "Cracker", "jid=");
			int numberOfUse = 0;
			while (rsNumberOfUse.next()){
				numberOfUse = rsNumberOfUse.getInt(1);
			}
			System.out.println("number of use: " + numberOfUse);
			
			//total royalty paid
			System.out.println("total royalty paid: " + (royalty*numberOfUse));
			
			//mark the end of information
			System.out.println("==========/JOKE " + jid + "==========\n");
		}catch(SQLException e){
			System.out.println(e.getMessage());
		}
		
	}
	
	public void insertCracker(String id, String name, String jokeId, String giftId, String hatId, String saleprice, String quantity) {
		int idNumber   = 0;
		int jokeNumber = 0;
		int giftNumber = 0;
		int hatNumber  = 0;
		float salePriceNumber = 0.0f;
		int quantityNumber    = 0;
		
		try{
			idNumber   = Integer.parseInt(id);
			jokeNumber = Integer.parseInt(jokeId);
			giftNumber = Integer.parseInt(giftId);
			hatNumber  = Integer.parseInt(hatId);
		}catch(NumberFormatException e){
			System.out.println("All ids must be valid numbers");
			return;
		}
		
		try{
			salePriceNumber = Float.parseFloat(saleprice);
		}catch(NumberFormatException e){
			System.out.println("Sale price must be a valid number.");
			return;
		}
		
		try{
			quantityNumber = Integer.parseInt(quantity);
		}catch(NumberFormatException e){
			System.out.println("Quantity number must be a valid number.");
			return;
		}
		
		try{
			PreparedStatement addStatement = conn.prepareStatement(""
					+ "INSERT INTO Cracker VALUES(?,?,?,?,?,?,?)");
			addStatement.setInt(1, idNumber);
			addStatement.setString(2, name);
			addStatement.setInt(3, jokeNumber);
			addStatement.setInt(4, giftNumber);
			addStatement.setInt(5, hatNumber);
			addStatement.setFloat(6, salePriceNumber);
			addStatement.setInt(7, quantityNumber);
			addStatement.executeUpdate();
		}catch(SQLException e){
			System.out.println(e.getMessage());
			return;
		}
		System.out.println("Cracker "+id+" inserted successfully.");
	}
	
	//help functionality for users
	public String help(){
		StringBuilder helpBuilder = new StringBuilder();
		helpBuilder.append("\nHi! Here you have a list of commands: \n");
		helpBuilder.append("\"report cracker\" - produce a report for a cracker\n");
		helpBuilder.append("\"report joke\"    - produce a report for a joke\n");
		helpBuilder.append("\"insert cracker\" - insert a new cracker in table\n");
		helpBuilder.append("\"reset\"          - delete, create and populate again all tables\n");
		helpBuilder.append("\"quit\"           - exit the program");
		return helpBuilder.toString();
	}
	
	public void reset(){
		try{
			CreateAndPopulate reset = new CreateAndPopulate(conn);
			reset.deleteAllTables();
			reset.createAllTables();
			reset.populateAllTables();
		}catch(SQLException e){
			System.out.println(e.getMessage());
			return;
		}
		System.out.println("Database fully restored.");
	}
	
	public void start(Connection conn){
		System.out.println("Welcome back to dxi695 database");
		//first check the tables
		System.out.println("Everything seems to work fine!");
		System.out.println("Database is set up and ready for future commands.");
		System.out.println("As you probably know, we have \"help\" command to show command list.");
		String line = "";
		System.out.print("Next command> ");
		while(!(line = scanner.nextLine()).equals("quit")){
			
			switch(line){
				case "report cracker" : System.out.print("Cracker's id> ");
										line = scanner.nextLine();
										reportCracker(line);
										break;
				case "report joke"    : System.out.print("Joke's id> ");
										line = scanner.nextLine();
										reportJoke(line);
										break;
				case "insert cracker" : System.out.print("Cracker's id> ");
										String newId = scanner.nextLine();
										System.out.print("Cracker's name> ");
										String newName = scanner.nextLine();
										System.out.print("Joke's id> ");
										String newJokeId = scanner.nextLine();
										System.out.print("Gift id> ");
										String newGiftId = scanner.nextLine();
										System.out.print("Hat id> ");
										String newHatId = scanner.nextLine();
										System.out.print("Sale price> ");
										String newSalePrice = scanner.nextLine();
										System.out.print("Quantity> ");
										String newQuantity = scanner.nextLine();
										insertCracker(newId, newName, newJokeId, newGiftId, newHatId, newSalePrice, newQuantity);
										break;
				case "reset"		  : System.out.print("Are you sure you want to reset the database? y/n > ");
										String response = scanner.nextLine();
										if(response.equals("y")){
											System.out.println("Database restoring... Please wait.");
											reset();
										}
										else
											System.out.println("Abort...");
										break;
				case "help"           : System.out.println(help());
										break;
				default				  : System.out.println("Ups... Command not found.");
						 				System.out.println("Type \"help\" for more informations.\n");
						 				break;
				
			}
			System.out.print("\nNext command> ");
			
		}
		System.out.println("Goodbye!");
	}
	
	public static void main(String[] args){
		//default values
		String username = "yourUsername";
		String password  = "yourPassword";
		String URL      = "yourURL";
		//if 3 arguments then change default values for database
		if(args.length == 3){
			username = args[0];
			password = args[1];
			URL = args[2];
		}
		Connection conn = TryConnection.connectToDB(username, password, URL);
		UI ui = new UI(conn);
		ui.start(conn);
	}
	
}
