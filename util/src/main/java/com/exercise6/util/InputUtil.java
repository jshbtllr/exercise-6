package com.exercise6.util;
import java.util.Scanner;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.text.ParseException;

public class InputUtil {
	public static int inputOptionCheck(Integer maxValue) {
		Scanner userInput = new Scanner(System.in);
		String inputValue = new String();
		Integer output = new Integer(0);

		while (true){
			inputValue = userInput.nextLine();
			
			try{
				output = Integer.parseInt(inputValue);
				
				if ((output < 1) || (output > maxValue)) {
					System.out.print("Input invalid. Choose another: ");
				} else {
					break;
				}

			} catch (NumberFormatException ne) {
				System.out.print("Input not a number, please provide another: ");
			}
		}

		return output;
	}

	public static String getRequiredInput() {
		Scanner userInput = new Scanner(System.in);
		String output = userInput.nextLine();

		while(true) {
			if(output.isEmpty()) {
				System.out.print("Input is blank. Input another value: ");
				output = userInput.nextLine();
			} else {
				break;
			}
		}
		return output;
	}

	public static String getOptionalInput() {
		Scanner userInput = new Scanner(System.in);
		String output = userInput.nextLine();

		return output;	
	}

	public static Date getDate() {
		Date date = null;
		Boolean flag = true;
		String input = new String();
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
		format.setLenient(false);

		Scanner userInput = new Scanner(System.in);
		input = userInput.nextLine();

		while(true) {
			try {
				flag = true;
				date = format.parse(input);
			} catch(ParseException pe) {
				flag = false;
				System.out.print("Date or format incorrect. Input another: ");
				input = userInput.nextLine();
			}

			if (flag) {
				break;
			}
		}

		return date;
	}

	public static Boolean getStatus() {
		Scanner userInput = new Scanner(System.in);
		String input = new String();
		Boolean output = false;

		input = userInput.nextLine();

		while(true) {
			if(input == "Y"){
				output = true;
				break;
			} else if (input == "N") {
				break;
			} else {
				System.out.print("Input not Y or N. Input another value: ");
				input = userInput.nextLine();
			}
		}

		return output;
	}

	public static Float getGrade() {
		Scanner userInput = new Scanner(System.in);
		String input = new String();
		Float grade;

		while (true){
			input = userInput.nextLine();
			
			try{
				grade = Float.parseFloat(input);
				
				if ((grade <= 0)) {
					System.out.print("Input invalid. Choose another: ");
				} else {
					break;
				}

			} catch (NumberFormatException ne) {
				System.out.print("Input not a number, please provide another: ");
			}
		}

		return grade;
	}	
}






