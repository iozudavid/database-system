package databases;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class CreateAndPopulate {

	private Connection dbConn;
	private GenerateDataForTables data;
	
	public CreateAndPopulate(Connection conn){
		this.dbConn = conn;
		this.data   = new GenerateDataForTables();
	}

	//for reset use
	public void deleteAllTables() throws SQLException{
			deleteJokeTable();
			deleteGiftTable();
			deleteHatTable();
			deleteCrackerTable();
	}
	
	//for reset use
	public void createAllTables() throws SQLException{
			createJokeTable();
			createGiftTable();
			createHatTable();
			createCrackerTable();
	}
	
	//for reset use
	public void populateAllTables() throws SQLException{
			populateJokeTable();
			populateGiftTable();
			populateHatTable();
			populateCrackerTable();
	}
	
	//create all tables
	public void createJokeTable() throws SQLException{
			
			PreparedStatement jokeStatement = 
					dbConn.prepareStatement(""
							+ "CREATE TABLE Joke("
							+ "jid          INT             NOT NULL UNIQUE,"
							+ "joke         CHAR(150)       NOT NULL,"
							+ "royalty      REAL            NOT NULL,"
							+ "CONSTRAINT   jokeKey         PRIMARY KEY(jid),"
							+ "CONSTRAINT   royaltyCheck    CHECK(royalty >= 0),"
							+ "CONSTRAINT   jidCheck        CHECK(jid > 0)"
							+ ");");
			jokeStatement.executeUpdate();
			

	}
	
	public void createGiftTable() throws SQLException{
			
			PreparedStatement giftStatement = 
					dbConn.prepareStatement(""
							+ "CREATE TABLE Gift("
							+ "gid           INT            NOT NULL UNIQUE,"
							+ "description   CHAR(150),"
							+ "price         REAL           NOT NULL,"
							+ "CONSTRAINT    giftKey        PRIMARY KEY(gid),"
							+ "CONSTRAINT    priceCheck     CHECK (price >= 0),"
							+ "CONSTRAINT    gidCheck       CHECK (gid > 0)"
							+ ");");
			giftStatement.executeUpdate();

	}
	
	public void createHatTable() throws SQLException {
					
			PreparedStatement hatStatement = 
					dbConn.prepareStatement(""
							+ "CREATE TABLE Hat("
							+ "hid                 INT               NOT NULL UNIQUE,"
							+ "description         CHAR(150),"
							+ "price               REAL              NOT NULL,"
							+ "CONSTRAINT          hatKey            PRIMARY KEY(hid),"
							+ "CONSTRAINT          priceCheck        CHECK (price >= 0),"
							+ "CONSTRAINT          hidCheck          CHECK (hid > 0)"
							+ ");");
			hatStatement.executeUpdate();

	}
	
	public void createCrackerTable() throws SQLException{	
		
		PreparedStatement crackerStatement = 
				dbConn.prepareStatement(""
						+ "CREATE TABLE Cracker("
						+ "cid         INT            NOT NULL UNIQUE,"
						+ "name        CHAR(150)      NOT NULL,"
						+ "jid         INT            NOT NULL,"
						+ "gid         INT            NOT NULL,"
						+ "hid         INT            NOT NULL,"
						+ "saleprice   REAL           NOT NULL,"
						+ "quantity    INT            NOT NULL,"
						+ "CONSTRAINT  crackerKeyJ    FOREIGN KEY(jid) REFERENCES Joke(jid),"
						+ "CONSTRAINT  crackerKeyG    FOREIGN KEY(gid) REFERENCES Gift(gid),"
						+ "CONSTRAINT  crackerKeyH    FOREIGN KEY(hid) REFERENCES Hat(hid),"
						+ "CONSTRAINT  saleCheck      CHECK(saleprice >= 0),"
						+ "CONSTRAINT  quantityCheck  CHECK(quantity >= 0),"
						+ "CONSTRAINT  cidCheck       CHECK(cid > 0)"
						+ ");");
		crackerStatement.executeUpdate();
		
	}
	
	//delete all tables
	public void deleteJokeTable() throws SQLException{
		
		PreparedStatement jokeDelete;
		
		jokeDelete = dbConn.prepareStatement("DROP TABLE Joke CASCADE");
		jokeDelete.executeUpdate();
		
	}
	
	public void deleteGiftTable() throws SQLException{
		
		PreparedStatement giftDelete;
		
		giftDelete = dbConn.prepareStatement("DROP TABLE Gift CASCADE");
		giftDelete.executeUpdate();
		
	}
	
	public void deleteHatTable() throws SQLException{
		
		PreparedStatement hatDelete;
	
		hatDelete = dbConn.prepareStatement("DROP TABLE Hat CASCADE");
		hatDelete.executeUpdate();
		
	}

	public void deleteCrackerTable() throws SQLException{
		
		PreparedStatement crackerDelete;
		
		crackerDelete = dbConn.prepareStatement("DROP TABLE Cracker");
		crackerDelete.executeUpdate();
		
	}
	
	//populate all tables with data
	public void populateJokeTable() throws SQLException{
		
		PreparedStatement jokePopulate;
		String[] jokeData = data.generateAllJokes();
		
		for(String joke : jokeData){
			jokePopulate = dbConn.prepareStatement("INSERT INTO Joke VALUES" + joke);
			jokePopulate.executeUpdate();
		}
		
	}
	
	public void populateGiftTable() throws SQLException{
		PreparedStatement giftPopulate;
		String[] giftData = data.generateAllGifts();
		
		for(String gift : giftData){
			giftPopulate = dbConn.prepareStatement("INSERT INTO Gift VALUES" + gift);
			giftPopulate.executeUpdate();
		}
	}
	
	public void populateHatTable() throws SQLException{
		PreparedStatement hatPopulate;
		String[] hatData = data.generateAllHats();
		
		for(String hat : hatData){
			hatPopulate = dbConn.prepareStatement("INSERT INTO Hat VALUES" + hat);
			hatPopulate.executeUpdate();
		}
	}
	
	public void populateCrackerTable() throws SQLException{
		
		PreparedStatement crackerPopulate;
		String[] crackerData = data.generateAllCrackers();
		
		for(String cracker : crackerData){
			crackerPopulate = dbConn.prepareStatement("INSERT INTO Cracker VALUES" + cracker);
			crackerPopulate.executeUpdate();
		}
	}

}
