import java.util.ArrayList;
import java.util.Scanner;

public class CPSC331_Ass2 {

	// input strings (numbers)
	public static String num1, num2;
	
	// carry value
	public static short carry;
	
	/* 
	* Different data type arrays, Character to hold chars
	* of the inputs
	* and String to hold strings (imm. calculations)
	* without and with pre-zeroes 
	*/ 
	public static ArrayList<Character> num1List, num2List; 
	public static ArrayList<String> partNumsList, totList;
	
	// counter for the loops (could also be initialized in
	// the loops)
	public static short counter, counter2;
		
		// Main function where everything is calculated
		public static void main(String[] args) {	
			// Importing the Scanner for user input
			Scanner inp = new Scanner(System.in);		
			
			
			/////////////////////////////////////////////////////////////////////
			////////////////////// S1 - Entering 2 numbers //////////////////////
			/////////////////////////////////////////////////////////////////////
			
			/*
			===== Precondition: =====
			* Two strings that are at least length of 1 and at most 32,767 characters
			* The entered number must be an element of Natural Numbers, >1
			* Restrictions are decimal numbers {0,...,9}
			===== Postcondition: =====
			* Print/+Ask for the strings and get inputs from the user
			* Keep the user inputs in two different strings that are to be used later
			*/
			
			// User inputs their desired two decimal values (0-9 and . can be entered)
			System.out.println("Enter your first decimal number");
			num1 = inp.nextLine();
			System.out.println("Enter your second decimal number");
			num2 = inp.nextLine();
			
			//////////////////////////////////////////////////////////////////////
			//////////////// 	S2 - Setting up the arrays,       ////////////////
			//////////////// 	initialization and calculations   ////////////////
			//////////////////////////////////////////////////////////////////////
			
			/*
			===== Precondition: =====
			* 	Global variables and the inputs from S1
			* 	Length of the strings are kept in short variables 
			* 	Dynamic Arrays created with the input sizes and the elements are kept
			* as characters which are obtained by the inputs
			* 	2 new Dynamic arrays with the size 32767 (max amount intermediate calculations
			* to be hold)- these hold calculations to be used for intermediate and total calculation
			* 	Stings to hold the temporary calculations and to be added to the arrays
			===== Postcondition: =====
			* 	Calculate lengths of the arrays
			* 	Fill up the arrays
			* 	Intermediate calculations and the product result which will be calculated
			* by using those
			* 	(Specific postconditions/descriptions will be commented on top of everything 
			*/
			
			// Calculate the lengths of the inputs so that you can create a dynamic array
			// with the size equal to the string and use it for the loop when adding them
			short list1Length = (short) num1.length();
			short list2Length = (short) num2.length();
			
			// Initialize the arrays and fill them up with the characters of the inputs
			num1List = new ArrayList<Character>(list1Length);
			for (counter = 0; counter < list1Length; counter++){
				num1List.add(num1.charAt(counter));
			}
			num2List = new ArrayList<Character>(list2Length);
			for (counter = 0; counter < list2Length; counter++){
				num2List.add(num2.charAt(counter));
			}
			

			/*
			 * Initialize the dynamic arrays with the size 32767
			 * (Since the cost of initializing is O(1), we can set it as high as possible
			 * and it will still cost the same, so we will set it to the max it requires
			 * to hold and not worry about it again)
			*/
			partNumsList = new ArrayList<String>(32767);
			totList = new ArrayList<String>(32767);
			
			
			// Strings to hold the temporary calculations and power of 10's
			String tempMult = "";
			String leadingZeroes = "";
			
			// Set the carry value to 0
			carry = 0;
			
			/*
			 * 	This algorithm has a nested for loop, that has 2 separate inner for loops
			 * Outer loop runs the second input's length times and then the inner loop2 runs,
			 * the first input's length times (input is set inside the array but I am using
			 * the term "input"). It's done this way so that the second input's number can be 
			 * multiplied with all the numbers in the first input without changing, then when
			 * all the calculations are made, it will move on to the next number (last-1)st 
			 * number of the second input and do all the calculations and so on.
			 * 	The first inner loop checks if the number is on 1's column or 10's column or etc.
			 * And depending on which column it is, it will multiply (concatenate) by the needed
			 * power of 10.
			 * 	In the end, we check if the calculated number is greater than 9, meaning it will
			 * have a carry, we just set the last character in the temporary multiplication string
			 * and set the first character to carry.
			 * 	When the immediate calculation string is complete (all the numbers in the first
			 * input are multiplied with the number in the second input) add it to the dynamic
			 * array which will keep the immediate calculations
			 */
			for (counter = 0; counter < list2Length; counter++){
				// Check if you need to multiply (concatenate) it by the power of 10 (if != rowLast)
				for (short zeroCounter = 0; zeroCounter < counter; zeroCounter++){
					leadingZeroes += "0";
				}
				// Do the multiplication (+carry), number from the second input doesn't change until it moves
				// onto the next outer loop
				for (counter2 = 0; counter2 < list1Length; counter2++){
					short temp = (short) (Character.getNumericValue(num2List.get(list2Length - (counter + 1)))
										* Character.getNumericValue(num1List.get(list1Length - (counter2 + 1)))
										+ carry	);
					// check if you will have a carry, and if it's not the last calculation
					if(temp > 9 && counter2 != list1Length-1){
						tempMult = Short.toString(temp).substring(1,2) + tempMult;
						carry = Short.parseShort((Short.toString(temp).substring(0,1)));
					} else {				
						tempMult = Short.toString(temp) + tempMult;
						carry = 0;	
					}
				}				
				// add the temporary calculation to the dynamic array
				partNumsList.add(tempMult+leadingZeroes);
				// set them back to ""
				tempMult = "";
				leadingZeroes = "";
			}
			
			// Strings to hold the pre-zeroes for alignment and
			// a temporary "calculation" which can be used to apply the above on
			String preZeroes = "";
			String tempVal = "";
			
			/*
			 * 	Here we are adding the pre-zeroes to align the numbers when doing the addition
			 * One problem that may occur here is that the second immediate multiplication
			 * doesn't necessarily need extra 0, imagine 55*19 = 495 + 550, they have the same
			 * length so we can just add extra zero every time it's not the last immediate 
			 * calculation result
			 * 	thisManyTimes checks the length difference between the last(greatest) value and the 
			 * current value so that it will tell the algorithm if any pre-zeroes are needed or not
			 * 	The pre-zeroes are going to be added then the string is going to be added to our 
			 * dynamic array which has the same property as the 3rd dynamic array which holds the 
			 * immediate calculations, but with pre-zeroes included
			 */
			
			
			
			for (counter = 0; counter < partNumsList.size(); counter++){
				// check the length difference
				short thisManyTimes = (short) (partNumsList.get(partNumsList.size() - 1).length()
											 - partNumsList.get(partNumsList.size() - (counter + 1)).length());
				// add the needed pre-zeroes depending on the difference
				for (counter2 = 0; counter2 < thisManyTimes; counter2++){
					preZeroes += "0";
				}
				
				// concatenate pre-zeroes with the number
				tempVal = preZeroes + partNumsList.get(partNumsList.size() - (counter + 1));
				
				// add it to the array and set those back to "" and 0
				totList.add(tempVal);
				preZeroes = "";
				thisManyTimes = 0;
			}
			
			/* Short values to hold the length of the greatest number (since all aligned now),
			 * number of elements in the list and temporary calculation for the sum of the nums
			 * And also a string which will keep hold the total sum of the temp sums
			*/ 
			short bigLength = (short) totList.get(0).length();	// index=0 because same legnth now
			short numOfElms = (short) totList.size();
			short tempSum	= 0;
			String tempSumS	= "";
			

			/*
			 * 	This algorithm here calculates the total product by adding the immediate calculations
			 * It runs the greatest num's length times and every time it runs, within the inner loop
			 * it checks every element's same specific character, starting from the last. So in every
			 * run(within the inner loop), numbers in the same column are added, and in every run of the
			 * outer loop, it moves onto the next column that is to be calculated 
			 * 	The checked numbers are added to the tempSum value and then it's checked if the 
			 * calculation's length is greater than 2 (meaning >9), then check how greater it is and get the 
			 * carry depending on that (because if we had 32k characters multiplied as our second input, we 
			 * might have a column that has 32767 9's added which will have the total of length 6 (294903)
			 * Thus instead of checking it for every single case, we will have a general tempLength value 
			 * which will check the length for us and apply it to the substring function 
			 */
			
			// carry = 0;
			for (counter = 0; counter < bigLength; counter++){
				for (counter2 = 0; counter2 < numOfElms; counter2++){
					// get the index and add that to tempSum
					short temp2 = (short) Character.getNumericValue(totList.get(numOfElms - (counter2 + 1)).charAt(bigLength - (counter + 1)));
					tempSum += temp2;
				}
				tempSum += carry;
				// calculate the length of the sum
				short tempLength = (short) Short.toString(tempSum).length();
				
				// same logic as above, with the new explanation
				if(tempSum > 9 && counter != bigLength-1){
					tempSumS = Short.toString(tempSum).substring(tempLength-1) + tempSumS;					
					carry = Short.parseShort((Short.toString(tempSum).substring(0,tempLength-1)));
				} else {				// no need for else if(temp > 9){ since same steps (add the whole temp value)
					tempSumS = Short.toString(tempSum) + tempSumS;
					carry = 0;	
				}
				tempSum = 0;
				tempLength = 0;
				
			}
			
			
			/////////////////////////////////////////////////////////////////////
			///////////////////// S3 - Print the result     /////////////////////
			/////////////////////////////////////////////////////////////////////
			
			/*
			* ===== Precondition: =====
			* tempSumS which holds the product
			* ===== Postcondition: =====
			* Print out the following:
			* 	A sentence
			* 	Outcome of the addition of the partial numbers (product)
			*/ 
			
			System.out.print("\n\nResults:\n------------------\n");
			
			System.out.printf("Product: %s \n\n", tempSumS);
			
			/////////////////////////////////////////////////////////////////////
			///////////////////// S4 - Print intrm. calcs.  /////////////////////
			/////////////////////////////////////////////////////////////////////
			
			/*
			* ===== Precondition: =====
			* partNumsList array which holds the immediate calculations
			* and it's elements that are to be printed
			* a counter and for loop to print out all the elements
			* ===== Postcondition: =====
			* Print out the following:
			* 	A sentence
			* 	Immediate products
			*/ 
			
			System.out.print("Immediate Calculations:\n");
			
			for (counter = 0; counter < partNumsList.size(); counter++){
				System.out.printf("%s) %s \n", counter+1, partNumsList.get(counter));
			}
			

		}
}
