package InventoryManagement;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Item  {
	/**
	 * 
	 */
	private int itemID;
	private String itemName;
	private double itemCost;
	private int itemCount;
	private static String path = "items.txt";
	String mapString;

	public Item(int id, String i,  double t, int c) {
		setID(id);
		setName(i);
		setCount(c);	
		setCost(t);
		}




	public  void setCount(int t) {
		itemCount = t;
	}
	public void setName(String i) {
		itemName = String.valueOf(i);
	}
	public void setID(int id) {
		itemID = id;
	}
	public void setCost(double c) {
		itemCost = c;
	}
	
	
	
	public int getItemID() {
		return itemID;
	}
	
	public String getItemName() {
		return itemName;
	}
	
	public double getItemCost() {
		return itemCost;
	}
	
	public int getItemCount() {
		return itemCount;
	}
	
	public double getCostTax() {
		double tax = this.itemCost* 0.7;
		double itemCostTxed = this.itemCost +tax;
		return itemCostTxed;
	}
	
	public static void writeItem(String str) {
		
		try {
		BufferedWriter writer = new BufferedWriter(new FileWriter(path, true));
		writer.write(str);
		writer.newLine();
		writer.flush();
		writer.close();
		}
		catch(IOException e){
	            e.printStackTrace();
	    }
	}
	
	public static String stringMap(Item i, int newCount) {
		LinkedHashMap<String, String> itemProps =  new LinkedHashMap<String, String>();
		itemProps.put("ID", String.valueOf(i.getItemID())+" ");
		itemProps.put("Name", String.valueOf(i.getItemName())+" ");
		itemProps.put("Price", String.valueOf(i.getItemCost())+" ");
		itemProps.put("Count", String.valueOf(newCount));
		
		
		StringBuilder sb = new StringBuilder();

		itemProps.forEach((key, value) -> {
			
			try {
				sb.append(key + ":" + value + " ");
			} 
			catch (Exception e) {
				e.printStackTrace();
			}
		
		});
		return sb.toString();
		
	}

	
	public static LinkedHashMap<String, String> readItems(String s){
		LinkedHashMap<String, String> myMap = null;
	        String line = s;
	        if (line != null) {
				myMap = new LinkedHashMap<String, String>();
				String[] pairs = line.split("  ");
				for (int i=0;i<pairs.length;i++) {
				    String pair = pairs[i];
				    String[] keyValue = pair.split(":");
				    myMap.put(keyValue[0], keyValue[1]);
				   
				}

	        } 

        return myMap;
	}
	public static String search(String word) throws IOException {
		int counter2 = 0; 
		String pattern = "\\b"+word+"\\b";
	    Pattern patt = Pattern.compile(pattern);
	    BufferedReader r = new BufferedReader(new FileReader(path));
	    String line = null;
	    while ((line = r.readLine()) != null) {
	      Matcher m = patt.matcher(line);
	      while(m.find() == true) {
	        System.out.println("\n" +line+ "\n");
	        counter2+=1;
	        return line;
	      }
	      
	      }
	    if(counter2<1) {
	    		System.out.println("Item not found");
	    	  return ("Item not found");
	    	  
	    }
	    return line;
	  }


	
	public static void replaceItem(int lineNumber, String data) throws IOException {
		Path paths = Paths.get(path);
	    List<String> line = Files.readAllLines(paths, StandardCharsets.UTF_8);
	    line.set(lineNumber - 1, data);
	    Files.write(paths, line, StandardCharsets.UTF_8);
	}
}
