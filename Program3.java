/**
 * File:		Program3.java
 * Author:		Annie Wu
 * Class:		CS 241 - Data Structures and Algorithms II
 * 
 * Assignment:		Program 3
 * Date:		28 February 2018
 * 
 * Purpose:		This file contains user interactions.
 * 
 */

import java.util.*;

public class Program3 {

	private static String choices = "\n 1. Insert a value" +
					  "\n 2. Delete a value" +
					  "\n 3. Return count of leaves" +
					  "\n 4. Return all values in the tree between a and b" +
					  "\n 5. Delete the first 20 entries" +
					  "\n 6. Exit the program" +
					  "\n Command? ";
	private static String insertValue = "What value would you like to insert? ";
	private static String deleteValue = "What value would you like to delete? ";
	private static String thankyou = "Thank you for using my program!";
	private static String invalid = "Invalid Entry. Please enter a number for a command.";
	private static String AandB = "Enter values for a and b: ";
	
	public static void main(String[] args) {
		userInterface();
	}
	
	/**
	 * - Insert a new value (ignore repetitions, values should be added to both trees)
	 * - Delete a value (values should be deleted from both trees)
	 * - Return a count of the leaves of both trees
	 * - Return a listing of all values in the tree between a and b, where a and b are input by the user.
	 * - An option for the user to delete the first 20 entries in the trees encountered by a preorder traversal if possible. 
	 *   Once completed, this option will automatically display the new height of the tree, 
	 *   and the count of the remaining leaves in both trees.
	 */
	public static void userInterface() {
		Scanner sc = new Scanner(System.in);
		BinarySearchTree bst = new BinarySearchTree();
		RedBlackTree rbt = new RedBlackTree();
		Random random = new Random();
		//for 100 randomly generated numbers
		for(int j = 0; j < 100; j++) {
			//+1 so it eliminates the possibility of getting the number 0
			int number = random.nextInt(140) +1;
			
			//while the number exists in the tree, 
			//get a new random number
			while (bst.exists(number)) {
				number = random.nextInt(140) +1;
			}
			bst.insert(number);
			rbt.add(number);
		}
		
		boolean exit = false;
		while (exit == false) {
			try {
				System.out.print(choices);          
				String choice = sc.next().toUpperCase();
				switch (choice) {
					case "1": 
						System.out.print(insertValue);
						int value = sc.nextInt();
						bst.insert(value);
						rbt.add(value);
						break;
		            case "2":
		            	System.out.print(deleteValue);
						value = sc.nextInt();
						bst.delete(value);
						rbt.remove(value);
						break;                         
		            case "3":
		                bst.getNumberOfLeaves();
		                rbt.getNumberOfLeaves();
		            	break;
		            case "4":
		            	System.out.print(AandB);
		            	int a = sc.nextInt();
		            	int b = sc.nextInt();
		            	bst.printInRange(a, b);
		            	break;
		            case "5":
		            	bst.delete20();
		            	rbt.delete20();
		            	break;
					case "6":
						System.out.println(thankyou);
						exit = true;
						break;   
					default:
						System.out.println(invalid);
				}
			}
			catch (InputMismatchException e) {
				System.out.println(invalid);				
			}
           
		}
		sc.close();
	}
}
