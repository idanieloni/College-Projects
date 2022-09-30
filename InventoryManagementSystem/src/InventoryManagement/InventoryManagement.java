package InventoryManagement;
import java.util.Map;
import java.util.Scanner;
import java.util.LinkedHashMap;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;



public class InventoryManagement {

    public static void main(String[] args) throws IOException {
        String inputPword, itName, str = null;
        double itCost;
        int itCount, itID;
        boolean exit = false;
        boolean endProgram = false;
        boolean authenticated = false;
        int attempts = 4;
        int selection;
        boolean found = false;
        Scanner input = new Scanner(System.in);
        Item[] items = new Item[10];
        
        
        while (endProgram != true) {
        	itCost = 0;
        	itCount = 0;
        	itID = 0;
        	itName = null;
        	
	        System.out.println("------- Main Menu----- \n"
	                + "Choose an option: \n"
	                + "1. Admin \n"
	                + "2. User \n"
	                + "3. Exit"
	        );
	        
	        selection = menuInputValidation(3, str, input);
	        input.nextLine();
	
	        switch (selection) {
	
	        // Admin User
	
	        case 1: {
	        		exit = false;
		            File file = new File("adminpassword.txt");
		            
		            while (authenticated == false && exit !=true)
		            {
		            	System.out.println("Enter admin password: ");
		                inputPword = input.nextLine();
		                authenticated = readPassword(file, inputPword);
		                if (authenticated == false) {
		                    attempts -= 1;
		                    System.out.println("Incorrect password. " + attempts + " attempts left.");
		                } 
		                
		                else {
		                        System.out.println("Login success! \n");
		                    }
		                if (attempts < 1) {
		                    System.out.println("***** Exiting Admin menu *****");
		                    exit = true;
		                }
		            }
		            while ( exit != true) {
		            System.out.println("\n------- Admin Menu----- \n"
		                    + "Choose an option: \n"
		                    + "1.  Change password \n"
		                    + "2.  Add new item \n"
		                    + "3.  Search and update item \n"
		                    + "4.  Search and delete \n"
		                    + "5.  Exit"
		            );
		            selection = menuInputValidation(5, str, input);
		            input.nextLine();
		            switch (selection) {
		
		            //Change password
		            case 1: {
		            	FileWriter writer;
		                writer = null;
		                try {
		                    writer = new FileWriter("adminpassword.txt", false);
		                } catch (IOException e) {
		                    e.printStackTrace();
		                }
		                PrintWriter outputFile = new PrintWriter(writer);
		            	System.out.println("New password must be 4 or more characters.");
		            	String text = " ";
		            	while(text.length()< 4) {
		            		System.out.println("New password:");
		            		text = input.nextLine();
		            		if (text.length()< 4) {
		            			System.out.println("Password must be 4 or more characters");
		            		
		            		}
		            		else {
		            			System.out.println("Password Changed!\n");
		            			outputFile.println(text);
				                outputFile.close();
				                text = " ";
				                break;
				
		            		}
		            	}
		            	
		                
		                
		            }
		            break;
		
		
		            // Add new item
		            case 2: {
			            	System.out.println("Enter item name: ");
			            	itName = input.nextLine();
			            	System.out.println("Enter item cost: ");
			            	itCost = input.nextDouble();
			            	input.nextLine();
			            	System.out.println("Enter item stock: ");
			            	itCount = input.nextInt();
			            	input.nextLine();
			            	itID =  (int) Files.lines(Paths.get("items.txt")).count();
			            	Item item   = new Item(itID, itName, itCost, itCount);
			            	int newCount = item.getItemCount();
			            	String itemtoWrite= Item.stringMap(item, newCount);
			            	Item.writeItem(itemtoWrite);
		            }
		            	
		            break;
		            
		            case 3 :{
		            	System.out.println("Enter search term:");
						String term = input.nextLine();
						String result = Item.search(term); 
						if (result.equals("Item not found")) {
							;
						}
						else {
		            	String answer = "y";
		            	System.out.println("Update an item? Y:Yes, any other key: No ");
		            	answer = input.nextLine();
		            	while(answer.equals("y") || answer.equals("Y")) {
							System.out.println("Enter id of item you want to change");
			            	itID = input.nextInt();
			            	input.nextLine();
			            	System.out.println("Enter item name: ");
			            	itName = input.nextLine();
			            	System.out.println("Enter item cost: ");
			            	itCost = input.nextDouble();
			            	input.nextLine();
			            	System.out.println("Enter item stock: ");
			            	itCount = input.nextInt();
			            	input.nextLine();
			            	Item item   = new Item(itID, itName, itCost, itCount);
			            	String itemtoWrite = Item.stringMap(item, item.getItemCount());
			            	Item.replaceItem(itID, itemtoWrite);
			            	System.out.println("Replaced item");
			            	System.out.println("Replace another item? Y:Yes, any other key: No");
			            	answer = input.nextLine();
		            	}
						}
						

		            }
		            	
		            break;
		            
		            case 4:{
		            	System.out.println("Enter search term:");
						String term = input.nextLine();
						String result = Item.search(term); 
						if (result.equals("Item not found")) {
							;
						}
						else {
		            	String answer = "y";
		            	System.out.println("Delete an item? Y:Yes, any other key: No ");
		            	answer = input.nextLine();
		            	while(answer.equals("y") || answer.equals("Y")) {
		            		System.out.println("Enter id of item you want to delete");
			            	itID = input.nextInt();
			            	Item.replaceItem(itID, "");
			            	
		            		
		            	}
		            	
		            }
		            }
		            
		            
		            
		            
		            
		            case 5 :{
		            	System.out.println("***** Exiting Admin menu *****\n");
			        	exit = true;
			
			            }
			        }
			  }
		     }
	        break;
	        

	
	        //-----------------------------------------------------
	
	
	        // User Menu
	        case 2: {
	        	exit = false;
	            while(exit!=true) {
		            System.out.println("\n------- User Menu----- \n"
		                    + "Choose an option: \n"
		                    + "1. Search items \n"
		                    + "2. View cart \n"
		                    + "3. Delete item from cart\n"
		                    + "4. View and Place order \n" 
		                    + "5. Exit (will delete items from cart)"
		            );
		            selection = menuInputValidation(6, str, input);
		            input.nextLine();
		            int id = 0;
		            switch (selection) {
						case 1: {
							
							found = false;
							System.out.println("Enter search term:");
							String term = input.nextLine();
							Item.search(term);
							System.out.println("Do you want to add item to cart? Y:Yes, any other key: No ");
							String answer = input.nextLine();
							if (answer.equals("Y") || answer.equals("y")) {
								System.out.println("Selct the id of th idem you want to add to cart");
								int idToCart = input.nextInt();
								input.nextLine();
								String searchString = "ID:"+String.valueOf(idToCart);
								System.out.println(searchString);
								LinkedHashMap<String, String> dataSet = Item.readItems(Item.search(searchString));
								
									itID = Integer.parseInt(dataSet.get("ID"));
								
								if (dataSet .containsKey("Name") ) {
									itName = dataSet.get("Name");
								}
								if (dataSet .containsKey("Count") ) {
									itCount = Integer.parseInt(dataSet.get("Count"));
								}
								if (dataSet .containsKey("Price")  ) {
									itCost = Double.parseDouble(dataSet.get("Price"));
								}
								Item item = new Item(itID, itName, itCost, itCount);
								System.out.println(item.getItemName() + "Added");
								for (int i = 0; i < items.length; i++) {
		                            if (items[i] == null) {
		                                items[i] = item;
		                                found = true;
		                                id+=1;
		                                break;
		                            }
		                        }
								if (found == false) {
									System.out.println("Cannot add more than 10 items");
								}
								
							}
							else {
								System.out.println("annnna");
							}
						}
						break;
						
						case 2:{
							int numItems = 0;
							System.out.println("\nYour order:");
							double total = 0;
							for (Item item: items) {
	                            if (item != null) {
	                                System.out.println(item.getItemName() + ": $"+ item.getCostTax());
	                                total += item.getCostTax();
	                                found = true;
	                            }
	                        }
							if (total > 1) {
								System.out.println("Total: "+total);
							}
	                        if (found == false) {
	                            System.out.println("No items have been added");
	                        }
	                        
	                    }
						break;
						
						case 3:{
							System.out.println("Enter ID of item to remove");
							int id1 =  menuInputValidation(10, str, input);
							for (int i = 0; i < items.length; i++) {
	                            if (items[i] != null && items[i].getItemID() == id1) {
	                                System.out.println(" Item deleted");
	                                items[i] = null;
	                                found = true;
	                                break;
	                            }
							
							}
							
						}
						break;
						
						case 4:{
							System.out.println("Your order:");
							double total = 0;
							for (Item item: items) {
	                            if (item != null) {
	                                System.out.println(item.getItemName() + ": $"+ item.getCostTax());
	                                total += item.getCostTax();
	                                found = true;
	                                String updateCount = Item.stringMap(item, item.getItemCount()-1);
	                                Item.replaceItem(item.getItemID(), updateCount);
	                            }
	                        }
							if (total > 1) {
								
								System.out.println("Total: "+total);
							}
	                        if (found == false) {
	                            System.out.println("No items have been added");
	                        }
	                        
	                    }
						break;
						
						case 5:{
							System.out.println("***** Cart emptied. Exiting user menu *****\n");
							exit = true;
							
						}
						break;
				
		            }
	            }
	        }
	       break;
	
	        // End program
	        case 3: {
	        	System.out.println("***** Program ended *****");
	            endProgram = true;
	        }
	        //----------------------------------------------------
	
	        }
	}
}


        // Read orders
        public static boolean readPassword (File file, String pass) {
        	boolean authenticated = false;

	            Scanner scanner = null;
	            try {
	                scanner = new Scanner(file);
	            } 
	            catch (FileNotFoundException e) {
	                e.printStackTrace();
	            }
	            
	            while (scanner.hasNext()) {
	                String userpass = scanner.nextLine();
	                if (pass.equals(userpass)) {
	                    authenticated = true;
	                } 
	                
	            }
           
            return authenticated;
        }


        public static int menuInputValidation( int numOptions, String str, Scanner input){
           int selection = 0;
           
           while (selection < 1 || selection > numOptions) {
        	   str = input.next();
                try {
                    selection = Integer.parseInt(str);
                    if (selection >= 1 && 3 >= selection) {
                        break;
                    }
                    if (selection > numOptions) {
                        throw new IllegalArgumentException("'" + selection + "' is invalid. Enter a number from 1 - " + numOptions);
                    }
                    if (selection < 1) {
                        throw new IllegalArgumentException("Zero and Negative numbers not allowed.\nEnter a number from 1 - " + numOptions);
                    }
                    continue;
                } catch (NumberFormatException e) {
                    System.out.println("Non-integer entered. Enter a number from 1 -" + numOptions);
                    selection = 0;
                    continue;
                } catch (IllegalArgumentException e) {
                    System.out.println(e.getMessage());
                    selection = 0;
                    continue;
                }
            }
            return selection;
        }
}

