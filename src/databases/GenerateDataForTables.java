package databases;

import java.util.Random;

public class GenerateDataForTables {

	//real data
	private String[] realCrackers = {"Arnott''s Shapes",
						             "Bremner Wafer",
						             "Captain''s Wafers",
						             "Carr''s",
						             "Cheddars",
						             "Cheese Nips",
						             "CheezIt",
						             "Club Crackers",
						             "Crown Pilot Crackers",
						             "Goldfish",
						             "In a Biskit",
						             "Jacob''s Oddities",
						             "Le Snak",
						             "Pepperidge Farm",
						             "Premium Plus",
						             "Rice Thins",
						             "Ritz Crackers",
						             "Ry-Krisp",
						             "Ryvita",
						             "SAO",
						             "Triscuit",
						             "Shoon Fatt",
						             "TUC",
					                 "Vegetable Thins",
						            };
		
	private String[] realJokes    = {"Q: What goes up and down but does not move? A: Stairs", 
  						             "Q: Where should a 500 pound alien go? A: On a diet", 
						             "Q: What did one toilet say to the other? A: You look a bit flushed.",
						             "Q: Why did the picture go to jail? A: Because it was framed.", 
						             "Q: What did one wall say to the other wall? A: I''ll meet you at the corner.", 
						             "Q: What did the paper say to the pencil? A: Write on!", 
						             "Q: What do you call a boy named Lee that no one talks to? A: Lonely", 
						             "Q: What gets wetter the more it dries? A: A towel.", 
						             "Q: Why do bicycles fall over? A: Because they are two-tired!",
						             "Q: Why do dragons sleep during the day? A: So they can fight knights!", 
						             "Q: What did Cinderella say when her photos did not show up? A: Someday my prints will come!", 
						             "Q: Why was the broom late? A: It over swept!", 
						             "Q: What part of the car is the laziest? A: The wheels, because they are always tired!", 
						             "Q: What did the stamp say to the envelope? A: Stick with me and we will go places!", 
						             "Q: What is blue and goes ding dong? A: An Avon lady at the North Pole!", 
						             "Q: We''re you long in the hospital? A: No, I was the same size I am now!", 
						             "Q: Why couldn''t the pirate play cards? A: Because he was sitting on the deck!", 
						             "Q: What did the laundryman say to the impatient customer? A: Keep your shirt on!", 
						             "Q: What''s the difference between a TV and a newspaper? A: Ever tried swatting a fly with a TV?", 
						             "Q: What did one elevator say to the other elevator? A: I think I''m coming down with something!", 
						             "Q: Why was the belt arrested? A: Because it held up some pants!", 
						             "Q: Why was everyone so tired on April 1st? A: They had just finished a March of 31 days.", 
						             "Q: Which hand is it better to write with? A: Neither, it''s best to write with a pen!",
						             "Q: Why can''t your nose be 12 inches long? A: Because then it would be a foot!"
						            };
	
	private String[] realGiftDesc = {"Is about as attention-grabbing as they come",
									 "These are at the top of every kid''s wish list.",
									 "Meet this year''s Hatchimals. Your kids are going to be asking for the whole set.",
									 "For toddlers up to teens, these are the most fun (and functional) picks.",
									 "We''ll always have a soft spot in our hearts for 1985 Barbie.",
									 "Forget \"the basics.\" These picks give new parents what they really need.",
									 "Make sure you''re not wrapping these up this holiday season.",
									 "These picks will keep baby safe and your hands free.",
									 "Check your model number immediately.",
									 "Stock up now before they fly off shelves.",
									 "Spoiler alert: It''s double the fun.",
									 "A nifty umbrella stroller that is great for travel.",
									 "An amazingly innovative stroller for travelers or those who live in a city.",
									 "Bring the force home.",
									 "Don''t you wish the Candy Cane Forest was real?",
									 "Stocking up for the fall won''t break the bank.",
									 "The \"It\" stroller in many urban dwellings, the Uppababy Vista stroller is a highly functional, aesthetic, and durable pick.",
									 "Looking good, Ken!",
									 "The newest it-toy among middle schooler kids is more controversial than you may think.",
									 "Sleepy parents everywhere will love this, too."
								    };
	
	private String[] realHatDesc  = {"Lightweight, durable, good sun protection",
									 "Good protection, comfortable, good in wind, breathable",
									 "Total protection, removable cape, good in the wind, good value",
									 "Great protection all around, especially on the back; brim is angled for longer protection, good ventilation, comfortable",
									 "Extremely durable, comfortable, stylish",
									 "Super stylish",
									 "Great for backpacking",
									 "Great protection all around, especially on the back",
									 "Comfortable",
									 "Lots of colors to choose from",
									 "Good in wind",
									 "Clean and classic white",
									 "Navy-burgundy colour combo",
									 "A combination of cellulose and viscose",
									 "Popular street wear piece",
									 "Keeping cool in the summer heat",
									 "Classic style",
									 "Balance coverage with visibility.",
									 "Rigid in the wind",
									 "Protecting the face from the sun"			
									};
	
	
	
	public GenerateDataForTables(){
		//nothing here...
	}
	
	//good to use for both price and royalty
	//return a string which is in fact a float between 1.00f and 1000.00f
	//which has exactly 2 decimals
	private String generateRandomPrice(){
		Random random     = new Random();
		float min         = 1.0f;
		float max         = 1000.0f;
		float genPrice    = min + (max - min)*(random.nextFloat());
		String finalPrice = String.format("%.2f", genPrice);
		finalPrice        = finalPrice.replace(',', '.');
		return finalPrice;
	}
	
	//can be used for generating id for joke, hats and gifts for cracker table
	//return a string which is in fact an integer between 1 and 150
	private String generateRandomId(){
		Random random     = new Random();
		int id            = 1+ random.nextInt(150);
		String finalId    = "" + id;
		return finalId;
	}
	
	//reuse generateRandomId() and substract 1
	//as the quantity can be 0
	private String generateRandomQuantity(){
		String randomValue = generateRandomId();
		int value          = Integer.parseInt(randomValue);
		value--;
		String quantity    = "" + value;
		return quantity;
	}
	
	//construct the array to be ready to use
	//e.g. : (1,'example of string',245.23)
	public String[] generateAllJokes(){
		String[] jokes = new String[150];
		
		//first put the real data
		for(int i = 0; i<realJokes.length; i++){
			jokes[i]   = "(" + (i+1) + ",'" + realJokes[i] + "'," + generateRandomPrice() + ");";
		}
		//generate synthetic data
		for(int i = realJokes.length; i<150; i++){
			jokes[i]   = "(" + (i+1) + ",'" + "Joke " + i + "'," + generateRandomPrice() + ");";
		}
		return jokes;
	}
	
	//construct the array to be ready to use
	//e.g. : (1,'example of string',245.23)
	public String[] generateAllHats(){
		String[] hats = new String[150];
		
		//first put the real data
		for(int i = 0; i<realHatDesc.length; i++){
			hats[i] = "(" + (i+1) + ",'" + realHatDesc[i] + "'," + generateRandomPrice() + ");";
		}
		
		//generate synthetic data
		for(int i = realHatDesc.length; i<150; i++){
			hats[i] = "(" + (i+1) + ",'" + "Hat " + i + "'," + generateRandomPrice() + ");";
		}
		
		return hats;
	}
	
	//construct the array to be ready to use
	//e.g. : (1,'example of string',245.23)
	public String[] generateAllGifts(){
		String[] gifts = new String[150];
		
		//first put the real data
		for(int i = 0; i<realGiftDesc.length; i++){
			gifts[i] = "(" + (i+1) + ",'" + realGiftDesc[i] + "'," + generateRandomPrice() + ");";
		}
		
		//generate synthetic data
		for(int i = realGiftDesc.length; i<150; i++){
			gifts[i] = "(" + (i+1) + ",'" + "Gift " + i + "'," + generateRandomPrice() + ");";
		}
		
		return gifts;
	}
	
	//construct the array to be ready to use
	//e.g. : (1,'example of string',12,27,135,245.23,2)
	public String[] generateAllCrackers(){
		String[] crackers = new String[1200];
		
		//first put real data
		for(int i = 0; i<realCrackers.length; i++){
			crackers[i] = "(" + (i+1) + ",'" + realCrackers[i] + "'," + generateRandomId() + "," + generateRandomId() + "," + 
								generateRandomId() + "," + generateRandomPrice() + "," + generateRandomQuantity() + ");";
		}
		
		for(int i = realCrackers.length; i<1200; i++){
			crackers[i] = "(" + (i+1) + ",'" + "Cracker " + i + "'," + generateRandomId() + "," + generateRandomId() + "," + 
					generateRandomId() + "," + generateRandomPrice() + "," + generateRandomQuantity() + ");";
		}
		
		return crackers;
	}
	
	
}
