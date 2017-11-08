package choHan;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class UserInterface {
	public void UserImput() { // For initilization puropses
	}
	
	public String getString() { // Get imput from user
		BufferedReader consoleInput = new BufferedReader(new InputStreamReader(System.in));
		String input = "";
		try {
			input = consoleInput.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return(input);
	}
	
	public Double getDouble(String failMessage) { // Get imput from user
		BufferedReader consoleInput = new BufferedReader(new InputStreamReader(System.in));
		String input = "";
		try {
			input = consoleInput.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		boolean isNumeric = false;
		do {
			isNumeric = true;
			try  
			{  
				Double.parseDouble(input);
				//d = d + 1; // so it doesn't warn me about it not being used
			}  
			catch(NumberFormatException nfe)  
			{  
				isNumeric = false;  
			}  
			if(input.isEmpty()) {
				isNumeric = false;
			}
			if(isNumeric == false) {
				System.out.println(failMessage);
			}
		} while (isNumeric == false);
		return(Double.parseDouble(input));
	}
	
	public int getInt(String failMessage) { // Get imput from user
		BufferedReader consoleInput = new BufferedReader(new InputStreamReader(System.in));
		String input = "";
		try {
			input = consoleInput.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		boolean isNumeric = false;
		do {
			isNumeric = true;
			try  
			{  
				Integer.parseInt(input);
				//d = d + 1; // so it doesn't warn me about it not being used
			}  
			catch(NumberFormatException nfe)  
			{  
				isNumeric = false;  
			}  
			if(input.isEmpty()) {
				isNumeric = false;
			}
			if(isNumeric == false) {
				System.out.println(failMessage);
			}
		} while (isNumeric == false);
		return(Integer.parseInt(input));
	}
	
	public String niceNum(long num) {
		boolean foundLegnth = false;
		long sizeMult = 1000;
		long mod = 0;
		String output = "";
		String tempOut = "";
		
		do {
			mod = num % sizeMult;
			if(num / sizeMult < 1) {
				foundLegnth = true;
				output = String.valueOf(mod) + output;
			} else {
				tempOut = String.valueOf(mod);
				while(tempOut.length() < 3) {
					tempOut = "0" + tempOut;
				}
				output = "," + tempOut + output;
				num = (num - mod) / sizeMult;
			}
		} while (foundLegnth == false);
		
		return output;
	}
	
	public String niceNum(double num) {
		boolean foundLegnth = false;
		double sizeMult = 1000;
		double mod = 0;
		String output = "";
		String tempOut = "";
		
		do {
			mod = num % sizeMult;
			if(num / sizeMult < 1) {
				foundLegnth = true;
				output = String.valueOf(mod) + output;
			} else {
				tempOut = String.valueOf(mod);
				while(tempOut.length() < 3) {
					tempOut = "0" + tempOut;
				}
				output = "," + tempOut + output;
				num = (num - mod) / sizeMult;
			}
		} while (foundLegnth == false);
		
		return output;
	}
	
	public String niceNum(double num, int dec) {
		boolean foundLegnth = false;
		double sizeMult = 1000;
		double mod = 0;
		String output = "";
		String tempOut = "";
		
		// big numbers
		do {
			mod = num % sizeMult;
			if(num / sizeMult < 1) {
				foundLegnth = true;
				output = String.valueOf(mod) + output;
			} else {
				tempOut = String.valueOf(mod);
				while(tempOut.length() < 3) {
					tempOut = "0" + tempOut;
				}
				output = "," + tempOut + output;
				num = (num - mod) / sizeMult;
			}
		} while (foundLegnth == false);
		
		if(dec > 0) {
			output = output + ".";
		}
		//small numbers
		for(int i = 0; i < dec; i++) {
			num = num * 10;
			mod = num % 1;
			output = output + String.valueOf((int) mod);
			num = num - mod;
		}
		
		return output;
	}
	
	// Print text
	public void print(String text) {
		System.out.print(text);
	}
	
	// Print text in new line
	public void println(String text) {
		System.out.println(text);
	}
	public void sendMessage(String message) {  // For backwards compatibility
		System.out.println(message);
	}
	
	// Print numbers
	public void print(byte num) {
		System.out.print(num);
	}
	
	public void print(short num) {
		System.out.print(num);
	}
	
	public void print(int num) {
		System.out.print(num);
	}
	
	public void print(long num) {
		System.out.print(num);
	}
	
	public void print(float num) {
		System.out.print(num);
	}
	
	public void print(double num) {
		System.out.print(num);
	}
	
	// Print numbers on new lines
	public void println() {
		System.out.println();
	}
	
	public void println(byte num) {
		System.out.println(num);
	}
	
	public void println(short num) {
		System.out.println(num);
	}
	
	public void println(int num) {
		System.out.println(num);
	}
	
	public void println(long num) {
		System.out.println(num);
	}
	
	public void println(float num) {
		System.out.println(num);
	}
	
	public void println(double num) {
		System.out.println(num);
	}
	
	public boolean isNumeric(String str)  { // Find out if a string only contains numbers and spaces
		try  
		{  
			double d = Double.parseDouble(str);
			d = d + 1;
		}  
		catch(NumberFormatException nfe)  
		{  
			return false;  
		}  
		if(str.isEmpty()) {
			return false;
		}
	return true;
	}
}
